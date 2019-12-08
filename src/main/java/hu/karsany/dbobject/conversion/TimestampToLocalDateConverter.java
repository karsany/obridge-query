package hu.karsany.dbobject.conversion;

import java.sql.Timestamp;
import java.time.LocalDate;

public class TimestampToLocalDateConverter implements Converter<Timestamp, LocalDate> {
    @Override
    public LocalDate valueOf(Timestamp from) {
        return from.toLocalDateTime().toLocalDate();
    }
}
