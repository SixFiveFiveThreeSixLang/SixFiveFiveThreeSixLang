package io.github.placereporter99.sixfivefivethreesixlang.types.exceptions;

import io.github.placereporter99.sixfivefivethreesixlang.types.SFFTSObject;

public class SFFTSException extends SFFTSObject<SFFTSRuntimeException> {
    public SFFTSException(SFFTSRuntimeException data) {
        super(data);
    }

    public static SFFTSException fromString(String message) {
        return new SFFTSException(new SFFTSRuntimeException(message));
    }

    public static SFFTSException fromThrowable(Throwable cause) {
        return new SFFTSException(new SFFTSRuntimeException(cause));
    }

    public void raise() {
        throw getData();
    }
}
