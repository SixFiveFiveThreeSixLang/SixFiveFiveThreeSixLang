package io.github.placereporter99.sixfivefivethreesixlang.types.iterable;

import io.github.placereporter99.sixfivefivethreesixlang.types.SFFTSObject;
import io.github.placereporter99.sixfivefivethreesixlang.types.misc.SFFTSNull;

public final class SFFTSStack extends SFFTSFiniteIterable {
    @Override
    public SFFTSObject<?> pop() {
        if (getData().isEmpty()) {
            return SFFTSNull.NULL;
        }
        return super.pop();
    }

    @Override
    public SFFTSObject<?> popHead() {
        if (getData().isEmpty()) {
            return SFFTSNull.NULL;
        }
        return super.popHead();
    }
}
