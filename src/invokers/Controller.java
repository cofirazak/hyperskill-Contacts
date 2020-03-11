package invokers;

import commands.Command;

public class Controller {
    private Command command;

    public final void setCommand(Command command) {
        this.command = command;
    }

    public final void execute() {
        command.execute();
    }
}
