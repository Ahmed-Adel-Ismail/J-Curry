package com.functional.curry;


import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.BiPredicate;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * a class that Curries functions after swapping it's arguments, where the first argument becomes
 * the second argument, and the second argument becomes the first one
 * <p>
 * Created by Ahmed Adel Ismail on 7/26/2017.
 */
public class SwapCurry
{

    /**
     * curry a {@link BiFunction} after swapping it's parameters, where it's first parameter
     * will be the second one, and the second parameter will be the first one
     *
     * @param function       the {@link BiFunction} that will be executed
     * @param parameterTwo   the original function's second parameter
     * @param <ParameterOne> the type of the first parameter
     * @param <ParameterTwo> the type of the second parameter
     * @param <Return>       the type of the expected result
     * @return the {@link Function} that will be passed the first parameter
     * later and be executed
     */
    public static <ParameterOne, ParameterTwo, Return> RxFunction<ParameterOne, Return>
    toFunction(BiFunction<ParameterOne, ParameterTwo, Return> function, ParameterTwo parameterTwo) {
        return new CurriedBiFunction<>(swappedFunction(function), parameterTwo);
    }

    private static <T1, T2, R> BiFunction<T2, T1, R> swappedFunction(final BiFunction<T1, T2, R> function) {
        return new BiFunction<T2, T1, R>()
        {
            @Override
            public R apply(T2 parameterTwo, T1 parameterOne) throws Exception {
                return function.apply(parameterOne, parameterTwo);
            }
        };
    }

    /**
     * curry a {@link BiConsumer} after swapping it's parameters, where it's first parameter
     * will be the second one, and the second parameter will be the first one
     *
     * @param consumer       the {@link BiConsumer} that will be executed
     * @param parameterTwo   the original function's second parameter
     * @param <ParameterOne> the type of the first parameter
     * @param <ParameterTwo> the type of the second parameter
     * @return the {@link Consumer} that will be passed the first parameter
     * later and be executed
     */
    public static <ParameterOne, ParameterTwo> RxConsumer<ParameterOne>
    toConsumer(BiConsumer<ParameterOne, ParameterTwo> consumer, ParameterTwo parameterTwo) {
        return new CurriedBiConsumer<>(swappedConsumer(consumer), parameterTwo);
    }

    private static <T1, T2> BiConsumer<T2, T1> swappedConsumer(final BiConsumer<T1, T2> consumer) {
        return new BiConsumer<T2, T1>()
        {
            @Override
            public void accept(T2 t2, T1 t1) throws Exception {
                consumer.accept(t1, t2);
            }
        };
    }


    /**
     * curry a {@link BiPredicate} after swapping it's parameters, where it's first parameter
     * will be the second one, and the second parameter will be the first one
     *
     * @param predicate      the {@link BiPredicate} that will be executed
     * @param parameterTwo   the original function's second parameter
     * @param <ParameterOne> the type of the first parameter
     * @param <ParameterTwo> the type of the second parameter
     * @return the {@link Predicate} that will be passed the first parameter
     * later and be executed
     */
    public static <ParameterOne, ParameterTwo> RxPredicate<ParameterOne>
    toPredicate(BiPredicate<ParameterOne, ParameterTwo> predicate, ParameterTwo parameterTwo) {
        return new CurriedBiPredicate<>(swappedPredicate(predicate), parameterTwo);
    }

    private static <T1, T2> BiPredicate<T2, T1> swappedPredicate(final BiPredicate<T1, T2> predicate) {
        return new BiPredicate<T2, T1>()
        {
            @Override
            public boolean test(@NonNull T2 t2, @NonNull T1 t1) throws Exception {
                return predicate.test(t1, t2);
            }
        };
    }

}
