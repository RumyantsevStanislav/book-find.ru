package server.utils.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PhoneOrEmailValidator implements ConstraintValidator<PhoneOrEmail, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$").matcher(value).matches()) {
            return true;
        } else {
            return Pattern.compile("^((\\+7)|7|8)?\\d{10}$").matcher(value).matches();
        }
    }
}
