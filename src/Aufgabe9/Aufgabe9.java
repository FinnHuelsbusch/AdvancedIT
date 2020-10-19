package Aufgabe9;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

public class Aufgabe9 {
    public Aufgabe9(int length, int[] incomingArray) {
        System.out.println("Mit einem Array der Größe " + length + ".");
        int[] array = Arrays.copyOfRange(incomingArray, 0, length - 1);

        withThreds(array, 1);
        withThreds(array, 2);
        withThreds(array, 4);
        withThreds(array, 8);
        withThreds(array, 16);
        withThreds(array, 32);
        withThreds(array, 64);
        withThreds(array, 128);
        System.out.println("-----------------------------------------------------------------------------------------------");
    }

    public static void main(String[] args) {
        int[] array = new int[16384];

        for ( int i = 0; i < array.length; i++ ) {
            array[i] = (int) (Math.random() * 10000);
        }

        new Aufgabe9(32, array);
        new Aufgabe9(64, array);
        new Aufgabe9(128, array);
        new Aufgabe9(256, array);
        new Aufgabe9(512, array);
        new Aufgabe9(1024, array);
        new Aufgabe9(2048, array);
        new Aufgabe9(4096, array);
        new Aufgabe9(8192, array);
        new Aufgabe9(16384, array);
    }

    public void noThreads(int[] array) {
        long startTime = System.currentTimeMillis();
        BigInteger bigInteger = BigInteger.ONE;
        for ( int i : array ) {
            bigInteger = bigInteger.multiply(BigInteger.valueOf(i));
        }
        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("No Threads in: " + elapsedTime + "ms. Sum= " + bigInteger.toString());
    }

    public void withThreds(int[] array, int numberOfThread) {
        int lastEndIndex = 0;
        int sequenzeLength = array.length / numberOfThread;
        ArrayList<Thread> aufgabe9Threads = new ArrayList<>();
        ArrayList<Aufgabe9Runnable> aufgabe9Runnables = new ArrayList<>();
        for ( int i = 0; i < numberOfThread; i++ ) {
            Aufgabe9Runnable current = new Aufgabe9Runnable(array, lastEndIndex, sequenzeLength + lastEndIndex);
            aufgabe9Runnables.add(current);
            aufgabe9Threads.add(new Thread(current));
            lastEndIndex += sequenzeLength;
        }
        long startTime = System.currentTimeMillis();
        for ( Thread t : aufgabe9Threads ) {
            t.start();
        }
        for ( Thread t : aufgabe9Threads ) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        BigInteger sum = BigInteger.ONE;
        for ( Aufgabe9Runnable runnable : aufgabe9Runnables ) {
            sum = sum.multiply(runnable.getBigInteger());
        }
        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println(numberOfThread + "\tThreads in: " + elapsedTime + " ms.\t Sum= " + sum.toString());
    }
}
