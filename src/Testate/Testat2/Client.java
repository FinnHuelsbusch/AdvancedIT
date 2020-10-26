package Testate.Testat2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    public static final int serverPort = 7777;

    public static void main(String[] args) {
        //Den User gebührende Verabschiedung
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Auf wiedersehen!!");
        }));

        System.out.println("Als Schlüssel wurden HEX-Farben verwendet." +
                "\nDie Dateien werden im Ordner Messages abgelegt, der im Home-Verzeichnis des Users erstellt werden muss.");

        // Anlegen der Variablen
        String hostname = "localhost";
        PrintWriter networkOut = null;
        BufferedReader networkIn = null;
        Socket s = null;
        try {
            while (true) {
                //Aufbauen einer Verbindung
                s = new Socket(hostname, serverPort);
                //Anlegen der In- Outputs
                networkIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
                BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
                networkOut = new PrintWriter(s.getOutputStream());
                //Eingabe lesen
                String theLine = userIn.readLine();
                if (theLine.equals(".")) {
                    break;
                }
                //An den Service senden
                networkOut.println(theLine);
                networkOut.flush();
                //Rückgabewert auf der Konsole ausgeben
                String ausgabe = networkIn.readLine();

                System.out.println(ausgabe);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (s != null) {
                    //Verbindungen schließen
                    s.close();
                    networkIn.close();
                    networkOut.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


