package com.functional.curry;

import io.reactivex.functions.Predicate;


/**
 * an interface that is implemented by all the Currying {@link Predicate} related implementers
 * <p>
 * Created by Ahmed Adel Ismail on 7/26/2017.
 */
public interface CurriedPredicate<ApplyParameter, ReturnedFunctionParameter>
        extends RxFunction<ApplyParameter, RxPredicate<ReturnedFunctionParameter>>
{

    /**
     * append the second parameter and return the next {@link Predicate}
     *
     * @param parameter the next parameter
     * @return another curried {@link Predicate}
     */
    RxPredicate<ReturnedFunctionParameter> apply(ApplyParameter parameter);

}