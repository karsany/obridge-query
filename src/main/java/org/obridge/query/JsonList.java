package org.obridge.query;

import java.util.List;
import java.util.stream.Collectors;

public class JsonList implements JsonString {

    private final List<? extends JsonString> list;
    private final String name;

    public JsonList(List<? extends JsonString> list, String name) {
        this.list = list;
        this.name = name;
    }

    @Override
    public String toJson() {
        return "{ \"" + name + "\": " + "[" +
                list.stream().map(o -> ((JsonString) o).toJson()).collect(Collectors.joining(", "))
                + "] }";
    }
}
