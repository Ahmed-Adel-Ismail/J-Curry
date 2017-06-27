package com.functional.curry;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiConsumer;

/**
 * a curried {@link BiConsumer}
 * <p>
 * Created by Ahmed Adel Ismail on 6/26/2017.
 */
class CurriedBiConsumer<ParameterOne, ParameterTwo>
        implements RxConsumer<ParameterTwo>
{
    private final BiConsumer<ParameterOne, ParameterTwo> biConsumer;
    private final ParameterOne parameterOne;

    CurriedBiConsumer(BiConsumer<ParameterOne, ParameterTwo> biConsumer,
                      ParameterOne parameterOne) {
        this.biConsumer = biConsumer;
        this.parameterOne = parameterOne;
    }

    @Override
    public void accept(@NonNull ParameterTwo parameterTwo) {
        try {
            biConsumer.accept(parameterOne, parameterTwo);
        }
        catch (Throwable e) {
            throw new RuntimeExceptionConverter().apply(e);
        }
    }
}
