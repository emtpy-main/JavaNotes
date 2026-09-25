Your notes cover the major interface concepts, but there are some **important corrections**: interface methods are not all necessarily public/abstract anymore, `Comparable` is not a functional interface, `RandomAccess` is a marker interface but has a different purpose, and the diamond-resolution example needs correction.

Here is the enhanced, interview-ready version.

# 🔌 Interfaces in Java

## 1. Overview

An **interface** defines a contract that specifies **what a class can do**, without necessarily specifying how the behavior is implemented.

Think of an interface as a **contract** between the interface and the implementing class.

```text
Interface
   ↓
Defines WHAT should be done
   ↓
Implementing class
   ↓
Defines HOW it should be done
```

For example:

```java id="h3c6k7"
interface Flyable {
    void fly();
}
```

Any class implementing `Flyable` must provide an implementation of `fly()`:

```java id="v8d9xu"
class Bird implements Flyable {

    @Override
    public void fly() {
        System.out.println("Bird is flying");
    }
}
```

---

# 2. Definition

> **An interface is a reference type in Java that defines a contract of behavior that implementing classes agree to provide.**

Interfaces are heavily used for:

* abstraction
* loose coupling
* polymorphism
* multiple inheritance of type
* dependency inversion
* API design
* functional programming

---

# 3. Basic Syntax

```java id="0y2d8w"
interface Vehicle {

    void drive();
}
```

Implementation:

```java id="qj8h6p"
class Car implements Vehicle {

    @Override
    public void drive() {
        System.out.println("Car is driving");
    }
}
```

Usage:

```java id="5kcr3z"
Vehicle v = new Car();

v.drive();
```

This is **polymorphism**.

```text id="gj1gq3"
Vehicle reference
       ↓
    Car object
       ↓
  drive() implementation
```

---

# 4. Interface as a Contract

Suppose:

```java id="akl4d9"
interface Payment {

    void pay(double amount);
}
```

We don't specify how payment happens.

Different classes can implement it differently:

```java id="7lqg6s"
class CreditCardPayment implements Payment {

    @Override
    public void pay(double amount) {
        System.out.println("Paid using credit card");
    }
}
```

```java id="z8b0h2"
class UpiPayment implements Payment {

    @Override
    public void pay(double amount) {
        System.out.println("Paid using UPI");
    }
}
```

The interface says:

```text id="g2l6k5"
Payment
   │
   └── pay()
       ↓
"Every payment method must support pay()"
```

but each implementation decides **how** to perform it.

---

# 5. Interface Variables

Variables declared inside an interface are implicitly:

```java id="v5d7k8"
public static final
```

Example:

```java id="v0plu6"
interface MathConstants {

    double PI_VALUE = 3.14;
}
```

Conceptually:

```java id="k9i7lo"
interface MathConstants {

    public static final double PI_VALUE = 3.14;
}
```

Therefore:

```java id="2z9c8r"
MathConstants.PI_VALUE
```

can be accessed directly.

You cannot do:

```java id="9r3m4w"
MathConstants.PI_VALUE = 4.0; // ❌
```

because it is `final`.

### ⭐ Remember

> **Every interface field is implicitly `public static final`.**

---

# 6. Interface Methods — Important Modern Rule

Your note says:

> Methods inside interface are by default abstract and public.

This is **true only for ordinary abstract interface methods**.

Example:

```java id="t3s7v4"
interface Math {

    int add(int a, int b);
}
```

is equivalent to:

```java id="6m2s5k"
interface Math {

    public abstract int add(int a, int b);
}
```

But since Java 8+, interfaces can also contain:

* `default` methods
* `static` methods

And since Java 9:

* `private` methods

So don't memorize:

> "All interface methods are public abstract."

Instead remember:

> **An interface method without a body is implicitly `public abstract`, but interfaces can also contain `default`, `static`, and `private` methods.**

---

# 7. Implementing an Interface

A class uses:

```java id="1j4vqw"
implements
```

to implement an interface.

```java id="t8i2k5"
interface A {
    void fun();
}

class B implements A {

    @Override
    public void fun() {
        System.out.println("Implementation");
    }
}
```

### Important

The implemented method must be at least as accessible as the interface method.

Since:

```java id="2h6y9w"
A.fun()
```

is public, you cannot implement it as:

```java id="e5q1lm"
void fun() { } // ❌
```

You need:

```java id="y8b3zx"
public void fun() { } // ✅
```

---

# 8. Interface Inheritance

An interface can extend another interface using:

```java id="3xq8c4"
extends
```

Example:

```java id="n1u7kh"
interface A {
    void fun();
}

interface B extends A {
    void fun2();
}
```

Now `B` inherits the contract of `A`.

```text id="o7b1w4"
A
│
└── fun()
     ↓
B
├── fun()
└── fun2()
```

A class implementing `B` must satisfy both contracts:

```java id="4r6c9d"
class C implements B {

    @Override
    public void fun() {
    }

    @Override
    public void fun2() {
    }
}
```

---

# 9. Interface Can Extend Multiple Interfaces

This is one of the major differences from classes.

An interface can extend multiple interfaces:

```java id="9i6t2j"
interface A {
    void funA();
}

interface B {
    void funB();
}

interface C extends A, B {
    void funC();
}
```

Therefore:

```text id="u7h5qk"
A       B
 \     /
   \ /
    C
```

`C` inherits the contracts of both `A` and `B`.

---

# 10. Multiple Inheritance Through Interfaces

Java does not allow:

```java id="6t1c3m"
class C extends A, B {  // ❌
}
```

because multiple class inheritance creates ambiguity.

But Java allows:

```java id="2w7s8x"
class C implements A, B {
}
```

Therefore, interfaces provide **multiple inheritance of type/contracts**.

Example:

```java id="r4q8v1"
interface Walkable {
    void walk();
}

interface Runnable {
    void run();
}

class Person implements Walkable, Runnable {

    @Override
    public void walk() {
        System.out.println("Walking");
    }

    @Override
    public void run() {
        System.out.println("Running");
    }
}
```

---

# 11. Interface Reference and Dynamic Dispatch

Interfaces support runtime polymorphism.

```java id="3k9p5x"
interface Animal {
    void sound();
}

class Dog implements Animal {

    @Override
    public void sound() {
        System.out.println("Bark");
    }
}

class Cat implements Animal {

    @Override
    public void sound() {
        System.out.println("Meow");
    }
}
```

Now:

```java id="6u1h8s"
Animal a;

a = new Dog();
a.sound();
```

Output:

```text id="b0d8n7"
Bark
```

Then:

```java id="2j5g9v"
a = new Cat();
a.sound();
```

Output:

```text id="z1k5w3"
Meow
```

The method implementation is selected according to the **runtime object**.

```text id="k5d8p0"
Animal reference
      │
      ├── Dog object → Dog.sound()
      │
      └── Cat object → Cat.sound()
```

This is **dynamic method dispatch/runtime polymorphism**.

---

# 12. Default Methods — Java 8

Before Java 8, interface methods were essentially contracts without implementations, apart from special cases like static methods introduced later.

Suppose an interface was already being implemented by hundreds of classes:

```java id="v6c2a8"
interface Vehicle {
    void drive();
}
```

Now Java designers want to add a new method:

```java id="x9f5q1"
void brake();
```

If it were abstract, every existing implementation would potentially need to implement the new method.

This could break existing code.

Therefore Java 8 introduced **default methods**.

```java id="3v4x7m"
interface Vehicle {

    void drive();

    default void brake() {
        System.out.println("Vehicle is braking");
    }
}
```

A class can use the default implementation without overriding it.

---

# 13. Why Were Default Methods Introduced?

⭐ **Interview question**

The major reason was **interface evolution/backward compatibility**.

Java needed a way to add new behavior to existing interfaces without forcing every existing implementing class to immediately provide an implementation.

Examples from the Java API include methods added to interfaces such as `Collection` and `List` over time.

---

# 14. Static Methods in Interfaces

Interfaces can have static methods.

```java id="m7r3p8"
interface Vehicle {

    static void brake() {
        System.out.println("Vehicle is braking");
    }
}
```

Call it using the interface name:

```java id="z8x2c4"
Vehicle.brake();
```

### Important

You cannot call an interface static method through an implementing object:

```java id="w5n8j2"
Vehicle v = new Car();

v.brake(); // ❌
```

Use:

```java id="c6v4m9"
Vehicle.brake(); // ✅
```

---

# 15. Private Methods in Interfaces

Java 9 introduced private interface methods.

```java id="r4m7p2"
interface Vehicle {

    default void drive() {
        helper();
        System.out.println("Driving");
    }

    private void helper() {
        System.out.println("Common logic");
    }
}
```

A private interface method:

* can be called from methods inside the same interface
* cannot be called by implementing classes
* cannot be inherited by implementing classes

### Why useful?

To avoid duplicating common logic among multiple default methods.

---

# 16. Interface Method Summary

| Method type       |  Since | Can have body? | Accessible from           |
| ----------------- | -----: | -------------- | ------------------------- |
| `public abstract` | Java 1 | ❌              | Implementing class        |
| `default`         | Java 8 | ✅              | Implementing class/object |
| `static`          | Java 8 | ✅              | Interface itself          |
| `private`         | Java 9 | ✅              | Interface itself          |

⭐ This table is very useful for interviews.

---

# 17. Diamond Problem

The **diamond problem** occurs when a class inherits the same method through multiple paths and the language cannot determine which implementation should be used.

Consider:

```text id="5c7w2n"
        A
       / \
      B   C
       \ /
        D
```

Suppose:

```java id="v7j3q9"
interface A {
    default void fun() {
        System.out.println("A");
    }
}
```

Both `B` and `C` inherit `fun()`:

```java id="j8r1s4"
interface B extends A {
}
```

```java id="s6k4y2"
interface C extends A {
}
```

Now:

```java id="m3p8v5"
class D implements B, C {
}
```

There is ambiguity about which inherited implementation should be used if `B` and `C` provide conflicting defaults.

---

# 18. How Does Java Resolve Default-Method Conflicts?

Java has explicit rules.

### Rule 1: Class wins over interface

If a superclass provides a concrete method, it takes priority over a default interface method.

Example:

```java id="h7d2k9"
interface A {

    default void fun() {
        System.out.println("A");
    }
}

class B {

    public void fun() {
        System.out.println("B");
    }
}

class C extends B implements A {
}
```

Calling:

```java id="g5w8m1"
new C().fun();
```

prints:

```text id="j9k3p6"
B
```

The class implementation wins.

### ⭐ Priority

```text id="v4n7c2"
Class method
    ↓
wins over
    ↓
Interface default method
```

---

# 19. Rule 2: More Specific Interface Wins

Suppose:

```java id="6u8k3p"
interface A {
    default void fun() {
        System.out.println("A");
    }
}

interface B extends A {
    @Override
    default void fun() {
        System.out.println("B");
    }
}

class C implements B {
}
```

`B` is more specific than `A`, so:

```java id="5j9m4x"
new C().fun();
```

prints:

```text id="k3p7v1"
B
```

---

# 20. Rule 3: Explicit Resolution

If two unrelated interfaces provide conflicting default methods:

```java id="c4m8y2"
interface A {
    default void fun() {
        System.out.println("A");
    }
}

interface B {
    default void fun() {
        System.out.println("B");
    }
}
```

Then:

```java id="p7r3x9"
class C implements A, B {
}
```

produces a compile-time error because Java cannot automatically choose.

The class must resolve the conflict:

```java id="n5q8v2"
class C implements A, B {

    @Override
    public void fun() {
        A.super.fun();
    }
}
```

Or choose its own implementation:

```java id="y3k6m8"
@Override
public void fun() {
    System.out.println("C");
}
```

---

# 21. `InterfaceName.super.method()`

This syntax is used to explicitly select a default implementation from a direct superinterface.

```java id="8w4p2n"
A.super.fun();
```

Example:

```java id="q5m9r3"
interface A {
    default void fun() {
        System.out.println("A");
    }
}

interface B {
    default void fun() {
        System.out.println("B");
    }
}

class C implements A, B {

    @Override
    public void fun() {
        A.super.fun();
    }
}
```

Output:

```text id="s7c1x4"
A
```

### ⚠️ Important correction to your note

You had:

```java id="z2x7m5"
B.super.fun();
C.super.fun();
```

inside `C` itself.

That's not the correct pattern.

You use:

```java
A.super.fun();
```

to invoke a default method from a **direct superinterface** of the current class/interface context.

---

# 22. Interface vs Abstract Class

This is one of the most important Java interview comparisons.

| Feature                              | Interface                                 | Abstract Class                    |
| ------------------------------------ | ----------------------------------------- | --------------------------------- |
| Main purpose                         | Contract/capability                       | Common base for related classes   |
| Relationship                         | "Can-do"                                  | "Is-a"                            |
| Multiple inheritance                 | A class can implement multiple interfaces | A class can extend only one class |
| Instance fields                      | ❌                                         | ✅                                 |
| Interface constants                  | `public static final`                     | Any valid field                   |
| Constructor                          | ❌                                         | ✅                                 |
| Abstract methods                     | ✅                                         | ✅                                 |
| Default methods                      | ✅                                         | N/A                               |
| Static methods                       | ✅                                         | ✅                                 |
| Private methods                      | ✅                                         | ✅                                 |
| Instance methods with implementation | Through default methods                   | ✅                                 |
| Object state                         | Generally not instance state              | Can maintain instance state       |

---

# 23. Interface = Capability / Contract

Think:

```text id="j7x4m2"
Bird
 ├── Flyable
 └── Walkable

Human
 ├── Walkable
 └── Runnable
```

`Flyable` does not mean:

> "This class belongs to the Flyable family."

It means:

> "This class has the capability/contract to fly."

---

# 24. Abstract Class = Common Family/Base

For example:

```java id="v8q3m1"
abstract class Animal {

    String name;

    Animal(String name) {
        this.name = name;
    }

    abstract void sound();

    void eat() {
        System.out.println("Eating");
    }
}
```

Then:

```java id="h5c9k2"
class Dog extends Animal {
}
```

`Dog` **is an** `Animal`.

So:

```text id="e6m2q7"
Animal
  │
  ├── Dog
  ├── Cat
  └── Elephant
```

---

# 25. Multiple Interfaces

A class can implement multiple interfaces:

```java id="b7p4w9"
interface Flyable {
    void fly();
}

interface Swimmable {
    void swim();
}

class Duck implements Flyable, Swimmable {

    @Override
    public void fly() {
        System.out.println("Flying");
    }

    @Override
    public void swim() {
        System.out.println("Swimming");
    }
}
```

This gives:

```text id="m2k8r5"
          Duck
         /    \
    Flyable   Swimmable
```

This is one reason interfaces are important in Java's design.

---

# 26. Functional Interface

A **functional interface** is an interface that has **exactly one abstract method**.

Example:

```java id="r8q3m1"
@FunctionalInterface
interface Calculator {
    int add(int a, int b);
}
```

It can be used with a lambda:

```java id="x4n7p2"
Calculator c =
    (a, b) -> a + b;
```

### Important correction

Your note mentions:

> `Comparable, Predicate`

Both are functional interfaces.

Examples:

```text id="q6m2v8"
Comparable<T>  → compareTo(T)
Predicate<T>   → test(T)
```

But don't say a functional interface has "only one method."

More accurately:

> **A functional interface has exactly one abstract method.**

It can still have:

* default methods
* static methods
* private methods
* methods inherited from `Object`

and still be functional.

---

# 27. `@FunctionalInterface`

Use:

```java id="z4k8p1"
@FunctionalInterface
interface Calculator {
    int add(int a, int b);
}
```

The annotation tells the compiler that the interface is intended to be functional.

If you accidentally add another abstract method:

```java id="y7m3q5"
@FunctionalInterface
interface Calculator {

    int add(int a, int b);

    int subtract(int a, int b); // ❌
}
```

the compiler reports an error.

---

# 28. Marker Interface

A **marker interface** is an interface that has **no methods** and is used to indicate some special property/capability.

Example:

```java id="x9q4m7"
interface MyMarker {
}
```

Common Java examples include:

```text id="u5c8n2"
Serializable
Cloneable
RandomAccess
```

### `Serializable`

Marks a class as eligible for Java's serialization mechanism.

### `Cloneable`

Indicates that the class permits cloning through the `Object.clone()` mechanism.

### `RandomAccess`

Marks list implementations that support efficient indexed/random access.

> ⭐ `RandomAccess` does not mean "random behavior." It is a performance-related marker.

---

# 29. Functional vs Marker Interface

| Feature                | Functional Interface          | Marker Interface           |
| ---------------------- | ----------------------------- | -------------------------- |
| Abstract methods       | Exactly 1                     | 0                          |
| Main purpose           | Lambda/functional programming | Mark a capability/property |
| Example                | `Predicate`                   | `Serializable`             |
| `@FunctionalInterface` | Usually used                  | ❌                          |
| Lambda compatible      | ✅                             | ❌                          |

---

# 30. Interface and Lambda

Functional interfaces are closely connected to lambda expressions.

```java id="t5m8q2"
@FunctionalInterface
interface Greeting {
    void sayHello(String name);
}
```

Lambda:

```java id="r2k7v9"
Greeting g =
    name -> System.out.println("Hello " + name);
```

Calling:

```java id="m8q4x1"
g.sayHello("Pratik");
```

produces:

```text id="h5v9c3"
Hello Pratik
```

---

# 31. Can an Interface Have a Constructor?

❌ No.

An interface does not represent an object instance in the same way a class does, so it does not have constructors.

```java id="k7x2m4"
interface A {

    A() { } // ❌
}
```

A class implementing the interface has its own constructor.

---

# 32. Can We Create an Object of an Interface?

You cannot directly instantiate an interface:

```java id="n4q8w2"
Animal a = new Animal(); // ❌
```

But you can have an interface reference pointing to an implementing object:

```java id="p6m3x9"
Animal a = new Dog(); // ✅
```

This is interface-based polymorphism.

---

# 33. Anonymous Class and Interface

Although you cannot instantiate an interface directly, you can create an **anonymous class** implementing it:

```java id="c9w5k2"
Animal a = new Animal() {

    @Override
    public void sound() {
        System.out.println("Anonymous implementation");
    }
};
```

Here:

```text id="q3m7v8"
Animal
   ↓
anonymous implementation
   ↓
object
```

This is different from directly instantiating the interface.

---

# 34. Interface Internal Representation

Your note says:

> "Internally, interface is class itself with ACC_INTERFACE tag."

This is a **JVM/bytecode-level concept**.

At the JVM class-file level, an interface is represented using a class-file structure with the `ACC_INTERFACE` flag (along with appropriate related flags).

But conceptually at the Java language level:

> **An interface is not a class. It is a distinct reference type.**

For your interview notes, keep both levels separate:

```text id="p1x7m4"
Java source level:
Interface ≠ Class

JVM class-file level:
Interface uses class-file format
and ACC_INTERFACE flag
```

---

# 35. Interface Evolution

A useful historical timeline:

```text id="e7q2n5"
Java 1
   ↓
Interfaces with abstract methods + constants

Java 8
   ↓
default methods
static methods

Java 9
   ↓
private methods
```

This is useful for understanding **why modern interfaces can contain implementation**.

---

# 🧠 Complete Interface Revision

```text id="a4m9q2"
                    INTERFACE
                        │
                        ↓
                     Contract
                        │
             ┌──────────┴──────────┐
             ↓                     ↓
        What to do             Type safety
             │
             ↓
      class implements
             │
             ↓
       How to do it
```

### Members

```text id="w8k3p6"
Interface Fields
      ↓
public static final

Methods
      ↓
abstract   → Java 1
default    → Java 8
static     → Java 8
private    → Java 9
```

### Inheritance

```text id="y6m2q8"
Interface extends Interface
          ↓
Can extend multiple interfaces

Class implements Interface
          ↓
Can implement multiple interfaces
```

### Important types

```text id="r3v7k1"
Functional Interface
      ↓
Exactly ONE abstract method
      ↓
Lambda compatible

Marker Interface
      ↓
ZERO methods
      ↓
Marks capability/property
```

---

# 🎯 Important Interview Questions

## 🟢 Basic

### 1. What is an interface?

**Answer:**
An interface is a reference type in Java that defines a contract of behavior that implementing classes agree to provide. It is commonly used for abstraction, loose coupling, polymorphism, and defining capabilities.

---

### 2. Why do we use interfaces?

**Answer:**
Interfaces allow us to define common contracts, achieve loose coupling, support runtime polymorphism, and allow a class to implement multiple types.

---

### 3. Can we create an object of an interface?

**Answer:**
No, an interface cannot be directly instantiated. However, an interface reference can refer to an object of a class that implements the interface.

```java id="g4m8x2"
Animal a = new Dog();
```

---

### 4. Can an interface have variables?

**Answer:**
Yes. Fields declared in an interface are implicitly `public static final`, so they represent constants.

---

### 5. Can an interface have a constructor?

**Answer:**
No. Interfaces do not have constructors because they cannot be directly instantiated.

---

# 🟡 Intermediate

### 6. Can an interface extend another interface?

**Answer:**
Yes. An interface can extend one or multiple interfaces using `extends`.

```java id="h6q2v9"
interface C extends A, B {
}
```

---

### 7. Can a class implement multiple interfaces?

**Answer:**
Yes. A class can implement multiple interfaces.

```java id="m9x4p7"
class C implements A, B, D {
}
```

This provides multiple inheritance of type/contracts.

---

### 8. What is a default method?

**Answer:**
A default method is an interface method with an implementation, introduced in Java 8. It allows interfaces to evolve by adding behavior without requiring every existing implementing class to immediately implement the new method.

---

### 9. Can interfaces have static methods?

**Answer:**
Yes. Since Java 8, interfaces can contain static methods. They are invoked using the interface name.

```java id="n5w8q3"
Vehicle.brake();
```

---

### 10. Can interfaces have private methods?

**Answer:**
Yes. Since Java 9, interfaces can have private methods. These methods are used internally by the interface, commonly to share implementation between default or static methods.

---

# 🔴 SDE Interview Questions

### 11. What is the difference between interface and abstract class?

**Answer:**
An interface primarily defines a contract or capability, while an abstract class is generally used as a common base for closely related classes and can maintain instance state. A class can implement multiple interfaces but can extend only one class.

---

### 12. Why were default methods introduced?

**Answer:**
Default methods were introduced in Java 8 mainly to allow existing interfaces to evolve by adding new behavior without forcing all existing implementing classes to provide implementations immediately.

---

### 13. What is the diamond problem in Java?

**Answer:**
The diamond problem occurs when a class receives conflicting default implementations of the same method through multiple interfaces. Java resolves this using rules such as class methods taking priority, more specific interface methods taking priority, and requiring the implementing class to explicitly resolve remaining conflicts.

---

### 14. What happens if a class and interface have methods with the same signature?

**Answer:**
If the class has a concrete method, the class method takes priority over the interface's default method.

```java id="r8m2x5"
class B {
    public void fun() {
        System.out.println("B");
    }
}

interface A {
    default void fun() {
        System.out.println("A");
    }
}

class C extends B implements A {
}
```

`C` uses `B.fun()`.

---

### 15. What is a functional interface?

**Answer:**
A functional interface is an interface that contains exactly one abstract method. It can be used as the target type of a lambda expression or method reference.

Examples:

```text id="u3q7m9"
Runnable
Predicate<T>
Function<T,R>
Consumer<T>
Supplier<T>
Comparable<T>
```

---

### 16. Can a functional interface have default and static methods?

**Answer:**
Yes. A functional interface is defined by having exactly one **abstract** method. It may contain multiple default, static, or private methods.

---

### 17. What is a marker interface?

**Answer:**
A marker interface is an interface with no abstract methods, used to indicate a particular capability or property to the Java platform or libraries.

Examples include:

```text id="b7n4x2"
Serializable
Cloneable
RandomAccess
```

---

### 18. Why does Java allow multiple interfaces but not multiple class inheritance?

**Answer:**
Multiple class inheritance can create ambiguity in inherited state and implementation. Interfaces primarily define contracts and Java provides explicit rules for resolving default-method conflicts, allowing multiple interface inheritance while avoiding many of the complexities of multiple class inheritance.

---

# ⭐ Most Important Interview Trap

If the interviewer asks:

> **"Are all methods in an interface public and abstract?"**

Don't answer simply **yes**.

Say:

> **"An interface method without a body is implicitly public and abstract. However, since Java 8 interfaces can also contain public default and static methods, and since Java 9 they can contain private methods. Therefore, not every interface method is necessarily public abstract."**

That answer shows you understand **modern Java interfaces**, rather than only the pre-Java-8 model.

---

# 🔥 Final 10-Point Revision

```text id="z5m8q2"
1. Interface = contract / capability.
2. Class uses implements; interface uses extends.
3. Interface fields = public static final.
4. Ordinary interface methods without body = public abstract.
5. Java 8 → default + static methods.
6. Java 9 → private methods.
7. Class can implement multiple interfaces.
8. Interface can extend multiple interfaces.
9. Functional interface = exactly one abstract method.
10. Marker interface = no methods.
```

### And the most important mental model:

```text id="j2q7m4"
             INTERFACE
                 │
          "What can you do?"
                 │
        ┌────────┼────────┐
        ↓        ↓        ↓
      Flyable  Walkable  Swimmable
        │        │        │
        └────────┼────────┘
                 ↓
              Duck
                 │
          "How do I do it?"
```

**Interface = "can-do" contract**
**Abstract class = "is-a" common base**
**Concrete class = actual implementation**
