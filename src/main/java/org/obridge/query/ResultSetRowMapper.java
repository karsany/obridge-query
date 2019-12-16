package org.obridge.query;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.obridge.query.conversion.Converters;
import org.obridge.query.conversion.exception.ConverterNotFoundException;
import org.obridge.query.exception.MappingException;

class ResultSetRowMapper<T> {
    private final Class<T> clazz;

    public ResultSetRowMapper(Class<T> clazz) {
        this.clazz = clazz;
    }

    @SuppressWarnings("unchecked")
    public T getObject(Map<Method, String> methodColumnNameMap, ResultSet resultSet) throws SQLException {

        Map<Method, Object> methodMap = new LinkedHashMap<>();

        for (Map.Entry<Method, String> metCol : methodColumnNameMap.entrySet()) {
            final Method key = metCol.getKey();
            Object value = resultSet.getObject(metCol.getValue());

            if (value.getClass()
                     .equals(key.getReturnType())) {
                // nothing to do
            } else if (key.getReturnType()
                          .isInstance(value)) {
                // nothing to do
            } else if (value instanceof ResultSet) {

                final Type genericReturnType = key.getGenericReturnType();
                final ParameterizedType returnType = (ParameterizedType) genericReturnType;
                final Type actualTypeArgument = returnType.getActualTypeArguments()[0];

                value = new AutoObject<>((Class<?>) actualTypeArgument, (ResultSet) value).getList();

            } else {
                // convert
                try {
                    value = Converters.getConverter(value.getClass(), (Class) key.getGenericReturnType())
                                      .convert(value);
                } catch (ConverterNotFoundException e) {
                    if (key.getReturnType()
                           .equals(String.class)) {
                        value = value.toString();
                    } else {
                        throw new MappingException(e);
                    }
                }
            }

            methodMap.put(key, value);
        }

        return (T) Proxy.newProxyInstance(this.getClass()
                                              .getClassLoader(),
                                          new Class[] { this.clazz },
                                          new MethodMapBasedInterfaceInvocationHandler(methodMap));

    }
}
