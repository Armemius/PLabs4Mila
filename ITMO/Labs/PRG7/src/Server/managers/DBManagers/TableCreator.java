package Server.managers.DBManagers;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TableCreator {
    private Connection connection;

    //create table fot SpaceMarine object
    public static void createCollectionTable(Connection connection) {
        Statement statement;
        try {
            String query = "CREATE TABLE collection" +
                    " (" +
                    "id serial PRIMARY KEY not null," +
                    "name varchar(50)," +
                    "x bigint," +
                    "y double precision," +
                    "health float," +
                    "heartCount integer," +
                    "loyal boolean," +
                    "meleeWeapon varchar(50)," +
                    "chapter_name varchar(50)," +
                    "chapter_world varchar(50)," +
                    "creationDate varchar(200)," +
                    "user_id int, " + "FOREIGN KEY (user_id) REFERENCES users (user_id) " +
                    ")";
            statement = connection.createStatement();
            statement.execute(query);
            System.out.println("Table \"Collection\" created");
        } catch (Exception e) {
            System.out.println("Table is not created:" + e);
        }
    }

    public static void createUsersTable(Connection connection) {
        Statement statement;
        try {
            String stm = "CREATE TABLE Users " +
                    "(" + "user_id serial PRIMARY KEY not null," +
                    "username varchar(100)," + "password varchar(64)" + ")";
            statement = connection.createStatement();
            statement.execute(stm);
            System.out.println("Table \"Users\" created");
        } catch (SQLException e) {
            System.out.println("Table wasn't created: " + e);
        }

    }

    //delete table
    public static void deleteTable(Connection connection, String tableName) {
        Statement statement;
        try {
            String query = "DROP TABLE " + tableName;
            statement = connection.createStatement();
            statement.execute(query);
            System.out.println("Table " + tableName + " deleted");

        } catch (Exception e) {
            System.out.println("Table is not deleted: " + e);
        }
    }
}
