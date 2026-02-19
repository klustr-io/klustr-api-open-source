package io.klustr.storage.docs.rethinkdb;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Sets;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.gen.ast.*;
import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Result;
import io.klustr.storage.DocumentResult;
import io.klustr.storage.Pagination;
import io.klustr.storage.docs.DocumentTable;
import io.klustr.storage.docs.DocumentTableStats;
import io.klustr.storage.query.DbQuery;
import io.klustr.storage.query.DbSort;
import io.klustr.utils.Json;
import io.klustr.utils.U;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class RethinkDbTable<T> implements DocumentTable<T> {

    private static final Logger log = LoggerFactory.getLogger(RethinkDbDocumentDatabase.class);

    private final RethinkDbConnectionPool pool;

    private final Table table;

    private final String tableName;

    private final String database;

    private final Class<T> clazz;

    public RethinkDbTable(String database, String tableName, Table table, RethinkDbConnectionPool pool, Class<T> clazz) {
        this.pool = pool;
        this.tableName = tableName;
        this.table = table;
        this.clazz = clazz;
        this.database = database;
    }

    public void ensureIndex(String index) {
        try (RethinkDbConnection r = this.pool.getInstance()) {
            Connection conn = r.getConnection();
            Result<Object> result = this.table.indexList().run(conn);

            Set<String> indexes = Sets.newConcurrentHashSet();
            result.stream().forEach(json -> {
                if (json instanceof ArrayList<?>) {
                    ArrayList<?> list = (ArrayList<?>) json;
                    Set<String> items = list.stream().map(Object::toString).collect(Collectors.toSet());
                    indexes.addAll(items);
                }
            });

            if (!indexes.isEmpty()) {
                if (indexes.contains(index)) return;
            }
            try {
                this.table.indexCreate(index).run(conn);
            } catch (Exception ex) {
                log.warn("Unable to create index {} on table {}", index, table, ex);
                // ignore as we can bootstrap multi-servers and end up in race condition
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void insert(String id, JsonNode node) {
        try (RethinkDbConnection db = this.pool.getInstance()) {
            this.table.insert(node).run(db.getConnection());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Optional<T> getDocument(String id) {

        try (RethinkDbConnection db = this.pool.getInstance()) {
            Result<Object> result = table.filter(row -> row.g("id").eq(id)).run(db.getConnection());
            if (!result.hasNext()) {
                return Optional.empty();
            }
            Object r = result.first();
            if (r == null) {
                return Optional.empty();
            }
            return Optional.of(deserialize(r));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean update(String id, JsonNode node) {

        // verify its there and its not difference
        Optional<T> obj = this.getDocument(id);
        if (obj.isEmpty()) {
            throw new RuntimeException("Document does not exist");
        }
        String json1 = Json.toJson(obj.get());
        String json2 = node.toString();
        if (json1.equals(json2)) {
            return false;
        }

        try (RethinkDbConnection db = this.pool.getInstance()) {
            this.table.get(id).update(node).run(db.getConnection());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return true;
    }

    @Override
    public void delete(String id) {
        try (RethinkDbConnection db = this.pool.getInstance()) {
            this.table.get(id).delete().run(db.getConnection());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void delete(DbQuery query) {
        try (RethinkDbConnection db = this.pool.getInstance()) {
            this.table.filter(query.rethinkDbQuery()).delete().run(db.getConnection());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public DocumentResult<String> getDocumentIds(DbQuery q, Pagination pagination) {
        try (RethinkDbConnection db = this.pool.getInstance()) {

            ReqlFunction1 fq = q.rethinkDbQuery();
            Limit docQuery =
                    this.table
                            .getField("id")
                            .filter(fq)
                            .skip(pagination.start)
                            .limit(pagination.limit);

            Count countQuery = this.table
                    .filter(fq)
                    .count();


            try (RethinkDbConnection r = this.pool.getInstance()) {
                Connection conn = r.getConnection();
                Result<Object> counts = countQuery.run(conn);
                Result<Object> docs = docQuery.run(conn);
                Object count = counts.first();
                DocumentResult<String> resultSet = new DocumentResult<String>();

                while (docs.hasNext()) {
                    try {
                        String value = Objects.requireNonNull(docs.next()).toString();
                        resultSet.docs.add(value);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }

                resultSet.count = (Long) count;
                resultSet.limit = pagination.limit;
                resultSet.skip = pagination.start;

                return resultSet;
            } catch (Exception ex) {
                throw new RuntimeException("Failure when querying " + this.table + " with query " + docQuery, ex);
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public DocumentResult<T> insecureQuery(DbQuery q, DbSort orderBy, Pagination pagination) {
        ReqlExpr sort = orderBy.asc() ? RethinkDB.r.asc(orderBy.getField()) : RethinkDB.r.desc(orderBy.getField());

        ReqlFunction1 fq = q.rethinkDbQuery();
        Limit docQuery =
                this.table
                        .orderBy().optArg("index", sort)
                        .filter(fq)
                        .skip(pagination.start)
                        .limit(pagination.limit);

        Count countQuery = this.table
                .filter(fq)
                .count();

        return insecureQuery(pagination, countQuery, docQuery);
    }

    @Override
    public DocumentResult<T> insecureQuery(DbQuery q, Pagination pagination) {
        ReqlFunction1 fq = q.rethinkDbQuery();

        Limit docQuery =
                this.table
                        .filter(fq)
                        .skip(pagination.start)
                        .limit(pagination.limit);

        Count countQuery = this.table
                .filter(fq)
                .count();

        return insecureQuery(pagination, countQuery, docQuery);
    }

    @Override
    public DocumentResult<T> insecureQuery(Pagination pagination) {
        Limit docQuery =
                this.table
                        .skip(pagination.start)
                        .limit(pagination.limit);

        Count countQuery = this.table
                .count();

        return insecureQuery(pagination, countQuery, docQuery);
    }

    public DocumentResult<T> insecureQuery(Pagination pagination,
                                           Count countQuery,
                                           Limit docQuery) {
        try (RethinkDbConnection r = this.pool.getInstance()) {
            Connection conn = r.getConnection();
            Result<Object> counts = countQuery.run(conn);
            Result<Object> docs = docQuery.run(conn);
            Object count = counts.first();
            DocumentResult<T> resultSet = new DocumentResult<T>();

            while (docs.hasNext()) {
                try {
                    resultSet.docs.add(deserialize(docs.next()));
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }

            resultSet.count = (Long) count;
            resultSet.limit = pagination.limit;
            resultSet.skip = pagination.start;

            return resultSet;
        } catch (Exception ex) {
            throw new RuntimeException("Failure when querying " + this.table + " with query " + docQuery, ex);
        }
    }

    private T deserialize(Object o) {
        if (o instanceof String) {
            return Json.parse((String) o, this.clazz);
        } else {
            return Json.parse(Json.toJson(o), this.clazz);
        }
    }

    @Override
    public Optional<DocumentTableStats> stats() {
        return this.pool.getTableStats(this.database, this.tableName);
    }
}
