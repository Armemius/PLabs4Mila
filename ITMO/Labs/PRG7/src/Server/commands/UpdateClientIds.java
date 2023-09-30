package Server.commands;

import Common.commands.Command;
import Common.consoles.Console;
import Server.managers.DBManagers.TableCollectionManager;
import Server.managers.collectionManagers.CollectionManager;

public class UpdateClientIds extends Command {
    private final CollectionManager collectionManager;
    private final TableCollectionManager tableCollectionManager;

    public UpdateClientIds(Console console, CollectionManager collectionManager, TableCollectionManager tableCollectionManager) {
        super("updateClientIds", "update ids set on the com.lunghr.lab6.client module", console);
        this.collectionManager = collectionManager;
        this.console = console;
        this.tableCollectionManager = tableCollectionManager;
    }

    @Override
    public void execute(String[] strings) {
        console.writeStr(tableCollectionManager.getClientIds(Integer.parseInt(strings[0])));
    }
}

