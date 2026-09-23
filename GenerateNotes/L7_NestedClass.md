# Java Notes — Nested Classes

---

# ==================== Nested Classes ====================

## 1. Overview

A **nested class** is a class that is declared inside another class or interface.

```java
class Outer {

    class Inner {
        // nested class
    }
}
```

Here:

* `Outer` → outer/enclosing class
* `Inner` → nested class

Nested classes are mainly used for **logical grouping, encapsulation, and better organization of closely related classes**.

### Why do we need Nested Classes?

### 1. Logical Grouping

If a class is useful only in the context of another class, keeping it inside the outer class makes the relationship clear.

```java
class Car {

    class Engine {
        // Engine is strongly related to Car
    }
}
```

`Engine` logically belongs to `Car`, so nesting improves code organization.

### 2. Better Encapsulation

A nested class can access the members of its enclosing class, including **private members**.

```java
class Outer {

    private int x = 10;

    class Inner {
        void display() {
            System.out.println(x);
        }
    }
}
```

Even though `x` is private, `Inner` can access it.

### 3. Better Readability

If a helper class is used only by one class, nesting it prevents unnecessary exposure at the package level.

---

# Types of Nested Classes

Java has four major types:

```text
Nested Classes
│
├── 1. Static Nested Class
│
├── 2. Inner Class
│   ├── Member Inner Class
│   ├── Local Inner Class
│   └── Anonymous Inner Class
```

More simply:

1. **Static nested class**
2. **Non-static member inner class**
3. **Local class**
4. **Anonymous class**

> **Important:** Technically, only a non-static nested class is called an **inner class**. A static nested class is a nested class but **not** an inner class.

---

# 1️⃣ Static Nested Class

A class declared with the `static` keyword inside another class is called a **static nested class**.

```java
class Outer {

    static class Inner {

        void fun() {
            System.out.println("Inner class method");
        }
    }
}
```

## Object Creation

A static nested class does **not require an object of the outer class**.

```java
Outer.Inner inner = new Outer.Inner();

inner.fun();
```

### Why?

Because the static nested class belongs to the **Outer class**, rather than to a particular object of `Outer`.

---

## Accessing Outer Class Members

A static nested class can directly access **static members** of the outer class.

```java
class Outer {

    static int x = 10;

    static class Inner {

        void display() {
            System.out.println(x);
        }
    }
}
```

This works because `x` is static.

### What about non-static members?

A static nested class **cannot directly access non-static members** of the outer class.

```java
class Outer {

    int x = 10;

    static class Inner {

        void display() {
            // System.out.println(x); // ERROR
        }
    }
}
```

Why?

A non-static variable belongs to an **object**, but the static nested class doesn't automatically have an `Outer` object reference.

However, we can access it if we explicitly provide an `Outer` object.

```java
class Outer {

    int x = 10;

    static class Inner {

        void display(Outer outer) {
            System.out.println(outer.x);
        }
    }
}
```

Usage:

```java
Outer outer = new Outer();

Outer.Inner inner = new Outer.Inner();

inner.display(outer);
```

---

## Static Nested Class Behaves Like a Normal Class

A static nested class can:

* Extend another class
* Implement interfaces
* Have constructors
* Have instance variables
* Have static variables
* Have static methods
* Have instance methods
* Use access modifiers

Example:

```java
class Outer {

    static class Inner extends Parent implements MyInterface {

        static int x;

        int y;

        static void staticMethod() {
        }

        void instanceMethod() {
        }
    }
}
```

Its binary name is generally represented as:

```text
Outer$Inner
```

while Java source code refers to it as:

```text
Outer.Inner
```

---

# Use Cases of Static Nested Classes

### 1. Helper Classes

When a helper class is strongly related to an outer class.

```java
class BankAccount {

    private static class InterestCalculator {

        static double calculateYearly(double principal, double rate) {
            return principal * rate;
        }
    }

    public double computeInterest(double principal) {
        return InterestCalculator.calculateYearly(principal, 0.09);
    }
}
```

Here `InterestCalculator` is an implementation detail of `BankAccount`.

---

### 2. Builder Design Pattern

Static nested classes are commonly used for the **Builder Pattern**.

```java
class User {

    private String name;
    private int age;

    private User(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
    }

    static class Builder {

        private String name;
        private int age;

        Builder setName(String name) {
            this.name = name;
            return this;
        }

        Builder setAge(int age) {
            this.age = age;
            return this;
        }

        User build() {
            return new User(this);
        }
    }
}
```

Usage:

```java
User user = new User.Builder()
                .setName("Pratik")
                .setAge(23)
                .build();
```

---

### 3. DTO / Request / Response Classes

Static nested classes can be useful when request/response objects are tightly associated with a particular API or domain class.

```java
class UserController {

    static class UserRequest {
        String name;
        String email;
    }

    static class UserResponse {
        int id;
        String name;
    }
}
```

---

# ⭐ Interview Questions — Static Nested Class

### Q1. What is a static nested class?

**Interview Answer:**

> A static nested class is a class declared with the `static` keyword inside another class. Unlike a non-static inner class, it does not require an instance of the outer class to be created. It can directly access only the static members of the outer class.

---

### Q2. Does a static nested class require an outer class object?

**Answer:**

> No. A static nested class can be instantiated directly using `Outer.Inner`, without creating an object of `Outer`.

```java
Outer.Inner obj = new Outer.Inner();
```

---

### Q3. Can a static nested class access non-static members of the outer class?

**Answer:**

> Not directly. Non-static members belong to an instance of the outer class. Therefore, the static nested class needs an explicit reference to an outer-class object to access them.

---

### Q4. Is a static nested class actually an inner class?

**Answer:**

> No. Technically, a static nested class is a **nested class**, but it is not called an inner class. In Java terminology, an inner class specifically refers to a non-static nested class.

---

# 2️⃣ Non-Static Member Inner Class

A class declared inside another class **without `static`** is called a member inner class.

```java
class Outer {

    int x = 10;

    class Inner {

        void fun() {
            System.out.println(x);
        }
    }
}
```

The `Inner` class is associated with an **instance of `Outer`**.

---

# Object Creation

Because `Inner` is non-static, we need an outer-class object first.

### Method 1 — Using an existing outer object

```java
Outer outer = new Outer();

Outer.Inner inner = outer.new Inner();

inner.fun();
```

This is the most common syntax.

### Method 2 — Without storing the outer reference

```java
Outer.Inner inner = new Outer().new Inner();

inner.fun();
```

Here:

```java
new Outer()
```

creates the outer object, and:

```java
.new Inner()
```

creates the inner object associated with that outer object.

---

# Important Concept: Inner Class Has an Association With Outer Object

A non-static inner class instance is associated with an instance of its enclosing class.

Conceptually, you can think of the relationship as:

```text
Outer Object
     ↑
     │ associated with
     │
Inner Object
```

This association allows the inner class to access the outer object's instance members.

For example:

```java
class Outer {

    int x = 10;

    class Inner {

        void display() {
            System.out.println(x);
        }
    }
}
```

The inner class can directly access:

```java
x
```

because it is associated with an `Outer` instance.

### Important Interview Point

Do **not** say that Java literally adds:

```java
Outer outer;
```

to your source-level inner class.

A better interview explanation is:

> The Java compiler implements the required relationship between an inner-class instance and its enclosing instance. Conceptually, the inner object needs a reference to its enclosing `Outer` instance.

This distinction is useful because the exact generated representation is an implementation detail.

---

# Accessing Outer and Inner Variables

Consider:

```java
class Outer {

    int x = 10;

    class Inner {

        int x = 20;

        void fun() {

            System.out.println(x);

            System.out.println(Outer.this.x);
        }
    }
}
```

Output:

```text
20
10
```

### Why?

Inside `Inner`:

```java
x
```

refers to the nearest variable, which is:

```java
Inner.x
```

To explicitly access the outer object's variable, use:

```java
Outer.this.x
```

---

# `Outer.this`

`Outer.this` represents the **current enclosing `Outer` object**.

Example:

```java
class Outer {

    int x = 10;

    class Inner {

        int x = 20;

        void display() {

            System.out.println(this.x);        // 20
            System.out.println(Outer.this.x);  // 10
        }
    }
}
```

So:

```text
this
        → current Inner object

Outer.this
        → current Outer object
```

This is an important interview concept.

---

# Inner Class Can Access Private Members

```java
class Outer {

    private int salary = 50000;

    class Inner {

        void display() {
            System.out.println(salary);
        }
    }
}
```

This is completely valid.

The inner class has access to the members of its enclosing class, subject to the normal language rules.

---

# Can an Inner Class Have Static Members?

This is an important version-specific point.

Historically, Java restricted static declarations inside inner classes.

Modern Java has relaxed this restriction. **Since Java 16, an inner class can declare static members**, including static fields and methods, subject to the language rules.

For example:

```java
class Outer {

    class Inner {

        static int x = 10;

        static void display() {
            System.out.println(x);
        }
    }
}
```

This is valid in modern Java.

### Why was this historically restricted?

An inner class instance is associated with an outer object, while a static member belongs to the class rather than to a particular object.

Older Java rules restricted such declarations to avoid situations where the meaning of class-level static state inside an instance-associated class could become confusing.

Modern Java's language rules have relaxed this restriction.

> **Interview tip:** If asked this question, mention the Java version:
> **"Before Java 16, inner classes had restrictions on static declarations. Since Java 16, static members are allowed in inner classes, subject to the applicable restrictions."**

---

# ⭐ Interview Questions — Inner Class

### Q1. What is an inner class?

**Answer:**

> An inner class is a non-static nested class. Its instance is associated with an instance of the enclosing class, and it can directly access the enclosing object's members, including private members.

---

### Q2. Why do we need an object of the outer class to create a non-static inner class?

**Answer:**

> Because a non-static inner class instance is associated with a specific instance of the enclosing class. Therefore, Java requires an enclosing object when creating the inner-class object.

---

### Q3. What does `Outer.this` mean?

**Answer:**

> `Outer.this` refers to the current instance of the enclosing `Outer` class from within the inner class.

---

### Q4. Difference between `this` and `Outer.this`?

**Answer:**

```java
this
```

refers to the current inner-class object.

```java
Outer.this
```

refers to the current enclosing `Outer` object.

---

### Q5. Can an inner class access private members of the outer class?

**Answer:**

> Yes. A nested class can access members of its enclosing class, including private members.

---

# 3️⃣ Local Class

A **local class** is a class declared inside a method, constructor, initializer, or another local block.

Example:

```java
class Outer {

    void greet() {

        class Local {

            void sayHello() {
                System.out.println("Hello");
            }

            void sayHi() {
                System.out.println("Hi");
            }
        }

        Local local = new Local();

        local.sayHello();
    }
}
```

The scope of `Local` is limited to the block in which it is declared.

Therefore, you cannot do:

```java
Local obj = new Local();
```

from outside `greet()`.

---

# Why Use Local Classes?

Local classes are useful when:

> A class is needed only within a particular method or block and does not need to be visible outside that scope.

For example:

```java
void processData() {

    class Validator {

        boolean validate(String data) {
            return data != null && !data.isEmpty();
        }
    }

    Validator validator = new Validator();

    validator.validate("Hello");
}
```

The `Validator` class is relevant only to `processData()`.

---

# Effective Final — Very Important ⭐⭐⭐

A local class can access local variables from the enclosing method **only if those variables are final or effectively final**.

Example:

```java
class Outer {

    void greet() {

        int x = 5;

        class Local {

            void display() {
                System.out.println(x);
            }
        }

        Local obj = new Local();

        obj.display();
    }
}
```

This works because `x` is **effectively final**.

We didn't explicitly write:

```java
final int x = 5;
```

but we never modify `x`.

Therefore:

```java
int x = 5;
```

is effectively final.

---

## What happens if we modify `x`?

```java
void greet() {

    int x = 5;

    x++;

    class Local {

        void display() {
            System.out.println(x);
        }
    }
}
```

This causes a compilation error when `x` is captured by the local class.

Why?

Because `x` is no longer effectively final.

---

# Why Does Java Require Effectively Final Variables?

This is an extremely important interview question.

Consider:

```java
void test() {

    int x = 10;

    class Local {

        void print() {
            System.out.println(x);
        }
    }

    Local obj = new Local();
}
```

The local variable `x` normally exists in the method's local scope.

But the `Local` object may continue to exist after the method's execution context would normally disappear.

Therefore, Java's implementation captures the value of the local variable rather than allowing the inner object to freely share a mutable method-local variable.

A useful mental model is:

```text
Method local variable
        ↓
captured value
        ↓
Local class object
```

The value must not change after capture, which is why Java requires it to be final or effectively final.

### Important clarification

Don't say:

> "Java requires final because local variables are stored in stack and objects are stored in heap."

That's an oversimplification and is not a good interview explanation.

The important concept is **capturing local variables and preserving consistent semantics**.

---

# Local Class Example

```java
class Outer {

    void greet() {

        int x = 5;  // effectively final

        class Local {

            void sayHello() {
                System.out.println("Hello");
            }

            void sayHi() {
                System.out.println("Hi");
                System.out.println("x = " + x);
            }

            void sayTakeCare() {
                System.out.println("Take Care");
            }
        }

        Local local = new Local();

        local.sayHello();
        local.sayHi();
        local.sayTakeCare();
    }
}
```

Usage:

```java
public class Main {

    public static void main(String[] args) {

        Outer outer = new Outer();

        outer.greet();
    }
}
```

---

# ⭐ Interview Questions — Local Class

### Q1. What is a local class?

**Answer:**

> A local class is a class declared inside a method, constructor, initializer, or local block. Its scope is restricted to the block where it is declared.

---

### Q2. Can a local class access local variables?

**Answer:**

> Yes, but local variables accessed by the local class must be final or effectively final.

---

### Q3. What is an effectively final variable?

**Answer:**

> A variable is effectively final if it is initialized once and is never modified afterward, even if the `final` keyword is not explicitly used.

Example:

```java
int x = 10;
```

If we never modify `x`, it is effectively final.

---

### Q4. Why can't a local class modify a captured local variable?

**Answer:**

> Local classes capture the value of local variables from the enclosing scope. Java requires those variables to remain final or effectively final so that the captured value has consistent semantics.

---

# 4️⃣ Anonymous Class

An **anonymous class** is a class that is declared and instantiated at the same time and has **no explicit class name**.

It is commonly used when we need a customized implementation **only once**.

---

# Basic Syntax

```java
Parent obj = new Parent() {

    @Override
    void method() {
        // implementation
    }
};
```

The class has no explicit name.

---

# Example

Suppose we have:

```java
class Person {

    void introduce() {
        System.out.println("Hi, I am a person");
    }
}
```

Suppose we want a special implementation for only one use.

We could create another class:

```java
class Guest extends Person {

    @Override
    void introduce() {
        System.out.println("Hi, I am a guest");
    }
}
```

Then:

```java
Person p = new Guest();

p.introduce();
```

Output:

```text
Hi, I am a guest
```

But if we need this implementation only once, creating a separate named class may be unnecessary.

We can use an anonymous class:

```java
Person p = new Person() {

    @Override
    void introduce() {
        System.out.println("Hi, I am a guest");
    }
};

p.introduce();
```

---

# Anonymous Class Can Have Its Own Members

Example:

```java
Person p = new Person() {

    int x = 10;

    @Override
    void introduce() {
        greet();
        System.out.println("Hi, I am a guest");
    }

    void greet() {
        System.out.println("Hi, I am greeting");
    }
};
```

The anonymous class has:

```java
int x;
```

and:

```java
greet()
```

---

# Important: Reference Type vs Object Type ⭐⭐⭐

Consider:

```java
Person p = new Person() {

    int x = 10;

    @Override
    void introduce() {
        System.out.println("Guest");
    }

    void greet() {
        System.out.println("Hello");
    }
};
```

This is valid.

But:

```java
p.introduce();
```

works because `introduce()` exists in `Person`.

However:

```java
p.greet();
```

does **not** compile.

And:

```java
p.x
```

does **not** compile.

### Why?

Because the **reference type** is:

```java
Person
```

Therefore, the compiler checks accessible members based on the `Person` type.

The actual object is an instance of the anonymous subclass, but the reference only exposes members known through `Person`.

This is a very important OOP concept:

```text
Person p
   │
   │ reference type
   ↓
Person

Actual object
   │
   ↓
Anonymous subclass of Person
```

---

# Runtime Method Overriding

Although the reference is:

```java
Person p
```

calling:

```java
p.introduce();
```

executes the overridden method in the anonymous class.

This happens because of **runtime polymorphism / dynamic method dispatch**.

So:

```java
Person p = new Person() {

    @Override
    void introduce() {
        System.out.println("Guest");
    }
};
```

and:

```java
p.introduce();
```

prints:

```text
Guest
```

---

# Anonymous Classes Can Implement Interfaces

Anonymous classes are not limited to extending classes.

They can implement interfaces as well.

```java
Runnable task = new Runnable() {

    @Override
    public void run() {
        System.out.println("Task running");
    }
};
```

Then:

```java
task.run();
```

---

# Anonymous Class and Functional Interfaces

Before Java 8, anonymous classes were commonly used for one-time interface implementations.

Example:

```java
Runnable task = new Runnable() {

    @Override
    public void run() {
        System.out.println("Running");
    }
};
```

Since Java 8, if the interface is a **functional interface**, a lambda expression is often more concise:

```java
Runnable task = () -> {
    System.out.println("Running");
};
```

### Interview Point

> Anonymous classes are still useful when we need to create a one-time implementation, especially when the implementation requires additional state or multiple methods. For functional interfaces, lambda expressions are usually more concise.

---

# Can Anonymous Classes Have Constructors?

An anonymous class **does not have a named constructor** because it does not have a class name.

You cannot write:

```java
new Person() {

    Person() {   // ❌ not a normal constructor declaration
    }
};
```

However, you can use an **instance initializer block**:

```java
Person p = new Person() {

    {
        System.out.println("Anonymous class initialized");
    }

    @Override
    void introduce() {
        System.out.println("Guest");
    }
};
```

This initializer runs when the anonymous object is created.

---

# Anonymous Class and Local Variables

Like local classes, an anonymous class can access local variables from the enclosing method only when those variables are **final or effectively final**.

```java
void test() {

    int x = 10;

    Runnable r = new Runnable() {

        @Override
        public void run() {
            System.out.println(x);
        }
    };
}
```

This works because `x` is effectively final.

But:

```java
int x = 10;

x++;

Runnable r = new Runnable() {

    @Override
    public void run() {
        System.out.println(x);
    }
};
```

causes a compilation error.

---

# Anonymous Class vs Named Class

| Feature                 | Named Class       | Anonymous Class        |
| ----------------------- | ----------------- | ---------------------- |
| Class name              | Yes               | No                     |
| Reusable                | Yes               | Usually one-time use   |
| Constructor             | Yes               | No named constructor   |
| Can extend class        | Yes               | Yes                    |
| Can implement interface | Yes               | Yes                    |
| Can override methods    | Yes               | Yes                    |
| Can have fields         | Yes               | Yes                    |
| Can have methods        | Yes               | Yes                    |
| Good for                | Reusable behavior | One-time customization |

---

# ⭐ Interview Questions — Anonymous Class

### Q1. What is an anonymous class?

**Answer:**

> An anonymous class is a class without an explicit name that is declared and instantiated at the same time. It is generally used when we need a one-time customized implementation of a class or interface.

---

### Q2. Why use an anonymous class?

**Answer:**

> Anonymous classes are useful when we need a customized implementation only once and creating a separate named class would add unnecessary code.

---

### Q3. Can an anonymous class have variables and methods?

**Answer:**

> Yes. An anonymous class can have its own fields, methods, initializer blocks, and can override methods from its superclass or interface.

---

### Q4. Can an anonymous class have a constructor?

**Answer:**

> It cannot declare a named constructor because it has no explicit class name. However, it can use instance initializer blocks to perform initialization.

---

### Q5. Why can't we access `x` in this example?

```java
Person p = new Person() {

    int x = 10;

    @Override
    void introduce() {
        System.out.println("Guest");
    }
};

System.out.println(p.x);
```

**Answer:**

> Because the reference variable `p` has the type `Person`, and `x` is not a member of `Person`. The compiler checks member accessibility using the reference type, even though the actual object is an instance of the anonymous subclass.

---

# 🔥 Nested Classes — Important Comparison

| Feature                                     | Static Nested   | Member Inner             | Local Class                       | Anonymous Class                   |
| ------------------------------------------- | --------------- | ------------------------ | --------------------------------- | --------------------------------- |
| `static` keyword                            | Yes             | No                       | No                                | No explicit name                  |
| Needs Outer object?                         | ❌ No            | ✅ Yes                    | Depends on context                | Depends on context                |
| Has explicit name?                          | Yes             | Yes                      | Yes                               | ❌ No                              |
| Scope                                       | Outer class     | Outer class              | Local block                       | Expression                        |
| Can access outer instance members directly? | ❌ No            | ✅ Yes                    | If associated with outer instance | If associated with outer instance |
| Can access local variables?                 | ❌               | ❌                        | ✅ effectively final               | ✅ effectively final               |
| Can extend class?                           | ✅               | ✅                        | ✅                                 | ✅                                 |
| Can implement interface?                    | ✅               | ✅                        | ✅                                 | ✅                                 |
| Main purpose                                | Grouping/helper | Object-specific behavior | Method-specific helper            | One-time implementation           |

---

# 🔥 Most Important Conceptual Difference

Remember this hierarchy:

```text
Nested Class
│
├── Static Nested Class
│      └── Does NOT need Outer object
│
└── Inner Class
       │
       ├── Member Inner Class
       │
       ├── Local Class
       │
       └── Anonymous Class
```

The easiest way to remember:

> **Static nested class → belongs to the outer class.**
> **Inner class → associated with an outer object.**
> **Local class → declared inside a local scope.**
> **Anonymous class → unnamed one-time implementation.**

---

# 🎯 Top Interview Questions — Nested Classes

These are the questions I would especially prepare for an SDE interview:

### 1. What is a nested class?

> A nested class is a class declared inside another class or interface. Java provides nested classes to improve logical grouping, encapsulation, and code organization.

### 2. What are the types of nested classes?

> Java supports static nested classes, member inner classes, local classes, and anonymous classes.

### 3. Difference between static nested class and inner class?

> A static nested class does not require an instance of the enclosing class, whereas a non-static inner class is associated with an instance of the enclosing class.

### 4. Can a static nested class access non-static members?

> Not directly. It needs an explicit reference to an instance of the outer class.

### 5. Can an inner class access private members of its outer class?

> Yes. A nested class can access members of its enclosing class, including private members.

### 6. What is `Outer.this`?

> `Outer.this` refers to the current instance of the enclosing `Outer` class from inside the inner class.

### 7. What is an effectively final variable?

> A local variable is effectively final when it is assigned once and never modified afterward, even though the `final` keyword is not explicitly used.

### 8. Why must captured local variables be final or effectively final?

> Because local and anonymous classes capture the value of those variables, and Java requires the captured variables to remain unchanged to provide consistent semantics.

### 9. Can an anonymous class extend a class?

> Yes. An anonymous class can extend a class and override its methods.

### 10. Can an anonymous class implement an interface?

> Yes. It can provide an implementation of an interface without creating a separately named class.

### 11. Why can't we access anonymous-class-specific members through the parent reference?

> Because compile-time member access is determined by the reference type. If the reference is of the parent type, only members available through that type can be accessed.

### 12. Anonymous class vs Lambda?

> An anonymous class can extend a class or implement an interface and can contain its own state and multiple methods. A lambda expression can be used only with a functional interface and is generally more concise for representing a single behavior.

---

# 🧠 30-Second Interview Revision

If the interviewer asks **"Explain nested classes in Java"**, you can answer:

> **"A nested class is a class declared inside another class. Java provides four commonly discussed types: static nested classes, member inner classes, local classes, and anonymous classes. A static nested class does not require an outer-class instance, while a non-static inner class is associated with an outer object and can directly access its instance members. A local class is declared inside a method or block and is useful for method-specific functionality. An anonymous class is an unnamed class used mainly for one-time customization of a class or interface. Local and anonymous classes can capture local variables only when those variables are final or effectively final."**

That is a strong **SDE interview-level answer** without going unnecessarily deep.
