package com.functional.curry;

import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.junit.Test;

import java.io.IOException;

import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function3;

import static org.junit.Assert.*;

/**
 * Created by Ahmed Adel Ismail on 2/21/2018.
 */
public class TuplesTest {
    @Test
    public void withBiConsumerThenInvokeSuccessfully() throws Exception {
        final boolean[] result = {false, false};
        Tuples.withBiConsumer(new BiConsumer<Boolean, Boolean>() {
            @Override
            public void accept(Boolean valueOne, Boolean valueTwo) throws Exception {
                result[0] = valueOne;
                result[1] = valueTwo;
            }
        }, Pair.with(true, true));

        assertTrue(result[0] && result[1]);
    }

    @Test
    public void withBiFunctionThenInvokeSuccessfully() throws Exception {
        boolean result = Tuples.withBiFunction(new BiFunction<Boolean, Boolean, Boolean>() {
            @Override
            public Boolean apply(Boolean valueOne, Boolean valueTwo) throws Exception {
                return valueOne && valueTwo;
            }
        }, Pair.with(true, true));

        assertTrue(result);
    }

    @Test
    public void withFunction3ThenInvokeSuccessfully() throws Exception {
        boolean result = Tuples.withFunction3(new Function3<Boolean, Boolean, Boolean, Boolean>() {
            @Override
            public Boolean apply(Boolean valueOne, Boolean valueTwo, Boolean valueThree) throws Exception {
                return valueOne && valueTwo && valueThree;
            }
        }, Triplet.with(true, true, true));

        assertTrue(result);
    }

    @Test(expected = RuntimeException.class)
    public void withCrashingBiConsumerThenCrashWithRuntimeException() throws Exception {
        Tuples.withBiConsumer(new BiConsumer<Integer, Integer>() {
            @Override
            public void accept(Integer valueOne, Integer valueTwo) throws Exception {
                throw new IOException();
            }
        }, Pair.with(0, 0));

    }

    @Test(expected = RuntimeException.class)
    public void withCrashingBiFunctionThenCrashWithRuntimeException() throws Exception {
        Tuples.withBiFunction(new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer valueOne, Integer valueTwo) throws Exception {
                throw new IOException();
            }
        }, Pair.with(0, 0));

    }

    @Test(expected = RuntimeException.class)
    public void withCrashingFunction3ThenCrashWithRuntimeException() throws Exception {
        Tuples.withFunction3(new Function3<Integer, Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer valueOne, Integer valueTwo, Integer valueThree) throws Exception {
                throw new IOException();
            }
        }, Triplet.with(0, 0, 0));

    }

}