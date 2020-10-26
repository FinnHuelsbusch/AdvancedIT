package Testate.Testat2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Server {
    //Ausgabe Port
    static final int DEFAULT_PORT = 7777;
    //Ausgabe Pfad
    static final String FILEPATH = System.getProperty("user.home") +File.separator+ "Messages"+File.separator;

    int port;
    PrintWriter out;
    BufferedReader in;
    Socket connection = null;

    public Server(int port) {
        // festlegen des Ports
        this.port = port;

        ServerSocket server = null;
        try {
            //erstellen eines Sockets auf dem Angegebenen Port
            server = new ServerSocket(this.port);
            while (true) {
                try {
                    //warten auf eine Anfrage
                    connection = server.accept();
                    //In- Output für die Verbindung anlegen.
                    out = new PrintWriter(connection.getOutputStream());
                    in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    //einlesen der Eingabe
                    String input = in.readLine();
                    //Zuordnung der Eingabe
                    if (input.startsWith("SAVE ")) {
                        //Prüfen ob eine Eingabe getätigt wurde
                        if (input.length() > 5) {
                            //Speichern der Eingabe ohne Schlüsselwort
                            String key = saveNewValue(input.substring(5));
                            //Prüfen, ob das Speichern erfolgreich war
                            if (key.startsWith("FAILED ")) {
                                out.println("\u001B[31m" + key);
                            } else {
                                out.println("\u001B[0mKEY " + key);
                            }
                        } else {
                            out.println("\u001B[31mFAILED input to short");
                        }
                    } else if (input.startsWith("GET ")) {
                        //Prüfen, ob ein Schlüssel gegeben ist
                        if (input.length() > 4) {
                            //Auslesen der Datei
                            String value = getValue(input.substring(4));
                            //Prüfen, ob das Lesen erfolgreich war.
                            if (value.startsWith("FAILED")) {
                                out.println("\u001B[31m" + value);
                            } else {
                                out.println("\u001B[0mOK " + value);
                            }
                        } else {
                            out.println("\u001B[31mFAILED input to short");
                        }
                    } else {
                        //Annahme ist, dass das abschließende Leerzeichen zum Schlüsselwort gehört
                        out.println("\u001B[31mFAILED " + input + " does not start with a keyword");
                    }
                    //Rückgabe des Outputs
                    out.flush();
                } catch (IOException e) {
                } finally {
                    if (connection != null) {
                        //Verbindung schließen
                        connection.close();
                        in.close();
                        out.close();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (server != null) {
                try {
                    // Port schließen
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Server() {
        this(DEFAULT_PORT);
    }

    public static void main(String[] args) {
        new Server();
    }

    private String getValue(String key) {
        //Anlegen eines Datei Objekts
        File file = new File(FILEPATH, key);
        BufferedReader bufferedReader = null;
        try {
            //Auslesen der Datei.
            bufferedReader = new BufferedReader(new FileReader(file));
            return bufferedReader.readLine();
        } catch (FileNotFoundException e) {
            return "FAILED file not found ";
        } catch (IOException e) {
            return "FAILED internel error ";
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String saveNewValue(String value) {
        //Erstellen eines zufälligen Schlüssels in Form einer Hex-Farbe
        String key = String.format("#%06x", new Random().nextInt(0xffffff + 1));
        //Anlegen eines entsprechenden Dateiobjekts im Homeverzeichnis

        File file = new File(FILEPATH, key);
        FileWriter fileWriter = null;
        try {
            //Speichern der Datei
            fileWriter = new FileWriter(file);
            fileWriter.write(value);
            fileWriter.flush();
            return key;
        } catch (IOException e) {
            return "FAILED file already exists";
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
