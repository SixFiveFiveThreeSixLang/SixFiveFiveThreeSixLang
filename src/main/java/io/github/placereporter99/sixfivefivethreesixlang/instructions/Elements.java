package io.github.placereporter99.sixfivefivethreesixlang.instructions;

import io.github.placereporter99.sixfivefivethreesixlang.context.Context;
import io.github.placereporter99.sixfivefivethreesixlang.types.Conversions;
import io.github.placereporter99.sixfivefivethreesixlang.types.SFFTSObject;
import io.github.placereporter99.sixfivefivethreesixlang.types.exceptions.SFFTSException;
import io.github.placereporter99.sixfivefivethreesixlang.types.executable.SFFTSFunction;
import io.github.placereporter99.sixfivefivethreesixlang.types.helpers.functions.TriFunction;
import io.github.placereporter99.sixfivefivethreesixlang.types.iterable.SFFTSFiniteIterable;
import io.github.placereporter99.sixfivefivethreesixlang.types.iterable.SFFTSInfiniteIterable;
import io.github.placereporter99.sixfivefivethreesixlang.types.number.SFFTSByte;
import io.github.placereporter99.sixfivefivethreesixlang.types.number.SFFTSNumber;
import io.github.placereporter99.sixfivefivethreesixlang.types.number.SFFTSNumeric;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.*;

final public class Elements implements ElementalClass {
    private static final Map<Short, BiConsumer<SFFTSFiniteIterable, Context>> elements = new HashMap<>();
    private static final Map<Short, String> docs = new HashMap<>();

    private Elements() {
        throw new AssertionError("This class cannot be instantiated.");
    }

    public static void addSimpleToInstructionStack(List<Short> code, List<BiConsumer<SFFTSFiniteIterable, Context>> instructionStack) {
        var instruction = code.removeFirst();
        instructionStack.add(getElementFunction(instruction));
    }

    private static BiConsumer<SFFTSFiniteIterable, Context> getElementFunction(short element) {
        if (elements.containsKey(element)) {
            return elements.get(element);
        }
        SFFTSException.fromString("Element does not exist.").raise();
        return null;
    }

    private static void addElement(short element, String doc, BiConsumer<SFFTSFiniteIterable, Context> implementation) {
        if (elements.containsKey(element)) {
            throw new IllegalArgumentException("Element already implemented.");
        } else if (MetaElements.takenView.contains(element)) {
            throw new IllegalArgumentException("Codepoint already taken by meta element.");
        }
        elements.put(element, (x, c) -> {
            try {
                implementation.accept(x, c);
            } catch (ClassCastException e) {
                SFFTSException.fromString("Element does not accept those types.").raise();
            } catch (Exception e) {
                SFFTSException.fromThrowable(e).raise();
            }
        });
        docs.put(element, doc);
    }

    private static void addConstant(short element, SFFTSObject<?> constant) {
        addElement(element, "Pushes the constant value " + constant + " to the stack.", (x, c) -> x.push(constant));
    }

    private static void addConstant(short element, Object constant) {
        addConstant(element, Conversions.convert(constant));
    }

    private static void addNilad(short element, String doc, Supplier<SFFTSObject<?>> function) {
        addElement(element, doc, (x, c) -> x.push(function.get()));
    }

    private static void addMonad(short element, String doc, Function<SFFTSObject<?>, SFFTSObject<?>> function) {
        addElement(element, doc, (x, c) -> x.push(function.apply(x.pop())));
    }

    private static void addDyad(short element, String doc, BiFunction<SFFTSObject<?>, SFFTSObject<?>, SFFTSObject<?>> function) {
        addElement(element, doc, (x, c) -> {
            var b = x.pop();
            var a = x.pop();
            x.push(function.apply(a, b));
        });
    }

    private static void addTriad(short element, String doc, TriFunction<SFFTSObject<?>, SFFTSObject<?>, SFFTSObject<?>, SFFTSObject<?>> function) {
        addElement(element, doc, (x, c) -> {
            var d = x.pop();
            var b = x.pop();
            var a = x.pop();
            x.push(function.apply(a, b, d));
        });
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
        addElement(s(259), "Pushes an infinite iterable containing all non-negative integers.", (z, c) -> new SFFTSInfiniteIterable(new SFFTSFunction((x, y, p) -> {
            var a = SFFTSNumber.ZERO;
            try {
                while (true) {
                    p.push(a);
                    a = a.add(SFFTSNumber.ONE);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }), z, c));
        addDyad(s(512), "Adds two numbers together.",(x, y) -> ((SFFTSNumeric) x).add((SFFTSNumeric) y));
        addDyad(s(513), "Subtracts one number from another",(x, y) -> ((SFFTSNumeric) x).subtract((SFFTSNumeric) y));
        addDyad(s(514), "Adds two numbers together.",(x, y) -> ((SFFTSNumeric) x).add((SFFTSNumeric) y));
    }
}
