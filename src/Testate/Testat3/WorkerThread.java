package Testate.Testat3;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class WorkerThread implements Runnable{

    private Buffer buffer;
    private DatagramSocket socket;
    DatagramPacket datagramPacket;

    @Override
    public void run() {
        while (true) {
            work();
        }
    }


    //Verarbeitung der Eingabe und weiterleiten an die entsprechenden Methoden
    private void work() {
        datagramPacket = buffer.get();
        System.out.println(datagramPacket.getPort() + " wird von Thread " + Thread.currentThread().getId() + " bearbeitet.");
        String input = new String(datagramPacket.getData(),0, datagramPacket.getLength());
        if (input.startsWith("READ ")) {
            handleRead(input);
        }
        else if (input.startsWith("WRITE ")) {
            handleWrite(input);
        } else {
            String error = "Error " + input + "does not start with a keyword.";
            try {
                socket.send(new DatagramPacket(error.getBytes(), error.getBytes().length,datagramPacket.getAddress(),datagramPacket.getPort()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    //verarbeitung einer Readanfrage
    private void handleRead(String input) {
        input = input.substring(5);
        System.out.println(datagramPacket.getPort() + " wird von Thread " + Thread.currentThread().getId() + " als read bearbeitet.");
        try {
            String fileName = input.split(",", 2)[0];
            int lineNo = Integer.parseInt(input.split(",", 2)[1]);
            System.out.println(datagramPacket.getPort() +" wird an den Monitor übergeben.");
            //holen eines Monitors für die Datei sowie aufruf der Methode.
            String output = MonitorManagement.getMonitor(fileName).readMethod(lineNo);
            System.out.println(datagramPacket.getPort() +" kam vom Monitor zurück.");
            socket.send(new DatagramPacket(output.getBytes(), output.getBytes().length, datagramPacket.getAddress(), datagramPacket.getPort()));
        } catch (IOException e) {
            e.printStackTrace();
        }catch (ArrayIndexOutOfBoundsException e) {
            try {
                socket.send(new DatagramPacket("ERROR invalid input".getBytes(), "ERROR invalid input".getBytes().length, datagramPacket.getAddress(), datagramPacket.getPort()));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    //verarbeitung einer Readanfrage
    private void handleWrite(String input) {
        input = input.substring(6);
        System.out.println(datagramPacket.getPort() + " wird von Thread " + Thread.currentThread().getId() + " als write bearbeitet.");
        try {
            String fileName = input.split(",", 3)[0];
            int lineNo = Integer.parseInt(input.split(",", 3)[1]);
            String data = input.split(",", 3)[2];
            System.out.println(datagramPacket.getPort() +" wird an den Monitor übergeben.");
            MonitorManagement.getMonitor(fileName).writeMethod(lineNo, data);
            System.out.println(datagramPacket.getPort() +" kam vom Monitor zurück.");
            socket.send(new DatagramPacket("OK".getBytes(), 2, datagramPacket.getAddress(), datagramPacket.getPort()));
        } catch (IOException e) {
            try {
                socket.send(new DatagramPacket("ERROR File does not exist".getBytes(), "ERROR File does not exist".getBytes().length, datagramPacket.getAddress(), datagramPacket.getPort()));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            try {
                socket.send(new DatagramPacket("ERROR invalid input".getBytes(), "ERROR invalid input".getBytes().length, datagramPacket.getAddress(), datagramPacket.getPort()));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public WorkerThread(Buffer buffer, DatagramSocket socket) {
        this.buffer = buffer;
        this.socket = socket;
    }
}
