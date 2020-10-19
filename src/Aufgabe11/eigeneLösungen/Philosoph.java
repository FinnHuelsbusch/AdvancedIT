package Aufgabe11.eigeneLösungen;

import java.util.ArrayList;

public class Philosoph implements Runnable{

    int nr;
    Essenverwaltung essenverwaltung;

    public Philosoph(int nr, Essenverwaltung essenverwaltung) {
        this.nr = nr;
        this.essenverwaltung = essenverwaltung;
    }

    @Override
    public void run() {
        for ( int i = 0; i <3; i++ ) {
            essenverwaltung.eat(nr);
            try {
                Thread.sleep(((int) Math.random() *100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        int size = 5;
        Essenverwaltung essenverwaltung = new Essenverwaltung(5);
        ArrayList<Thread> threads = new ArrayList<>();
        for ( int i = 0; i < size; i++ ) {
            threads.add(new Thread(new Philosoph(i, essenverwaltung)));
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
