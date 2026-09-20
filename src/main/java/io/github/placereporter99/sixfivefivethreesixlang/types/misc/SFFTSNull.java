package io.github.placereporter99.sixfivefivethreesixlang.types.misc;

import io.github.placereporter99.sixfivefivethreesixlang.types.SFFTSObject;

public class SFFTSNull extends SFFTSObject<Void> {
    public static final SFFTSNull NULL = new SFFTSNull();

    private SFFTSNull() {
        super(null);
    }

    @Override
    public boolean isTruthy() {
        return false;
    }
}
