package io.klustr.events;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import io.klustr.permissions.PrincipleUtils;
import io.klustr.schemas.events.LocationEvent;
import io.klustr.schemas.events.LocationPayloadEvent;
import io.klustr.schemas.events.UserLocationHistory;
import io.klustr.spring.OAuthCredentialType;
import io.klustr.storage.DocumentResult;
import io.klustr.storage.Pagination;
import io.klustr.storage.query.Db;
import io.klustr.storage.query.DbQueryAndCondition;
import io.klustr.storage.query.DbSort;
import io.klustr.utils.Json;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/**
 * Maintains a method of accessing and securing access to user location information.
 */
@RestController
@Component
@RequestMapping("/events/me/locations")
@Tag(name = "Event APIs", description = "APIs for tracking and analytics")
public class EventLocationService {

    private static final Logger log = LoggerFactory.getLogger(EventLocationService.class);
    private static final Comparator<LocationEvent> dateSort = new Comparator<LocationEvent>() {
        public int compare(LocationEvent a, LocationEvent b) {
            return a.getTimestamp().compareTo(b.getTimestamp());
        }
    };
    private final Cache<String, DateTime> userLastWrite;
    private final io.klustr.events.EventStorage storage;

    public EventLocationService(io.klustr.events.EventStorage storage) {

        this.storage = storage;

        this.userLastWrite = CacheBuilder.newBuilder()
                .expireAfterWrite(Duration.ofSeconds(500))
                .initialCapacity(1000)
                .maximumSize(64000) // TODO watch for this as we could easily see max number of users (500 residents)
                .recordStats()
                .build();
    }

    private static UserLocationHistory compact(UserLocationHistory history, LocationEvent e) {

        // condense recent to most recent 50
        if (history.getRecent().size() >= 100) {
            List<LocationEvent> copyOfLast20 = Lists.newArrayList(history.getRecent().stream().sorted(dateSort).limit(20).toList());
            history.setRecent(copyOfLast20);
        }

        // push at the start to maintain ordering.
        history.getRecent().add(0, e);


        return history;
    }

    private static DateTime hour(DateTime dt) {
        return dt.withSecondOfMinute(0).withMinuteOfHour(0).withMillis(0);
    }

    private static DateTime day(DateTime dt) {
        return dt.withHourOfDay(0).withSecondOfMinute(0).withMinuteOfHour(0).withMillis(0);
    }

    @GetMapping
    @Operation(
description = """
Fetches the user's location events, sorted from the most recent to the oldest. This endpoint ensures that users can securely access their own location data, promoting privacy while enabling tracking and analytics related to their movements.
""",
            operationId = "getMyLocationEvents",
            summary = "Retrieve my location events in chronological order.",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('SCOPE_location.places.read')")
    public DocumentResult<LocationEvent> getLocations(
            @RequestParam(value = "start", defaultValue = "0") Integer start,
            @RequestParam(value = "limit", defaultValue = "1024") Integer limit,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {
        DbQueryAndCondition fq = Db.and(
                Db.query("org_id").eq(PrincipleUtils.tryGetOrgId(user)),
                Db.query("client_id").eq(PrincipleUtils.tryGetClientId(user))
        );
        Pagination pagination = new Pagination().withLimit(limit).withStart(start);

        // remove the identity from the response to avoid leaking?

        DocumentResult<LocationEvent> result = this.storage.user_locations_events().insecureQuery(fq, DbSort.dsc("timestamp"),
                pagination);
        return  result;
    }

    @DeleteMapping
    @Operation(
description = """
This endpoint allows the authenticated user to delete their own location event records. It ensures that only the user can manage their location data, thereby maintaining privacy and security. Use this operation to remove any unwanted location history associated with your account.
""",
            operationId = "deleteMyLocationEvents",
            summary = "Delete my location event records.",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('SCOPE_location.places.write')")
    public void deleteLocations(
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {
        DbQueryAndCondition fq = Db.and(
                Db.query("org_id").eq(PrincipleUtils.tryGetOrgId(user)),
                Db.query("client_id").eq(PrincipleUtils.tryGetClientId(user))
        );
        Pagination pagination = Pagination.all();
        DocumentResult<LocationEvent> result = this.storage.user_locations_events().insecureQuery(fq, pagination);
        result.docs.forEach(x -> {
            this.storage.user_locations_events().deleteObject(x.getId());
        });
    }

    /**
     * Records an application event for reporting and insights.
     *
     * @param payload The event to track
     */
    @PostMapping
    @Operation(
description = """
Records a location event for the authenticated user. This API securely logs user location information for tracking and analytics. It ensures that only authorized users can access and submit their location data, maintaining privacy and security.
""",
            operationId = "recordMyLocationEvent",
            summary = "Record my location event for tracking.",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('SCOPE_location.places.write')")
    public void recordEvent(
            @RequestBody LocationPayloadEvent payload,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {
        if (payload.getReceivedAt() == null) {
            payload.setReceivedAt(DateTime.now());
        }

        LocationEvent e = Json.parse(Json.toJson(payload), LocationEvent.class);
        // ensure we log under the right user context
        e.setUserId(user.getName());
        e.setId(UUID.randomUUID().toString());
        e.setClientId(PrincipleUtils.tryGetClientId(user));
        e.setOrgId(PrincipleUtils.tryGetOrgId(user));
        e.setProjectId(PrincipleUtils.tryGetProjectId(user));
        e.setTimestamp(DateTime.now());


        // write to rethinkdb (testing)
        this.storage.user_locations_events().insertObject(e.getId(), e);

        updateHistory(user.getName(), e);
    }

    void updateHistory(String userId, LocationEvent event) {
        UserLocationHistory match = this.storage.user_locations_history().getObject(userId);
        boolean exists = match != null;
        if (!exists) {
            match = new UserLocationHistory()
                    .withId(event.getUserId());
        }

        match = compact(match, event);

        if (exists) {
            this.storage.user_locations_history().updateObject(userId, match);
        } else {
            this.storage.user_locations_history().insertObject(userId, match);
        }
    }
}
