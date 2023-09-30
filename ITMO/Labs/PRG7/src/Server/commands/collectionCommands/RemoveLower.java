package Server.commands.collectionCommands;

import Common.commands.CompoundCommand;
import Common.consoles.Console;
import Common.exceptions.WrongArgumentsException;
import Common.models.SpaceMarine;
import Server.managers.DBManagers.TableCollectionManager;
import Server.managers.collectionManagers.CollectionManager;

public class RemoveLower extends CompoundCommand {
    private final CollectionManager collectionManager;
    private SpaceMarine spaceMarine;
    private final TableCollectionManager tableCollectionManager;

    public RemoveLower(CollectionManager collectionManager, Console console, TableCollectionManager tableCollectionManager) {
        super("remove_lower", "remove items from the collection with a lower health value than the specified one", console);
        this.collectionManager = collectionManager;
        this.tableCollectionManager = tableCollectionManager;
    }

    @Override
    public void setSpaceMarine(SpaceMarine spaceMarine) {
        this.spaceMarine = spaceMarine;
    }

    @Override
    public void execute(String[] strings) {
        if (strings.length != 1) {
            try {
                throw new WrongArgumentsException();
            } catch (WrongArgumentsException e) {
                console.writeStr(e.toString());
            }
        }
        tableCollectionManager.removeLower(spaceMarine.getHealth(), Integer.parseInt(strings[0]));
        collectionManager.refreshMarines(tableCollectionManager);
    }
}
