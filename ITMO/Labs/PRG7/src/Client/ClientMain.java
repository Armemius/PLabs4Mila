package Client;

import Client.managers.Client;
import Client.managers.ClientManager;
import Common.consoles.BaseConsole;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;

public class ClientMain {
    public static void main(String[] args) {
        BaseConsole console = new BaseConsole();
        Client client = new Client();

        ClientManager clientManager;
        try {
            InetAddress inetAddress = InetAddress.getByName("localhost");
            clientManager = new ClientManager(client, console, inetAddress);
            clientManager.run();
        } catch (UnknownHostException ex) {
            System.err.println("Host name is incorrect");
        } catch (SocketException ex) {
            System.err.println("Internet error");
        } catch (IOException e) {
            System.err.println("Authorization error");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
