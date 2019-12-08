package hu.karsany.dbobject;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.stream.Collectors;

class MethodMapBasedInterfaceInvocationHandler implements InvocationHandler {

    private final Map<Method, Object> map;

    public MethodMapBasedInterfaceInvocationHandler(Map<Method, Object> map) {
        this.map = map;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (method.getName().equals("toString")) {
            return map.entrySet().stream().map(e -> e.getKey().getName() + " = " + e.getValue().toString()).collect(Collectors.toList()).toString();
        }

        final Object value = this.map.get(method);

        return value;


        /*
            final Class<?> returnType = method.getReturnType();

            if (value.getClass().equals(returnType)) {
                return value;
            } else if (returnType.isInstance(value)) {
                return value;
            } else if (returnType.equals(LocalDate.class) && value.getClass().equals(Timestamp.class)) {
                return new ProxyRowMapperTest.SqlTimestampToLocalDate().convert((Timestamp) value);
            } else if (value instanceof BigDecimal && returnType.equals(Integer.class)) {
                return new ProxyRowMapperTest.BigDecimalToInteger().convert((BigDecimal) value);
            } else {
                System.out.println(String.format("Cannot convert %s to %s.", value.getClass(), returnType));
                return null;
            }
        */

    }
}

