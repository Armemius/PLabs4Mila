package Server.commands.collectionCommands;

import Common.commands.CompoundCommand;
import Common.consoles.Console;
import Common.models.SpaceMarine;
import Server.managers.DBManagers.TableCollectionManager;
import Server.managers.collectionManagers.CollectionManager;

public class Add extends CompoundCommand {
    private final CollectionManager collectionManager;
    private SpaceMarine spaceMarine;
    private final TableCollectionManager tableCollectionManager;

    // update on description
    public Add(Console console, CollectionManager collectionManager, TableCollectionManager tableCollectionManager) {
        super("add", "add an element to the collection (u can also interrupt input manual by \"end\" command)", console);
        this.collectionManager = collectionManager;
        this.console = console;
        this.tableCollectionManager = tableCollectionManager;
    }

    @Override
    public void setSpaceMarine(SpaceMarine spaceMarine) {
        this.spaceMarine = spaceMarine;
    }

    @Override
    public void execute(String[] strings) {
        tableCollectionManager.insertSpaceMarine(spaceMarine, Integer.parseInt(strings[0]));
        collectionManager.refreshMarines(tableCollectionManager);
    }
}