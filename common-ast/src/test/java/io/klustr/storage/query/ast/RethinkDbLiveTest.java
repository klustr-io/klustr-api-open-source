package io.klustr.storage.query.ast;

import com.fasterxml.jackson.databind.JsonNode;
import com.rethinkdb.gen.ast.ReqlFunction1;
import io.klustr.storage.DbAdapter;
import io.klustr.storage.DocumentResult;
import io.klustr.storage.Pagination;
import io.klustr.storage.docs.DocumentDatabaseFactory;
import io.klustr.storage.docs.rethinkdb.RethinkDbConnectionPool;
import io.klustr.storage.docs.rethinkdb.RethinkDbDocumentDatabaseFactory;
import io.klustr.storage.query.DbQueryRaw;
import io.klustr.storage.query.parser.QueryParserToRethinkDb;
import io.klustr.utils.U;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class RethinkDbLiveTest {

    static RethinkDbConnectionPool pool = new RethinkDbConnectionPool("rethinkdb.dev.klustr.io", 28015);
    static DocumentDatabaseFactory f = new RethinkDbDocumentDatabaseFactory(pool);
    static final String TABLE = "test";
    static final String DB = "test";
    static final String ID = "59a1b022-7e83-4c11-97a4-872d3e820c88";
    static DbAdapter<JsonNode> tbl = new DbAdapter<>(DB, TABLE, f, JsonNode.class);

    @BeforeAll
    public static void setup_database_and_sample_date() {
    }

    @BeforeEach
    public void data() {
        String jsonString = U.getResourceAsString("photo.json", this);
        JsonNode node = U.fromJson(jsonString, JsonNode.class);
        assertThat(node).isNotEmpty();

        System.out.println(U.toJson(node));

        tbl.deleteObject(ID);
        tbl.insertObject(ID, node);
        JsonNode saved = tbl.getObject(ID);
        assertThat(saved).isNotEmpty();

    }

    @Test
    public void we_can_query_by_field_equality() {
        ReqlFunction1 fn = QueryParserToRethinkDb.parseAndBuild("_etag:433bfbc2515aa820f85de2043780f5a2");
        assertThat(fn).isNotNull();

        DocumentResult<JsonNode> result = tbl.insecureQuery(new DbQueryRaw(fn), Pagination.all());
        assertThat(result.count).isGreaterThan(0);
        System.out.println(U.toJsonPrettyFormat(result));
    }

    @Test
    public void we_can_query_by_field_equality_should_not_match() {
        ReqlFunction1 fn = QueryParserToRethinkDb.parseAndBuild("_etag:433bfbc2515aa820f85de20437");
        assertThat(fn).isNotNull();

        DocumentResult<JsonNode> result = tbl.insecureQuery(new DbQueryRaw(fn), Pagination.all());
        assertThat(result.count).isEqualTo(0);
    }

    @Test
    public void we_can_query_by_nested_field_equality() {
        ReqlFunction1 fn = QueryParserToRethinkDb.parseAndBuild("classifiers.mood:peaceful");
        assertThat(fn).isNotNull();

        DocumentResult<JsonNode> result = tbl.insecureQuery(new DbQueryRaw(fn), Pagination.all());
        assertThat(result.count).isGreaterThan(0);
        System.out.println(U.toJsonPrettyFormat(result));
    }

    @Test
    public void we_can_query_by_nested_field_equality_should_not_match() {
        ReqlFunction1 fn = QueryParserToRethinkDb.parseAndBuild("classifiers.mood:peace");
        assertThat(fn).isNotNull();

        DocumentResult<JsonNode> result = tbl.insecureQuery(new DbQueryRaw(fn), Pagination.all());
        assertThat(result.count).isEqualTo(0);
    }

    @Test
    public void we_can_query_by_nested_field_regex() {
        ReqlFunction1 fn = QueryParserToRethinkDb.parseAndBuild("classifiers.mood:peace.*");
        assertThat(fn).isNotNull();

        DocumentResult<JsonNode> result = tbl.insecureQuery(new DbQueryRaw(fn), Pagination.all());
        assertThat(result.count).isGreaterThan(0);
        System.out.println(U.toJsonPrettyFormat(result));
    }

    @Test
    public void we_can_query_by_nested_field_collection() {
        ReqlFunction1 fn = QueryParserToRethinkDb.parseAndBuild("classifiers.objects[]:cat");
        assertThat(fn).isNotNull();

        DocumentResult<JsonNode> result = tbl.insecureQuery(new DbQueryRaw(fn), Pagination.all());
        assertThat(result.count).isGreaterThan(0);
        System.out.println(U.toJsonPrettyFormat(result));
    }

    @Test
    public void we_can_query_by_nested_field_collection_should_not_match() {
        ReqlFunction1 fn = QueryParserToRethinkDb.parseAndBuild("classifiers.objects[]:dog");
        assertThat(fn).isNotNull();

        DocumentResult<JsonNode> result = tbl.insecureQuery(new DbQueryRaw(fn), Pagination.all());
        assertThat(result.count).isEqualTo(0);
        System.out.println(U.toJsonPrettyFormat(result));
    }

    @Test
    public void we_can_query_by_range() {
        ReqlFunction1 fn = QueryParserToRethinkDb.parseAndBuild("_creation_date:[* TO NOW]");
        assertThat(fn).isNotNull();

        DocumentResult<JsonNode> result = tbl.insecureQuery(new DbQueryRaw(fn), Pagination.all());
        assertThat(result.count).isGreaterThan(0);
        System.out.println(U.toJsonPrettyFormat(result));
    }

    @Test
    public void we_can_query_by_range_negative() {
        ReqlFunction1 fn = QueryParserToRethinkDb.parseAndBuild("_creation_date:[* TO NOW/YEAR-10YEARS]");
        assertThat(fn).isNotNull();

        DocumentResult<JsonNode> result = tbl.insecureQuery(new DbQueryRaw(fn), Pagination.all());
        assertThat(result.count).isLessThanOrEqualTo(0);
        System.out.println(U.toJsonPrettyFormat(result));
    }

    @Test
    public void we_can_query_by_range_all() {
        ReqlFunction1 fn = QueryParserToRethinkDb.parseAndBuild("_creation_date:[* TO *]");
        assertThat(fn).isNotNull();

        DocumentResult<JsonNode> result = tbl.insecureQuery(new DbQueryRaw(fn), Pagination.all());
        assertThat(result.count).isGreaterThan(0);
        System.out.println(U.toJsonPrettyFormat(result));
    }

    @Test
    public void complex_query() {
        ReqlFunction1 fn = QueryParserToRethinkDb.parseAndBuild("owner.acls[].global.sub.list[]:x");
        assertThat(fn).isNotNull();

        DocumentResult<JsonNode> result = tbl.insecureQuery(new DbQueryRaw(fn), Pagination.all());
        assertThat(result.count).isGreaterThan(0);
        System.out.println(U.toJsonPrettyFormat(result));
    }
}
