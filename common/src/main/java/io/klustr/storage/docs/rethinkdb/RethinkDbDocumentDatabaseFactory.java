package io.klustr.storage.docs.rethinkdb;

import io.klustr.storage.docs.DocumentDatabase;
import io.klustr.storage.docs.DocumentDatabaseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "rethinkdb.hostname")
public class RethinkDbDocumentDatabaseFactory implements DocumentDatabaseFactory {

    private final RethinkDbConnectionPool pool;

    @Autowired
    public RethinkDbDocumentDatabaseFactory(RethinkDbConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public DocumentDatabase resolve() {
        return new RethinkDbDocumentDatabase(this.pool);
    }
}
