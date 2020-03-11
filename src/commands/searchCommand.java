package commands;

import receivers.ContactBook;

public class searchCommand implements Command {
    private ContactBook receiver;
    private String searchString;

    private searchCommand() {
    }

    public searchCommand(ContactBook receiver, String searchString) {
        this.receiver = receiver;
        this.searchString = searchString;
    }

    @Override
    public final void execute() {
        receiver.searchContact(searchString);
    }
}
