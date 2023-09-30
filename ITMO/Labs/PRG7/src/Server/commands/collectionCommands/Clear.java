package Server.commands.collectionCommands;

import Common.commands.Command;
import Common.consoles.Console;
import Common.exceptions.WrongArgumentsException;
import Server.managers.DBManagers.TableCollectionManager;
import Server.managers.collectionManagers.CollectionManager;

public class Clear extends Command {
    private final CollectionManager collectionManager;
    private final TableCollectionManager tableCollectionManager;

    public Clear(CollectionManager collectionManager, Console console, TableCollectionManager tableCollectionManager) {
        super("clear", "collection cleanup", console);
        this.collectionManager = collectionManager;
        this.tableCollectionManager = tableCollectionManager;
    }

    @Override
    public void execute(String[] strings) {
        try {
            if (strings.length != 1) {
                throw new WrongArgumentsException();
            }
            tableCollectionManager.clearObjects(Integer.parseInt(strings[0]));
            collectionManager.setCollection(tableCollectionManager.readSpaceMarines());
            console.writeStr("Collection was cleared");
        } catch (WrongArgumentsException e) {
            console.writeStr(e.toString() + ": clear command doesn't need additional arguments");
        }
    }
}
