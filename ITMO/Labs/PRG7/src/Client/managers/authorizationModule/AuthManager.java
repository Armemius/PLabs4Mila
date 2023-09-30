package Client.managers.authorizationModule;

import Client.managers.ReceiveManager;
import Common.consoles.Console;
import Common.utils.Message;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static Client.managers.SendManager.sendMessage;

public class AuthManager {
    private Console console;
    private String username;
    private String password;
    public static final String SEPARATOR = "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" +
            "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";

    public static int id;

    public AuthManager(Console console) {
        this.console = console;
        id = 0;
        this.username = "";
        this.password = "";
    }

    public boolean auth() throws IOException, NoSuchAlgorithmException {
        boolean tmp;
        while (console.isNextStr()) {
            console.writeStr(SEPARATOR);
            console.writeStr("Do u have an account? (enter \"yes\" or \"no\")");
            console.writeStr(SEPARATOR);
            String answer = console.getNextStr();
            if (answer.equals("yes")) {
                console.writeStr(SEPARATOR);
                tmp = getUser();
                if (tmp) {
                    console.writeStr(SEPARATOR);
                    console.writeStr("U are logged in");
                    console.writeStr(SEPARATOR);
                    return true;
                }
            }
            if (answer.equals("no")) {
                tmp = createUser();
                if (tmp) {
                    tmp = getUser();
                    if (tmp) {
                        console.writeStr(SEPARATOR);
                        console.writeStr("U are logged in");
                        console.writeStr(SEPARATOR);
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public boolean checkUser(String user) {
        try {
            Message mess = new Message();
            mess.setCommand("check_user " + user);
            sendMessage(mess);
            return Boolean.parseBoolean(ReceiveManager.getMessage());
        } catch (IOException e) {
            return false;
        }
    }

    public boolean checkUserPass(String user, String pass) {
        try {
            Message mess = new Message();
            mess.setCommand("check_user_pass " + user + " " + pass);
            sendMessage(mess);
            return Boolean.parseBoolean(ReceiveManager.getMessage());
        } catch (IOException e) {
            return false;
        }

    }


    public boolean getUser() throws IOException, NoSuchAlgorithmException {
        String helpText = "Enter your login/username: ";
        int attempts = 3;
        console.writeStr(helpText);
        console.writeStr(SEPARATOR);
        String user = console.getNextStr();
        if (checkUser(user)) {
            String helpPassText = "Enter password (attempts left " + "3" + "): ";
            console.writeStr(SEPARATOR);
            console.writeStr(helpPassText);
            console.writeStr(SEPARATOR);
            String pass;
            setUsername(user);
            while (attempts > 0) {
                pass = hashPassword(console.getNextStr());
                if (checkUserPass(user, pass)) {
                    setPassword(pass);
                    getUserId();
                    return true;
                }
                attempts -= 1;
                console.writeStr(SEPARATOR);
                console.writeStr("Wrong password");

                if (attempts == 0) {
                    console.writeStr(SEPARATOR);
                    console.writeStr("Could not log in to your account. Try again or create a new account");

                } else {
                    console.writeStr(SEPARATOR);
                    console.writeStr("Enter password (attempts left " + attempts + "):");
                    console.writeStr(SEPARATOR);
                }
            }
            return false;
        }
        console.writeStr(SEPARATOR);
        console.writeStr("Account with this name doesn't exist");
        return false;
    }

    public boolean createUser() throws IOException, NoSuchAlgorithmException {
        String helpText = "Enter username: ";
        console.writeStr(SEPARATOR);
        console.writeStr(helpText);
        console.writeStr(SEPARATOR);
        String user = console.getNextStr();
        if (!checkUser(user)) {
            setUsername(user);
            console.writeStr(SEPARATOR);
            console.writeStr("Enter password: ");
            console.writeStr(SEPARATOR);
            String pass = hashPassword(console.getNextStr());
            setPassword(pass);
            sendUser();

            return true;
        }
        console.writeStr(SEPARATOR);
        console.writeStr("Account with this username is already exist, try another username");
        return false;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void getUserId() throws IOException {
        Message mess = new Message();
//        System.out.println(username);
        mess.setCommand("get_user_id " + username);
        sendMessage(mess);
        id = Integer.parseInt(ReceiveManager.getMessage());
    }

    public void sendUser() throws IOException {
        Message mess = new Message();
        mess.setCommand("add_new_user " + username + " " + password);
        sendMessage(mess);
        console.writeStr(SEPARATOR);
        console.writeStr(ReceiveManager.getMessage());
        console.writeStr(SEPARATOR);
    }

    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD2");

        byte[] passwordBytes = password.getBytes();
        byte[] hashBytes = md.digest(passwordBytes);

        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

}
