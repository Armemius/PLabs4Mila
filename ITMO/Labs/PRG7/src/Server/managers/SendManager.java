package Server.managers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SendManager {
    private SocketAddress socketAddress;
    private ByteBuffer buffer;

    private final DatagramChannel channel;
    private static final int FRAGMENT_SIZE = 65507;

    public SendManager(DatagramChannel channel) {
        this.channel = channel;
        this.buffer = ByteBuffer.allocate(1024);
    }

    public void sendMessage(String message) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream oOut = new ObjectOutputStream(out);
            oOut.writeObject(message);
            oOut.flush();

            byte[] responseData = out.toByteArray();
            List<byte[]> fragments = new ArrayList<>();
            for (int i = 0; i < responseData.length; i += FRAGMENT_SIZE) {
                int fragmentLength = Math.min(FRAGMENT_SIZE, responseData.length - i);
                byte[] fragment = Arrays.copyOfRange(responseData, i, i + fragmentLength);
                fragments.add(fragment);
            }
            for (byte[] fragment : fragments) {
                ByteBuffer responseBuffer = ByteBuffer.wrap(fragment);
                channel.send(responseBuffer, socketAddress);
            }
            System.out.println("Answer was sent: " + message);

            oOut.close();
        } catch (IOException e) {
            System.out.println("Sending error");
        }
    }


    public void setSocketAddress(SocketAddress socketAddress) {
        this.socketAddress = socketAddress;

    }
}
