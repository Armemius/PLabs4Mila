package Server.commands.collectionCommands;

import Common.commands.CompoundCommand;
import Common.consoles.Console;
import Common.exceptions.WrongArgumentsException;
import Common.models.SpaceMarine;
import Server.managers.DBManagers.TableCollectionManager;
import Server.managers.collectionManagers.CollectionManager;

public class AddIfMax extends CompoundCommand {
    private final CollectionManager collectionManager;
    private SpaceMarine spaceMarine;
    private final TableCollectionManager tableCollectionManager;

    public AddIfMax (CollectionManager collectionManager, Console console, TableCollectionManager tableCollectionManager){
        super ("add_if_max", "adds a new item to the collection if its health field is greater than the maximum " +
                "health of the collection", console);
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
        tableCollectionManager.addIfMax(spaceMarine, Integer.parseInt(strings[0]));
        collectionManager.setCollection(tableCollectionManager.readSpaceMarines());
    }
}

