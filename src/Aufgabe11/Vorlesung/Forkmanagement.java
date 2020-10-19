package Aufgabe11.Vorlesung;

import java.util.concurrent.Semaphore;

public class Forkmanagement {
    Semaphore[] forkmanagement;
    Semaphore mutex;



    public Forkmanagement(int size) {
        forkmanagement = new Semaphore[size];
        mutex = new Semaphore(1, true);
        for ( int i = 0; i < size; i++ ) {
            forkmanagement[i] = new Semaphore(1, true);
        }
    }


    public void returnForks(int nr) {
        forkmanagement[nr].release();
        forkmanagement[(nr + 1) % forkmanagement.length].release();
        System.out.printf("Essenverwaltung %d ist mit dem essen fertig und legt sein Besteck zurück.%n", nr);
    }

    public void getForksTwoPhaseLocking(int nr) throws Exception {
        if (nr > forkmanagement.length-1) {
            throw new Exception();
        }
        System.out.printf("Essenverwaltung %d möchte gerne auf den Tisch schauen.%n", nr);
        mutex.acquire();
        System.out.printf("Essenverwaltung %d darf auf den Tisch schauen.%n", nr);
        System.out.printf("Essenverwaltung %d möchte gerne Gabel %d.%n", nr,nr);
        forkmanagement[nr].acquire();
        System.out.printf("Essenverwaltung %d hat Gabel %d.%n", nr,nr);
        System.out.printf("Essenverwaltung %d möchte gerne Gabel %d.%n", nr, (nr + 1) % forkmanagement.length);
        forkmanagement[(nr + 1) % forkmanagement.length].acquire();
        System.out.printf("Essenverwaltung %d hat Gabel %d.%n", nr, (nr + 1) % forkmanagement.length);
        System.out.printf("Essenverwaltung %d gibt die Sicht frei und beginnt mit dem essen.%n", nr);
        mutex.release();
    }

    public void getForksResourceOrdering(int nr) throws Exception {
        if (nr > forkmanagement.length-1) {
            throw new Exception();
        }
        if (nr == forkmanagement.length - 1) {
            System.out.printf("Essenverwaltung %d möchte gerne Gabel %d und Gabel %d.%n", nr,nr,(nr + 1) % forkmanagement.length);
            forkmanagement[(nr + 1) % forkmanagement.length].acquire();
            forkmanagement[nr].acquire();
            System.out.printf("Essenverwaltung %d hat Gabel %d und Gabel %d.%n", nr,nr,(nr + 1) % forkmanagement.length);
        }
        else {
            System.out.printf("Essenverwaltung %d möchte gerne Gabel %d und Gabel %d.%n", nr,nr,(nr + 1) % forkmanagement.length);
            forkmanagement[nr].acquire();
            forkmanagement[(nr + 1) % forkmanagement.length].acquire();
            System.out.printf("Essenverwaltung %d hat Gabel %d und Gabel %d.%n", nr,nr,(nr + 1) % forkmanagement.length);
        }
    }
}
