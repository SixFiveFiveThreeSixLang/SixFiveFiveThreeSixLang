package io.github.placereporter99.sixfivefivethreesixlang.instructions;

import ch.obermuhlner.math.big.BigComplex;
import io.github.placereporter99.sixfivefivethreesixlang.context.Context;
import io.github.placereporter99.sixfivefivethreesixlang.types.SFFTSObject;
import io.github.placereporter99.sixfivefivethreesixlang.types.exceptions.SFFTSException;
import io.github.placereporter99.sixfivefivethreesixlang.types.exceptions.SFFTSRuntimeException;
import io.github.placereporter99.sixfivefivethreesixlang.types.executable.SFFTSFunction;
import io.github.placereporter99.sixfivefivethreesixlang.types.iterable.SFFTSFiniteIterable;
import io.github.placereporter99.sixfivefivethreesixlang.types.iterable.SFFTSString;
import io.github.placereporter99.sixfivefivethreesixlang.types.misc.SFFTSNull;
import io.github.placereporter99.sixfivefivethreesixlang.types.number.SFFTSInteger;
import io.github.placereporter99.sixfivefivethreesixlang.types.number.SFFTSNumber;
import io.github.placereporter99.sixfivefivethreesixlang.types.number.SFFTSRealNumber;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;


final public class Parser {
    private Parser() {
        throw new AssertionError("This class cannot be instantiated.");
    }

    private final static List<Map.Entry<Predicate<String>, Function<String, SFFTSObject<?>>>> inputParsers = new ArrayList<>();

    public static ParsedCode parseCode(String code) {
        return parseCode(code.codePoints().mapToObj(x -> Codepage.charToShort(new String(Character.toChars(x)))).toList());
    }

    public static ParsedCode parseCode(List<Short> code) {
        var codeCopy = new ArrayList<>(code);
        var instructionStack = new ArrayList<BiConsumer<SFFTSFiniteIterable, Context>>();
        while (!codeCopy.isEmpty()) {
            try {
                MetaElements.addMetaToInstructionStack(codeCopy, instructionStack);
            } catch (SFFTSRuntimeException e) {
                try {
                    Elements.addSimpleToInstructionStack(codeCopy, instructionStack);
                } catch (SFFTSRuntimeException ex) {
                    SFFTSException.fromThrowable(ex).raise();
                }
            }
        }
        return new ParsedCode(instructionStack.toArray(BiConsumer[]::new));
    }

    public static SFFTSObject<?> parseLine(String input) {
        for (var parser : inputParsers) {
            if (parser.getKey().test(input)) {
                return parser.getValue().apply(input);
            }
        }
        return SFFTSNull.UNKNOWN_OBJECT;
    }

    private static void addRegexParser(String regex, Function<String, SFFTSObject<?>> parser) {
        inputParsers.add(Map.entry(x -> Pattern.compile(regex).matcher(x).matches(), parser));
    }

    private static void addComplexParser(Predicate<String> tester, Function<String, SFFTSObject<?>> parser) {
        inputParsers.add(Map.entry(tester, parser));
    }

    static {
        //  String  //
        addRegexParser("^\".*\"$", x -> new SFFTSString(x.substring(1, x.length() - 1).replace("\\n", "\n").replace("\\\"", "\"").replace("\\s", "\\")));
        //  Number  //
        addRegexParser("^-?\\d+$", x -> SFFTSInteger.create(new BigInteger(x)));
        addRegexParser("^-?\\d*\\.\\d*$", x -> SFFTSRealNumber.create(new BigDecimal(x, SFFTSNumber.getContext())));
        addRegexParser("^-?(?:\\d*\\.\\d*|\\d+)i$", x -> SFFTSNumber.create(BigComplex.valueOf(BigDecimal.ZERO, new BigDecimal(x.replace("i", ""), SFFTSNumber.getContext()))));
        addRegexParser("\"^-?(?:\\d*\\.\\d*|\\d+)\\+-?(?:\\d*\\.\\d*|\\d+)i$\"", x -> {
            var a = x.split("\\+");
            var re = new BigDecimal(a[0], SFFTSNumber.getContext());
            var im = new BigDecimal(a[1].replace("i", ""), SFFTSNumber.getContext());
            return SFFTSNumber.create(BigComplex.valueOf(re, im));
        });
        addRegexParser("^-?(?:\\d*\\.\\d*|\\d+)-(?:\\d*\\.\\d*|\\d+)i$", x -> {
            var a = x.split("-");
            var re = new BigDecimal(a[0], SFFTSNumber.getContext());
            var im = new BigDecimal(a[1].replace("i", ""), SFFTSNumber.getContext()).negate();
            return SFFTSNumber.create(BigComplex.valueOf(re, im));
        });
        // Function //
        addRegexParser("^λ[\0]*⧚$".replace("\0", Codepage.getCodepage()), x -> new SFFTSFunction(Parser.parseCode(x.substring(1, x.length() - 1))));
        //   List   //
        addRegexParser("⧛[\0]⧚".replace("\0", Codepage.getCodepage()), x -> {
            var s = new SFFTSFiniteIterable();
            Parser.parseCode(x.substring(1, x.length() - 1)).executeExistingStack(new Context(), s);
            return s;
        });
    }
}
