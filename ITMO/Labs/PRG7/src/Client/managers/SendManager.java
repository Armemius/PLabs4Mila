package Client.managers;

import Client.managers.authorizationModule.AuthManager;
import Common.consoles.Console;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import Common.utils.Message;

public class SendManager {
    static DatagramSocket socket;
    static InetAddress address;
    static int port;
    private static Console console;

    public SendManager(DatagramSocket socket, InetAddress address, int port, Console console){
        SendManager.port = port;
        SendManager.socket = socket;
        SendManager.address = address;
        SendManager.console = console;
    }

    public static void sendMessage(Message message) throws IOException {
        try {
            message.setUser_id(AuthManager.id);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream oOut = new ObjectOutputStream(out);
            oOut.writeObject(message);
            oOut.flush();

            byte[] data = out.toByteArray();

            DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
//                System.out.println(message.getCommand());
//                if(message.getSpaceMarine()!= null){
//                    System.out.println(message.getSpaceMarine().toString());
//                }

            socket.send(packet);
//            console.writeStr("message was sent");

            oOut.close();
        } catch (IOException e) {
            console.writeStr("Something bad happened and message wasn't sent");
        }
    }
}
