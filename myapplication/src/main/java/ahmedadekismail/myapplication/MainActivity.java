package ahmedadekismail.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.functional.curry.Curry;
import com.functional.curry.Entries;
import com.functional.curry.RxBiFunction;
import com.functional.curry.RxConsumer;
import com.functional.curry.SwapTuples;
import com.functional.curry.Tuples;
import com.functional.types.Either;
import com.functional.types.Try;

import org.javatuples.Pair;

import java.util.Map;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

public class MainActivity extends AppCompatActivity {

    private final RxConsumer<String> log = Curry.toConsumer(Log::d, "MainActivity");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        log.accept("onCreate()");
    }
}


class Main {


    public int sumValuesInMap(Map<Integer, Integer> map) {
        return Observable.fromIterable(map.entrySet())
                .map(Entries.toFunction(this::sum))
                .reduce(this::sum)
                .blockingGet();
    }

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


    String concatenateOneAndTwo() {
        return Single.just(2)
                .map(concatenateTwoNumbers().apply(1))
                .blockingGet();
    }

    private RxBiFunction<Integer, Integer, String> concatenateTwoNumbers() {
        return intOne -> intTwo -> intOne + " and " + intTwo;
    }


    public static void main(String[] args) {

        System.out.println(castedIntToString());


    }

    private static Either<ClassCastException, String> castedIntToString() {
        return castInteger("test")
                .mapRight(String::valueOf)
                .mapRight(text -> text + " --- ");

    }

    private static Either<ClassCastException, Integer> castInteger(Object intValue) {
        try {
            return Either.withRight((int) intValue);
        } catch (ClassCastException e) {
            return Either.withLeft(e);
        }
    }

    private void cast(Object intValue) {
        Try.with(() -> (int) intValue)
                .map(integer -> integer * 100)
                .flatMap(Single::just, Single::error)
                .subscribe(System.out::println,Throwable::printStackTrace);

    }

}
