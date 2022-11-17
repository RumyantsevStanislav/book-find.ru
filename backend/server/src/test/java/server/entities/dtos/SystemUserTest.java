package server.entities.dtos;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import server.utils.RegExpPatterns;

import java.util.regex.Pattern;

public class SystemUserTest {
    // TODO: 17.11.2022 refactor
    @Test
    public void phoneOrEmailPatternTest() {
        Assertions.assertTrue(Pattern.matches(RegExpPatterns.PHONE_OR_EMAIL, "+0"));
        Assertions.assertTrue(Pattern.matches(RegExpPatterns.PHONE_OR_EMAIL, "+11"));
        Assertions.assertTrue(Pattern.matches(RegExpPatterns.PHONE_OR_EMAIL, "A"));
        Assertions.assertTrue(Pattern.matches(RegExpPatterns.PHONE_OR_EMAIL, "ZZ"));
        Assertions.assertFalse(Pattern.matches(RegExpPatterns.PHONE_OR_EMAIL, "++1"));
        Assertions.assertFalse(Pattern.matches(RegExpPatterns.PHONE_OR_EMAIL, "1a"));
        Assertions.assertTrue(Pattern.matches("^.*(?=.*\\d)(?=.*[a-zа-яё])(?=.*[A-ZА-ЯЁ]).*$", "Qw1"));
    }
}
