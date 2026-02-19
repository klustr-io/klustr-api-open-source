package io.klustr.storage.query;

import com.rethinkdb.gen.ast.ReqlFunction1;

public class DbQueryEverything implements DbQuery {

    @Override
    public ReqlFunction1 rethinkDbQuery() {
        return x -> true;
    }

    @Override
    public String toSolrQuery() {
        return "*:*";
    }
}
