package org.obridge.query;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.obridge.query.interfaces.JsonString;
import org.obridge.query.util.JsonCollection;

class MethodMapBasedInterfaceInvocationHandler implements InvocationHandler {

    private final Map<Method, Object> map;

    public MethodMapBasedInterfaceInvocationHandler(Map<Method, Object> map) {
        this.map = map;
    }

    public static String jsonKey(String name) {
        if (name.startsWith("get")) {
            return name.substring(3, 4)
                       .toLowerCase()
                    + name.substring(4);
        } else {
            return name;
        }
    }

    public static String jsonValue(Object o) {

        if (Collection.class.isAssignableFrom(o.getClass())) {
            return new JsonCollection<>((Collection<? extends JsonString>) o).toJson();
        } else {
            return "\"" + o.toString() + "\"";
        }

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {

        if (method.getName()
                  .equals("toString")) {
            return this.map.entrySet()
                      .stream()
                      .map(e -> e.getKey()
                                 .getName()
                              + " = " + e.getValue()
                                         .toString())
                      .collect(Collectors.toList())
                      .toString();
        }

        if (method.getName()
                  .equals("toJson")) {
            return "{" + this.map.entrySet()
                            .stream()
                            .map(e -> "\"" + MethodMapBasedInterfaceInvocationHandler.jsonKey(e.getKey()
                                                      .getName())
                                    + "\": " + MethodMapBasedInterfaceInvocationHandler.jsonValue(e.getValue()))
                            .collect(Collectors.joining(","))
                    + "}";
        }

        return this.map.get(method);
    }
}
