package Aufgabe15;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Server {
    public final int DEFAULT_PORT = 5999;
    int port;



    public static void main(String[] args) {
        new Server();
    }

    public Server() {
        new Server(DEFAULT_PORT);
    }

    public Server(int port) {
        this.port = port;
        DatagramSocket socket ;
        try {
            socket = new DatagramSocket(port);
            while (true) {
                DatagramPacket datagramPacket = new DatagramPacket(new byte[65507], 65507);
                socket.receive(datagramPacket);
                new Thread(new WorkerThread(datagramPacket, socket)).start();
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
