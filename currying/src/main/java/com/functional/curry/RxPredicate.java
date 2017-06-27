package com.functional.curry;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Predicate;

/**
 * same as {@link Predicate} but it's {@link #test(Object)} signature does not declare
 * {@code throws Exception}
 * <p>
 * Created by Ahmed Adel Ismail on 6/27/2017.
 */
public interface RxPredicate<T> extends Predicate<T>
{
    boolean test(@NonNull T t);
}
