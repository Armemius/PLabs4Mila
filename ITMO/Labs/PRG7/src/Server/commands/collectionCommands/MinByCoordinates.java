package Server.commands.collectionCommands;

import Common.commands.Command;
import Common.consoles.Console;
import Common.exceptions.WrongArgumentsException;
import Server.managers.collectionManagers.CollectionManager;

public class MinByCoordinates extends Command {
    private final CollectionManager collectionManager;

    public MinByCoordinates(CollectionManager collectionManager, Console console) {
        super("min_by_coordinates", "finding the element with the smallest value of the coordinates field", console);
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] strings) {
        try {
            for (String str : strings){
                System.out.println(str);
            }
            if (strings.length != 1) {
                throw new WrongArgumentsException();
            }
            collectionManager.findMinElementByCoordinates();
        } catch (WrongArgumentsException e) {
            console.writeStr(e + ": min_by_coordinates command doesn't need additional arguments");
        }
    }
}
