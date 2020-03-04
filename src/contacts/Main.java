package contacts;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Contact book console application.
 * A program to create contacts (like on the mobile phone) and search for people or organizations by name.
 */
public class Main {
    /**
     * Entry point to Contact book console application.
     *
     * @param args list of arguments form console.
     */
    public static void main(String[] args) {
        ContactBook contactBook = new ContactBook();
        loadContacts(args, contactBook);
        contactBook.openMainMenu();
    }

    /**
     * Loads contacts from file by filename if given in 1st argument from console.
     *
     * @param args list of arguments form console.
     * @param contactBook instance of ContactBook.
     */
    private static void loadContacts(String[] args, ContactBook contactBook) {
        if (args.length != 0) {
            String givenFilename = args[0];
            contactBook.setFilename(givenFilename);
            try {
                ArrayList<Contact> contacts = (ArrayList<Contact>) SerializationUtils.deserialize(givenFilename);
                contactBook.setContacts(contacts);
                System.out.println("Loaded contacts from file: " + givenFilename);
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("No contacts loaded. New contacts will be saved to \"" + givenFilename + "\"");
            }
        }
    }
}
