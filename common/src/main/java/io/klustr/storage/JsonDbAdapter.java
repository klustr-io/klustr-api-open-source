package io.klustr.storage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.klustr.storage.docs.DocumentDatabaseFactory;
import io.klustr.utils.U;

import java.util.UUID;

public class JsonDbAdapter extends DbAdapter<JsonNode> {
    public JsonDbAdapter(String db, String table, DocumentDatabaseFactory f, Class<JsonNode> clazz) {
        super(db, table, f, clazz);
    }

    @Override
    protected JsonNode enrich(JsonNode node) {
        if (!(node instanceof ObjectNode obj)) {
            throw new IllegalArgumentException("Document must be a JSON object");
        }
        long now = System.currentTimeMillis();
        if (!obj.has("id")) {
            obj.put("id", UUID.randomUUID().toString());
        }
        if (!obj.has("_creation_date")) {
            obj.put("_creation_date", now);
        }
        obj.put("_modified_date", now);
        obj.put("_etag", U.md5(U.toJson(obj)));
        return obj;
    }

    @Override
    protected boolean canGenerateIds() {
        return true;
    }
}
