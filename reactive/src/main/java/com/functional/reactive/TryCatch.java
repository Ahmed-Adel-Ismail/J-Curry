package com.functional.reactive;


import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class TryCatch {

    private final Action action;

    private TryCatch(Action action) {
        this.action = action;
    }

    public static TryCatch with(@NonNull Action action) throws NullPointerException {
        if (action == null) throw new NullPointerException();
        return new TryCatch(action);
    }

    public RxAction toRxAction(@NonNull Consumer<Throwable> onError) {
        return () -> onError(onError);
    }

    public void onError(@NonNull Consumer<Throwable> onError) throws NullPointerException {
        if (onError == null) throw new NullPointerException();
        try {
            action.run();
        } catch (Throwable e) {
            Functions.invokeConsumer(onError, e);
        }
    }

}
