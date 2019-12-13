package org.obridge.query.conversion.converters;

import org.obridge.query.conversion.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StringPatternToLocalDateConverter implements Converter<String, LocalDate> {
    private final String pattern;

    public StringPatternToLocalDateConverter(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public LocalDate convert(String from) {
        return LocalDate.parse(from, DateTimeFormatter.ofPattern(pattern));
    }
}
