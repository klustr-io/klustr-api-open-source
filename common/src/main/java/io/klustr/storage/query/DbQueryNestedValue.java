package io.klustr.storage.query;

import com.rethinkdb.gen.ast.ReqlFunction1;
import org.apache.commons.lang3.StringUtils;

public class DbQueryNestedValue implements DbQuery {
    private final String parent;
    private String field;
    private Object value;

    public DbQueryNestedValue(String parent) {
        this.parent = parent;
    }

    public DbQueryNestedValue field(String field) {
        this.field = field;
        return this;
    }

    public DbQuery eq(Object value) {
        this.value = value;
        return this;
    }

    @Override
    public ReqlFunction1 rethinkDbQuery() {
        if (this.value == null) {
            return row -> row.g(this.parent).g(this.field).not().contains();
        }
        if (this.value.toString().contains(".*")) {
            return row -> row.g(this.parent).g(this.field).match(value);
        }
        return row -> row.g(this.parent).g(this.field).eq(value);
    }

    @Override
    public String toSolrQuery() {
        return this.parent + "." + this.field + ":" + this.value;
    }
}
