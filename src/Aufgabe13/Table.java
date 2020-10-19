package Aufgabe13;

import java.util.Arrays;

public class Table {
    private final boolean[] forks;

    public Table(int size) {
        forks = new boolean[size];
        Arrays.fill(forks, true);
    }

    public synchronized void getForks(int philNr) {
        System.out.printf("Philosoph %d möchte gerne Gabeln.%n", philNr);
        while (!(forks[philNr] && forks[(philNr + 1) % forks.length])) {
            try {
                System.out.printf("Philosoph %d muss warten.%n", philNr);
                wait();
                System.out.printf("Philosoph %d versucht es erneut.%n", philNr);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.printf("Philosoph %d hat seine Gabeln bekommen.%n", philNr);
        forks[philNr] = false;
        forks[(philNr + 1) % forks.length] = false;
    }

    public synchronized void releaseForks(int philNr) {
        System.out.printf("Philosoph %d gibt seine Gabeln zurück.%n", philNr);
        forks[philNr] = true;
        forks[(philNr + 1) % forks.length] = true;
        notifyAll();
    }
}
