package com.functional.curry;


import com.functional.types.Lazy;

import java.util.concurrent.Callable;

public interface LazyCallable<T> extends Callable<T> {

    default Lazy<T> toLazy() {
        return new Lazy<>(this);
    }
}
