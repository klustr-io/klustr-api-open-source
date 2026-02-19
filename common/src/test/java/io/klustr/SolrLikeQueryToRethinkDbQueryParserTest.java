package io.klustr;

import com.rethinkdb.gen.ast.ReqlFunction1;
import io.klustr.storage.query.Db;
import io.klustr.storage.query.DbQuery;
import io.klustr.storage.query.DbQueryAndCondition;
import io.klustr.storage.query.parser.SolrLikeQueryToRethinkDbQueryParser;
import io.klustr.utils.U;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class SolrLikeQueryToRethinkDbQueryParserTest {

    @Test
    public void field_exists() throws Exception {
        assertThat(Db.all().toSolrQuery()).isEqualTo("*:*");

        DbQuery fq1 = Db.query("members").contains("person_id").eq("subject1234");
        System.out.println(fq1.toSolrQuery());

        DbQueryAndCondition fq2 = Db.and(
                Db.query("subject_id").eq("subject1234"),
                Db.query("app_id").eq("app1234")
        );
        System.out.println(fq2.toSolrQuery());

        DbQuery fq3 = Db.query("clients").contains("client_id").eq("my_client_id");
        System.out.println(fq3.toSolrQuery());

        DbQuery fq4 = Db.query("members").contains("person_id").eq("subject1234");
        System.out.println(fq4.toSolrQuery());

        DbQuery fq5 = Db.query("members").with("person_id").eq("person1234");
        System.out.println(fq5.toSolrQuery());

        System.out.println(fq2.toSolrQuery());
    }
}
