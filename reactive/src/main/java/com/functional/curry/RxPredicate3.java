package com.functional.curry;

import io.reactivex.Maybe;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * a functional interface that takes the first parameter, and returns a {@link Function},
 * that takes the second parameter, and returns another {@link Predicate},
 * that takes the third parameter then returns {@code boolean} ...
 * this functional interface enables partial application by default
 *
 * @param <T1> the type of the first parameter
 * @param <T2> the type of the second parameter
 * @param <T3> the type of the third parameter
 */
public interface RxPredicate3<T1, T2, T3> extends RxFunction<T1, RxBiPredicate<T2, T3>> {

    default RxPredicate<T3> test(T1 t1, T2 t2) {
        return apply(t1).apply(t2);
    }

    default RxBiPredicate<T3, T2> flip(T1 t1) {
        return t3 -> t2 -> test(t1, t2, t3);
    }

    default boolean test(T1 t1, T2 t2, T3 t3) {
        return apply(t1).apply(t2).test(t3);
    }

    default <R> Maybe<R> toMaybe(T1 parameterOne, T2 parameterTwo, T3 parameterThree, R valueIfTrue) {
        return toRxCallable(parameterOne, parameterTwo, parameterThree)
                .toMaybe()
                .filter(Boolean::booleanValue)
                .map(ignoredBoolean -> valueIfTrue);
    }

    default RxCallable<Boolean> toRxCallable(T1 t1, T2 t2, T3 t3) {
        return () -> test(t1, t2, t3);
    }
}
