package com.functional.curry;


import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiPredicate;
import io.reactivex.functions.Predicate;

/**
 * a CurriedFunction {@link BiPredicate}
 *
 * Created by Ahmed Adel Ismail on 6/26/2017.
 */
class CurriedBiPredicate<ParameterOne, ParameterTwo> implements Predicate<ParameterTwo>
{
    private final BiPredicate<ParameterOne, ParameterTwo> biPredicate;
    private final ParameterOne parameterOne;

    CurriedBiPredicate(BiPredicate<ParameterOne, ParameterTwo> biPredicate,
                       ParameterOne parameterOne) {
        this.biPredicate = biPredicate;
        this.parameterOne = parameterOne;
    }


    @Override
    public boolean test(@NonNull ParameterTwo parameterTwo) throws Exception {
        return biPredicate.test(parameterOne, parameterTwo);
    }
}
