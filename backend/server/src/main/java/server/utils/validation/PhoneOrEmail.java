package server.utils.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = PhoneOrEmailValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PhoneOrEmail {
    String message() default "{It is not phone and not email}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
