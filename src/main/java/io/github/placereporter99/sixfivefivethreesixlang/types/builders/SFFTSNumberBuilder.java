package io.github.placereporter99.sixfivefivethreesixlang.types.builders;

import ch.obermuhlner.math.big.BigComplex;
import io.github.placereporter99.sixfivefivethreesixlang.types.enums.Base10Digit;
import io.github.placereporter99.sixfivefivethreesixlang.types.number.SFFTSNumber;

import java.math.BigDecimal;
import java.util.stream.Collectors;

public class SFFTSNumberBuilder extends SFFTSBuilder<Base10Digit, SFFTSNumber> {

    @Override
    public SFFTSNumber build() {
        var realInt = get("RealInt").getItems().stream().map(Base10Digit::getString).collect(Collectors.joining());
        var realDecimal = get("RealDecimal").getItems().stream().map(Base10Digit::getString).collect(Collectors.joining());
        var imagInt = get("ImagInt").getItems().stream().map(Base10Digit::getString).collect(Collectors.joining());
        var imagDecimal = get("ImagDecimal").getItems().stream().map(Base10Digit::getString).collect(Collectors.joining());

        var real = new BigDecimal(realInt + "." + realDecimal, SFFTSNumber.getContext());
        var imag = new BigDecimal(imagInt + "." + imagDecimal, SFFTSNumber.getContext());
        return SFFTSNumber.create(BigComplex.valueOf(real, imag));
    }
}
