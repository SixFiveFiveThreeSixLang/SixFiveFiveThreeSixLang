package io.github.placereporter99.sixfivefivethreesixlang.types.number;

import java.math.*;
import ch.obermuhlner.math.big.*;

sealed public class SFFTSRealNumber extends SFFTSNumber implements Comparable<SFFTSRealNumber> permits SFFTSInteger {
    public static SFFTSRealNumber create(BigDecimal data) {
        if (data.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0) {
            return SFFTSInteger.create(data.toBigIntegerExact());
        } else {
            return new SFFTSRealNumber(data);
        }
    }

    protected SFFTSRealNumber(BigDecimal data) {
        super(BigComplex.valueOf(data));
    }

    public BigDecimal value() {
        return getData().abs(SFFTSNumber.getContext());
    }

    public int compareTo(SFFTSRealNumber o) {
        return value().compareTo(o.value());
    }

    public SFFTSRealNumber remainder(SFFTSRealNumber o) {
        return new SFFTSRealNumber(value().remainder(o.value(), SFFTSNumber.getContext()));
    }

    public SFFTSRealNumber divideToIntegralValue(SFFTSRealNumber o) {
        return new SFFTSRealNumber(value().divideToIntegralValue(o.value()));
    }
}
