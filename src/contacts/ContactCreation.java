package contacts;

class ContactCreation extends ContactFactory {
    @Override
    final Contact createContact(ContactType type) {
        Contact contact = null;
        if (ContactType.PERSON == type) {
            Person person = new Person();
            Client.TERMINAL_COMMON.showEnterField("name");
            person.setName(Client.TERMINAL_COMMON.getUserInput());
            Client.TERMINAL_COMMON.showEnterField("surname");
            person.setSurname(Client.TERMINAL_COMMON.getUserInput());
            Client.TERMINAL_COMMON.showEnterField("birth date(yyyy-MM-dd)");
            person.setBirthDate(person.tryCastStrToDate(Client.TERMINAL_COMMON.getUserInput()));
            Client.TERMINAL_COMMON.showEnterField("gender(M|F)");
            person.setGender(person.tryCastStrToGender(Client.TERMINAL_COMMON.getUserInput()));
            contact = person;
        } else if (ContactType.ORGANIZATION == type) {
            Organization organization = new Organization();
            Client.TERMINAL_COMMON.showEnterField("name");
            organization.setName(Client.TERMINAL_COMMON.getUserInput());
            Client.TERMINAL_COMMON.showEnterField("address");
            organization.setAddress(Client.TERMINAL_COMMON.getUserInput());
            contact = organization;
        }
        return contact;
    }
}
