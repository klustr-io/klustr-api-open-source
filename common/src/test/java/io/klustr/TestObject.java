package io.klustr;

import com.fasterxml.jackson.databind.JsonNode;
import io.klustr.utils.Json;

import java.util.List;

public class TestObject {
    public String id;
    public String client_id;
    public String project_id;

    public NestedObject child;

    public List<NestedObject> children;

    public static class Serialize {
        public static JsonNode toNode(TestObject obj) {
            return Json.toJsonNode(Json.toJson(obj));
        }
    }
}

