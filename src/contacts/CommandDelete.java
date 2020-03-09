package contacts;

public class CommandDelete implements Command {
    private ContactBook receiver;
    private int contactId;

    final void setReceiver(ContactBook receiver) {
        this.receiver = receiver;
    }

    final void setContactId(int contactId) {
        this.contactId = contactId;
    }

    @Override
    public final void execute() {
        receiver.deleteContact(contactId);
    }
}
