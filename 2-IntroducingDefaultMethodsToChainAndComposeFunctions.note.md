# 2. Introducing Default Methods to Chain and Compose Functions

## Chaining Consumers (with default methods)

### `src/function/Consumer.java`
```java
package function;

import java.util.Objects;

@FunctionalInterface
public interface Consumer<T> {
    void accept(T t);

    default Consumer<T> andThen(Consumer<T> that) { // use "default" method to chain lambdas
        return (T t) -> {
            Objects.requireNonNull(that); // fail fast
            this.accept(t);
            that.accept(t);
        };
    }
}
```

### `src/PlayWithConsumers.java`
```java
import function.Consumer;

public class PlayWithConsumers {
    public static void main(String[] args) {
        Consumer<String> c1 = s -> System.out.println("c1 =" + s);
        Consumer<String> c2 = s -> System.out.println("c2 =" + s);
        Consumer<String> c3 = s -> c1.andThen(c2); // chaining
        c3.accept("Hello");
    }
} 
```

### `OUTPUT`
```text
c1 = Hello
c2 = Hello
```

## Combining and Negating Predicates

### `src/function/Predicate.java`
```java
package function;

import java.util.Objects;

@FunctionalInterface
public interface Predicate<T> {
    boolean test(T t);

    default Predicate<T> and(Predicate<T> that) {
        Objects.requireNonNull(that);
        return (T t) -> this.test(t) && that.test(t);
    }
    
    default Predicate<T> negate() {
        return (T t) -> !this.test(t);
    }
}
```

### `src/PlayWithPredicates.java`
```java
import function.Predicate;

public class PlayWithPredicates {
    public static void main(String[] args) {
        Predicate<String> p1 = s -> s != null;
        Predicate<String> p2 = s -> s.isEmpty();
        Predicate<String> notP2 = p2.negate();
        Predicate<String> p3 = p1.and(notP2); // not null and not empty

        System.out.println("p3.test(null) = " + p3.test(null));
        System.out.println("p3.test(\"\") = " + p3.test(""));
        System.out.println("p3.test(\"Hello\") = " + p3.test("Hello"));
        System.out.println();
    }
}
```

### `OUTPUT`
```text
p3.test(null) = false
p3.test("") = false
p3.test("Hello") = true
```

## Chaining and Composing Functions

### `src/function/Predicate.java`

```java
package function;

import java.util.Objects;

@FunctionalInterface
public interface Function<T, R> {
    R apply(T t);

    default <V> Function<T, V> andThen(Function<R, V> that) { // needs another type V
        Objects.requireNonNull(that);
        return (T t) -> {
            R r = this.apply(t);
            return that.apply(r);
        };
    }
    
    default <V> Function<V, R> compose(Function<V, T> that) {
        Objects.requireNonNull(that);
        return (V v) -> {
            T t = that.apply(v);
            return this.apply(t);
        };
    }
    
    static <T> Function<T, T> identity() {
        return t -> t;
    }
}
```

### `src/model/Meteo.java`
```java
package model;

public class Meteo {
    private int temperature;

    public Meteo(int temperature) {
        this.temperature = temperature;
    }

    public int getTemperature() {
        return temperature;
    }
}
```

### `src/PlayWithFunctions.java`

```java
import function.Function;
import model.Meteo;

import java.util.function.Function;

public class PlayWithFunctions {
    public static void main(String[] args) {
        Meteo meteo = new Meteo(10);

        Function<Meteo, Integer> readCelsius = m -> m.getTemperature();
        Function<Integer, Double> celsiusToFahrenheit = t -> t * 9d / 5d + 32d;
        Function<Meteo, Double> readFahrenheit = readCelsius.andThen(celsiusToFahrenheit); // chaining

        System.out.println("readFahrenheit.apply(meteo) = " + readFahrenheit.apply(meteo));

        readFahrenheit = celsiusToFahrenheit.composing(readCelsius); // function composition

        System.out.println("readFahrenheit.apply(meteo) = " + readFahrenheit.apply(meteo));

        Function<String, String> identity = Function.identity(); // identity function (factory method)
    }
}
```

- function composition works the opposite way than function chaining
  - with chaining, you define the order that functions are applied
  - with composition, you apply a function to result of the previous one
  - only possible with the Function interface
- identity function returns the same argument it was given

### `OUTPUT`
```text
readFahrenheit.apply(meteo) = 50.0
readFahrenheit.apply(meteo) = 50.0
```



