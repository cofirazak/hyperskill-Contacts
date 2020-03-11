package contactUtils;

import java.util.regex.Pattern;

/**
 * Class for validating input by predefined regular expressions.
 */
public final class RegexValidator {
    private static final Pattern phonePattern = Pattern.compile(
            "^\\+?([\\da-zA-Z]{1,}[\\s-]?)?(\\([\\da-zA-Z]{2,}(\\)[\\s-]|\\)$))?([\\da-zA-Z]{2,}[\\s-]?)*([\\da-zA-Z]{2,})?$"
    );

    private RegexValidator() {
    }

    public static boolean isValidPhoneNumber(CharSequence phone) {
        return phonePattern.matcher(phone).matches();
    }
}
