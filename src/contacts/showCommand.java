package contacts;

public class showCommand implements Command {
    private ContactBook receiver;
    private int contactId;

    private showCommand() {
    }

    showCommand(ContactBook receiver, int contactId) {
        this.receiver = receiver;
        this.contactId = contactId;
    }

    @Override
    public final void execute() {
        receiver.showContact(contactId);
    }
}
