package ru.otus.cache;

import java.util.HashSet;
import java.util.Set;
import java.util.WeakHashMap;

import static ru.otus.cache.LocalWeakRefCache.Action.GET;
import static ru.otus.cache.LocalWeakRefCache.Action.PUT;
import static ru.otus.cache.LocalWeakRefCache.Action.REMOVE;

public class LocalWeakRefCache<K, V> implements Cache<K, V> {

    private final WeakHashMap<K, V> cache = new WeakHashMap<>();
    private final Set<Listener<K, V>> listeners = new HashSet<>();

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
        notifyByListener(key, value, PUT.name());
    }

    @Override
    public void remove(K key) {
        var value = cache.remove(key);
        notifyByListener(key, value, REMOVE.name());
    }

    @Override
    public V get(K key) {
        V value = cache.get(key);
        notifyByListener(key, value, GET.name());
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

    enum Action {
        GET,
        PUT,
        REMOVE
    }
}
