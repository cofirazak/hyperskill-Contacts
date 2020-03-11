package receivers;

import app.Contacts;
import contactUtils.Serialization;
import contacts.Contact;
import contacts.ContactCreation;
import contacts.ContactType;

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
    private static final int INITIAL_CAPACITY = 50;
    private ArrayList<Contact> contacts = new ArrayList<>(INITIAL_CAPACITY);
    private String filename;

    /**
     * Loads the list of contact objects previously saved to db or serialized.
     *
     * @param contacts list of contact objects.
     */
    public final void setContacts(ArrayList<Contact> contacts) {
        this.contacts = new ArrayList<>(contacts);
    }

    /**
     * Sets the file for saving all the contacts after each change.
     *
     * @param filename name of file to save contacts to.
     */
    public final void setFilename(String filename) {
        this.filename = filename;
    }

    public final int getContactSize() {
        return contacts.size();
    }

    public final Contact getContactById(int contactId) {
        return contacts.get(contactId);
    }

    public final void addPerson() {
        contacts.add(new ContactCreation().getContact(ContactType.PERSON));
        Contacts.TERMINAL.showContactAdded();
    }

    public final void addOrganization() {
        contacts.add(new ContactCreation().getContact(ContactType.ORGANIZATION));
        Contacts.TERMINAL.showContactAdded();
    }

    public final void listContacts() {
        final int size = contacts.size();
        for (int i = 0; i < size; i++) {
            contacts.get(i).showContactsListItem(i + 1);
        }
    }

    public final void showContact(int inputIndex) {
        Contacts.TERMINAL.showContact(contacts.get(inputIndex).toString());
    }

    public final void updateContact(int contactId, String fieldName, String newValue) {
        Contact contact = contacts.get(contactId);
        contact.setFieldByName(fieldName, newValue);
        contact.setLastEditDateTime(LocalDateTime.now().withNano(0));
        contacts.set(contactId, contact);
        Contacts.TERMINAL.showContactSaved();
    }

    public final boolean deleteContact(int contactIndex) {
        boolean result = false;
        if (contacts.isEmpty()) {
            Contacts.TERMINAL.showNoContacts();
        } else {
            try {
                contacts.remove(contactIndex);
                Contacts.TERMINAL.showContactRemoved();
                result = true;
            } catch (IndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
                System.out.println(Arrays.toString(e.getStackTrace()));
            }
        }
        return result;
    }

    public final void serialize() {
        if (null != filename) {
            try {
                Serialization.serialize(contacts, filename);
            } catch (IOException e) {
                Contacts.TERMINAL.showCantSaveContacts();
            }
        }
    }

    public final void searchContact(String searchStr) {
        Pattern searchQuery = Pattern.compile(".*" + searchStr + ".*", Pattern.CASE_INSENSITIVE);
        List<Contact> searchResult = new ArrayList<>(INITIAL_CAPACITY);
        for (Contact contact : contacts) {
            List<Field> fields = contact.getAllFieldNames();
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    Object fieldValue = field.get(contact);
                    if (null != fieldValue && searchQuery.matcher(fieldValue.toString()).matches()) {
                        searchResult.add(contact);
                    }
                } catch (IllegalAccessException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        final int size = searchResult.size();
        Contacts.TERMINAL.showFoundResults(size);
        for (int i = 0; i < size; i++) {
            searchResult.get(i).showContactsListItem(i + 1);
        }
    }
}
