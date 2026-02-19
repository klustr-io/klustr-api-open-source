package io.klustr.compute.dns.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.klustr.compute.dns.ComputeDnsProvider;
import io.klustr.schemas.integrations.compute.ComputeDnsZone;
import io.klustr.schemas.integrations.compute.ComputeDnsZoneRequest;
import io.klustr.schemas.integrations.pdns.PdnsZone;
import io.klustr.schemas.integrations.pdns.PdnsZoneCreateRequest;
import io.klustr.utils.Json;
import io.klustr.utils.U;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PowerDnsApiComputeDnsProvider implements ComputeDnsProvider {

    private final String api;
    private final String apiKey;

    private final OkHttpClient http = new OkHttpClient();
    private static final MediaType mediaType = MediaType.parse("application/json");

    public PowerDnsApiComputeDnsProvider(@Value("${dns.pdns.url:http://192.168.0.210:7000}") String api,
                                         @Value("${dns.pdns.api_key:mykey}") String apiKey) {
        // "http://192.168.0.210:7000/api/v1/servers/localhost/zones/dev.klustr.io"
        // 100.124.171.82 --> ip route
        this.api = api;
        this.apiKey = apiKey;
    }

    @Override
    public Health health() {
        Request req = new Request.Builder()
                .url(this.api + "/metrics")
                .header("X-API-Key", this.apiKey)
                .get()
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                return Health.down(new RuntimeException("Could not get successful response")).withDetail("url", this.api + "/metrics").build();
            }
            return Health.up().build();
        } catch (Exception ex) {
            return Health.down(ex).withDetail("url", this.api + "/metrics").build();
        }
    }

    @Override
    public List<ComputeDnsZone> listZones() {
        Request req = new Request.Builder()
                .url(this.api + "/api/v1/servers/localhost/zones")
                .header("X-API-Key", this.apiKey)
                .get()
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.body().string());
            }
            String json = res.body().string();
            return Json.parseArray(json, ComputeDnsZone.class);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public ComputeDnsZone createZone(String zone) {

        PdnsZoneCreateRequest body = new PdnsZoneCreateRequest()
                .withKind("Master")
                .withName(zone);

        Request req = new Request.Builder()
                .url(this.api + "/api/v1/servers/localhost/zones")
                .header("X-API-Key", this.apiKey)
                .post(RequestBody.create(Json.toJson(body), mediaType))
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.body().string());
            }
            String json = res.body().string();
            return Json.parse(json, ComputeDnsZone.class);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void deleteZone(String zone) {
        Request req = new Request.Builder()
                .url(this.api + "/api/v1/servers/localhost/zones/" + zone)
                .header("X-API-Key", this.apiKey)
                .delete()
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.body().string());
            }
            String json = res.body().string();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public PdnsZone listRecordsInZone(String zone) {

        Request req = new Request.Builder()
                .url(this.api + "/api/v1/servers/localhost/zones/" + zone)
                .header("X-API-Key", this.apiKey)
                .get()
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.body().string());
            }
            String json = res.body().string();
            return Json.parse(json, PdnsZone.class);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void deleteRecords(String zone, List<ComputeDnsZoneRequest> records) {

        // ensure we have them all set to replace for PDNS
        records.forEach(x -> x.withChangetype("DELETE"));

        ObjectNode body = (ObjectNode)Json.toJsonNode("{}");
        body.put("rrsets", Json.toJsonNode(Json.toJson(records)));

        String json = Json.toJson(body);

        Request req = new Request.Builder()
                .url(this.api + "/api/v1/servers/localhost/zones/" + zone)
                .header("X-API-Key", this.apiKey)
                .patch(RequestBody.create(json, mediaType))
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.body().string());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void createRecords(String zone, List<ComputeDnsZoneRequest> records) {

        // ensure we have them all set to replace for PDNS
        records.forEach(x -> x.withChangetype("REPLACE"));

        ObjectNode body = (ObjectNode)Json.toJsonNode("{}");
        body.put("rrsets", Json.toJsonNode(Json.toJson(records)));

        String json = Json.toJson(body);

        Request req = new Request.Builder()
                .url(this.api + "/api/v1/servers/localhost/zones/" + zone)
                .header("X-API-Key", this.apiKey)
                .patch(RequestBody.create(json, mediaType))
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.body().string());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
