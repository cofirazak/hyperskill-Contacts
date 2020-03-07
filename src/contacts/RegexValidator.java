package contacts;

import java.util.regex.Pattern;

/**
 * Class for validating input by predefined regular expressions.
 */
class RegexValidator {
    static final Pattern phonePattern = Pattern.compile("^\\+?([\\da-zA-Z]{1,}[\\s-]?)?(\\([\\da-zA-Z]{2,}(\\)[\\s-]|\\)$))?([\\da-zA-Z]{2,}[\\s-]?)*([\\da-zA-Z]{2,})?$");

    static boolean validatePhoneNumber(String phone) {
        return phonePattern.matcher(phone).matches();
    }
}
