package com.functional.curry;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

/**
 * a CurriedFunction {@link BiFunction}
 * <p>
 * Created by Ahmed Adel Ismail on 6/26/2017.
 */
class CurriedBiFunction<ParameterOne, ParameterTwo, Return>
        implements Function<ParameterTwo, Return>
{
    private final BiFunction<ParameterOne, ParameterTwo, Return> biFunction;
    private final ParameterOne parameterOne;

    CurriedBiFunction(BiFunction<ParameterOne, ParameterTwo, Return> biFunction,
                             ParameterOne parameterOne) {
        this.biFunction = biFunction;
        this.parameterOne = parameterOne;
    }

    @Override
    public Return apply(@NonNull ParameterTwo parameterTwo) throws Exception {
        return biFunction.apply(parameterOne, parameterTwo);
    }
}
