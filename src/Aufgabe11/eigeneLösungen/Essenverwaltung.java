package Aufgabe11.eigeneLösungen;

import java.util.concurrent.Semaphore;

public class Essenverwaltung implements Runnable {
    enum Status {
        think,
        eat,
        hungry
    }

    private Status[] status;
    private Semaphore[] privateSemaphores;
    private Semaphore mutex;

    public Essenverwaltung(int size) {
        status = new Status[size];
        privateSemaphores = new Semaphore[size];
        for ( int i = 0; i < size; i++ ) {
            status[i] = Status.think;
            privateSemaphores[i] = new Semaphore(0, true);
        }
        mutex = new Semaphore(1, true);
    }

    public void eat(int nr ) {
        try {
            mutex.acquire();
            status[nr] = Status.hungry;
            mutex.release();
            System.out.printf("Philosoph %d ist hungrig.%n" , nr);
            test(nr);
            privateSemaphores[nr].acquire();
            System.out.printf("Philosoph %d isst gerade.%n" , nr);
            Thread.sleep(((int) (Math.random() * 100)));
            System.out.printf("Philosoph %d denkt gerade.%n" , nr);
            status[nr] = Status.think;
            test((nr + 1) % privateSemaphores.length);
            test((nr + privateSemaphores.length-1) % privateSemaphores.length);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void test(int nr) {
        try {
            mutex.acquire();
            if (status[(nr + 1) % privateSemaphores.length] != Status.eat &&
                status[(nr +privateSemaphores.length-1) % privateSemaphores.length] != Status.eat &&
                status[nr] == Status.hungry)
            {
                status[nr] = Status.eat;
                privateSemaphores[nr].release();
            }
            mutex.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {

        for ( int i = 0; i < 4; i++ ) {

        }
    }


    public static void main(String[] args) {
        new Essenverwaltung(5);
    }
}
