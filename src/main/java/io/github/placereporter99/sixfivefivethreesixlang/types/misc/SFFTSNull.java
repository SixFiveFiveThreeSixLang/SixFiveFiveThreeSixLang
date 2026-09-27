package io.github.placereporter99.sixfivefivethreesixlang.types.misc;

import io.github.placereporter99.sixfivefivethreesixlang.types.SFFTSObject;

enum NullReason {
    NOT_A_NUMBER,
    NOT_APPLICABLE,
    STACK_EMPTY,
    UNDEFINED,
    EXPLICITLY_SET_AS_NULL,
    UNKNOWN_OBJECT,
    NO_RETURN_VALUE,
}

public class SFFTSNull extends SFFTSObject<NullReason> {
    public static final SFFTSNull NOT_A_NUMBER = new SFFTSNull(NullReason.NOT_A_NUMBER);
    public static final SFFTSNull NOT_APPLICABLE = new SFFTSNull(NullReason.NOT_APPLICABLE);
    public static final SFFTSNull STACK_EMPTY = new SFFTSNull(NullReason.STACK_EMPTY);
    public static final SFFTSNull UNDEFINED = new SFFTSNull(NullReason.UNDEFINED);
    public static final SFFTSNull EXPLICITLY_SET_AS_NULL = new SFFTSNull(NullReason.EXPLICITLY_SET_AS_NULL);
    public static final SFFTSNull UNKNOWN_OBJECT = new SFFTSNull(NullReason.UNKNOWN_OBJECT);
    public static final SFFTSNull NO_RETURN_VALUE = new SFFTSNull(NullReason.NO_RETURN_VALUE);
    public static final SFFTSNull TRUE_NULL = new SFFTSNull(null);

    private SFFTSNull(NullReason reason) {
        super(reason);
    }

    @Override
    public boolean isTruthy() {
        return false;
    }
}
