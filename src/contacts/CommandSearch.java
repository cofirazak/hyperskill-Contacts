package contacts;

public class CommandSearch implements Command {
    private ContactBook receiver;
    private String searchString;

    private CommandSearch() {
    }

    CommandSearch(ContactBook receiver, String searchString) {
        this.receiver = receiver;
        this.searchString = searchString;
    }

    @Override
    public final void execute() {
        receiver.searchContact(searchString);
    }
}
