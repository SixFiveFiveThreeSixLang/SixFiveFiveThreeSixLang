package io.github.placereporter99.sixfivefivethreesixlang.types.number;

import java.math.*;
import java.util.function.*;

import io.github.placereporter99.sixfivefivethreesixlang.types.SFFTSObject;
import io.github.placereporter99.sixfivefivethreesixlang.types.helpers.functions.TriFunction;
import ch.obermuhlner.math.big.*;

sealed public class SFFTSNumber extends SFFTSObject<BigComplex> permits SFFTSRealNumber {
    public static final SFFTSNumber ZERO = SFFTSNumber.create(BigComplex.ZERO);
    public static final SFFTSNumber ONE = SFFTSNumber.create(BigComplex.ONE);
    public static final SFFTSNumber MINUS_ONE = SFFTSNumber.create(BigComplex.valueOf(-1));

    private static MathContext context = new MathContext(1024, RoundingMode.HALF_EVEN);

    public static void setPrecision(int precision) {
        SFFTSNumber.context = new MathContext(precision, RoundingMode.HALF_EVEN);
    }

    public static int getPrecision() {
        return SFFTSNumber.context.getPrecision();
    }

    public static MathContext getContext() {
        return context;
    }

    public static SFFTSNumber create(BigComplex data) {
        if (data.isReal()) {
            return SFFTSRealNumber.create(data.abs(SFFTSNumber.context));
        } else {
            return new SFFTSNumber(data);
        }
    }

    public static SFFTSRealNumber create(BigDecimal data) {
        return SFFTSRealNumber.create(data);
    }

    protected SFFTSNumber(BigComplex data) {
        super(data);
    }

    private SFFTSNumber operate(SFFTSNumber ot, TriFunction<BigComplex, BigComplex, MathContext, BigComplex> op) {
        return SFFTSNumber.create(op.apply(getData(), ot.getData(), SFFTSNumber.context));
    }

    private SFFTSNumber operateSingle(BiFunction<BigComplex, MathContext, BigComplex> op) {
        return SFFTSNumber.create(op.apply(getData(), SFFTSNumber.context));
    }

    public SFFTSNumber signum() {
        return SFFTSNumber.create(getData().divide(getData().abs(SFFTSNumber.context), SFFTSNumber.context));
    }

    public boolean equals(SFFTSNumber o) {
        return getData().equals(o.getData());
    }

    public boolean isInteger() {
        return getData().isReal() && getData().re().abs(SFFTSNumber.context).remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0;
    }

    public SFFTSNumber add(SFFTSNumber o) {
        return operate(o, BigComplex::add);
    }

    public SFFTSNumber subtract(SFFTSNumber o) {
        return operate(o, BigComplex::subtract);
    }

    public SFFTSNumber multiply(SFFTSNumber o) {
        return operate(o, BigComplex::multiply);
    }

    public SFFTSNumber divide(SFFTSNumber o) {
        return operate(o, BigComplex::divide);
    }

    public SFFTSNumber abs() {
        return operateSingle((x, y) -> BigComplex.valueOf(x.abs(y)));
    }

    public SFFTSNumber norm() {
        return operateSingle((x, y) -> BigComplex.valueOf(x.absSquare(y)));
    }

    public SFFTSNumber pow(SFFTSNumber o) throws ArithmeticException {
        return operate(o, BigComplexMath::pow);
    }

    @Override
    public boolean isTruthy() {
        return !norm().getData().equals(BigComplex.ZERO);
    }
}
