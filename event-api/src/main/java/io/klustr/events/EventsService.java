package io.klustr.events;

import io.klustr.schemas.events.ApplicationEvent;
import io.klustr.spring.OAuthCredentialType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Records that a user saw a specific varient or experiment.
 */
@RestController
@Component
@RequestMapping("/events/me")
@Tag(name = "Event APIs", description = "APIs for tracking and analytics")
public class EventsService {

    private static final Logger log = LoggerFactory.getLogger(EventsService.class);

    private final EventStorage storage;

    public EventsService(EventStorage storage) {
        this.storage = storage;
    }

    /**
     * Records an application event for reporting and insights.
     *
     * @param event The event to track
     */
    @PostMapping("/app")
    @Operation(
description = """
This endpoint records an application-level event that a user has experienced. It captures essential analytics data to track user interactions with the app. This information is vital for enhancing user experience and improving engagement through targeted notifications.
""",
            operationId = "recordUserApplicationEvent",
            summary = "Log an application event for the current user.",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public void app(@RequestBody ApplicationEvent event,
                    @AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {
        // replace event id
        if (!StringUtils.isNotBlank(event.getEventId())) {
            event.setEventId(UUID.randomUUID().toString());
        }
        if (event.getReceivedAt() == null) {
            event.setReceivedAt(DateTime.now());
        }
        event.setUserId(user.getName());

        // write to rethinkdb (testing)
        this.storage.events().insertObject(event.getEventId(), event);
    }

}
