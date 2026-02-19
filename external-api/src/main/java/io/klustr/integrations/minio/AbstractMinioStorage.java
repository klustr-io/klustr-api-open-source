package io.klustr.integrations.minio;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
import io.minio.messages.Item;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

public abstract class AbstractMinioStorage {
    private final String accessKey;
    private final String secretKey;
    private final String minioUrl;


    public AbstractMinioStorage(String minioUrl, String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.minioUrl = minioUrl;
    }

    protected abstract String getBucketName();


    protected void putObject(String objectName, String contentType, ByteArrayInputStream is) {
        try (MinioClient client = this.getClient()) {
            client.putObject(PutObjectArgs.builder()
                    .bucket(getBucketName())
                    .object(objectName)
                    .contentType(contentType)
                    .stream(is, is.available(), -1)
                    .build()
            );
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    protected void removeVersion(String objectName, String version) {
        try (MinioClient client = this.getClient()) {
            client.removeObject(RemoveObjectArgs
                    .builder()
                    .bucket(getBucketName())
                    .object(objectName)
                    .versionId(version)
                    .build());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    protected Optional<StorageInfo> getStorageInfo() {
        try (MinioClient client = this.getClient()) {
            boolean b = getClient().bucketExists(BucketExistsArgs.builder().bucket(this.getBucketName()).build());
            if (!b) {
                return Optional.empty();
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        try {
            MinioAdminClient admin = getAdmin();
            long bucketQuota = admin.getBucketQuota(this.getBucketName());
            BucketUsageInfo bucketUsageInfo = admin.getDataUsageInfo().bucketsUsageInfo().get(getBucketName());
            if (bucketUsageInfo == null) {
                return Optional.empty();
            }
            StorageInfo storageInfo = new StorageInfo();
            storageInfo.usage = bucketUsageInfo.size();
            storageInfo.count = bucketUsageInfo.objectsCount();
            storageInfo.quota = bucketQuota;
            return Optional.of(storageInfo);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


    protected MinioClient getClient() {
        MinioClient mc =
                MinioClient.builder()
                        .endpoint(this.minioUrl)
                        .credentials(this.accessKey, this.secretKey)
                        .build();
        return mc;
    }

    protected MinioAdminClient getAdmin() {
        MinioAdminClient admin = MinioAdminClient.builder()
                .endpoint(this.minioUrl)
                .credentials(this.accessKey, this.secretKey)
                .build();
        return admin;
    }

    protected void provisionStorage() {
        try (MinioClient client = this.getClient()) {
            boolean found = client.bucketExists(BucketExistsArgs.builder().bucket(getBucketName()).build());
            if (!found) {
                // Make a new bucket called 'asiatrip'.
                client.makeBucket(MakeBucketArgs.builder().bucket(getBucketName()).build());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    protected byte[] getObjectBytes(String filename) {
        try (MinioClient client = this.getClient()) {
            GetObjectResponse object = client.getObject(GetObjectArgs.builder().object(filename).bucket(getBucketName()).build());
            return object.readAllBytes();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    protected byte[] getObjectBytes(String filename, String version) {
        try (MinioClient client = this.getClient()) {
            GetObjectResponse object = client.getObject(GetObjectArgs.builder().object(filename).bucket(getBucketName()).versionId(version).build());
            return object.readAllBytes();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    protected List<StorageObject> getObjectInfo(String filename) {
        try (MinioClient client = this.getClient()) {
            List<StorageObject> yield = Lists.newArrayList();

            ListObjectsArgs arg = ListObjectsArgs.builder()
                    .bucket(getBucketName())
                    .includeVersions(true)
                    .prefix(filename)
                    .build();

            Iterable<Result<Item>> results = client.listObjects(arg);

            for (Result<Item> result : results) {
                Item item = result.get();
                yield.add( new StorageObject()
                        .withObjectName(item.objectName())
                        .withVersionId(item.versionId())
                        .withIsLatest(item.isLatest())
                        .withEtag(item.etag())
                        .withSize(item.size())
                        .withLastModified(new DateTime(item.lastModified().toEpochSecond())));

            }

            return yield;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    protected void deleteStorage() {
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
