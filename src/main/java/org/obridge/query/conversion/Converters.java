package org.obridge.query.conversion;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class Converters {

    static Map<FromTo, Converter> converters = new HashMap<>();

    static {
        Converters.register(new NumberToIntegerConverter());
        Converters.register(new TimestampToLocalDateConverter());
    }

    public static void register(Converter converter) {

        final FromTo key = new FromTo(
                                      (Class) ((ParameterizedType) converter.getClass()
                                                                            .getGenericInterfaces()[0]).getActualTypeArguments()[0],
                                      (Class) ((ParameterizedType) converter.getClass()
                                                                            .getGenericInterfaces()[0]).getActualTypeArguments()[1]);
        System.out.println("Registered converter: " + key);
        converters.put(key, converter);
    }

    @SuppressWarnings("unchecked")
    public static <F, T> Converter<F, T> getConverter(Class<F> from, Class<T> to) throws ConverterNotFoundException {
        final FromTo fromTo = new FromTo(from, to);
        if (converters.containsKey(fromTo)) {
            return converters.get(fromTo);
        } else {
            return converters.entrySet()
                             .stream()
                             .filter(entry -> entry.getKey().from.isAssignableFrom(from)
                                     && to.equals(entry.getKey().to))
                             .map(Map.Entry::getValue)
                             .findFirst()
                             .orElseThrow(() -> new ConverterNotFoundException("Converter not found: " + fromTo));
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
            return "from = " + this.from + " to = " + this.to;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            FromTo fromTo = (FromTo) o;
            return this.from.equals(fromTo.from) &&
                    this.to.equals(fromTo.to);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.from, this.to);
        }
    }

}
