package com.functional.curry;

import io.reactivex.functions.Consumer;


/**
 * an interface that is implemented by all the Currying {@link Consumer} related implementers
 * <p>
 * Created by Ahmed Adel Ismail on 7/26/2017.
 */
public interface CurriedConsumer<ApplyParameter, ReturnedFunctionParameter>
        extends RxFunction<ApplyParameter, RxConsumer<ReturnedFunctionParameter>>
{

    /**
     * append the second parameter and return the next {@link Consumer}
     *
     * @param parameter the next parameter
     * @return another curried {@link Consumer}
     */
    RxConsumer<ReturnedFunctionParameter> apply(ApplyParameter parameter);

}