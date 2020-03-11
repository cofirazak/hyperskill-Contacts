package contactsIO;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ConsoleTerminal implements Terminal {
    @Override
    public final String getUserInput() {
        try (final Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8)) {
            return scanner.nextLine();
        }
    }

    @Override
    public final void showMainMenuEnterAction() {
        System.out.println();
        System.out.print("[menu] Enter action (add, list, search, count, exit): ");
    }

    @Override
    public final void showAddMenuEnterType() {
        System.out.println();
        System.out.print("[add] Enter the type (person, organization) or back: ");
    }

    @Override
    public final void showWrongInput() {
        System.out.println("Wrong input entered. Try again...");
    }

    @Override
    public final void showContactAdded() {
        System.out.println("Contact added.");
    }

    @Override
    public final void showCantSaveContacts() {
        System.out.println("Couldn't save contacts.");
    }

    @Override
    public final void showListMenuEnterAction() {
        System.out.println();
        System.out.print("[list] Enter action ([number], back): ");
    }

    @Override
    public final void showListItem(int index, String name, String surname) {
        System.out.printf("%d. %s %s", index, name, surname);
        System.out.println();
    }

    @Override
    public final void showListItem(int index, String orgName) {
        System.out.printf("%d. %s", index, orgName);
        System.out.println();
    }

    @Override
    public final void showContact(String contactStr) {
        System.out.println(contactStr);
    }

    @Override
    public final void showContactMenuEnterAction() {
        System.out.println();
        System.out.print("[contact] Enter action (edit, delete, menu): ");
    }

    @Override
    public final void showNoContacts() {
        System.out.println("No contacts to delete!");
    }

    @Override
    public final void showContactRemoved() {
        System.out.println("Contact deleted!");
    }

    @Override
    public final void showNoRecordsToShow() {
        System.out.println("No records to show!");
    }

    @Override
    public final void showPersonEditableFields() {
        System.out.println();
        System.out.println("Select a field (name, surname, birth, gender, number): ");
    }

    @Override
    public final void showOrgEditableFields() {
        System.out.println();
        System.out.print("Select a field (name, address, number): ");
    }

    @Override
    public final void showEnterField(String field) {
        System.out.printf("Enter %s: ", field);
    }

    @Override
    public final void showBadDate() {
        System.out.println("Bad date given!");
    }

    @Override
    public final void showBadGender() {
        System.out.println("Bad gender given!");
    }

    @Override
    public final void showWrongNumberFormat() {
        System.out.println("Wrong number format!");
    }

    @Override
    public final void showContactSaved() {
        System.out.println("Contact saved.");
    }

    @Override
    public final void showEnterSearch() {
        System.out.print("Enter search query: ");
    }

    @Override
    public final void showFoundResults(int size) {
        System.out.printf("Found %d results:", size);
        System.out.println();
    }

    @Override
    public final void showSearchEnterAction() {
        System.out.print("[search] Enter action ([number], back, again): ");
    }

    @Override
    public final void showNoRecordsToSearch() {
        System.out.println("No records to search!");
    }

    @Override
    public final void showCount(int size) {
        System.out.printf("The Phone Book has %d records.", size);
        System.out.println();
    }

    @Override
    public final void showFileLoaded(String givenFilename) {
        System.out.printf("Loaded contacts from file: %s", givenFilename);
        System.out.println();
    }

    @Override
    public final void showNewFileCreated(String givenFilename) {
        System.out.printf("No contacts loaded. New contacts will be saved to \"%s\"", givenFilename);
        System.out.println();
    }
}
