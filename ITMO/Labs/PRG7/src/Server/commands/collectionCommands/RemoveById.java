package Server.commands.collectionCommands;

import Common.commands.Command;
import Common.consoles.Console;
import Common.exceptions.WrongArgumentsException;
import Server.managers.DBManagers.TableCollectionManager;
import Server.managers.collectionManagers.CollectionManager;

public class RemoveById extends Command {
    private final CollectionManager collectionManager;
    private final TableCollectionManager tableCollectionManager;

    public RemoveById(Console console, CollectionManager collectionManager, TableCollectionManager tableCollectionManager) {
        super("remove_by_id", "removing a collection by a given id", console);
        this.collectionManager = collectionManager;
        this.tableCollectionManager = tableCollectionManager;
    }

    @Override
    public void execute(String[] strings) {
        try {
            if (strings.length != 2) {
                throw new WrongArgumentsException();
            }
            if (isInteger(strings[0])) {
                tableCollectionManager.removeById(Integer.parseInt(strings[1]), Integer.parseInt(strings[0]));
                collectionManager.refreshMarines(tableCollectionManager);
            } else {
                console.writeStr("The argument must be an integer");
            }
        } catch (WrongArgumentsException e) {
            console.writeStr(e + ": remove_by_id command requires only one id argument (int number)");
        }
    }

    private boolean isInteger(String string) {
        try {
            Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
