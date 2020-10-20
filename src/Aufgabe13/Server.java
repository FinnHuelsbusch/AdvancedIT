package Aufgabe13;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Server {
    public final int DEFAULT_PORT = 4999;
    int port;

    public static void main(String[] args) {
        new Server();
    }

    public Server() {
        new Server(DEFAULT_PORT);
    }

    public Server(int port) {
        this.port = port;
        DatagramSocket datagramSocket = null;
        try {
            datagramSocket= new DatagramSocket(port);
            DatagramPacket data = new DatagramPacket(new byte[65507], 65507);

            while (true) {
                datagramSocket.receive(data);
                String input = new String(data.getData(),0,data.getLength());
                InetAddress inetAddress = data.getAddress();
                System.out.println(inetAddress + ":"+ data.getPort()+" "+ input);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (datagramSocket != null) {
                datagramSocket.close();
            }
        }
    }
}
