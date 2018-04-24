package com.functional.curry;

import io.reactivex.functions.Function;

/**
 * a functional interface that takes the first parameter, and returns a {@link Function},
 * that takes the second parameter then returns the result ...
 * this functional interface enables partial application by default,
 * unlike other functional interfaces that you need to use {@link Curry} to partially apply them
 *
 * @param <T1> the type of the first parameter
 * @param <T2> the type of the second parameter
 * @param <R>  the type of the returned item
 */
public interface RxBiFunction<T1, T2, R> extends RxFunction<T1, RxFunction<T2, R>> {

}
