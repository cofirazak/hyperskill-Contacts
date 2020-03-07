package contacts;

public class CommandShow implements Command {
    private ContactBook receiver;
    private int contactId;

    public void setReceiver(ContactBook receiver) {
        this.receiver = receiver;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    @Override
    public void execute() {
        receiver.showContact(contactId);
    }
}
