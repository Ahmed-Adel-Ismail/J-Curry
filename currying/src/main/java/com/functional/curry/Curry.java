package com.functional.curry;

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
 * every where
 * <p>
 * Created by Ahmed Adel Ismail on 6/27/2017.
 */
public class Curry
{
    /**
     * curry a {@link BiFunction}
     *
     * @param function       the {@link BiFunction} that will be executed
     * @param parameterOne   the first parameter
     * @param <ParameterOne> the type of the first parameter
     * @param <ParameterTwo> the type of the second parameter
     * @param <Return>       the type of the expected result
     * @return the {@link Function} that will be passed the second parameter
     * later and be executed
     */
    public static <ParameterOne, ParameterTwo, Return> Function<ParameterTwo, Return>
    apply(BiFunction<ParameterOne, ParameterTwo, Return> function, ParameterOne parameterOne) {
        return new CurriedBiFunction<>(function, parameterOne);
    }

    /**
     * curry a {@link Function3}
     *
     * @param function         the {@link Function3} that will be executed
     * @param parameterOne     the first parameter
     * @param <ParameterOne>   the type of the first parameter
     * @param <ParameterTwo>   the type of the second parameter
     * @param <ParameterThree> the type of the third parameter
     * @param <Return>         the type of the expected result
     * @return the {@link CurriedFunction} that will be passed the next parameter
     * later
     */
    public static <ParameterOne, ParameterTwo, ParameterThree, Return>
    CurriedFunction<ParameterTwo, ParameterThree, Return>
    apply(Function3<ParameterOne, ParameterTwo, ParameterThree, Return> function, ParameterOne parameterOne) {
        return new CurriedFunc3<>(function, parameterOne).curry();
    }

    /**
     * curry a {@link BiConsumer}
     *
     * @param consumer       the {@link BiConsumer} that will be executed
     * @param parameterOne   the first parameter
     * @param <ParameterOne> the type of the first parameter
     * @param <ParameterTwo> the type of the second parameter
     * @return the {@link Consumer} that will be passed the second parameter
     * later and be executed
     */
    public static <ParameterOne, ParameterTwo> Consumer<ParameterTwo>
    apply(BiConsumer<ParameterOne, ParameterTwo> consumer, ParameterOne parameterOne) {
        return new CurriedBiConsumer<>(consumer, parameterOne);
    }


    /**
     * curry a {@link BiPredicate}
     *
     * @param predicate      the {@link BiPredicate} that will be executed
     * @param parameterOne   the first parameter
     * @param <ParameterOne> the type of the first parameter
     * @param <ParameterTwo> the type of the second parameter
     * @return the {@link Predicate} that will be passed the second parameter
     * later and be executed
     */

    public static <ParameterOne, ParameterTwo> Predicate<ParameterTwo>
    apply(BiPredicate<ParameterOne, ParameterTwo> predicate, ParameterOne parameterOne) {
        return new CurriedBiPredicate<>(predicate, parameterOne);
    }
}
