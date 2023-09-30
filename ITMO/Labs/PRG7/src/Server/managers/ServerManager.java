package Server.managers;

import Common.consoles.Console;
import Common.consoles.ServerConsole;
import Common.utils.Message;
import Server.managers.DBManagers.TableCollectionManager;
import Server.managers.DBManagers.UsersManager;
import Server.managers.collectionManagers.CollectionManager;
import Server.managers.collectionManagers.CommandsManager;

import java.io.IOException;

public class ServerManager {
    private final Server server;
    private static CollectionManager collectionManager;
    private static ServerConsole console;
    private ReceiveManager receiveM;
    private SendManager sendM;
    private boolean workingState = true;
    private static UsersManager usersManager;
    private final TableCollectionManager tableCollectionManager;

    //literally server manager
    public ServerManager(Server server, CollectionManager collectionManager, UsersManager usersManager, TableCollectionManager tableCollectionManager) {
        this.server = server;
        ServerManager.collectionManager = collectionManager;
        console = collectionManager.getConsole();
        ServerManager.usersManager = usersManager;
        this.tableCollectionManager = tableCollectionManager;
    }

    //main Server executor
    public void start() {
        try {
            //open channel, create buffer, bind InetSocketAddress
            server.start();
            System.out.println("server has been started");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" +
                    "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            receiveM = new ReceiveManager(server.getDatagramChannel());
            sendM = new SendManager(server.getDatagramChannel());
            CommandsManager commandsManager = new CommandsManager(collectionManager, console, usersManager, tableCollectionManager);
            //  consoleUnit = new ServConsoleUnit(server, collectionManager);


            while (workingState) {
                try {
                    handleCommand(commandsManager);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public void handleCommand(CommandsManager commandsManager) throws IOException {
        Message message = receiveM.getMessage();
        commandsManager.execute(message);
        setConsole(collectionManager.getConsole());
        sendM.setSocketAddress(receiveM.getSocketAddress());
        String result = console.getText();
        //System.out.println(result);
        if (!message.getCommand().equals("exit")) {
            sendM.sendMessage(result);
        }
    }

    public static Console getConsole() {
        return console;
    }

    public static CollectionManager getCollectionManager() {
        return collectionManager;
    }

    public void setConsole(ServerConsole console){
        ServerManager.console = console;
    }
}








//Scanner scanner = new Scanner(System.in);
//Thread inputThread = new Thread(() -> {
//                while (true) {
//                    String serveConsoleCm = scanner.nextLine().strip();
//                    if (serveConsoleCm.length() == 0) {
//                        scanner.close();
//                        return;
//                    }
//                    if (serveConsoleCm.length() > 0) {
//                        if (serveConsoleCm.equals("save")) {
//                            FileManager.saveCollection(collectionManager.getCollection());
//                            //console.writeStr("collection was saved");
//                        }
//                        if (serveConsoleCm.equals("exit")) {
//                            FileManager.saveCollection(collectionManager.getCollection());
//                            try {
//                                server.close();
//                            } catch (IOException e) {
//                                throw new RuntimeException(e);
//                            }
//                            System.out.println("server was closed");
//                            System.exit(0);
//                        } else if (!(serveConsoleCm.equals("save") || serveConsoleCm.equals("exit"))) {
//                            System.out.println("Command doesn't exist");
//                        }
//                    }
//                }
//            });
//            inputThread.start();