package contacts;

class Invoker {
    private Command command;

    final void setCommand(Command command) {
        this.command = command;
    }

    final void execute() {
        command.execute();
    }
}
