package contacts;

class CommandAddOrganization implements Command {
    private ContactBook receiver;

    final void setReceiver(ContactBook receiver) {
        this.receiver = receiver;
    }

    @Override
    public final void execute() {
        receiver.addOrganization();
        receiver.serializeContacts();
    }
}
