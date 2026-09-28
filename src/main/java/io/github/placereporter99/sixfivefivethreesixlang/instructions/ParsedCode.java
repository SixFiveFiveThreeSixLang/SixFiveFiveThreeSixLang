package io.github.placereporter99.sixfivefivethreesixlang.instructions;

import io.github.placereporter99.sixfivefivethreesixlang.context.Context;
import io.github.placereporter99.sixfivefivethreesixlang.context.ContextAndStack;
import io.github.placereporter99.sixfivefivethreesixlang.types.SFFTSObject;
import io.github.placereporter99.sixfivefivethreesixlang.types.executable.TriConsumer;
import io.github.placereporter99.sixfivefivethreesixlang.types.helpers.FunctionDataOutPipeline;
import io.github.placereporter99.sixfivefivethreesixlang.types.iterable.SFFTSFiniteIterable;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

public class ParsedCode {
    private final List<TriConsumer<SFFTSFiniteIterable, Context, FunctionDataOutPipeline>> instructionStack;

    @SafeVarargs
    public ParsedCode(BiConsumer<SFFTSFiniteIterable, Context>... instructionStack) {
        this(Arrays.stream(instructionStack).map(x -> (TriConsumer<SFFTSFiniteIterable, Context, FunctionDataOutPipeline>) ((a, b, c) -> x.accept(a, b))).toArray(TriConsumer[]::new));
    }

    @SafeVarargs
    public ParsedCode(TriConsumer<SFFTSFiniteIterable, Context, FunctionDataOutPipeline>... instructionStack) {
        this.instructionStack = Arrays.stream(instructionStack).toList();
    }

    public ContextAndStack execute(SFFTSObject<?>... arguments) {
        return execute(new Context(), arguments);
    }

    public ContextAndStack execute(Context context, SFFTSObject<?>... arguments) {
        var stack = new SFFTSFiniteIterable(arguments);
        return executeExistingStack(context, stack);
    }

    public ContextAndStack executeExistingStack(Context context, SFFTSFiniteIterable stack) {
        instructionStack.forEach(x -> x.accept(stack, context, context.pipeline));
        return new ContextAndStack(context, stack.getData());
    }
}
