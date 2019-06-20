package com.functional.reactive;


import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * a type that holds one value, either a success value (right), or a failure/exception value (left)
 *
 * @param <T> the type of the expected value in case of success
 */
public abstract class Either<T> implements CallableStream<T>, LazyCallable<T> {

    public static <T> Either<T> right(T item) {
        return new Right<>(item);
    }

    public static <T> Either<T> left(Throwable exception) {
        return new Left<>(exception);
    }

    public TryCatch fold(@NonNull Consumer<T> onSuccess) throws NullPointerException {
        if (onSuccess == null) throw new NullPointerException();
        return TryCatch.with(() -> onSuccess.accept(call()));
    }

    public <R> TryCatchReturn<R> map(@NonNull Function<T,R> onSuccess) throws NullPointerException {
        if (onSuccess == null) throw new NullPointerException();
        return new TryCatchReturn<>(() -> onSuccess.apply(call()));
    }

    static class Right<T> extends Either<T> {

        private final T item;

        Right(T item) {
            this.item = item;
        }

        @Override
        public T call() {
            return item;
        }
    }

    static class Left<T> extends Either<T> {

        private final Throwable error;

        Left(Throwable error) {
            this.error = error;
        }

        @Override
        public T call() throws Exception {
            if (error instanceof Exception) {
                throw (Exception) error;
            } else {
                throw new Exception(error);
            }
        }
    }

}
