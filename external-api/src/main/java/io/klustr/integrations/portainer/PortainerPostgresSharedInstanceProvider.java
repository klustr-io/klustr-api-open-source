package io.klustr.integrations.portainer;

import com.google.common.collect.Lists;
import io.klustr.compute.ComputeInstanceTypeProvider;
import io.klustr.compute.dns.ComputeDnsProvider;
import io.klustr.compute.ingress.ComputeLoadBalancerProvider;
import io.klustr.compute.ingress.ComputeLoadBalancerResolver;
import io.klustr.schemas.integrations.compute.ComputeDnsZoneRecord;
import io.klustr.schemas.integrations.compute.ComputeDnsZoneRequest;
import io.klustr.schemas.integrations.compute.ComputeInstanceType;
import io.klustr.schemas.integrations.portainer.PortainerComputeReference;
import io.klustr.schemas.integrations.portainer.PortainerStackReference;
import io.klustr.utils.Json;
import io.klustr.utils.U;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;

import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PortainerPostgresSharedInstanceProvider {

    private final PortainerComputeResourceProvider provision;


    public PortainerPostgresSharedInstanceProvider(PortainerComputeResourceProvider provision) {
        this.provision = provision;
    }

    public static class Result {
        public String server;
        public String endpoint;
    }

    public Result provision(String database, String username, String password) {
        // String dbInitStep = U.getResourceAsString("templates/psql/step1.sql", this);

        String step2 = U.getResourceAsString("templates/psql/step2.sql", this);
        String step3 = U.getResourceAsString("templates/psql/step3.sql", this);
        String step4 = U.getResourceAsString("templates/psql/step4.sql", this);

        HashMap<String, String> envVariables = new HashMap<>();
        envVariables.put("username", username);
        envVariables.put("password", password);
        envVariables.put("database", database);
        StringSubstitutor substitutor = new StringSubstitutor(envVariables, "${", "}");

        List<PortainerComputeReference> servers = this.getServers();

        List<String> availableServers = servers.stream().map(x -> {
            if (!x.getLabels().containsKey("endpoint")) return null;
            return x.getLabels().get("endpoint");
        }).filter(StringUtils::isNotBlank).toList();


        String server = availableServers.get(0);
        // execSqlOnPostgres(substitutor.replace(dbInitStep), server, 5432, "admin", "admin", "postgres");
        createDatabaseIfNotExists(database, server, 5432, "admin", "admin");

        Lists.newArrayList(step2, step3, step4).forEach(step -> {
            execSqlOnPostgres(substitutor.replace(step), server, 5432, "admin", "admin", database);
        });

        Result r = new Result();
        r.endpoint =  "jdbc:postgresql://" + server + ":" + 5432 + "/" + database + "?sslmode=require";
        r.server =server;
        return r;
    }

    private void createDatabaseIfNotExists(String dbName, String host, int port, String adminUser, String adminPassword) {
        String url = "jdbc:postgresql://" + host + ":" + port + "/postgres?ssl=true&sslmode=require";

        try (Connection conn = DriverManager.getConnection(url, adminUser, adminPassword)) {

            // Step 1 — check existence
            String checkSql = "SELECT 1 FROM pg_database WHERE datname = ?";
            try (PreparedStatement ps = conn.prepareStatement(checkSql)) {
                ps.setString(1, dbName);
                try (ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {
                        // Database exists, nothing to do
                        return;
                    }
                }
            }

            // Step 2 — create the database (must be standalone)
            String createSql = "CREATE DATABASE \"" + dbName + "\"";
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(createSql);
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Failed to create database: " + ex.getMessage(), ex);
        }
    }

    private void execSqlOnPostgres(
            String sql,
            String host,
            int port,
            String adminUser,
            String adminPassword,
            String database
    ) {
        String url = "jdbc:postgresql://" + host + ":" + port + "/" + database
                + "?sslmode=require";

        System.out.println(sql);

        try (Connection conn = DriverManager.getConnection(url, adminUser, adminPassword)) {
            conn.setAutoCommit(true);
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
            } catch (SQLException ex) {
                throw new RuntimeException("PostgreSQL execution failed: " + ex.getMessage(), ex);
            }
        } catch (Exception ex) {
            throw new RuntimeException("PostgreSQL execution failed: " + ex.getMessage(), ex);
        }

    }

    private List<PortainerComputeReference> getServers() {
        List<PortainerStackReference> stacks = this.provision.listStacks().stream().filter(x -> {
            return x.getName().toLowerCase().startsWith("klustr-postgres-pool-");
        }).toList();

        List<PortainerComputeReference> available = Lists.newArrayList();

        stacks.forEach(stack -> {
            List<PortainerComputeReference> compute = this.provision.listContainersInStack(stack.getName());
            List<PortainerComputeReference> matches = compute.stream().filter(c -> {
                return c.getLabels().get("workload").equalsIgnoreCase("postgres");
            }).toList();
            available.addAll(matches);
        });

        return available;
    }


}