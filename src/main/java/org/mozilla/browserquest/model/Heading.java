package org.mozilla.browserquest.model;

public enum Heading {

    RIGHT(1),
    BOTTOM(2),
    LEFT(3),
    TOP(4);

    private int value;

    Heading(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
