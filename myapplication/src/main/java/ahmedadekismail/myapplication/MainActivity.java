package ahmedadekismail.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.functional.curry.Curry;

import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity
{

    private final Consumer<String> log = Curry.apply(debugLog(), "MainActivity");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        log.accept("onCreate()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        log.accept("onStart()");
    }


    /**
     * a function that logs debug messages, can be declared in another class
     *
     * @return a {@link BiConsumer} that logs the passed tag and message
     */
    private static BiConsumer<String, String> debugLog() {
        return new BiConsumer<String, String>()
        {
            @Override
            public void accept(String tag, String message) {
                Log.d(tag, message);
            }
        };
    }

}
