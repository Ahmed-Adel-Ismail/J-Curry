package com.functional.curry;

import io.reactivex.functions.Predicate;

/**
 * a functional interface that takes the first parameter, and returns a {@link Predicate},
 * that takes the second parameter, and returns {@code boolean},
 * this functional interface enables partial application by default,
 * unlike other functional interfaces that you need to use {@link Curry} to partially apply them
 *
 * @param <T1> the type of the first parameter
 * @param <T2> the type of the second parameter
 */
public interface RxBiPredicate<T1, T2> extends RxFunction<T1, RxPredicate<T2>> {
}
