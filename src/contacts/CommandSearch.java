package contacts;

public class CommandSearch implements Command {
    private ContactBook receiver;
    private String searchString;

    final void setReceiver(ContactBook receiver) {
        this.receiver = receiver;
    }

    final void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    @Override
    public final void execute() {
        receiver.searchContact(searchString);
    }
}
