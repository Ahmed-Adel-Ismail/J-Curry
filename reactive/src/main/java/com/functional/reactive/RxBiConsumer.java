package com.functional.reactive;


import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;

/**
 * a functional interface that takes the first parameter, and returns a {@link Consumer},
 * that takes the second parameter, and returns {@code void},
 * this functional interface enables partial application by default
 *
 * @param <T1> the type of the first parameter
 * @param <T2> the type of the second parameter
 */
public interface RxBiConsumer<T1, T2> extends
        BiConsumer<T1, T2>,
        RxFunction<T1, RxConsumer<T2>> {

    default RxAction toRxAction(T1 t1, T2 t2) {
        return () -> accept(t1, t2);
    }

    default void accept(T1 t1, T2 t2) {
        apply(t1).accept(t2);
    }

    default void flip(T2 t2, T1 t1) {
        accept(t1, t2);
    }

    default RxConsumer<T1> flip(T2 t2) {
        return flip().apply(t2);
    }

    default RxBiConsumer<T2, T1> flip() {
        return t2 -> t1 -> accept(t1, t2);
    }

    default RxBiConsumer<T1, T2> compose(BiConsumer<? super T1, ? super T2> before) throws NullPointerException {
        if (before == null) throw new NullPointerException();
        return t1 -> t2 -> {
            Functions.invokeBiConsumer(before, t1, t2);
            accept(t1, t2);
        };
    }

    default RxBiConsumer<T1, T2> andThen(BiConsumer<? super T1, ? super T2> after) throws NullPointerException {
        if (after == null) throw new NullPointerException();
        return t1 -> t2 -> {
            accept(t1, t2);
            Functions.invokeBiConsumer(after, t1, t2);
        };
    }

}
