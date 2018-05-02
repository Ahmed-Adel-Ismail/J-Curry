package com.functional.types;

import com.functional.curry.Curry;
import com.functional.curry.Invoker;

import java.util.concurrent.Callable;

import io.reactivex.functions.Function;

public class Try<T> implements Callable<T> {

    private final T value;
    private final Exception exception;

    private Try(T value, Exception exception) {
        this.value = value;
        this.exception = exception;
    }

    private Try(Callable<T> callable) throws NullPointerException {

        T tempValue = null;
        Exception tempException = null;

        try {
            tempValue = callable.call();
            if (tempValue == null) {
                throw nullPointerException();
            }
        } catch (Exception e) {
            tempException = e;
        }

        value = tempValue;
        exception = tempException;
    }

    private NullPointerException nullPointerException() {
        return new NullPointerException(
                "null values not accepted as a returned type from tha passed Callable to "
                        + getClass().getSimpleName());
    }

    /**
     * same as {@link #getOrCrash()}
     *
     * @return the value if available
     * @throws Exception the {@link Exception} if available
     */
    @Override
    public T call() throws Exception {
        return getOrCrash();
    }

    public T getOrCrash() throws Exception {
        if (exception == null) {
            return value;
        } else {
            throw exception;
        }
    }

    public T getOrElse(Function<Exception, T> failureExpression) {
        if (value != null) {
            return value;
        } else {
            return Invoker.invoke(failureExpression, exception);
        }
    }

    public <R> Try<R> map(Function<T, R> mapper) {
        if (value != null) {
            return Try.with(Curry.toCallable(mapper, value));
        } else {
            return new Try<>(null, exception);
        }
    }

    /**
     * create a {@link Try} instance
     *
     * @param expression the expression that may crash
     * @param <T>        the return type of the expression
     * @return a {@link Try} that holds the value, or the exception
     * @throws NullPointerException if the expression {@link Callable} returned {@code null}
     */
    public static <T> Try<T> with(Callable<T> expression) throws NullPointerException {
        return new Try<>(expression);
    }

    public <R> R flatMap(Function<Try<T>, R> converter) {
        return Invoker.invoke(converter, this);
    }

    public <R> R flatMap(Function<T, R> successMapper, Function<Exception, R> failureMapper) {
        if (value != null) {
            return nonNullMapper(successMapper, value);
        } else {
            return nonNullMapper(failureMapper, exception);
        }
    }

    private <P, V> V nonNullMapper(Function<P, V> mapper, P oldValue) {
        V result = Invoker.invoke(mapper, oldValue);
        if (result == null) throw new NullPointerException("null values not accepted");
        return result;
    }
}
