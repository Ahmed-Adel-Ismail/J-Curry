package com.functional.curry;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function3;

/**
 * a curried {@link Function3}
 * <p>
 * Created by Ahmed Adel Ismail on 6/26/2017.
 */
class CurriedFunc3<T1, T2, T3, R> implements RxFunction<T3, R>
{
    private final boolean executable;
    private final Function3<T1, T2, T3, R> function3;
    private final T1 parameterOne;
    private final T2 parameterTwo;


    CurriedFunc3(Function3<T1, T2, T3, R> function3, T1 parameterOne) {
        this.function3 = function3;
        this.parameterOne = parameterOne;
        this.parameterTwo = null;
        this.executable = false;
    }

    private CurriedFunc3(Function3<T1, T2, T3, R> function3, T1 parameterOne, T2 parameterTwo) {
        this.function3 = function3;
        this.parameterOne = parameterOne;
        this.parameterTwo = parameterTwo;
        this.executable = true;
    }

    @Override
    public R apply(@NonNull T3 parameterThree) {
        try {
            return doApply(parameterThree);
        }
        catch (Throwable e) {
            throw new RuntimeExceptionConverter().apply(e);
        }
    }

    private R doApply(@NonNull T3 parameterThree) throws Exception {
        if (executable) {
            return function3.apply(parameterOne, parameterTwo, parameterThree);
        }
        else {
            throw new IllegalArgumentException("second parameter not set");
        }
    }


    /**
     * curry the current function
     *
     * @return a curried version of this function
     */
    CurriedFunction<T2, T3, R> curry() {
        return new CurriedFunction<T2, T3, R>()
        {
            @Override
            public RxFunction<T3, R> apply(@NonNull T2 parameterTwo) {
                return new CurriedFunc3<>(function3, parameterOne, parameterTwo);
            }
        };
    }
}
