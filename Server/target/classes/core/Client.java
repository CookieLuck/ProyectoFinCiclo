package core;

import javax.sound.sampled.Port;
import java.net.InetAddress;

public class Client {
    InetAddress address;
    int port;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public Client(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }
}
