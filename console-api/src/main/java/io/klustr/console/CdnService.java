package io.klustr.console;

import io.klustr.integrations.cdn.CdnProvider;
import io.klustr.integrations.cdn.CdnUploadResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.klustr.integrations.cdn.CdnHost;
import io.klustr.spring.OAuthCredentialType;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@Component
@RequestMapping("/cdn")
@Tag(name = "CDN Service",
        description = "Enables saving images and assets into a CDN resource server. ")
public class CdnService {

    private final CdnProvider cdn;

    public CdnService(CdnProvider cdn) {
        this.cdn = cdn;
    }

    @PostMapping("/image")
    @Operation(
summary = "Upload a public Base64 encoded image",
            operationId = "uploadPublicBase64Image",
            description = """
This endpoint allows users to upload a Base64 encoded image to the CDN. The image will be stored publicly, ensuring it is accessible via a returned URL. Please ensure that the image meets the required specifications for a successful upload.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public ImageResponse image(@RequestBody ImageRequest request,
                               @AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {
        ImageResponse r = new ImageResponse();
        Optional<CdnHost.FileInfo> fileInfo = CdnHost.tryGetFileFromPossiblyBase64Url(request.uri);
        if (fileInfo.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The image URI is not a base64 encoded image.");
        }
        CdnUploadResponse upload = this.cdn.upload("/", fileInfo.get().filename, fileInfo.get().contentType, fileInfo.get().bytes);
        r.uri = upload.url;
        return r;
    }

    public static class ImageRequest {
        public String uri;
    }

    public static class ImageResponse {
        public String uri;
    }
}
