package contacts;

import app.Contacts;

public class ContactCreation extends ContactFactory {
    @Override
    final Contact createContact(ContactType type) {
        Contact contact = null;
        if (ContactType.PERSON == type) {
            PersonContact person = new PersonContact();
            Contacts.TERMINAL.showEnterField("name");
            person.setName(Contacts.TERMINAL.getUserInput());
            Contacts.TERMINAL.showEnterField("surname");
            person.setSurname(Contacts.TERMINAL.getUserInput());
            Contacts.TERMINAL.showEnterField("birth date(yyyy-MM-dd)");
            person.setBirthDate(person.tryCastStrToDate(Contacts.TERMINAL.getUserInput()));
            Contacts.TERMINAL.showEnterField("gender(M|F)");
            person.setGender(person.tryCastStrToGender(Contacts.TERMINAL.getUserInput()));
            contact = person;
        } else if (ContactType.ORGANIZATION == type) {
            OrganizationContact organization = new OrganizationContact();
            Contacts.TERMINAL.showEnterField("name");
            organization.setName(Contacts.TERMINAL.getUserInput());
            Contacts.TERMINAL.showEnterField("address");
            organization.setAddress(Contacts.TERMINAL.getUserInput());
            contact = organization;
        }
        return contact;
    }
}
