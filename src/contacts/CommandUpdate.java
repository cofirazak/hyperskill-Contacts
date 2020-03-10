package contacts;

public class CommandUpdate implements Command {
    private ContactBook receiver;
    private int contactId;
    private String fieldForUpdate;
    private String newValue;

    private CommandUpdate() {
    }

    CommandUpdate(ContactBook receiver, int contactId, String fieldForUpdate, String newValue) {
        this.receiver = receiver;
        this.contactId = contactId;
        this.fieldForUpdate = fieldForUpdate;
        this.newValue = newValue;
    }

    @Override
    public final void execute() {
        receiver.updateContact(contactId, fieldForUpdate, newValue);
        receiver.serialize();
    }
}
