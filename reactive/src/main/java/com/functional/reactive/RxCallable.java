package com.functional.reactive;


import java.util.concurrent.Callable;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * an interface that is a {@link Callable} but it's {@link #call()} method does not declare an
 * {@link Exception} in its signature
 * <p>
 * Created by Ahmed Adel Ismail on 9/11/2017.
 */
public interface RxCallable<T> extends CallableStream<T>, LazyCallable<T> {

    default Either<T> tryToCall() {
        try {
            return Either.right(call());
        } catch (Throwable e) {
            return Either.left(e);
        }
    }

    @Override
    T call();

    default RxCallable<T> compose(Action before) {
        return () -> {
            Functions.invokeAction(before);
            return call();
        };

    }

    default <R> RxCallable<R> andThen(Function<T, R> after) throws NullPointerException {
        if (after == null) throw new NullPointerException();
        return () -> Functions.invokeFunction(after, Functions.invokeCallable(this));
    }

    default RxAction andThenRun(Consumer<T> after) throws NullPointerException {
        if (after == null) throw new NullPointerException();
        return () -> Functions.invokeConsumer(after, Functions.invokeCallable(this));
    }

}