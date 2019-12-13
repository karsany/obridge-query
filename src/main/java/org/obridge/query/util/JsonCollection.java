package org.obridge.query.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collectors;

import org.obridge.query.interfaces.JsonString;

public class JsonCollection<E extends JsonString> implements JsonString, Collection<E> {

    private final Collection<E> collection;

    private final String name;

    public JsonCollection(Collection<E> collection, String name) {
        this.collection = collection;
        this.name = name;
    }

    public JsonCollection(Collection<E> collection) {
        this.collection = collection;
        this.name = null;
    }

    @Override
    public String toJson() {
        if (this.name == null || this.name.trim()
                                          .isEmpty()) {
            return "[" +
                    this.collection.stream()
                                   .map(JsonString::toJson)
                                   .collect(Collectors.joining(", "))
                    + "]";
        } else {
            return "{ \"" + this.name + "\": " + "[" +
                    this.collection.stream()
                                   .map(JsonString::toJson)
                                   .collect(Collectors.joining(", "))
                    + "] }";
        }
    }

    @Override
    public int size() {
        return this.collection.size();
    }

    @Override
    public boolean isEmpty() {
        return this.collection.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return this.collection.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return this.collection.iterator();
    }

    @Override
    public Object[] toArray() {
        return this.collection.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return this.collection.toArray(a);
    }

    @Override
    public boolean add(E e) {
        return this.collection.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return this.collection.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return this.collection.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return this.collection.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return this.collection.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return this.collection.retainAll(c);
    }

    @Override
    public void clear() {
        this.collection.clear();
    }
}
