package Server.managers.DBManagers;

import Common.consoles.Console;
import Common.consoles.ServerConsole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersManager {
    private Console console;
    private final Connection connection;

    public UsersManager(Console console, Connection connection) {
        this.console = console;
        this.connection = connection;
        System.out.println("userManager was created");

    }

    public void isUserExist(String username) {
        PreparedStatement statement;
        try {
            String stm = "SELECT username FROM users WHERE username = ?";
            statement = connection.prepareStatement(stm);
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                console.writeStr(String.valueOf(true));
            } else console.writeStr(String.valueOf(false));
        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e);
        }
    }

    public void isPasswordCorrect(String username, String password) {
        PreparedStatement statement;
        try {
            String stm = "SELECT password FROM users WHERE username = ? AND password = ?";
            statement = connection.prepareStatement(stm);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
//                System.out.println(result.getString("password"));
                console.writeStr(String.valueOf(true));
            } else console.writeStr(String.valueOf(false));
        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e);
        }
    }

    public void addNewUser(String username, String password) {
        PreparedStatement statement;
        try {
            String stm = "INSERT INTO users (username, password) VALUES (?, ?)";
            statement = connection.prepareStatement(stm);
            statement.setString(1, username);
            statement.setString(2, password);

            statement.executeUpdate();
            console.writeStr("An account with name " + username + " successfully created");

        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e);
        }
    }

    public void getUserId(String username) {
        PreparedStatement statement;
        try {
            String stm = "SELECT user_id FROM users WHERE username = ?";
            statement = connection.prepareStatement(stm);
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                //System.out.println(resultSet.getString("user_id"));
                console.writeStr(resultSet.getString("user_id"));
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e);
        }
    }

    public void setConsole(ServerConsole console) {
        this.console = console;
    }

}
