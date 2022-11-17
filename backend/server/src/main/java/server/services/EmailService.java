package server.services;

import javax.mail.MessagingException;

// TODO: 17.11.2022 include in project
public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);

    public void sendSimpleMessageUsingTemplate(String to, String subject, String... templateModel);

    void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment) throws MessagingException;
}
