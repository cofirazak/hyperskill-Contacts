package contacts;

class addOrganizationCommand implements Command {
    private ContactBook receiver;

    private addOrganizationCommand() {
    }

    addOrganizationCommand(ContactBook receiver) {
        this.receiver = receiver;
    }

    @Override
    public final void execute() {
        receiver.addOrganization();
        receiver.serialize();
    }
}
