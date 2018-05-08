# J-Curry
A library that enables Currying and Partial Application functions in Java (using RxJava2 interfaces), compatible with Java 7, also starting from Version 2.0.0, The library offers extension functions for Kotlin functions, to make currying and partial application available by default

# What is Currying

A small video that explains Currying : https://www.youtube.com/watch?v=iZLP4qOwY8I&feature=youtu.be

# J-Curry for Kotlin

This library adds extensions to Functions in kotlin so that you can apply currying and partial application to any function in kotlin

## Currying and Partial application (pass some of the parameters now, and the rest later) :

```kotlin
fun main(args: Array<String>) {
    val curried = ::twoParametersFunction.with(1)
    val result = curried(2)
}

fun twoParametersFunction(p1: Int, p2: Int): Int = p1 + p2
```

The above code is the same as :

```kotlin
fun main(args: Array<String>) {
    val curried = ::twoParametersFunction with 1    // infixing
    val result = curried(2)
}

fun twoParametersFunction(p1: Int, p2: Int): Int = p1 + p2
```

Partial application is supported for functions upto 4 parameters :

```kotlin
fun main(args: Array<String>) {
    val curried = ::fourParametersFunction.with(1)
    curried(2,3,4)
}

fun fourParametersFunction(p1: Int, p2: Int, p3: Int, p4: Int): Int = p1 + p2 + p3 + p4
```

The above code can be even partially applied in steps :

```kotlin
fun main(args: Array<String>) {
    val curriedOne = ::fourParametersFunction.with(1)
    val curriedTwo = curriedOne.with(2)
    val curriedThree = curriedTwo.with(3)
    curriedThree(4)
}

fun fourParametersFunction(p1: Int, p2: Int, p3: Int, p4: Int): Int = p1 + p2 + p3 + p4
```

## Flipping functions (swapping the order of parameters)

You can flip the order of the parameters for any functions through the <b>flip()</b> extension function (similar to Haskell's flip function), as follows :

```kotlin
fun main(args: Array<String>) {
    val flipped = ::twoDifferentParametersFunction.flip()
    flipped("--",1)
}

fun twoDifferentParametersFunction(p1: Int, p2: String): String = "$p1$p2"
```

Flipping is used to make a function match a signature of another function parameter for example, you can also <b>flip</b> then partially apply a function in one step, which is <b>flipWith</b> :

```kotlin
fun main(args: Array<String>) {
    val flipped = ::twoDifferentParametersFunction.flipWith("--")
    flipped(1)
}

fun twoDifferentParametersFunction(p1: Int, p2: String): String = "$p1$p2"
```

which is the same as :

```kotlin
fun main(args: Array<String>) {
    val flipped = ::twoDifferentParametersFunction flipWith "--" // infixing
    flipped(1)
}

fun twoDifferentParametersFunction(p1: Int, p2: String): String = "$p1$p2"
```

## Deconstructing Tuples (Pairs and Triples) into function parameters

when having a function with two or three parameters, with there types that match a Tuple (Pair or Triple) ... so instead of passing this Tuple as Pair.first and Pair.second for example, you can use the extension function that deconnstructs that Tuple for you :

```kotlin
fun main(args: Array<String>) {
    val pair = Pair(1,2)
    val result = ::twoParametersFunction.with(pair)

    val triple = Triple(1,2,3)
    val resultAgain = ::threeParametersFunction.with(tuple)
}

fun twoParametersFunction(p1: Int, p2: Int): Int = p1 + p2
fun threeParametersFunction(p1: Int, p2: Int, p3: Int): Int = p1 + p2 + p3
```

The above code is same as :

```kotlin
fun main(args: Array<String>) {
    val pair = Pair(1,2)
    val result = ::twoParametersFunction with pair          // infixing

    val triple = Triple(1,2,3)
    val resultAgain = ::threeParametersFunction with tuple  // infixing
}

fun twoParametersFunction(p1: Int, p2: Int): Int = p1 + p2
fun threeParametersFunction(p1: Int, p2: Int, p3: Int): Int = p1 + p2 + p3
```

For functions with two parameters, you can flip before deconstructing the tuple through invoking <b>flipWith()</b> :

```kotlin
fun main(args: Array<String>) {
    val pair = Pair("-",1)
    val result = ::twoDifferentParametersFunction.flipWith(pair)
}

fun twoDifferentParametersFunction(p1: Int, p2: String): String = "$p1$p2"
```

the above code is the same as :

```kotlin
fun main(args: Array<String>) {
    val pair = Pair("-",1)
    val result = ::twoDifferentParametersFunction flipWith pair  // infixing
}

fun twoDifferentParametersFunction(p1: Int, p2: String): String = "$p1$p2"
```

## Notes

Note that most of the extension functions in this library are infixed, which you can write your code as follows :

```kotlin
fun main(args: Array<String>) {
    val resultOne = ::fourParametersFunction with 1 with 2 with 3 with 4

    //also the same code can be :

    val curried = ::fourParametersFunction with 1 with 2 with 3
    val resultTwo = curried(4)
}

fun fourParametersFunction(p1: Int, p2: Int, p3: Int, p4: Int): Int = p1 + p2 + p3 + p4
```


# J-Curry for Java

## Curry.toConsumer(), Curry.toFunction(), Curry.toBiFunction(), Curry.toPredicate(), Curry.toAction(), Curry.toCallable()

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

## RxConsumer, RxFunction, RxPredicate, RxAction, RxCallable

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

## More Functional interfaces that enable currying and partial application by default

Also there are interfaces like <b>RxBiFunction, RxFunction3, RxFunction4, RxBiConsumer, RxConsumer3, RxConsumer4, RxBiPredicate, RxPredicate3, RxPredicate4</b> ... those functions enables currying and partial applying by default, as they follow the deep concepts of functional programming, where functions should have one and only parameter, so each parameter passed to those functions, returns another function awaiting for the next parameter, and so on ... using lambdas and method references is highly recommended when implementing these interfaces ... and example is as follows :

```java

// RxJava2 used here :
String concatenateOneAndTwo() {
    return Single.just(2)
            .map(concatenateTwoNumbers().apply(1))
            .blockingGet();
}

RxBiFunction<Integer, Integer, String> concatenateTwoNumbers() {
    return intOne -> intTwo -> intOne + " and " + intTwo;
}

```

As you can see. when we used concatenateTwoNumbers() and passed it's first Integer parameter, it returned another function that takes an Integer and returns a String ... so we passed the this new function to the map() operator, since it is waiting for a function that takes an Integer and returns a String as well (same signature)

## Usage with RxJava2 Operators in Java 7 :

The greatest benefit from such library is to use with RxJava2 operators, since it uses the RxJava interfaces (Consumer, Function, Predicate).

examples from CurryTest.java in version 0.0.1 :

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

## Usage with RxJava2 Operators through lambdas and method references in Java 8 (or using Retrolambda in Java 7) :

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

## SwapCurry ... where the fun begins (<b>flip</b> in Haskell)
after version 0.0.3, it is possible to swap the parameters of the curried function, so we can pass the second parameter of the method first, and the curried function will return another function that expects to receive the first parameter of the original method, then it executes, like in the following example :

```java
Observable.fromArray("%d","%02d","%04d")
            .forEach(SwapCurry.toConsumer(System.out::printf,10));
```

the System.out.printf() takes a "String" as it's first parameter, and an "Integer" as it's second parameter, what was done here is that, we passed the "Integer" first in the SwapCurry.toConsumer() method, and we receieved the "String" later from the Observable.forEach() method.

## Tuples and Entries

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

With RxJava2 Streams, we have higher order functions that takes a BiFunction or BiConsumer and returns a Function that takes the <b>Pair</b> or <b>Map.Entry</b> and pass it as parameters to the BiFunction or BiConsumer, for example :

```java
public int sumMatrix(Map<Integer, Integer> matrix){
    return Observable.fromIterable(matrix.entrySet())
            // first convert every map entry to the sum of it's
            // key and value
            .map(entry -> Entries.withBiFunction(this::sum, entry))
            // then sum all the results
            .reduce(this::sum)
            // then get final result
            .blockingGet();
}
```

The above code is the same as :

```java
public int sumValuesInMap(Map<Integer, Integer> matrix) {
    return Observable.fromIterable(matrix.entrySet())
            // first convert every map entry to the sum of it's
            // key and value
            .map(Entries.toFunction(this::sum))
            // then sum all the results
            .reduce(this::sum)
            // then get final result
            .blockingGet();
}
```

## Types

The library now supports Functional types, each type has it's functional APIs like <b>map()</b>, <b>flatMap()</b>, etc...

<b>Either</b> : A type that will either hold a correct value (right) or an error value (left) ... the main point for <b>Either</b> is to delegate handling errors to the callers, so they are in control of what happens on error (inversion of control)

```java

Either<ClassCastException, String> castedIntToString() {
    return castInteger("test")
            .mapRight(String::valueOf)
            .mapRight(text -> text + " --- ");
}


Either<ClassCastException, Integer> castInteger(Object intValue) {
    try {
        return Either.withRight((int) intValue);
    } catch (ClassCastException e) {
        return Either.withLeft(e);
    }
}

```


<b>Try</b> : A type that will take a <b>Callable</b>, and will hold the value of it's execution or it's exception ... so instead of using try/catch blocks, you can make a your methods return the Try Object, and let the control for who called the method ... the main point of <b>Try</b> is to handle exceptions in a Functional fashion, without try/catch blocks, so we can invoke multiple operations on the values and if it crashed, the operations are ignored, and we can notice weather there is a success or failure at the end

```java

void castInteger(Object intValue) {
    Try.with(() -> (int) intValue)
            .map(integer -> integer * 100)
            .flatMap(Single::just, Single::error)  // convert to RxJava Single
            .subscribe(System.out::println,Throwable::printStackTrace);
}

```


# Adding gradle dependency

Step 1. Add it in your root build.gradle at the end of repositories:

```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
  
Step 2. Add the dependency

For Java :

```gradle
dependencies {
    compile 'com.github.Ahmed-Adel-Ismail.J-Curry:currying:2.0.0'
}
```

For Kotlin :

```gradle
dependencies {
    compile 'com.github.Ahmed-Adel-Ismail.J-Curry:kotlin:2.0.0'
}
```
