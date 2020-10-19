package Aufgabe9;

import java.math.BigInteger;

public class Aufgabe9Runnable implements Runnable {
    private final int[] array;
    private final int startIndex;
    private final int endIndex;
    private BigInteger bigInteger;

    public Aufgabe9Runnable(int[] array, int startIndex, int endIndex) {
        this.array = array;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    public void run() {
        bigInteger = BigInteger.ONE;
        for ( int i = startIndex; i < endIndex; i++ ) {
            bigInteger = bigInteger.multiply(BigInteger.valueOf(array[i]));
        }
    }

    public BigInteger getBigInteger() {
        return bigInteger;
    }
}
