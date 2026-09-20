package io.github.placereporter99.sixfivefivethreesixlang.types.number;

final public class SFFTSBoolean extends SFFTSByte {
    public static final SFFTSInteger TRUE = new SFFTSBoolean(true);
    public static final SFFTSInteger FALSE = new SFFTSBoolean(false);

    public SFFTSBoolean(boolean data) {
        super((byte) (data ? 1 : 0));
    }

    public boolean valueBool() {
        return !(valueByte() == (byte) 0);
    }

    @Override
    public boolean isTruthy() {
        return valueBool();
    }
}
