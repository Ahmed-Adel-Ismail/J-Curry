# J-Curry
a library that enables Currying functions in Java (using RxJava2 interfaces), compatible with Java 6, 7 and 8

# What is Currying

a small video that explains Currying : https://www.youtube.com/watch?v=iZLP4qOwY8I&feature=youtu.be

# Curry.apply(function,firstParameter)

To curry any function you will need to call Curry.apply(), the apply method will return another function that takes the next parameter and be executed later.
A sample example for a logging function is as follows :

    public class MainActivity extends AppCompatActivity
    {

        private final Consumer<String> log = Curry.apply(debugLog(), "MainActivity");
    
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
    
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            try{
                log.accept("onCreate()");
            }catch(Exception e){
                // do something
            }
        }
    
    }

# RxConsumer, RxFunction, RxPredicate

By default the functional interfaces (Consumer, Function, Predicate), there method throws Exception by default, thats why we have 
to wrap it's call in a try/catch, like this :

    try{
        log.accept("onCreate()");
    }catch(Exception e){
        // do something
    }

so the library provides interfaces that already extends those functional interfaces, but removes the "throws Exception" from 
the method's Signature, those interfaces are (RxConsumer, RxFunction, RxPredicate) ... used as follows :

    public class MainActivity extends AppCompatActivity
    {

        private final RxConsumer<String> log = Curry.apply(debugLog(), "MainActivity");
    
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
    
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            log.accept("onCreate()");
        }
    
    }
    
# Usage with RxJava2 Operators

The greatest benefit from such library is to use with RxJava2 operators, since it uses the RxJava interfaces (Consumer, Function, Predicate).

exmaples from CurryTest.java :

use in map() operator :

    @Test
    public void useCurriedBiFunctionInLocalVariableInMapOperator() throws Exception {

        Function<Integer, Integer> sumWith10 = Curry.apply(sumFunction(), 10);
        List<Integer> integers = Observable.fromArray(1, 2)
                .map(sumWith10)
                .toList().blockingGet();

        assertTrue(integers.get(0).equals(11) && integers.get(1).equals(12));

    }
    
    private BiFunction<Integer, Integer, Integer> sumFunction() {
        return new BiFunction<Integer, Integer, Integer>()
        {
            @Override
            public Integer apply(@NonNull Integer numOne, @NonNull Integer numTwo) {
                return numOne + numTwo;
            }
        };
    }

use in filter() operator :

    @Test
    public void curryBiPredicateInFilterOperator() throws Exception {
        List<Integer> evens = Observable.fromArray(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
                .filter(Curry.apply(remainderFilter(), 2))
                .toList().blockingGet();

        assertTrue(evens.get(0).equals(0) && evens.get(1).equals(2));
    }

    private BiPredicate<Integer, Integer> remainderFilter() {
        return new BiPredicate<Integer, Integer>()
        {
            @Override
            public boolean test(@NonNull Integer remainder, @NonNull Integer value) {
                return value % remainder == 0;
            }
        };
    }

and so on ... since every Curry.apply() returns a Functional interface that awaits single parameter, this is perfectly usable in RxJava2 operators 

# Adding gradle dependency

Step 1. Add it in your root build.gradle at the end of repositories:

    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
  
Step 2. Add the dependency
	  
    dependencies {
	      compile 'com.github.Terebentikh:J-Curry:0.0.1'
    }


# Limitations

this library already adds RxJava2 2.1.1 in it's dependencies :

    dependencies {
        compile 'io.reactivex.rxjava2:rxjava:2.1.1'
    }

if you add this library to your project, you dont need to include RxJava2 in your build.gradle, since it is already included
