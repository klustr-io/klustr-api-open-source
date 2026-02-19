package io.klustr.consent.cms;

import io.klustr.integrations.minio.AbstractMinioStorage;
import io.klustr.integrations.minio.MinioStorageProvider;
import io.klustr.schemas.console.storage.StorageObject;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Component
public class MinioAgreementStorageProvider extends AbstractMinioStorage implements AgreementStorageProvider {

    public MinioAgreementStorageProvider(@Value("${minio.url}")String minioUrl,
                                         @Value("${minio.accessKey}")String minioAccessKey,
                                         @Value("${minio.secretKey}")String minioSecretKey) {
        super(minioUrl, minioAccessKey, minioSecretKey);
    }

    @Override
    protected String getBucketName() {
        return "agreements";
    }

    @Override
    public StorageObject putAgreement(String agreementId, String locale, String mimeType, byte[] bits) {
        String key = agreementId + "." + locale;
        super.putObject(key, mimeType, new ByteArrayInputStream(bits));
        return this.getLatest(agreementId, locale);
    }

    @Override
    public void removeVersion(String agreementId, String locale, String version) {
        String key = agreementId + "." + locale;
        super.removeVersion(key, version);
    }

    @Override
    public List<StorageObject> listVersions(String agreementId, String locale) {
        String key = agreementId + "." + locale;
        return super.getObjectInfo(key);
    }

    @Override
    public StorageObject getVersion(String agreementId, String locale, String version) {
        String key = agreementId + "." + locale;
        List<StorageObject> versions = super.getObjectInfo(key);
        Optional<StorageObject> match = versions.stream().filter(x -> x.getVersionId().equalsIgnoreCase(version)).findFirst();
        return match.orElse(null);
    }

    @Override
    public StorageObject getLatest(String agreementId, String locale) {
        String key = agreementId + "." + locale;
        List<StorageObject> items = super.getObjectInfo(key);
        Optional<StorageObject> first = items.stream().filter(StorageObject::getIsLatest).findFirst();
        return first.orElse(null);
    }

    @Override
    public String getMarkdown(String agreementId, String locale, String version) {
        String key = agreementId + "." + locale;
        byte[] objectBytes = super.getObjectBytes(key, version);
        return new String(objectBytes);
    }
}
