package Aufgabe10;

public class Element {
    String value;
    Element nextElement;

    public Element(String value) {
        this.value = value;
    }

    public Element getNextElement() {
        return nextElement;
    }

    public void setNextElement(Element nextElement) {
        this.nextElement = nextElement;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
