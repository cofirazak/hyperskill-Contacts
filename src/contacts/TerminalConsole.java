package contacts;


import java.util.Scanner;

public class TerminalConsole implements TerminalCommon {
    @Override
    public String getUserInput(Scanner scanner) {
        return scanner.nextLine();
    }

    @Override
    public void showMainMenuEnterAction() {
        System.out.print("\n[menu] Enter action (add, list, search, count, exit): ");
    }

    @Override
    public void showAddMenuEnterType() {
        System.out.print("\n[add] Enter the type (person, organization) or back: ");
    }

    @Override
    public void showWrongInput() {
        System.out.println("Wrong input entered. Try again...");
    }

    @Override
    public void showContactAdded() {
        System.out.println("Contact added.");
    }

    @Override
    public void showCantSaveContacts() {
        System.out.println("Couldn't save contacts.");
    }

    @Override
    public void showListMenuEnterAction() {
        System.out.print("\n[list] Enter action ([number], back): ");
    }

    @Override
    public void showListItem(int index, String name, String surname) {
        System.out.printf("%d. %s %s\n", index, name, surname);
    }

    @Override
    public void showListItem(int index, String orgName) {
        System.out.printf("%d. %s\n", index, orgName);
    }

    @Override
    public void showContact(String contactStr) {
        System.out.println(contactStr);
    }

    @Override
    public void showContactMenuEnterAction() {
        System.out.print("\n[contact] Enter action (edit, delete, menu): ");
    }

    @Override
    public void showNoContacts() {
        System.out.println("No contacts to delete!");
    }

    @Override
    public void showContactRemoved() {
        System.out.println("Contact deleted!");
    }

    @Override
    public void showNoRecordsToShow() {
        System.out.println("No records to show!");
    }

    @Override
    public void showPersonEditableFields() {
        System.out.println("\nSelect a field (name, surname, birth, gender, number): ");
    }

    @Override
    public void showOrgEditableFields() {
        System.out.print("\nSelect a field (name, address, number): ");
    }

    @Override
    public void showEnterField(String field) {
        System.out.printf("Enter %s: ", field);
    }

    @Override
    public void showBadDate() {
        System.out.println("Bad date given!");
    }

    @Override
    public void showWrongNumberFormat() {
        System.out.println("Wrong number format!");
    }

    @Override
    public void showContactSaved() {
        System.out.println("Contact saved.");
    }

    @Override
    public void showEnterSearch() {
        System.out.print("Enter search query: ");
    }

    @Override
    public void showFoundResults(int size) {
        System.out.printf("Found %d results: \n", size);
    }

    @Override
    public void showSearchEnterAction() {
        System.out.print("[search] Enter action ([number], back, again): ");
    }

    @Override
    public void showNoRecordsToSearch() {
        System.out.println("No records to search!");
    }

    @Override
    public void showCount(int size) {
        System.out.printf("The Phone Book has %d records.\n", size);
    }
}
