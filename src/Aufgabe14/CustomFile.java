package Aufgabe14;

import java.io.*;

public class CustomFile {

    String getLine(String fileName, int lineNo) {
        File file = new File("src/Aufgabe14/Files", fileName);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            for (int i = 1; i < lineNo; i++) {
                bufferedReader.readLine();
            }
            return bufferedReader.readLine();
        } catch (FileNotFoundException e) {
            return "File not found.";
        } catch (IOException e) {
            return "Line does not exist";
        }
    }

    public void newFile(String fileName, String data) {
        File file = new File("src/Aufgabe14/Files", fileName);
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(data);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addLine(String fileName,int lineNo, String data) {
        File file = new File("src/Aufgabe14/Files", fileName);
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(data);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
