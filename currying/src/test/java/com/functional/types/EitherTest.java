package com.functional.types;

import com.functional.curry.RxConsumer;
import com.functional.curry.RxFunction;

import junit.framework.AssertionFailedError;

import org.junit.Test;

import io.reactivex.functions.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EitherTest {

    @Test(expected = UnsupportedOperationException.class)
    public void getRightOrCrashWithLeftEitherThenCrash() {
        Either.withLeft(new UnsupportedOperationException())
                .getRightOrCrash();
    }

    @Test
    public void getRightOrCrashWithRightEitherThenReturnValue() throws Exception {
        boolean result = Either.withRight(true)
                .getRightOrCrash();
        assertTrue(result);

    }

    @Test
    public void isRightWithRightEitherThenReturnTrue() {
        assertTrue(Either.withRight(10).isRight());
    }

    @Test
    public void isLeftWithLeftEitherThenReturnTrue() {
        assertTrue(Either.withLeft(new NullPointerException()).isLeft());
    }

    @Test
    public void foldWithLeftWitherThenInvokeLeftOption() {
        Either<UnsupportedOperationException, Integer> either =
                Either.withLeft(new UnsupportedOperationException("--"));

        either.fold()
                .apply(new RxConsumer<UnsupportedOperationException>() {
                    @Override
                    public void accept(UnsupportedOperationException e) {
                        assertEquals("--", e.getMessage());
                    }
                })
                .accept(new RxConsumer<Integer>() {
                    @Override
                    public void accept(Integer o) {
                        throw new AssertionFailedError("should not reach here");
                    }
                });

    }

    @Test
    public void foldWithRightWitherThenInvokeLeftOption() {
        Either<UnsupportedOperationException, Integer> either =
                Either.withRight(10);

        either.fold()
                .apply(new RxConsumer<UnsupportedOperationException>() {
                    @Override
                    public void accept(UnsupportedOperationException e) {
                        throw new AssertionFailedError("should not reach here");
                    }
                })
                .accept(new RxConsumer<Integer>() {
                    @Override
                    public void accept(Integer value) {
                        assertTrue(value == 10);
                    }
                });
    }

    @Test
    public void flatMapWithRightEitherThenReturnRightFlatMapperValue() {
        String result = Either.withRight(10)
                .flatMap(new RxFunction<Exception, String>() {
                    @Override
                    public String apply(Exception e) {
                        return "";
                    }
                }, new RxFunction<Integer, String>() {
                    @Override
                    public String apply(Integer integer) {
                        return String.valueOf(integer);
                    }
                });

        assertEquals("10", result);
    }

    @Test
    public void flatMapWithLeftEitherThenReturnLeftFlatMapperValue() {
        String result = Either.withLeft(new Exception("10"))
                .flatMap(new RxFunction<Exception, String>() {
                    @Override
                    public String apply(Exception e) {
                        return e.getMessage();
                    }
                }, new RxFunction<Object, String>() {
                    @Override
                    public String apply(Object integer) {
                        return "";
                    }
                });

        assertEquals("10", result);
    }

    @Test(expected = NullPointerException.class)
    public void mapLeftWithLeftEitherThenReturnEitherWithMappedValue() {
        Either.withLeft(new UnsupportedOperationException("1"))
                .mapLeft(new RxFunction<UnsupportedOperationException, NullPointerException>() {
                    @Override
                    public NullPointerException apply(UnsupportedOperationException e) {
                        return new NullPointerException("2");
                    }
                }).getRightOrCrash();
    }

    @Test
    public void mapRightWithRightEitherThenReturnEitherWithMappedValue() throws Exception {
        String result = Either.withRight(10)
                .mapRight(new RxFunction<Integer, String>() {
                    @Override
                    public String apply(Integer integer) {
                        return String.valueOf(integer);
                    }
                })
                .getRightOrCrash();

        assertEquals("10", result);
    }

    @Test
    public void flatMapEitherToRightWithRightEitherThenReturnValue() {
        int result = Either.withRight(10)
                .flatMap(new Function<Either<Exception, Integer>, Integer>() {
                    @Override
                    public Integer apply(Either<Exception, Integer> either) throws Exception {
                        return either.call();
                    }
                });

        assertEquals(10, result);
    }
}