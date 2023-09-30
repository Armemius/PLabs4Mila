package Server.managers;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Server {
    public Server() {
    }

    public Server(int port) {
        inetSocketAddress = new InetSocketAddress(port);
    }

    private InetSocketAddress inetSocketAddress;
    private DatagramChannel datagramChannel;
    protected ByteBuffer buffer;


    public void start() throws IOException {
        datagramChannel = DatagramChannel.open();
        datagramChannel.bind(inetSocketAddress);
        buffer = ByteBuffer.allocate(4096);
    }

    public DatagramChannel getDatagramChannel() {
        return datagramChannel;
    }

    public void close() throws IOException {
        datagramChannel.close();
    }
}
