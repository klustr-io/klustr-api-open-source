package io.klustr.integrations.cdn;

public interface CdnProvider {

    CdnUploadResponse upload(String path, String filename, String contentType, byte[] bits);
}
