package commands;

import receivers.ContactBook;

public class deleteCommand implements Command {
    private ContactBook receiver;
    private int contactId;

    private deleteCommand() {
    }

    public deleteCommand(ContactBook receiver, int contactId) {
        this.receiver = receiver;
        this.contactId = contactId;
    }

    @Override
    public final void execute() {
        if (receiver.deleteContact(contactId)) {
            receiver.serialize();
        }
    }
}
