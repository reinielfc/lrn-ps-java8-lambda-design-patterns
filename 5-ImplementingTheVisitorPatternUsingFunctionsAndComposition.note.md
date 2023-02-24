# 5. Implementing The Visitor Pattern Using Functions and Composition

## What Does the Visitor Pattern Do?

- an operation to be performed on the elements of an object structure
- let you define a new operation w/o changing the classes of the elements on which it operates
- your classes do have to be prepared to receive this visitor

take an existing set of classes

- maybe in hierarchy
- and add operations on those classes
- w/o modifying them
- all these classes need to do is to expose an accept(Visitor) method


## How does the Visitor Pattern Work?

```java
public class Car { ... }
public class Body { ... }
public class Wheel { ... }
public class Engine { ... }
```

```java
public class Car {
    Engine engine = ...;
    Body body = ...;
    Wheel wheel1 = ...;
    
    void accept(Visitor visitor) {
        engine.accept(visitor);
        body.accept(visitor);
        wheel1.accept(visitor);
        
        visitor.visit(this);
    } 
}
```

```java
public interface Visitor {
    void visit(Car car);
    void visit(Engine engine);
    void visit(Body body);
    void visit(Wheel wheel);
}
```

```java
public class Engine {
    void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
```

to implement a new operation

- you just need to implement a visitor
- you still need those accept() methods
- what to do if you do not have them?

with lambdas, you can define operations

- on classes
- without adding them to the class
- a lambda is a method without
- that you can pass as a parameter
- or record in a registry

the API implementation details are very complex
