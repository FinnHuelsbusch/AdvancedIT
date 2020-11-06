package Aufgabe15;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Client {
    public static void main(String[] args) {
        new Client();
    }

    public Client() {
        DatagramSocket socket = null;
        try {
             socket = new DatagramSocket();
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while (true) {

                String input = in.readLine();
                socket.send(new DatagramPacket(input.getBytes(),input.getBytes().length, InetAddress.getLocalHost(), 5999));
                DatagramPacket receive = new DatagramPacket(new byte[65507], 65507);
                socket.receive(receive);
                System.out.println(new String(receive.getData(),0,receive.getLength()));
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}
