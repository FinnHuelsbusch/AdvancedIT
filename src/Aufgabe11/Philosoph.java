package Aufgabe11;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Philosoph implements Runnable{

    int nr;
    ArrayList<Philosoph> philosophs;
    Semaphore philosophSemaphore;
    Semaphore werDarfAufDenTischSchauen;

    public Philosoph(int nr ,ArrayList<Philosoph> philosophs, Semaphore mutex) {
        this.nr = nr;
        this.philosophs = philosophs;
        philosophSemaphore = new Semaphore(1, true);
        this.werDarfAufDenTischSchauen = mutex;
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
            System.out.printf("Philosoph %d möchte gerne auf den Tisch schauen.%n", nr);
            werDarfAufDenTischSchauen.acquire();
            System.out.printf("Philosoph %d darf auf den Tisch schauen.%n", nr);

            System.out.printf("Philosoph %d möchte gerne Gabel %d.%n", nr,nr);
            philosophSemaphore.acquire();
            System.out.printf("Philosoph %d hat Gabel %d.%n", nr,nr);
            System.out.printf("Philosoph %d möchte gerne Gabel %d.%n", nr, (nr+1)%philosophs.size());
            philosophs.get((nr+1)%philosophs.size()).neighbourWantsToEat();
            System.out.printf("Philosoph %d hat Gabel %d.%n", nr, (nr+1)%philosophs.size());
            werDarfAufDenTischSchauen.release();
            System.out.printf("Philosoph %d gibt die Sicht frei und beginnt mit dem essen.%n", nr);
            Thread.sleep(((int) (Math.random() * 100)));
            System.out.printf("Philosoph %d ist mit dem essen fertig und legt sein Besteck zurück.%n", nr);
            philosophSemaphore.release();
            philosophs.get((nr+1)%philosophs.size()).neighbourDoneToEat();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void neighbourWantsToEat() throws InterruptedException {
        philosophSemaphore.acquire();
    }

    public void neighbourDoneToEat()  {
        philosophSemaphore.release();
    }



    public static void main(String[] args) {
        ForkManagement forkManagement = new ForkManagement(5);
        ArrayList<Philosoph> philosophs = new ArrayList<>();
        Semaphore mutex = new Semaphore(2, true);
        Philosoph p0 = new Philosoph(0, philosophs,mutex);
        Philosoph p1 = new Philosoph(1, philosophs,mutex);
        Philosoph p2 = new Philosoph(2, philosophs,mutex);
/*
        Philosoph p3 = new Philosoph(3, philosophs,mutex);
        Philosoph p4 = new Philosoph(4, philosophs,mutex);
*/

        philosophs.add(p0);
        philosophs.add(p1);
        philosophs.add(p2);
/*        philosophs.add(p3);
        philosophs.add(p4);*/

        ArrayList<Thread> threads = new ArrayList<>();
        for ( Philosoph p : philosophs ) {
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
}
