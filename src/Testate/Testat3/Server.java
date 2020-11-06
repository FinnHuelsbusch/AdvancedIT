package Testate.Testat3;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;

public class Server {

    final static int DEFAULTPORT = 5999;
    ArrayList<Thread> worker;
    Buffer buffer;
    DatagramSocket socket;

    public static void main(String[] args) {
        new Server();
    }

    public Server() {
        this(DEFAULTPORT);
    }

    public Server(int port){
        worker = new ArrayList<>();
        // erstellt einen Buffer. Die größe hängt vom System ab.
        buffer = new Buffer(Runtime.getRuntime().availableProcessors());
        try {
            // Erstellt den Socket
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            System.out.println("Port schon belegt.");
        }
        buildWorkerPool();
        startReceiving();
    }

    private void startReceiving() {
        try {
            // Empfangen und weitergabe der Eingaben
            while (true) {
                DatagramPacket datagramPacket = new DatagramPacket(new byte[65507], 65507);
                socket.receive(datagramPacket);
                System.out.println(datagramPacket.getPort()+ " Anfrage empfangen.");
                buffer.insert(datagramPacket);
                System.out.println(datagramPacket.getPort()+ " Anfrage in den Puffer gelegt.");
            }
        }  catch (IOException e) {
            System.out.println("Error");
        }
    }

    private void buildWorkerPool() {
        // Erstellen und starten der einzelenen Worker. Die Anzahl hängt vom System ab.
        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            worker.add(new Thread(new WorkerThread(buffer,socket)));
        }
        for (Thread r : worker) {
            r.start();
        }
    }
}
