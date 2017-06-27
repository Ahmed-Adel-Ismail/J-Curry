package com.functional.curry;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * same as {@link Consumer} but it's {@link #accept(Object)} signature does not declare
 * {@code throws Exception}
 * <p>
 * Created by Ahmed Adel Ismail on 6/27/2017.
 */
public interface RxConsumer<T> extends Consumer<T>
{
    void accept(@NonNull T t);
}
