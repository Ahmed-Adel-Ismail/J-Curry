package com.functional.curry;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiPredicate;


/**
 * a CurriedFunction {@link BiPredicate}
 * <p>
 * Created by Ahmed Adel Ismail on 6/26/2017.
 */
class CurriedBiPredicate<ParameterOne, ParameterTwo> implements RxPredicate<ParameterTwo>
{
    private final BiPredicate<ParameterOne, ParameterTwo> biPredicate;
    private final ParameterOne parameterOne;

    CurriedBiPredicate(BiPredicate<ParameterOne, ParameterTwo> biPredicate,
                       ParameterOne parameterOne) {
        this.biPredicate = biPredicate;
        this.parameterOne = parameterOne;
    }


    @Override
    public boolean test(@NonNull ParameterTwo parameterTwo) {
        try {
            return biPredicate.test(parameterOne, parameterTwo);
        }
        catch (Throwable e) {
            throw new RuntimeExceptionConverter().apply(e);
        }
    }
}
