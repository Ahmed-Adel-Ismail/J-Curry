package com.functional.types;

import com.functional.curry.Invoker;
import com.functional.curry.RxBiConsumer;
import com.functional.curry.RxConsumer;
import com.functional.curry.RxFunction;

import java.util.concurrent.Callable;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * a data structure that will hold one of two values, the left value indicates the error value, and
 * the right value indicates the success value
 *
 * @param <L> the left value type - usually this is the error type
 * @param <R> the right value type, usually this is the success type
 */
public class Either<L extends Exception, R> implements Callable<R> {

    private final L left;
    private final R right;

    private Either(L left, R right) {
        this.left = left;
        this.right = right;
    }

    /**
     * initialize a {@link Either} with an error value
     *
     * @param leftValue the error value
     * @param <L>       a type that is a {@link Exception}
     * @param <R>       a type that is an Object
     * @return an {@link Either} that holds an error
     * @throws NullPointerException if the passed value is {@code null}
     */
    public static <L extends Exception, R> Either<L, R> withLeft(L leftValue) throws NullPointerException {
        if (leftValue == null) throw new NullPointerException("null values not accepted");
        return new Either<>(leftValue, null);
    }

    /**
     * initialize a {@link Either} with a success value
     *
     * @param rightValue the success value
     * @param <L>        a type that is a {@link Exception}
     * @param <R>        a type of the success value
     * @return an {@link Either} that holds a success value
     * @throws NullPointerException if the passed value is {@code null}
     */
    public static <L extends Exception, R> Either<L, R> withRight(R rightValue) throws NullPointerException {
        if (rightValue == null) throw new NullPointerException("null values not accepted");
        return new Either<>(null, rightValue);
    }

    /**
     * check weather this is an {@link Either} with error value
     *
     * @return {@code true} if it holds an error
     */
    public boolean isLeft() {
        return left != null;
    }

    /**
     * same as {@link #getRightOrCrash()}
     *
     * @return the value on the right if available
     * @throws L the value on the left if available
     */
    @Override
    public R call() throws L {
        return getRightOrCrash();
    }

    /**
     * get the right (success) value, or throw the left value if no success value was available
     *
     * @return the right (success) value if available
     * @throws L the left (error) value if available
     */
    public R getRightOrCrash() throws L {
        if (left != null) {
            throw left;
        } else {
            return right;
        }
    }

    /**
     * a fold operation
     *
     * @return a {@link RxBiConsumer} that takes two {@link Consumer} instances, the first
     * {@link Consumer} will be executed if the value stored is the left (error) value, and the second
     * {@link Consumer} will be executed if the value stored is the right (success) value ... in each
     * case the value will be passed to it's corresponding consumer
     */
    public RxBiConsumer<Consumer<L>, Consumer<R>> fold() {
        return new RxBiConsumer<Consumer<L>, Consumer<R>>() {

            @Override
            public RxConsumer<Consumer<R>> apply(final Consumer<L> leftOption) {
                return new RxConsumer<Consumer<R>>() {
                    @Override
                    public void accept(Consumer<R> rightOption) {
                        if (left != null) {
                            Invoker.invoke(leftOption, left);
                        } else {
                            Invoker.invoke(rightOption, right);
                        }
                    }
                };
            }
        };

    }

    /**
     * convert this {@link Either} into another Object
     *
     * @param flatMapper the {@link Function} that takes this {@link Either} and returns another Object
     * @param <V>        the type of the expected Object
     * @return the new Object
     */
    public <V> V flatMap(Function<Either<L, R>, V> flatMapper) {
        return Invoker.invoke(flatMapper, this);
    }

    /**
     * check weather this is an {@link Either} with success value
     *
     * @return {@code true} if it holds a success value
     */
    public boolean isRight() {
        return right != null;
    }

    /**
     * convert this {@link Either} into another Object
     *
     * @param leftFlatMapper  the {@link Function} that takes the left value if availabel
     *                        and returns the new Object
     * @param rightFlatMapper the {@link Function} that takes the right value if availabel
     *                        and returns the new Object
     * @param <V>             the expected type of the new Object
     * @return the new Object
     */
    public <V> V flatMap(Function<L, V> leftFlatMapper, Function<R, V> rightFlatMapper) {
        if (left != null) {
            return nonNullMapper(leftFlatMapper, left);
        } else {
            return nonNullMapper(rightFlatMapper, right);
        }
    }

    private <T, V> V nonNullMapper(Function<T, V> mapper, T oldValue) {
        V result = Invoker.invoke(mapper, oldValue);
        if (result == null) throw new NullPointerException("null values not accepted");
        return result;
    }

    /**
     * convert the left value into another value if available, or the operation will be skipped
     *
     * @param mapper a {@link Function} the function that will convert the value if available
     * @param <V> the expected type of the new value
     * @return a new {@link Either} that holds the new values
     */
    public <V extends Exception> Either<V, R> mapLeft(Function<L, V> mapper) {
        if (left == null) {
            return new Either<>(null, right);
        } else {
            return new Either<>(nonNullMapper(mapper, left), null);
        }
    }

    /**
     * convert the right value into another value if available, or the operation will be skipped
     *
     * @param mapper a {@link Function} the function that will convert the value if available
     * @param <V> the expected type of the new value
     * @return a new {@link Either} that holds the new values
     */
    public <V> Either<L, V> mapRight(Function<R, V> mapper) {
        if (right == null) {
            return new Either<>(left, null);
        } else {
            return new Either<>(null, nonNullMapper(mapper, right));
        }
    }

}
