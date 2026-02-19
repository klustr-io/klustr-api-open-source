package io.klustr.storage.query;

import com.rethinkdb.gen.ast.ReqlFunction1;

public class DbQueryFieldValue implements DbQuery {
    private final String field;
    private Object value;

    public DbQueryFieldValue(String field) {
        this.field = field;
    }

    public DbQueryFieldValue eq(Object value) {
        this.value = value;
        return this;
    }


    public ReqlFunction1 rethinkDbQuery() {
        return row -> row.g(this.field).eq(this.value);
    }

    @Override
    public String toSolrQuery() {
        return this.field + ":" + this.value;
    }
}
