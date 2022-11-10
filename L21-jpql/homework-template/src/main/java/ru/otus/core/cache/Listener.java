package ru.otus.core.cache;


public interface Listener<K, V> {
    void notify(K key, V value, String action);
}
