package commands;

import receivers.ContactBook;

public class addPersonCommand implements Command {
    private ContactBook receiver;

    private addPersonCommand() {
    }

    public addPersonCommand(ContactBook receiver) {
        this.receiver = receiver;
    }

    @Override
    public final void execute() {
        receiver.addPerson();
        receiver.serialize();
    }
}
