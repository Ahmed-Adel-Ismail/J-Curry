package com.reactive.sample;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.functional.curry.Functions;
import com.functional.curry.RxAction;
import com.functional.curry.RxCallable;

import java.util.LinkedHashMap;
import java.util.Map;

public class SampleActivity extends Activity {




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


}


class MainTest implements RxAction {

    private final Map<Integer, Integer> numberMatrix = new LinkedHashMap<>();

    {
        numberMatrix.put(0, 1);
        numberMatrix.put(1, 2);
        numberMatrix.put(2, 3);
        numberMatrix.put(3, 4);
        numberMatrix.put(4, 5);
        numberMatrix.put(5, 0);
    }

    public static void main(String[] args) {
        System.out.println("start");
        new MainTest().run();
        System.out.println("end");
    }

    public void run(){

        Functions.with(0)
                .toRxCallable(numberMatrix::get)
                .andThen(this::print)
                .andThen(numberMatrix::get)
                .andThen(this::print)
                .andThen(numberMatrix::get)
                .andThen(this::print)
                .andThenRun(number -> System.out.println(number + " is the last one to print"))
                .run();

    }

    private int print(int number) {
        System.out.println(number);
        return number;
    }
}


class RandomGenerator implements RxCallable<Integer> {

    @Override
    public Integer call() {
        return (int) Math.random();
    }
}


