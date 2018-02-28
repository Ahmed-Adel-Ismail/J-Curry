package com.functional.curry;


import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.BiPredicate;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.functions.Predicate;

/**
 * a class that curries any function with multiple parameters, there are multiple
 * {@code apply()} methods for multiple function types, the supplied types are
 * <p>
 * {@link BiConsumer}<br>
 * {@link BiPredicate}<br>
 * {@link BiFunction}<br>
 * {@link Function3}<br>
 * <p>
 * all of those types are the {@code RxJava} types, not {@code Java 8}
 * <p>
 * notice that curried functions does not throw exceptions other than {@link RuntimeException},
 * if there execution method threw an {@link Exception}, it will be wrapped in a
 * {@link RuntimeException}, else it will throw the sub-class of the {@link RuntimeException} that
 * was already thrown by the executing function ... this is to avoid the {@code try/catch} clauses
 * every where, you can use the non-throwing versions of the functional interfaces to benefit from
 * this behavior, like :
 * <p>
 * {@link RxFunction}<br>
 * {@link RxConsumer}<br>
 * {@link RxPredicate}<br>
 * <p>
 * Created by Ahmed Adel Ismail on 6/27/2017.
 */
public class Curry {
    
    /**
     * curry a {@link Function3}
     *
     * @param function     the {@link Function3} that will be executed
     * @param parameterOne the first parameter
     * @param <P1>         the type of the first parameter
     * @param <P2>         the type of the second parameter
     * @param <P3>         the type of the third parameter
     * @param <R>          the type of the expected result
     * @return the {@link CurriedFunction} that will be passed the next parameter
     * later
     */
    public static <P1, P2, P3, R> RxFunction<P3, R> toFunction(
            Function3<P1, P2, P3, R> function, P1 parameterOne, P2 parameterTwo) {
        return toBiFunction(function, parameterOne).apply(parameterTwo);
    }

    /**
     * curry a {@link Function3}
     *
     * @param function     the {@link Function3} that will be executed
     * @param parameterOne the first parameter
     * @param <P1>         the type of the first parameter
     * @param <P2>         the type of the second parameter
     * @param <P3>         the type of the third parameter
     * @param <R>          the type of the expected result
     * @return the {@link CurriedFunction} that will be passed the next parameter
     * later
     */
    public static <P1, P2, P3, R> CurriedFunction<P2, P3, R> toBiFunction(
            Function3<P1, P2, P3, R> function, P1 parameterOne) {
        return new CurriedFunction3<>(function, parameterOne).curry();
    }

    /**
     * curry a {@link BiFunction}
     *
     * @param function     the {@link BiFunction} that will be executed
     * @param parameterOne the first parameter
     * @param <P1>         the type of the first parameter
     * @param <P2>         the type of the second parameter
     * @param <R>          the type of the expected result
     * @return the {@link Function} that will be passed the second parameter
     * later and be executed
     */
    public static <P1, P2, R> RxFunction<P2, R> toFunction(
            BiFunction<P1, P2, R> function, P1 parameterOne) {
        return new CurriedBiFunction<>(function, parameterOne);
    }


    /**
     * curry a {@link Function3} with a {@link Void} return type as a {@link BiConsumer}
     *
     * @param function     the {@link Function3} that will be executed
     * @param parameterOne the first parameter
     * @param <P1>         the type of the first parameter
     * @param <P2>         the type of the second parameter
     * @param <P3>         the type of the third parameter
     * @return the {@link CurriedFunction} that will be passed the next parameter
     * later
     */
    public static <P1, P2, P3> CurriedConsumer<P2, P3> toBiConsumer(
            Function3<P1, P2, P3, Void> function, P1 parameterOne) {
        return new CurriedConsumer3<>(function, parameterOne).curry();
    }

    /**
     * curry a {@link Function3} with a {@link Void} return type as a {@link Consumer}
     *
     * @param function     the {@link Function3} that will be executed
     * @param parameterOne the first parameter
     * @param <P1>         the type of the first parameter
     * @param <P2>         the type of the second parameter
     * @param <P3>         the type of the third parameter
     * @return the {@link CurriedFunction} that will be passed the next parameter
     * later
     */
    public static <P1, P2, P3> RxConsumer<P3> toConsumer(
            Function3<P1, P2, P3, Void> function, P1 parameterOne, P2 parameterTwo) {
        return new CurriedConsumer3<>(function, parameterOne).curry().apply(parameterTwo);
    }

    /**
     * curry a {@link BiConsumer}
     *
     * @param consumer     the {@link BiConsumer} that will be executed
     * @param parameterOne the first parameter
     * @param <P1>         the type of the first parameter
     * @param <P2>         the type of the second parameter
     * @return the {@link Consumer} that will be passed the second parameter
     * later and be executed
     */
    public static <P1, P2> RxConsumer<P2> toConsumer(BiConsumer<P1, P2> consumer, P1 parameterOne) {
        return new CurriedBiConsumer<>(consumer, parameterOne);
    }


    /**
     * curry a {@link Function3} with a {@link Boolean} return type as a {@link BiPredicate}
     *
     * @param function     the {@link Function3} that will be executed
     * @param parameterOne the first parameter
     * @param <P1>         the type of the first parameter
     * @param <P2>         the type of the second parameter
     * @param <P3>         the type of the third parameter
     * @return the {@link CurriedFunction} that will be passed the next parameter
     * later
     */
    public static <P1, P2, P3> CurriedPredicate<P2, P3> toBiPredicate(
            Function3<P1, P2, P3, Boolean> function, P1 parameterOne) {
        return new CurriedPredicate3<>(function, parameterOne).curry();
    }

    /**
     * curry a {@link Function3} with a {@link Boolean} return type as a {@link Predicate}
     *
     * @param function     the {@link Function3} that will be executed
     * @param parameterOne the first parameter
     * @param parameterTwo the second parameter
     * @param <P1>         the type of the first parameter
     * @param <P2>         the type of the second parameter
     * @param <P3>         the type of the third parameter
     * @return the {@link CurriedFunction} that will be passed the next parameter
     * later
     */
    public static <P1, P2, P3> RxPredicate<P3> toPredicate(
            Function3<P1, P2, P3, Boolean> function, P1 parameterOne, P2 parameterTwo) {
        return new CurriedPredicate3<>(function, parameterOne).curry().apply(parameterTwo);
    }

    /**
     * curry a {@link BiPredicate}
     *
     * @param predicate    the {@link BiPredicate} that will be executed
     * @param parameterOne the first parameter
     * @param <P1>         the type of the first parameter
     * @param <P2>         the type of the second parameter
     * @return the {@link Predicate} that will be passed the second parameter
     * later and be executed
     */
    public static <P1, P2> RxPredicate<P2> toPredicate(
            BiPredicate<P1, P2> predicate, P1 parameterOne) {
        return new CurriedBiPredicate<>(predicate, parameterOne);
    }

    /**
     * curry a {@link Consumer} to an {@link Action}
     *
     * @param consumer     the {@link BiPredicate} that will be executed
     * @param parameterOne the first parameter
     * @return the {@link Action} that will be passed the second parameter
     * later and be executed
     */
    public static <P1> RxAction toAction(Consumer<P1> consumer, P1 parameterOne) {
        return new CurriedAction<>(consumer, parameterOne);
    }

    /**
     * curry a {@link Consumer} to an {@link Action}
     *
     * @param function     the {@link BiPredicate} that will be executed
     * @param parameterOne the first parameter
     * @return the {@link Action} that will be passed the second parameter
     * later and be executed
     */
    public static <P1> RxAction toAction(Function<P1, ?> function, P1 parameterOne) {
        return new CurriedAction<>(function, parameterOne);
    }

    /**
     * curry a {@link Consumer} to an {@link Action}
     *
     * @param function     the {@link BiPredicate} that will be executed
     * @param parameterOne the first parameter
     * @return the {@link Action} that will be passed the second parameter
     * later and be executed
     */
    public static <P1, R> RxCallable<R> toCallable(Function<P1, R> function, P1 parameterOne) {
        return new CurriedCallable<>(function, parameterOne);
    }


}
