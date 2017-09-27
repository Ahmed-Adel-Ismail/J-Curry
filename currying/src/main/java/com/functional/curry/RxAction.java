package com.functional.curry;

import io.reactivex.functions.Action;

/**
 * an interface that is an {@link Action} but it's {@link #run()} does not declare an
 * {@link Exception} in it's signature
 *
 * Created by Ahmed Adel Ismail on 9/11/2017.
 */
public interface RxAction extends Action
{
    void run();
}