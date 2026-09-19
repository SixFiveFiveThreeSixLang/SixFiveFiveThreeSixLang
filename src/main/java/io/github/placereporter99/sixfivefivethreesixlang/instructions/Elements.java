package io.github.placereporter99.sixfivefivethreesixlang.instructions;

import ch.obermuhlner.math.big.BigComplex;
import io.github.placereporter99.sixfivefivethreesixlang.types.Conversions;
import io.github.placereporter99.sixfivefivethreesixlang.types.SFFTSObject;
import io.github.placereporter99.sixfivefivethreesixlang.types.exceptions.SFFTSException;
import io.github.placereporter99.sixfivefivethreesixlang.types.executable.SFFTSFunction;
import io.github.placereporter99.sixfivefivethreesixlang.types.helpers.functions.TriFunction;
import io.github.placereporter99.sixfivefivethreesixlang.types.iterable.SFFTSFiniteIterable;
import io.github.placereporter99.sixfivefivethreesixlang.types.iterable.SFFTSInfiniteIterable;
import io.github.placereporter99.sixfivefivethreesixlang.types.iterable.SFFTSStack;
import io.github.placereporter99.sixfivefivethreesixlang.types.number.SFFTSByte;
import io.github.placereporter99.sixfivefivethreesixlang.types.number.SFFTSInteger;
import io.github.placereporter99.sixfivefivethreesixlang.types.number.SFFTSNumber;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

final public class Elements implements ElementalClass {
    private static final Map<Short, Consumer<SFFTSStack>> elements = new HashMap<>();

    private Elements() {
        throw new AssertionError("This class cannot be instantiated.");
    }

    public static void interpretElement(short element, SFFTSStack stack) {
        if (elements.containsKey(element)) {
            elements.get(element).accept(stack);
        } else {
            SFFTSException.fromString("Element does not exist.").raise();
        }
    }

    private static void addElement(short element, Consumer<SFFTSStack> implementation) {
        if (elements.containsKey(element)) {
            throw new IllegalArgumentException("Element already implemented.");
        }
        elements.put(element, (x) -> {
            try {
                implementation.accept(x);
            } catch (ClassCastException e) {
                SFFTSException.fromString("Element does not accept those types.").raise();
            } catch (Exception e) {
                SFFTSException.fromThrowable(e).raise();
            }
        });
    }

    private static void addConstant(short element, SFFTSObject<?> constant) {
        addElement(element, (x) -> x.push(constant));
    }

    private static void addConstant(short element, Object constant) {
        addConstant(element, Conversions.convert(constant));
    }

    private static void addNilad(short element, Supplier<SFFTSObject<?>> function) {
        addElement(element, (x) -> x.push(function.get()));
    }

    private static void addMonad(short element, Function<SFFTSObject<?>, SFFTSObject<?>> function) {
        addElement(element, (x) -> x.push(function.apply(x.pop())));
    }

    private static void addDyad(short element, BiFunction<SFFTSObject<?>, SFFTSObject<?>, SFFTSObject<?>> function) {
        addElement(element, (x) -> x.push(function.apply(x.pop(), x.pop())));
    }

    private static void addTriad(short element, TriFunction<SFFTSObject<?>, SFFTSObject<?>, SFFTSObject<?>, SFFTSObject<?>> function) {
        addElement(element, (x) -> x.push(function.apply(x.pop(), x.pop(), x.pop())));
    }

    private static short s(int number) {
        return (short) number;
    }

    static {
        for (short i = 0; i < 256; i++) {
            addConstant(i, new SFFTSByte((byte) i));
        }

        addConstant(s(256), "Hello, World!");
        addConstant(s(257), "Goodbye, World!");
        addConstant(s(258), List.of("Fizz", "Buzz"));
        addConstant(s(259), new SFFTSInfiniteIterable(new SFFTSFunction((x, y) -> {
            var a = SFFTSNumber.ZERO;
            try {
                while (true) {
                    y.push(a);
                    a = a.add(SFFTSNumber.ONE);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }), new SFFTSFiniteIterable()));

    }
}
