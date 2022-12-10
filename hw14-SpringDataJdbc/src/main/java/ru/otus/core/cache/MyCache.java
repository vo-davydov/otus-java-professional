package ru.otus.core.cache;

import java.util.HashSet;
import java.util.Set;
import java.util.WeakHashMap;

public class MyCache<K, V> implements Cache<K, V> {
//Надо реализовать эти методы

    private final WeakHashMap<K, V> cache = new WeakHashMap<>();
    private final Set<Listener<K, V>> listeners = new HashSet<>();

    private final String GET = "GET";
    private final String PUT = "PUT";
    private final String REMOVE = "REMOVE";

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
        notifyByListener(key, value, PUT);
    }

    @Override
    public void remove(K key) {
        var value = cache.remove(key);
        notifyByListener(key, value, REMOVE);
    }

    @Override
    public V get(K key) {
        V value = cache.get(key);
        notifyByListener(key, value, GET);
        return value;
    }

    @Override
    public void addListener(Listener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(Listener<K, V> listener) {
        listeners.remove(listener);
    }

    private void notifyByListener(K key, V value, String action) {
        if (!listeners.isEmpty()) {
            for (var listener : listeners) {
                listener.notify(key, value, action);
            }
        }
    }
}
