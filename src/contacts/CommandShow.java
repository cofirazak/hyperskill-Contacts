package contacts;

public class CommandShow implements Command {
    private ContactBook receiver;
    private int contactId;

    private CommandShow() {
    }

    CommandShow(ContactBook receiver, int contactId) {
        this.receiver = receiver;
        this.contactId = contactId;
    }

    @Override
    public final void execute() {
        receiver.showContact(contactId);
    }
}
