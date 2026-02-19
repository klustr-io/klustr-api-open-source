package io.klustr.integrations.mailtrap;

import io.klustr.schemas.integrations.mailtrap.EmailPayload;
import io.klustr.spring.OAuthCredentialType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Component
@RequestMapping("/notifications")
@Tag(name = "Notifications APIs", description = "APIs for notifications")
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    private final MailTrapApi emailApi;

    public EmailService(MailTrapApi emailApi) {
        this.emailApi = emailApi;
    }

    @PostMapping("/email")
    @Operation(
summary = "Send email notifications to users.",
            operationId = "sendNotificationEmail",
            description = """
This endpoint facilitates the sending of email notifications to users. It is intended for internal use, ensuring that the provided email payload is processed accurately. Use this API to enhance communication through email within the notification system.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('emails.create')")
    public void sendEmail(@RequestBody EmailPayload payload) {
        // live.smtp.mailtrap.io
        // 587 (recommended), 465, 2525 or 25
        // uid: api smtp@mailtrap.io
        this.emailApi.send(payload);
    }

}
