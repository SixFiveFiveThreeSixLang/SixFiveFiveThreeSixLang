package io.github.placereporter99.sixfivefivethreesixlang.types.builders;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Component<T> {
    private final List<T> items = new CopyOnWriteArrayList<>();
    private final String name;

    public Component(String name) {
        this.name = name;
    }

    public void addItem(T item) {
        items.add(item);
    }

    public List<T> getItems() {
        return new ArrayList<>(items);
    }

    public String getName() {
        return name;
    }

    public boolean isTruthy() {
        return !items.isEmpty();
    }

    public String toString() {
        return items.toString();
    }
}
