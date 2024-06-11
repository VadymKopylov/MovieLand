package com.kopylov.movieland.util;

import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class SoftReferenceCache<K, V> {

    private final ConcurrentHashMap<K, SoftReference<V>> cache = new ConcurrentHashMap<>();

    private final Function<K, V> loader;

    public SoftReferenceCache(Function<K, V> loader) {
        this.loader = loader;
    }

    public V get(K key) {
        SoftReference<V> dtoSoftReference = cache.compute(key, (cacheKey, val) -> {
            if (val == null || val.get() == null) {
                V value = loader.apply(cacheKey);
                return new SoftReference<>(value);
            }
            return val;
        });
        return dtoSoftReference.get();
    }

    public void put(K key, V value) {
        cache.put(key, new SoftReference<>(value));
    }
}
