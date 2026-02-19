package io.klustr.integrations.minio;

import io.klustr.schemas.integrations.minio.MinioOrganizationPolicy;
import io.klustr.utils.U;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.SetBucketPolicyArgs;

public class MinioPublicStorageProvider extends MinioStorageProvider implements PublicStorageProvider {

    public MinioPublicStorageProvider(String bucketId, String url, String accessKey, String secretKey) {
        super(bucketId, url, accessKey, secretKey);
    }

    @Override
    public void provisionStorage() {
        super.provisionStorage();

        String policy = "{\"Version\":\"2012-10-17\",\"Statement\":[{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":\"*\"},\"Action\":\"s3:GetObject\",\"Resource\":\"arn:aws:s3:::" + getBucketName() + "/*\"}]}";
        try {
            this.getClient().setBucketPolicy(SetBucketPolicyArgs.builder().bucket(this.getBucketName()).config(policy).build());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String getPublicUrl() {
        return "https://minio.dev.klustr.io/" + getBucketName() + "/";
    }
}
