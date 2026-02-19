package io.klustr.storage.docs;

import java.util.List;
import java.util.Set;

/**
 * A document database that assumes serialization of the object to a document and back again.
 */
public interface DocumentDatabase {
    /**
     * Creates and returns a reference to the specified table.
     * @param db The database to connect with
     * @param table The table to create or connect
     * @param clazz The class of object stored in this table
     * @return Returns the table created or looked up.
     * @param <T> The type of object stored.
     */
    <T> DocumentTable<T> table(String db, String table, Class<T> clazz);

    /**
     * Returns the available tables under the specified database.
     * @return Returns the tables under this database.
     */
    Set<String> getTables(String database);

    /**
     * Deletes the specified table in the specified database.
     * @param database The database
     * @param table The table to delete
     */
    void deleteTable(String database, String table);

    /**
     * Deletes the entire database.
     * @param database The database name to delete.
     */
    void deleteDatabase(String database);
}
