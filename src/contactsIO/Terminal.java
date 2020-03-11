package contactsIO;

public interface Terminal {

    String getUserInput();

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

    void showBadGender();

    void showWrongNumberFormat();

    void showContactSaved();

    void showEnterSearch();

    void showFoundResults(int size);

    void showSearchEnterAction();

    void showNoRecordsToSearch();

    void showCount(int size);

    void showFileLoaded(String givenFilename);

    void showNewFileCreated(String givenFilename);
}
