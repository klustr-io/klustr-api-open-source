package io.klustr.storage.docs;

import com.fasterxml.jackson.databind.JsonNode;
import io.klustr.storage.DocumentResult;
import io.klustr.storage.Pagination;
import io.klustr.storage.query.DbQuery;
import io.klustr.storage.query.DbSort;

import java.util.Optional;
import java.util.Set;

/**
 * Represents a table in a document database (collection, etc)
 * @param <T>
 */
public interface DocumentTable<T> {

    /**
     * Inserts the specified JSON document.
     * @param id The id of the document being inserted
     * @param node The json object being inserted.
     */
    void insert(String id, JsonNode node);

    /**
     * Updates the specified JSON document.
     * @param id The id of the document being updated
     * @param node The json object being updated.
     */
    boolean update(String id, JsonNode node);

    /**
     * Deletes the specified JSON document.
     * @param id The id of the document being updated
     */
    void delete(String id);

    /**
     * Will delete all documents that match the specified condition.
     * @param query The query to delete.
     */
    void delete(DbQuery query);

    /**
     * Ensure the index exists on the document database, this is specific to each
     * document db.
     * @param index The index to create.
     */
    void ensureIndex(String index);

    /**
     * Returns the object with the specified ID or Empty.
     * @param id The id of the object to fetch
     * @return Returns the object.
     */
    Optional<T> getDocument(String id);

    /**
     * Returns documents that match the specified query.
     * @param q The query (can be wildcard)
     * @param pagination The pagination style to use.
     * @return Returns the document result to iterate.
     */
    DocumentResult<String> getDocumentIds(DbQuery q, Pagination pagination);

    DocumentResult<T> insecureQuery(DbQuery q, DbSort orderBy, Pagination pagination);

    DocumentResult<T> insecureQuery(DbQuery q, Pagination pagination);

    DocumentResult<T> insecureQuery(Pagination pagination);

    Optional<DocumentTableStats> stats();
}
