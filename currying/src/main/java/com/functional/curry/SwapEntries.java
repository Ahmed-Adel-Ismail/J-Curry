package com.functional.curry;

import java.util.Map.Entry;

import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

/**
 * a class that holds functions that breaks down {@link Entry} into parameters, but it swaps the order of
 * the parameters, so it passes {@link Entry#getKey()} as the second argument in
 * the function, and passes the {@link Entry#getValue()}
 * <p>
 * Created by Ahmed Adel Ismail on 2/28/2018.
 */
public class SwapEntries {

    /**
     * pass the {@link Entry#getValue()}  as the first parameter to the given {@link BiConsumer},
     * and {@link Entry#getKey()} as the second parameter
     *
     * @param biConsumer the {@link BiConsumer} that will handle the passed parameters
     * @param <T1>       the type of the first parameter
     * @param <T2>       the type of the second parameter
     */
    public static <T1, T2> RxConsumer<Entry<T2, T1>> toConsumer(final BiConsumer<T1, T2> biConsumer) {
        return new RxConsumer<Entry<T2, T1>>() {
            @Override
            public void accept(Entry<T2, T1> pair) {
                withBiConsumer(biConsumer, pair);
            }
        };
    }

    /**
     * pass the {@link Entry#getValue()}  as the first parameter to the given {@link BiConsumer},
     * and {@link Entry#getKey()}  as the second parameter
     *
     * @param biConsumer the {@link BiConsumer} that will handle the passed parameters
     * @param entry      the {@link Entry} that holds the parameters in the same order
     * @param <T1>       the type of the first parameter
     * @param <T2>       the type of the second parameter
     */
    public static <T1, T2> void withBiConsumer(BiConsumer<T1, T2> biConsumer, Entry<T2, T1> entry) {
        Invoker.invoke(biConsumer, entry.getValue(), entry.getKey());
    }

    /**
     * create a {@link Function} that takes the {@link Entry#getValue()} as the first parameter
     * and {@link Entry#getKey()}  as the second parameter
     *
     * @param biFunction the {@link BiFunction} that will handle the passed parameters
     * @param <T1>       the type of the first parameter
     * @param <T2>       the type of the second parameter
     * @param <R>        the expected return type
     * @return the result of the passed {@link BiFunction}
     */
    public static <T1, T2, R> RxFunction<Entry<T2, T1>, R> toFunction(
            final BiFunction<T1, T2, R> biFunction) {
        return new RxFunction<Entry<T2, T1>, R>() {
            @Override
            public R apply(Entry<T2, T1> pair) {
                return withBiFunction(biFunction, pair);
            }
        };

    }

    /**
     * pass the {@link Entry#getValue()}  as the first parameter to the given {@link BiFunction},
     * and {@link Entry#getKey()}  as the second parameter
     *
     * @param biFunction the {@link BiFunction} that will handle the passed parameters
     * @param entry      the {@link Entry} that holds the parameters in the same order
     * @param <T1>       the type of the first parameter
     * @param <T2>       the type of the second parameter
     * @param <R>        the expected return type
     * @return the result of the passed {@link BiFunction}
     */
    public static <T1, T2, R> R withBiFunction(BiFunction<T1, T2, R> biFunction, Entry<T2, T1> entry) {
        return Invoker.invoke(biFunction, entry.getValue(), entry.getKey());
    }
}
