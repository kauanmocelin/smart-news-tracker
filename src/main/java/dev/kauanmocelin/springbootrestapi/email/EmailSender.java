package dev.kauanmocelin.springbootrestapi.email;

public interface EmailSender {
    void send(final String to, final String email);
}
