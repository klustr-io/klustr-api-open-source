package io.klustr.storage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.klustr.storage.docs.DocumentDatabase;
import io.klustr.storage.docs.DocumentDatabaseFactory;
import io.klustr.storage.docs.DocumentTable;
import io.klustr.storage.cache.DbAdapterCache;
import io.klustr.storage.cache.DbAdapterCacheDefaultImpl;
import io.klustr.storage.docs.DocumentTableStats;
import io.klustr.storage.query.Db;
import io.klustr.storage.query.DbQuery;
import io.klustr.storage.query.DbSort;
import io.klustr.utils.Json;
import io.klustr.utils.U;
import io.micrometer.core.instrument.MeterRegistry;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The DB adapter is a simplified abstraction over CRUD operations inside a database
 * and its current implementation wraps RethinkDB. In future iterations this
 * should be replaced with a DB resolver allowed developers to switch the implementation
 * to DocumentDB, CouchDB, MongoDb, or other document datasources.
 */
public class DbAdapter<T> {
    private static final Logger log = LoggerFactory.getLogger(DbAdapter.class);
    private final String table;
    private final DocumentTable<T> dbTable;
    private final Class<T> clazz;
    private final DbAdapterCache<T> cache;
    private DbAdapterListener<T> listener;
    private final DocumentDatabase db;

    public DbAdapter(String db, String table, DocumentDatabaseFactory f, Class<T> clazz) {
        this.table = table;
        this.clazz = clazz;
        this.db = f.resolve();
        this.dbTable = this.db.table(db, this.table, clazz);
        this.cache = new DbAdapterCacheDefaultImpl<>(table);
    }

    public DbAdapter(String db, String table, DocumentDatabaseFactory f, Class<T> clazz, DbAdapterBroadcasterFactory bcf) {
        this.table = table;
        this.clazz = clazz;
        this.listener = bcf != null ? bcf.create(clazz, db, table) : null;
        this.db = f.resolve();
        this.dbTable = this.db.table(db, this.table, clazz);
        this.cache = new DbAdapterCacheDefaultImpl<>(table);
    }

    public DbAdapter(String db, String table, DocumentDatabaseFactory f, Class<T> clazz, DbAdapterBroadcasterFactory bcf, DbAdapterCache<T> cache) {
        this.table = table;
        this.clazz = clazz;
        this.listener = bcf != null ? bcf.create(clazz, db, table) : null;
        this.cache = cache;
        this.db = f.resolve();
        this.dbTable = this.db.table(db, this.table, clazz);
    }

    public DbAdapter<T> ensureIndex(String index) {
        this.dbTable.ensureIndex(index);
        return this;
    }

    public void deleteAllRecords() {
        List<String> docs = this.listObjectIds(Db.all(), Pagination.all()).docs;
        docs.forEach(this::deleteObject);
    }

    public void insertObject(String id, final T object) {
        JsonNode json = object instanceof JsonNode ? (JsonNode) object : U.fromJson(Json.toJson(object), JsonNode.class);
        this.dbTable.insert(id, json);
        if (cache != null) {
            this.cache.invalidate(id, object);
            this.cache.invalidateAll();
        }
        if (listener != null) {
            this.listener.onCreate(id, object);
        }
    }

    public void insertObject(final T object) {
        JsonNode json = object instanceof JsonNode ? (JsonNode) object : U.fromJson(Json.toJson(object), JsonNode.class);
        String id = getId(json);
        this.dbTable.insert(id, json);
        if (cache != null) {
            this.cache.invalidate(id, object);
            this.cache.invalidateAll();
        }
        if (listener != null) {
            this.listener.onCreate(id, object);
        }
    }

    public T getObject(final String id) {
        Optional<T> t = tryGetObject(id);
        return t.orElse(null);
    }

    public T getObjectNoCache(final String id) {
        Optional<T> t = tryGetObjectNoCache(id);
        return t.orElse(null);
    }

    public boolean exists(final String id) {
        return tryGetObjectNoCache(id).isPresent();
    }

    public Optional<T> tryGetObject(final String id) {
        Nullable<T> obj = this.cache.getIfPresent(id);
        if (obj != null) {
            return obj.toOptional();
        }

        Optional<T> match = this.dbTable.getDocument(id);
        return this.cache.put(id, match);
    }

    public Optional<T> tryGetObjectNoCache(final String id) {
        Optional<T> match = this.dbTable.getDocument(id);
        return this.cache.put(id, match);
    }

    public void updateObject(String id, final T obj) {
        JsonNode json = obj instanceof JsonNode ? (JsonNode) obj : U.fromJson(Json.toJson(obj), JsonNode.class);
        boolean updated = this.dbTable.update(id, json);
        if (updated) {
            if (this.listener != null) {
                this.listener.onUpdate(id, obj);
            }
        } else {
            // skip broadcasts and cache invalidation.
        }
        if (this.cache != null) {
            this.cache.put(id, Optional.of( obj));
        }
    }

    public void updateObject(final T obj) {
        JsonNode json = obj instanceof JsonNode ? (JsonNode) obj : U.fromJson(Json.toJson(obj), JsonNode.class);
        String id = getId(json);
        boolean updated = this.dbTable.update(id, json);
        if (updated) {
            if (this.listener != null) {
                this.listener.onUpdate(id, obj);
            }
        } else {
            // skip broadcasts and cache invalidation.
        }
        if (this.cache != null) {
            this.cache.put(id, Optional.of( obj));
        }
    }

    public void deleteObject(final String id) {
        T object = this.getObject(id);
        if (object == null) return;
        this.dbTable.delete(id);
        if (this.listener != null) {
            this.listener.onDelete(id, object);
        }
        if (this.cache != null) {
            this.cache.invalidate(id, object);
            this.cache.invalidateAll();
        }
    }

    public DocumentResult<String> listObjectIds(DbQuery q, Pagination pagination) {
        return this.dbTable.getDocumentIds(q, pagination);
    }

    public Optional<T> findFirst(DbQuery q) {
        DocumentResult<T> query = insecureQuery(q, new Pagination().withLimit(1));
        if (query.count <= 0) {
            return Optional.empty();
        }
        if (query.docs == null) {
            return Optional.empty();
        }
        if (query.docs.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of( query.docs.get(0));
    }

    public DocumentResult<T> insecureQuery(DbQuery q, DbSort orderBy, Pagination pagination) {
        String cacheKey = q.toSolrQuery() + ":" + orderBy + ":" + Json.toJson(pagination);
        DocumentResult<T> match = this.cache.tryGetCachedQuery(cacheKey);
        if (match != null && pagination.cache) {
            return match;
        }
        match = this.dbTable.insecureQuery(q,orderBy,pagination);
        if (pagination.cache) {
            this.cache.updateCacheQuery(cacheKey, match);
        }
        return match;
    }

    public DbAdapter<T> noCache() {
        this.cache.invalidateAll();
        return this;
    }

    public DocumentResult<T> insecureQuery(DbQuery q, Pagination pagination) {
        String cacheKey = q.toSolrQuery() + ":" + Json.toJson(pagination);
        DocumentResult<T> match = null;
        if (pagination.cache) {
            match = this.cache.tryGetCachedQuery(cacheKey);
            if (match != null) {
                return match;
            }
        }
        match =  this.dbTable.insecureQuery(q,pagination);
        if (pagination.cache) {
            this.cache.updateCacheQuery(cacheKey, match);
        }
        return match;
    }

    public DocumentResult<T> insecureQuery(Pagination pagination) {
        String cacheKey = Json.toJson(pagination);
        DocumentResult<T> match = this.cache.tryGetCachedQuery(cacheKey);
        if (match != null && pagination.cache) {
            return match;
        }
        match = this.dbTable.insecureQuery(pagination);
        if (pagination.cache) {
            this.cache.updateCacheQuery(cacheKey, match);
        }
        return match;
    }

    /**
     * Returns stats for the specified intance of the table.
     */
    public Optional<DocumentTableStats> stats() {
        return this.dbTable.stats();
    }

    protected JsonNode enrich(JsonNode node) {
        return node;
    }

    protected String getId(JsonNode node) {
        if (!(node instanceof ObjectNode obj)) {
            throw new IllegalArgumentException("Document must be a JSON object");
        }
        JsonNode idNode = obj.get("id");
        if (!canGenerateIds()) {
            if (idNode == null || idNode.textValue() == null) {
                throw new RuntimeException("Node missing identifier");
            }
        }
        if (idNode == null || StringUtils.isBlank(idNode.textValue())) {
            obj.put("id", UUID.randomUUID().toString());
        }
        return idNode.asText();
    }

    protected boolean canGenerateIds() {
        return false;
    }
}
