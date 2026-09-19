package io.github.placereporter99.sixfivefivethreesixlang.types;

import ch.obermuhlner.math.big.BigComplex;
import io.github.placereporter99.sixfivefivethreesixlang.types.executable.SFFTSFunction;
import io.github.placereporter99.sixfivefivethreesixlang.types.iterable.SFFTSFiniteIterable;
import io.github.placereporter99.sixfivefivethreesixlang.types.iterable.SFFTSInfiniteIterable;
import io.github.placereporter99.sixfivefivethreesixlang.types.iterable.SFFTSStack;
import io.github.placereporter99.sixfivefivethreesixlang.types.iterable.SFFTSString;
import io.github.placereporter99.sixfivefivethreesixlang.types.misc.SFFTSNull;
import io.github.placereporter99.sixfivefivethreesixlang.types.number.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

record ConversionResultInfo<T, U>(Class<T> from, Class<U> to, Function<T, U> converter) {}

public final class Conversions {
    private static final Map<Class<?>, ConversionResultInfo<?, ?>> conversionMap = new HashMap<>();

    private Conversions() {
        throw new AssertionError("This class cannot be instantiated.");
    }

    public static <X, Y> Y convert(X data) {
        return ((ConversionResultInfo<X, Y>) conversionMap.get(data.getClass())).converter().apply(data);
    }

    private static <X, Y> void addConversion(Class<X> from, Class<Y> to, Function<X, Y> forward, Function<Y, X> backward) {
        conversionMap.put(from, new ConversionResultInfo<X, Y>(from, to, forward));
        conversionMap.put(to, new ConversionResultInfo<Y, X>(to, from, backward));
    }

    private static <T, U extends SFFTSObject<?>> void addStandardConversion(Class<U> langType, Class<T> nativeType) {
        try {
            var getDataMethod = langType.getMethod("getData", null);
            var constructor = langType.getConstructor(nativeType);
            addConversion(langType, nativeType, (x) -> {try {return (T) getDataMethod.invoke(x);} catch (Exception e) {throw new AssertionError(e);}}, (y) -> {try {return constructor.newInstance(y);} catch (Exception e) {throw new AssertionError(e);}});
        } catch (NoSuchMethodException e) {
            throw new AssertionError("SFFTSObject does not have method getData somehow. This is very, very bad.");
        }
    }

    static {
        addStandardConversion(SFFTSObject.class, Object.class);
        addStandardConversion(SFFTSNumber.class, BigComplex.class);
        addStandardConversion(SFFTSRealNumber.class, BigDecimal.class);
        addStandardConversion(SFFTSInteger.class, BigInteger.class);
        addStandardConversion(SFFTSByte.class, Byte.class);
        addStandardConversion(SFFTSBoolean.class, Boolean.class);
        addStandardConversion(SFFTSFunction.class, Function.class);
        addStandardConversion(SFFTSFiniteIterable.class, ArrayList.class);
        addStandardConversion(SFFTSNull.class, Void.class);
        addStandardConversion(SFFTSString.class, String.class);
    }
}
