package server.verification;

import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import server.entities.User;
import server.entities.VerificationToken;
import server.services.UsersService;

@Component
public class ResendTokenListener extends AbstractListener<OnResendTokenEvent> {

    private final UsersService service;

    private final MessageSource messages;

    private final JavaMailSender mailSender;

    public ResendTokenListener(UsersService service, MessageSource messages, JavaMailSender mailSender) {
        this.service = service;
        this.messages = messages;
        this.mailSender = mailSender;
    }

    @Override
    public void onApplicationEvent(OnResendTokenEvent event) {
        this.resendToken(event);
    }

    private void resendToken(OnResendTokenEvent event) {
        VerificationToken verificationToken = service.generateNewVerificationToken(event.getExistingToken());
        User user = verificationToken.getUser();
        String subject = "Resend Registration Token";
        String confirmationUrl = event.getAppUrl() + "/registrationConfirm?token=" + verificationToken.getToken();
        String message = messages.getMessage("message.resendToken", null, event.getLocale());
        String body = message + "\r\n" + "http://localhost:8080" + confirmationUrl;
        SimpleMailMessage email = constructEmail(subject, body, user);
        mailSender.send(email);
    }
}