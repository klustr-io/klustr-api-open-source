package io.klustr.integrations.svix;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.svix.openapi.api.ApplicationApi;
import com.svix.openapi.api.EndpointApi;
import com.svix.openapi.api.MessageApi;
import com.svix.openapi.api.MessageAttemptApi;
import com.svix.openapi.client.ApiClient;
import com.svix.openapi.client.ApiException;
import com.svix.openapi.client.ServerConfiguration;
import com.svix.openapi.model.*;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.klustr.schemas.console.apps.WebhookEndpoint;
import io.klustr.schemas.console.webhooks.WebhookExternalId;
import io.klustr.schemas.console.webhooks.WebhookMessage;
import io.klustr.schemas.console.webhooks.WebhookMessageAudit;
import io.klustr.schemas.console.webhooks.WebhookProject;
import io.klustr.utils.Json;
import io.klustr.utils.U;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Component
@ConditionalOnProperty(name = "svix.url")
public class SvixWebhookProvider implements WebhookProvider {

    private static final Logger log = LoggerFactory.getLogger(SvixWebhookProvider.class);

    private final String url;
    private final String secret;

    public SvixWebhookProvider(@Value("${svix.url:http://127.0.0.1:8071}") String url,
                               @Value("${svix.secret}") String secret) {
        log.info("SVIX Url: " + url);
        this.url = url;
        this.secret = secret;
    }

    @Override
    public Page<WebhookMessageAudit> listMessageAttempts(WebhookExternalId projectRef, WebhookExternalId endpointRef, Pagination pagination) {
        try {
            MessageAttemptApi api = new MessageAttemptApi(this.getClient());
            SvixListResponseMessageAttemptOut docs = api.v1MessageAttemptListByEndpoint(
                    projectRef.getId(), endpointRef.getId(), pagination.limit, pagination.iterator,
                    null, null, null, null, null, false, null
            );
            List<WebhookMessageAudit> records = new ArrayList<>();
                records.addAll(docs.getData().stream().map(x -> {
                    WebhookMessageAudit.Status status = WebhookMessageAudit.Status.PENDING;
                    switch (x.getStatus()) {
                        case Fail -> status = WebhookMessageAudit.Status.FAIL;
                        case Sending -> status = WebhookMessageAudit.Status.SENDING;
                        case Success -> status = WebhookMessageAudit.Status.SUCCESS;
                    }
                    WebhookMessageAudit.TriggerType triggerType = WebhookMessageAudit.TriggerType.MANUAL;
                    switch (x.getTriggerType()) {
                        case Manual -> triggerType = WebhookMessageAudit.TriggerType.MANUAL;
                        case Scheduled -> triggerType = WebhookMessageAudit.TriggerType.SCHEDULED;
                    }
                    return new WebhookMessageAudit()
                            .withId(x.getId())
                            .withUrl( x.getUrl().toString())
                            .withStatus(status)
                            .withResponse(x.getResponse())
                            .withTimestamp(new DateTime(x.getTimestamp()))
                            .withMsgId(x.getMsgId())
                            .withResponseStatusCode(x.getResponseStatusCode())
                            .withTriggerType(triggerType);
                }).toList());

            Page<WebhookMessageAudit> p = new Page<>();
            p.docs = records;
            p.limit = pagination.limit;;
            p.iterator = docs.getIterator();
            p.done = docs.getDone();

            // null this out.
            if (p.done) {
                p.iterator = null;
            }
            return p;
        } catch (ApiException e) {
            e.printStackTrace();
            return new Page<WebhookMessageAudit>();
            // throw new RuntimeException(e);
        }
    }

    @Override
    public WebhookProject registerProject(WebhookProject project) {
        ApplicationApi apps = new ApplicationApi(getClient());
        try {
            SvixApplicationOut out = apps.v1ApplicationCreate(new SvixApplicationIn()
                    .name(project.getName())
                    .uid(project.getId())
                    .metadata(project.getMetadata()), true, UUID.randomUUID().toString()
            );
            project.setExternalId(new WebhookExternalId()
                    .withId(out.getId())
            );
            return project;
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    private Optional<SvixEndpointOut> findEndpoint(WebhookExternalId projectRef, WebhookExternalId endpointRef) {
        try {
            EndpointApi e = new EndpointApi(this.getClient());
            SvixEndpointOut endpointOut = e.v1EndpointGet(projectRef.getId(), endpointRef.getId());
            return Optional.of(endpointOut);
        } catch (ApiException e) {
            return Optional.empty();
        }
    }

    public void health() {
        ApplicationApi apps = new ApplicationApi(getClient());
        try {
            apps.v1ApplicationList(10, null, SvixOrdering.ASCENDING);
        } catch (Exception ex) {
            throw new RuntimeException("Could not use svix at " + url + " with token " + secret + ".",ex);
        }
    }

    @Override
    public void deleteProject(WebhookExternalId projectRef) {
        Optional<WebhookProject> exists = this.getProject(projectRef);
        if (exists.isEmpty()) return;

        ApplicationApi apps = new ApplicationApi(getClient());
        try {
            apps.v1ApplicationDelete(projectRef.getId());
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<WebhookExternalId> getProjectExternalId(String projectId) {
        try {
            ApplicationApi api = new ApplicationApi(this.getClient());
            String iterator = null;
            SvixListResponseApplicationOut docs = api.v1ApplicationList(128, iterator, SvixOrdering.ASCENDING);
            boolean hasNext = !docs.getData().isEmpty();
            while (hasNext) {
                Optional<SvixApplicationOut> match = docs.getData().stream().filter(x -> {
                    assert x.getUid() != null;
                    return x.getUid().equalsIgnoreCase(projectId);
                }).findFirst();
                if (match.isPresent()) {
                    return Optional.of(new WebhookExternalId()
                            .withId(match.get().getId()));
                }
                iterator = docs.getIterator();
                docs = api.v1ApplicationList(128, iterator, SvixOrdering.ASCENDING);
                hasNext = !docs.getData().isEmpty();
            }
            return Optional.empty();
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<WebhookProject> getProject(WebhookExternalId projectRef) {
        try {
            ApplicationApi apps = new ApplicationApi(getClient());
            SvixApplicationOut svixApplicationOut = apps.v1ApplicationGet(projectRef.getId());
            WebhookProject app = new WebhookProject()
                    .withId(svixApplicationOut.getId())
                    .withUid(svixApplicationOut.getUid())
                    .withName(svixApplicationOut.getName())
                    .withMetadata(svixApplicationOut.getMetadata())
                    .withDateCreated(new DateTime(svixApplicationOut.getCreatedAt()));
            return Optional.of(app);
        } catch (ApiException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<WebhookEndpoint> getEndpoint(WebhookExternalId projectRef, WebhookExternalId endpointRef) {
        Optional<SvixEndpointOut> x = findEndpoint(projectRef, endpointRef);
        if (x.isEmpty()) return Optional.empty();

        SvixEndpointOut result = x.get();
        WebhookEndpoint endpoint = new WebhookEndpoint()
                .withId(result.getId())
                .withUid(result.getUid())
                .withUrl(result.getUrl())
                .withFilterTypes(result.getFilterTypes() == null ? null : result.getFilterTypes())
                .withMetadata(result.getMetadata())
                .withDisabled(result.getDisabled())
                .withDescription(result.getDescription())
                .withDateCreated(new DateTime(result.getCreatedAt()))
                .withExternalId(new WebhookExternalId().withId(result.getId()));
        return Optional.of(endpoint);
    }

    public List<WebhookEndpoint> createEndpoints(WebhookExternalId projectRef, List<WebhookEndpoint> endpoints) {
        try {
            EndpointApi api = new EndpointApi(this.getClient());
            String iterator = null;
            SvixListResponseEndpointOut res = api.v1EndpointList(projectRef.getId(), 128, iterator, SvixOrdering.ASCENDING);
            boolean hasNext = !res.getData().isEmpty();

            List<String> existing = Lists.newArrayList();
            while (hasNext) {
                existing.addAll( res.getData().stream().map(SvixEndpointOut::getId).collect(Collectors.toList()));
                iterator = res.getIterator();
                res = api.v1EndpointList(projectRef.getId(), 128, iterator, SvixOrdering.ASCENDING);
                hasNext = !res.getData().isEmpty();
            }

            List<String> requested = endpoints.stream()
                    .filter(x -> x.getExternalId() != null && x.getExternalId().getId() != null)
                    .map(w -> w.getExternalId().getId()).toList();;

            // remove all previous version
            existing.removeAll(requested);

            existing.forEach(existingId -> {
                try {
                    api.v1EndpointDelete(projectRef.getId(), existingId);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });

            List<WebhookEndpoint> results = Lists.newArrayList();
            endpoints.forEach(m -> {
                results.add( createEndpoint(projectRef, m));
            });
            return results;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public WebhookEndpoint createEndpoint(WebhookExternalId projectRef, WebhookEndpoint endpoint) {
        EndpointApi api = new EndpointApi(this.getClient());
        try {
            Optional<WebhookEndpoint> match = endpoint.getExternalId() != null && endpoint.getExternalId().getId() != null ?
                    this.getEndpoint(projectRef, endpoint.getExternalId()) : Optional.empty();

            Set<String> filterTypes = endpoint.getFilterTypes() != null && !endpoint.getFilterTypes().isEmpty() ? endpoint.getFilterTypes() : null;
            Set<String> channels = endpoint.getChannels() != null && !endpoint.getChannels().isEmpty() ? endpoint.getChannels() : null;
            if (match.isEmpty()) {
                SvixEndpointOut res = api.v1EndpointCreate(projectRef.getId(), new SvixEndpointIn()
                                .url(endpoint.getUrl())
                                .description(endpoint.getDescription())
                                .uid(endpoint.getUid())
                                .channels(channels)
                                .filterTypes(filterTypes)
                                .metadata(endpoint.getMetadata())
                                .secret(endpoint.getSecret())
                                .rateLimit(endpoint.getRateLimit())
                                .disabled(endpoint.getDisabled()),
                        UUID.randomUUID().toString()
                );
                return endpoint.withExternalId(new WebhookExternalId().withId(res.getId()));
            } else {
                SvixEndpointOut res = api.v1EndpointUpdate(projectRef.getId(), match.get().getUid(), new SvixEndpointUpdate()
                        .uid(endpoint.getUid())
                        .disabled(endpoint.getDisabled())
                        .channels(channels)
                        .filterTypes(filterTypes)
                        .metadata(endpoint.getMetadata())
                        .description(endpoint.getDescription())
                        .rateLimit(endpoint.getRateLimit())
                        .url(endpoint.getUrl())
                );
                return endpoint.withExternalId(new WebhookExternalId().withId(res.getId()));
            }
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createMessage(WebhookExternalId projectRef, WebhookMessage message) {
        MessageApi api = new MessageApi(this.getClient());
        try {
            JsonObject jsonObject = Json.toJsonObject(U.toJson(message.getPayload()));
            api.v1MessageCreate(projectRef.getId(),
                    new SvixMessageIn()
                        .eventId(message.getEventId())
                        .eventType(message.getEventType())
                            .channels(message.getChannels() != null && message.getChannels().isEmpty() == false ? message.getChannels() : null)
                            .payload(jsonObject), false, message.getEventId());
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    private ApiClient getClient() {
        String token = jwt(secret,
                "org_23rb8YdGqMT0qIzpgGwdXfHirMu",
                "svix-server",
                1000 * 60 * 10);

        ApiClient c = new ApiClient().setBasePath(url).setServers(
                Lists.newArrayList(
                        new ServerConfiguration(url, "Server", new HashMap<>())
                )
        ).setDebugging(false);
        c.setBearerToken(token);
        return c;
    }

    public static String jwt(String secret, String sub, String iss, Integer ttlMs) {
        byte[] bits = secret.getBytes(StandardCharsets.UTF_8);
        SecretKey key = Keys.hmacShaKeyFor(bits);
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(now)
                .setSubject(sub)
                .setIssuer(iss)
                .setNotBefore(now)
                .signWith(SignatureAlgorithm.HS256,bits);

        //if it has been specified, let's add the expiration
        if (ttlMs != null && ttlMs >= 0) {
            long expMillis = nowMillis + ttlMs;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }
}
