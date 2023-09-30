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
            String query = "CREATE TABLE IF NOT EXISTS collection" +
                    " (" +
                    "id serial PRIMARY KEY not null," +
                    "name varchar," +
                    "x bigint," +
                    "y double precision," +
                    "health float," +
                    "heartCount integer," +
                    "loyal boolean," +
                    "meleeWeapon varchar," +
                    "chapter_name varchar," +
                    "chapter_world varchar," +
                    "creationDate varchar," +
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
            String stm = "CREATE TABLE IF NOT EXISTS Users " +
                    "(user_id serial PRIMARY KEY not null," +
                    "username varchar NOT NULL , password varchar NOT NULL)";
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
