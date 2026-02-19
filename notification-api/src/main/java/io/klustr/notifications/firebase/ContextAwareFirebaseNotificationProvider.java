package io.klustr.notifications.firebase;

import io.klustr.notifications.Channel;
import io.klustr.notifications.NotificationStorage;
import io.klustr.notifications.SubscriptionChannels;
import io.klustr.storage.DocumentResult;
import io.klustr.storage.Pagination;
import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Component
public class ContextAwareFirebaseNotificationProvider implements NotificationProvider {
    private final NotificationStorage storage;

    private final FirebaseNotificationProvider firebase;

    public ContextAwareFirebaseNotificationProvider(NotificationStorage storage, io.klustr.notifications.firebase.FirebaseNotificationProvider firebase) {
        this.storage = storage;
        this.firebase = firebase;
    }

    @Override
    public void send(io.klustr.notifications.firebase.Notification notification) {
        DocumentResult<SubscriptionChannels> result = this.storage.subs().insecureQuery(Pagination.all());
        List<SubscriptionChannels> list = result.docs;
        List<SubscriptionChannels> destinations = list.stream()
                .filter(x -> x.user_id.equalsIgnoreCase(notification.userId))
                .toList();

        if (destinations.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }
        for (SubscriptionChannels channel :
                destinations) {
            Optional<Channel> gcs = channel.channels.stream().filter(x -> x.type.equalsIgnoreCase("gcs")).findFirst();
            if (gcs.isEmpty()) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND
                );
            }
            boolean expired = gcs.get().last_modified.isBefore(DateTime.now().minusHours(12));
            if (expired) {
                throw new ResponseStatusException(
                        HttpStatus.PRECONDITION_FAILED, "Token is expired, must have an active token"
                );
            }

            notification.token = gcs.get().token;
            notification.send = DateTime.now();

            this.firebase.send(notification);
        }
    }
}
