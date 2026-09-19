package io.github.placereporter99.sixfivefivethreesixlang.instructions;

import java.util.*;

interface TokenOrSingle {}

class Single implements TokenOrSingle {
    public final short item;
    public Single(short item) {
        this.item = item;
    }
}

class Token implements TokenOrSingle {
    public List<TokenOrSingle> items = new ArrayList<>();
}

final class BracketMatcher {
    private final Short[] starts;
    private final Short[] ends;

    private final List<Short> items = new ArrayList<>();

    public BracketMatcher(Short[] starts, Short[] ends) {
        this.starts = starts;
        this.ends = ends;
    }

    public void add(short item) {
        items.add(item);
    }

    public Token match() {
        var tokens = new ArrayList<Token>();
        var rootToken = new Token();
        tokens.add(rootToken);
        for (short item : items) {
            if (List.of(starts).contains(item)) {
                var t = new Token();
                t.items.add(new Single(item));
                tokens.getLast().items.add(t);
            } else if (List.of(ends).contains(item)) {
                tokens.getLast().items.add(new Single(item));
                tokens.removeLast();
            } else {
                tokens.getLast().items.add(new Single(item));
            }
        }
        return rootToken;
    }
}

final public class Parser {
    private final List<Short> structureStarts = new ArrayList<>();
    private final List<Short> structureEnds = new ArrayList<>();

    public void addStarting(short starting) {
        structureStarts.add(starting);
    }

    public void addEnding(short ending) {
        structureEnds.add(ending);
    }

    public List<List<Short>> parse(short[] code) {
        var a = new ArrayList<List<Short>>();
        var b = new ArrayList<Short>();
        var inStructure = false;
        for (short item : code) {
            if (structureStarts.contains(item)) {
                inStructure = true;
                b.add(item);
            } else if (inStructure & structureEnds.contains(item)) {
                inStructure = false;
                b.add(item);
                a.add(b);
                b = new ArrayList<>();
            } else {
                b.add(item);
                a.add(b);
                b = new ArrayList<>();
            }
        }
        return a;
    }
}
