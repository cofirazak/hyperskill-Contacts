package contacts;

import java.util.Scanner;

interface TerminalCommon {

    String getUserInput(Scanner scanner);

    void showMainMenuEnterAction();

    void showAddMenuEnterType();

    void showWrongInput();

    void showContactAdded();

    void showCantSaveContacts();

    void showListMenuEnterAction();

    void showNoRecordsToShow();

    void showListItem(int index, String name, String surname);

    void showListItem(int index, String orgName);

    void showContact(String contactStr);

    void showContactMenuEnterAction();

    void showNoContacts();

    void showContactRemoved();

    void showPersonEditableFields();

    void showOrgEditableFields();

    void showEnterField(String field);

    void showBadDate();

    void showWrongNumberFormat();

    void showContactSaved();

    void showEnterSearch();

    void showFoundResults(int size);

    void showSearchEnterAction();

    void showNoRecordsToSearch();

    void showCount(int size);
}
