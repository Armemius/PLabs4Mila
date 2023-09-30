package Server.commands.collectionCommands;

import Common.commands.Command;
import Common.consoles.Console;

public class Exit extends Command {

    public Exit(Console console) {
        super("exit", "end the program", console);
    }

    @Override
    public void execute(String[] strings) {
        System.out.println("Client was disconnected");

    }
}
