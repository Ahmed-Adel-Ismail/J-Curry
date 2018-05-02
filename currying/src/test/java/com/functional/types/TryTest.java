package com.functional.types;

import org.junit.Test;

import java.util.concurrent.Callable;

import io.reactivex.functions.Function;

import static org.junit.Assert.*;

public class TryTest {


    @Test
    public void getOrCrashWithValidValueThenReturnValue() throws Exception {

        boolean result =
                Try.with(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return true;
                    }
                }).getOrCrash();

        assertTrue(result);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getOrCrashWithInvalidValueThenCrash() throws Exception {
        Try.with(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                throw new UnsupportedOperationException();
            }
        }).getOrCrash();
    }

    @Test
    public void mappedGetOrElseWithValidValueThenReturnValue() {
        int result = Try.with(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return 10;
            }
        }).map(new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) throws Exception {
                return 100;
            }
        }).getOrElse(new Function<Exception, Integer>() {
            @Override
            public Integer apply(Exception e) throws Exception {
                return 20;
            }
        });

        assertEquals(100, result);
    }

    @Test
    public void getOrElseWithInvalidValueThenReturnElseExpression() {
        int result = Try.with(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                throw new Exception();
            }
        }).getOrElse(new Function<Exception, Integer>() {
            @Override
            public Integer apply(Exception e) throws Exception {
                return 20;
            }
        });

        assertEquals(20, result);
    }


    @Test
    public void flatMapWithValueThenReturnValueFlatMapper() {
        int result = Try.with(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return true;
            }
        }).flatMap(new Function<Boolean, Integer>() {
            @Override
            public Integer apply(Boolean aBoolean) throws Exception {
                return 1;
            }
        }, new Function<Exception, Integer>() {
            @Override
            public Integer apply(Exception e) throws Exception {
                return 2;
            }
        });

        assertEquals(1, result);
    }

    @Test
    public void flatMapWithExceptionThenReturnExceptionFlatMapper() {
        int result = Try.with(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                throw new Exception();
            }
        }).flatMap(new Function<Boolean, Integer>() {
            @Override
            public Integer apply(Boolean aBoolean) throws Exception {
                return 1;
            }
        }, new Function<Exception, Integer>() {
            @Override
            public Integer apply(Exception e) throws Exception {
                return 2;
            }
        });

        assertEquals(2, result);
    }

    @Test
    public void flatMapTryWithValueThenReturnConvertedValue() {
        boolean result = Try.with(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return true;
            }
        }).flatMap(new Function<Try<Boolean>, Boolean>() {
            @Override
            public Boolean apply(Try<Boolean> theTry) throws Exception {
                return theTry.getOrCrash();
            }
        });

        assertTrue(result);
    }
}