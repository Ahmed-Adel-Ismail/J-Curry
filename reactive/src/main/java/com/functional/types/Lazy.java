package com.functional.types;


import com.functional.curry.CallableStream;
import com.functional.curry.Functions;

import java.util.concurrent.Callable;

import io.reactivex.annotations.NonNull;

public class Lazy<T> implements CallableStream<T> {

    private final Callable<T> initializer;
    private final LateInit<T> value = new LateInit<>();

    public Lazy(@NonNull Callable<T> initializer) throws NullPointerException {
        if (initializer == null) throw new NullPointerException();
        this.initializer = initializer;
    }

    @Override
    @NonNull
    public T call() {
        if (!value.isInitialized()) {
            value.initialize(Functions.invokeCallable(initializer));
        }
        return value.call();
    }
}
