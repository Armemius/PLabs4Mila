package Client.managers;

import Common.consoles.Console;
import Common.exceptions.EndInputException;
import Common.exceptions.NotFoundCommandException;
import Common.exceptions.StopInputException;
import Common.models.SpaceMarine;
import Common.utils.Message;

import java.util.ArrayList;
import java.util.List;

import static Client.managers.TypeCheckingManager.isInteger;

public class InputDataManager {
    private final Console console;
    private final SpaceMarineManager spaceMarineManager;
    private final List<String> listOfAvailableCommands = new ArrayList<>();
    private final List<String> listOfCompoundCommands = new ArrayList<>();
    private final List<String> listOfNoArgsCommands = new ArrayList<>();



    public InputDataManager(ClientManager clientManager) {
        spaceMarineManager = new SpaceMarineManager(clientManager.getConsole());
        this.console = clientManager.getConsole();
        listOfAvailableCommands.add("show");
        listOfAvailableCommands.add("clear");
        listOfAvailableCommands.add("info");
        listOfAvailableCommands.add("history");
        listOfAvailableCommands.add("help");
        listOfAvailableCommands.add("min_by_coordinates");
        listOfAvailableCommands.add("print_field_descending_health");
        listOfAvailableCommands.add("remove_by_id");
        listOfAvailableCommands.add("add");
        listOfAvailableCommands.add("add_if_max");
        listOfAvailableCommands.add("update");
        listOfAvailableCommands.add("updateClientIds");
        listOfAvailableCommands.add("remove_lower");
        listOfAvailableCommands.add("exit");
        listOfAvailableCommands.add("execute_script");

        listOfCompoundCommands.add("add");
        listOfCompoundCommands.add("add_if_max");
        listOfCompoundCommands.add("update");
        listOfCompoundCommands.add("remove_lower");

        listOfNoArgsCommands.add("show");
        listOfNoArgsCommands.add("clear");
        listOfNoArgsCommands.add("info");
        listOfNoArgsCommands.add("history");
        listOfNoArgsCommands.add("help");
        listOfNoArgsCommands.add("min_by_coordinates");
        listOfNoArgsCommands.add("print_field_descending_health");

    }

    public Console getConsole() {
        return console;
    }

    public boolean check(String command) {
        String[] tmp = command.split("\\s+");
        command = tmp[0];
        String[] arguments = new String[tmp.length - 1];
        System.arraycopy(tmp, 1, arguments, 0, tmp.length - 1);
        try {
            if (!listOfAvailableCommands.contains(command)) {
                throw new NotFoundCommandException();
            }
            if(command.equals("add")){
                if (arguments.length != 0) {
                    console.writeStr("At this stage add command doesn't need additional arguments");
                    console.writeStr(ClientManager.SEPARATOR);
                    return false;
                }
            }
            if(command.equals("exit")) {
                if (arguments.length != 0) {
                    console.writeStr("Exit command doesn't need additional arguments");
                    console.writeStr(ClientManager.SEPARATOR);
                    return false;
                }
            }
            if (command.equals("add_if_max")){
                if (arguments.length != 0) {
                    console.writeStr("At this stage add_if_max command doesn't need additional arguments");
                    console.writeStr(ClientManager.SEPARATOR);
                    return false;
                }
            }
            if (command.equals("update")) {
                if (arguments.length != 1) {
                    console.writeStr("Update command requires an id (one int number)");
                    console.writeStr(ClientManager.SEPARATOR);
                    return false;
                }
                if (!isInteger(arguments[0])){
                    console.writeStr("The argument must be an integer");
                    console.writeStr(ClientManager.SEPARATOR);
                    return false;
                }
                if (!ClientManager.clientIds.contains(Integer.parseInt(arguments[0]))){
                    console.writeStr("There is no element with this id in the collection or u don't have access to a collection item with this id");
                    console.writeStr(ClientManager.SEPARATOR);
                    return false;
                }
            }
            if (command.equals("remove_lower")){
                if (arguments.length != 0) {
                    console.writeStr("At this remove_lower command doesn't need additional arguments");
                    console.writeStr(ClientManager.SEPARATOR);
                    return false;
                }
            }
            if (command.equals("remove_by_id")){
                if (arguments.length != 1) {
                    console.writeStr("Remove_by_id command need Integer argument");
                    console.writeStr(ClientManager.SEPARATOR);
                    return false;
                }
            }
            if (listOfNoArgsCommands.contains(command)){
                if(arguments.length!=0){
                    console.writeStr("This command doesn't need additional arguments");
                    return false;
                }
            }
            return true;
        } catch (NotFoundCommandException e) {
            console.writeStr(e.toString());
        }
        return false;
    }


    public Message execute(String command) {
        Message message = new Message();
        String[] tmp = command.split("\\s+");
        command = tmp[0];
        String[] arguments = new String[tmp.length - 1];
        System.arraycopy(tmp, 1, arguments, 0, tmp.length - 1);
        try {
            if (listOfCompoundCommands.contains(command)){
                SpaceMarine spaceMarine = spaceMarineManager.getSpaceMarine();
                if(spaceMarine == null){
                    message.setSpaceMarine(null);
                }
                else if(command.equals("update")){
                    //System.out.println(arguments[0]);
                    spaceMarine.setId(Integer.valueOf(arguments[0]));
                }
                message.setSpaceMarine(spaceMarine);
            }
            message.setCommand(command);
            if (command.equals("remove_by_id")){
                message.setCommand("remove_by_id "+arguments[0]);
            }
            if(command.equals("update")){
                message.setCommand("update "+arguments[0]);
            }
            return  message;
        } catch (StopInputException | EndInputException e) {
            return null;
        }
    }
}

