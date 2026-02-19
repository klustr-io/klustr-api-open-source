package io.klustr.storage.query;

import com.rethinkdb.gen.ast.ReqlFunction1;

public class DbQueryContains implements DbQuery {
    private final String parent;
    private String field;
    private Object value;

    public DbQueryContains(String parent) {
        this.parent = parent;
    }

    public DbQueryContains field(String field) {
        this.field = field;
        return this;
    }

    public DbQuery eq(Object value) {
        this.value = value;
        return this;
    }

    @Override
    public ReqlFunction1 rethinkDbQuery() {
        return row -> row.g(this.parent).contains(x -> {
            return x.g(field).eq(value);
        });
    }

    @Override
    public String toSolrQuery() {
        return this.parent + "[]." + this.field + ":" + this.value;
    }
}
