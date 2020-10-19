package Ports;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public static final int DEFAULT_PORT = 7777;

    public static void main(String[] args) {
        int port = DEFAULT_PORT;
        PrintWriter out;
        BufferedReader in;
        Socket connection = null;
        try {
            ServerSocket server = new ServerSocket(port);
            while (true) {
                try {
                    System.out.println("Warte auf Verbindung");
                    connection = server.accept();
                    out = new PrintWriter(connection.getOutputStream());
                    in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String x = in.readLine();
                    out.println(x);
                    System.out.println(x);
                    out.flush();
                    System.out.println("geflusht");

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (connection != null) {
                            connection.close();
                        }
                    } catch (IOException w) {
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
