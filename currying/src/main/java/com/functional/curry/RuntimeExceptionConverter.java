package com.functional.curry;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * convert any Exception into a {@link RuntimeException}
 * <p>
 * Created by Ahmed Adel Ismail on 6/27/2017.
 */
class RuntimeExceptionConverter implements Function<Throwable, RuntimeException>
{
    @Override
    public RuntimeException apply(@NonNull Throwable throwable) {
        if (throwable instanceof RuntimeException) {
            return (RuntimeException) throwable;
        }
        else {
            return new RuntimeException(throwable);
        }
    }
}
