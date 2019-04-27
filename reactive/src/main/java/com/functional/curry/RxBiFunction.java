package com.functional.curry;

import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

/**
 * a functional interface that takes the first parameter, and returns a {@link Function},
 * that takes the second parameter then returns the result ...
 * this functional interface enables partial application by default
 *
 * @param <T1> the type of the first parameter
 * @param <T2> the type of the second parameter
 * @param <R>  the type of the returned item
 */
public interface RxBiFunction<T1, T2, R> extends
        RxFunction<T1, RxFunction<T2, R>>,
        BiFunction<T1, T2, R> {

    default R flip(T2 t2, T1 t1) {
        return apply(t1, t2);
    }

    default R apply(T1 t1, T2 t2) {
        return apply(t1).apply(t2);
    }

    default RxFunction<T1, R> flip(T2 t2) {
        return flip().apply(t2);
    }

    default RxBiFunction<T2, T1, R> flip() {
        return t2 -> t1 -> apply(t1, t2);
    }

    default RxCallable<R> toRxCallable(T1 t1, T2 t2) {
        return () -> apply(t1, t2);
    }

}
