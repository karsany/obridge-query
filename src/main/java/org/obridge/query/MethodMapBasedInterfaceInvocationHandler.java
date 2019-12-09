package org.obridge.query;

import org.obridge.query.interfaces.JsonString;
import org.obridge.query.util.JsonList;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class MethodMapBasedInterfaceInvocationHandler implements InvocationHandler {

    private final Map<Method, Object> map;

    public MethodMapBasedInterfaceInvocationHandler(Map<Method, Object> map) {
        this.map = map;
    }

    public String jsonKey(String name) {
        if (name.startsWith("get")) {
            return name.substring(3, 4).toLowerCase() + name.substring(4);
        } else {
            return name;
        }
    }

    public String jsonValue(Object o) {

        if (o instanceof List) {
            return new JsonList((List<? extends JsonString>) o).toJson();
        } else {
            return "\"" + o.toString() + "\"";
        }

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (method.getName().equals("toString")) {
            return map.entrySet().stream().map(e -> e.getKey().getName() + " = " + e.getValue().toString()).collect(Collectors.toList()).toString();
        }

        if (method.getName().equals("toJson")) {
            return "{" + map.entrySet().stream().map(e -> "\"" + jsonKey(e.getKey().getName()) + "\": " + jsonValue(e.getValue())).collect(Collectors.joining(",")) + "}";
        }


        final Object value = this.map.get(method);

        return value;

    }
}

