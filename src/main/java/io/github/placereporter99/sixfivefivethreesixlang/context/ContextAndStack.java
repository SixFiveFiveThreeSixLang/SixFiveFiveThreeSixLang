package io.github.placereporter99.sixfivefivethreesixlang.context;

import io.github.placereporter99.sixfivefivethreesixlang.types.SFFTSObject;

import java.util.List;

public record ContextAndStack(Context context, List<SFFTSObject<?>> stack) {}
