package io.klustr.storage.query;

import com.rethinkdb.gen.ast.ReqlFunction1;

import java.util.Objects;

public class DbQueryNestedArrayValue implements DbQuery {
    private final String parent;
    private String field;
    private Object value;

    public DbQueryNestedArrayValue(String parent) {
        this.parent = parent;
    }

    public DbQueryNestedArrayValue field(String field) {
        this.field = field;
        return this;
    }

    public DbQueryNestedArrayValue contains(Object value) {
        this.value = Objects.requireNonNull(value, "Value for contains() must not be null");
        return this;
    }

    // RethinkDB
    public ReqlFunction1 rethinkDbQuery() {
        return doc -> doc.g(this.parent).g(this.field).contains(this.value);
    }

    @Override
    public String toSolrQuery() {
        return this.parent + "." + this.field + "[]:" + this.value;
    }
}
