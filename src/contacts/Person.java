package contacts;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

/**
 * Concrete realisation of Contact abstraction.
 * This class describes all fields and behaviour needed for a person to be saved in this Contact book console application.
 */
class Person extends Contact {
    private String name;
    private String surname;
    private LocalDate birthDate;
    private Gender gender;

    @Override
    String[] getEditableFields() {
        return Arrays.stream(EditableFields.values()).map(Enum::toString).toArray(String[]::new);
    }

    @Override
    void setFieldByName(String fieldName, String newValue) {
        switch (fieldName) {
            case "NAME":
                setName(newValue);
                break;
            case "SURNAME":
                setSurname(newValue);
                break;
            case "BIRTHDATE":
                setBirthDate(tryCastStrToDate(newValue));
                break;
            case "GENDER":
                setGender(tryCastStrToGender(newValue));
                break;
            case "NUMBER":
                setNumber(filterPhoneNumber(newValue));
                break;
        }
    }

    void setName(String name) {
        this.name = name;
    }

    void setSurname(String surname) {
        this.surname = surname;
    }

    void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    LocalDate tryCastStrToDate(String inputDate) {
        try {
            return LocalDate.parse(inputDate);
        } catch (DateTimeParseException e) {
            ContactBook.TERMINAL_COMMON.showBadDate();
            return null;
        }
    }

    void setGender(Gender gender) {
        this.gender = gender;
    }

    Gender tryCastStrToGender(String inputGender) {
        try {
            return Gender.valueOf(inputGender);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    void showEditableFields() {
        ContactBook.TERMINAL_COMMON.showPersonEditableFields();
    }

    @Override
    void showContactsListItem(int index) {
        ContactBook.TERMINAL_COMMON.showListItem(index, name, surname);
    }

    @Override
    public String toString() {
        String birthDate = this.birthDate == null ? "[no data]" : this.birthDate.toString();
        String gender = this.gender == null ? "[no data]" : this.gender.toString();
        String number = this.number.isBlank() ? "[no number]" : this.number;
        return "Name: " + name + "\nSurname: " + surname + "\nBirth date: " +
                birthDate + "\nGender: " + gender + "\nNumber: " + number +
                "\nTime created: " + creationDateTime + "\nTime last edit: " + lastEditDateTime;
    }

    enum Gender {
        M, F
    }

    enum EditableFields {
        NAME, SURNAME, BIRTHDATE, GENDER, NUMBER
    }
}
