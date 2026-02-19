package io.klustr.storage.query;

import java.util.List;

/**
 * Simplified wrapper for our access to queries
 * so that we can run any document database and query it.
 */
public class Db {
    private final String field;

    private Db(String field) {
        this.field = field;
    }

    /**
     * Query everything
     * @return Returns the DB queries
     */
    public static DbQueryEverything all() {
        return new DbQueryEverything();
    }

    /**
     * Query a specific field in the document database.
     *
     * @param field The field to query
     * @return The DB query.
     */
    public static Db query(String field) {
        return new Db(field);
    }

    /**
     * Combine multiple queries into an AND condition
     *
     * @param args The args to combine.
     * @return The filter condition.
     */
    public static DbQueryAndCondition and(DbQuery... args) {
        return new DbQueryAndCondition(args);
    }

    public static DbQueryAndCondition and(List<DbQuery> args) {
        return new DbQueryAndCondition(args);
    }


    /**
     * The value to check for equality.
     *
     * @param value The value to check
     * @return The value to query
     */
    public DbQuery eq(Object value) {
        return new DbQueryFieldValue(field).eq(value);
    }

    /**
     * Matches the regex.
     *
     * @param regex The regex value to check against.
     * @return The value to query.
     */
    public DbQuery matchesRegex(String regex) {
        return new DbQueryMatchValue(field).match(regex);
    }

    /**
     * Query a nested value of the document.
     *
     * @param child The child to query.
     * @return
     */
    public DbQueryNestedValue with(String child) {
        return new DbQueryNestedValue(this.field).field(child);
    }

    public DbQueryContains contains(String child) {
        return new DbQueryContains(this.field).field(child);
    }

    public static DbQueryNestedArrayValue array(String parent) {
        return new DbQueryNestedArrayValue(parent);
    }
}
