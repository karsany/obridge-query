package org.obridge.query.conversion;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class Converters {

    static Map<FromTo, Converter> converters = new HashMap<>();

    static {
        Converters.register(new BigDecimalToIntegerConverter());
        Converters.register(new TimestampToLocalDateConverter());
    }

    public static void register(Converter converter) {

        final FromTo key = new FromTo(
                (Class) ((ParameterizedType) converter.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0],
                (Class) ((ParameterizedType) converter.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[1]
        );
        System.out.println("Registered converter: " + key);
        converters.put(key, converter);
    }

    public static <F, T> Converter<F, T> getConverter(Class<F> from, Class<T> to) throws ConverterNotFoundException {
        final FromTo fromTo = new FromTo(from, to);
        if (converters.containsKey(fromTo)) {
            return converters.get(fromTo);
        } else {
            throw new ConverterNotFoundException("Converter not found: " + fromTo);
        }
    }

    private static class FromTo {
        private final Class from;
        private final Class to;

        public FromTo(Class from, Class to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public String toString() {
            return "from = " + from + " to = " + to;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            FromTo fromTo = (FromTo) o;
            return from.equals(fromTo.from) &&
                    to.equals(fromTo.to);
        }

        @Override
        public int hashCode() {
            return Objects.hash(from, to);
        }
    }

}
