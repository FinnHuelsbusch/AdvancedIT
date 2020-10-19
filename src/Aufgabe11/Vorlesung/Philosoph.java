package Aufgabe11.Vorlesung;

import java.util.ArrayList;

public class Philosoph implements Runnable {

    static Forkmanagement forkmanagement;
    private int nr;

    public static void main(String[] args) {
       forkmanagement =  new Forkmanagement(5);
        ArrayList<Philosoph> philosophs = new ArrayList<>();
        ArrayList<Thread> threads = new ArrayList<>();
        for ( int i = 0; i <5; i++ ) {
            philosophs.add(new Philosoph(i));
            threads.add(new Thread(philosophs.get(philosophs.size() - 1)));
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

    public Philosoph(int nr) {
        this.nr = nr;
    }

    @Override
    public void run() {
        for ( int i = 0; i < 4; i++ ) {
            eat();
            think();
        }
    }

    public void think() {
        try {
            Thread.sleep(((int) (Math.random() * 100)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void eat() {
        try {
            forkmanagement.getForksResourceOrdering(nr);
            Thread.sleep(((int) (Math.random() * 100)));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            forkmanagement.returnForks(nr);
        }
    }
}
