package com.functional.reactive;


import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;

public interface CallableStream<T> extends Callable<T>, Monad<T> {

    default Maybe<T> toMaybe() {
        return Maybe.defer(() -> {
            try {
                T value = call();
                if (value == null) {
                    return Maybe.empty();
                } else {
                    return Maybe.just(value);
                }
            } catch (Throwable e) {
                return Maybe.error(e);
            }
        });
    }

    default Single<T> toSingle(@NonNull T defaultIfNull) throws NullPointerException {
        if (defaultIfNull == null) throw new NullPointerException();
        return Single.defer(() -> {
            try {
                T value = call();
                if (value == null) {
                    return Single.just(defaultIfNull);
                } else {
                    return Single.just(value);
                }
            } catch (Throwable e) {
                return Single.error(e);
            }
        });
    }

    default Observable<T> toObservable(@NonNull T defaultIfNull) throws NullPointerException {
        if (defaultIfNull == null) throw new NullPointerException();
        return Observable.defer(() -> {
            try {
                T value = call();
                if (value == null) {
                    return Observable.just(defaultIfNull);
                } else {
                    return Observable.just(value);
                }
            } catch (Throwable e) {
                return Observable.error(e);
            }
        });
    }

    default Flowable<T> toFlowable(@NonNull T defaultIfNull) throws NullPointerException {
        if (defaultIfNull == null) throw new NullPointerException();
        return Flowable.defer(() -> {
            try {
                T value = call();
                if (value == null) {
                    return Flowable.just(defaultIfNull);
                } else {
                    return Flowable.just(value);
                }
            } catch (Throwable e) {
                return Flowable.error(e);
            }
        });
    }

    default Single<T> toSingleOrError() throws NullPointerException {
        return Single.defer(() -> {
            try {
                T value = call();
                if (value == null) {
                    return Single.error(new NullPointerException("call() method returned a null value"));
                } else {
                    return Single.just(value);
                }
            } catch (Throwable e) {
                return Single.error(e);
            }
        });
    }

    default Observable<T> toObservableOrError() {
        return Observable.defer(() -> {
            try {
                T value = call();
                if (value == null) {
                    return Observable.error(new NullPointerException("call() method returned a null value"));
                } else {
                    return Observable.just(value);
                }
            } catch (Throwable e) {
                return Observable.error(e);
            }
        });
    }

    default Flowable<T> toFlowableOrError() {
        return Flowable.defer(() -> {
            try {
                T value = call();
                if (value == null) {
                    return Flowable.error(new NullPointerException("call() method returned a null value"));
                } else {
                    return Flowable.just(value);
                }
            } catch (Throwable e) {
                return Flowable.error(e);
            }
        });
    }

    default Completable toCompletableOrError() {
        return Completable.defer(() -> {
            try {
                T value = call();
                if (value == null) {
                    return Completable.error(new NullPointerException("call() method returned a null value"));
                } else {
                    return Completable.complete();
                }
            } catch (Throwable e) {
                return Completable.error(e);
            }
        });
    }
}
