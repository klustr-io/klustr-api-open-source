package io.klustr.storage.query;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.gen.ast.ReqlExpr;
import com.rethinkdb.gen.ast.ReqlFunction1;

import java.util.Arrays;
import java.util.List;

public class DbQueryAndCondition implements DbQuery {

    private List<DbQuery> ands = Lists.newArrayList();

    public DbQueryAndCondition(DbQuery... args) {
        this.ands = Arrays.stream(args).toList();
    }

    public DbQueryAndCondition(List<DbQuery> args) {
        this.ands = args;
    }

    protected DbQueryAndCondition and(DbQuery fq) {
        this.ands.add(fq);
        return this;
    }

    @Override
    public ReqlFunction1 rethinkDbQuery() {
        return row -> {
            if (ands.isEmpty()) {
                return RethinkDB.r.expr(true); // vacuously true
            }

            // Start with first predicate
            ReqlExpr expr = (ReqlExpr) ands.get(0).rethinkDbQuery().apply(row);

            // Chain the rest with AND
            for (int i = 1; i < ands.size(); i++) {
                expr = expr.and((ReqlExpr) ands.get(i).rethinkDbQuery().apply(row));
            }

            return expr;
        };
    }

    @Override
    public String toSolrQuery() {
        List<String> queries = Lists.newArrayList();
        ands.forEach(q -> {
            if (q instanceof DbQueryAndCondition) {
                throw new RuntimeException("Can not nest AND conditions!");
            }
            queries.add(q.toSolrQuery());
        });
        return Joiner.on("&fq=").join(queries);
    }
}
