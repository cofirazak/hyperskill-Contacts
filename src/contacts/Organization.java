package contacts;

import java.util.Arrays;

/**
 * Concrete realisation of Contact abstraction.
 * This class describes all fields and behaviour needed for a organisation
 * to be saved in this Contact book console application.
 */
class Organization extends Contact {
    private static final long serialVersionUID = -414925866745458581L;
    private String address;

    final void setAddress(String address) {
        this.address = address;
    }

    final void setFieldByName(String fieldName, String newValue) {
        switch (fieldName) {
            case "NAME":
                name = newValue;
                break;
            case "ADDRESS":
                address = newValue;
                break;
            case "NUMBER":
                number = filterPhoneNumber(newValue);
                break;
        }
    }

    @Override
    final String[] getEditableFields() {
        return Arrays.stream(EditableFields.values()).map(Enum::toString).toArray(String[]::new);
    }

    @Override
    final void showContactsListItem(int index) {
        Client.TERMINAL.showListItem(index, name);
    }

    @Override
    final void showEditableFields() {
        Client.TERMINAL.showOrgEditableFields();
    }

    @Override
    public final String toString() {
        String currentNumber = number.isBlank() ? "[no number]" : number;
        return String.format("Organization name: %s%nAddress: %s%nNumber: %s%nTime created: %s%nTime last edit: %s%n",
                name, address, currentNumber, creationDateTime, lastEditDateTime);
    }

    enum EditableFields {
        NAME, ADDRESS, NUMBER
    }
}
