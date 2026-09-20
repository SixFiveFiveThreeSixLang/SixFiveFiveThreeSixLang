package io.github.placereporter99.sixfivefivethreesixlang.types.executable;

import io.github.placereporter99.sixfivefivethreesixlang.types.*;
import io.github.placereporter99.sixfivefivethreesixlang.types.helpers.*;
import io.github.placereporter99.sixfivefivethreesixlang.types.iterable.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;

public class SFFTSFunction extends SFFTSObject<BiConsumer<SFFTSFiniteIterable, FunctionDataOutPipeline>>{
    public SFFTSFunction(BiConsumer<SFFTSFiniteIterable, FunctionDataOutPipeline> function) {
        super(function);
    }

    public void execute(SFFTSFiniteIterable stack, FunctionDataOutPipeline pipeline) {
        getData().accept(stack, pipeline);
    }

    @Override
    public boolean isTruthy() {
        return true;
    }
}
