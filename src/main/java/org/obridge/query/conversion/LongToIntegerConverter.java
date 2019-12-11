package org.obridge.query.conversion;

public class LongToIntegerConverter implements Converter<Long, Integer> {


    @Override
    public Integer valueOf(Long from) {
        return from.intValue();
    }
}
