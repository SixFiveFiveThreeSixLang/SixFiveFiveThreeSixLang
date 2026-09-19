package io.github.placereporter99.sixfivefivethreesixlang.types.number;

import java.math.*;

sealed public class SFFTSByte extends SFFTSInteger permits SFFTSBoolean {
    public SFFTSByte(byte data) {
        super(BigInteger.valueOf(data));
    }

    public byte valueByte() {
        return valueInt().byteValueExact();
    }
}
