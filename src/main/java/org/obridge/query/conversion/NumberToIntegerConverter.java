package org.obridge.query.conversion;

public class NumberToIntegerConverter implements Converter<Number, Integer> {
    @Override
    public Integer valueOf(Number from) {
        return from.intValue();
    }
}
