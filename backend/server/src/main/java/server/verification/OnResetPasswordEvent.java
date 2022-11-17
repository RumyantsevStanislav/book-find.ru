package server.verification;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@Getter
@Setter
@ToString
public class OnResetPasswordEvent extends ApplicationEvent {
    private String appUrl;
    private Locale locale;
    private String email;

    public OnResetPasswordEvent(String email, Locale locale, String appUrl) {
        super(email);
        this.email = email;
        this.locale = locale;
        this.appUrl = appUrl;
    }
}
