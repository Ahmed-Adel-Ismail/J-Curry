package com.functional.reactive;

import java.util.concurrent.Callable;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

public class TryCatchReturn<T> {

    private final Callable<T> action;

    public TryCatchReturn(Callable<T> action) {
        if (action == null) throw new NullPointerException();
        this.action = action;
    }

    public RxCallable<T> toRxCallable(@NonNull Function<Throwable, T> onError) {
        return () -> onErrorReturn(onError);
    }

    public T onErrorReturn(@NonNull Function<Throwable, T> onError) throws NullPointerException {
        if (onError == null) throw new NullPointerException();
        try {
            return action.call();
        } catch (Throwable e) {
            return Functions.invokeFunction(onError, e);
        }
    }
}
