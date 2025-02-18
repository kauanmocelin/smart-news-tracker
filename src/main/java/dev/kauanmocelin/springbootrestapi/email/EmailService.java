package dev.kauanmocelin.springbootrestapi.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService implements EmailSender {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromSender;

    @Override
    @Async
    public void send(final String toRecipient, final String emailContent, final String subject) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(emailContent, true);
            helper.setTo(toRecipient);
            helper.setSubject(subject);
            helper.setFrom(fromSender);
            mailSender.send(mimeMessage);
            log.info("email sent successfully to {} with subject {}", toRecipient, subject);
        } catch (MessagingException ex) {
            log.error("fail to send email to {} with subject {}", toRecipient, subject, ex);
            throw new IllegalStateException("fail to send email", ex);
        }
    }
}
