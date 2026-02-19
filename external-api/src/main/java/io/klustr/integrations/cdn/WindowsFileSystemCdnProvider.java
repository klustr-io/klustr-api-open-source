package io.klustr.integrations.cdn;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@ConditionalOnProperty(name = "cdn.windows.path")
public class WindowsFileSystemCdnProvider implements CdnProvider {

    private final String windowsRootFolder;

    public WindowsFileSystemCdnProvider(@Value("${cdn.windows.path}")String windowsRootFolder) {
        // "E:\\http\\wwwroot\\klustr\\"
        this.windowsRootFolder = windowsRootFolder;
    }

    public CdnUploadResponse upload(String path, String filename, String contentType, byte[] bits) {
        try {
            path = path.replace("/", "\\");
            path = StringUtils.stripStart(StringUtils.stripEnd(path, "\\"), "\\");
            FileUtils.writeByteArrayToFile(new File(this.windowsRootFolder + "\\" + path + "\\" + filename), bits);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        CdnUploadResponse r = new CdnUploadResponse();
        r.url = "https://cdn.dev.klustr.io/klustr/" + StringUtils.stripEnd(path.replace("\\", "/"),"/") + "/" + filename;
        return r;
    }
}
