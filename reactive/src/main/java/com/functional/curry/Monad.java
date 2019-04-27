package com.functional.curry;


import com.functional.types.Objects;

import java.util.concurrent.Callable;

import io.reactivex.functions.Function;

public interface Monad<T> extends Callable<T> {

    default <R> R to(Function<T, R> flatMapper) throws NullPointerException {
        return Functions.invokeFunction(Objects.requireNonNull(flatMapper),
                Functions.invokeCallable(this));
    }

}
