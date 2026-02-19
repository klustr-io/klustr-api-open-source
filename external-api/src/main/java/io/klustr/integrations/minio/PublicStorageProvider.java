package io.klustr.integrations.minio;

public interface PublicStorageProvider extends StorageProvider {

    String getPublicUrl();


}
