# J-Curry
A library that enables Currying functions in Java (using RxJava2 interfaces), compatible with Java 7

# What is Currying

A small video that explains Currying : https://www.youtube.com/watch?v=iZLP4qOwY8I&feature=youtu.be

# Curry.toConsumer(), Curry.toFunction(), Curry.toBiFunction(), Curry.toPredicate(), Curry.toAction(), Curry.toCallable()

it is possible to Curry any method through one of 2 ways, the first is to put this method in one of the RxJava2 functional interfaces like a Consumer.java, or Function.java, etc..., or through passing it's "method reference" as there first parameter, for Android this requires adding Retrolambda

- how to use Retrolambda : http://www.vogella.com/tutorials/Retrolambda/article.html
- Retrolambda on Github : https://github.com/evant/gradle-retrolambda

```java
public class MainActivity extends AppCompatActivity{

    private final Consumer<String> log = Curry.toConsumer(Log::d, "MainActivity");

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
 ```

# RxConsumer, RxFunction, RxPredicate, RxAction, RxCallable

By default the functional interfaces (Consumer, Function, Predicate), there method throws Exception by default, thats why we have 
to wrap it's call in a try/catch, like this :

```java
try{
    log.accept("onCreate()");
} catch(Exception e){
    // do something
}
```

so the library provides interfaces that already extends those functional interfaces, but removes the "throws Exception" from 
the method's Signature, those interfaces are (RxConsumer, RxFunction, RxPredicate) ... used as follows :

```java
public class MainActivity extends AppCompatActivity {

    private final RxConsumer<String> log = Curry.toConsumer(Log::d, "MainActivity");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        log.accept("onCreate()");
    }
}
```
    
so what happened to the thrown Exception ?

    Curried functions does not throw exceptions other than RuntimeException,
    if there execution method threw an Exception, it will be wrapped in a
    RuntimeException, else it will throw the sub-class of the RuntimeException that
    was already thrown by the executing function    
    
# Usage with RxJava2 Operators through functional interfaces

The greatest benefit from such library is to use with RxJava2 operators, since it uses the RxJava interfaces (Consumer, Function, Predicate).

exmaples from CurryTest.java in version 0.0.1 :

use in map() operator :

```java
@Test
public void useCurriedBiFunctionInLocalVariableInMapOperator() throws Exception {

    Function<Integer, Integer> sumWith10 = Curry.toFunction(sumFunction(), 10);
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
```

use in filter() operator :

```java
@Test
public void curryBiPredicateInFilterOperator() throws Exception {
    List<Integer> evens = Observable.fromArray(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
            .filter(Curry.toPredicate(remainderFilter(), 2))
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
```

and so on ... since every Curry method returns a Functional interface that awaits single parameter, this is perfectly usable in RxJava2 operators 

# Usage with RxJava2 Operators through method reference (using Retrolambda)

```java
Observable.fromCallable()

Observable.fromArray("1","2","3","4","5")
            .forEach(Curry.toConsumer(Log::d,"MY_TAG"));

Observable.fromArray(1,2,3,4)
            .map(Curry.toFunction(IntMath::checkedMultiply,10))
            .map(Curry.toFunction(String::format, "|%3d|"))
            .subscribe(Curry.toConsumer(Log::d,"MY_TAG"), Throwable::printStackTrace);
```

notice that Currying now can work on any function in any class, and now it does not require implementing functional interfaces any more

# SwapCurry ... where the fun begins
after version 0.0.3, it is possible to swap the parameters of the curried function, so we can pass the second parameter of the method first, and the curried function will return another function that expects to receive the first parameter of the original method, then it executes, like in the following example :

```java
Observable.fromArray("%d","%02d","%04d")
            .forEach(SwapCurry.toConsumer(System.out::printf,10));
```

the System.out.printf() takes a "String" as it's first parameter, and an "Integer" as it's second parameter, what was done here is that, we passed the "Integer" first in the SwapCurry.toConsumer() method, and we receieved the "String" later from the Observable.forEach() method.

# Tuples and Entries

After version 1.0.0, this library supports breaking down Map.Entry and Tuples (Pairs and Triplets) into method parameters, weather in there same order, or in there swapped order (for Pairs only) ... for example :

```java
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
```

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
	      compile 'com.github.Ahmed-Adel-Ismail:J-Curry:1.1.0'
    }

