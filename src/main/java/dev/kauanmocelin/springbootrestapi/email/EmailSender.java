package dev.kauanmocelin.springbootrestapi.email;

import jakarta.mail.MessagingException;

public interface EmailSender {
    void send(final String to, final String email) throws MessagingException;
}
