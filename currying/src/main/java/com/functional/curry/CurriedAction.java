package com.functional.curry;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * a curried {@link Consumer} or {@link Function} to an {@link Action}
 * <p>
 * Created by Ahmed Adel Ismail on 9/11/2017.
 */
class CurriedAction<ParameterOne> implements RxAction {
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
        if (consumer != null) {
            Invoker.invoke(consumer, parameterOne);
        }

        if (function != null) {
            Invoker.invoke(function, parameterOne);
        }
    }
}
