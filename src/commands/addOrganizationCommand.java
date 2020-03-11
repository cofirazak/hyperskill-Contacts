package commands;

import receivers.ContactBook;

public class addOrganizationCommand implements Command {
    private ContactBook receiver;

    private addOrganizationCommand() {
    }

    public addOrganizationCommand(ContactBook receiver) {
        this.receiver = receiver;
    }

    @Override
    public final void execute() {
        receiver.addOrganization();
        receiver.serialize();
    }
}
