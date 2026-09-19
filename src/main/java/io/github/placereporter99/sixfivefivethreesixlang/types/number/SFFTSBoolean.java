package io.github.placereporter99.sixfivefivethreesixlang.types.number;

final public class SFFTSBoolean extends SFFTSByte {
    public SFFTSBoolean(boolean data) {
        super((byte) (data ? 1 : 0));
    }

    public boolean valueBool() {
        return !(valueByte() == (byte) 0);
    }
}
