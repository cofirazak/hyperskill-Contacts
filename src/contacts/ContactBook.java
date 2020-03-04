package contacts;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Main logic of the contact book application.
 */
class ContactBook {
    private final Scanner scanner = new Scanner(System.in);
    private ArrayList<Contact> contacts = new ArrayList<>();
    private String filename;

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

    /**
     * Entry point to contact book.
     */
    void openMainMenu() {
        boolean exitMenu = false;

        do {
            System.out.print("\n[menu] Enter action (add, list, searchContact, count, exit): ");

            switch (scanner.nextLine().toUpperCase()) {
                case "ADD":
                    openAddMenu();
                    break;
                case "LIST":
                    openListMenu();
                    break;
                case "SEARCH":
                    searchContact();
                    break;
                case "COUNT":
                    System.out.println("The Phone Book has " + contacts.size() + " records.");
                    break;
                case "EXIT":
                    exitMenu = true;
                    break;
                default:
                    wrongInputEntered();
            }
        } while (!exitMenu);
    }

    private void openAddMenu() {
        boolean exitAddMenu = true;

        do {
            System.out.print("\n[add] Enter the type (person, organization) or back: ");

            switch (scanner.nextLine().toUpperCase()) {
                case "PERSON":
                    addContact(new Person());
                    break;
                case "ORGANIZATION":
                    addContact(new Organization());
                    break;
                case "BACK":
                    break;
                default:
                    exitAddMenu = false;
                    wrongInputEntered();
            }
        } while (!exitAddMenu);
    }

    private void openListMenu() {
        String action;
        boolean exitListMenu = false;

        do {
            listContacts();
            System.out.print("\n[list] Enter action ([number], back): ");

            switch (action = scanner.nextLine().toUpperCase()) {
                case "":
                case "NUMBER":
                    showContact(scanner.nextLine());
                    exitListMenu = true;
                    break;
                case "BACK":
                    exitListMenu = true;
                    break;
                default:
                    try {
                        Integer.parseInt(action);
                        showContact(action);
                        exitListMenu = true;
                    } catch (NumberFormatException e) {
                        wrongInputEntered();
                    }
            }
        } while (!exitListMenu);
    }

    private void openSearchMenu() {
        String action;
        boolean exitSearchMenu = true;

        do {
            System.out.print("[searchContact] Enter action ([number], back, again): ");

            switch (action = scanner.nextLine().toUpperCase()) {
                case "":
                case "NUMBER":
                    String contactIndex = scanner.nextLine();
                    showContact(contactIndex);
                    openContactMenu(Integer.parseInt(contactIndex));
                    break;
                case "BACK":
                    break;
                case "AGAIN":
                    searchContact();
                    break;
                default:
                    try {
                        Integer.parseInt(action);
                        showContact(action);
                    } catch (NumberFormatException e) {
                        wrongInputEntered();
                    }
            }

        } while (!exitSearchMenu);

    }

    private void openEditMenu(int contactIndex) {
        Contact contact = getContactById(contactIndex);
        boolean exitEditMenu = false;

        do {
            contact.showEditMenuFields();
            String fieldToEdit = scanner.nextLine().toLowerCase();
            List<Field> fieldsAbleToEdit = contact.getAllFieldNames();

            for (Field filedAbleToEdit : fieldsAbleToEdit) {
                if (filedAbleToEdit.getName().equals(fieldToEdit)) {
                    System.out.print("Enter " + filedAbleToEdit + ": ");
                    if ("number".equals(filedAbleToEdit.getName())) {
                        contact.setValueToField(filedAbleToEdit.getName(), filterPhoneNumber(scanner.nextLine()));
                    } else {
                        contact.setValueToField(filedAbleToEdit.getName(), scanner.nextLine());
                    }
                    contact.setLastEditDateTime(LocalDateTime.now().withNano(0));
                    contacts.set(contactIndex, contact);
                    System.out.println("Saved");
                    serializeContacts();
                    exitEditMenu = true;
                    break;
                } else {
                    wrongInputEntered();
                }
            }
        } while (!exitEditMenu);
    }

    private void openContactMenu(int contactIndex) {
        boolean exitContactMenu = false;

        do {
            System.out.print("\n[contact] Enter action (edit, delete, menu): ");

            switch (scanner.nextLine().toUpperCase()) {
                case "EDIT":
                    openEditMenu(contactIndex);
                    break;
                case "DELETE":
                    deleteContact(contactIndex);
                    exitContactMenu = true;
                    break;
                case "MENU":
                    exitContactMenu = true;
                    break;
                default:
                    wrongInputEntered();
            }

        } while (!exitContactMenu);
    }

    private void addContact(Contact contact) {
        List<Field> fieldsAbleToEdit = contact.getAllFieldNames();

        for (Field filedAbleToEdit : fieldsAbleToEdit) {
            System.out.print("Enter " + filedAbleToEdit.getName() + ": ");
            if ("number".equals(filedAbleToEdit.getName())) {
                contact.setValueToField(filedAbleToEdit.getName(), filterPhoneNumber(scanner.nextLine()));
            } else {
                contact.setValueToField(filedAbleToEdit.getName(), scanner.nextLine());
            }
        }

        contact.setCreationDateTime(LocalDateTime.now().withNano(0));
        contact.setLastEditDateTime(LocalDateTime.now().withNano(0));
        contacts.add(contact);
        System.out.println("Contact added.");
        serializeContacts();
    }

    private void searchContact() {
        System.out.println("Enter searchContact query: ");
        Pattern searchQuery = Pattern.compile(".*" + scanner.nextLine() + ".*", Pattern.CASE_INSENSITIVE);
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

        System.out.println("Found " + searchResult.size() + " results: ");

        for (int i = 0; i < searchResult.size(); i++) {
            searchResult.get(i).showListItems(i + 1);
        }

        openSearchMenu();
    }

    private void deleteContact(int contactIndex) {
        if (contacts.isEmpty()) {
            System.out.println("No records to delete!");
        } else {
            try {
                contacts.remove(contactIndex);
                System.out.println("The record removed!");
                serializeContacts();
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                wrongInputEntered();
            }
        }
    }

    private void showContact(String inputStr) {
        try {
            int contactIndex = Integer.parseInt(inputStr) - 1;
            Contact contact = getContactById(contactIndex);
            System.out.println(contact.toString());
            openContactMenu(contactIndex);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            wrongInputEntered();
        }
    }

    private Contact getContactById(int id) throws IndexOutOfBoundsException {
        return contacts.get(id);
    }

    private void listContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No records to show!");
        } else {
            for (int i = 0; i < contacts.size(); i++) {
                getContactById(i).showListItems(i + 1);
            }
        }
    }

    private void serializeContacts() {
        if (filename != null) {
            try {
                SerializationUtils.serialize(contacts, filename);
            } catch (IOException e) {
                System.out.println("Couldn't save contacts.");
                System.out.println(e.getMessage());
            }
        }
    }

    private String filterPhoneNumber(String number) {
        if (RegexValidator.validatePhoneNumber(number)) {
            return number;
        } else {
            System.out.println("Wrong number format!");
            return "";
        }
    }

    private void wrongInputEntered() {
        System.out.println("Wrong input entered. Try again...");
    }
}
