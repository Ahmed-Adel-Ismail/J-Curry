package com.functional.curry;

import java.util.concurrent.Callable;

import io.reactivex.functions.Function;

/**
 * a curried {@link Function} to an {@link Callable} to be executed
 * <p>
 * Created by Ahmed Adel Ismail on 9/11/2017.
 */
class CurriedCallable<ParameterOne, Return> implements RxCallable<Return> {
    private final Function<ParameterOne, Return> function;
    private final ParameterOne parameterOne;

    CurriedCallable(Function<ParameterOne, Return> function, ParameterOne parameterOne) {
        this.function = function;
        this.parameterOne = parameterOne;
    }

    @Override
    public Return call() {
        return Invoker.invoke(function, parameterOne);
    }
}