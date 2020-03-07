package contacts;

public class CommandSearch implements Command {
    private ContactBook receiver;
    private String searchString;

    public void setReceiver(ContactBook receiver) {
        this.receiver = receiver;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    @Override
    public void execute() {
        receiver.searchContact(searchString);
    }
}
