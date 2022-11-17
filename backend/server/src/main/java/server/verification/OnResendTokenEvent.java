package server.verification;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@Getter
@Setter
@ToString
public class OnResendTokenEvent extends ApplicationEvent {
    private String appUrl;
    private Locale locale;
    private String existingToken;

    public OnResendTokenEvent(String existingToken, Locale locale, String appUrl) {
        super(existingToken);
        this.existingToken = existingToken;
        this.locale = locale;
        this.appUrl = appUrl;
    }
}
