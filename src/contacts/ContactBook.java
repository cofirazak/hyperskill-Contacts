package contacts;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Main logic of the contact book application.
 */
public class ContactBook {
    static final TerminalConsole TERMINAL_COMMON = new TerminalConsole();
    private ArrayList<Contact> contacts = new ArrayList<>();
    private String filename;

    int getContactSize() {
        return contacts.size();
    }

    Contact getContactById(int contactId) {
        return contacts.get(contactId);
    }

    /**
     * Loads the list of contact objects previously saved to db or serialized.
     *
     * @param contacts list of contact objects.
     */
    void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    /**
     * Sets the file for saving all the contacts after each change.
     *
     * @param filename name of file to save contacts to.
     */
    void setFilename(String filename) {
        this.filename = filename;
    }

    void addPerson() {
        Person person = new Person();
        TERMINAL_COMMON.showEnterField("name");
        person.setName(TERMINAL_COMMON.getUserInput(Client.SCANNER));
        TERMINAL_COMMON.showEnterField("surname");
        person.setSurname(TERMINAL_COMMON.getUserInput(Client.SCANNER));
        TERMINAL_COMMON.showEnterField("birth date(yyyy-MM-dd)");
        person.setBirthDate(person.tryCastStrToDate(TERMINAL_COMMON.getUserInput(Client.SCANNER)));
        TERMINAL_COMMON.showEnterField("gender(M|F)");
        person.setGender(person.tryCastStrToGender(TERMINAL_COMMON.getUserInput(Client.SCANNER)));
        TERMINAL_COMMON.showEnterField("phone number");
        person.setNumber(person.filterPhoneNumber(TERMINAL_COMMON.getUserInput(Client.SCANNER)));
        person.setCreationDateTime(LocalDateTime.now().withNano(0));
        person.setLastEditDateTime(LocalDateTime.now().withNano(0));
        contacts.add(person);
        TERMINAL_COMMON.showContactAdded();
        serializeContacts();
    }

    private void serializeContacts() {
        if (filename != null) {
            try {
                SerializationUtils.serialize(contacts, filename);
            } catch (IOException e) {
                TERMINAL_COMMON.showCantSaveContacts();
                //TODO add logging e.getStackTrace()
            }
        }
    }

    void addOrganization() {
        Organization organization = new Organization();
        TERMINAL_COMMON.showEnterField("name");
        organization.setOrganizationName(TERMINAL_COMMON.getUserInput(Client.SCANNER));
        TERMINAL_COMMON.showEnterField("address");
        organization.setAddress(TERMINAL_COMMON.getUserInput(Client.SCANNER));
        TERMINAL_COMMON.showEnterField("phone number");
        organization.setNumber(organization.filterPhoneNumber(TERMINAL_COMMON.getUserInput(Client.SCANNER)));
        organization.setCreationDateTime(LocalDateTime.now());
        contacts.add(organization);
        TERMINAL_COMMON.showContactAdded();
        serializeContacts();
    }

    void listContacts() {
        for (int i = 0; i < contacts.size(); i++) {
            contacts.get(i).showContactsListItem(i + 1);
        }
    }

    void showContact(int inputIndex) {
        TERMINAL_COMMON.showContact(contacts.get(inputIndex).toString());
    }

    void searchContact(String searchStr) {
        Pattern searchQuery = Pattern.compile(".*" + searchStr + ".*", Pattern.CASE_INSENSITIVE);
        ArrayList<Contact> searchResult = new ArrayList<>();
        for (Contact contact : contacts) {
            List<Field> fields = contact.getAllFieldNames();
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    Object fieldValue = field.get(contact);
                    if (fieldValue != null && searchQuery.matcher(fieldValue.toString()).matches()) {
                        searchResult.add(contact);
                    }
                } catch (IllegalAccessException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        TERMINAL_COMMON.showFoundResults(searchResult.size());
        for (int i = 0; i < searchResult.size(); i++) {
            searchResult.get(i).showContactsListItem(i + 1);
        }
    }

    void updateContact(int contactId, String fieldName, String newValue) {
        Contact contact = contacts.get(contactId);
        contact.setFieldByName(fieldName, newValue);
        contact.setLastEditDateTime(LocalDateTime.now().withNano(0));
        contacts.set(contactId, contact);
        TERMINAL_COMMON.showContactSaved();
        serializeContacts();
    }

    void deleteContact(int contactIndex) {
        if (contacts.isEmpty()) {
            TERMINAL_COMMON.showNoContacts();
        } else {
            try {
                contacts.remove(contactIndex);
                TERMINAL_COMMON.showContactRemoved();
                serializeContacts();
            } catch (IndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
                System.out.println(Arrays.toString(e.getStackTrace()));
            }
        }
    }
}
