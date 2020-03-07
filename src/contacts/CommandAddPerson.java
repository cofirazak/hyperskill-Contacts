package contacts;

class CommandAddPerson implements Command {
    private ContactBook receiver;

    public void setReceiver(ContactBook receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.addPerson();
    }
}
