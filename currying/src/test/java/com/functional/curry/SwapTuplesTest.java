package com.functional.curry;

import org.javatuples.Pair;
import org.junit.Test;

import java.io.IOException;

import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;

import static org.junit.Assert.*;

/**
 * Created by Ahmed Adel Ismail on 2/21/2018.
 */
public class SwapTuplesTest
{
    @Test
    public void withBiConsumerThenInvokeSuccessfully() throws Exception {
        final int[] result = {1, 2};
        SwapTuples.withBiConsumer(new BiConsumer<Integer, Integer>()
        {
            @Override
            public void accept(Integer valueOne, Integer valueTwo) throws Exception {
                result[0] = valueOne;
                result[1] = valueTwo;
            }
        }, Pair.with(3, 4));

        assertTrue(result[0] == 4 && result[1] == 3);
    }

    @Test
    public void withBiFunctionThenInvokeSuccessfully() throws Exception {
        int[] result = SwapTuples.withBiFunction(new BiFunction<Integer, Integer, int[]>()
        {
            @Override
            public int[] apply(Integer valueOne, Integer valueTwo) throws Exception {
                return new int[]{valueOne, valueTwo};
            }
        }, Pair.with(3, 4));

        assertTrue(result[0] == 4 && result[1] == 3);
    }

    @Test(expected = RuntimeException.class)
    public void withCrashingBiConsumerThenCrashWithRuntimeException() throws Exception {
        SwapTuples.withBiConsumer(new BiConsumer<Integer, Integer>()
        {
            @Override
            public void accept(Integer valueOne, Integer valueTwo) throws Exception {
              throw new IOException();
            }
        }, Pair.with(0, 0));

    }

    @Test(expected = RuntimeException.class)
    public void withCrashingBiFunctionThenCrashWithRuntimeException() throws Exception {
        SwapTuples.withBiFunction(new BiFunction<Integer, Integer,Integer>()
        {
            @Override
            public Integer apply(Integer valueOne, Integer valueTwo) throws Exception {
                throw new IOException();
            }
        }, Pair.with(0, 0));

    }


}