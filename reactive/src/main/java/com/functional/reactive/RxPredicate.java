package com.functional.reactive;


import io.reactivex.Maybe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Predicate;

/**
 * same as {@link Predicate} but it's {@link #test(Object)} signature does not declare
 * {@code throws Exception}
 * <p>
 * Created by Ahmed Adel Ismail on 6/27/2017.
 */
public interface RxPredicate<T> extends Predicate<T> {

    default RxPredicate<T> and(Predicate<? super T> other) throws NullPointerException {
        if (other == null) throw new NullPointerException();
        return (t) -> test(t) && Functions.invokePredicate(other, t);
    }

    boolean test(@NonNull T t);

    default RxPredicate<T> negate() {
        return (t) -> !test(t);
    }

    default RxPredicate<T> or(Predicate<? super T> other) throws NullPointerException {
        if (other == null) throw new NullPointerException();
        return (t) -> test(t) || Functions.invokePredicate(other, t);
    }

    default <R> Maybe<R> toMaybe(T parameter, R valueIfTrue) {
        return toRxCallable(parameter)
                .toMaybe()
                .filter(Boolean::booleanValue)
                .map(ignoredBoolean -> valueIfTrue);
    }

    default RxCallable<Boolean> toRxCallable(T parameter) {
        return () -> test(parameter);
    }

    default Maybe<T> toMaybe(T parameter) {
        return toRxCallable(parameter)
                .toMaybe()
                .filter(Boolean::booleanValue)
                .map(ignoredBoolean -> parameter);
    }

}
