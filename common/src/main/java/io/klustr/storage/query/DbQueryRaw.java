package io.klustr.storage.query;

import com.rethinkdb.gen.ast.ReqlExpr;
import com.rethinkdb.gen.ast.ReqlFunction1;
import io.klustr.utils.U;

public class DbQueryRaw implements DbQuery {

    private final ReqlFunction1 exp;

    public DbQueryRaw(ReqlFunction1 exp) {
        this.exp = exp;
    }


    @Override
    public ReqlFunction1 rethinkDbQuery() {
        return exp;
    }

    @Override
    public String toSolrQuery() {
        return U.md5(U.toJson(exp));    // TODO hack
    }
}
