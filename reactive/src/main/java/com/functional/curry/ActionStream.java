package com.functional.curry;


import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;

public interface ActionStream extends Action {

    default Completable toCompletable() {
        return Completable.defer(() -> Completable.fromAction(this));
    }

    default <T> Maybe<T> toMaybe(@NonNull T nextItem) throws NullPointerException {
        if (nextItem == null) throw new NullPointerException();
        return Maybe.defer(() -> {
            try {
                run();
                return Maybe.just(nextItem);
            } catch (Throwable e) {
                return Maybe.error(e);
            }
        });
    }

    default <T> Single<T> toSingle(@NonNull T nextItem) throws NullPointerException {
        if (nextItem == null) throw new NullPointerException();
        return Single.defer(() -> {
            try {
                run();
                return Single.just(nextItem);
            } catch (Throwable e) {
                return Single.error(e);
            }
        });
    }

    default <T> Observable<T> toObservable(@NonNull T nextItem) throws NullPointerException {
        if (nextItem == null) throw new NullPointerException();
        return Observable.defer(() -> {
            try {
                run();
                return Observable.just(nextItem);
            } catch (Throwable e) {
                return Observable.error(e);
            }
        });
    }

    default <T> Flowable<T> toFlowable(@NonNull T nextItem) throws NullPointerException {
        if (nextItem == null) throw new NullPointerException();
        return Flowable.defer(() -> {
            try {
                run();
                return Flowable.just(nextItem);
            } catch (Throwable e) {
                return Flowable.error(e);
            }
        });
    }

}
