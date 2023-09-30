package Server.managers.DBManagers;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    // fields for driver
    private final String URL;
    private final String username;
    private final String password;
    private Connection connection;

    private static Session session = null;
    private static final int L_PORT = 1337;
    private static final int R_PORT = 5432;
    private static final String SSH_URL = "jdbc:postgresql://localhost:" + L_PORT + "/studs";
    private static final String HOST = "helios.se.ifmo.ru";
    private static final String DB_HOST = "pg";
    private static final int PORT = 2222;
    private static final String USER = "s368849";


    // object of connector
    public DBConnector() {
        //local db
        this.URL = "jdbc:postgresql://localhost:5432/studs";
        this.username = "postgres";
        this.password = "Shaurma8982";
    }

    // method for initialize connection
    public void connect() {
        try {
            try {
                connectBySSH("s368849", "HELIOS PW", "DB PW");
                return;
            } catch (JSchException | SQLException e) {
                System.out.println("Connection by SSH failed");
            }
            connection = DriverManager.getConnection(URL, username, password);
            System.out.println("Connection established");
        } catch (SQLException e) {
            System.out.println("Connection error");
        }
    }

    public void connectBySSH(String username, String password, String dbPass) throws JSchException, SQLException {
        JSch jsch = new JSch();
        session = jsch.getSession(username, HOST, PORT);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");
        System.out.println("Establishing SSH connection");
        session.connect();
        System.out.println("SSH connection established");
        int assignedPort = session.setPortForwardingL(L_PORT, DB_HOST, R_PORT);
        System.out.println("Port successfully forwarded (" + "localhost:" + assignedPort + " -> " + HOST + ":" + R_PORT + ")");
        System.out.println("Establishing connection to database");
        connection = DriverManager.getConnection(SSH_URL, username, dbPass);
        System.out.println("Connection to database established");
    }

    public Connection getConnection() {
        return connection;
    }

}
