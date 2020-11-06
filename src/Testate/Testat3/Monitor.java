package Testate.Testat3;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Monitor {
    private int numReaders = 0;
    private int numWriters = 0;
    private File file;
    private static final String FILEPATH = "src/Testate/Testat3/Files";
    private boolean activeWriter;

    public Monitor(String filename) {
        file = new File(FILEPATH, filename);
    }

    private synchronized void prepareToRead () {
        while ( numWriters > 0 ) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        numReaders++;
    }

    private synchronized void doneReading () {
        numReaders--;
        notifyAll();
    }

    public String readMethod(int line) {

        //Leserechte holen
        System.out.println("Zeile " + line +" aus Datei " +file.getName()+" soll gelesen werden.");
        prepareToRead ();
        System.out.println("Zeile " +line +" aus Datei " +file.getName()+" wird gelesen werden.");

        //warten für Tests
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //einlesen der Datei
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            for (int i = 1; i < line-1; i++) {
                bufferedReader.readLine();
            }
            String content = bufferedReader.readLine();
            return Objects.requireNonNullElse(content, "Line does not exist");

        } catch (FileNotFoundException e) {
            return "File not found.";
        } catch (IOException e) {
            return "Line does not exist";
        }finally {
            //schließen der Reader und Rückgabe der Werte. Außerdem Freigabe der Datei.
            System.out.println("Zeile " +line +" aus Datei " +file.getName()+" fertig gelesen werden.");
            doneReading();
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {

            }
        }

    }

    private synchronized void prepareToWrite () {
        numWriters++;
        while ( numReaders != 0 || activeWriter) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        activeWriter = true;
    }

    private synchronized void doneWriting () {
        numWriters--;
        activeWriter = false;
        notifyAll();
    }

    public void writeMethod (int line, String data) throws IOException {

        // Schreibrechte erhalten
        System.out.println("Zeile " + line +" aus Datei " +file.getName()+" soll ersetzt werden.");
        prepareToWrite ();
        System.out.println("Zeile " +line +" wird in Datei " +file.getName()+" ersetzt.");


        // Warten für tests
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        List<String> fileContent = null;
        // Einlesen der Datei und Zeile ersetzen der Zeile
        try {
            fileContent = new ArrayList<>(Files.readAllLines(file.toPath()));
            fileContent.set(line - 1, data);
            writeToFile(file, fileContent);
        } catch (IndexOutOfBoundsException e) {
            // hinzufügen neuer Zeilen wenn sie noch nicht existiert
            if (fileContent != null) {
                while (fileContent.size() < line-1) {
                    fileContent.add("");
                }
                fileContent.add(data);
                try {
                    writeToFile(file, fileContent);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }finally {
            System.out.println("Zeile " + line +" in Datei " +file.getName()+" ersetzt.");
            doneWriting ();
        }

    }

    private void writeToFile(File file, List<String> content) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        for ( int i = 0; i < content.size()-1; i++) {
            writer.write(content.get(i));
            writer.newLine();
        }
        writer.write(content.get(content.size()-1));
        writer.flush();
        writer.close();
    }
}
