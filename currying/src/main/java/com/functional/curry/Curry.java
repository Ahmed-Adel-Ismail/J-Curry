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
 * every where, you can use the non-throwing versions of the functional interfaces to benefit from
 * this behavior, like :
 * <p>
 * {@link RxFunction}<br>
 * {@link RxConsumer}<br>
 * {@link RxPredicate}<br>
 * <p>
 * Created by Ahmed Adel Ismail on 6/27/2017.
 */
public class Curry
{


    /**
     * curryParameterTwo a {@link Function3}
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
    toBiFunction(Function3<ParameterOne, ParameterTwo, ParameterThree, Return> function, ParameterOne parameterOne) {
        return new CurriedFunction3<>(function, parameterOne).curry();
    }

    /**
     * curryParameterTwo a {@link Function3}
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
    RxFunction<ParameterThree, Return>
    toFunction(Function3<ParameterOne, ParameterTwo, ParameterThree, Return> function,
               ParameterOne parameterOne, ParameterTwo parameterTwo) {
        return toBiFunction(function, parameterOne).apply(parameterTwo);
    }

    /**
     * curryParameterTwo a {@link BiFunction}
     *
     * @param function       the {@link BiFunction} that will be executed
     * @param parameterOne   the first parameter
     * @param <ParameterOne> the type of the first parameter
     * @param <ParameterTwo> the type of the second parameter
     * @param <Return>       the type of the expected result
     * @return the {@link Function} that will be passed the second parameter
     * later and be executed
     */
    public static <ParameterOne, ParameterTwo, Return> RxFunction<ParameterTwo, Return>
    toFunction(BiFunction<ParameterOne, ParameterTwo, Return> function, ParameterOne parameterOne) {
        return new CurriedBiFunction<>(function, parameterOne);
    }


    /**
     * curryParameterTwo a {@link Function3} with a {@link Void} return type as a {@link BiConsumer}
     *
     * @param function         the {@link Function3} that will be executed
     * @param parameterOne     the first parameter
     * @param <ParameterOne>   the type of the first parameter
     * @param <ParameterTwo>   the type of the second parameter
     * @param <ParameterThree> the type of the third parameter
     * @return the {@link CurriedFunction} that will be passed the next parameter
     * later
     */
    public static <ParameterOne, ParameterTwo, ParameterThree>
    CurriedConsumer<ParameterTwo, ParameterThree>
    toBiConsumer(Function3<ParameterOne, ParameterTwo, ParameterThree, Void> function,
                 ParameterOne parameterOne) {
        return new CurriedConsumer3<>(function, parameterOne).curry();
    }

    /**
     * curryParameterTwo a {@link Function3} with a {@link Void} return type as a {@link Consumer}
     *
     * @param function         the {@link Function3} that will be executed
     * @param parameterOne     the first parameter
     * @param <ParameterOne>   the type of the first parameter
     * @param <ParameterTwo>   the type of the second parameter
     * @param <ParameterThree> the type of the third parameter
     * @return the {@link CurriedFunction} that will be passed the next parameter
     * later
     */
    public static <ParameterOne, ParameterTwo, ParameterThree>
    RxConsumer<ParameterThree>
    toConsumer(Function3<ParameterOne, ParameterTwo, ParameterThree, Void> function,
               ParameterOne parameterOne, ParameterTwo parameterTwo) {
        return new CurriedConsumer3<>(function, parameterOne).curry().apply(parameterTwo);
    }

    /**
     * curryParameterTwo a {@link BiConsumer}
     *
     * @param consumer       the {@link BiConsumer} that will be executed
     * @param parameterOne   the first parameter
     * @param <ParameterOne> the type of the first parameter
     * @param <ParameterTwo> the type of the second parameter
     * @return the {@link Consumer} that will be passed the second parameter
     * later and be executed
     */
    public static <ParameterOne, ParameterTwo> RxConsumer<ParameterTwo>
    toConsumer(BiConsumer<ParameterOne, ParameterTwo> consumer, ParameterOne parameterOne) {
        return new CurriedBiConsumer<>(consumer, parameterOne);
    }


    /**
     * curryParameterTwo a {@link Function3} with a {@link Boolean} return type as a {@link BiPredicate}
     *
     * @param function         the {@link Function3} that will be executed
     * @param parameterOne     the first parameter
     * @param <ParameterOne>   the type of the first parameter
     * @param <ParameterTwo>   the type of the second parameter
     * @param <ParameterThree> the type of the third parameter
     * @return the {@link CurriedFunction} that will be passed the next parameter
     * later
     */
    public static <ParameterOne, ParameterTwo, ParameterThree>
    CurriedPredicate<ParameterTwo, ParameterThree>
    toBiPredicate(Function3<ParameterOne, ParameterTwo, ParameterThree, Boolean> function,
                  ParameterOne parameterOne) {
        return new CurriedPredicate3<>(function, parameterOne).curry();
    }

    /**
     * curryParameterTwo a {@link Function3} with a {@link Boolean} return type as a {@link Predicate}
     *
     * @param function         the {@link Function3} that will be executed
     * @param parameterOne     the first parameter
     * @param parameterTwo     the second parameter
     * @param <ParameterOne>   the type of the first parameter
     * @param <ParameterTwo>   the type of the second parameter
     * @param <ParameterThree> the type of the third parameter
     * @return the {@link CurriedFunction} that will be passed the next parameter
     * later
     */
    public static <ParameterOne, ParameterTwo, ParameterThree>
    RxPredicate<ParameterThree>
    toPredicate(Function3<ParameterOne, ParameterTwo, ParameterThree, Boolean> function,
                ParameterOne parameterOne, ParameterTwo parameterTwo) {
        return new CurriedPredicate3<>(function, parameterOne).curry().apply(parameterTwo);
    }

    /**
     * curryParameterTwo a {@link BiPredicate}
     *
     * @param predicate      the {@link BiPredicate} that will be executed
     * @param parameterOne   the first parameter
     * @param <ParameterOne> the type of the first parameter
     * @param <ParameterTwo> the type of the second parameter
     * @return the {@link Predicate} that will be passed the second parameter
     * later and be executed
     */
    public static <ParameterOne, ParameterTwo> RxPredicate<ParameterTwo>
    toPredicate(BiPredicate<ParameterOne, ParameterTwo> predicate, ParameterOne parameterOne) {
        return new CurriedBiPredicate<>(predicate, parameterOne);
    }

}
