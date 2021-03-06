package org.obridge.query.conversion.converters;

import org.obridge.query.conversion.Converter;

import java.sql.Timestamp;
import java.time.LocalDate;

public class TimestampToLocalDateConverter implements Converter<Timestamp, LocalDate> {
    @Override
    public LocalDate convert(Timestamp from) {
        return from.toLocalDateTime().toLocalDate();
    }
}
