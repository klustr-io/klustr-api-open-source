package io.klustr.notifications.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import io.klustr.utils.U;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FirebaseNotificationProvider implements NotificationProvider {

    public FirebaseNotificationProvider(@Value("${firebase.credentials.resource:firebase.json}") String resource,
                                        @Value("${firebase.credentials.projectId:klustr-io}") String projectId,
                                        @Value("${firebase.credentials.projectId:klustr-io-firebase-adminsdk-an9nu-d598a90d84}") String accountId
    ) throws IOException {
        // TODO issue is sending on behalf of the other firebase accounts?
        // need to register these credentials dynamically via an admin interface
        // which then enables a developer to use GCS as a deliver mechanic.
        GoogleCredentials credentials = GoogleCredentials
                .fromStream(U.getResourceAsStream(resource, this));

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(credentials)
                .setProjectId(projectId)
                .setServiceAccountId(accountId)
                .build();

        FirebaseApp.initializeApp(options);
    }

    @Override
    public void send(Notification notification) {
        // See documentation on defining a message payload.
        AndroidNotification android_notification = AndroidNotification
                .builder()
                .setImage(notification.image)
                .setChannelId(notification.channelId)      // must be registered on client
                .setClickAction(notification.clickAction)
                .setDefaultLightSettings(true)
                .setEventTimeInMillis(DateTime.now().getMillis())
                .setTag(notification.tag)
                .setColor(notification.color)
                .setVisibility(AndroidNotification.Visibility.PUBLIC)
                .build();

        Message.Builder msg = Message.builder();
        notification.params.forEach(msg::putData);
        if (StringUtils.isNotBlank(notification.channelId)) {
            msg = msg.putData("channelId", notification.channelId);
        }
        if (StringUtils.isNotBlank(notification.tag)) {
            msg = msg.putData("tag", notification.tag);
        }
        if (StringUtils.isNotBlank(notification.type)) {
            msg = msg.putData("type", notification.type);
        }
        if (StringUtils.isNotBlank(notification.defaultButtonText)) {
            msg = msg.putData("default_button", notification.defaultButtonText);
        }
        if (StringUtils.isNotBlank(notification.dismissButtonText)) {
            msg = msg.putData("dismiss_button", notification.dismissButtonText);
        }
        if (StringUtils.isNotBlank(notification.route)) {
            msg = msg.putData("route", notification.route);
        }
        if (StringUtils.isNotBlank(notification.largeIcon)) {
            msg = msg.putData("largeIcon", notification.largeIcon);
        }

        Message req = msg.setAndroidConfig(AndroidConfig.builder()
                        .setNotification(android_notification)
                        .setTtl(300)
                        .setPriority(AndroidConfig.Priority.HIGH)
                        .build())
                .setNotification(com.google.firebase.messaging.Notification.builder()
                        .setBody(notification.body)
                        .setImage(notification.image)
                        .setTitle(notification.title)
                        .build())
                .setToken(notification.token)
                .build();

        try {
            String response = FirebaseMessaging.getInstance().send(req);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
