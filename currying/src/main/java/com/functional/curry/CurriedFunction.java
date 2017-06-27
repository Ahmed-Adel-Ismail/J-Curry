package com.functional.curry;

import io.reactivex.functions.Function;

/**
 * an interface that is implemented by all the Currying {@link Function} related implementers
 * <p>
 * Created by Ahmed Adel Ismail on 6/26/2017.
 */
public interface CurriedFunction<ApplyParameter, ReturnedFunctionParameter, ReturnedFunctionResult>
        extends RxFunction<ApplyParameter, RxFunction<ReturnedFunctionParameter, ReturnedFunctionResult>>
{

    /**
     * append the second parameter and return the next {@link Function}
     *
     * @param parameter the next parameter
     * @return another curried {@link Function}
     */
    RxFunction<ReturnedFunctionParameter, ReturnedFunctionResult> apply(ApplyParameter parameter);

}
