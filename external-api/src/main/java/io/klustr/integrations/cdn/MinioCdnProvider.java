package io.klustr.integrations.cdn;

import io.klustr.integrations.minio.MinioPublicStorageProvider;
import io.klustr.integrations.minio.MinioStorageProvider;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Component
public class MinioCdnProvider implements CdnProvider {

    private final MinioPublicStorageProvider storage;

    public MinioCdnProvider(@Value("${cdn.s3.bucketId}") String bucketId,
                            @Value("${cdn.s3.url}") String url,
                            @Value("${cdn.s3.accessKey}") String accessKey,
                            @Value("${cdn.s3.secretKey}") String secretKey) {
        this.storage = new MinioPublicStorageProvider(bucketId, url, accessKey, secretKey);
    }

    public CdnUploadResponse upload(String path, String filename, String contentType, byte[] bits) {

        // no ending "/"
        String sanitizedPath = StringUtils.removeEnd(path, "/");
        this.storage.putObject(sanitizedPath + "/" + filename, contentType, new ByteArrayInputStream(bits));

        // no ending "/"
        String sanitizedUrl = StringUtils.removeEnd(this.storage.getPublicUrl(), "/");
        String url = sanitizedUrl + "/" + path + "/" + filename;

        CdnUploadResponse r = new CdnUploadResponse();
        r.url = url;
        return r;
    }
}
