package contacts;

class addPersonCommand implements Command {
    private ContactBook receiver;

    private addPersonCommand() {
    }

    addPersonCommand(ContactBook receiver) {
        this.receiver = receiver;
    }

    @Override
    public final void execute() {
        receiver.addPerson();
        receiver.serialize();
    }
}
