package Client.managers;

import Client.managers.authorizationModule.AuthManager;
import Common.consoles.Console;
import Common.utils.Message;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Client.managers.Client.port;

public class ClientManager {
    private final Client client;
    private final Console console;
    public static final String SEPARATOR = "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" +
            "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";
    private ReceiveManager receiveManager;
    private SendManager sendManager;
    private final InetAddress address;

    public static Set<Integer> clientIds = new TreeSet<>();

    public ClientManager(Client client, Console console, InetAddress address) {
        this.client = client;
        this.console = console;
        this.address = address;
    }

    public Console getConsole() {
        return console;
    }

    public void run() throws IOException, NoSuchAlgorithmException {
        client.start();
        receiveManager = new ReceiveManager();
        sendManager = new SendManager(client.getSocket(), address, port, console);
        InputDataManager inputDataManager = new InputDataManager(this);
        AuthManager authManager = new AuthManager(console);

        while (!authManager.auth()) {
            Thread.onSpinWait();
            // do nothing
        }

        while (console.isNextStr()) {
            console.writeStr("Enter a command (use help for a list of available commands): ");
            try {
                String command = console.getNextStr();
                console.writeStr(SEPARATOR);
                if (isExecuteScript(command)) {
                    String[] commands = command.split("\\s");
                    if (commands.length < 2) {
                        System.out.println("File(s) with script is not provided");
                        console.writeStr(SEPARATOR);
                        continue;
                    } else if (commands.length > 2) {
                        System.out.println("Too many arguments were provided");
                        console.writeStr(SEPARATOR);
                        continue;
                    }
                    System.out.println("Executing script");
                    try {
                        System.out.println(commands[1]);
                        executeScript(commands[1], inputDataManager);
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
//                    Message scriptMessage = new Message();
//                    scriptMessage.setCommand(commands[0]);
//                    String[] arguments = new String[commands.length - 1];
//                    System.arraycopy(commands, 1, arguments, 0, commands.length - 1);
//                    scriptMessage.setArgs(arguments);
//                    sendManager.sendMessage(scriptMessage);
//                    console.writeStr(receiveManager.getMessage());
                    console.writeStr(SEPARATOR);
                    continue;
                }

                if (isUpdate(command)) {
                    Message updMessage = inputDataManager.execute("updateClientIds");
                    SendManager.sendMessage(updMessage);
                    String[] ids = ReceiveManager.getMessage().split("\s");
                    for (String id : ids) {
                        clientIds.add(Integer.parseInt(id));
                    }
                }

                if (inputDataManager.check(command)) {
                    handleCommand(command, inputDataManager);
                    console.writeStr(SEPARATOR);
                }
            } catch (IOException ex) {
                console.writeStr("");
            }
        }
    }

    private void executeScript(String path, InputDataManager inputDataManager) throws IOException {
        System.out.println("Executing: " + path);
        var recursion = checkRecursion(path, new HashSet<>());
        if (recursion != null) {
            System.out.println("Found recursion: " + recursion);
            return;
        }
        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNextLine()) {
                var buffer = scanner.nextLine();
                if (!buffer.isBlank()) {
                    if (buffer.contains("execute_script")) {
                        String[] commands = buffer.split("\\s");
                        executeScript(commands[1], inputDataManager);
                    }
                    handleCommand(buffer, inputDataManager);
                }
            }
        } catch (FileNotFoundException e) {
            throw new IOException("Error while reading the file");
        }
    }

    private String checkRecursion(String path, Set<String> used) throws IOException {
        StringBuilder script = new StringBuilder();
        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNextLine()) {
                script.append(scanner.nextLine()).append('\n');
            }
            Matcher matcher = Pattern.compile("execute_script\\s+(.+)\n", Pattern.MULTILINE).matcher(script);
            while (matcher.find()) {
                String match = matcher.group(1);
                if (used.contains(path)) {
                    return path;
                }
                used.add(path);
                String recursion = checkRecursion(match, new HashSet<>(used));
                if (recursion != null) {
                    return path + " -> " + recursion;
                }
            }
            return null;
        } catch (FileNotFoundException e) {
            throw new IOException("Error while reading the file");
        }
    }

    private void handleCommand(String command, InputDataManager inputDataManager) throws IOException {
        Message message = inputDataManager.execute(command);
        SendManager.sendMessage(message);
        if (isExit(command)) {
            client.stop();
            System.exit(0);
        }
        console.writeStr(ReceiveManager.getMessage());
    }

    public boolean isUpdate(String str) {
        Matcher matcher = Pattern.compile("^update").matcher(str);
        String x = "";
        while (matcher.find()) {
            x = String.valueOf(matcher.group());
        }
        return x.equals("update");
    }

    public boolean isExit(String str) {
        Matcher matcher = Pattern.compile("^exit").matcher(str);
        String x = "";
        while (matcher.find()) {
            x = String.valueOf(matcher.group());
        }
        return x.equals("exit");
    }

    public boolean isExecuteScript(String str) {
        Matcher matcher = Pattern.compile("^execute_script").matcher(str);
        String x = "";
        while (matcher.find()) {
            x = String.valueOf(matcher.group());
        }
        return x.equals("execute_script");
    }

    public Client getClient() {
        return client;
    }

    public void stop() {
        client.stop();
    }
}
