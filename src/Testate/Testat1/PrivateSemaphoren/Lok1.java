package Testate.Testat1.PrivateSemaphoren;


public class Lok1 implements Lok {

    int zeitInMillisekundenImAbschnitt, zeitInMillisekundenAußerhalb;
    private final AbschnittsVerwaltung abschnittsVerwaltung;


    // Füllen der Variablen
    public Lok1(AbschnittsVerwaltung abschnittsVerwaltung, int zeitInMillisekundenImAbschnitt, int zeitInMillisekundenAußerhalb) {
        this.abschnittsVerwaltung = abschnittsVerwaltung;
        this.zeitInMillisekundenAußerhalb = zeitInMillisekundenAußerhalb;
        this.zeitInMillisekundenImAbschnitt = zeitInMillisekundenImAbschnitt;
    }

    private void fahren() {
        System.out.printf("Lok 1 beginnt mit der Fahrt.%n");
        while (true) {
            try {
                //Fahren außerhalb des gemeinsamen Abschnitts
                Thread.sleep(zeitInMillisekundenAußerhalb);
                //Einfahrt in den gemeinsamen Abschnitt beantragen und auf Freigabe warten
                abschnittsVerwaltung.enterLok1();
                //Durch den gemeinsamen Abschnitt fahren
                Thread.sleep(zeitInMillisekundenImAbschnitt);
                //Aus dem gemeinsamen Abschnitt ausfahren
                abschnittsVerwaltung.exitLok1();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void fahrenRandom() {
        System.out.printf("Lok 1 beginnt mit der Fahrt.%n");
        while (true) {
            try {
                //fahren außerhalb des gemeinsamen Abschnitts
                Thread.sleep((int) Math.random() * 10000);
                //Einfahrt in den gemeinsamen Abschnitt beantragen und auf Freigabe warten
                abschnittsVerwaltung.enterLok1();
                //Durch den gemeinsamen Abschnitt fahren
                Thread.sleep((int) Math.random() * 10000);
                //Aus dem gemeinsamen Abschnitt ausfahren
                abschnittsVerwaltung.exitLok1();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void run() {
        fahren();
    }
}
