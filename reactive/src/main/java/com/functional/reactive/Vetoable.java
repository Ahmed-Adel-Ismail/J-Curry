package com.functional.reactive;


import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiPredicate;

public class Vetoable<T> implements RxCallable<T> {

    private final BiPredicate<T, T> isChangeAccepted;
    @NonNull
    private T value;

    public Vetoable(@NonNull T value, BiPredicate<T, T> isChangeAccepted) {
        if (value == null) throw new NullPointerException();
        this.value = value;
        this.isChangeAccepted = isChangeAccepted;
    }


    public void update(@NonNull T value) {
        if (value == null) throw new NullPointerException();
        if (Functions.invokeBiPredicate(isChangeAccepted, this.value, value)) {
            this.value = value;
        }
    }

    @Override
    public T call() {
        return this.value;
    }


}
