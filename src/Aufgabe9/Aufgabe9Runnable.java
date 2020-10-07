package Aufgabe9;

import java.math.BigInteger;

public class Aufgabe9Runnable implements Runnable {
    private int[] array;
    private int startIndex, endIndex;
    private BigInteger bigInteger;

    @Override
    public void run() {
        bigInteger = BigInteger.ONE;
        for ( int i = startIndex; i < endIndex; i++) {
            bigInteger = bigInteger.multiply(BigInteger.valueOf(array[i]));
        }
    }

    public Aufgabe9Runnable(int[] array, int startIndex, int endIndex) {
        this.array = array;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public BigInteger getBigInteger() {
        return bigInteger;
    }
}
