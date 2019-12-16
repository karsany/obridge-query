package org.obridge.query.conversion.converters;

import org.obridge.query.conversion.Converter;

public class NumberToIntegerConverter implements Converter<Number, Integer> {
    @Override
    public Integer convert(Number from) {
        return from.intValue();
    }
}
