package Server.managers.collectionManagers;

import Common.commands.Command;
import Common.commands.CompoundCommand;
import Common.consoles.Console;
import Common.exceptions.NotFoundCommandException;
import Common.models.SpaceMarine;
import Common.utils.Message;
import Server.commands.UpdateClientIds;
import Server.commands.collectionCommands.*;
import Server.commands.serviceCommands.AddNewUserToTable;
import Server.commands.serviceCommands.CheckUser;
import Server.commands.serviceCommands.CheckUserPass;
import Server.commands.serviceCommands.GetUserId;
import Server.managers.DBManagers.TableCollectionManager;
import Server.managers.DBManagers.UsersManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static java.util.Collections.max;

public class CommandsManager {

    private final Console console;
    private final CollectionManager collectionManager;
    private final LinkedHashMap<String, Command> listOfCommands = new LinkedHashMap<>();
    public static List<String> history = new ArrayList<>();
    private final List<String> serviceCommands = new ArrayList<>();
    private final UsersManager usersManager;
    private final TableCollectionManager tableCollectionManager;
    public static ExecutorService threadPool;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private static HashSet<SpaceMarine> fileCollection;


    public CommandsManager(CollectionManager collectionManager, Console console, UsersManager usersManager,
                           TableCollectionManager tableCollectionManager) {
        this.collectionManager = collectionManager;
        this.console = console;
        this.usersManager = usersManager;
        this.tableCollectionManager = tableCollectionManager;
        threadPool = Executors.newFixedThreadPool(4);

        Command[] commandsList = {new Show(collectionManager, console), new CheckUser(console, usersManager, this),
                new CheckUserPass(console, usersManager), new AddNewUserToTable(console, usersManager),
                new GetUserId(console, usersManager), new Add(console, collectionManager, tableCollectionManager),
                new Clear(collectionManager, console, tableCollectionManager),
                new AddIfMax(collectionManager, console, tableCollectionManager), new Exit(console),
                new Info(collectionManager, console), new MinByCoordinates(collectionManager, console),
                new History(collectionManager, console), new PrintFieldDescendingHealth(collectionManager, console),
                new RemoveById(console, collectionManager, tableCollectionManager),
                new RemoveLower(collectionManager, console, tableCollectionManager),
                new Update(collectionManager, console, tableCollectionManager),
                new UpdateClientIds(console, collectionManager, tableCollectionManager)};
        listOfCommands.put("help", new Help(commandsList, console));
        for (Command command : commandsList) {
            listOfCommands.put(command.getName(), command);
        }
        serviceCommands.add("check_user");
        serviceCommands.add("check_user_pass");
        serviceCommands.add("updateClientIds");
        serviceCommands.add("add_new_user");
        serviceCommands.add("get_user_id");
    }

    /**
     * A method for tracking the last 11 commands executed
     *
     * @param command
     */
    public void controlHistoryList(String command) {
        String[] tmp = command.split("\\s+");
        command = tmp[0];
        if (history.size() == 11) {
            history.remove(0);
        }
        if (!serviceCommands.contains(command)) {
            history.add(command);
        }
    }

    /**
     * Method for invoking and executing commands
     *
     * @param message
     */

    public void execute(Message message) {
        String[] tmp = (message.getCommand()).split("\\s+");
        String tmpCommand = tmp[0];
//        System.out.println(tmpCommand);
        String[] arguments = new String[tmp.length];
        System.arraycopy(tmp, 1, arguments, 0, tmp.length - 1);
        arguments[tmp.length - 1] = String.valueOf((message.getUser_id()));
        SpaceMarine spaceMarine = message.getSpaceMarine();
        try {
            if (!listOfCommands.containsKey(tmpCommand)) {
                throw new NotFoundCommandException();
            }
            Command command = listOfCommands.get(tmpCommand);
            if (command instanceof CompoundCommand) {
                if (spaceMarine == null) return;
                if (SpaceMarine.ids.contains(spaceMarine.getId())) {
                    spaceMarine.setId(max(SpaceMarine.ids) + 1);
                }
                ((CompoundCommand) command).setSpaceMarine(spaceMarine);
            }
            command.execute(arguments);
            controlHistoryList(tmpCommand);
        } catch (NotFoundCommandException e) {
            console.writeStr(e.toString());
        }
    }

//    public void execute(Message message) {
//        ServerConsole tmpConsole = new ServerConsole();
//        threadPool.execute(() -> {
//            this.collectionManager.setConsole(tmpConsole);
//            this.usersManager.setConsole(tmpConsole);
//            this.tableCollectionManager.setConsole(tmpConsole);
//            try {
//                String[] tmp = (message.getCommand()).split("\\s+");
//                String tmpCommand = tmp[0];
//                //System.out.println(tmpCommand);
//                String[] arguments = new String[tmp.length];
//                for (int i = 1; i < tmp.length; i++) {
//                    arguments[i - 1] = tmp[i];
//                }
//                arguments[tmp.length - 1] = String.valueOf((message.getUser_id()));
//                SpaceMarine spaceMarine = message.getSpaceMarine();
//                try {
//                    if (!listOfCommands.containsKey(tmpCommand)) {
//                        throw new NotFoundCommandException();
//                    }
//                    Command command = listOfCommands.get(tmpCommand);
//                    if (command instanceof CompoundCommand) {
//                        if (spaceMarine == null) return;
//                        if (SpaceMarine.ids.contains(spaceMarine.getId())) {
//                            spaceMarine.setId(max(SpaceMarine.ids) + 1);
//                        }
//                        ((CompoundCommand) command).setSpaceMarine(spaceMarine);
//                    }
//
//                    command.execute(arguments);
//                    String tmpString = tmpConsole.getText();
//                    System.out.println(tmpString);
//                    this.console.writeStr(tmpString);
//                    String res = console.getText();
//                    System.out.println(res);
//
////                    this.collectionManager.setConsole(this.console);
////                    this.usersManager.setConsole((ServerConsole) this.console);
////                    this.tableCollectionManager.setConsole((ServerConsole) this.console);
//                    controlHistoryList(tmpCommand);
//                } catch (NotFoundCommandException e) {
//                    console.writeStr(e.toString());
//                }
//            }finally {
//            }
//        });
//    }

    public Console getConsole() {
        return console;
    }
}

