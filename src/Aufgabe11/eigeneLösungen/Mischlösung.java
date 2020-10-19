package Aufgabe11.eigeneLösungen;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Mischlösung implements Runnable {

    int nr;
    ArrayList<Mischlösung> philosophs;
    Semaphore philosophSemaphore;
    Semaphore werDarfAufDenTischSchauen;

    public Mischlösung(int nr, ArrayList<Mischlösung> philosophs, Semaphore mutex) {
        this.nr = nr;
        this.philosophs = philosophs;
        philosophSemaphore = new Semaphore(1, true);
        this.werDarfAufDenTischSchauen = mutex;
    }

    public static void main(String[] args) {

        ArrayList<Mischlösung> philosophs = new ArrayList<>();
        Semaphore mutex = new Semaphore(2, true);
        Mischlösung p0 = new Mischlösung(0, philosophs, mutex);
        Mischlösung p1 = new Mischlösung(1, philosophs, mutex);
        Mischlösung p2 = new Mischlösung(2, philosophs, mutex);
/*
        Essenverwaltung p3 = new Essenverwaltung(3, philosophs,mutex);
        Essenverwaltung p4 = new Essenverwaltung(4, philosophs,mutex);
*/

        philosophs.add(p0);
        philosophs.add(p1);
        philosophs.add(p2);
/*        philosophs.add(p3);
        philosophs.add(p4);*/

        ArrayList<Thread> threads = new ArrayList<>();
        for ( Mischlösung p : philosophs ) {
            threads.add(new Thread(p));
        }
        for ( Thread t : threads ) {
            t.start();
        }
        for ( Thread t : threads ) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {

        for ( int i = 0; i < 3; i++ ) {
            eat();
            think();
        }

    }

    private void think() {
        try {
            Thread.sleep(((int) (Math.random() * 100)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void eat() {
        try {
            System.out.printf("Essenverwaltung %d möchte gerne auf den Tisch schauen.%n", nr);
            werDarfAufDenTischSchauen.acquire();
            System.out.printf("Essenverwaltung %d darf auf den Tisch schauen.%n", nr);
            System.out.printf("Essenverwaltung %d möchte gerne Gabel %d.%n", nr, nr);
            philosophSemaphore.acquire();
            System.out.printf("Essenverwaltung %d hat Gabel %d.%n", nr, nr);
            System.out.printf("Essenverwaltung %d möchte gerne Gabel %d.%n", nr, (nr + 1) % philosophs.size());
            philosophs.get((nr + 1) % philosophs.size()).neighbourWantsToEat();
            System.out.printf("Essenverwaltung %d hat Gabel %d.%n", nr, (nr + 1) % philosophs.size());
            werDarfAufDenTischSchauen.release();
            System.out.printf("Essenverwaltung %d gibt die Sicht frei und beginnt mit dem essen.%n", nr);
            Thread.sleep(((int) (Math.random() * 100)));
            System.out.printf("Essenverwaltung %d ist mit dem essen fertig und legt sein Besteck zurück.%n", nr);
            philosophSemaphore.release();
            philosophs.get((nr + 1) % philosophs.size()).neighbourDoneToEat();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void neighbourWantsToEat() throws InterruptedException {
        philosophSemaphore.acquire();
    }

    public void neighbourDoneToEat() {
        philosophSemaphore.release();
    }
}
