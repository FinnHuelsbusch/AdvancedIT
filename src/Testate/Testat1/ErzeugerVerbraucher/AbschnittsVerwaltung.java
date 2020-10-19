package Testate.Testat1.ErzeugerVerbraucher;

import java.util.concurrent.Semaphore;

public class AbschnittsVerwaltung {
    Semaphore full, empty;

    public AbschnittsVerwaltung(int size) {
        //Semaphore für den gemeinsamen Abschnitt
        empty = new Semaphore(size, true);
        full = new Semaphore(0, true);
    }

    public void enterLok0() {
        try {
            System.out.printf("Lok 0 beantragt Einfahrt in den gemeinsamen Abschnitt.%n");
            empty.acquire();
            System.out.printf("Lok 0 hat Einfahrt in den gemeinsamen Abschnitt erhalten.%n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exitLok0() {
        System.out.printf("Lok 0 fährt aus dem kritischen Abschnitt aus.%n");
        full.release();
        System.out.printf("Lok 0 gibt den gemeinsamen Abschnitt wieder für Lok1 frei.%n");
    }

    public void enterLok1() {
        try {
            System.out.printf("Lok 1 beantragt Einfahrt in den gemeinsamen Abschnitt.%n");
            full.acquire();
            System.out.printf("Lok 1 hat Einfahrt in den kirttischen Abschnitt erhalten.%n");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exitLok1() {
        System.out.printf("Lok 1 fährt aus dem kritischen Abschnitt aus.%n");
        empty.release();
        System.out.printf("Lok 1 gibt den gemeinsamen Abschnitt wieder für Lok 0 frei.%n");
    }
}
