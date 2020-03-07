package contacts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Client {
    static final Scanner SCANNER = new Scanner(System.in);
    private static final Client CLIENT = new Client();
    private static final ContactBook CONTACT_BOOK = new ContactBook();
    private static final Invoker INVOKER = new Invoker();

    public static void main(String[] args) {
        loadContacts(args);
        CLIENT.openMainMenuDialog();
    }

    private static void loadContacts(String[] args) {
        if (args.length != 0) {
            String givenFilename = args[0];
            CONTACT_BOOK.setFilename(givenFilename);
            try {
                @SuppressWarnings("unchecked")
                ArrayList<Contact> contacts = (ArrayList<Contact>) SerializationUtils.deserialize(givenFilename);
                CONTACT_BOOK.setContacts(contacts);
                System.out.println("Loaded contacts from file: " + givenFilename);
            } catch (IOException | ClassNotFoundException e) {
                System.out.printf("No contacts loaded. New contacts will be saved to \"%s\"\n", givenFilename);
            }
        }
    }

    private void openMainMenuDialog() {
        boolean exitMenu = false;
        do {
            ContactBook.TERMINAL_COMMON.showMainMenuEnterAction();
            switch (ContactBook.TERMINAL_COMMON.getUserInput(SCANNER).toUpperCase()) {
                case "ADD":
                    openAddMenu();
                    break;
                case "LIST":
                    openListMenu();
                    break;
                case "SEARCH":
                    search();
                    break;
                case "COUNT":
                    ContactBook.TERMINAL_COMMON.showCount(CONTACT_BOOK.getContactSize());
                    break;
                case "EXIT":
                    exitMenu = true;
                    break;
                default:
                    ContactBook.TERMINAL_COMMON.showWrongInput();
            }
        } while (!exitMenu);
    }

    private void openAddMenu() {
        boolean exitMenu;
        do {
            ContactBook.TERMINAL_COMMON.showAddMenuEnterType();
            switch (ContactBook.TERMINAL_COMMON.getUserInput(SCANNER).toUpperCase()) {
                case "PERSON":
                    CommandAddPerson addPerson = new CommandAddPerson();
                    addPerson.setReceiver(CONTACT_BOOK);
                    INVOKER.setCommand(addPerson);
                    INVOKER.execute();
                    exitMenu = true;
                    break;
                case "ORGANIZATION":
                    CommandAddOrganization addOrganization = new CommandAddOrganization();
                    addOrganization.setReceiver(CONTACT_BOOK);
                    INVOKER.setCommand(addOrganization);
                    INVOKER.execute();
                    exitMenu = true;
                    break;
                case "BACK":
                    exitMenu = true;
                    break;
                default:
                    exitMenu = false;
                    ContactBook.TERMINAL_COMMON.showWrongInput();
            }
        } while (!exitMenu);
    }

    private void openListMenu() {
        if (CONTACT_BOOK.getContactSize() == 0) {
            ContactBook.TERMINAL_COMMON.showNoRecordsToShow();
        } else {
            boolean exitMenu;
            String action;
            do {
                CONTACT_BOOK.listContacts();
                ContactBook.TERMINAL_COMMON.showListMenuEnterAction();
                switch (action = ContactBook.TERMINAL_COMMON.getUserInput(SCANNER).toUpperCase()) {
                    case "":
                    case "NUMBER":
                        try {
                            contactById(ContactBook.TERMINAL_COMMON.getUserInput(SCANNER));
                            exitMenu = true;
                            break;
                        } catch (NumberFormatException | IndexOutOfBoundsException e) {
                            ContactBook.TERMINAL_COMMON.showWrongInput();
                            exitMenu = true;
                            break;
                        }
                    case "BACK":
                        exitMenu = true;
                        break;
                    default:
                        try {
                            contactById(action);
                            exitMenu = true;
                        } catch (NumberFormatException | IndexOutOfBoundsException e) {
                            ContactBook.TERMINAL_COMMON.showWrongInput();
                            exitMenu = false;
                        }
                }
            } while (!exitMenu);
        }
    }

    private void search() {
        if (CONTACT_BOOK.getContactSize() == 0) {
            ContactBook.TERMINAL_COMMON.showNoRecordsToSearch();
        } else {
            ContactBook.TERMINAL_COMMON.showEnterSearch();
            CommandSearch commandSearch = new CommandSearch();
            commandSearch.setReceiver(CONTACT_BOOK);
            commandSearch.setSearchString(ContactBook.TERMINAL_COMMON.getUserInput(SCANNER));
            INVOKER.setCommand(commandSearch);
            INVOKER.execute();
            openSearchMenu();
        }
    }

    private void openSearchMenu() {
        boolean exitMenu = true;
        String action;
        do {
            ContactBook.TERMINAL_COMMON.showSearchEnterAction();
            switch (action = ContactBook.TERMINAL_COMMON.getUserInput(SCANNER).toUpperCase()) {
                case "":
                case "NUMBER":
                    contactById(ContactBook.TERMINAL_COMMON.getUserInput(SCANNER));
                    break;
                case "BACK":
                    break;
                case "AGAIN":
                    search();
                    break;
                default:
                    try {
                        contactById(action);
                    } catch (NumberFormatException | IndexOutOfBoundsException e) {
                        ContactBook.TERMINAL_COMMON.showWrongInput();
                        exitMenu = false;
                    }
            }

        } while (!exitMenu);

    }

    private void contactById(String userInputId) {
        int contactId = Integer.parseInt(userInputId) - 1;
        showContactById(contactId);
        openContactMenu(contactId);
    }

    private void openContactMenu(int contactId) {
        boolean exitMenu = false;
        do {
            ContactBook.TERMINAL_COMMON.showContactMenuEnterAction();
            switch (ContactBook.TERMINAL_COMMON.getUserInput(SCANNER).toUpperCase()) {
                case "EDIT":
                    Contact contact = CONTACT_BOOK.getContactById(contactId);
                    contact.showEditableFields();
                    String[] editableFields = contact.getEditableFields();
                    String fieldForUpdate = ContactBook.TERMINAL_COMMON.getUserInput(SCANNER).toUpperCase();
                    if (!Arrays.asList(editableFields).contains(fieldForUpdate)) {
                        ContactBook.TERMINAL_COMMON.showWrongInput();
                    } else {
                        CommandUpdate commandUpdate = new CommandUpdate();
                        commandUpdate.setReceiver(CONTACT_BOOK);
                        commandUpdate.setContactId(contactId);
                        commandUpdate.setFieldForUpdate(fieldForUpdate);
                        ContactBook.TERMINAL_COMMON.showEnterField(fieldForUpdate.toLowerCase());
                        commandUpdate.setNewValue(ContactBook.TERMINAL_COMMON.getUserInput(SCANNER));
                        INVOKER.setCommand(commandUpdate);
                        INVOKER.execute();
                        showContactById(contactId);
                    }
                    break;
                case "DELETE":
                    CommandDelete commandDelete = new CommandDelete();
                    commandDelete.setReceiver(CONTACT_BOOK);
                    commandDelete.setContactId(contactId);
                    INVOKER.setCommand(commandDelete);
                    INVOKER.execute();
                    exitMenu = true;
                    break;
                case "MENU":
                    exitMenu = true;
                    break;
                default:
                    ContactBook.TERMINAL_COMMON.showWrongInput();
            }
        } while (!exitMenu);
    }

    private void showContactById(int contactId) {
        CommandShow commandShow = new CommandShow();
        commandShow.setReceiver(CONTACT_BOOK);
        commandShow.setContactId(contactId);
        INVOKER.setCommand(commandShow);
        INVOKER.execute();
    }
}
