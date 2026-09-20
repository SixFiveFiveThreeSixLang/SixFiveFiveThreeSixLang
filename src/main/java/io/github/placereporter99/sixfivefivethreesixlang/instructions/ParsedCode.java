package io.github.placereporter99.sixfivefivethreesixlang.instructions;

import io.github.placereporter99.sixfivefivethreesixlang.context.Context;
import io.github.placereporter99.sixfivefivethreesixlang.context.ContextAndStack;
import io.github.placereporter99.sixfivefivethreesixlang.types.SFFTSObject;
import io.github.placereporter99.sixfivefivethreesixlang.types.iterable.SFFTSFiniteIterable;

import java.util.List;
import java.util.function.BiConsumer;

public class ParsedCode {
    private final List<BiConsumer<SFFTSFiniteIterable, Context>> instructionStack;

    public ParsedCode(List<BiConsumer<SFFTSFiniteIterable, Context>> instructionStack) {
        this.instructionStack = instructionStack;
    }

    public ContextAndStack execute(SFFTSObject<?>... arguments) {
        return execute(new Context(), arguments);
    }

    public ContextAndStack execute(Context context, SFFTSObject<?>... arguments) {
        var stack = new SFFTSFiniteIterable(arguments);
        instructionStack.forEach(x -> x.accept(stack, context));
        return new ContextAndStack(context, stack.getData());
    }
}
