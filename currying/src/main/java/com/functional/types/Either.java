package com.functional.types;

import com.functional.curry.Invoker;
import com.functional.curry.RxBiConsumer;
import com.functional.curry.RxConsumer;
import com.functional.curry.RxFunction;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * a data structure that will hold one of two values, the left value indicates the error value, and
 * the right value indicates the success value
 *
 * @param <L> the left value type - usually this is the error type
 * @param <R> the right value type, usually this is the success type
 */
public class Either<L extends Exception, R> {

    private final L left;
    private final R right;

    private Either(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public static <L extends Exception, R> Either<L, R> withLeft(L leftValue) throws NullPointerException {
        if (leftValue == null) throw new NullPointerException("null values not accepted");
        return new Either<>(leftValue, null);
    }

    public static <L extends Exception, R> Either<L, R> withRight(R rightValue) throws NullPointerException {
        if (rightValue == null) throw new NullPointerException("null values not accepted");
        return new Either<>(null, rightValue);
    }

    public boolean isLeft() {
        return left != null;
    }

    public boolean isRight() {
        return right != null;
    }

    public R getRightOrCrash() throws L {
        if (left != null) {
            throw left;
        } else {
            return right;
        }
    }

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

    public <V> RxFunction<Function<R, V>, V> flatMap(final Function<L, V> leftFlatMapper) {
        return new RxFunction<Function<R, V>, V>() {
            @Override
            public V apply(Function<R, V> rightFlatMapper) {
                if (left != null) {
                    return nonNullMapper(leftFlatMapper, left);
                } else {
                    return nonNullMapper(rightFlatMapper, right);
                }
            }
        };
    }

    private <T, V> V nonNullMapper(Function<T, V> mapper, T oldValue) {
        V result = Invoker.invoke(mapper, oldValue);
        if (result == null) throw new NullPointerException("null values not accepted");
        return result;
    }

    public <V extends Exception> Either<V, R> mapLeft(RxFunction<L, V> mapper) {
        if (left == null) {
            return new Either<>(null, right);
        } else {
            return new Either<>(nonNullMapper(mapper, left), null);
        }
    }

    public <V> Either<L, V> mapRight(RxFunction<R, V> mapper) {
        if (right == null) {
            return new Either<>(left, null);
        } else {
            return new Either<>(null, nonNullMapper(mapper, right));
        }
    }


}
