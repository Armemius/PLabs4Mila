package Server.commands.collectionCommands;

import Common.commands.Command;
import Common.consoles.Console;
import Common.exceptions.WrongArgumentsException;
import Server.managers.collectionManagers.CollectionManager;

public class PrintFieldDescendingHealth extends Command {
    private final CollectionManager collectionManager;

    public PrintFieldDescendingHealth(CollectionManager collectionManager, Console console) {
        super("print_field_descending_health", "print the values of all health fields in descending order", console);
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] strings) {
        try {
            if (strings.length != 1) {
                throw new WrongArgumentsException();
            }
            collectionManager.printSortedHealthFields();
        } catch (WrongArgumentsException e) {
            console.writeStr(e + ": print_field_descending_health command doesn't need additional arguments");
        }

    }
}
