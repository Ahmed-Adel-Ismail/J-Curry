package com.functional.reactive;


import com.functional.reactive.RxCallable;

import java.util.NoSuchElementException;

import io.reactivex.annotations.NonNull;

public class LateInit<T> implements RxCallable<T> {

    private boolean initialized;
    private T value;

    @Override
    @NonNull
    public T call() throws NoSuchElementException {
        if (!initialized) throw new NoSuchElementException("value not initialized yet");
        return value;
    }

    public void initialize(@NonNull T value) throws NullPointerException, IllegalArgumentException {
        if (value == null) throw new NullPointerException();
        if (initialized) return;
        this.value = value;
        this.initialized = true;
    }

    public boolean isInitialized() {
        return initialized;
    }

}
