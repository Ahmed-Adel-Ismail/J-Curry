package com.functional.reactive;


import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

public class PartiallyAppliedParameter<T> {

    private final T t;

    PartiallyAppliedParameter(T t) {
        this.t = t;
    }

    public void accept(Consumer<T> consumer) {
        Functions.invokeConsumer(consumer, t);
    }

    public <R> R apply(Function<T, R> function) {
        return Functions.invokeFunction(function, t);
    }

    public boolean test(Predicate<T> predicate) {
        return Functions.invokePredicate(predicate, t);
    }

    public <U> PartiallyAppliedSecondParameter<T, U> and(U nextParameter) {
        return new PartiallyAppliedSecondParameter<>(t, nextParameter);
    }

    public <U> PartiallyAppliedSecondParameter<U, T> flip(U previousParameter) {
        return new PartiallyAppliedSecondParameter<>(previousParameter, t);
    }

    public <R> RxCallable<R> toRxCallable(Function<T, R> function) {
        return Functions.asRxFunction(function).toRxCallable(t);
    }

    public RxAction toRxAction(Consumer<T> consumer) {
        return Functions.asRxConsumer(consumer).toRxAction(t);
    }
}
