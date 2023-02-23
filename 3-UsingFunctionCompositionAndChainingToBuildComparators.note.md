# 3. Using Function Composition and Chaining to Build Comparators

`src/util/Comparator.java`
```java
package util;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface Comparator<T> {
    int compare(T t1, T t2);

    default <U extends Comparable<U>> Comparator<T> thenComparing(Function<T, U> keyExtractor) {
        Objects.requireNonNull(keyExtractor);
        Comparator<T> that = comparing(keyExtractor);
        return this.thenComparing(that);
    }

    default Comparator<T> thenComparing(Comparator<T> that) {
        Objects.requireNonNull(that);
        return (T t1, T t2) -> {
            int compare = this.compare(t1, t2);
            return compare == 0 ? that.compare(t1, t2) : compare;
        };
    }

    default Comparator<T> reversed() {
        return (t1, t2) -> this.compare(t2, t1);
    }

    //static <T> comparing(Function<T, String> keyExtractor) {
    //static <T, U extends Comparable<U>> comparing(Function<T, U> keyExtractor) {
    static <T, U extends Comparable<? super U>> comparing(Function<T, U> keyExtractor) {
        Objects.requireNonNull(keyExtractor);
        return (t1, t2) -> {
            U u1 = keyExtractor.apply(t1);
            U u2 = keyExtractor.apply(t2);
            return s1.compareTo(s2);
        };
    }


}
```

`src/model/Person.java`
```java
package model;

public class Person {
    private String name;
    private int age;
    
    // all-args constructor, getters, setter & toString  
}
```

`src/PlayWithComparators.java`
```java
import model.Person;
import util.Comparator;

public class PlayWithComparators {
    public static void main(String[] args) {
        Person mary = new Person("Mary", 28);
        Person john = new Person("John", 22);
        Person linda = new Person("Linda", 26);
        Person james = new Person("James", 32);

        //Comparator<Person> cmpName = (p1, p2) -> {
        //    String name1 = p1.getName();
        //    String name2 = p2.getName();
        //  Person john = new    return name1.cogetPersonComparatormpareTo(name2);
        //};

        Function<Person, String> getName = p -> p.getName();
        Comparator<Person> cmpName = Comparator.comparing(getName);

        System.out.println("mary > john :" + (cmpName.compare(mary, john) > 0));
        System.out.println("john > james :" + (cmpName.compare(john, james) > 0));
        System.out.println("linda > john :" + (cmpName.compare(linda, john) > 0));
        System.out.println();

        Comparator<Person> cmpNameReversed = cmpName.reversed();
        System.out.println("linda > john :" + (cmpNameReversed.compare(linda, john) > 0));
        System.out.println();
        
        Comparator<Person> cmpAge = Comparator.comparing(Person::getAge);
        Comparator<Person> cmpNameThenAge = cmpName.thenComparing(cmpAge); // composition
        
        Person james2 = new Person("James", 26);
        System.out.println("james > james2 :" + (cmpName.compare(james, james2) > 0));
        
        cmpNameThenAge = Comparator // chaining
                .comparing(Person::getName)
                .thenComparing(Person::getAge);        
    }
}
```

`OUTPUT`
```text
mary > john : true
john > james : true
linda > john : true

linda > john : false

james > james2 : true
```