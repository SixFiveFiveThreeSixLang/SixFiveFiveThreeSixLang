package io.github.placereporter99.sixfivefivethreesixlang.types;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.stream.*;

import io.github.placereporter99.sixfivefivethreesixlang.types.helpers.emptyinterfaces.*;

abstract public class SFFTSObject<T> implements SFFTSObjectOrNull {
    private final T data;

    public SFFTSObject(T data) {
        this.data = data;
    }

    public final T getData() {
        return data;
    }

    public final SFFTSObject<T> obj() {
        return this;
    }

    public boolean equals(SFFTSObject<T> other) {
        if (this instanceof Comparable) {
            return ((Comparable<SFFTSObject<T>>) this).compareTo(other) == 0;
        } else {
            return getData() == other.getData();
        }
    }
}
