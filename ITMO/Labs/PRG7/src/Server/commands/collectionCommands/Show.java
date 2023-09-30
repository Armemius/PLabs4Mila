package Server.commands.collectionCommands;

import Common.commands.Command;
import Common.consoles.Console;
import Common.exceptions.WrongArgumentsException;
import Server.managers.collectionManagers.CollectionManager;

public class Show extends Command {
    private final CollectionManager collectionManager;

    public Show(CollectionManager collectionManager, Console console) {
        super("show", "collection items output", console);
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] strings) {
        try {
            if (strings.length != 1) {
                throw new WrongArgumentsException();
            }
            collectionManager.printCollection();
        } catch (WrongArgumentsException e) {
            console.writeStr(e + ": show command doesn't need additional arguments");
        }
    }
}
