package contacts;

import app.Contacts;

import java.time.LocalDateTime;

abstract class ContactFactory {
    public final Contact getContact(ContactType type) {
        Contact contact = createContact(type);
        Contacts.TERMINAL.showEnterField("phone number");
        contact.setNumber(contact.filterPhoneNumber(Contacts.TERMINAL.getUserInput()));
        contact.setCreationDateTime(LocalDateTime.now().withNano(0));
        contact.setLastEditDateTime(LocalDateTime.now().withNano(0));
        return contact;
    }

    abstract Contact createContact(ContactType type);
}
