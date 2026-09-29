package io.github.placereporter99.sixfivefivethreesixlang.types.enums;

public enum Base10Digit {
    ZERO("0"),
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9");

    private final String value;
    private final int valueInt;

    Base10Digit(String value) {
        this.value = value;
        this.valueInt = Integer.parseInt(value);
    }

    public String getString() {
        return value;
    }

    public String toString() {
        return getString();
    }

    public int getInt() {
        return valueInt;
    }
}
