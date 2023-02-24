# 6. Using Partial Application and Lambdas to Build Validators

- there is no validator patter in the GoF
- so many apps need one
- a Validator is an object? (it's not, it's a lambda expression)
- to perform validation in a certain way
- let's make it dynamic w/ lambdas

## How Does a Validator Work?

- suppose you need to validate a form
- a validator validates all the fields that need to be validated
- and then produce a set of exceptions
- w/ a message for each faulty field
- then throws a single "model" exception
- wrapping all the other ones

## Validating a Person Bean

```java
public class Person {
    private String name;    // 1. name should not be null
    private int age;        // 2. age should be > 0
                            // 3. age should be < 150
    
    // getters and setters
}
```