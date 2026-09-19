package io.github.placereporter99.sixfivefivethreesixlang.types.helpers.functions;

@FunctionalInterface
public interface TriFunction<A, B, C, R> {
    R apply(A a, B b, C c);
}
