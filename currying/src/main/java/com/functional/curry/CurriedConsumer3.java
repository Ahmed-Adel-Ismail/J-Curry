package com.functional.curry;


import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function3;


/**
 * a curried {@link Function3} with a {@code void} return type, curried as a
 * {@link Consumer}
 * <p>
 * Created by Ahmed Adel Ismail on 7/26/2017.
 */
public class CurriedConsumer3<T1, T2, T3> implements RxConsumer<T3>
{
    private final boolean executable;
    private final Function3<T1, T2, T3, Void> function3;
    private final T1 parameterOne;
    private final T2 parameterTwo;


    CurriedConsumer3(Function3<T1, T2, T3, Void> function3, T1 parameterOne) {
        this.function3 = function3;
        this.parameterOne = parameterOne;
        this.parameterTwo = null;
        this.executable = false;
    }

    private CurriedConsumer3(Function3<T1, T2, T3, Void> function3, T1 parameterOne, T2 parameterTwo) {
        this.function3 = function3;
        this.parameterOne = parameterOne;
        this.parameterTwo = parameterTwo;
        this.executable = true;
    }

    @Override
    public void accept(@NonNull T3 parameterThree) {
        try {
            doApply(parameterThree);
        }
        catch (Throwable e) {
            throw new RuntimeExceptionConverter().apply(e);
        }
    }

    private Void doApply(@NonNull T3 parameterThree) throws Exception {
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
    CurriedConsumer<T2, T3> curry() {
        return new CurriedConsumer<T2, T3>()
        {
            @Override
            public RxConsumer<T3> apply(@NonNull T2 parameterTwo) {
                return new CurriedConsumer3<>(function3, parameterOne, parameterTwo);
            }
        };
    }

}
