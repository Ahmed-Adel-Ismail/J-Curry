package com.functional.curry;


import java.util.concurrent.Callable;

import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.BiPredicate;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.functions.Predicate;

public class Functions {

    public static <V> RxCallable<V> asRxCallable(Callable<V> callable) {
        return () -> invokeCallable(callable);
    }

    public static <T> T invokeCallable(Callable<T> function) {
        return new Invoker<T>().apply(function);
    }

    public static RxAction asRxAction(Action action) {
        return () -> invokeAction(action);
    }

    public static void invokeAction(Action function) {
        new Invoker<Void>().apply(() -> {
            function.run();
            return null;
        });
    }

    public static <T> RxConsumer<T> asRxConsumer(Consumer<T> consumer) {
        return t -> invokeConsumer(consumer, t);
    }

    public static <T> void invokeConsumer(Consumer<T> function, T t) {
        new Invoker<Void>().apply(() -> {
            function.accept(t);
            return null;
        });
    }

    public static <T, R> RxFunction<T, R> asRxFunction(Function<T, R> function) {
        return t -> invokeFunction(function, t);
    }

    public static <T, R> R invokeFunction(Function<T, R> function, T t) {
        return new Invoker<R>().apply(() -> function.apply(t));
    }

    public static <T> RxPredicate<T> asRxPredicate(Predicate<T> predicate) {
        return t -> invokePredicate(predicate, t);
    }

    public static <T> boolean invokePredicate(Predicate<T> function, T t) {
        return new Invoker<Boolean>().apply(() -> function.test(t));
    }

    public static <T1, T2> RxBiConsumer<T1, T2> asRxBiConsumer(BiConsumer<T1, T2> biConsumer) {
        return t1 -> t2 -> invokeBiConsumer(biConsumer, t1, t2);
    }

    public static <T1, T2> void invokeBiConsumer(BiConsumer<T1, T2> function, T1 t1, T2 t2) {
        new Invoker<Void>().apply(() -> {
            function.accept(t1, t2);
            return null;
        });
    }

    public static <T1, T2, R> RxBiFunction<T1, T2, R> asRxBiFunction(BiFunction<T1, T2, R> biFunction) {
        return t1 -> t2 -> invokeBiFunction(biFunction, t1, t2);
    }

    public static <T1, T2, R> R invokeBiFunction(BiFunction<T1, T2, R> function, T1 t1, T2 t2) {
        return new Invoker<R>().apply(() -> function.apply(t1, t2));
    }

    public static <T1, T2> RxBiPredicate<T1, T2> asRxBiPredicate(BiPredicate<T1, T2> biPredicate) {
        return t1 -> t2 -> invokeBiPredicate(biPredicate, t1, t2);
    }

    public static <T1, T2> boolean invokeBiPredicate(BiPredicate<T1, T2> function, T1 t1, T2 t2) {
        return new Invoker<Boolean>().apply(() -> function.test(t1, t2));
    }

    public static <T1, T2, T3, R> RxFunction3<T1, T2, T3, R> asRxFunction3(Function3<T1, T2, T3, R> function3) {
        return t1 -> t2 -> t3 -> invokeFunction3(function3, t1, t2, t3);
    }

    public static <T1, T2, T3, R> R invokeFunction3(Function3<T1, T2, T3, R> function, T1 t1, T2 t2, T3 t3) {
        return new Invoker<R>().apply(() -> function.apply(t1, t2, t3));
    }

    public static <T, R> RxCallable<R> toRxCallable(Function<T, R> function, T t) {
        return () -> invokeFunction(function, t);
    }

    public static <T> RxAction toRxAction(Consumer<T> consumer, T t) {
        return () -> invokeConsumer(consumer, t);
    }

    public static <T1, T2, R> RxFunction<T2, R> toRxFunction(BiFunction<T1, T2, R> biFunction, T1 t1) {
        return t2 -> invokeBiFunction(biFunction, t1, t2);
    }

    public static <T1, T2, T3, R> RxBiFunction<T2, T3, R> toRxBiFunction(Function3<T1, T2, T3, R> function3, T1 t1) {
        return t2 -> t3 -> invokeFunction3(function3, t1, t2, t3);
    }

    public static <T1, T2> RxConsumer<T2> toRxConsumer(BiConsumer<T1, T2> biConsumer, T1 t1) {
        return t2 -> invokeBiConsumer(biConsumer, t1, t2);
    }

    public static <T1, T2, T3> RxBiConsumer<T2, T3> toRxBiConsumer(Consumer3<T1, T2, T3> consumer3, T1 t1) {
        return t2 -> t3 -> consumer3.accept(t1, t2, t3);
    }

    public static <T1, T2> RxPredicate<T2> toRxPredicate(BiPredicate<T1, T2> biPredicate, T1 t1) {
        return t2 -> invokeBiPredicate(biPredicate, t1, t2);
    }

    public static <T1, T2, T3> RxBiPredicate<T2, T3> toRxBiPredicate(Predicate3<T1, T2, T3> predicate3, T1 t1) {
        return t2 -> t3 -> predicate3.test(t1, t2, t3);
    }

    public static <T> PartiallyAppliedParameter<T> with(T parameter) {
        return new PartiallyAppliedParameter<>(parameter);
    }

    public static <T1, T2> PartiallyAppliedSecondParameter<T1, T2> with(T1 parameterOne, T2 parameterTwo) {
        return new PartiallyAppliedSecondParameter<>(parameterOne, parameterTwo);
    }

    public static <T1, T2, T3> PartiallyAppliedThirdParameter<T1, T2, T3> with(
            T1 parameterOne, T2 parameterTwo, T3 parameterThree) {
        return new PartiallyAppliedThirdParameter<>(parameterOne, parameterTwo, parameterThree);
    }

    public static RxAction emptyRxAction() {
        return () -> {
        };
    }

    public static <T> RxConsumer<T> emptyRxConsumer() {
        return ignoredParameter -> {
        };
    }

    public static <T1, T2> RxBiConsumer<T1, T2> emptyRxBiConsumer() {
        return ignoredParameterOne -> ignoredParameterTwo -> {
        };
    }

    public static void invokeRunnable(Runnable function) {
        new Invoker<Void>().apply(() -> {
            function.run();
            return null;
        });
    }

    private static class Invoker<T> implements Function<Callable<T>, T> {

        @Override
        public T apply(Callable<T> callable) {
            try {
                return callable.call();
            } catch (Throwable throwable) {
                if (throwable instanceof RuntimeException) {
                    throw (RuntimeException) throwable;
                } else {
                    throw new RuntimeException(throwable);
                }
            }
        }
    }
}
