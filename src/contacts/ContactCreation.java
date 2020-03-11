package contacts;

import clients.ContactsApp;

public class ContactCreation extends ContactFactory {
    @Override
    final Contact createContact(ContactType type) {
        Contact contact = null;
        if (ContactType.PERSON == type) {
            PersonContact person = new PersonContact();
            ContactsApp.TERMINAL.showEnterField("name");
            person.setName(ContactsApp.TERMINAL.getUserInput());
            ContactsApp.TERMINAL.showEnterField("surname");
            person.setSurname(ContactsApp.TERMINAL.getUserInput());
            ContactsApp.TERMINAL.showEnterField("birth date(yyyy-MM-dd)");
            person.setBirthDate(person.tryCastStrToDate(ContactsApp.TERMINAL.getUserInput()));
            ContactsApp.TERMINAL.showEnterField("gender(M|F)");
            person.setGender(person.tryCastStrToGender(ContactsApp.TERMINAL.getUserInput()));
            contact = person;
        } else if (ContactType.ORGANIZATION == type) {
            OrganizationContact organization = new OrganizationContact();
            ContactsApp.TERMINAL.showEnterField("name");
            organization.setName(ContactsApp.TERMINAL.getUserInput());
            ContactsApp.TERMINAL.showEnterField("address");
            organization.setAddress(ContactsApp.TERMINAL.getUserInput());
            contact = organization;
        }
        return contact;
    }
}
