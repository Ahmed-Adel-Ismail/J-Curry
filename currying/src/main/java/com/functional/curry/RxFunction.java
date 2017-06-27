package com.functional.curry;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * same as {@link Function} but it's {@link #apply(Object)} signature  does not declare
 * {@code throws Exception}
 * <p>
 * Created by Ahmed Adel Ismail on 6/27/2017.
 */
public interface RxFunction<T, R> extends Function<T, R>
{
    R apply(@NonNull T t);
}
