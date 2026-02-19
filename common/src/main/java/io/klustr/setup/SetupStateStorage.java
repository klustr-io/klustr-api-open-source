package io.klustr.setup;

import com.fasterxml.jackson.databind.JsonNode;
import io.klustr.storage.DbAdapter;
import io.klustr.storage.docs.DocumentDatabaseFactory;
import org.springframework.stereotype.Component;

@Component
public class SetupStateStorage {
    private final DbAdapter<SetupState> db;
    private final DbAdapter<JsonNode> history;

    public SetupStateStorage(DocumentDatabaseFactory pool) {
        this.db = new DbAdapter<>("setup", "setup", pool, SetupState.class);
        this.history = new DbAdapter<>("setup", "history", pool, JsonNode.class);
    }

    public DbAdapter<SetupState> state() {
        return this.db;
    }
    public DbAdapter<JsonNode> history() {
        return this.history;
    }
}
