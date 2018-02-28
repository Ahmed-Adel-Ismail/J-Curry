package com.functional.curry;

import org.javatuples.Pair;
import org.javatuples.Triplet;

import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;

/**
 * a class that holds functions that breaks down tuple into parameters
 * <p>
 * Created by Ahmed Adel Ismail on 2/21/2018.
 */
public class Tuples {

    /**
     * pass the {@link Triplet#getValue0()} as the first parameter to the given {@link Function3},
     * and {@link Triplet#getValue1()} as the second parameter, and {@link Triplet#getValue2()} as
     * the third one
     *
     * @param function3 the {@link Function3} that will handle the passed parameters
     * @param triplet   the {@link Triplet} that holds the parameters in the same order
     * @param <T1>      the type of the first parameter
     * @param <T2>      the type of the second parameter
     * @param <T3>      the type of the third parameter
     * @param <R>       the expected return type
     * @return the result of the passed {@link Function3}
     */
    public static <T1, T2, T3, R> R withFunction3(Function3<T1, T2, T3, R> function3,
                                                  Triplet<T1, T2, T3> triplet) {
        try {
            return function3.apply(triplet.getValue0(), triplet.getValue1(), triplet.getValue2());
        } catch (Throwable e) {
            throw new RuntimeExceptionConverter().apply(e);
        }
    }

    /**
     * pass the {@link Pair#getValue0()} as the first parameter to the given {@link BiConsumer},
     * and {@link Pair#getValue1()} as the second parameter
     *
     * @param biConsumer the {@link BiConsumer} that will handle the passed parameters
     * @param <T1>       the type of the first parameter
     * @param <T2>       the type of the second parameter
     */
    public static <T1, T2> RxConsumer<Pair<T1, T2>> toConsumer(final BiConsumer<T1, T2> biConsumer) {
        return new RxConsumer<Pair<T1, T2>>() {
            @Override
            public void accept(Pair<T1, T2> pair) {
                withBiConsumer(biConsumer, pair);
            }
        };
    }

    /**
     * pass the {@link Pair#getValue0()} as the first parameter to the given {@link BiConsumer},
     * and {@link Pair#getValue1()} as the second parameter
     *
     * @param biConsumer the {@link BiConsumer} that will handle the passed parameters
     * @param pair       the {@link Pair} that holds the parameters in the same order
     * @param <T1>       the type of the first parameter
     * @param <T2>       the type of the second parameter
     */
    public static <T1, T2> void withBiConsumer(BiConsumer<T1, T2> biConsumer, Pair<T1, T2> pair) {
        try {
            biConsumer.accept(pair.getValue0(), pair.getValue1());
        } catch (Throwable e) {
            throw new RuntimeExceptionConverter().apply(e);
        }
    }

    /**
     * create a {@link Function} that takes the {@link Pair#getValue0()} as the first parameter
     * and {@link Pair#getValue1()} as the second parameter
     *
     * @param biFunction the {@link BiFunction} that will handle the passed parameters
     * @param <T1>       the type of the first parameter
     * @param <T2>       the type of the second parameter
     * @param <R>        the expected return type
     * @return the result of the passed {@link BiFunction}
     */
    public static <T1, T2, R> RxFunction<Pair<T1, T2>, R> toFunction(
            final BiFunction<T1, T2, R> biFunction) {
        return new RxFunction<Pair<T1, T2>, R>() {
            @Override
            public R apply(Pair<T1, T2> pair) {
                return withBiFunction(biFunction, pair);
            }
        };

    }

    /**
     * pass the {@link Pair#getValue0()} as the first parameter to the given {@link BiFunction},
     * and {@link Pair#getValue1()} as the second parameter
     *
     * @param biFunction the {@link BiFunction} that will handle the passed parameters
     * @param pair       the {@link Pair} that holds the parameters in the same order
     * @param <T1>       the type of the first parameter
     * @param <T2>       the type of the second parameter
     * @param <R>        the expected return type
     * @return the result of the passed {@link BiFunction}
     */
    public static <T1, T2, R> R withBiFunction(BiFunction<T1, T2, R> biFunction, Pair<T1, T2> pair) {
        try {
            return biFunction.apply(pair.getValue0(), pair.getValue1());
        } catch (Throwable e) {
            throw new RuntimeExceptionConverter().apply(e);
        }
    }

}
