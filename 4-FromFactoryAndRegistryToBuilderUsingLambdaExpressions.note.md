# 4. From Factory and Registry to Builder Using Lambda Expressions

## What is a Factory?

- an object able to create other objects
- can be modeled by a Supplier (functional interface)

## Creating Registries Using Builders

- registry has the same role as a factory, can also build other objects
- the difference is that it works with key
  - you pass a key to a factory method
  - the method will then create the right instance of the object you need
- adding elements dynamically to a registry
  - can be achieved with a **Builder Pattern**
    1. add elements to the registry
    2. build the registry and **seal** it (no more elements are allowed)
  - e.g. `Stream.Builder`

 ```java
import java.util.stream.Stream;

class Main {
    public static void main(String[] args) {
        Stream.Builder<String> builder = Stream.builder();
        Stream<String> stream = builder
                .add("one")
                .add("two")
                .add("three")
                .build(); // sealed
        
        stream.forEach(System.out::println);
    }
}
```

- problems with this
  - 2 phases
    - builder is still available and 
    - if somebody can get a reference to it and use it, 
    - an exception will be thrown
  - the builder has to know the factory

In fact the factory needs to know the builder (but not the contrary)

```java
import java.util.function.Supplier;

public class Builder<T> {
  public void add(String label, Supplier<T> supplier) { // the builder can be made independent of the factory
      // ...
  }
}
```

```java
public class Registry<T> {
    public T createFactory(String label) {}
    
    public static <T> Registry<T> build(Builder<T> builder) {} // this factory is created using this factory method
}
```

## Wrap Up

- the factory / builder / registry elements
- can be modeled w/ functional elements
- implemented using lambdas
- very well hidden references
