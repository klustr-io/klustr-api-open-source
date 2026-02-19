package io.klustr;

import com.fasterxml.jackson.databind.JsonNode;
import com.rethinkdb.gen.ast.ReqlFunction1;
import io.klustr.storage.Pagination;
import io.klustr.storage.docs.DocumentTable;
import io.klustr.storage.docs.DocumentTableStats;
import io.klustr.storage.docs.rethinkdb.*;
import io.klustr.storage.query.Db;
import io.klustr.storage.query.DbQuery;
import io.klustr.storage.query.DbQueryAndCondition;
import io.klustr.storage.query.DbQueryRaw;
import io.klustr.storage.query.parser.SolrLikeQueryToRethinkDbQueryParser;
import io.klustr.utils.U;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

public class RethinkDbTest {

    @Test
    public void can_get_status() {
        RethinkDbConnectionProperties properties = new RethinkDbConnectionProperties();
        properties.setHostname("rethinkdb.dev.klustr.io");
        properties.setPort(28015);
        RethinkDbConnectionPool pool = new RethinkDbConnectionPool(properties);
        Optional<DocumentTableStats> tableStats = pool.getTableStats("consent", "consent_scopes");
        System.out.println(U.toJsonPrettyFormat(tableStats.get()));
    }

    @Test
    public void can_do_and() {
        RethinkDbConnectionProperties properties = new RethinkDbConnectionProperties();
        properties.setHostname("rethinkdb.dev.klustr.io");
        properties.setPort(28015);
        RethinkDbConnectionPool pool = new RethinkDbConnectionPool(properties);
        RethinkDbDocumentDatabaseFactory f = new RethinkDbDocumentDatabaseFactory(pool);
        DocumentTable<JsonNode> table = f.resolve().table("rizefit_0c9f7354865897d830cf6d11d3913461", "fitbit_ingestion_data", JsonNode.class);
        DbQueryAndCondition condition = Db.and(
                Db.query("subject_id").eq("621b3e3c-2ae7-494b-ad83-e9e37618bfd4"),
                Db.query("status").eq("SUCCESS")
        );
        System.out.println(U.toJsonPrettyFormat(condition.rethinkDbQuery()));
        List<JsonNode> docs = table.insecureQuery(condition, Pagination.all()).docs;
        System.out.println("Docs -> " + docs.size());

        System.out.println("------------");

        ReqlFunction1 query = SolrLikeQueryToRethinkDbQueryParser.toReSQL("subject_id:621b3e3c-2ae7-494b-ad83-e9e37618bfd4 AND status:SUCCESS");
        DbQueryRaw fq = new DbQueryRaw(query);
        System.out.println(U.toJsonPrettyFormat(query));
        docs = table.insecureQuery(fq, Pagination.all()).docs;
        System.out.println("Docs -> " + docs.size());
    }
}
