package io.klustr.documents;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.rethinkdb.gen.ast.ReqlFunction1;
import com.webcohesion.enunciate.metadata.swagger.OperationId;
import io.klustr.documents.utils.CollectionNamingConvention;
import io.klustr.permissions.PermissionProvider;
import io.klustr.permissions.PrincipleUtils;
import io.klustr.spring.OAuthCredentialType;
import io.klustr.storage.DbAdapter;
import io.klustr.storage.DbAdapterBroadcasterFactory;
import io.klustr.storage.DocumentResult;
import io.klustr.storage.Pagination;
import io.klustr.storage.docs.DocumentDatabaseFactory;
import io.klustr.storage.docs.DocumentTableStats;
import io.klustr.storage.query.Db;
import io.klustr.storage.query.DbQuery;
import io.klustr.storage.query.DbQueryAndCondition;
import io.klustr.storage.query.DbQueryRaw;
import io.klustr.storage.query.parser.SolrLikeQueryToRethinkDbQueryParser;
import io.klustr.utils.Json;
import io.klustr.utils.U;
import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@Component
@RequestMapping("/{orgId}/collections")
@Tag(name = "Collections APIs", description = "APIs for generic storage of documents.")
public class CollectionService {

    private static final Logger log = LoggerFactory.getLogger(CollectionService.class);

    private final Cache<String, DbAdapter<JsonNode>> tables;

    private final DocumentDatabaseFactory pool;
    private final DbAdapterBroadcasterFactory factory;
    private final PermissionProvider permissions;
    private final MeterRegistry registry;

    public CollectionService(
            PermissionProvider permissions,
            DocumentDatabaseFactory pool,
            DbAdapterBroadcasterFactory factory, MeterRegistry registry) {
        this.permissions = permissions;
        this.tables = CacheBuilder.newBuilder()
                .expireAfterAccess(Duration.ofSeconds(500))
                .initialCapacity(1000)
                .maximumSize(64000)
                .recordStats()
                .build();

        this.pool = pool;
        this.factory = factory;
        this.registry = registry;
    }

    private DbAdapter<JsonNode> getTable(String db, String table) {
        String key = db + ":" + table;
        DbAdapter<JsonNode> t = this.tables.getIfPresent(key);
        if (t != null) {
            return t;
        }
        t = new DbAdapter<JsonNode>(db, table,
                this.pool, JsonNode.class, this.factory);
        this.tables.put(key, t);
        return t;
    }


    /**
     * Searches all the specified objects for the given field and term.
     *
     * @param table       The table to query.
     * @param q           The query as defined by SOLR syntax, limited operations <a href="https://docs.klustr.io/concepts/references/document-api">currently supported</a>.
     * @param oauth       The authentication context
     *
     * @return Returns the objects that match the specified result.
     */
    @GetMapping("/{table}/search")
    @OperationId("List Objects")
    public DocumentResult<JsonNode> search(
            @PathVariable("orgId") String orgId,
            @PathVariable("table") String table,
            @RequestParam(value = "q", required = false) String q,
            @RequestParam(value = "fq", required = false) List<String> andQueries,
            @RequestParam(value = "start", defaultValue = "0") Integer start,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        String db = getDatabaseName(oauth);
        List<DbQuery> queries = Lists.newArrayList();

        // core query
        if (StringUtils.isNotBlank(q) && !q.trim().equalsIgnoreCase("*") && !q.trim().equalsIgnoreCase("*:*")) {
            ReqlFunction1 fq = SolrLikeQueryToRethinkDbQueryParser.toReSQL(q);
            queries.add(new DbQueryRaw(fq));
        }

        // and queries
        if (andQueries != null && !andQueries.isEmpty()) {
            Set<DbQuery> additionalConditions = andQueries.stream()
                    .map(txt -> new DbQueryRaw(SolrLikeQueryToRethinkDbQueryParser.toReSQL(txt)))
                    .collect(Collectors.toSet());
            queries.addAll(additionalConditions);
        }
        if (queries.isEmpty()) {
            return this.getTable(db, table).insecureQuery(Pagination.limit(limit).withStart(start).withCache(false));
        }
        return this.getTable(db, table).insecureQuery(Db.and(queries), Pagination.limit(limit).withStart(start).withCache(false));
    }

    @GetMapping("/{table}/.metadata/ids")
    @OperationId("List Objects")
    public DocumentResult<String> getDocumentIds(
            @PathVariable("orgId") String orgId,
            @PathVariable("table") String table,
            @RequestParam(value = "q", required = false) String q,
            @RequestParam(value = "start", defaultValue = "0") Integer start,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        String db = getDatabaseName(oauth);
        if (StringUtils.isNotBlank(q) && !q.trim().equalsIgnoreCase("*")) {
            ReqlFunction1 fq = SolrLikeQueryToRethinkDbQueryParser.toReSQL(q);
            return this.getTable(db, table).listObjectIds(new DbQueryRaw(fq), Pagination.limit(limit).withStart(start).withCache(false));
        } else {
            return this.getTable(db, table).listObjectIds(Db.all(), Pagination.limit(limit).withStart(start).withCache(false));
        }
    }

    @GetMapping
    @Operation(
operationId = "getCollectionStats",
            summary = "Retrieve statistics for the specified collection.",
            description = """
This endpoint retrieves detailed statistics about the tables within the specified collection. It is designed for users with access to the organization's data, enabling them to understand the structure and contents of their collections. Use this information to analyze and manage your data effectively.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
)
    public ResponseEntity<Set<String>> getDbStats(@PathVariable("orgId") String orgId,
                                                  @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        String db = getDatabaseName(oauth);
        String projectId = PrincipleUtils.tryGetProjectId(oauth);
        String clientId = PrincipleUtils.tryGetClientId(oauth);
        return ResponseEntity.ok(this.pool.resolve().getTables(db));
    }

    @DeleteMapping
    @Operation(
operationId = "deleteCollectionForOrganization",
            summary = "Delete a collection for the specified organization",
            description = """
This endpoint permanently removes the entire collection linked to the provided organization ID. Ensure you possess the required permissions to execute this action. This operation is critical and should be approached with caution to avoid unintended data loss.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
)
    public ResponseEntity<OperationResult> deleteDb(@PathVariable("orgId") String orgId, @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        String db = getDatabaseName(oauth);
        this.pool.resolve().deleteDatabase(db);
        this.tables.invalidateAll();
        this.tables.cleanUp();// TODO impacts all other people
        return ResponseEntity.ok(new OperationResult());
    }

    @DeleteMapping("/{table}")
    @Operation(
operationId = "deleteCollectionTable",
            summary = "Remove a collection table for the organization.",
            description = """
This endpoint allows authorized users to delete an entire table within a specified collection for their organization. Ensure the correct organization ID and table name are provided to prevent accidental data loss. This operation is critical for effective data management.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
)
    public ResponseEntity<OperationResult> deleteTable(@PathVariable("orgId") String orgId,
                                                       @PathVariable("table") String table,
                                                       @RequestParam("q") String query,
                                                       @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        String db = getDatabaseName(oauth);
        this.pool.resolve().deleteTable(db, table);
        this.tables.invalidate(db + ":" + table);
        this.tables.cleanUp();
        return ResponseEntity.ok(new OperationResult());
    }

    public static class OperationResult {
        public boolean success = true;
    }

    @GetMapping("/{table}")
    @Operation(
operationId = "getDocumentTableStats",
            summary = "Retrieve statistics for a specific document table.",
            description = """
This endpoint provides detailed statistics about the specified document table within the organization. It includes insights into document counts and other relevant metrics. Access requires appropriate authentication to ensure data security and privacy.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
)
    public ResponseEntity<DocumentTableStats> getTableStats(@PathVariable("orgId") String orgId,
                                                  @PathVariable("table") String table,
                                                  @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        String db = getDatabaseName(oauth);
        DbAdapter<JsonNode> tableInstance = getTable(db, table);
        Optional<DocumentTableStats> stats = tableInstance.stats();
        if (stats.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NO_CONTENT, "Table does not exist or has no stats yet."
            );
        }
        return ResponseEntity.of(stats);
    }

    @GetMapping("/{table}/{id}")
    @Operation(
operationId = "getDocumentById",
            summary = "Retrieve a document by its unique ID.",
            description = """
This endpoint allows users to access a specific document within a given organization and table using its unique identifier. Ensure you have the necessary permissions to view this document. This operation is crucial for managing document access and visibility.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
)
    public ResponseEntity<JsonNode> getDocumentById(@PathVariable("orgId") String orgId,
                                        @PathVariable("table") String table,
                                        @PathVariable("id") String id,
                                        @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        String db = getDatabaseName(oauth);
        DbAdapter<JsonNode> tableInstance = getTable(db, table);
        JsonNode object = tableInstance.getObjectNoCache(id);
        if (object == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Document with identifier '" + id + "' not found."
            );
        }
        return ResponseEntity.ok(object);
    }


    @PostMapping("/{table}")
    @Operation(
operationId = "createDocumentInCollection",
            summary = "Insert a document into a specified collection.",
            description = """
This endpoint allows users to insert a document into a specified collection within the organization's database. If a document with the same ID already exists, an exception will be thrown. Ensure that the provided data adheres to the expected schema to avoid errors.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
)
    public ResponseEntity<JsonNode> createDocument(
            @PathVariable("orgId") String orgId,
            @PathVariable("table") String table,
            @RequestBody JsonNode json,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        String db = getDatabaseName(oauth);

        JsonNode doc = validate_doc_has_unique_id_and_add_audit_fields(json);

        String id = doc.get("id").asText();

        DbAdapter<JsonNode> tableInstance = getTable(db, table);
        if (tableInstance.exists(id)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Table already contains id, use PUT or other methods to update the document."
            );
        }
        tableInstance.insertObject(id, doc);
        return ResponseEntity.ok(doc);
    }

    @PostMapping("/{table}/bulk")
    @Operation(
operationId = "bulkCreateDocuments",
            summary = "Bulk insert documents into a specified collection",
            description = """
This endpoint facilitates the bulk insertion of multiple documents into a designated collection. Ensure that all provided document IDs are unique to avoid a 400 error response. If any ID already exists, the entire request will fail, so please verify uniqueness before submission.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
)
    public ResponseEntity<List<JsonNode>> bulkCreateDocuments(
            @PathVariable("orgId") String orgId,
            @PathVariable("table") String table,
            @RequestBody List<JsonNode> jsonList,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        String db = getDatabaseName(oauth);
        DbAdapter<JsonNode> tableInstance = getTable(db, table);

        List<JsonNode> validatedDocs = new ArrayList<>();

        for (JsonNode json : jsonList) {
            JsonNode doc = validate_doc_has_unique_id_and_add_audit_fields(json);
            String id = doc.get("id").asText();

            if (tableInstance.exists(id)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Table already contains id " + id + ", use PUT or other methods to update the document."
                );
            }
            tableInstance.insertObject(id, doc);
            validatedDocs.add(doc);
        }

        return ResponseEntity.ok(validatedDocs);
    }

    @PutMapping("/{table}/{id}")
    @Operation(
operationId = "updateDocumentInCollection",
            summary = "Update a document in a specified collection.",
            description = """
This endpoint updates a document identified by its ID in a specific collection. Ensure the correct organization ID and table name are provided. Use the If-Match header for concurrency control to prevent overwriting changes made by others.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
)
    public ResponseEntity<JsonNode> updateDocument(@PathVariable("orgId") String orgId,
                                           @PathVariable("table") String table,
                                           @PathVariable("id") String id,
                                           @RequestHeader(value = "If-Match", required = false) String concurrencyEtag,
                                           @RequestBody JsonNode originalRequestJson,
                                           @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        String db = getDatabaseName(oauth);

        // validate JSON document ID
        String documentId = originalRequestJson.get("id").asText();
        if (!StringUtils.isNotBlank(documentId)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "JSON document does not have an document ID."
            );
        }
        if (!id.equalsIgnoreCase(documentId)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "JSON document ID is not same as REST URL."
            );
        }

        // validate document exists to update
        DbAdapter<JsonNode> tableInstance = getTable(db, table);
        JsonNode existingDoc = tableInstance.getObject(id);
        if (existingDoc == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Table does not have any document with that ID to update."
            );
        }

        // optimistic concurrency check: optional to check when a user supplied the etag either in
        // the document or in the header to see if the document was modified while the user was editing
        // it and a race condition occured. Not providing this will OVERWRITE any changes that happened
        // between getting the ID and setting the ID.
        String currentEtag = existingDoc.has("_etag") ? existingDoc.get("_etag").asText() : null;

        // If client supplied an ETag, enforce it
        if (StringUtils.isNotBlank(concurrencyEtag) && !concurrencyEtag.equalsIgnoreCase(currentEtag)) {
            throw new ResponseStatusException(
                    HttpStatus.PRECONDITION_FAILED, "Optimistic concurrency exception, document was modified outside of scope."
            );
        } else if (originalRequestJson.get("_etag") != null) {
            String etagInDocument = originalRequestJson.get("_etag").asText();
            if (StringUtils.isNotBlank(etagInDocument) && !etagInDocument.equalsIgnoreCase(currentEtag)) {
                throw new ResponseStatusException(
                        HttpStatus.PRECONDITION_FAILED, "Optimistic concurrency exception, document was modified outside of scope."
                );
            }
        }

        // enrich
        JsonNode doc = validate_doc_has_unique_id_and_add_audit_fields(originalRequestJson);
        tableInstance.updateObject(id, doc);
        return ResponseEntity.ok(doc);
    }


    @PutMapping("/{table}/.index/{index}")
    @Operation(
operationId = "addIndexToCollectionTable",
            summary = "Add an index to a collection table.",
            description = """
This endpoint allows you to add an index to a specified table within a collection. Proper indexing is crucial for optimizing query performance and ensuring efficient data retrieval. Enhanced indexing improves the responsiveness of document storage operations.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
)
    public void ensureIndexExists(@PathVariable("orgId") String orgId,
                         @PathVariable("table") String table,
                         @PathVariable("index") String index,
                       @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        String db = getDatabaseName(oauth);
        DbAdapter<JsonNode> tableInstance = getTable(db, table);
        tableInstance.ensureIndex(index);
    }

    @DeleteMapping("/{table}/{id}")
    @Operation(
operationId = "deleteDocumentFromCollection",
            summary = "Delete a document from a specified collection.",
            description = """
This endpoint enables the deletion of a specific document from a collection within an organization. It requires the organization ID, table name, and document ID. Optionally, an ETag can be provided for concurrency control, ensuring safe deletion.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
)
    public void deleteDocument(@PathVariable("orgId") String orgId,
                       @PathVariable("table") String table,
                       @PathVariable("id") String id,
                       @RequestHeader(value = "If-Match", required = false) String concurrencyEtag,
                       @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        String db = getDatabaseName(oauth);

        // validate document exists to delete
        DbAdapter<JsonNode> tableInstance = getTable(db, table);
        JsonNode existingDoc = tableInstance.getObject(id);
        if (existingDoc == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Table does not have any document with that ID to delete."
            );
        }

        // etag check
        String currentEtag = existingDoc.has("_etag") ? existingDoc.get("_etag").asText() : null;
        if (StringUtils.isNotBlank(concurrencyEtag) && !concurrencyEtag.equalsIgnoreCase(currentEtag)) {
            throw new ResponseStatusException(
                    HttpStatus.PRECONDITION_FAILED, "Optimistic concurrency exception, document was modified outside of scope."
            );
        }

        tableInstance.deleteObject(id);
    }

    private static @NotNull String getDatabaseName(OAuth2AuthenticatedPrincipal oauth) {
        String projectId = PrincipleUtils.tryGetProjectId(oauth);
        String orgId = PrincipleUtils.tryGetOrgId(oauth);
        if (StringUtils.isBlank(projectId)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "OIDC invalid, missing project id."
            );
        }
        if (StringUtils.isBlank(projectId)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "OIDC invalid, missing organization id."
            );
        }

        return CollectionNamingConvention.getDatabase(orgId, projectId);
    }

    private JsonNode validate_doc_has_unique_id_and_add_audit_fields(JsonNode node) {
        if (!(node instanceof ObjectNode obj)) {
            throw new IllegalArgumentException("Document must be a JSON object");
        }
        long now = System.currentTimeMillis();
        if (!obj.has("id")) {
            obj.put("id", UUID.randomUUID().toString());
        }

        if (!obj.has("_creation_date")) {
            obj.put("_creation_date", now);
        }
        obj.put("_modified_date", now);

        // TODO fix performance on this etag thing
        // our audit dates shouldn't count for etag
        ObjectNode clean = (ObjectNode)Json.toJsonNode(U.toJson(obj));
        clean.remove("_modified_date");

        obj.put("_etag", U.md5(U.toJson(clean)));

        return obj;
    }
}
