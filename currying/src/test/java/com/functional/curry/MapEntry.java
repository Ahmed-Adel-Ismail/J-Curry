package com.functional.curry;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

class MapEntry<T1, T2> implements Callable<Map.Entry<T1, T2>> {

    private final T1 key;
    private final T2 value;

    private MapEntry(T1 key, T2 value) {
        this.key = key;
        this.value = value;
    }

    static <T1, T2> MapEntry<T1, T2> with(T1 key, T2 value) {
        return new MapEntry<>(key, value);
    }

    @Override
    public Map.Entry<T1, T2> call() {
        Map<T1, T2> map = new HashMap<>();
        map.put(key, value);
        for (Map.Entry<T1, T2> entry : map.entrySet()) {
            return entry;
        }
        return null;
    }
}
