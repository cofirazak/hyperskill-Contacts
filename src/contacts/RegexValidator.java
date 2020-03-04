package contacts;

import java.util.regex.Pattern;

/**
 * Class for validating input by predefined regular expressions.
 */
class RegexValidator {
    /**
     * Predefined regular expression
     */
    static final Pattern phonePattern = Pattern.compile("^\\+?([\\da-zA-Z]{1,}[\\s-]?)?(\\([\\da-zA-Z]{2,}(\\)[\\s-]|\\)$))?([\\da-zA-Z]{2,}[\\s-]?)*([\\da-zA-Z]{2,})?$");

    /**
     * Checks whether the given phone number conforms the predefined regular expression
     * for phones in this Contact book console application.
     *
     * @param phone string representing a phone number
     * @return boolean
     */
    static boolean validatePhoneNumber(String phone) {
        return phonePattern.matcher(phone).matches();
    }
}
