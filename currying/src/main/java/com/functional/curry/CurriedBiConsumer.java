package com.functional.curry;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;

/**
 * a curried {@link BiConsumer}
 * <p>
 * Created by Ahmed Adel Ismail on 6/26/2017.
 */
class CurriedBiConsumer<ParameterOne, ParameterTwo>
        implements Consumer<ParameterTwo>
{
    private final BiConsumer<ParameterOne, ParameterTwo> biConsumer;
    private final ParameterOne parameterOne;

    CurriedBiConsumer(BiConsumer<ParameterOne, ParameterTwo> biConsumer,
                      ParameterOne parameterOne) {
        this.biConsumer = biConsumer;
        this.parameterOne = parameterOne;
    }

    @Override
    public void accept(@NonNull ParameterTwo parameterTwo) throws Exception {
        biConsumer.accept(parameterOne, parameterTwo);
    }
}
