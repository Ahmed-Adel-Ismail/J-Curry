package com.functional.curry;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function3;
import io.reactivex.functions.Predicate;


/**
 * a curried {@link Function3} with a {@code boolean} return type, curried as a
 * {@link Predicate}
 * <p>
 * Created by Ahmed Adel Ismail on 7/26/2017.
 */
public class CurriedPredicate3<T1, T2, T3> implements RxPredicate<T3> {
    private final boolean executable;
    private final Function3<T1, T2, T3, Boolean> function3;
    private final T1 parameterOne;
    private final T2 parameterTwo;


    CurriedPredicate3(Function3<T1, T2, T3, Boolean> function3, T1 parameterOne) {
        this.function3 = function3;
        this.parameterOne = parameterOne;
        this.parameterTwo = null;
        this.executable = false;
    }

    private CurriedPredicate3(Function3<T1, T2, T3, Boolean> function3, T1 parameterOne, T2 parameterTwo) {
        this.function3 = function3;
        this.parameterOne = parameterOne;
        this.parameterTwo = parameterTwo;
        this.executable = true;
    }

    @Override
    public boolean test(@NonNull T3 parameterThree) {
        if (executable) {
            return Invoker.invoke(function3, parameterOne, parameterTwo, parameterThree);
        } else {
            throw new IllegalArgumentException("second parameter not set");
        }
    }


    /**
     * curryParameterTwo the current function
     *
     * @return a curried version of this function
     */
    CurriedPredicate<T2, T3> curry() {
        return new CurriedPredicate<T2, T3>() {
            @Override
            public RxPredicate<T3> apply(@NonNull T2 parameterTwo) {
                return new CurriedPredicate3<>(function3, parameterOne, parameterTwo);
            }
        };
    }
}