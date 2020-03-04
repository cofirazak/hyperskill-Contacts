package contacts;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

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
    public String toString() {
        String birthDate = this.birthDate == null ? "[no data]" : this.birthDate.toString();
        String gender = this.gender == null ? "[no data]" : this.gender.toString();
        String number = this.number.isBlank() ? "[no number]" : this.number;
        return "Name: " + name + "\nSurname: " + surname + "\nBirth date: " +
                birthDate + "\nGender: " + gender + "\nNumber: " + number +
                "\nTime created: " + creationDateTime + "\nTime last edit: " + lastEditDateTime;
    }

    @Override
    void setValueToField(String fieldName, String newValue) {
        try {
            Field field = getFieldByName(this.getClass(), fieldName);

            switch (field.getType().getName()) {
                case "java.lang.String":
                    field.set(this, newValue);
                    break;
                case "java.time.LocalDate":
                    field.set(this, tryCastStrToDate(newValue));
                    break;
                case "contacts.Gender":
                    field.set(this, tryCastStrToGender(newValue));
                    break;
                default:
            }

        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.println("Oops, something went wrong.");
        }
    }

    @Override
    void showEditMenuFields() {
        System.out.print("Select a field (name, surname, number, birth, gender): ");
    }

    @Override
    void showListItems(int index) {
        System.out.printf("%d. %s %s\n", index, name, surname);
    }

    private LocalDate tryCastStrToDate(String inputDate) {
        try {
            return LocalDate.parse(inputDate);
        } catch (DateTimeParseException e) {
            System.out.println("Bad birth date!");
            return null;
        }
    }

    private Gender tryCastStrToGender(String inputGender) {
        try {
            return Gender.valueOf(inputGender.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
