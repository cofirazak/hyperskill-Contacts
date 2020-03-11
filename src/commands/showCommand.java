package commands;

import receivers.ContactBook;

public class showCommand implements Command {
    private ContactBook receiver;
    private int contactId;

    private showCommand() {
    }

    public showCommand(ContactBook receiver, int contactId) {
        this.receiver = receiver;
        this.contactId = contactId;
    }

    @Override
    public final void execute() {
        receiver.showContact(contactId);
    }
}
