package server.utils;

import java.util.regex.Pattern;

// TODO: 17.11.2022 include const instead of regexp in project
public class RegExpPatterns {

    static Pattern PHONE_OR_EMAIL_PATTERN = Pattern.compile("^\\+?[0-9]*$|^[A-Z]*$");

    public static String PHONE_OR_EMAIL = PHONE_OR_EMAIL_PATTERN.pattern();
}
