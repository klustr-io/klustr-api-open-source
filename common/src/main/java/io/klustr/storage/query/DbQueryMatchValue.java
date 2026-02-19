package io.klustr.storage.query;

import com.rethinkdb.gen.ast.ReqlFunction1;

public class DbQueryMatchValue implements DbQuery {
    private final String field;
    private String regex;

    public DbQueryMatchValue(String field) {
        this.field = field;
    }

    protected DbQueryMatchValue match(String regex) {
        this.regex = regex;
        return this;
    }

    public ReqlFunction1 rethinkDbQuery() {
        return row -> row.g(this.field).match(this.regex);
    }

    @Override
    public String toSolrQuery() {
        return this.field + ":" + this.regex;
    }
}
