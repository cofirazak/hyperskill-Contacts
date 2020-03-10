package contacts;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Client {
    static final TerminalCommon TERMINAL_COMMON = new TerminalConsole();
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
                TERMINAL_COMMON.showFileLoaded(givenFilename);
            } catch (FileNotFoundException | ClassNotFoundException e) {
                TERMINAL_COMMON.showNewFileCreated(givenFilename);
            } catch (IOException e) {
                TERMINAL_COMMON.showNewFileCreated(givenFilename);
            }
        }
    }

    private void openMainMenuDialog() {
        boolean doNotExit = true;
        do {
            TERMINAL_COMMON.showMainMenuEnterAction();
            switch (TERMINAL_COMMON.getUserInput().toUpperCase()) {
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
                    TERMINAL_COMMON.showCount(CONTACT_BOOK.getContactSize());
                    break;
                case "EXIT":
                    doNotExit = false;
                    break;
                default:
                    TERMINAL_COMMON.showWrongInput();
            }
        } while (doNotExit);
    }

    private void openAddMenu() {
        boolean doNotStop = false;
        do {
            TERMINAL_COMMON.showAddMenuEnterType();
            switch (TERMINAL_COMMON.getUserInput().toUpperCase()) {
                case "PERSON":
                    commandAddPerson();
                    break;
                case "ORGANIZATION":
                    commandAddOrganisation();
                    break;
                case "BACK":
                    break;
                default:
                    TERMINAL_COMMON.showWrongInput();
                    doNotStop = true;
            }
        } while (doNotStop);
    }

    private void commandAddPerson() {
        Command addPerson = new CommandAddPerson(CONTACT_BOOK);
        INVOKER.setCommand(addPerson);
        INVOKER.execute();
    }

    private void commandAddOrganisation() {
        Command addOrganization = new CommandAddOrganization(CONTACT_BOOK);
        INVOKER.setCommand(addOrganization);
        INVOKER.execute();
    }

    private void openListMenu() {
        if (0 == CONTACT_BOOK.getContactSize()) {
            TERMINAL_COMMON.showNoRecordsToShow();
        } else {
            boolean doNotStop = false;
            String action;
            do {
                CONTACT_BOOK.listContacts();
                TERMINAL_COMMON.showListMenuEnterAction();
                switch (action = TERMINAL_COMMON.getUserInput().toUpperCase()) {
                    case "":
                    case "NUMBER":
                        try {
                            contactById(TERMINAL_COMMON.getUserInput());
                            break;
                        } catch (NumberFormatException | IndexOutOfBoundsException e) {
                            TERMINAL_COMMON.showWrongInput();
                            break;
                        }
                    case "BACK":
                        break;
                    default:
                        try {
                            contactById(action);
                        } catch (NumberFormatException | IndexOutOfBoundsException e) {
                            TERMINAL_COMMON.showWrongInput();
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
        Command commandShow = new CommandShow(CONTACT_BOOK, contactId);
        INVOKER.setCommand(commandShow);
        INVOKER.execute();
    }

    private void openContactMenu(int contactId) {
        boolean doNotStop = true;
        do {
            TERMINAL_COMMON.showContactMenuEnterAction();
            switch (TERMINAL_COMMON.getUserInput().toUpperCase()) {
                case "EDIT":
                    Contact contact = CONTACT_BOOK.getContactById(contactId);
                    contact.showEditableFields();
                    String[] editableFields = contact.getEditableFields();
                    String fieldForUpdate = TERMINAL_COMMON.getUserInput().toUpperCase();
                    if (Arrays.asList(editableFields).contains(fieldForUpdate)) {
                        commandUpdate(contactId, fieldForUpdate);
                        commandShowContact(contactId);
                    } else {
                        TERMINAL_COMMON.showWrongInput();
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
                    TERMINAL_COMMON.showWrongInput();
            }
        } while (doNotStop);
    }

    private void commandUpdate(int contactId, String fieldForUpdate) {
        TERMINAL_COMMON.showEnterField(fieldForUpdate.toLowerCase());
        Command commandUpdate = new CommandUpdate(CONTACT_BOOK, contactId, fieldForUpdate, TERMINAL_COMMON.getUserInput());
        INVOKER.setCommand(commandUpdate);
        INVOKER.execute();
    }

    private void commandDelete(int contactId) {
        Command commandDelete = new CommandDelete(CONTACT_BOOK, contactId);
        INVOKER.setCommand(commandDelete);
        INVOKER.execute();
    }

    private void search() {
        if (0 == CONTACT_BOOK.getContactSize()) {
            TERMINAL_COMMON.showNoRecordsToSearch();
        } else {
            TERMINAL_COMMON.showEnterSearch();
            commandSearch();
            openSearchMenu();
        }
    }

    private void commandSearch() {
        Command commandSearch = new CommandSearch(CONTACT_BOOK, TERMINAL_COMMON.getUserInput());
        INVOKER.setCommand(commandSearch);
        INVOKER.execute();
    }

    private void openSearchMenu() {
        boolean doNotExit = false;
        String action;
        do {
            TERMINAL_COMMON.showSearchEnterAction();
            switch (action = TERMINAL_COMMON.getUserInput().toUpperCase()) {
                case "":
                case "NUMBER":
                    contactById(TERMINAL_COMMON.getUserInput());
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
                        TERMINAL_COMMON.showWrongInput();
                        doNotExit = true;
                    }
            }

        } while (doNotExit);

    }
}
