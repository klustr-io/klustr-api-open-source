package io.klustr.integrations.minio;

import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

import java.io.ByteArrayInputStream;
import java.util.Optional;

public interface StorageProvider {

    void provisionStorage();

    void deleteStorage();

    void updateQuotaInMb( long size);

    Optional<StorageInfo> getStorageInfo();

    void grantPermission(OAuth2AuthenticatedPrincipal principal);

    void createServiceAccount(OAuth2AuthenticatedPrincipal principal);

    void putObject(String path, String contentType, ByteArrayInputStream is);
}
