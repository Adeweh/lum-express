package africa.semicolon.lumexpress.services.notification;

import africa.semicolon.lumexpress.data.dtos.requests.EmailNotificationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class GmailNotificationServiceImplTest {
    @Autowired
    private EmailNotificationService emailNotificationService;

    @Test
    void sendHtmlMailTest(){
        EmailNotificationRequest emailNotificationRequest = new EmailNotificationRequest();
        emailNotificationRequest.setUserEmail("adewehabang@gmail.com");
        emailNotificationRequest.setMailContent("Hello Beautiful");
        String response = emailNotificationService.sendHtmlMail(emailNotificationRequest);
        assertThat(response.contains("successfully")).isTrue();

    }
}