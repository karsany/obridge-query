package hu.karsany.dbobject.conversion;

public interface Converter<F, T> {

    T valueOf(F from);

}
