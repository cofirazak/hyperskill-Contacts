package contacts;

public class CommandDelete implements Command {
    private ContactBook receiver;
    private int contactId;

    private CommandDelete() {
    }

    CommandDelete(ContactBook receiver, int contactId) {
        this.receiver = receiver;
        this.contactId = contactId;
    }

    @Override
    public final void execute() {
        receiver.deleteContact(contactId);
    }
}
