package com.functional.reactive;

import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;

/**
 * a functional interface that takes the first parameter, and returns a {@link Function},
 * that takes the second parameter, and returns another {@link Function},
 * that takes the third parameter then returns the result ...
 * this functional interface enables partial application by default
 *
 * @param <T1> the type of the first parameter
 * @param <T2> the type of the second parameter
 * @param <T3> the type of the third parameter
 * @param <R>  the type of the returned item
 */
public interface RxFunction3<T1, T2, T3, R> extends
        RxFunction<T1, RxBiFunction<T2, T3, R>>,
        BiFunction<T1, T2, RxFunction<T3, R>>,
        Function3<T1, T2, T3, R> {

    default RxFunction<T3, R> apply(T1 t1, T2 t2) {
        return apply(t1).apply(t2);
    }

    default RxBiFunction<T3, T2, R> flip(T1 t1) {
        return t3 -> t2 -> apply(t1).apply(t2, t3);
    }

    default RxCallable<R> toRxCallable(T1 t1, T2 t2, T3 t3) {
        return () -> apply(t1, t2, t3);
    }

    default R apply(T1 t1, T2 t2, T3 t3) {
        return apply(t1).apply(t2).apply(t3);
    }

}
