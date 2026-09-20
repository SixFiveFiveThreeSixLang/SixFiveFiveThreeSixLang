package io.github.placereporter99.sixfivefivethreesixlang.types.iterable;

import io.github.placereporter99.sixfivefivethreesixlang.types.SFFTSObject;
import io.github.placereporter99.sixfivefivethreesixlang.types.helpers.emptyinterfaces.SFFTSIterable;

public class SFFTSString extends SFFTSObject<String> implements SFFTSIterable {
    public SFFTSString(String data) {
        super(data);
    }

    @Override
    public boolean isTruthy() {
        return !getData().isEmpty();
    }
}
