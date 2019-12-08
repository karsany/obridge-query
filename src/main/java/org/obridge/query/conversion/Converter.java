package org.obridge.query.conversion;

public interface Converter<F, T> {

    T valueOf(F from);

}
