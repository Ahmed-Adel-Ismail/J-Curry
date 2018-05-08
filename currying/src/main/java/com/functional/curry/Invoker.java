package com.functional.curry;


import java.util.concurrent.Callable;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.BiPredicate;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.functions.Predicate;

/**
 * a class that handles invoking crashing functions and convert it's {@link Exception} thrown to
 * a {@link RuntimeException}
 * <p>
 * Created by Ahmed Adel Ismail on 12/10/2017.
 */
public class Invoker {

    /**
     * invoke a {@link Function} or throw a {@link RuntimeException}
     *
     * @param function  the {@link Function} to invoke
     * @param parameter the parameter passed
     * @param <T>       the type of the parameter
     * @param <R>       the type of the return
     * @return the result of the invocation
     */
    public static <T, R> R invoke(Function<T, R> function, T parameter) {
        try {
            return function.apply(parameter);
        } catch (Exception e) {
            throw new RuntimeExceptionConverter().apply(e);
        }
    }

    /**
     * invoke a {@link BiFunction} or throw a {@link RuntimeException}
     *
     * @param biFunction   the {@link Function} to invoke
     * @param parameterOne the first parameter passed
     * @param parameterTwo the second parameter passed
     * @param <T1>         the type of the first parameter
     * @param <T2>         the type of the second parameter
     * @param <R>          the type of the return
     * @return the result of the invocation
     */
    public static <T1, T2, R> R invoke(BiFunction<T1, T2, R> biFunction, T1 parameterOne, T2 parameterTwo) {
        try {
            return biFunction.apply(parameterOne, parameterTwo);
        } catch (Exception e) {
            throw new RuntimeExceptionConverter().apply(e);
        }
    }

    /**
     * invoke a {@link Consumer} or throw a {@link RuntimeException}
     *
     * @param consumer  the {@link Consumer} to invoke
     * @param parameter the parameter passed
     * @param <T>       the type of the parameter
     */
    public static <T> void invoke(Consumer<T> consumer, T parameter) {
        try {
            consumer.accept(parameter);
        } catch (Exception e) {
            throw new RuntimeExceptionConverter().apply(e);
        }
    }

    /**
     * invoke a {@link BiConsumer} or throw a {@link RuntimeException}
     *
     * @param biConsumer   the {@link BiConsumer} to invoke
     * @param parameterOne the first parameter passed
     * @param parameterTwo the second parameter passed
     * @param <T1>         the type of the first parameter
     * @param <T2>         the type of the second parameter
     */
    public static <T1, T2> void invoke(BiConsumer<T1, T2> biConsumer, T1 parameterOne, T2 parameterTwo) {
        try {
            biConsumer.accept(parameterOne, parameterTwo);
        } catch (Exception e) {
            throw new RuntimeExceptionConverter().apply(e);
        }
    }

    /**
     * invoke a {@link Predicate} or throw a {@link RuntimeException}
     *
     * @param predicate the {@link Predicate} to invoke
     * @param parameter the parameter passed
     * @param <T>       the type of the parameter
     * @return the result of the invocation
     */
    public static <T> boolean invoke(Predicate<T> predicate, T parameter) {
        try {
            return predicate.test(parameter);
        } catch (Exception e) {
            throw new RuntimeExceptionConverter().apply(e);
        }
    }

    /**
     * invoke a {@link Callable} or throw a {@link RuntimeException}
     *
     * @param callable the {@link Callable} to invoke
     * @param <R>      the type of the return
     * @return the result of the invocation
     */
    public static <R> R invoke(Callable<R> callable) {
        try {
            return callable.call();
        } catch (Exception e) {
            throw new RuntimeExceptionConverter().apply(e);
        }
    }

    /**
     * invoke an {@link Action} or throw a {@link RuntimeException}
     *
     * @param action the {@link Action} to invoke
     */
    public static void invoke(Action action) {
        try {
            action.run();
        } catch (Exception e) {
            throw new RuntimeExceptionConverter().apply(e);
        }
    }

    public static <T1, T2> boolean invoke(BiPredicate<T1, T2> biPredicate, T1 parameterOne, T2 parameterTwo) {
        try {
            return biPredicate.test(parameterOne, parameterTwo);
        } catch (Exception e) {
            throw new RuntimeExceptionConverter().apply(e);
        }
    }

    public static <T2, T1, T3,R> R invoke(Function3<T1, T2, T3, R> function3, T1 parameterOne, T2 parameterTwo, T3 parameterThree) {
        try{
            return function3.apply(parameterOne,parameterTwo,parameterThree);
        }catch (Exception e){
            throw new RuntimeExceptionConverter().apply(e);
        }
    }

    /**
     * a class that wraps any Throwable and turns it into a
     * {@link RuntimeException}
     * <p>
     * Created by Ahmed Adel Ismail on 5/3/2017.
     */
    public static class RuntimeExceptionConverter implements Function<Throwable, RuntimeException> {


        @Override
        public RuntimeException apply(@NonNull Throwable e) {
            return (e instanceof RuntimeException)
                    ? (RuntimeException) e
                    : new RuntimeException(e);
        }

    }

}
