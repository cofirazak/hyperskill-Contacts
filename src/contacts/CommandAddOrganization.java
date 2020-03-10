package contacts;

class CommandAddOrganization implements Command {
    private ContactBook receiver;

    private CommandAddOrganization() {
    }

    CommandAddOrganization(ContactBook receiver) {
        this.receiver = receiver;
    }

    @Override
    public final void execute() {
        receiver.addOrganization();
        receiver.serialize();
    }
}
