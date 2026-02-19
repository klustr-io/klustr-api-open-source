package io.klustr.storage.query;

import com.rethinkdb.gen.ast.ReqlFunction1;

/**
 * The query that can run against our document database.
 */
public interface DbQuery {

    /**
     * Converts the DbQuery into a RethinkDB executable function.
     *
     * @return The db query to process.
     */
    ReqlFunction1 rethinkDbQuery();

    /**
     * Converts the query into human readable string.
     *
     * @return The query string to return.
     */
    String toSolrQuery();
}
