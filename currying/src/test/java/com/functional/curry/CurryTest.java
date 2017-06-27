package com.functional.curry;

import org.junit.Test;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.BiPredicate;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * the test cases for {@link Curry} class and it's usage
 * <p>
 * Created by Ahmed Adel Ismail on 6/26/2017.
 */
public class CurryTest
{

    @Test
    public void curryBiFunctionInMapOperator() throws Exception {
        List<Integer> integers = Observable.fromArray(1, 2)
                .map(Curry.apply(sumFunction(), 10))
                .toList().blockingGet();

        assertTrue(integers.get(0).equals(11) && integers.get(1).equals(12));

    }

    @Test
    public void useCurriedBiFunctionInLocalVariableInMapOperator() throws Exception {

        Function<Integer, Integer> sumWith10 = Curry.apply(sumFunction(), 10);
        List<Integer> integers = Observable.fromArray(1, 2)
                .map(sumWith10)
                .toList().blockingGet();

        assertTrue(integers.get(0).equals(11) && integers.get(1).equals(12));

    }

    @Test
    public void curryBiFunctionSuccessfully() throws Exception {
        Function<Integer, Integer> curriedBiFunction = Curry.apply(sumFunction(), 10);
        Integer result = curriedBiFunction.apply(10);
        assertEquals(result, Integer.valueOf(20));
    }


    @Test
    public void curryBiFunctionTwiceAndUseEachOneAsDifferentInstance() throws Exception {
        Function<Integer, Integer> curriedFunction = Curry.apply(sumFunction(), 10);
        Integer result = curriedFunction.apply(10);
        Integer resultTwo = curriedFunction.apply(20);
        assertTrue(result.equals(20) && resultTwo.equals(30));
    }

    private BiFunction<Integer, Integer, Integer> sumFunction() {
        return new BiFunction<Integer, Integer, Integer>()
        {
            @Override
            public Integer apply(@NonNull Integer numOne, @NonNull Integer numTwo) {
                return numOne + numTwo;
            }
        };
    }

    @Test
    public void curryBiPredicateInFilterOperator() throws Exception {
        List<Integer> evens = Observable.fromArray(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
                .filter(Curry.apply(remainderFilter(), 2))
                .toList().blockingGet();

        assertTrue(evens.get(0).equals(0) && evens.get(1).equals(2));
    }

    private BiPredicate<Integer, Integer> remainderFilter() {
        return new BiPredicate<Integer, Integer>()
        {
            @Override
            public boolean test(@NonNull Integer remainder, @NonNull Integer value) {
                return value % remainder == 0;
            }
        };
    }


    @Test
    public void curryBiConsumerInForEachOperator() throws Exception {
        Observable.fromArray(0, 0).blockingForEach(Curry.apply(nonZeroConsumer(), 1));
    }

    private BiConsumer<Integer, Integer> nonZeroConsumer() {
        return new BiConsumer<Integer, Integer>()
        {
            @Override
            public void accept(@NonNull Integer numOne, @NonNull Integer numTwo) {
                assertTrue((numOne + numTwo) != 0);
            }
        };
    }

    @Test
    public void curryFunction3Successfully() throws Exception {
        CurriedFunction<String, Integer, String> formatInteger = Curry.apply(formatter(), 0);
        Function<Integer, String> stringWithSpace = formatInteger.apply(" ");
        List<String> strings = Observable.fromArray(1, 2).map(stringWithSpace).toList().blockingGet();
        assertTrue(strings.get(0).equals("0 1") && strings.get(1).equals("0 2"));
    }

    private Function3<Integer, String, Integer, String> formatter() {
        return new Function3<Integer, String, Integer, String>()
        {
            @Override
            public String apply(Integer integerOne, String separator, Integer integerTwo) {
                return integerOne + separator + integerTwo;
            }
        };
    }

    @Test(expected = RuntimeException.class)
    public void throwNonRuntimeException_WrapInRuntimeException() throws Exception{
        Curry.apply(new BiConsumer<Integer, Integer>()
        {
            @Override
            public void accept(Integer integer, Integer integer2) throws Exception {
                throw new Exception();
            }
        },10).accept(0);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void throwRuntimeException_DelegateTheException() throws Exception{
        Curry.apply(new BiConsumer<Integer, Integer>()
        {
            @Override
            public void accept(Integer integer, Integer integer2) throws Exception {
                throw new UnsupportedOperationException();
            }
        },10).accept(0);
    }
}