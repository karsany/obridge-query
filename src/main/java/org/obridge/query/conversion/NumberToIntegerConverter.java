package org.obridge.query.conversion;

public class NumberToIntegerConverter implements Converter<Number, Integer> {
    @Override
    public Integer convert(Number from) {
        return from.intValue();
    }
}
