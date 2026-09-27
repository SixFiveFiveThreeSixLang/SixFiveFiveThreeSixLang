package io.github.placereporter99.sixfivefivethreesixlang.types.executable;

import io.github.placereporter99.sixfivefivethreesixlang.context.Context;
import io.github.placereporter99.sixfivefivethreesixlang.instructions.ParsedCode;
import io.github.placereporter99.sixfivefivethreesixlang.types.*;
import io.github.placereporter99.sixfivefivethreesixlang.types.helpers.*;
import io.github.placereporter99.sixfivefivethreesixlang.types.iterable.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;

public class SFFTSFunction extends SFFTSObject<TriConsumer<SFFTSFiniteIterable, Context, FunctionDataOutPipeline>>{
    public SFFTSFunction(TriConsumer<SFFTSFiniteIterable, Context, FunctionDataOutPipeline> function) {
        super(function);
    }

    public SFFTSFunction(ParsedCode parsedCode) {
        this(parsedCode::execute);
    }

    public void execute(SFFTSFiniteIterable stack, Context context, FunctionDataOutPipeline pipeline) {
        getData().accept(stack, context, pipeline);
    }

    @Override
    public boolean isTruthy() {
        return true;
    }
}
