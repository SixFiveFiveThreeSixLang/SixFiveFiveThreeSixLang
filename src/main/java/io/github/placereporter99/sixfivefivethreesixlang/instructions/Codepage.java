package io.github.placereporter99.sixfivefivethreesixlang.instructions;

import java.util.*;

public class Codepage {
    private static final String[] mappings = new String[65536];

    private Codepage() {
        throw new AssertionError("This class cannot be instantiated.");
    }

    public static String shortToChar(short value) {
        var s = mappings[value & 0xFFFF];
        if (s == null) {
            throw new IllegalArgumentException("Short value does not have assigned character.");
        }
        return s;
    }

    public static short charToShort(String value) {
        var s = Arrays.asList(mappings).indexOf(value);
        if (s == -1) {
            throw new IllegalArgumentException("Character does not have assigned short value.");
        }
        return (short) s;
    }

    public static String getCodepage() {
        StringBuilder s = new StringBuilder();
        for (var c : mappings) {
            s.append(c == null ? "" : c);
        }
        return s.toString();
    }

    private static void put(String character, short value) {
        if (Arrays.asList(mappings).contains(character)) {
            throw new IllegalArgumentException("Character already assigned.");
        } else if (mappings[value & 0xFFFF] != null) {
            throw new IllegalArgumentException("Value already assigned.");
        }
        mappings[value & 0xFFFF] = character;
    }

    private static void put(String character, int value) {
        put(character, (short) value);
    }

    private static void put(char character, short value) {
        put(String.valueOf(character), value);
    }

    private static void put(char character, int value) {
        put(character, (short) value);
    }

    static {
        put('␀', 0);
        put('␁', 1);
        put('␂', 2);
        put('␃', 3);
        put('␄', 4);
        put('␅', 5);
        put('␆', 6);
        put('␇', 7);
        put('␈', 8);
        put('␉', 9);
        put('␊', 10);
        put('␋', 11);
        put('␌', 12);
        put('␍', 13);
        put('␎', 14);
        put('␏', 15);
        put('␐', 16);
        put('␑', 17);
        put('␒', 18);
        put('␓', 19);
        put('␔', 20);
        put('␕', 21);
        put('␖', 22);
        put('␗', 23);
        put('␘', 24);
        put('␙', 25);
        put('␚', 26);
        put('␛', 27);
        put('␜', 28);
        put('␝', 29);
        put('␞', 30);
        put('␟', 31);
        put('␠', 32);
        put('㉝', 33);
        put('㉞', 34);
        put('㉟', 35);
        put('㊱', 36);
        put('㊲', 37);
        put('㊳', 38);
        put('㊴', 39);
        put('㊵', 40);
        put('㊶', 41);
        put('㊷', 42);
        put('㊸', 43);
        put('㊹', 44);
        put('㊺', 45);
        put('㊻', 46);
        put('㊼', 47);
        put('㊽', 48);
        put('㊾', 49);
        put('㊿', 50);
        put('␡', 127);
        put('⧛', 65500);
        put('⧚', 65535);
    }
}
