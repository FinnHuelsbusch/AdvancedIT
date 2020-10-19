package Testate.Testat1.PrivateSemaphoren;

import java.util.ArrayList;

public class Test {

    Runnable lok0, lok1;
    AbschnittsVerwaltung abschnittsVerwaltung;

    public Test() {
        abschnittsVerwaltung = new AbschnittsVerwaltung(2);


        //Lok 0 ist überall langsamer
        lok0 = new Lok0(abschnittsVerwaltung, 3000, 3000);
        lok1 = new Lok1(abschnittsVerwaltung, 2500, 2500);

        //Lok 0 ist im Abschnitt schneller als Lok 1 außerhalb langsamer
        lok0 = new Lok0(abschnittsVerwaltung, 2000, 3000);
        lok1 = new Lok1(abschnittsVerwaltung, 2500, 2500);

        //Lok 0 ist im Abschnitt langsamer als Lok 1 außerhalb schneller
        lok0 = new Lok0(abschnittsVerwaltung, 3000, 2000);
        lok1 = new Lok1(abschnittsVerwaltung, 2500, 2500);

        //Lok0 ist in jedem Abschnitt schneller als Lok 1
        lok0 = new Lok0(abschnittsVerwaltung, 2000, 2000);
        lok1 = new Lok1(abschnittsVerwaltung, 2500, 2500);

        //Lok 0 ist wesentlich langsamer als Lok 1
        lok0 = new Lok0(abschnittsVerwaltung, 2500, 2500);
        lok1 = new Lok1(abschnittsVerwaltung, 1, 1);

        //Lok 0 ist wesentlich schneller als Lok 1
        lok0 = new Lok0(abschnittsVerwaltung, 1, 1);
        lok1 = new Lok1(abschnittsVerwaltung, 2500, 2500);

        //Anlegen einer List von Thread, in der anschließend die Loks gespeichert werden
        ArrayList<Thread> loks = new ArrayList<>();
        loks.add(new Thread(lok0));
        loks.add(new Thread(lok1));

        //Anfangen zu spielen
        for ( Thread t : loks ) {
            t.start();
        }


    }

    public static void main(String[] args) {
        new Test();
    }
}
