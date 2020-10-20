package Aufgabe13;

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
                System.out.println("Bitte tätigen sie eine Eingabe: ");
                byte[] input = in.readLine().getBytes();
                socket.send(new DatagramPacket(input,input.length, InetAddress.getLocalHost(), 4999));
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
