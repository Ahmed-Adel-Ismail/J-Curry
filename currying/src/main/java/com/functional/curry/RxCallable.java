package com.functional.curry;

import java.util.concurrent.Callable;

/**
 * an interface that is a {@link Callable} but it's {@link #call()} method does not declare an
 * {@link Exception} in its signature
 *
 * Created by Ahmed Adel Ismail on 9/11/2017.
 */
public interface RxCallable<V> extends Callable<V>
{
    @Override
    V call();
}