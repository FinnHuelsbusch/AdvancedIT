package Aufgabe14;

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
            DatagramPacket datagramPacket = new DatagramPacket(new byte[65507], 65507);

            while (true) {
                socket.receive(datagramPacket);
                String input = new String(datagramPacket.getData(),0, datagramPacket.getLength());
                if (input.startsWith("READ ")) {
                    input = input.substring(5);
                    String fileName = input.split(",",2)[0];
                    int lineNo= Integer.parseInt(input.split(",",2)[1]);
                    CustomFile customFile = new CustomFile();
                    String output = customFile.getLine(fileName, lineNo);
                    socket.send(new DatagramPacket(output.getBytes(), output.getBytes().length, datagramPacket.getAddress(), datagramPacket.getPort()));
                } else if (input.startsWith("WRITE ")) {
                    input = input.substring(6);
                    String fileName = input.split(",",3)[0];
                    int lineNo = Integer.parseInt(input.split(",",3)[1]);
                    String data= input.split(",",2)[2];
                    CustomFile customFile = new CustomFile();
                    customFile.addLine(fileName, lineNo,data);
                    socket.send(new DatagramPacket("OK".getBytes(), 2,datagramPacket.getAddress(),datagramPacket.getPort()));
                } else {
                    System.out.println("Error" + input + "does not start with a keyword.");
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
