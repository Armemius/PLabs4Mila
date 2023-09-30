package Server;

import Common.consoles.ServerConsole;
import Server.managers.*;
import Server.managers.DBManagers.DBConnector;
import Server.managers.DBManagers.TableCollectionManager;
import Server.managers.DBManagers.TableCreator;
import Server.managers.DBManagers.UsersManager;
import Server.managers.collectionManagers.CollectionManager;

public class ServerMain {
    public static void main(String[] args) {
        Thread mainThread = Thread.currentThread();
        DBConnector dbConnector = new DBConnector();
        dbConnector.connect();

        ServerConsole serverConsole = new ServerConsole();
        UsersManager usersManager = new UsersManager(serverConsole, dbConnector.getConnection());

        TableCollectionManager  tableCollectionManager = new TableCollectionManager(dbConnector.getConnection(), serverConsole);
        CollectionManager collectionManager = new CollectionManager(tableCollectionManager.readSpaceMarines(), serverConsole);

//        Use CREATE TABLE IF NOT EXISTS
//        ⛔⛔⛔ HARAM ⛔⛔⛔
//        TableCreator.deleteTable(dbConnector.getConnection(), "collection");
//        TableCreator.deleteTable(dbConnector.getConnection(), "users");

        TableCreator.createUsersTable(dbConnector.getConnection());
        TableCreator.createCollectionTable(dbConnector.getConnection());


        int port = 6789;
        Server server = new Server(port);
        ServerManager serverM = new ServerManager(server, collectionManager, usersManager, tableCollectionManager);
        serverM.start();

    }
}

/*
⠄⣾⠟⢋⣉⣙⠛⠿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡀⠄
⣶⠏⣰⣿⣿⣿⣿⣶⣌⠻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⠄
⣿⣴⠟⣋⣩⣭⣭⣿⣿⣶⣾⣿⣿⣿⣿⣿⣿⡿⠟⢛⣉⣉⣉⡙⠻⣿⣿⣿⣿⡄
⣿⢁⣾⣿⣿⣿⣯⣛⠛⣿⣿⣿⣿⣿⣿⣿⣯⣤⡾⣿⣿⣿⣿⣿⣷⣤⠉⠻⣿⣷
⣿⠘⠋⠉⠉⣉⣉⣉⡙⠻⢿⣿⣿⣿⣿⣿⣿⠏⣴⣾⣥⣶⣶⣤⣍⣻⣿⣿⣿⣿
⣿⠘⠁⠄⠄⢸⣿⣿⣿⡷⠂⣹⣿⣿⣿⣿⣿⠘⠛⠛⠛⠻⣿⣿⣿⣿⣿⣿⠛⣿
⣿⡄⠄⣀⣀⣚⣛⣉⣥⡴⠾⠿⢻⣿⣿⣿⣿⡇⢀⡋⠄⠄⣀⠉⠛⠿⢿⠟⣸⣿
⣿⣿⣄⠛⠿⠟⢻⣿⣡⣴⡶⠄⣾⣿⣿⣿⣿⣿⣌⡙⠿⣶⣶⣶⣶⣶⣶⣶⣿⣿
⣿⣿⣿⣿⡿⠟⢋⣽⣿⠟⣠⣿⣿⣿⣿⣿⣿⡿⣿⣿⣶⣦⣶⣿⣿⣮⣭⣶⣿⣿
⣿⡿⠏⣁⣀⢔⠿⠋⡏⢸⣿⣿⣿⡿⠛⠉⠉⣰⣟⠻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⣿⣶⠞⣉⣁⣉⣁⣈⡀⠘⠛⠛⠉⣤⣚⣛⣙⣋⣻⣇⠹⣿⣿⣿⣿⣿⣿⣿⣿⣿
⣿⠁⣼⡟⠻⠿⠿⠿⣿⣿⣦⣤⣄⣀⣉⣉⣉⣉⡛⢻⣀⣿⣿⢻⣿⣿⣿⣿⣿⣿
⣿⣦⡈⠻⢷⣤⣙⠒⠶⢤⣭⣭⣭⣭⠍⢉⣩⣾⡿⠈⣿⣿⣿⣦⣽⣿⣿⣿⣿⣿
⣿⣿⣿⣦⣤⣈⡛⠻⠿⠶⠶⠶⠶⠶⠚⣛⣉⣠⣴⣾⣿⣿⠿⢛⣿⣿⣿⣿⣿⣿
⢿⠟⣽⢿⣿⣿⣿⠻⠶⡶⢶⠲⣶⣿⣿⣿⣿⣿⣿⡟⢿⣷⣶⣿⣿⣿⣿⣿⠟⠋
 */
