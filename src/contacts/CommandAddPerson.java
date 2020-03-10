package contacts;

class CommandAddPerson implements Command {
    private ContactBook receiver;

    private CommandAddPerson() {
    }

    CommandAddPerson(ContactBook receiver) {
        this.receiver = receiver;
    }

    @Override
    public final void execute() {
        receiver.addPerson();
        receiver.serialize();
    }
}
