package org.obridge.query.conversion;

public interface Converter<F, T> {

    T convert(F from);

}
