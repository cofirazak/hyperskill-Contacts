package contacts;

import java.time.LocalDateTime;

abstract class ContactFactory {
    final Contact getContact(ContactType type) {
        Contact contact = createContact(type);
        Client.TERMINAL.showEnterField("phone number");
        contact.setNumber(contact.filterPhoneNumber(Client.TERMINAL.getUserInput()));
        contact.setCreationDateTime(LocalDateTime.now().withNano(0));
        contact.setLastEditDateTime(LocalDateTime.now().withNano(0));
        return contact;
    }

    abstract Contact createContact(ContactType type);
}
