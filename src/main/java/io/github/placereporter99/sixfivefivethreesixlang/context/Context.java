package io.github.placereporter99.sixfivefivethreesixlang.context;

import io.github.placereporter99.sixfivefivethreesixlang.types.SFFTSObject;
import io.github.placereporter99.sixfivefivethreesixlang.types.helpers.FunctionDataOutPipeline;
import io.github.placereporter99.sixfivefivethreesixlang.types.misc.SFFTSNull;

import java.util.HashMap;
import java.util.Map;

public class Context {
    public SFFTSObject<?> register = SFFTSNull.UNDEFINED;
    public FunctionDataOutPipeline pipeline = new FunctionDataOutPipeline();
}
