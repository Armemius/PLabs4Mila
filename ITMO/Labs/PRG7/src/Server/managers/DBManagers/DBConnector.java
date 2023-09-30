package Server.managers.DBManagers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    // fields for driver
    private final String URL;
    private final String username;
    private final String password;
    private Connection connection;

    // object of connector
    public DBConnector() {
        //local db
        this.URL = "jdbc:postgresql://77.234.216.45:5432/studs"; // TODO Go back to localhost
        this.username = "postgres";
        this.password = "Shaurma8982";
    }

    // method for initialize connection
    public void connect() {
        try {
            connection = DriverManager.getConnection(URL, username, password);
            System.out.println("Connection established");
        } catch (SQLException e) {
            System.out.println("Connection error");
        }

    }

    public Connection getConnection() {
        return connection;
    }

}
