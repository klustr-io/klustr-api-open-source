package io.klustr.storage.query;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.gen.ast.ReqlFunction1;

public class DbQueryFieldExists implements DbQuery {
    private final String field;

    public DbQueryFieldExists(String field) {
        this.field = field;
    }

    public DbQueryFieldExists exists() {
        return this;
    }

    public ReqlFunction1 rethinkDbQuery() {
        return row -> {
            return row.hasFields(this.field);
        };
    }

    @Override
    public String toSolrQuery() {
        return  this.field + ":*";
    }
}
