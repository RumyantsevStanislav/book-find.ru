package server.verification;

import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import server.entities.User;
import server.entities.VerificationToken;
import server.services.UsersService;

@Component
public class RegistrationListener extends AbstractListener<OnRegistrationCompleteEvent> {

    private final UsersService service;

    private final MessageSource messages;

    private final JavaMailSender mailSender;

    public RegistrationListener(UsersService service, MessageSource messages, JavaMailSender mailSender) {
        this.service = service;
        this.messages = messages;
        this.mailSender = mailSender;
    }

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        VerificationToken verificationToken = service.createVerificationToken(user);
        String subject = "Registration Confirmation";
        String confirmationUrl = event.getAppUrl() + "/registrationConfirm?token=" + verificationToken.getToken();
        String message = messages.getMessage("message.regSucc", null, event.getLocale());
        String body = message + "\r\n" + "http://localhost:8080" + confirmationUrl;
        SimpleMailMessage email = constructEmail(subject, body, user);
        mailSender.send(email);
    }
}
