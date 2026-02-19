package io.klustr.storage.query;

import com.rethinkdb.gen.ast.ReqlExpr;
import com.rethinkdb.gen.ast.ReqlFunction1;

public class DbQueryRange implements DbQuery {
    private final String field;
    private final Object lower;
    private final Object upper;
    private final boolean includeLower;
    private final boolean includeUpper;

    public DbQueryRange(String field, Object lower, Object upper,
                        boolean includeLower, boolean includeUpper) {
        this.field = field;
        this.lower = lower;
        this.upper = upper;
        this.includeLower = includeLower;
        this.includeUpper = includeUpper;
    }

    @Override
    public ReqlFunction1 rethinkDbQuery() {
        return doc -> {
            ReqlExpr expr = doc.g(field);
            ReqlExpr lowerCheck = (lower != null)
                    ? (includeLower ? expr.ge(lower) : expr.gt(lower))
                    : null;
            ReqlExpr upperCheck = (upper != null)
                    ? (includeUpper ? expr.le(upper) : expr.lt(upper))
                    : null;

            if (lowerCheck != null && upperCheck != null) {
                return lowerCheck.and(upperCheck);
            }
            return (lowerCheck != null) ? lowerCheck : upperCheck;
        };
    }

    @Override
    public String toSolrQuery() {
        String left = includeLower ? "[" : "{";
        String right = includeUpper ? "]" : "}";
        return field + ":" + left + lower + " TO " + upper + right;
    }
}
