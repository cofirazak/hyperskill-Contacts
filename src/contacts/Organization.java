package contacts;

import java.util.Arrays;

/**
 * Concrete realisation of Contact abstraction.
 * This class describes all fields and behaviour needed for a organisation to be saved in this Contact book console application.
 */
class Organization extends Contact {
    private String organizationName;
    private String address;

    @Override
    String[] getEditableFields() {
        return Arrays.stream(EditableFields.values()).map(Enum::toString).toArray(String[]::new);
    }

    void setFieldByName(String fieldName, String newValue) {
        switch (fieldName) {
            case "NAME":
                setOrganizationName(newValue);
                break;
            case "ADDRESS":
                setAddress(newValue);
                break;
            case "NUMBER":
                setNumber(filterPhoneNumber(newValue));
                break;
        }
    }

    void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    void setAddress(String address) {
        this.address = address;
    }

    @Override
    void showContactsListItem(int index) {
        ContactBook.TERMINAL_COMMON.showListItem(index, organizationName);
    }

    @Override
    void showEditableFields() {
        ContactBook.TERMINAL_COMMON.showOrgEditableFields();
    }

    @Override
    public String toString() {
        String number = this.number.isBlank() ? "[no number]" : this.number;
        return "Organization name: " + organizationName + "\nAddress: " + address + "\nNumber: " + number +
                "\nTime created: " + creationDateTime + "\nTime last edit: " + lastEditDateTime;
    }

    enum EditableFields {
        NAME, ADDRESS, NUMBER
    }
}
