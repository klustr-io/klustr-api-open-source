package io.klustr.integrations.cdn;

import io.klustr.Op;
import io.klustr.utils.U;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.Base64;
import java.util.Optional;

public class CdnHost {

    public static class FileInfo {
        public String extension;
        public String filename;
        public String contentType;
        public byte[] bytes;
    }

    public static Optional<FileInfo> tryGetFileFromPossiblyBase64Url(String url) {
        // not encoded not need to change
        if (!(url.contains("data:") || url.contains("base64,"))) {
            return Optional.empty();
        }

        // data:image/png;base64,.....
        String[] mime = url.split(",");
        String imageType = mime[0].split(";")[0].replace("data:", "");
        String dataEncoded = mime[1];

        byte[] decodedBytes = Base64.getDecoder().decode(dataEncoded);
        String extension = imageType.split("/")[1];
        String filename = U.md5(dataEncoded) + "." + extension;

        FileInfo f = new FileInfo();
        f.contentType = imageType;
        f.extension = extension;
        f.filename = filename;
        f.bytes = decodedBytes;
        return Optional.of(f);
    }
}
