package io.github.placereporter99.sixfivefivethreesixlang.main;

import io.github.placereporter99.sixfivefivethreesixlang.context.Context;
import io.github.placereporter99.sixfivefivethreesixlang.context.ContextAndStack;
import io.github.placereporter99.sixfivefivethreesixlang.instructions.Codepage;
import io.github.placereporter99.sixfivefivethreesixlang.instructions.Parser;
import io.github.placereporter99.sixfivefivethreesixlang.types.SFFTSObject;

import java.util.Arrays;
import java.util.List;

public class Executor {
    public static ContextAndStack execute(String code, List<SFFTSObject<?>> input) {
        return execute(code, input, new Context());
    }

    public static ContextAndStack execute(String code, List<SFFTSObject<?>> input, Context context) {
        return execute(code.codePoints().map(x -> Codepage.charToShort(Character.toString(x))).mapToObj(x -> (short) x).toArray(Short[]::new), input, context);
    }

    public static ContextAndStack execute(Short[] code, List<SFFTSObject<?>> input) {
        return execute(code, input, new Context());
    }

    public static ContextAndStack execute(Short[] code, List<SFFTSObject<?>> input, Context context) {
        return Parser.parseCode(Arrays.stream(code).toList()).execute(context, input.toArray(SFFTSObject<?>[]::new));
    }
}
