package Server.commands.collectionCommands;

import Common.commands.Command;
import Common.consoles.Console;
import Common.exceptions.WrongArgumentsException;
import Server.managers.collectionManagers.CollectionManager;

public class History extends Command {
    private final CollectionManager collectionManager;

    public History(CollectionManager collectionManager, Console console) {
        super("history", "print the last 11 commands", console);
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] strings) {
        try {
            if (strings.length != 1) {
                throw new WrongArgumentsException();
            }
            collectionManager.getHistory();
        } catch (WrongArgumentsException e) {
            console.writeStr(e.toString() + ": history command doesn't need additional arguments");
        }
    }
}