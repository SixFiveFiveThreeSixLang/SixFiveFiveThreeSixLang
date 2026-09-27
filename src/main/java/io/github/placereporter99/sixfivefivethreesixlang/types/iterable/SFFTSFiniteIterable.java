package io.github.placereporter99.sixfivefivethreesixlang.types.iterable;

import io.github.placereporter99.sixfivefivethreesixlang.types.*;
import io.github.placereporter99.sixfivefivethreesixlang.types.helpers.emptyinterfaces.SFFTSIterable;
import io.github.placereporter99.sixfivefivethreesixlang.types.misc.SFFTSNull;
import io.github.placereporter99.sixfivefivethreesixlang.types.number.SFFTSInteger;

import java.util.*;

public class SFFTSFiniteIterable extends SFFTSObject<List<SFFTSObject<?>>> implements SFFTSIterable {

    public SFFTSFiniteIterable(SFFTSObject<?>... data) {
        super(new ArrayList<>(Arrays.stream(data).toList()));
    }

    public SFFTSObject<?> pop(int index) {
        return getData().remove(index);
    }

    public SFFTSObject<?> pop() {
        if (getData().isEmpty()) {
            return SFFTSNull.STACK_EMPTY;
        }
        return getData().removeLast();
    }

    public SFFTSObject<?> popHead() {
        if (getData().isEmpty()) {
            return SFFTSNull.STACK_EMPTY;
        }
        return getData().removeFirst();
    }

    public void append(SFFTSObject<?> o) {
        getData().addLast(o);
    }

    public void push(SFFTSObject<?> o) {
        append(o);
    }

    public void prepend(SFFTSObject<?> o) {
        getData().addFirst(o);
    }

    public void insert(int index, SFFTSObject<?> o) {
        getData().add(index, o);
    }

    public void concat(SFFTSFiniteIterable o) {
        getData().addAll(o.getData());
    }

    public SFFTSObject<?> get(int index) {
        return getData().get(index);
    }

    public SFFTSObject<?> peek() {
        return getData().getLast();
    }

    public SFFTSObject<?> peekHead() {
        return getData().getFirst();
    }

    public SFFTSInteger length() {
        return SFFTSInteger.create(getData().size());
    }

    @Override
    public boolean isTruthy() {
        return length().isTruthy();
    }
}
