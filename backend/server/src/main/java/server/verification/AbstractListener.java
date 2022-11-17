package server.verification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import server.entities.User;

import java.util.Objects;

public abstract class AbstractListener<T extends ApplicationEvent> implements ApplicationListener<T> {

    @Autowired
    private Environment environment;

    @Value("${support.email}")
    private String supportEmail;

    protected SimpleMailMessage constructEmail(String subject, String body, User user) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        // TODO: 17.11.2022  think about using environment.getProperty("support.email")
        email.setFrom(Objects.requireNonNull(supportEmail));
        return email;
    }

    // TODO: 17.11.2022 refactor constructEmail method
    // ? String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    // ? SimpleMailMessage email = constructResendVerificationTokenEmail(appUrl, request.getLocale(), newToken, user);
    //    private SimpleMailMessage constructResendVerificationTokenEmail(String contextPath, Locale locale, VerificationToken newToken, User user) {
    //        String confirmationUrl = contextPath + "/registrationConfirm?token=" + newToken.getToken();
    //        String message = messages.getMessage("message.resendToken", null, locale);
    //        SimpleMailMessage email = new SimpleMailMessage();
    //        email.setSubject("Resend Registration Token");
    //        email.setText(message + " rn" + confirmationUrl);
    //        email.setFrom(env.getProperty("support.email"));
    //        email.setTo(user.getEmail());
    //        return email;
    //    }
}
