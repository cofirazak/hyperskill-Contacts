package contacts;

public class CommandUpdate implements Command {
    private ContactBook receiver;
    private int contactId;
    private String fieldForUpdate;
    private String newValue;

    public void setReceiver(ContactBook receiver) {
        this.receiver = receiver;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public void setFieldForUpdate(String fieldForUpdate) {
        this.fieldForUpdate = fieldForUpdate;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    @Override
    public void execute() {
        receiver.updateContact(contactId, fieldForUpdate, newValue);
    }
}
