# ============= Encapsulation 

## 1. Overview

**Encapsulation** means bundling an object's **data (state)** and the **methods (behavior) that operate on that data** together inside a class, while controlling how the internal data can be accessed or modified.

The word comes from the idea of forming a **capsule** — keeping related things together and protecting the internal details from uncontrolled external access.

### Real-life example

Consider a **Bank Account**.

A bank account has:

```text
Data / State
    ↓
accountNumber
balance
accountHolder

Behavior
    ↓
deposit()
withdraw()
checkBalance()
```

We should not allow anyone to directly modify:

```java
account.balance = 1000000;
```

Instead, we control access through methods:

```java
account.deposit(5000);
account.withdraw(1000);
```

This allows the class to validate and control changes to its internal state.

---

# 2. Main Principles of Encapsulation

### 1. Data and behavior should be grouped together

The data and the methods that operate on that data should be defined inside the same class.

```java
class BankAccount {

    private double balance;

    void deposit(double amount) {
        balance += amount;
    }
}
```

Here:

```text
balance → data
deposit() → behavior
```

Both are encapsulated inside `BankAccount`.

---

### 2. Restrict direct access to internal data

We generally make important fields `private`.

```java
class BankAccount {

    private double balance;
}
```

Now external classes cannot directly modify:

```java
account.balance = 100000; // ❌
```

Instead:

```java
account.deposit(100000);
```

---

### 3. Provide controlled access

We can expose specific methods that control how the data is accessed or modified.

```java
class BankAccount {

    private double balance;

    public void deposit(double amount) {

        if (amount > 0) {
            balance += amount;
        }
    }

    public double getBalance() {
        return balance;
    }
}
```

This gives us control over the object's state.

---

# 3. Encapsulation Example

```java
class BankAccount {

    private double balance;

    public void deposit(double amount) {

        if (amount > 0) {
            balance += amount;
        }
    }

    public void withdraw(double amount) {

        if (amount > 0 && amount <= balance) {
            balance -= amount;
        }
    }

    public double getBalance() {
        return balance;
    }
}
```

Usage:

```java
BankAccount account = new BankAccount();

account.deposit(5000);
account.withdraw(1000);

System.out.println(account.getBalance());
```

The important point is:

```text
Outside code
     ↓
public methods
     ↓
private data
```

The caller cannot arbitrarily manipulate `balance`.

---

# 4. Encapsulation vs Data Hiding

These terms are related but not exactly identical.

### Encapsulation

> Bundling data and the methods that operate on that data into a single unit, while controlling access to the internal state.

### Data Hiding

> Restricting direct access to implementation details or internal state.

For example:

```java
private double balance;
```

is an example of **data hiding**, while putting `balance` and `deposit()` together inside `BankAccount` contributes to **encapsulation**.

---

# ============================== Access Modifiers ==============================

Access modifiers determine **where a class member can be accessed**.

They can be applied to:

* Variables/fields
* Methods
* Constructors
* Classes

The four access levels are:

```text
public
protected
default (package-private)
private
```

---

# 5. `public`

A `public` member can be accessed from anywhere, provided the class itself is accessible.

```java
public int age;
```

Conceptually:

```text
Same class       ✅
Same package     ✅
Different package ✅
Subclass         ✅
```

---

# 6. `private`

A `private` member can be accessed only within the class in which it is declared.

```java
class Student {

    private int age;

    private void display() {
    }
}
```

Outside the class:

```java
Student s = new Student();

s.age; // ❌
s.display(); // ❌
```

This is one of the primary tools used for encapsulation.

---

# 7. Default / Package-Private

If no access modifier is specified, the member has **package-private** access.

```java
class Student {

    int age;
}
```

`age` can be accessed by classes in the **same package**.

```text
Same class        ✅
Same package      ✅
Different package ❌
Subclass outside package ❌ directly
```

Important:

> "Default access" does **not** mean public.

It means **package-private**.

---

# 8. `protected`

A `protected` member can be accessed:

1. By classes in the **same package**
2. By subclasses in other packages, subject to Java's protected-access rules

Example:

```java
class Parent {

    protected int x = 10;
}
```

A subclass can access it:

```java
class Child extends Parent {

    void display() {
        System.out.println(x);
    }
}
```

---

# 9. Access Modifier Table

| Modifier    | Same Class | Same Package | Subclass in Different Package | Different Package |
| ----------- | ---------: | -----------: | ----------------------------: | ----------------: |
| `private`   |          ✅ |            ❌ |                             ❌ |                 ❌ |
| default     |          ✅ |            ✅ |                            ❌* |                 ❌ |
| `protected` |          ✅ |            ✅ |                             ✅ |                 ❌ |
| `public`    |          ✅ |            ✅ |                             ✅ |                 ✅ |

`*` A subclass in another package does not get ordinary package-private access.

### Important `protected` detail ⭐

For a subclass in a different package, `protected` access is through inheritance, not arbitrary access through any `Parent` object.

This is a common advanced interview question.

---

# 10. Access Modifiers on Classes

For a **top-level class**, only:

```java
public
```

or:

```java
package-private
```

are allowed.

You cannot declare a top-level class:

```java
private class A { }    // ❌
protected class A { }  // ❌
```

However, a **nested class** can use all four access modifiers:

```java
class Outer {

    private class A { }
    protected class B { }
    public class C { }
    class D { }
}
```

---

# 11. Access Modifiers on Constructors

Constructors can also have access modifiers.

### Public constructor

```java
public Student() {
}
```

Can be called from accessible code.

### Private constructor

```java
private Student() {
}
```

Can only be called from within the class.

This is useful for patterns such as Singleton or controlled object creation.

### Protected constructor

Useful when object creation should be available within the package and through subclasses.

---

# ================== Packages 

## 12. What is a Package?

A **package** is a namespace used to organize related Java classes, interfaces, enums, and other types.

Example:

```java
package com.example.service;
```

Then:

```text
com.example.service
    ├── UserService
    ├── PaymentService
    └── OrderService
```

Packages help with:

* Organization
* Namespace management
* Access control
* Avoiding naming conflicts

---

# 13. Types of Packages

### 1. Built-in / Standard Java Packages

Examples:

```text
java.lang
java.util
java.io
java.time
java.sql
```

Examples:

```java
java.lang.String
java.util.ArrayList
java.io.File
```

`java.lang` is automatically available to Java source code, so you normally don't need:

```java
import java.lang.String;
```

---

### 2. User-defined Packages

Developers can create their own packages.

```java
package com.myapp.model;
```

Example structure:

```text
src/
 └── com/
     └── myapp/
         └── model/
             └── Student.java
```

---

# 14. Why Do We Need Packages?

### 1. Organization

Related classes can be grouped together.

### 2. Avoid Naming Conflicts

Suppose two companies both have:

```text
Employee
```

They can have:

```text
companyA.Employee
companyB.Employee
```

The package distinguishes them.

### 3. Access Control

Package-private and protected access depend partly on package boundaries.

### 4. Maintainability

Large applications can organize classes into meaningful packages:

```text
controller
service
repository
model
dto
exception
config
```

---

# 15. `import` Does Not Load All Classes

Your note says:

> "as bytecode generate of demo.class import all .class file of all package imported and loaded to remove ambiguity."

This needs correction.

When we write:

```java
import java.util.ArrayList;
```

the `import` statement is primarily a **compile-time source-code naming convenience**.

It does **not** mean:

> "Load every class from `java.util` into memory."

For example:

```java
import java.util.ArrayList;
```

allows us to write:

```java
ArrayList<Integer> list;
```

instead of:

```java
java.util.ArrayList<Integer> list;
```

### Important

```java
import java.util.*;
```

does **not** load every class in `java.util` into the JVM.

It simply allows unqualified references to types in that package during compilation.

---

# 16. How Does JVM Identify a Class?

Classes have fully qualified names.

For example:

```java
package com.example.model;

public class Student {
}
```

The fully qualified class name is:

```text
com.example.model.Student
```

The package name helps distinguish classes with the same simple name.

---

# ====================== Inheritance ================

# 17. What is Inheritance?

**Inheritance** is an OOP mechanism where a class acquires accessible members and behavior from another class.

Java uses:

```java
extends
```

for class inheritance.

Example:

```java
class Vehicle {

    void start() {
        System.out.println("Vehicle starts");
    }
}

class Car extends Vehicle {

    void drive() {
        System.out.println("Car drives");
    }
}
```

Here:

```text
Vehicle
   ↑
   |
  Car
```

`Car` is a subclass/child class.

`Vehicle` is a superclass/parent class.

---

# 18. Is-a Relationship

Inheritance represents an **is-a relationship** when the subclass genuinely represents a specialized form of the superclass.

```text
Car is-a Vehicle
Dog is-a Animal
Manager is-a Employee
```

Example:

```java
class Car extends Vehicle {
}
```

means:

> A Car is a Vehicle.

---

# 19. Why Do We Use Inheritance?

### 1. Code Reusability

Common functionality can be defined in the parent class.

```java
class Vehicle {

    void start() {
    }

    void stop() {
    }
}
```

Child classes can reuse it.

---

### 2. Polymorphism

Inheritance enables runtime polymorphism.

```java
Vehicle v = new Car();
```

Then overridden methods can be selected dynamically.

---

### 3. Establishing Relationships

Inheritance models an `is-a` relationship between classes.

---

### Important

Don't use inheritance **only** because you want code reuse.

Inheritance should represent a meaningful **is-a relationship**.

If the relationship is "has-a", composition is usually more appropriate.

```text
Car has-a Engine
```

This is better modeled using composition:

```java
class Car {
    Engine engine;
}
```

---

# 20. Types of Inheritance in Java

## 1. Single Inheritance

One child inherits from one parent.

```text
A
|
B
```

```java
class A {
}

class B extends A {
}
```

---

# 21. Multilevel Inheritance

A class inherits from another derived class.

```text
A
|
B
|
C
```

```java
class A {
}

class B extends A {
}

class C extends B {
}
```

`C` indirectly inherits from `A`.

---

# 22. Hierarchical Inheritance

Multiple child classes inherit from the same parent.

```text
       A
      / \
     B   C
```

```java
class A {
}

class B extends A {
}

class C extends A {
}
```

---

# 23. Multiple Inheritance

Multiple inheritance means one class inherits from multiple classes.

Conceptually:

```text
A       B
 \     /
   \ /
    C
```

Java does **not support multiple inheritance of classes**.

This is invalid:

```java
class C extends A, B { } // ❌
```

---

# 24. Why Doesn't Java Support Multiple Class Inheritance?

One important reason is ambiguity, commonly demonstrated using the **diamond problem**.

Consider:

```text
        A
       / \
      B   C
       \ /
        D
```

Suppose:

```java
class A {

    void show() {
        System.out.println("A");
    }
}
```

Both `B` and `C` override `show()`.

Then if `D` inherited from both:

```java
D obj = new D();

obj.show();
```

Which implementation should be called?

```text
A.show()
B.show()
C.show()
```

There would be ambiguity.

Java avoids this particular problem by not allowing a class to extend multiple classes.

---

# 25. Multiple Inheritance Through Interfaces

Java allows a class to implement multiple interfaces.

```java
interface A {

    void show();
}

interface B {

    void display();
}

class C implements A, B {

    public void show() {
    }

    public void display() {
    }
}
```

So:

```text
Multiple class inheritance      ❌
Multiple interface inheritance  ✅
```

Java also defines rules for resolving conflicts involving `default` interface methods.

---

# 26. `final` Class and Inheritance

A final class cannot be inherited.

```java
final class Vehicle {
}
```

This is invalid:

```java
class Car extends Vehicle {  // ❌
}
```

Examples of final classes in Java include:

```java
String
Integer
```

---

# =============== `super` Keyword ====================

## 27. What is `super`?

`super` is a keyword used inside a subclass to refer to the **immediate superclass part/context** of the current object.

It is commonly used to:

1. Access a parent-class field
2. Call a parent-class method
3. Call a parent-class constructor

---

# 28. Access Parent Class Variable

Example:

```java
class A {

    int x = 10;
}

class B extends A {

    int x = 20;

    void display() {

        System.out.println(x);
        System.out.println(super.x);
    }
}
```

Output:

```text
20
10
```

Here:

```java
x
```

refers to the child class field.

While:

```java
super.x
```

refers to the inherited parent-class field.

---

# 29. `super` vs `this`

Very important:

```text
this
 ↓
current object / current class context

super
 ↓
immediate superclass context
```

Example:

```java
class B extends A {

    int x;

    void display() {

        System.out.println(this.x);
        System.out.println(super.x);
    }
}
```

---

# 30. Call Parent Class Method

Suppose:

```java
class A {

    void show() {
        System.out.println("Parent");
    }
}

class B extends A {

    @Override
    void show() {

        System.out.println("Child");

        super.show();
    }
}
```

Calling:

```java
B b = new B();

b.show();
```

Output:

```text
Child
Parent
```

Here:

```java
super.show();
```

explicitly calls the parent implementation.

This is useful when the child wants to **extend** rather than completely replace the parent's behavior.

---

# 31. Call Parent Constructor

```java
class A {

    A() {
        System.out.println("Parent constructor");
    }
}

class B extends A {

    B() {

        super();

        System.out.println("Child constructor");
    }
}
```

When:

```java
new B();
```

is executed:

```text
Parent constructor
Child constructor
```

---

# 32. Is `super()` Mandatory?

No.

If a child constructor does not explicitly call `this(...)` or `super(...)`, Java implicitly inserts:

```java
super();
```

provided that the parent class has an accessible no-argument constructor.

Example:

```java
class A {

    A() {
        System.out.println("A");
    }
}

class B extends A {

    B() {
        System.out.println("B");
    }
}
```

Conceptually:

```java
B() {

    super();

    System.out.println("B");
}
```

---

# 33. Important Case: Parent Has Only Parameterized Constructor

Consider:

```java
class A {

    A(int x) {
    }
}

class B extends A {

    B() {
    }
}
```

This causes a compilation error.

Why?

Because Java tries to insert:

```java
super();
```

but `A` does not have a no-argument constructor.

We must explicitly call:

```java
class B extends A {

    B() {
        super(10);
    }
}
```

---

# 34. `super()` Must Be First Statement

Like `this()`:

```java
super();
```

must be the first statement in a constructor.

Correct:

```java
B() {

    super();

    System.out.println("B");
}
```

Incorrect:

```java
B() {

    System.out.println("B");

    super(); // ❌
}
```

---

# 35. Important `super` Rule

You cannot use both:

```java
this(...)
```

and:

```java
super(...)
```

in the same constructor because both must appear as the first statement.

Example:

```java
B() {

    this(10);
    super(); // ❌
}
```

---

# ====================== Quick Revision ===================

## Encapsulation

```text
Data + Behavior
      ↓
    Class
      ↓
Controlled access
```

Main tools:

```text
private fields
public/protected methods
validation
```

---

## Access Modifiers

```text
private
   ↓
same class

default
   ↓
same package

protected
   ↓
same package + subclasses

public
   ↓
everywhere accessible if the enclosing type is accessible
```

---

## Packages

```text
Package
  ↓
group related types
  ↓
organization
namespace
access control
```

---

## Inheritance

```text
Parent
  ↑
Child
```

Uses:

```text
Reusability
Polymorphism
is-a relationship
```

Types supported with classes:

```text
Single
Multilevel
Hierarchical
```

Not supported:

```text
Multiple class inheritance ❌
```

Supported:

```text
Multiple interface implementation ✅
```

---

## `super`

```text
super.variable
     ↓
parent field

super.method()
     ↓
parent method

super()
     ↓
parent constructor
```

---

# ================= ⭐ Important Interview Questions ============

### Q1. What is encapsulation?

**Answer:**

> Encapsulation is the OOP principle of bundling an object's data and the methods that operate on that data into a class while controlling access to the internal state.

---

### Q2. How do you achieve encapsulation in Java?

**Answer:**

> Encapsulation is commonly achieved by declaring fields private and providing controlled public or protected methods, such as getters, setters, or domain-specific operations, to access or modify the state.

---

### Q3. Is encapsulation the same as data hiding?

**Answer:**

> They are related but not identical. Encapsulation is about bundling state and behavior together, while data hiding focuses on restricting direct access to internal implementation details.

---

### Q4. Why should we make fields private?

**Answer:**

> Private fields prevent unrestricted external modification. The class can then validate or control how its internal state changes, improving safety, maintainability, and invariants.

---

### Q5. What are access modifiers in Java?

**Answer:**

> Java provides four access levels: `private`, package-private or default, `protected`, and `public`. They control where a class member can be accessed.

---

### Q6. What is the difference between default and protected access?

**Answer:**

> Package-private access allows access only within the same package. Protected access allows same-package access and also provides access to subclasses in other packages, subject to protected-access rules.

---

### Q7. Can a top-level class be private or protected?

**Answer:**

> No. A top-level class can be public or package-private. However, nested classes can be declared private, protected, public, or package-private.

---

### Q8. What is a package in Java?

**Answer:**

> A package is a namespace used to organize related Java types and avoid naming conflicts. Packages also participate in Java's access-control mechanism.

---

### Q9. Does `import java.util.*` load all classes of the package?

**Answer:**

> No. The import statement primarily provides compile-time name resolution convenience. It does not mean that all classes in the package are loaded into memory.

---

### Q10. What is inheritance?

**Answer:**

> Inheritance is an OOP mechanism in which a subclass extends a superclass and acquires accessible members and behavior from it. It is used to represent an appropriate is-a relationship and enables polymorphism.

---

### Q11. What are the types of inheritance supported by Java classes?

**Answer:**

> Java classes support single, multilevel, and hierarchical inheritance. Java does not support multiple inheritance of classes, although a class can implement multiple interfaces.

---

### Q12. Why doesn't Java support multiple inheritance of classes?

**Answer:**

> One important reason is to avoid ambiguity such as the diamond problem, where a class could inherit the same method through multiple parent classes and the language would need to resolve which implementation should be used.

---

### Q13. Does Java completely avoid multiple inheritance?

**Answer:**

> No. Java does not support multiple inheritance of classes, but a class can implement multiple interfaces.

---

### Q14. What is an is-a relationship?

**Answer:**

> An is-a relationship represents inheritance where the child is a specialized form of the parent. For example, `Car is-a Vehicle`.

---

### Q15. What is a has-a relationship?

**Answer:**

> A has-a relationship represents composition or aggregation, where one class contains or uses an object of another class. For example, a `Car has-a Engine`.

---

### Q16. Inheritance vs composition?

**Answer:**

> Inheritance represents an is-a relationship and allows a subclass to reuse and specialize superclass behavior. Composition represents a has-a relationship and builds a class using objects of other classes. Composition generally provides more flexibility because components can often be changed independently.

---

### Q17. What is the `super` keyword?

**Answer:**

> `super` is used inside a subclass to refer to the immediate superclass context. It can access a parent field, invoke a parent method, or invoke a parent constructor.

---

### Q18. What is the difference between `this` and `super`?

**Answer:**

> `this` refers to the current object and current class context, whereas `super` is used to access the immediate superclass context of that object.

---

### Q19. What is `super()`?

**Answer:**

> `super()` invokes the constructor of the immediate parent class. If a child constructor does not explicitly invoke another constructor using `this(...)` or `super(...)`, Java implicitly inserts a call to the parent's no-argument constructor if one is accessible.

---

### Q20. What happens if the parent class has only a parameterized constructor?

**Answer:**

> The child constructor must explicitly invoke an accessible parent constructor using `super(arguments)`. Otherwise, the compiler-generated `super()` call cannot be resolved and compilation fails.

---

### Q21. Can `super()` and `this()` be used together?

**Answer:**

> No. Both are constructor-invocation statements and must be the first statement of the constructor, so they cannot appear together in the same constructor.

---

### Q22. Can `super` access a parent method that has been overridden?

**Answer:**

> Yes. `super.method()` explicitly invokes the immediate parent's implementation instead of the overriding implementation in the current class.

---

### Q23. Can a final class be inherited?

**Answer:**

> No. A class declared `final` cannot be extended.

---

### Q24. Are private methods inherited?

**Answer:**

> Private members are not accessible in subclasses and are not inherited in the normal sense. A subclass cannot directly access a parent's private method or field.

---

### Q25. Are constructors inherited?

**Answer:**

> No. Constructors are not inherited. However, a subclass constructor can invoke a superclass constructor using `super(...)`.

---

### Q26. Does inheritance mean all parent members are accessible to the child?

**Answer:**

> No. Access depends on the member's access modifier. For example, private members of the parent cannot be directly accessed by the child, while public and protected members can be accessed subject to their respective rules.
