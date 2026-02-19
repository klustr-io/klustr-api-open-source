package io.klustr.integrations.minio;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConditionalOnProperty(name = "minio.url")
public class DefaultStorageProviderResolver implements StorageProviderResolver {

    private Map<String, StorageProvider> private_buckets = Maps.newConcurrentMap();
    private Map<String, PublicStorageProvider> public_buckets = Maps.newConcurrentMap();

    private final String minioUrl;
    private final String minioAccessKey;
    private final String minioSecretKey;

    public DefaultStorageProviderResolver(@Value("${minio.url}")String minioUrl,
                                          @Value("${minio.accessKey}")String minioAccessKey,
                                          @Value("${minio.secretKey}")String minioSecretKey) {
        this.minioUrl = minioUrl;
        this.minioSecretKey = minioSecretKey;
        this.minioAccessKey = minioAccessKey;
    }

    @Override
    public StorageProvider getStorageProvider(String projectId, String bucketId, OAuth2AuthenticatedPrincipal principal) {
        StorageProvider storageProvider = private_buckets.get(bucketId);
        if (storageProvider != null) return storageProvider;

        storageProvider = new MinioStorageProvider(bucketId, this.minioUrl, this.minioAccessKey, this.minioSecretKey);
        private_buckets.put(bucketId, storageProvider);
        return storageProvider;
    }

    @Override
    public PublicStorageProvider getPublicStorageProvider(String projectId, String bucketId, OAuth2AuthenticatedPrincipal principal) {
        PublicStorageProvider storageProvider = public_buckets.get(bucketId);
        if (storageProvider != null) return storageProvider;

        storageProvider = new MinioPublicStorageProvider(bucketId, this.minioUrl, this.minioAccessKey, this.minioSecretKey);
        public_buckets.put(bucketId, storageProvider);
        return storageProvider;
    }
}
