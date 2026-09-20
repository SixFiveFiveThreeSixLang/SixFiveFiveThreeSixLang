package io.github.placereporter99.sixfivefivethreesixlang.types.iterable;

import io.github.placereporter99.sixfivefivethreesixlang.types.*;
import io.github.placereporter99.sixfivefivethreesixlang.types.executable.SFFTSFunction;
import io.github.placereporter99.sixfivefivethreesixlang.types.helpers.FunctionDataOutPipeline;
import io.github.placereporter99.sixfivefivethreesixlang.types.helpers.Return;
import io.github.placereporter99.sixfivefivethreesixlang.types.helpers.emptyinterfaces.SFFTSIterable;

public final class SFFTSInfiniteIterable extends SFFTSObject<SFFTSFunction> implements SFFTSIterable {
    private final FunctionDataOutPipeline pipeline = new FunctionDataOutPipeline();

    public SFFTSInfiniteIterable(SFFTSFunction function, SFFTSFiniteIterable stack) {
        super(function);
        Thread.startVirtualThread(() -> function.execute(stack, pipeline));
    }

    public SFFTSObject<?> getNextValue() throws InterruptedException {
        return pipeline.pull().obj();
    }

    public SFFTSObject<?> getReturnValue() throws InterruptedException {
        var result = pipeline.pull();
        while (!(result instanceof Return)) {
            result = pipeline.pull();
        }
        return result.obj();
    }

    @Override
    public boolean isTruthy() {
        return true;
    }
}
