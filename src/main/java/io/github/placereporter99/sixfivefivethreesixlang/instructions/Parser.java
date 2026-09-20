package io.github.placereporter99.sixfivefivethreesixlang.instructions;

import io.github.placereporter99.sixfivefivethreesixlang.context.Context;
import io.github.placereporter99.sixfivefivethreesixlang.types.SFFTSObject;
import io.github.placereporter99.sixfivefivethreesixlang.types.exceptions.SFFTSException;
import io.github.placereporter99.sixfivefivethreesixlang.types.exceptions.SFFTSRuntimeException;
import io.github.placereporter99.sixfivefivethreesixlang.types.iterable.SFFTSFiniteIterable;

import java.util.*;
import java.util.function.BiConsumer;

final public class Parser {
    private Parser() {
        throw new AssertionError("This class cannot be instantiated.");
    }

    public static ParsedCode parseCode(List<Short> code) {
        var codeCopy = new ArrayList<>(code);
        var instructionStack = new ArrayList<BiConsumer<SFFTSFiniteIterable, Context>>();
        while (!codeCopy.isEmpty()) {
            try {
                MetaElements.addMetaToInstructionStack(codeCopy, instructionStack);
            } catch (SFFTSRuntimeException e) {
                try {
                    Elements.addSimpleToInstructionStack(codeCopy, instructionStack);
                } catch (SFFTSRuntimeException ex) {
                    SFFTSException.fromThrowable(ex).raise();
                }
            }
        }
        return new ParsedCode(instructionStack);
    }
}
