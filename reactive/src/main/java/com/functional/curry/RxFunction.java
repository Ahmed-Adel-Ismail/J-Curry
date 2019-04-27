package com.functional.curry;


import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

public interface RxFunction<T, R> extends Function<T, R> {

    default RxCallable<R> toRxCallable(T parameter) {
        return () -> apply(parameter);
    }

    R apply(@NonNull T t);

    default RxAction toRxAction(T parameter) {
        return () -> apply(parameter);
    }

    default <V> RxFunction<V, R> compose(Function<? super V, ? extends T> before)
            throws NullPointerException {

        if (before == null) throw new NullPointerException();
        return (V v) -> apply(Functions.invokeFunction(before, v));
    }

    default <V> RxFunction<T, V> andThen(Function<? super R, ? extends V> after)
            throws NullPointerException {

        if (after == null) throw new NullPointerException();
        return (T t) -> Functions.invokeFunction(after, apply(t));
    }

}
