package io.klustr.storage.query;

import com.google.common.base.Joiner;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.gen.ast.ReqlExpr;
import com.rethinkdb.gen.ast.ReqlFunction1;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DbQueryOrCondition implements DbQuery {

    private final List<DbQuery> ors;

    public DbQueryOrCondition(DbQuery... args) {
        this.ors = Arrays.asList(args);
    }

    protected DbQueryOrCondition or(DbQuery fq) {
        ors.add(fq);
        return this;
    }

    @Override
    public ReqlFunction1 rethinkDbQuery() {
        return row -> {
            if (ors.isEmpty()) {
                return RethinkDB.r.expr(true); // vacuously true
            }

            // Start with first predicate
            ReqlExpr expr = (ReqlExpr) ors.get(0).rethinkDbQuery().apply(row);

            // Chain the rest with OR
            for (int i = 1; i < ors.size(); i++) {
                expr = expr.or((ReqlExpr) ors.get(i).rethinkDbQuery().apply(row));
            }

            return expr;
        };
    }

    @Override
    public String toSolrQuery() {
        return Joiner.on(" OR ").join(
                ors.stream().map(DbQuery::toSolrQuery).collect(Collectors.toList())
        );
    }
}
