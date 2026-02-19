package io.klustr.storage.docs.rethinkdb;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Result;
import io.klustr.utils.Json;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Placeholder for use with our {@link RethinkDbConnectionPool} to gain
 * performance when under multithreaded service and API access to data.
 */
public class RethinkDbConnection implements AutoCloseable {
    private static final Logger log = LoggerFactory.getLogger(RethinkDbConnection.class);
    private final RethinkDB r = RethinkDB.r;
    private final Connection conn;
    private final String id = UUID.randomUUID().toString();

    protected RethinkDbConnection(String hostname, int port) {
        this.conn = r.connection().hostname(hostname).port(port).connect();
    }

    public void ensureDatabase(String name) {
        Result<Object> result = r.dbList().run(this.getConnection());
        Optional<Object> l = result.stream().toList().stream().findFirst();
        List<String> dbs = Json.parseGenericType(Json.toJson(l.get()), List.class, String.class);
        if (dbs.contains(name)) {
            return;
        }
            Result<Object> done = r.dbCreate(name).run(this.getConnection());
    }

    public Connection getConnection() {
        return this.conn;
    }

    public RethinkDB getDriver() {
        return this.r;
    }

    public String getId() { return this.id; }

    @Override
    public void close() throws Exception {
        if (this.conn.isOpen()) {
            IOUtils.closeQuietly(this.conn);
        }
    }
}
