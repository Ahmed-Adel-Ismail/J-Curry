package com.functional.curry;

import org.junit.Test;

import java.io.IOException;

import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;

import static org.junit.Assert.assertTrue;

/**
 * Created by Ahmed Adel Ismail on 2/28/2018.
 */
public class SwapEntriesTest {

    @Test
    public void withBiConsumerThenInvokeSuccessfully() throws Exception {
        final int[] result = {1, 2};
        SwapEntries.withBiConsumer(new BiConsumer<Integer, Integer>() {
            @Override
            public void accept(Integer valueOne, Integer valueTwo) throws Exception {
                result[0] = valueOne;
                result[1] = valueTwo;
            }
        }, MapEntry.with(3, 4).call());

        assertTrue(result[0] == 4 && result[1] == 3);
    }

    @Test
    public void withBiFunctionThenInvokeSuccessfully() throws Exception {
        int[] result = SwapEntries.withBiFunction(new BiFunction<Integer, Integer, int[]>() {
            @Override
            public int[] apply(Integer valueOne, Integer valueTwo) throws Exception {
                return new int[]{valueOne, valueTwo};
            }
        }, MapEntry.with(3, 4).call());

        assertTrue(result[0] == 4 && result[1] == 3);
    }

    @Test(expected = RuntimeException.class)
    public void withCrashingBiConsumerThenCrashWithRuntimeException() throws Exception {
        SwapEntries.withBiConsumer(new BiConsumer<Integer, Integer>() {
            @Override
            public void accept(Integer valueOne, Integer valueTwo) throws Exception {
                throw new IOException();
            }
        }, MapEntry.with(0, 0).call());

    }

    @Test(expected = RuntimeException.class)
    public void withCrashingBiFunctionThenCrashWithRuntimeException() throws Exception {
        SwapEntries.withBiFunction(new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer valueOne, Integer valueTwo) throws Exception {
                throw new IOException();
            }
        }, MapEntry.with(0, 0).call());

    }

}