package africa.semicolon.lumexpress.services.notification;

import africa.semicolon.lumexpress.data.dtos.requests.NotificationRequest;

public interface LumExpressNotificationService {
    String send (NotificationRequest notificationRequest);
}
