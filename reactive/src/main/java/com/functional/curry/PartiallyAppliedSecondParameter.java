package com.functional.curry;


import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.BiPredicate;

public class PartiallyAppliedSecondParameter<T1, T2> {

    private final T1 t1;
    private final T2 t2;

    PartiallyAppliedSecondParameter(T1 t1, T2 t2) {
        this.t1 = t1;
        this.t2 = t2;
    }

    public void accept(BiConsumer<T1, T2> consumer) {
        Functions.invokeBiConsumer(consumer, t1, t2);
    }

    public <R> R apply(BiFunction<T1, T2, R> function) {
        return Functions.invokeBiFunction(function, t1, t2);
    }

    public Boolean test(BiPredicate<T1, T2> predicate) {
        return Functions.invokeBiPredicate(predicate, t1, t2);
    }

    public <U> PartiallyAppliedThirdParameter<T1, T2, U> and(U nextParameter) {
        return new PartiallyAppliedThirdParameter<>(t1, t2, nextParameter);
    }

    public <U> PartiallyAppliedThirdParameter<U, T1, T2> flip(U previousParameter) {
        return new PartiallyAppliedThirdParameter<>(previousParameter, t1, t2);
    }

    public <U> PartiallyAppliedThirdParameter<T1, U, T2> flipFirst(U middleParameter) {
        return new PartiallyAppliedThirdParameter<>(t1, middleParameter, t2);
    }

    public <R> RxCallable<R> toRxCallable(BiFunction<T1, T2, R> function) {
        return Functions.asRxBiFunction(function).toRxCallable(t1, t2);
    }

    public RxAction toRxAction(BiConsumer<T1, T2> consumer) {
        return Functions.asRxBiConsumer(consumer).toRxAction(t1, t2);
    }

}
