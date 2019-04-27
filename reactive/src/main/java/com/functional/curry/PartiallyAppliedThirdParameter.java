package com.functional.curry;

import io.reactivex.functions.Function3;

public class PartiallyAppliedThirdParameter<T1, T2, T3> {

    private final T1 t1;
    private final T2 t2;
    private final T3 t3;

    PartiallyAppliedThirdParameter(T1 t1, T2 t2, T3 t3) {
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
    }

    public void accept(Consumer3<T1, T2, T3> consumer3) {
        consumer3.accept(t1, t2, t3);
    }

    public <R> R apply(Function3<T1, T2, T3, R> function) {
        return Functions.invokeFunction3(function, t1, t2, t3);
    }

    public boolean test(Predicate3<T1, T2, T3> predicate) {
        return predicate.test(t1, t2, t3);
    }

    public <R> RxCallable<R> toRxCallable(Function3<T1, T2, T3, R> function) {
        return Functions.asRxFunction3(function).toRxCallable(t1, t2, t3);
    }

    public RxAction toRxAction(Consumer3<T1, T2, T3> consumer) {
        return () -> consumer.accept(t1, t2, t3);
    }
}
