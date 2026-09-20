package io.github.placereporter99.sixfivefivethreesixlang.types.number;

import java.math.*;

sealed public class SFFTSInteger extends SFFTSRealNumber permits SFFTSByte {

    public static SFFTSInteger create(BigInteger data) {
        try {
            return new SFFTSByte(data.byteValueExact());
        } catch (ArithmeticException _) {
            return new SFFTSInteger(data);
        }
    }

    public static SFFTSInteger create(long data) {
        return create(BigInteger.valueOf(data));
    }

    protected SFFTSInteger(BigInteger data) {
        super(new BigDecimal(data));
    }

    protected SFFTSInteger(long data) {
        super(new BigDecimal(data));
    }

    public BigInteger valueInt() {
        return value().toBigIntegerExact();
    }
}
