package com.functional.curry;

import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * a functional interface that takes the first parameter, and returns a {@link Function},
 * that takes the second parameter, and returns another {@link Function},
 * that takes the third parameter, and returns another {@link Predicate},
 * that takes the fourth parameter then returns {@code void}
 * this functional interface enables partial application by default,
 * unlike other functional interfaces that you need to use {@link Curry} to partially apply them
 *
 * @param <T1> the type of the first parameter
 * @param <T2> the type of the second parameter
 * @param <T3> the type of the third parameter
 * @param <T4> the type of the fourth parameter
 */
public interface RxPredicate4<T1, T2, T3, T4> extends RxFunction<T1, RxPredicate3<T2, T3, T4>> {
}
