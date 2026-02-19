import com.rethinkdb.RethinkDB;
import com.rethinkdb.gen.ast.Table;
import io.klustr.storage.docs.rethinkdb.RethinkDbConnection;
import io.klustr.storage.docs.rethinkdb.RethinkDbConnectionPool;
import io.klustr.utils.Json;
import io.klustr.utils.U;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestConsentScopeStats {

    @Test
    public void test() throws Exception {
        RethinkDbConnectionPool pool = new RethinkDbConnectionPool("rethinkdb.dev.klustr.io", 28015, new CompositeMeterRegistry());
        String tableName = "consent_audit"; // replace with your table name

        try (RethinkDbConnection r = pool.getInstance()) {
            Table table = r.getDriver().db("consent").table(tableName);
            List<Map<String,String>> docs = table
                    .pluck("id", "app_id", "client_id", "approved_scopes", "subject_id", "timestamp")
                    .concatMap(doc -> RethinkDB.r.expr(doc.getField("approved_scopes")).map(scope ->
                            doc.without("approved_scopes").merge(RethinkDB.r.hashMap("scope", scope))
                    )).run(r.getConnection()).stream().map(x -> {
                        return (Map<String,String>)x;
                            }).toList();

            try {
                // Print the flattened documents
                r.ensureDatabase("consent_analytics");
                r.getDriver().db("consent_analytics").tableCreate("raw").run(r.getConnection());
            } catch (Exception ex) {
                // already exists likely
            }

            Table analytics = r.getDriver().db("consent_analytics").table("raw");
            for (Map<String,String> doc : docs) {
                // System.out.println("\n");
                doc.put("id", U.md5(doc.toString()));
                analytics.insert(doc).run(r.getConnection());
            }


            Object result = analytics
                        .filter(RethinkDB.r.hashMap("scope", "profile"))
                        // .filter(RethinkDB.r.hashMap("org_id", scopeId))
                        .group("client_id")
                        // .map(group -> group.g("client_id").count())
                        .count()
                        .ungroup()
                        .run(r.getConnection()).first();

            List<GroupCount> x = Json.parseGenericType(Json.toJson(result), ArrayList.class, GroupCount.class);
            for (GroupCount doc : x) {
                System.out.println(doc);
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static class GroupCount {
        public String group;
        public Integer reduction;
    }
}
