package com.functional.curry;

import org.junit.Test;

import java.io.IOException;

import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;

import static org.junit.Assert.assertTrue;

/**
 * Created by Ahmed Adel Ismail on 2/28/2018.
 */
public class EntriesTest {

    @Test
    public void withBiConsumerThenInvokeSuccessfully() throws Exception {
        final boolean[] result = {false, false};
        Entries.withBiConsumer(new BiConsumer<Boolean, Boolean>() {
            @Override
            public void accept(Boolean valueOne, Boolean valueTwo) throws Exception {
                result[0] = valueOne;
                result[1] = valueTwo;
            }
        }, MapEntry.with(true, true).call());

        assertTrue(result[0] && result[1]);
    }

    @Test
    public void withBiFunctionThenInvokeSuccessfully() throws Exception {
        boolean result = Entries.withBiFunction(new BiFunction<Boolean, Boolean, Boolean>() {
            @Override
            public Boolean apply(Boolean valueOne, Boolean valueTwo) throws Exception {
                return valueOne && valueTwo;
            }
        }, MapEntry.with(true, true).call());

        assertTrue(result);
    }


    @Test(expected = RuntimeException.class)
    public void withCrashingBiConsumerThenCrashWithRuntimeException() throws Exception {
        Entries.withBiConsumer(new BiConsumer<Integer, Integer>() {
            @Override
            public void accept(Integer valueOne, Integer valueTwo) throws Exception {
                throw new IOException();
            }
        }, MapEntry.with(0, 0).call());

    }

    @Test(expected = RuntimeException.class)
    public void withCrashingBiFunctionThenCrashWithRuntimeException() throws Exception {
        Entries.withBiFunction(new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer valueOne, Integer valueTwo) throws Exception {
                throw new IOException();
            }
        }, MapEntry.with(0, 0).call());

    }

}