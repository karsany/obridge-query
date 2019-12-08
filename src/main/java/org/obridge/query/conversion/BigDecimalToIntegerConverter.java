package org.obridge.query.conversion;

import java.math.BigDecimal;

public class BigDecimalToIntegerConverter implements Converter<BigDecimal, Integer> {
    @Override
    public Integer valueOf(BigDecimal from) {
        return from.intValue();
    }
}
