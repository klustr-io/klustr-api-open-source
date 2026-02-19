package io.klustr;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import io.klustr.storage.DocumentResult;
import io.klustr.storage.Pagination;
import io.klustr.storage.docs.DocumentTable;
import io.klustr.storage.docs.http.HttpClientCredentialAuthTokenProvider;
import io.klustr.storage.docs.http.HttpDocumentDatabase;
import io.klustr.storage.docs.http.HttpClientCredentialRefreshableToken;
import io.klustr.storage.docs.http.TokenExchange;
import io.klustr.storage.query.Db;
import io.klustr.utils.Json;
import io.klustr.utils.U;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class CollectionServiceTest {
    // String API_URL = "https://api.dev.klustr.io";
    String API_URL = "http://localhost:7777";

    @Test
    public void we_fixed_and_queries() {
        TokenExchange ex = new TokenExchange("36966cbd-0b58-48fb-8c72-bf171f2f7e13", "50a1644fbb23385e", "https://hydra.dev.klustr.io/oauth2/token");

        HttpDocumentDatabase db = new HttpDocumentDatabase(API_URL, ex);
        DocumentTable<JsonNode> my_table = db.table("rize", "fitbit_ingestion_data", JsonNode.class);

        DocumentResult<JsonNode> result = my_table.insecureQuery(Db.and(
                Db.query("subject_id").eq("621b3e3c-2ae7-494b-ad83-e9e37618bfd4"),
                Db.query("status").eq("SUCCESS")
        ), Pagination.all());

        System.out.println(U.toJsonPrettyFormat(result));
    }

    @Test
    public void we_can_query_table() {
        TokenExchange ex = new TokenExchange("36966cbd-0b58-48fb-8c72-bf171f2f7e13", "50a1644fbb23385e", "https://hydra.dev.klustr.io/oauth2/token");
        HttpDocumentDatabase db = new HttpDocumentDatabase(API_URL, ex);
        // db.deleteTable("rize.fit", "my_table");
        DocumentTable<JsonNode> my_table = db.table("rize.fit", "my_table", JsonNode.class);
        my_table.ensureIndex("client_id");

        Optional<JsonNode> exists1 = my_table.getDocument("test_case_1");
        Optional<JsonNode> exists2 = my_table.getDocument("test_case_2");

        Optional<JsonNode> should_never_exist = my_table.getDocument("should_never_exist");
        assertThat(should_never_exist.isPresent()).isFalse();

        if (my_table.insecureQuery(Pagination.all()).docs.size() <= 0) {
            TestObject test_case_1 = new TestObject();
            test_case_1.child = new NestedObject("my_child_1234", "Lisa");
            test_case_1.children = new ArrayList<>();
            test_case_1.children.add(new NestedObject("my_child_abcd", "Foo"));
            test_case_1.children.add(new NestedObject("my_child_1234", "XXX"));     // ensure we match correct, as we have same id value but different level!
            test_case_1.children.add(new NestedObject("my_child_xxxx", "Lisa"));     // ensure we match correct, as we have same id value but different level!
            test_case_1.project_id = "my_project";
            test_case_1.client_id = "client_1234";
            my_table.insert("test_case_1", TestObject.Serialize.toNode(test_case_1));

            TestObject test_case_2 = new TestObject();
            test_case_2.child = new NestedObject("hey_you", "Lisa");
            test_case_2.children = new ArrayList<>();
            test_case_2.children.add(new NestedObject("bob", "Bob"));
            test_case_2.project_id = "my_project_other_project";
            test_case_2.client_id = "client_abc";
            my_table.insert("test_case_2", TestObject.Serialize.toNode(test_case_2));
        }

        DocumentResult<JsonNode> result;

        result = my_table.insecureQuery(Pagination.all());
        System.out.println(U.toJsonPrettyFormat(result));
        assertThat(result.docs.size()).isEqualTo(2);


        result = my_table.insecureQuery(Db.query("client_id").eq("client_abc"), Pagination.all());
        System.out.println(U.toJsonPrettyFormat(result));
        assertThat(result.docs.size()).isEqualTo(1);

        result = my_table.insecureQuery(Db.query("client_id").eq("client_NOT_EXIST"), Pagination.all());
        System.out.println(U.toJsonPrettyFormat(result));
        assertThat(result.docs.size()).isEqualTo(0);

        result = my_table.insecureQuery(Db.query("children").contains("id").eq("bob"), Pagination.all());
        System.out.println(U.toJsonPrettyFormat(result));
        assertThat(result.docs.size()).isEqualTo(1);

        String json = U.toJson(result);
        TypeReference<DocumentResult<TestObject>> typeReference = new TypeReference<>() {
        };
        DocumentResult<TestObject> r = Json.parse(json, typeReference);
        assertThat(r.docs.get(0).id).isEqualTo("test_case_2");

        exists1 = my_table.getDocument("test_case_1");
        assertThat(exists1.isPresent()).isTrue();

        exists2 = my_table.getDocument("test_case_2");
        assertThat(exists2.isPresent()).isTrue();
    }
}
