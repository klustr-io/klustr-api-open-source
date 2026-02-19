package io.klustr.console.admin;

import com.google.common.collect.Lists;
import io.klustr.schemas.console.identity.IdentityTraits;
import io.klustr.schemas.console.projects.Project;
import io.klustr.utils.U;
import okhttp3.*;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * Will invoke the camunda BPM verification process.
 */
@Component
public class CamundaBpmVerificationProvider implements VerificationProvider {

    private static final Logger log = LoggerFactory.getLogger(CamundaBpmVerificationProvider.class);

    private final OkHttpClient http = new OkHttpClient();

    private final String url;

    private final MediaType jsonType = MediaType.parse("application/json");

    public CamundaBpmVerificationProvider(@Value("${camunda.connectors.url}") String url) {
        this.url = url;
    }

    @Override
    public void verification(Project project, IdentityTraits requestor) {

        CamundaVerificationRequest req = new CamundaVerificationRequest();
        req.id = UUID.randomUUID().toString();
        req.project = project;
        req.timestamp = new DateTime();
        req.requestor = requestor;
        req.type = "app_verification";

        RequestBody payload = RequestBody.create(U.toJson(req), jsonType);
        Request request = new Request.Builder()
                .url(url + "/inbound/app_verification")
                .post(payload)
                .build();

        try (Response res = http.newCall(request).execute()) {
            String body = res.body() != null ? res.body().string() : null;
            if (res.isSuccessful()) {
                log.debug(body);
            } else {
                if (res.code() == 204) {
                    return;
                }
                log.debug(res.toString());
                throw new RuntimeException(res.toString());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void trust(Project project, IdentityTraits requestor) {
        CamundaVerificationRequest req = new CamundaVerificationRequest();
        req.id = UUID.randomUUID().toString();
        req.project = project;
        req.timestamp = new DateTime();
        req.requestor = requestor;
        req.type = "app_trust";

        RequestBody payload = RequestBody.create(U.toJson(req), jsonType);
        Request request = new Request.Builder()
                .url(url + "/inbound/app_trust")
                .post(payload)
                .build();

        try (Response res = http.newCall(request).execute()) {
            String body = res.body() != null ? res.body().string() : null;
            if (res.isSuccessful()) {
                log.debug(body);
            } else {
                if (res.code() == 204) {
                    return;
                }
                throw new RuntimeException("Failed to initiate request. " + res.body().string());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static class CamundaVerificationRequest {
        public String id;
        public DateTime timestamp;
        public Project project;
        public String type;
        public IdentityTraits requestor;
    }

    public static class CamundaVerificationResponse {
        public String id;
        public DateTime timestamp;
        public Project project;
        public IdentityTraits requestor;
        public String status;
        public List<String> rejection_reasons = Lists.newArrayList();
    }
}
