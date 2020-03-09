package contacts;

import java.util.regex.Pattern;

/**
 * Class for validating input by predefined regular expressions.
 */
final class RegexValidator {
    private static final Pattern phonePattern = Pattern.compile(
            "^\\+?([\\da-zA-Z]{1,}[\\s-]?)?(\\([\\da-zA-Z]{2,}(\\)[\\s-]|\\)$))?([\\da-zA-Z]{2,}[\\s-]?)*([\\da-zA-Z]{2,})?$"
    );

    private RegexValidator() {
    }

    static boolean isValidPhoneNumber(CharSequence phone) {
        return phonePattern.matcher(phone).matches();
    }
}
