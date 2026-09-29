package io.github.placereporter99.sixfivefivethreesixlang.main;

import java.util.ArrayList;
import java.util.List;

enum InputMode {RAW, PARSED}
enum OutputMode {WHOLE_STACK, TOP_STACK, BOTTOM_STACK, STACK_AND_CONTEXT, REGISTER}

public class Main {
    static void main(String[] args) {
        var largs = new ArrayList<>(List.of(args));
        var prog = largs.removeFirst();
        var im = InputMode.PARSED;
        var om = OutputMode.TOP_STACK;
    }
}
