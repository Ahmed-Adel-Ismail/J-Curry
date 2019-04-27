package com.functional.curry;


import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * same as {@link Consumer} but it's {@link #accept(Object)} signature does not declare
 * {@code throws Exception}
 * <p>
 * Created by Ahmed Adel Ismail on 6/27/2017.
 */
public interface RxConsumer<T> extends Consumer<T> {

    default RxAction toRxAction(T parameter) {
        return () -> accept(parameter);
    }

    void accept(@NonNull T t);

    default RxConsumer<T> compose(Consumer<? super T> before) throws NullPointerException {
        if (before == null) throw new NullPointerException();
        return (T t) -> {
            Functions.invokeConsumer(before, t);
            accept(t);
        };
    }

    default RxConsumer<T> andThen(Consumer<? super T> after) throws NullPointerException {
        if (after == null) throw new NullPointerException();
        return (T t) -> {
            accept(t);
            Functions.invokeConsumer(after, t);
        };
    }
}


