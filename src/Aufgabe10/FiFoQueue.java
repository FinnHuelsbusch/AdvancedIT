package Aufgabe10;

import java.util.concurrent.Semaphore;

public class FiFoQueue {

    static Semaphore mutex = new Semaphore(1, true);
    Element firstElement;

    public void put(String stringToInsert) {
        try {
            mutex.acquire();
            if (firstElement == null) {
                firstElement = new Element(stringToInsert);
            } else {
                getLastElement().setNextElement(new Element(stringToInsert));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mutex.release();
        }



        /*if (firstElement == null) {
            firstElement = new Element(stringToInsert);
        } else {
            getLastElement().setNextElement(new Element(stringToInsert));
        }*/

    }

    public String get() {

        /*Element currentElement = firstElement;
        firstElement = firstElement.getNextElement();
        if (firstElement != null) {
            return currentElement.getValue();
        } else {
            return null;
        }*/
        try {
            mutex.acquire();
            Element currentElement = firstElement;
            if (currentElement != null) {
                mutex.release();
                firstElement = firstElement.getNextElement();
                return currentElement.getValue();
            } else {
                mutex.release();
                return null;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mutex.release();
        return null;
    }


    private Element getLastElement() {
        Element currentElement = firstElement;
        while (currentElement.getNextElement() != null) {
            currentElement = currentElement.getNextElement();
        }
        return currentElement;
    }
}
