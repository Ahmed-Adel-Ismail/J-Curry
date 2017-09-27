package com.functional.curry;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * a curried {@link Consumer} or {@link Function} to an {@link Action}
 *
 * Created by Ahmed Adel Ismail on 9/11/2017.
 */
class CurriedAction<ParameterOne> implements RxAction
{
    private final Function<ParameterOne, ?> function;
    private final Consumer<ParameterOne> consumer;
    private final ParameterOne parameterOne;

    CurriedAction(Consumer<ParameterOne> consumer, ParameterOne parameterOne) {
        this.function = null;
        this.consumer = consumer;
        this.parameterOne = parameterOne;
    }

    CurriedAction(Function<ParameterOne, ?> function, ParameterOne parameterOne) {
        this.function = function;
        this.consumer = null;
        this.parameterOne = parameterOne;
    }

    @Override
    public void run() {
        try {
            if (consumer != null) {
                consumer.accept(parameterOne);
            }

            if (function != null) {
                function.apply(parameterOne);
            }

        } catch (Throwable e) {
            throw new RuntimeExceptionConverter().apply(e);
        }
    }
}
