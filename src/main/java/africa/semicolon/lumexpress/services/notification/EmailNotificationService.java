package africa.semicolon.lumexpress.services.notification;

import africa.semicolon.lumexpress.data.dtos.requests.EmailNotificationRequest;

public interface EmailNotificationService {
    String sendHtmlMail(EmailNotificationRequest emailNotificationRequest);


}
