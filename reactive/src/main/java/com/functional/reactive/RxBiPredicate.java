package com.functional.reactive;


import io.reactivex.Maybe;
import io.reactivex.functions.BiPredicate;
import io.reactivex.functions.Predicate;

/**
 * a functional interface that takes the first parameter, and returns a {@link Predicate},
 * that takes the second parameter, and returns {@code boolean},
 * this functional interface enables partial application by default
 *
 * @param <T1> the type of the first parameter
 * @param <T2> the type of the second parameter
 */
public interface RxBiPredicate<T1, T2> extends
        RxFunction<T1, RxPredicate<T2>>,
        BiPredicate<T1, T2> {

    default RxBiPredicate<T1, T2> and(BiPredicate<? super T1, ? super T2> other)
            throws NullPointerException {

        if (other == null) throw new NullPointerException();
        return t1 -> t2 -> test(t1, t2) && Functions.invokeBiPredicate(other, t1, t2);
    }

    default boolean test(T1 t1, T2 t2) {
        return apply(t1).test(t2);
    }

    default boolean flip(T2 t2, T1 t1) {
        return test(t1, t2);
    }

    default RxPredicate<T1> flip(T2 t2) {
        return flip().apply(t2);
    }

    default RxBiPredicate<T2, T1> flip() {
        return t2 -> t1 -> test(t1, t2);
    }

    default RxBiPredicate<T1, T2> negate() {
        return t1 -> t2 -> !test(t1, t2);
    }

    default RxBiPredicate<T1, T2> or(BiPredicate<? super T1, ? super T2> other)
            throws NullPointerException {

        if (other == null) throw new NullPointerException();
        return t1 -> t2 -> test(t1, t2) || Functions.invokeBiPredicate(other, t1, t2);
    }

    default <R> Maybe<R> toMaybe(T1 parameterOne, T2 parameterTwo, R valueIfTrue) {
        return toRxCallable(parameterOne, parameterTwo)
                .toMaybe()
                .filter(Boolean::booleanValue)
                .map(ignoredBoolean -> valueIfTrue);
    }

    default RxCallable<Boolean> toRxCallable(T1 t1, T2 t2) {
        return () -> test(t1, t2);
    }

}
