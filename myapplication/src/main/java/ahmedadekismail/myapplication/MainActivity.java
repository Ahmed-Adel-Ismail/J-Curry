package ahmedadekismail.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.functional.curry.Curry;
import com.functional.curry.RxConsumer;
import com.functional.curry.SwapTuples;
import com.functional.curry.Tuples;

import org.javatuples.Pair;

public class MainActivity extends AppCompatActivity
{

    private final RxConsumer<String> log = Curry.toConsumer(Log::d, "MainActivity");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        log.accept("onCreate()");
    }
}


class Main
{


    public int sumNumbersInPair(Pair<Integer, Integer> numbers) {
        return Tuples.withBiFunction(this::sum, numbers);
    }


    private int sum(int first, int second) {
        return first + second;
    }

    public void printInDescendingOrder(Pair<Integer, Integer> numbers) {
        SwapTuples.withBiConsumer(this::printNumbers, numbers);
    }

    private void printNumbers(int first, int second) {
        System.out.println(first);
        System.out.println(second);
    }


}
