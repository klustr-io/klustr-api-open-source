package io.klustr.storage.docs.rethinkdb;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Result;
import io.klustr.patterns.ResourcePool;
import io.klustr.patterns.ResourcePoolConfiguration;
import io.klustr.storage.docs.DocumentTableStats;
import io.klustr.utils.U;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.klustr.storage.docs.DocumentDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.function.Supplier;
import java.util.stream.StreamSupport;

/**
 * Provides a large improvement on performance by implementing a cache pool
 * for rethinkDB connections using a very simple linked queue.
 */
@Component
public class RethinkDbConnectionPool {
    private static final Logger log = LoggerFactory.getLogger(RethinkDbConnectionPool.class);

    private final RethinkDbConnectionProperties properties;

    private final ResourcePool<RethinkDbConnection> singleton;

    private static final Map<String, ResourcePool<RethinkDbConnection>> pool = Maps.newConcurrentMap();

    public RethinkDbConnectionPool(RethinkDbConnectionProperties properties) {
        this(properties, null);
    }


    @Autowired
    public RethinkDbConnectionPool(RethinkDbConnectionProperties properties,
                                   CompositeMeterRegistry meterRegistry) {

        log.info("Rethinkdb Connection {}", properties.getHostname() + ":" + properties.getPort());

        this.properties = properties;

        String key = properties.getHostname() + ":" + properties.getPort();
        if (pool.containsKey(key)) {
            singleton = pool.get(key);
            return;
        }

        synchronized (pool) {
            if (pool.containsKey(key)) {
                singleton = pool.get(key);
                return;
            }

            singleton = new ResourcePool<>("rethinkdb",
                    new ResourcePoolConfiguration()
                            .setMinConnections(10)
                            .setMaxConnections(1000)
                            .setIdleTimeoutMillis(5000),
                    new Supplier<RethinkDbConnection>() {
                        @Override
                        public RethinkDbConnection get() {
                            return new RethinkDbConnection(properties.getHostname(), properties.getPort());
                        }
                    });
            pool.put(key, singleton);

            if (meterRegistry != null) {
                Gauge.builder("console.rethinkdb.pool.claimed", () -> singleton.getActiveCount()).register(meterRegistry);
                Gauge.builder("console.rethinkdb.pool.available", () -> singleton.getAvailableCount()).register(meterRegistry);
            }
        }

    }

    public RethinkDbConnectionPool(String hostname, int port,CompositeMeterRegistry meterRegistry) {
        this(RethinkDbConnectionProperties
                .newBuilder()
                .withHostname(hostname)
                .withPort(port)
                .build(), meterRegistry);
    }

    public RethinkDbConnectionPool(String hostname, int port) {
        this(RethinkDbConnectionProperties
                .newBuilder()
                .withHostname(hostname)
                .withPort(port)
                .build(), null);
    }

    public DocumentDatabaseFactory factory() {
        return new RethinkDbDocumentDatabaseFactory(this);
    }

    public RethinkDbConnection getInstance() {
        return singleton.getResource();
    }

    protected void release(RethinkDbConnection conn) {
        singleton.releaseResource(conn);
    }

    public Optional<RethinkDbStats> getRawStats(String database, String table) {
        try (RethinkDbConnection db = this.getInstance()) {

            RethinkDB r = db.getDriver();
            Result<Object> rows = r.db("rethinkdb").table("stats")
                    .filter(row ->
                            row.g("table").eq(table)                  // table == your table param
                                    .and(row.g("db").eq(database))            // db == your database param
                                    .and(row.g("server").ne(r.expr(null)))    // server != null
                    )
                    .run(db.getConnection());

            return rows.stream().map(x -> {
                RethinkDbStats res = U.fromJson(U.toJson(x), RethinkDbStats.class);
                return res;
            }).findFirst();
        } catch (Exception ex) {
            throw new RuntimeException("Failure when querying " + table + " for stats", ex);
        }
    }

    public List<RethinkDbStats> getTableStats(String database) {
        try (RethinkDbConnection db = this.getInstance()) {

            RethinkDB r = db.getDriver();
            Result<Object> rows = r.db("rethinkdb").table("stats")
                    .filter(row ->
                            row.g("db").eq(database)                  // table == your table param
                                    .and(row.g("server").ne(r.expr(null)))    // server != null
                    )
                    .run(db.getConnection());

            return rows.stream().map(x -> {
                return U.fromJson(U.toJson(x), RethinkDbStats.class);
            }).toList();
        } catch (Exception ex) {
            throw new RuntimeException("Failure when querying database for stats.", ex);
        }
    }


    public Optional<DocumentTableStats> getTableStats(String database, String table) {
        Optional<RethinkDbStats> rawStats = this.getRawStats(database, table);
        if (rawStats.isEmpty()) return Optional.empty();

        DocumentTableStats result = new DocumentTableStats();

        // query metrics
        result.query_docs_total = rawStats.get().query_engine().read_docs_total();
        result.query_read_docs_per_sec = rawStats.get().query_engine().read_docs_per_sec();
        result.query_written_docs_total = rawStats.get().query_engine().written_docs_total();
        result.query_written_docs_per_sec = rawStats.get().query_engine().written_docs_per_sec();

        return Optional.of(result);
    }

    public List<String> getTables(String database) {
        return this.getTableStats(database).stream().map(RethinkDbStats::table).toList();
    }

}
