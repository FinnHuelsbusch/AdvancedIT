package Testate.Testat3;

import java.net.DatagramPacket;

public class Buffer {
    public final int size;
    private DatagramPacket[] buffer;
    private int nextfree, nextfull, ctr;

    public Buffer(int size) {
        // Initialisierung
        this.size = size;
        buffer = new DatagramPacket[size];
        nextfree = nextfull = ctr = 0;
    }

    public synchronized void insert(DatagramPacket datagramPacket) {
        // warten, solange der Buffer voll ist
        while (ctr == size) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // Element hinzufügen und Variablen entsprechend anpassen
        buffer[nextfree] = datagramPacket;
        nextfree = (nextfree + 1) % size;
        ctr++;
        notifyAll();
    }

    public synchronized DatagramPacket get() {
        // Warten solange der Buffer leer ist.
        while (ctr == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // Element entnehmen und Variablen Pflegen.
        DatagramPacket datagramPacket = buffer[nextfull];
        ctr--;
        nextfull = (nextfull + 1) % size;
        notifyAll();
        return datagramPacket;
    }


}
