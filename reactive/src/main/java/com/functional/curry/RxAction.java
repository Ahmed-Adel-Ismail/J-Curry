package com.functional.curry;

import com.functional.types.Objects;
import com.functional.types.TryCatch;

import io.reactivex.functions.Action;

/**
 * an interface that is an {@link Action} but it's {@link #run()} does not declare an
 * {@link Exception} in it's signature
 * <p>
 * Created by Ahmed Adel Ismail on 9/11/2017.
 */
public interface RxAction extends ActionStream, Runnable {

    default TryCatch tryToRun() {
        return TryCatch.with(this);
    }

    default RxAction andThen(Action after) throws NullPointerException {
        Objects.requireNonNull(after);
        return () -> {
            run();
            Functions.invokeAction(after);
        };

    }

    default RxAction compose(Action before) throws NullPointerException {
        Objects.requireNonNull(before);
        return () -> {
            Functions.invokeAction(before);
            run();
        };

    }

    void run();

}