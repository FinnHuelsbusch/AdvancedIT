package Ports;

import java.io.*;
import java.net.Socket;

public class EchoClient {

    public static final int serverPort = 7777;

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                System.out.println("Tschüüüssss");
            }
        });


        String hostname = "localhost";
        PrintWriter networkOut = null;
        BufferedReader networkIn = null;
        Socket s = null;
        try {
            while (true) {
                s = new Socket(hostname, serverPort);
                System.out.printf("Connected to echo server%n");
                networkIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
                BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
                networkOut = new PrintWriter(s.getOutputStream());
                String theLine = userIn.readLine();
                if (theLine.equals(".")) {
                    break;
                }
                networkOut.println(theLine);
                networkOut.flush();
                System.out.println("geflusht");
                System.out.println(networkIn.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

                try {
                    if (s != null) {
                        s.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

        }
    }


}

