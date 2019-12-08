package org.obridge.query.conversion;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StringPatternToLocalDateConverter implements Converter<String, LocalDate> {
    private final String pattern;

    public StringPatternToLocalDateConverter(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public LocalDate valueOf(String from) {
        return LocalDate.parse(from, DateTimeFormatter.ofPattern(pattern));
    }
}
