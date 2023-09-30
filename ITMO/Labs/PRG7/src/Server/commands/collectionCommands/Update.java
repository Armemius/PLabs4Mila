package Server.commands.collectionCommands;

import Common.commands.CompoundCommand;
import Common.consoles.Console;
import Common.models.SpaceMarine;
import Server.managers.DBManagers.TableCollectionManager;
import Server.managers.collectionManagers.CollectionManager;

public class Update extends CompoundCommand {
    private SpaceMarine spaceMarine;
    private final CollectionManager collectionManager;
    private final TableCollectionManager tableCollectionManager;


    public Update(CollectionManager collectionManager, Console console, TableCollectionManager tableCollectionManager) {
        super("update", "updates a collection item with the given id", console);
        this.collectionManager = collectionManager;
        this.tableCollectionManager = tableCollectionManager;

    }

    @Override
    public void setSpaceMarine(SpaceMarine spaceMarine) {
        this.spaceMarine = spaceMarine;
    }

    @Override
    public void execute(String[] strings) {
        System.out.println(strings[0]);
        System.out.println(strings[1]);
        int id = Integer.parseInt(strings[0]);
        tableCollectionManager.updateElement(Integer.parseInt(strings[0]), spaceMarine);
        collectionManager.setCollection(tableCollectionManager.readSpaceMarines());
    }
}
