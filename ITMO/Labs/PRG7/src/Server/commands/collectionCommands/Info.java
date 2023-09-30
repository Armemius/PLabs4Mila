package Server.commands.collectionCommands;

import Common.commands.Command;
import Common.consoles.Console;
import Common.exceptions.WrongArgumentsException;
import Server.managers.collectionManagers.CollectionManager;

public class Info extends Command {
    private final CollectionManager collectionManager;

    public Info(CollectionManager collectionManager, Console console) {
        super("info", "collection information output ", console);
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] strings) {
        try {
            if (strings.length != 1) {
                throw new WrongArgumentsException();
            }
            collectionManager.getInfo();
        } catch (WrongArgumentsException e) {
            console.writeStr(e + ": info command doesn't need additional arguments");
        }
    }
}
