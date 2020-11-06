package Aufgabe15;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class WorkerThread implements Runnable{
    DatagramPacket datagramPacket;
    DatagramSocket socket;


    @Override
    public void run() {
        String input = new String(datagramPacket.getData(),0, datagramPacket.getLength());
        if (input.startsWith("READ ")) {
            handleRead(input);
        }
        else if (input.startsWith("WRITE ")) {
            handleWrite(input);
        } else {
            System.out.println("Error" + input + "does not start with a keyword.");
        }
    }

    public WorkerThread(DatagramPacket datagramPacket, DatagramSocket socket) {
        this.datagramPacket = datagramPacket;
        this.socket = socket;
    }

    private void handleRead(String input) {
        input = input.substring(5);
        String fileName = input.split(",", 2)[0];
        int lineNo = Integer.parseInt(input.split(",", 2)[1]);
        CustomFile customFile = new CustomFile();
        String output = customFile.getLine(fileName, lineNo);

        try {
            Thread.sleep(5000);
            socket.send(new DatagramPacket(output.getBytes(), output.getBytes().length, datagramPacket.getAddress(), datagramPacket.getPort()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void handleWrite(String input) {
        input = input.substring(6);
        String fileName = input.split(",",3)[0];
        int lineNo = Integer.parseInt(input.split(",",3)[1]);
        String data= input.split(",",3)[2];
        CustomFile customFile = new CustomFile();
        customFile.addLine(fileName, lineNo,data);
        try {
            socket.send(new DatagramPacket("OK".getBytes(), 2,datagramPacket.getAddress(),datagramPacket.getPort()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
