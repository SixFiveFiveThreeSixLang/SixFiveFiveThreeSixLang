package io.github.placereporter99.sixfivefivethreesixlang.instructions;

import io.github.placereporter99.sixfivefivethreesixlang.context.Context;
import io.github.placereporter99.sixfivefivethreesixlang.types.SFFTSObject;
import io.github.placereporter99.sixfivefivethreesixlang.types.exceptions.SFFTSException;
import io.github.placereporter99.sixfivefivethreesixlang.types.exceptions.SFFTSRuntimeException;
import io.github.placereporter99.sixfivefivethreesixlang.types.iterable.SFFTSFiniteIterable;
import io.github.placereporter99.sixfivefivethreesixlang.types.number.SFFTSInteger;

import javax.swing.text.html.ListView;
import java.math.BigInteger;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

@FunctionalInterface
interface StructureHandler {
    void execute(List<Short> completedStructure, SFFTSFiniteIterable stack, Context context);
}

@FunctionalInterface
interface SimpleProcessor {
    SFFTSObject<?> process(List<Short> completedStructure);
}

@FunctionalInterface
interface ContextAwareProcessor {
    SFFTSObject<?> process(List<Short> completedStructure, Context context);
}

@FunctionalInterface
interface BracketedStructureHandler {
    void execute(Map<Short, ParsedCode> completedStructure, SFFTSFiniteIterable stack, Context context);
}

record MetaElementInfo(Predicate<List<Short>> isMetaElementClosed, StructureHandler processElement) {}

public class MetaElements implements ElementalClass {
    private static final Map<Short, MetaElementInfo> elements = new HashMap<>();
    private static final Map<Short, String> docs = new HashMap<>();
    private static final List<Short> internalTaken = new ArrayList<>();
    public static final List<Short> takenView = Collections.unmodifiableList(internalTaken);

    private static short s(int number) {
        return (short) number;
    }

    private static void addTaken(short number) {
        internalTaken.add(number);
    }

    public static void addMetaToInstructionStack(List<Short> code, List<BiConsumer<SFFTSFiniteIterable, Context>> instructionStack) {
        var s = code.getFirst();
        var p = getMetaElementPredicate(s);
        var r = getMetaElementHandler(s);
        var q = new ArrayList<Short>();
        do {
            q.add(code.removeFirst());
        } while (!(p.test(q) || code.isEmpty()));
        instructionStack.add((x, y) -> r.execute(q, x, y));
    }

    private static Predicate<List<Short>> getMetaElementPredicate(short element) {
        if (elements.containsKey(element)) {
            return elements.get(element).isMetaElementClosed();
        }
        SFFTSException.fromString("Meta Element does not exist.").raise();
        return null;
    }

    private static StructureHandler getMetaElementHandler(short element) {
        if (elements.containsKey(element)) {
            return elements.get(element).processElement();
        }
        SFFTSException.fromString("Meta Element does not exist.").raise();
        return null;
    }

    private static void addMetaElement(short element, String doc, Predicate<List<Short>> isClosed, StructureHandler handler) {
        if (internalTaken.contains(element)) {
            throw new IllegalArgumentException("Meta Element already implemented.");
        }
        internalTaken.add(element);
        elements.put(element, new MetaElementInfo(isClosed, (x, y, z) -> {
            try {
                handler.execute(new ArrayList<>(x), y, z);
            } catch (ClassCastException e) {
                SFFTSException.fromString("Meta Element does not accept those types.").raise();
            } catch (Exception e) {
                SFFTSException.fromThrowable(e).raise();
            }
        }));
        docs.put(element, doc);
    }

    private static void addSimpleStructure(short element, short elementEnd, String doc, SimpleProcessor processor) {
        internalTaken.add(elementEnd);
        addMetaElement(element, doc, (x) -> x.contains(elementEnd), (a, b, c) -> b.push(processor.process(a)));
    }

    private static void addContextAwareStructure(short element, short elementEnd, String doc, ContextAwareProcessor processor) {
        internalTaken.add(elementEnd);
        addMetaElement(element, doc, (x) -> x.contains(elementEnd), (a, b, c) -> b.push(processor.process(a, c)));
    }

    private static void addBracketingStructure(short element, Short[] specialIntermediates, short elementEnd, String doc, BracketedStructureHandler handler) {
        internalTaken.addAll(Arrays.asList(specialIntermediates));
        internalTaken.add(elementEnd);
        addMetaElement(element, doc, (x) -> {
            var a = new ArrayList<>(x);
            var i = new ArrayList<BiConsumer<SFFTSFiniteIterable, Context>>();
            while (!a.isEmpty()) {
                try {
                    MetaElements.addMetaToInstructionStack(a, i);
                } catch (SFFTSRuntimeException e) {
                    try {
                        Elements.addSimpleToInstructionStack(a, i);
                    } catch (SFFTSRuntimeException ex) {
                        if (a.getFirst() == elementEnd) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }, (x, b, c) -> {
            var a = new ArrayList<>(x);
            var i = new ArrayList<BiConsumer<SFFTSFiniteIterable, Context>>();
            var h = new HashMap<Short, ParsedCode>();
            var s = element;
            while (!a.isEmpty()) {
                try {
                    MetaElements.addMetaToInstructionStack(a, i);
                } catch (SFFTSRuntimeException e) {
                    try {
                        Elements.addSimpleToInstructionStack(a, i);
                    } catch (SFFTSRuntimeException ex) {
                        if (Arrays.asList(specialIntermediates).contains(a.getFirst())) {
                            h.put(s, new ParsedCode(i.toArray(BiConsumer[]::new)));
                        } else if (a.getFirst() == elementEnd) {
                            return;
                        } else {
                            SFFTSException.fromThrowable(ex).raise();
                        }
                    }
                }
            }
            handler.execute(h, b, c);
        });
    }

    static {
        addSimpleStructure(s(65534), s(65535), "Interprets as a base 65535 compressed integer.", x -> {
            if (x.getFirst() == s(65534)) {
                x.removeFirst();
            }
            if (x.getLast() == s(65535)) {
                x.removeLast();
            }
            var placeCounter = BigInteger.ZERO;
            var number = BigInteger.ZERO;
            var sfftf = BigInteger.valueOf(65535);
            for (var value : x.reversed()) {
                number = number.add(BigInteger.valueOf(value).multiply(sfftf.pow(placeCounter.intValueExact())));
                placeCounter = placeCounter.add(BigInteger.ONE);
            }
            return SFFTSInteger.create(number);
        });
        addBracketingStructure(s(65280), new Short[]{s(65300)}, s(65535), "An if statement. Does not inherently support else-if. Nesting if statements will be required for that.", (a, b, c) -> {
            var cond = b.pop().isTruthy();
            if (cond) {
                a.get(s(65280)).execute(c, b);
            } else {
                a.get(s(65300)).execute(c, b);
            }
        });
    }
}
