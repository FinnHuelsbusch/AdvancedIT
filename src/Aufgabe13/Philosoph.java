package Aufgabe13;

import java.util.ArrayList;

public class Philosoph implements Runnable {

    private final Table table;
    private final int nr;

    public static void main(String[] args) {
        Table table =  new Table(5);
        ArrayList<Philosoph> philosophs = new ArrayList<>();
        ArrayList<Thread> threads = new ArrayList<>();
        for ( int i = 0; i <5; i++ ) {
            philosophs.add(new Philosoph(i, table));
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

    public Philosoph(int nr, Table table) {
        this.nr = nr;
        this.table = table;
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
            table.getForks( nr);
            Thread.sleep(((int) (Math.random() * 100)));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            table.releaseForks(nr);
        }
    }
}
