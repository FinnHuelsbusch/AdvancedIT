package Aufgabe15;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

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
            List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath()));
            fileContent.set(lineNo-1, data);
            Files.write(file.toPath(), fileContent, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
