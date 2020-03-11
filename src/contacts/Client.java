package contacts;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Client {
    static final Terminal TERMINAL = new ConsoleTerminal();
    private static final Client CLIENT = new Client();
    private static final ContactBook CONTACT_BOOK = new ContactBook();
    private static final Invoker INVOKER = new Invoker();

    public static void main(String[] args) {
        loadContacts(args);
        CLIENT.openMainMenuDialog();
    }

    private static void loadContacts(String[] args) {
        if (0 != args.length) {
            String givenFilename = args[0];
            CONTACT_BOOK.setFilename(givenFilename);
            try {
                @SuppressWarnings("unchecked")
                ArrayList<Contact> contacts = (ArrayList<Contact>) SerializationUtils.deserialize(givenFilename);
                CONTACT_BOOK.setContacts(contacts);
                TERMINAL.showFileLoaded(givenFilename);
            } catch (FileNotFoundException | ClassNotFoundException e) {
                TERMINAL.showNewFileCreated(givenFilename);
            } catch (IOException e) {
                TERMINAL.showNewFileCreated(givenFilename);
            }
        }
    }

    private void openMainMenuDialog() {
        boolean doNotExit = true;
        do {
            TERMINAL.showMainMenuEnterAction();
            switch (TERMINAL.getUserInput().toUpperCase()) {
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
                    TERMINAL.showCount(CONTACT_BOOK.getContactSize());
                    break;
                case "EXIT":
                    doNotExit = false;
                    break;
                default:
                    TERMINAL.showWrongInput();
            }
        } while (doNotExit);
    }

    private void openAddMenu() {
        boolean doNotStop = false;
        do {
            TERMINAL.showAddMenuEnterType();
            switch (TERMINAL.getUserInput().toUpperCase()) {
                case "PERSON":
                    commandAddPerson();
                    break;
                case "ORGANIZATION":
                    commandAddOrganisation();
                    break;
                case "BACK":
                    break;
                default:
                    TERMINAL.showWrongInput();
                    doNotStop = true;
            }
        } while (doNotStop);
    }

    private void commandAddPerson() {
        Command addPerson = new addPersonCommand(CONTACT_BOOK);
        INVOKER.setCommand(addPerson);
        INVOKER.execute();
    }

    private void commandAddOrganisation() {
        Command addOrganization = new addOrganizationCommand(CONTACT_BOOK);
        INVOKER.setCommand(addOrganization);
        INVOKER.execute();
    }

    private void openListMenu() {
        if (0 == CONTACT_BOOK.getContactSize()) {
            TERMINAL.showNoRecordsToShow();
        } else {
            boolean doNotStop = false;
            String action;
            do {
                CONTACT_BOOK.listContacts();
                TERMINAL.showListMenuEnterAction();
                switch (action = TERMINAL.getUserInput().toUpperCase()) {
                    case "":
                    case "NUMBER":
                        try {
                            contactById(TERMINAL.getUserInput());
                            break;
                        } catch (NumberFormatException | IndexOutOfBoundsException e) {
                            TERMINAL.showWrongInput();
                            break;
                        }
                    case "BACK":
                        break;
                    default:
                        try {
                            contactById(action);
                        } catch (NumberFormatException | IndexOutOfBoundsException e) {
                            TERMINAL.showWrongInput();
                            doNotStop = true;
                        }
                }
            } while (doNotStop);
        }
    }

    private void contactById(String userInputId) {
        int contactId = Integer.parseInt(userInputId) - 1;
        commandShowContact(contactId);
        openContactMenu(contactId);
    }

    private void commandShowContact(int contactId) {
        Command commandShow = new showCommand(CONTACT_BOOK, contactId);
        INVOKER.setCommand(commandShow);
        INVOKER.execute();
    }

    private void openContactMenu(int contactId) {
        boolean doNotStop = true;
        do {
            TERMINAL.showContactMenuEnterAction();
            switch (TERMINAL.getUserInput().toUpperCase()) {
                case "EDIT":
                    Contact contact = CONTACT_BOOK.getContactById(contactId);
                    contact.showEditableFields();
                    String[] editableFields = contact.getEditableFields();
                    String fieldForUpdate = TERMINAL.getUserInput().toUpperCase();
                    if (Arrays.asList(editableFields).contains(fieldForUpdate)) {
                        commandUpdate(contactId, fieldForUpdate);
                        commandShowContact(contactId);
                    } else {
                        TERMINAL.showWrongInput();
                    }
                    break;
                case "DELETE":
                    commandDelete(contactId);
                    doNotStop = false;
                    break;
                case "MENU":
                    doNotStop = false;
                    break;
                default:
                    TERMINAL.showWrongInput();
            }
        } while (doNotStop);
    }

    private void commandUpdate(int contactId, String fieldForUpdate) {
        TERMINAL.showEnterField(fieldForUpdate.toLowerCase());
        Command commandUpdate = new updateCommand(CONTACT_BOOK, contactId, fieldForUpdate, TERMINAL.getUserInput());
        INVOKER.setCommand(commandUpdate);
        INVOKER.execute();
    }

    private void commandDelete(int contactId) {
        Command commandDelete = new deleteCommand(CONTACT_BOOK, contactId);
        INVOKER.setCommand(commandDelete);
        INVOKER.execute();
    }

    private void search() {
        if (0 == CONTACT_BOOK.getContactSize()) {
            TERMINAL.showNoRecordsToSearch();
        } else {
            TERMINAL.showEnterSearch();
            commandSearch();
            openSearchMenu();
        }
    }

    private void commandSearch() {
        Command commandSearch = new searchCommand(CONTACT_BOOK, TERMINAL.getUserInput());
        INVOKER.setCommand(commandSearch);
        INVOKER.execute();
    }

    private void openSearchMenu() {
        boolean doNotExit = false;
        String action;
        do {
            TERMINAL.showSearchEnterAction();
            switch (action = TERMINAL.getUserInput().toUpperCase()) {
                case "":
                case "NUMBER":
                    contactById(TERMINAL.getUserInput());
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
                        TERMINAL.showWrongInput();
                        doNotExit = true;
                    }
            }

        } while (doNotExit);

    }
}
