package Server.Commands;

import Common.Commands.Command;
import Common.Consoles.Console;
import Common.Exceptions.WrongArgumentsException;
import Server.Managers.CollectionManager;

public class RemoveById extends Command {
    private CollectionManager collectionManager;

    public RemoveById (Console console, CollectionManager collectionManager){
        super("remove_by_id", "removing a collection by a given id", console);
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] strings) {
        try{
            if (strings.length != 1){
                throw new WrongArgumentsException();
            } if (isInteger(strings[0])) {
                int id = Integer.parseInt(strings[0]);
                collectionManager.removeElementById(id);
            }else{
                console.writeStr("The argument must be an integer");
            }
        }catch (WrongArgumentsException e){
            console.writeStr(e.toString()+": remove_by_id command requires only one id argument (int number)");
        }
    }

    private boolean isInteger(String string){
        Integer tmp;
        try {
            tmp = Integer.parseInt(string);
        }catch (NumberFormatException e){
            return false;
        }return true;
    }
}
