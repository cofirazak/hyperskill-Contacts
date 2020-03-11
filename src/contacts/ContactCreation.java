package contacts;

class ContactCreation extends ContactFactory {
    @Override
    final Contact createContact(ContactType type) {
        Contact contact = null;
        if (ContactType.PERSON == type) {
            Person person = new Person();
            Client.TERMINAL.showEnterField("name");
            person.setName(Client.TERMINAL.getUserInput());
            Client.TERMINAL.showEnterField("surname");
            person.setSurname(Client.TERMINAL.getUserInput());
            Client.TERMINAL.showEnterField("birth date(yyyy-MM-dd)");
            person.setBirthDate(person.tryCastStrToDate(Client.TERMINAL.getUserInput()));
            Client.TERMINAL.showEnterField("gender(M|F)");
            person.setGender(person.tryCastStrToGender(Client.TERMINAL.getUserInput()));
            contact = person;
        } else if (ContactType.ORGANIZATION == type) {
            Organization organization = new Organization();
            Client.TERMINAL.showEnterField("name");
            organization.setName(Client.TERMINAL.getUserInput());
            Client.TERMINAL.showEnterField("address");
            organization.setAddress(Client.TERMINAL.getUserInput());
            contact = organization;
        }
        return contact;
    }
}
