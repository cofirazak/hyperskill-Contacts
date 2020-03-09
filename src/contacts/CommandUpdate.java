package contacts;

public class CommandUpdate implements Command {
    private ContactBook receiver;
    private int contactId;
    private String fieldForUpdate;
    private String newValue;

    final void setReceiver(ContactBook receiver) {
        this.receiver = receiver;
    }

    final void setContactId(int contactId) {
        this.contactId = contactId;
    }

    final void setFieldForUpdate(String fieldForUpdate) {
        this.fieldForUpdate = fieldForUpdate;
    }

    final void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    @Override
    public final void execute() {
        receiver.updateContact(contactId, fieldForUpdate, newValue);
        receiver.serializeContacts();
    }
}
