package Testate.Testat1.PrivateSemaphoren;



public class Lok0 implements Lok {

    private AbschnittsVerwaltung abschnittsVerwaltung;
    int zeitInMillisekundenImAbschnitt,zeitInMillisekundenAußerhalb;


    // Füllen der Variablen
    public Lok0(AbschnittsVerwaltung abschnittsVerwaltung, int zeitInMillisekundenImAbschnitt, int zeitInMillisekundenAußerhalb) {
        this.abschnittsVerwaltung = abschnittsVerwaltung;
        this.zeitInMillisekundenAußerhalb = zeitInMillisekundenAußerhalb;
        this.zeitInMillisekundenImAbschnitt = zeitInMillisekundenImAbschnitt;
    }

    private void fahren() {
        System.out.printf("Lok 0 beginnt mit der Fahrt.%n");
        while (true) {
            try {
                //Fahren außerhalb des gemeinsamen Abschnitts
                Thread.sleep(zeitInMillisekundenAußerhalb);
                //Einfahrt in den gemeinsamen Abschnitt beantragen und auf Freigabe warten
                abschnittsVerwaltung.enterLok0();
                //Durch den gemeinsamen Abschnitt fahren
                Thread.sleep(zeitInMillisekundenImAbschnitt);
                //Aus dem gemeinsamen Abschnitt ausfahren
                abschnittsVerwaltung.exitLok0();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void fahrenRandom() {
        System.out.printf("Lok 0 beginnt mit der Fahrt.%n");
        while (true) {
            try {
                //fahren außerhalb des gemeinsamen Abschnitts
                Thread.sleep((int)Math.random()*10000);
                //Einfahrt in den gemeinsamen Abschnitt beantragen und auf Freigabe warten
                abschnittsVerwaltung.enterLok0();
                //Durch den gemeinsamen Abschnitt fahren
                Thread.sleep((int)Math.random()*10000);
                //Aus dem gemeinsamen Abschnitt ausfahren
                abschnittsVerwaltung.exitLok0();
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
