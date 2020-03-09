package contacts;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

/**
 * Concrete realisation of Contact abstraction.
 * This class describes all fields and behaviour needed for a person
 * to be saved in this Contact book console application.
 */
class Person extends Contact {
    private static final long serialVersionUID = 3725973772164558564L;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private Gender gender;

    @Override
    final String[] getEditableFields() {
        return Arrays.stream(EditableFields.values()).map(Enum::toString).toArray(String[]::new);
    }

    @Override
    final void setFieldByName(String fieldName, String newValue) {
        switch (fieldName) {
            case "NAME":
                name = newValue;
                break;
            case "SURNAME":
                surname = newValue;
                break;
            case "BIRTHDATE":
                birthDate = tryCastStrToDate(newValue);
                break;
            case "GENDER":
                gender = tryCastStrToGender(newValue);
                break;
            case "NUMBER":
                number = filterPhoneNumber(newValue);
                break;
        }
    }

    final LocalDate tryCastStrToDate(CharSequence inputDate) {
        LocalDate result = null;
        try {
            result = LocalDate.parse(inputDate);
        } catch (DateTimeParseException e) {
            ContactBook.TERMINAL_COMMON.showBadDate();
        }
        return result;
    }

    final Gender tryCastStrToGender(String inputGender) {
        Gender result = null;
        try {
            result = Gender.valueOf(inputGender);
        } catch (IllegalArgumentException e) {
            ContactBook.TERMINAL_COMMON.showBadGender();
        }
        return result;
    }

    final void setName(String name) {
        this.name = name;
    }

    final void setSurname(String surname) {
        this.surname = surname;
    }

    final void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    final void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    final void showContactsListItem(int index) {
        ContactBook.TERMINAL_COMMON.showListItem(index, name, surname);
    }

    @Override
    final void showEditableFields() {
        ContactBook.TERMINAL_COMMON.showPersonEditableFields();
    }

    @Override
    public final String toString() {
        String currentBirthDate = null == birthDate ? "[no data]" : birthDate.toString();
        String currentGender = null == gender ? "[no data]" : gender.toString();
        String currentNumber = number.isBlank() ? "[no number]" : number;
        return String.format(
                "Name: %s%nSurname: %s%nBirth date: %s%nGender: %s%nNumber: %s%nTime created: %s%nTime last edit: %s%n",
                name, surname, currentBirthDate, currentGender, currentNumber, creationDateTime, lastEditDateTime
        );
    }

    enum Gender {
        M, F
    }

    enum EditableFields {
        NAME, SURNAME, BIRTHDATE, GENDER, NUMBER
    }
}
