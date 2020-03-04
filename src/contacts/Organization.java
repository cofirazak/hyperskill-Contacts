package contacts;

import java.lang.reflect.Field;

/**
 * Concrete realisation of Contact abstraction.
 * This class describes all fields and behaviour needed for a organisation to be saved in this Contact book console application.
 */
class Organization extends Contact {
    private String organizationName;
    private String address;

    @Override
    public String toString() {
        String number = this.number.isBlank() ? "[no number]" : this.number;
        return "Organization name: " + organizationName + "\nAddress: " + address + "\nNumber: " + number +
                "\nTime created: " + creationDateTime + "\nTime last edit: " + lastEditDateTime;
    }

    @Override
    void setValueToField(String fieldName, String newValue) {
        try {
            Field field = getFieldByName(this.getClass(), fieldName);
            field.set(this, newValue);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.println("Oops, something went wrong.");
        }
    }

    @Override
    void showEditMenuFields() {
        System.out.print("Select a field (organization, address, number): ");
    }

    @Override
    void showListItems(int index) {
        System.out.printf("%d. %s\n", index, organizationName);
    }
}
