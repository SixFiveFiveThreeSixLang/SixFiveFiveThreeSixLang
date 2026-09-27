package io.github.placereporter99.sixfivefivethreesixlang.types.number;

import io.github.placereporter99.sixfivefivethreesixlang.types.SFFTSObject;

sealed abstract public class SFFTSNumeric<X extends SFFTSNumeric<X, Y>, Y> extends SFFTSObject<Y> permits SFFTSNumber {
    public SFFTSNumeric(Y data) {
        super(data);
    }
    public abstract X add(X o);
    public abstract X subtract(X o);
    public abstract X multiply(X o);
    public abstract X divide(X o);
    public abstract X abs();
    public abstract X norm();
    public abstract X pow(X o);
}
