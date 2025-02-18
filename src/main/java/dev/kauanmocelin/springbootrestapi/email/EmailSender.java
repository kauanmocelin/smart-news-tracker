package dev.kauanmocelin.springbootrestapi.email;

public interface EmailSender {
    void send(final String toRecipient, final String emailContent, final String subject);
}
