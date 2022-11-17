package server.verification;

import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import server.entities.PasswordResetToken;
import server.entities.User;
import server.entities.VerificationToken;
import server.services.UsersService;

@Component
public class ResetPasswordListener extends AbstractListener<OnResetPasswordEvent> {

    private final UsersService service;

    private final MessageSource messages;

    private final JavaMailSender mailSender;

    public ResetPasswordListener(UsersService service, MessageSource messages, JavaMailSender mailSender) {
        this.service = service;
        this.messages = messages;
        this.mailSender = mailSender;
    }

    @Override
    public void onApplicationEvent(OnResetPasswordEvent event) {
        this.sendResetPasswordToken(event);
    }

    private void sendResetPasswordToken(OnResetPasswordEvent event) {
        //TODO cut throw Exception
        User user = service.getUserByEmail(event.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        PasswordResetToken passwordResetToken = service.createPasswordResetToken(user);
        String subject = "Reset Password";
        String confirmationUrl = event.getAppUrl() + "/changePassword?token=" + passwordResetToken.getToken();
        String message = messages.getMessage("message.resetPassword", null, event.getLocale());
        String body = message + "\r\n" + "http://localhost:8080" + confirmationUrl;
        SimpleMailMessage email = constructEmail(subject, body, user);
        mailSender.send(email);
    }
}