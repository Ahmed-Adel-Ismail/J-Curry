package com.functional.types;

import com.functional.curry.Curry;
import com.functional.curry.Invoker;

import java.util.concurrent.Callable;

import io.reactivex.functions.Function;

/**
 * a data type that takes a <b>Callable</b>, and will hold the value of it's
 * execution or it's exception ... so instead of using try/catch blocks, you can make a your
 * methods return the Try Object, and let the control for who called the method ...
 * the main point of <b>Try</b> is to handle exceptions in a Functional fashion,
 * without try/catch blocks, so we can invoke multiple operations on the values and if it crashed,
 * the operations are ignored, and we can notice weather there is a success or failure at the end
 *
 * @param <T> the type of the value
 */
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

    /**
     * get the value if available
     *
     * @return the value if available
     * @throws Exception the {@link Exception} that was thrown if the value is not available
     */
    public T getOrCrash() throws Exception {
        if (exception == null) {
            return value;
        } else {
            throw exception;
        }
    }

    /**
     * get the value if available, or the result of the passed {@link Function} if the value was
     * not achieved successfully from the passed {@link Callable}
     *
     * @param failureExpression the fallback {@link Function} that will be executed if the value was
     *                          not achieved successfully from the passed {@link Callable} to
     *                          {@link #with(Callable)}
     * @return the original value, or the failureExpression {@link Function} returned value
     */
    public T getOrElse(Function<Exception, T> failureExpression) {
        if (value != null) {
            return value;
        } else {
            return Invoker.invoke(failureExpression, exception);
        }
    }

    /**
     * convert the value in this {@link Try} into another value if available, else the operation
     * will be skipped
     *
     * @param mapper the {@link Function} that will convert the value if available
     * @param <R>    the type of the expected new value
     * @return a {@link Try} holding the new value, or holding a {@link Exception} if there
     * was no value from the beginning
     */
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

    /**
     * convert this {@link Try} instance into another Object
     *
     * @param flatMapper the mapper {@link Function} that takes a {@link Try} and returns the new Object
     * @param <R>        the type of the new Object
     * @return a new Object from the {@link Function} passed
     */
    public <R> R flatMap(Function<Try<T>, R> flatMapper) {
        return Invoker.invoke(flatMapper, this);
    }

    /**
     * convert this {@link Try} instance into another Object
     *
     * @param successMapper the {@link Function} that will be invoked if this {@link Try} holds a
     *                      success value
     * @param failureMapper the {@link Function} that will be invoked if this {@link Try} holds a
     *                      failure {@link Exception}
     * @param <R>           the type of the new Object
     * @return a new Object from the {@link Function} passed
     */
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
