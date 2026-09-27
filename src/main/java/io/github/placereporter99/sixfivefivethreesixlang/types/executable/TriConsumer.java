package io.github.placereporter99.sixfivefivethreesixlang.types.executable;

@FunctionalInterface
public interface TriConsumer<A, B, C> {
    void accept(A a, B b, C c);
}
