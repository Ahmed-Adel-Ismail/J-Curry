package com.functional.curry;

import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.BiPredicate;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.functions.Predicate;

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
