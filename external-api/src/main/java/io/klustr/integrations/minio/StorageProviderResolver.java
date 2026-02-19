package io.klustr.integrations.minio;

import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

public interface StorageProviderResolver {

    StorageProvider getStorageProvider(String projectId, String bucketName, OAuth2AuthenticatedPrincipal principal);

    PublicStorageProvider getPublicStorageProvider(String projectId, String bucketName, OAuth2AuthenticatedPrincipal principal);
}
