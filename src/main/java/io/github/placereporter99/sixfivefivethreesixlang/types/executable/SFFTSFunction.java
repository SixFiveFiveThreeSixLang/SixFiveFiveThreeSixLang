package io.github.placereporter99.sixfivefivethreesixlang.types.executable;

import io.github.placereporter99.sixfivefivethreesixlang.types.*;
import io.github.placereporter99.sixfivefivethreesixlang.types.helpers.*;
import io.github.placereporter99.sixfivefivethreesixlang.types.iterable.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;

public class SFFTSFunction extends SFFTSObject<BiFunction<SFFTSFiniteIterable, FunctionDataOutPipeline, Logger>>{
    public SFFTSFunction(BiFunction<SFFTSFiniteIterable, FunctionDataOutPipeline, Logger> function) {
        super(function);
    }

    public Logger execute(SFFTSFiniteIterable stack, FunctionDataOutPipeline pipeline) {
        return getData().apply(stack, pipeline);
    }
}
