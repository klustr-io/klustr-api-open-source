package io.klustr.storage.docs.rethinkdb;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.rethinkdb.gen.ast.Db;
import com.rethinkdb.gen.ast.Table;
import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Result;
import io.klustr.storage.docs.DocumentDatabase;
import io.klustr.storage.docs.DocumentTable;
import io.klustr.utils.U;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RethinkDbDocumentDatabase implements DocumentDatabase {

    private static final Logger log = LoggerFactory.getLogger(RethinkDbDocumentDatabase.class);

    private static final Map<String, RethinkDbTable> _tables = Maps.newConcurrentMap();

    private final RethinkDbConnectionPool pool;
    private static final Object _lock = new Object();

    public RethinkDbDocumentDatabase(RethinkDbConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public void deleteDatabase(String db) {
        try (RethinkDbConnection r = this.pool.getInstance()) {
            Connection conn = r.getConnection();
            try {
                r.getDriver().dbDrop(db).run(conn);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void deleteTable(String database, String table) {
        try (RethinkDbConnection r = this.pool.getInstance()) {
            Connection conn = r.getConnection();
            try {
                r.getDriver().db(database).tableDrop(table).run(conn);
            } catch (Exception ex) {
                // no worries database is already gone
                throw new RuntimeException(ex);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public Set<String> getTables(String db) {
        try (RethinkDbConnection r = this.pool.getInstance()) {
            Connection conn = r.getConnection();
            Db driver = r.getDriver().db(db);
            Result<Object> tables = null;
            try {
                tables = driver.tableList().run(conn);
            } catch (Exception ex) {
                // Database doesn't exist yet, create it
                r.getDriver().dbCreate(db).run(conn);
                driver = r.getDriver().db(db);
                tables = driver.tableList().run(conn);
            }

            Set<String> tableList = Sets.newConcurrentHashSet();
            if (tables != null) {
                for (Object obj : tables) {
                    if (obj instanceof String val) {
                        tableList.add(val);
                    } else if (obj instanceof ArrayList<?> items) {
                        tableList.addAll(items.stream().map(Object::toString).toList());
                    }
                }
            }
            return tableList;
        } catch (Exception ex) {
            return Sets.newHashSet();
        }
    }

    private void cleanDupes(String db, String table) {
        try (RethinkDbConnection r = this.pool.getInstance()) {
            Connection conn = r.getConnection();
            Db driver = r.getDriver().db(db);
            r.getDriver()
                    .db("rethinkdb")
                    .table("table_config")
                    .filter(row -> row.g("db").eq(db).and(row.g("name").eq(table)))
                    .orderBy("id")
                    .slice(1)
                    .delete()
                    .run(conn);
        } catch (Exception ex) {
            log.error("Unable to clean dupes for table {}", table, ex);
        }
    }

    private void ensureTableExists(String db, String table) {

        try (RethinkDbConnection r = this.pool.getInstance()) {
            Connection conn = r.getConnection();
            Db driver = r.getDriver().db(db);

                // Step 3: Re-check and create the target table if needed
                Set<String> existingTables = getTables(db);
                if (!existingTables.contains(table)) {
                    driver.tableCreate(table).run(conn);
                    cleanDupes(db, table);
                }
        } catch (Exception ex) {
            log.error("Unable to lock and create table {}", table, ex);
        }
    }


    @Override
    public <T> DocumentTable<T> table(String db, String table, Class<T> clazz) {
        synchronized (_lock) {
            String key = db + "." + table;
            RethinkDbTable<T> match = _tables.get(key);
            if (match != null) {
                return match;
            }

            try (RethinkDbConnection r = this.pool.getInstance()) {
                Connection conn = r.getConnection();

                Db driver = r.getDriver().db(db);
                ensureTableExists(db, table);
                cleanDupes(db, table);

                Table rethinkTable = driver.table(table);
                match = new RethinkDbTable<T>(db, table, rethinkTable, pool, clazz);
                _tables.put(key, match);
                return match;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }


}
