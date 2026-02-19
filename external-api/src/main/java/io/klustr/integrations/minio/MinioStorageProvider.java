package io.klustr.integrations.minio;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.klustr.Op;
import io.klustr.schemas.console.storage.StorageObject;
import io.klustr.schemas.integrations.minio.MinioOrganizationPolicy;
import io.klustr.schemas.integrations.minio.MinioOrganizationPolicyCondition;
import io.klustr.schemas.integrations.minio.MinioOrganizationPolicyStatement;
import io.klustr.schemas.integrations.minio.MinioPolicyStringEquals;
import io.klustr.utils.RandomNameGenerator;
import io.klustr.utils.U;
import io.minio.*;
import io.minio.admin.MinioAdminClient;
import io.minio.admin.QuotaUnit;
import io.minio.admin.messages.BucketUsageInfo;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

public class MinioStorageProvider extends AbstractMinioStorage implements StorageProvider {

    private final String bucketName;

    public MinioStorageProvider(String bucketName, String url, String accessKey, String secretKey) {
        super(url, accessKey, secretKey);
        this.bucketName = bucketName.toLowerCase().trim();
    }

    protected String getBucketName() {
        return bucketName;
    }

    @Override
    public void createServiceAccount(OAuth2AuthenticatedPrincipal principal) {
        try {
            this.getAdmin().addServiceAccount(RandomNameGenerator.randomHexString(20),
                    RandomNameGenerator.randomHexString(40),
                    principal.getName(),
                    Maps.newConcurrentMap(), principal.getName() + "-token",
                    "Organization default token",
                    DateTime.now().withYear(3000).toGregorianCalendar().toZonedDateTime());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void putObject(String objectName, String contentType, ByteArrayInputStream is) {
        super.putObject(objectName, contentType, is);
    }

    @Override
    public Optional<StorageInfo> getStorageInfo() {
        return super.getStorageInfo();
    }

    @Override
    public void updateQuotaInMb(long size) {
        try {
            MinioAdminClient admin = getAdmin();
            admin.setBucketQuota(getBucketName(), size, QuotaUnit.MB);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void grantPermission(OAuth2AuthenticatedPrincipal principal) {
        MinioAdminClient admin = getAdmin();

        try {
            String policyId = "oidc";
            MinioOrganizationPolicy defaultPolicy = null;
            String policyJson = admin.listCannedPolicies().get(policyId);
            if (StringUtils.isNotBlank(policyJson)) {
                defaultPolicy = U.fromJson(policyJson, MinioOrganizationPolicy.class);
            } else {
                defaultPolicy = new MinioOrganizationPolicy().withVersion("2012-10-17");
            }

            // do we already have this resource defined? if so add the user to the role.
            String resourceID = "arn:aws:s3:::" + getBucketName() + "*";
            MinioOrganizationPolicyStatement existingStatement = defaultPolicy.getStatement().stream().filter(statement -> {
                return statement.getResource().stream().anyMatch(res -> res.equalsIgnoreCase(resourceID));
            }).findFirst().orElse(null);

            if (existingStatement == null) {
                existingStatement = new MinioOrganizationPolicyStatement()
                        .withResource(Lists.newArrayList(resourceID))
                        .withAction(Lists.newArrayList("s3:*"))
                        .withEffect("Allow")
                        .withCondition(new MinioOrganizationPolicyCondition()
                                .withStringEquals(new MinioPolicyStringEquals()
                                        .withJwtSub(Lists.newArrayList())
                                )
                        );
                defaultPolicy.getStatement().add(existingStatement);
            }

            List<String> jwtSubs = existingStatement.getCondition().getStringEquals().getJwtSub();
            if (!jwtSubs.contains(principal.getName())) {
                jwtSubs.add(principal.getName());
                String json = U.toJson(defaultPolicy);
                admin.addCannedPolicy(policyId, json);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void provisionStorage() {

        try (MinioClient client = this.getClient()) {
            boolean found = client.bucketExists(BucketExistsArgs.builder().bucket(getBucketName()).build());
            if (!found) {
                // Make a new bucket called 'asiatrip'.
                client.makeBucket(MakeBucketArgs.builder().bucket(getBucketName()).build());
            }
            updateQuotaInMb(1);

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void deleteStorage() {
        try (MinioClient client = this.getClient()) {
            boolean found = client.bucketExists(BucketExistsArgs.builder().bucket(getBucketName()).build());
            if (!found) {
                return;
            }
            Iterable<Result<Item>> results = client.listObjects(ListObjectsArgs.builder().bucket(getBucketName()).build());
            for (Result<Item> result : results) {
                client.removeObject(RemoveObjectArgs.builder().bucket(getBucketName()).object(result.get().objectName()).build());
            }
            client.removeObjects(RemoveObjectsArgs.builder().bucket(getBucketName()).build());
            client.removeBucket(RemoveBucketArgs.builder().bucket(getBucketName()).build());
            getAdmin().removeCannedPolicy(getBucketName());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
