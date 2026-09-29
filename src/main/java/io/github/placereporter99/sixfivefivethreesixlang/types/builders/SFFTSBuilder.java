package io.github.placereporter99.sixfivefivethreesixlang.types.builders;

import io.github.placereporter99.sixfivefivethreesixlang.types.SFFTSObject;

import java.util.*;
import java.util.stream.Collectors;

abstract public class SFFTSBuilder<InternalType, TargetType extends SFFTSObject<?>> extends SFFTSObject<Map<String, Component<InternalType>>> {
    private String current = "";

    public SFFTSBuilder() {
        super(new HashMap<>());
    }

    public SFFTSBuilder(Map<String, Component<InternalType>> data) {
        super(data);
    }

    protected Component<InternalType> get(String name) {
        getData().putIfAbsent(name, new Component<>(name));
        return getData().get(name);
    }

    public void setDefault(String name) {
        current = name;
    }

    public String getDefault(String name) {
        return current;
    }

    public void add(String name, InternalType item) {
        get(name).addItem(item);
    }

    public void add(InternalType item) {
        add(current, item);
    }

    @Override
    public boolean isTruthy() {
        return getData().values().stream().anyMatch(Component::isTruthy);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " with contents:\n" + getData().entrySet().stream().map(x -> "Component " + x.getKey() + " has " + x.getValue()).collect(Collectors.joining("\n"));
    }

    abstract public TargetType build();
}
