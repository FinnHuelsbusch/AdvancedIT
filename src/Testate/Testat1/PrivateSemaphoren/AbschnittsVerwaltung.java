package Testate.Testat1.PrivateSemaphoren;

import java.util.Arrays;
import java.util.concurrent.Semaphore;

public class AbschnittsVerwaltung {
    Semaphore[] privateSemaphore;
    Semaphore mutex;
    boolean abschnittFrei;
    boolean[] waiting;
    int lastIndex; // gibt an, welche Lok den gemeinsamen Abschnitt als letztes verlassen hat

    public AbschnittsVerwaltung(int size) {
        // der lastIndex wird auf 1 gesetzt, da so Lok 0 als erstes Einfahrt in den gemeinsamen Abschnitt erhält
        lastIndex = 1;

        // am Anfang ist der Abschnitt frei
        abschnittFrei = true;

        // Initialisierung gemäß der Vorlage
        mutex = new Semaphore(1, true);

        privateSemaphore = new Semaphore[size];
        waiting = new boolean[size];
        Arrays.fill(waiting, false);
        for ( int i = 0; i < size; i++ ) {
            privateSemaphore[i] = new Semaphore(0, true);
        }
    }

    public void enterLok0() {
        try {
            // Eintirttsprotokoll für Lok 0
            System.out.printf("Lok 0 möchte in den kritischen Abschnitt.%n");
            mutex.acquire();
            // Prüfen, ob der Abschnitt frei ist und Lok 1 als letztes den Abschnitt verlassen hat
            System.out.printf("Lok 0 prüft, ob sie druchfahren kann.%n");
            if (abschnittFrei && lastIndex == 1) {
                // Später nicht warten
                privateSemaphore[0].release();
                // Der Abschnitt wird belegt
                abschnittFrei = false;
                System.out.printf("Lok 0 darf durchfahren.%n");
            } else {
                // Wunsch auf Einfahrt vermerken
                waiting[0] = true;
                System.out.printf("Lok 0 muss warten.%n");
            }
            System.out.printf("Lok 0 verlässt den kritischen Abschnitt.%n");
            mutex.release();
            // wenn nötig auf Einfahrt warten
            System.out.printf("Lok 0 überprüft ihr privates Semaphore.%n");
            privateSemaphore[0].acquire();
            System.out.printf("Lok 0 hat Einfahrt in den gemeinsamen Abschnitt erhalten.%n");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void exitLok0() {
        try {
            // Austrittsprotokoll für Lok 0
            System.out.printf("Lok 0 möchte den kritischen Abschnitt betreten.%n");
            mutex.acquire();
            System.out.printf("Lok 0 fährt aus dem gemeinsamen Abschnitt aus.%n");
            // Strecke freigeben
            abschnittFrei = true;
            // Index der Lok ändern, die als letztes durch den Abschnitt gefahren ist
            lastIndex = 0;
            // Wenn Lok 1 wartet wird sie aufgeweckt und die Daten angepasst
            System.out.printf("Lok 0 prüft, ob Lok 1 wartet.%n");
            if (waiting[1]) {
                System.out.printf("Lok 1 wartet und wird aufgeweckt.%n");
                abschnittFrei = false;
                waiting[1] = false;
                privateSemaphore[1].release();
            }
            System.out.printf("Lok 0 verlässt den kritischen Abschnitt.%n");
            mutex.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void enterLok1() {
        try {
            // Eintrittsprotokoll für Lok 1
            System.out.printf("Lok 1 möchte in den kritischen Abschnitt.%n");
            mutex.acquire();
            // Prüfen ob der Abschnitt für Lok 1 befahrbar ist
            System.out.printf("Lok 1 prüft, ob sie druchfahren kann.%n");
            if (abschnittFrei && lastIndex == 0) {
                // später nicht warten
                privateSemaphore[1].release();
                // Abschnitt sperren
                abschnittFrei = false;
                System.out.printf("Lok 1 darf durchfahren.%n");
            } else {
                // Wunsch auf Einfahrt vermerken
                waiting[1] = true;
                System.out.printf("Lok 1 muss warten.%n");
            }
            System.out.printf("Lok 1 verlässt den kritischen Abschnitt.%n");
            mutex.release();
            // ggf. warten
            System.out.printf("Lok 1 überprüft ihr privates Semaphore.%n");
            privateSemaphore[1].acquire();
            System.out.printf("Lok 1 hat Einfahrt in den gemeinsamen Abschnitt erhalten.%n");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void exitLok1() {
        try {
            // Austrittsprotokoll für Lok 1
            System.out.printf("Lok 1 möchte den kritischen Abschnitt betreten.%n");
            mutex.acquire();
            // Abschnitt freigeben
            System.out.printf("Lok 1 fährt aus dem gemeinsamen Abschnitt aus.%n");
            abschnittFrei = true;
            // Index der Lok ändern, die als letztes durch den Abschnitt gefahren ist
            lastIndex = 1;
            // Wenn Lok 0 wartet Lok 0 aufwecken und die Daten anpassen
            System.out.printf("Lok 1 prüft, ob Lok 0 wartet.%n");
            if (waiting[0]) {
                System.out.printf("Lok 0 wartet und wird aufgeweckt.%n");
                abschnittFrei = false;
                waiting[0] = false;
                privateSemaphore[0].release();
            }
            System.out.printf("Lok 1 verlässt den kritischen Abschnitt.%n");
            mutex.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
