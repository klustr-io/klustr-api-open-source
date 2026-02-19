package io.klustr.integrations.mailtrap;


import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@Tag("integration")
public class SmtpServerIntegrationTest {

    @Test
    public void we_can_send_an_email() throws Exception {

        String username = System.getenv("MAILTRAP_USERNAME"); // e.g. "api"
        String password = System.getenv("MAILTRAP_PASSWORD");

        // Skip unless configured
        assumeTrue(username != null && !username.isBlank(), "MAILTRAP_USERNAME not set");
        assumeTrue(password != null && !password.isBlank(), "MAILTRAP_PASSWORD not set");

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "live.smtp.mailtrap.io");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("support@klustr.io"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("test@klustr.io")
            );
            message.setSubject("Test Email");
            message.setText("Hello from Java SMTP!");

            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
