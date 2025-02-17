package dev.kauanmocelin.springbootrestapi.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class EmailService implements EmailSender {

    private final JavaMailSender mailSender;

    @Override
    @Async
    public void send(String to, String email) throws MessagingException {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Confirm your email");
            helper.setFrom("hello@amigos.com");
            mailSender.send(mimeMessage);
            log.info("email sent successfully to {} with subject {}", to, "Confirm your email");
        } catch (MessagingException ex) {
            log.error("fail to send email to {} with subject {}", to, "Confirm your email", ex);
            throw ex;
        }
    }
}
