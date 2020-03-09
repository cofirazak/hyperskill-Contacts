package contacts;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Client {
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
                ContactBook.TERMINAL_COMMON.showFileLoaded(givenFilename);
            } catch (FileNotFoundException | ClassNotFoundException e) {
                ContactBook.TERMINAL_COMMON.showNewFileCreated(givenFilename);
            } catch (IOException e) {
                ContactBook.TERMINAL_COMMON.showNewFileCreated(givenFilename);
            }
        }
    }

    private void openMainMenuDialog() {
        boolean doNotExit = true;
        do {
            ContactBook.TERMINAL_COMMON.showMainMenuEnterAction();
            switch (ContactBook.TERMINAL_COMMON.getUserInput().toUpperCase()) {
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
                    doNotExit = false;
                    break;
                default:
                    ContactBook.TERMINAL_COMMON.showWrongInput();
            }
        } while (doNotExit);
    }

    private void openAddMenu() {
        boolean doNotStop = false;
        do {
            ContactBook.TERMINAL_COMMON.showAddMenuEnterType();
            switch (ContactBook.TERMINAL_COMMON.getUserInput().toUpperCase()) {
                case "PERSON":
                    CommandAddPerson addPerson = new CommandAddPerson();
                    addPerson.setReceiver(CONTACT_BOOK);
                    INVOKER.setCommand(addPerson);
                    INVOKER.execute();
                    break;
                case "ORGANIZATION":
                    CommandAddOrganization addOrganization = new CommandAddOrganization();
                    addOrganization.setReceiver(CONTACT_BOOK);
                    INVOKER.setCommand(addOrganization);
                    INVOKER.execute();
                    break;
                case "BACK":
                    break;
                default:
                    ContactBook.TERMINAL_COMMON.showWrongInput();
                    doNotStop = true;
            }
        } while (doNotStop);
    }

    private void openListMenu() {
        if (0 == CONTACT_BOOK.getContactSize()) {
            ContactBook.TERMINAL_COMMON.showNoRecordsToShow();
        } else {
            boolean doNotStop = false;
            String action;
            do {
                CONTACT_BOOK.listContacts();
                ContactBook.TERMINAL_COMMON.showListMenuEnterAction();
                switch (action = ContactBook.TERMINAL_COMMON.getUserInput().toUpperCase()) {
                    case "":
                    case "NUMBER":
                        try {
                            contactById(ContactBook.TERMINAL_COMMON.getUserInput());
                            break;
                        } catch (NumberFormatException | IndexOutOfBoundsException e) {
                            ContactBook.TERMINAL_COMMON.showWrongInput();
                            break;
                        }
                    case "BACK":
                        break;
                    default:
                        try {
                            contactById(action);
                        } catch (NumberFormatException | IndexOutOfBoundsException e) {
                            ContactBook.TERMINAL_COMMON.showWrongInput();
                            doNotStop = true;
                        }
                }
            } while (doNotStop);
        }
    }

    private void search() {
        if (0 == CONTACT_BOOK.getContactSize()) {
            ContactBook.TERMINAL_COMMON.showNoRecordsToSearch();
        } else {
            ContactBook.TERMINAL_COMMON.showEnterSearch();
            CommandSearch commandSearch = new CommandSearch();
            commandSearch.setReceiver(CONTACT_BOOK);
            commandSearch.setSearchString(ContactBook.TERMINAL_COMMON.getUserInput());
            INVOKER.setCommand(commandSearch);
            INVOKER.execute();
            openSearchMenu();
        }
    }

    private void openSearchMenu() {
        boolean doNotExit = false;
        String action;
        do {
            ContactBook.TERMINAL_COMMON.showSearchEnterAction();
            switch (action = ContactBook.TERMINAL_COMMON.getUserInput().toUpperCase()) {
                case "":
                case "NUMBER":
                    contactById(ContactBook.TERMINAL_COMMON.getUserInput());
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
                        doNotExit = true;
                    }
            }

        } while (doNotExit);

    }

    private void contactById(String userInputId) {
        int contactId = Integer.parseInt(userInputId) - 1;
        showContactById(contactId);
        openContactMenu(contactId);
    }

    private void openContactMenu(int contactId) {
        boolean doNotStop = true;
        do {
            ContactBook.TERMINAL_COMMON.showContactMenuEnterAction();
            switch (ContactBook.TERMINAL_COMMON.getUserInput().toUpperCase()) {
                case "EDIT":
                    Contact contact = CONTACT_BOOK.getContactById(contactId);
                    contact.showEditableFields();
                    String[] editableFields = contact.getEditableFields();
                    String fieldForUpdate = ContactBook.TERMINAL_COMMON.getUserInput().toUpperCase();
                    if (Arrays.asList(editableFields).contains(fieldForUpdate)) {
                        update(contactId, fieldForUpdate);
                        showContactById(contactId);
                    } else {
                        ContactBook.TERMINAL_COMMON.showWrongInput();
                    }
                    break;
                case "DELETE":
                    delete(contactId);
                    doNotStop = false;
                    break;
                case "MENU":
                    doNotStop = false;
                    break;
                default:
                    ContactBook.TERMINAL_COMMON.showWrongInput();
            }
        } while (doNotStop);
    }

    private void delete(int contactId) {
        CommandDelete commandDelete = new CommandDelete();
        commandDelete.setReceiver(CONTACT_BOOK);
        commandDelete.setContactId(contactId);
        INVOKER.setCommand(commandDelete);
        INVOKER.execute();
    }

    private void update(int contactId, String fieldForUpdate) {
        CommandUpdate commandUpdate = new CommandUpdate();
        commandUpdate.setReceiver(CONTACT_BOOK);
        commandUpdate.setContactId(contactId);
        commandUpdate.setFieldForUpdate(fieldForUpdate);
        ContactBook.TERMINAL_COMMON.showEnterField(fieldForUpdate.toLowerCase());
        commandUpdate.setNewValue(ContactBook.TERMINAL_COMMON.getUserInput());
        INVOKER.setCommand(commandUpdate);
        INVOKER.execute();
    }

    private void showContactById(int contactId) {
        CommandShow commandShow = new CommandShow();
        commandShow.setReceiver(CONTACT_BOOK);
        commandShow.setContactId(contactId);
        INVOKER.setCommand(commandShow);
        INVOKER.execute();
    }
}
