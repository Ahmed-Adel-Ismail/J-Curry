package com.functional.reactive;

import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * a functional interface that takes the first parameter, and returns a {@link Function},
 * that takes the second parameter, and returns another {@link Consumer},
 * that takes the third parameter then returns {@code void} ...
 * this functional interface enables partial application by default
 *
 * @param <T1> the type of the first parameter
 * @param <T2> the type of the second parameter
 * @param <T3> the type of the third parameter
 */
public interface RxConsumer3<T1, T2, T3>
        extends RxFunction<T1, RxBiConsumer<T2, T3>>,
        BiFunction<T1, T2, RxConsumer<T3>>,
        Consumer3<T1, T2, T3> {

    default RxConsumer<T3> apply(T1 t1, T2 t2) {
        return apply(t1).apply(t2);
    }

    default RxAction toRxAction(T1 t1, T2 t2, T3 t3) {
        return () -> accept(t1, t2, t3);
    }

    default void accept(T1 t1, T2 t2, T3 t3) {
        apply(t1).apply(t2).accept(t3);
    }

    default RxBiConsumer<T3, T2> flip(T1 t1) {
        return t3 -> t2 -> apply(t1).accept(t2, t3);
    }

}
