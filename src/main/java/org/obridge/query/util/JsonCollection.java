package org.obridge.query.util;

import org.obridge.query.interfaces.JsonString;

import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collectors;

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
        if (name == null) {
            return "[" +
                    collection.stream().map(o -> ((JsonString) o).toJson()).collect(Collectors.joining(", "))
                    + "]";
        } else {
            return "{ \"" + name + "\": " + "[" +
                    collection.stream().map(o -> ((JsonString) o).toJson()).collect(Collectors.joining(", "))
                    + "] }";
        }
    }

    @Override
    public int size() {
        return collection.size();
    }

    @Override
    public boolean isEmpty() {
        return collection.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return collection.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return collection.iterator();
    }

    @Override
    public Object[] toArray() {
        return collection.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return collection.toArray(a);
    }

    @Override
    public boolean add(E e) {
        return collection.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return collection.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return collection.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return collection.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return collection.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return collection.retainAll(c);
    }

    @Override
    public void clear() {
        collection.clear();
    }
}
