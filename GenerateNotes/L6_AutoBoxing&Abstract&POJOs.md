 
I’ll also correct technical inaccuracies—for example, `Integer x = 100; Integer y = 100;` can make `x == y` **true** because of Integer caching, so it shouldn't be documented as generally `false`.

# Java Notes — Enhanced Version

## 1. Why only one `public` class per Java file?

Java allows **at most one top-level `public` class** in a `.java` source file.

```java
public class Student {
}

public class Teacher {   // ❌ Error
}
```

The compiler will complain because both classes are `public`.

### Why?

Java uses the **public class name to identify the source file**.

If we have:

```java
public class Student {
}
```

the file must be:

```text
Student.java
```

This creates a clear and predictable relationship:

```text
Student.java
      ↓
public class Student
```

### Important Rule ⭐

A `.java` file can contain:

```text
0 or 1 public top-level class
+
any number of non-public top-level classes
```

Example:

```java
public class Student {
}

class Teacher {
}

class College {
}
```

This is valid.

However, if there is **no public class**, the filename does not have to match any particular class.

---

## 2. Why should the public class name be the same as the filename?

Suppose:

```java
public class Student {
}
```

The file must be:

```text
Student.java
```

### Reason

The Java compiler and JVM ecosystem use this naming convention to provide a predictable mapping between:

```text
Source file → Public class → Compiled class
```

After compilation:

```text
Student.java
      ↓
Student.class
```

### Important Point ⭐

The **filename must match the public top-level class name exactly**, including capitalization.

```java
public class Student
```

✅ `Student.java`

❌ `student.java`

❌ `Students.java`

### Interview Question

**Q. Can a Java file contain multiple classes?**

**Answer:**
Yes. A Java source file can contain multiple top-level classes, but it can have **at most one public top-level class**. If a public class exists, the filename must exactly match that public class name.

---

# 3. Wrapper Classes

## Overview

Java has **8 primitive data types**:

```text
byte
short
int
long
float
double
char
boolean
```

For each primitive type, Java provides a corresponding **wrapper class**.

```text
Primitive        Wrapper
--------------------------------
byte      →      Byte
short     →      Short
int       →      Integer
long      →      Long
float     →      Float
double    →      Double
char      →      Character
boolean   →      Boolean
```

### Primitive vs Wrapper

```text
                 Java Data Types
                       |
              +--------+--------+
              |                 |
         Primitive          Reference
              |                 |
       int, char, etc.      Integer, Character...
```

> ⚠️ The statement that "primitives live on stack and wrappers live on heap" is a useful beginner simplification, but it is **not an absolute JVM rule**. A primitive can be a field inside an object, and JVM implementation details can vary.

---

# 4. Why do we need Wrapper Classes?

Wrapper classes are important for several reasons.

### 1. Collections require objects

Java Collections such as:

```java
ArrayList
HashMap
HashSet
```

work with objects/reference types, not primitive types.

❌

```java
ArrayList<int> list;
```

✅

```java
ArrayList<Integer> list;
```

Example:

```java
ArrayList<Integer> numbers = new ArrayList<>();

numbers.add(10);
numbers.add(20);
```

Here:

```text
int 10
 ↓
Integer 10
```

Java performs this conversion automatically through **autoboxing**.

---

### 2. Wrapper classes provide useful methods

For example:

```java
Integer.parseInt("123");
Integer.compare(10, 20);
Integer.valueOf(100);
```

Similarly:

```java
Double.parseDouble("10.5");
Boolean.parseBoolean("true");
```

---

### 3. Wrapper objects can represent `null`

A primitive cannot contain `null`.

```java
int x = null;       // ❌
```

But:

```java
Integer x = null;   // ✅
```

This is particularly useful when "no value" needs to be represented.

---

### 4. Java Collections and generics work with reference types

```java
List<Integer> numbers;
Map<String, Integer> marks;
```

Primitive types cannot directly be used as generic type arguments.

---

# 5. Autoboxing

## Definition

**Autoboxing** is the automatic conversion of a primitive value into its corresponding wrapper object.

Example:

```java
int x = 10;

Integer y = x;
```

Conceptually:

```text
int
 ↓
Integer
```

The compiler effectively uses:

```java
Integer y = Integer.valueOf(x);
```

### Example

```java
int x = 10;
Integer y = x;       // Autoboxing
```

---

# 6. Unboxing

## Definition

**Unboxing** is the automatic conversion of a wrapper object into its corresponding primitive value.

```java
Integer x = 10;

int y = x;
```

Conceptually:

```java
int y = x.intValue();
```

So:

```text
Integer
   ↓
 int
```

---

# 7. `valueOf()` vs Constructor

Older Java code sometimes used:

```java
Integer x = new Integer(10);
```

This constructor approach is **deprecated**.

Modern Java should use:

```java
Integer x = Integer.valueOf(10);
```

or simply:

```java
Integer x = 10;
```

because autoboxing handles the conversion.

### Why `valueOf()`?

`valueOf()` can reuse/cached wrapper objects where applicable instead of necessarily creating a new object every time.

---

# 8. When does Autoboxing / Unboxing happen automatically?

Java can perform boxing and unboxing in several situations.

## 1. Assignment

```java
Integer x = 10;   // int → Integer
```

```java
Integer x = 10;
int y = x;        // Integer → int
```

---

## 2. Method calls

```java
void display(Integer x) {
    System.out.println(x);
}

int a = 10;

display(a);       // autoboxing
```

---

## 3. Arithmetic operations

```java
Integer x = 10;
Integer y = 20;

int result = x + y;
```

Before performing arithmetic, Java unboxes the objects:

```text
Integer x
   ↓
int

Integer y
   ↓
int

10 + 20
```

---

## 4. String concatenation / `println()`

```java
Integer x = 10;

System.out.println(x);
```

Here Java can use the object's string representation; this is **not simply "unboxing because println()"**.

For example:

```java
System.out.println(x);
```

does not need to convert `Integer` to `int` first.

This distinction is useful in interviews.

---

# 9. `NullPointerException` with Unboxing ⭐⭐⭐

This is a very important interview concept.

```java
Integer x = null;

int y = x;    // ❌ NullPointerException
```

Why?

Because conceptually:

```java
int y = x.intValue();
```

But:

```java
x == null
```

Therefore calling:

```java
x.intValue()
```

causes:

```text
NullPointerException
```

### Interview Question

**Q. Why does unboxing a null wrapper cause `NullPointerException`?**

**Answer:**
Because unboxing internally requires calling the wrapper's primitive-value method, such as `intValue()`. If the wrapper reference is `null`, invoking that method causes a `NullPointerException`.

---

# 10. `==` with Primitive and Wrapper Types ⭐⭐⭐

This is one of the most commonly asked Java interview topics.

## Case 1: Primitive vs Primitive

```java
int x = 100;
int y = 100;

System.out.println(x == y);
```

Output:

```text
true
```

Because primitive `==` compares **values**.

---

## Case 2: Wrapper vs Wrapper

```java
Integer x = 100;
Integer y = 100;

System.out.println(x == y);
```

Here `==` compares **object references**, not values.

However, the result can be:

```text
true
```

because of Integer caching.

This is the important part.

---

## Case 3: Use `.equals()` for wrapper value comparison

```java
Integer x = 100;
Integer y = 100;

System.out.println(x.equals(y));
```

Output:

```text
true
```

For wrapper objects, `.equals()` compares their **values**.

### Rule ⭐

```text
primitive == primitive
        ↓
     values

object == object
        ↓
    references

object.equals(object)
        ↓
    logical values
```

---

# 11. Integer Caching ⭐⭐⭐

Java optimizes certain wrapper objects using caching.

For `Integer`, values in the range:

```text
-128 to 127
```

are guaranteed to be cached by the Java specification.

Example:

```java
Integer x = 100;
Integer y = 100;

System.out.println(x == y);
```

Output:

```text
true
```

Both references can point to the same cached `Integer` object.

Conceptually:

```text
        Integer Cache
       +------------+
       | Integer 100|
       +------------+
          ↑      ↑
          |      |
          x      y
```

But:

```java
Integer x = 200;
Integer y = 200;

System.out.println(x == y);
```

should **not** be relied upon to return `true`.

Usually, these may be different objects:

```text
x → Integer(200)

y → Integer(200)
```

Therefore:

```java
x == y
```

may be:

```text
false
```

while:

```java
x.equals(y)
```

is:

```text
true
```

### Golden Interview Rule ⭐⭐⭐

> **Never use `==` to compare wrapper values. Use `.equals()` instead.**

```java
Integer a = 200;
Integer b = 200;

a.equals(b);   // ✅
a == b;        // ❌ Don't use for value comparison
```

---

# 12. Wrapper Classes and `final`

Wrapper classes such as `Integer`, `Long`, `Double`, etc. are designed as immutable value objects.

For example:

```java
Integer x = 10;

x = 20;
```

This does **not** change the existing `Integer(10)` object.

Instead:

```text
x → Integer(10)

x = 20

x → Integer(20)
```

The original object remains unchanged.

---

# 13. Important Wrapper Class Interview Questions

### Q1. What is autoboxing?

**Answer:**
Autoboxing is the automatic conversion of a primitive value into its corresponding wrapper object, such as converting `int` to `Integer`.

---

### Q2. What is unboxing?

**Answer:**
Unboxing is the automatic conversion of a wrapper object into its corresponding primitive type, such as converting `Integer` to `int`.

---

### Q3. Why are wrapper classes required?

**Answer:**
Wrapper classes allow primitive values to be represented as objects. They are required when working with generic collections, provide utility methods, can represent `null`, and integrate primitive values with Java's object-oriented APIs.

---

### Q4. What happens here?

```java
Integer x = null;
int y = x;
```

**Answer:**
It throws `NullPointerException` because unboxing `x` requires accessing its primitive value, but `x` refers to `null`.

---

### Q5. What is the difference between `==` and `.equals()` for Integer?

**Answer:**
For two `Integer` objects, `==` compares their references, whereas `.equals()` compares their integer values.

---

### Q6. Why can this return `true`?

```java
Integer a = 100;
Integer b = 100;

System.out.println(a == b);
```

**Answer:**
Because Java caches commonly used `Integer` objects, and `100` falls within the guaranteed cache range of `-128` to `127`. Both references can therefore refer to the same cached object.

---

### Q7. What happens here?

```java
Integer a = 200;
Integer b = 200;

System.out.println(a == b);
```

**Answer:**
The result should not be assumed to be `true`. `==` compares references, and values outside the guaranteed Integer cache range may refer to different objects. Use `.equals()` for value comparison.

---

### Q8. Can primitives store `null`?

**Answer:**
No. Primitive types cannot store `null`. Only reference types, including wrapper classes such as `Integer`, can hold `null`.

---

# Abstract Class

## Definition

An **abstract class** is a class declared using the `abstract` keyword that is intended to act as a base class and **cannot be instantiated directly**.

```java
abstract class Animal {
}
```

This is invalid:

```java
Animal a = new Animal();   // ❌
```

But:

```java
class Dog extends Animal {
}

Dog d = new Dog();         // ✅
```

---

# Why do we use Abstract Classes?

An abstract class is useful when we want to provide:

```text
common state
+
common behavior
+
some incomplete behavior
```

to a group of related classes.

Example:

```java
abstract class Animal {

    String name;

    Animal(String name) {
        this.name = name;
    }

    abstract void sound();

    void eat() {
        System.out.println("Animal is eating");
    }
}
```

Then:

```java
class Dog extends Animal {

    Dog(String name) {
        super(name);
    }

    @Override
    void sound() {
        System.out.println("Bark");
    }
}
```

---

# 1. Can an Abstract Class Have a Constructor?

### Yes ⭐⭐⭐

An abstract class **can have constructors**.

```java
abstract class Animal {

    String name;

    Animal(String name) {
        this.name = name;
    }
}
```

But we cannot directly create:

```java
new Animal("Dog");   // ❌
```

Then why does the constructor exist?

Because when a subclass object is created, the superclass constructor is executed.

```java
class Dog extends Animal {

    Dog(String name) {
        super(name);
    }
}
```

When:

```java
Dog d = new Dog("Tommy");
```

the construction sequence is conceptually:

```text
Dog constructor
      ↓
Animal constructor
      ↓
Dog object created
```

### Interview Question

**Q. If an abstract class cannot be instantiated, why can it have a constructor?**

**Answer:**
An abstract class cannot be instantiated directly, but its constructor is executed when an object of a concrete subclass is created. It is commonly used to initialize fields inherited by the subclass.

---

# 2. Can an Abstract Class Have Abstract Methods?

### Yes.

```java
abstract class Animal {

    abstract void sound();
}
```

An abstract method has no implementation/body:

```java
abstract void sound();
```

A concrete subclass must implement it:

```java
class Dog extends Animal {

    @Override
    void sound() {
        System.out.println("Bark");
    }
}
```

---

# 3. Can an Abstract Class Have No Abstract Methods?

### Yes ⭐

This is completely valid:

```java
abstract class Animal {

    void eat() {
        System.out.println("Eating");
    }
}
```

Even though it contains no abstract methods, it can still be declared `abstract`.

### Why?

Sometimes we want to **prevent direct instantiation** of a class while still providing complete implementations to subclasses.

---

# 4. Can an Abstract Class Have Static Members?

### Yes.

An abstract class can contain:

```java
static variables
static methods
static blocks
```

Example:

```java
abstract class Animal {

    static int count = 0;

    static void display() {
        System.out.println(count);
    }
}
```

Static members belong to the class, not to an individual object, so they do not require an instance.

---

# 5. Can an Abstract Class Have Final Methods?

### Yes.

```java
abstract class Animal {

    final void eat() {
        System.out.println("Eating");
    }
}
```

A subclass can inherit the method but **cannot override it**.

```java
class Dog extends Animal {

    // ❌ Cannot override final method
}
```

### Important Combination ⭐

```java
abstract final class Animal
```

❌ Invalid.

Why?

```text
abstract → must be inherited
final    → cannot be inherited
```

These two concepts conflict.

---

# 6. Can an Abstract Method Be `final`?

### No. ❌

```java
abstract final void sound();
```

Invalid.

An abstract method requires a subclass to provide an implementation, while a final method cannot be overridden.

---

# 7. Can an Abstract Method Be `private`?

### No. ❌

```java
abstract class Animal {

    private abstract void sound();
}
```

Invalid.

### Why?

An abstract method must be implemented/overridden by a subclass.

But a `private` method is not accessible to subclasses and cannot be overridden.

Therefore:

```text
abstract + private
       ↓
      ❌
```

### But private methods in abstract classes are allowed

This is perfectly valid:

```java
abstract class Animal {

    private void helper() {
        System.out.println("Helper");
    }
}
```

The method just cannot be abstract.

---

# 8. Important Abstract Class Rules ⭐⭐⭐

```text
Abstract class:
────────────────────────────────────────
✓ Can have constructors
✓ Can have instance variables
✓ Can have static variables
✓ Can have static methods
✓ Can have final methods
✓ Can have abstract methods
✓ Can have concrete methods
✓ Can have private methods
✓ Can have zero abstract methods
✓ Can be extended

✗ Cannot be instantiated directly
✗ Cannot be final
```

---

# Abstract Class — Interview Questions

### Q1. Can we create an object of an abstract class?

**Answer:**
No. An abstract class cannot be instantiated directly. However, its constructor is executed as part of creating an object of a concrete subclass.

---

### Q2. Can an abstract class have a constructor?

**Answer:**
Yes. Although an abstract class cannot be instantiated directly, its constructor is invoked when a subclass object is created.

---

### Q3. Can an abstract class contain non-abstract methods?

**Answer:**
Yes. An abstract class can contain both abstract and concrete methods.

---

### Q4. Can an abstract class have zero abstract methods?

**Answer:**
Yes. A class can be declared abstract even if it contains no abstract methods. This can be useful when we want to prevent direct instantiation.

---

### Q5. Can an abstract class be final?

**Answer:**
No. `abstract` requires the class to be extendable, whereas `final` prevents inheritance. Therefore, an abstract class cannot be final.

---

### Q6. Can an abstract method be private?

**Answer:**
No. An abstract method must be overridden by a subclass, but a private method is not accessible to subclasses and cannot be overridden.

---

### Q7. Can an abstract class have static methods?

**Answer:**
Yes. Static methods belong to the class rather than an object, so an abstract class can contain them.

---

# POJO Class

## Definition

**POJO** stands for:

> **Plain Old Java Object**

A POJO is a simple Java object that is not required to extend a particular framework class or implement a framework-specific interface.

The goal is to keep the class simple and primarily focused on representing data/behavior.

Example:

```java
public class Student {

    private int id;
    private String name;

    public Student() {
    }

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

This is a typical POJO.

---

# Common Characteristics of a POJO

A POJO commonly contains:

```text
        POJO
         |
   +-----+------+
   |            |
 Fields       Methods
   |            |
private      getters
fields       setters
              |
          constructors
```

It **may** have:

* private fields
* constructors
* getters/setters
* `equals()`
* `hashCode()`
* `toString()`
* business methods

### Important Correction ⭐

Your original note says:

> POJO = getter/setter + constructor + field + no business logic

The **"no business logic" part is too strict**.

A POJO can contain behavior/business logic. The defining idea is that it is an ordinary Java object without special framework restrictions.

---

# POJO vs JavaBean

These two are often confused in interviews.

## POJO

A POJO is a broad concept.

It does **not necessarily** have to follow JavaBean conventions.

## JavaBean

A JavaBean generally follows conventions such as:

```text
private fields
public getters/setters
public no-argument constructor
Serializable convention is commonly associated with beans
```

Example:

```java
public class Student {

    private String name;

    public Student() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

### Relationship

```text
             Java Objects
                  |
                 POJO
              /        \
       JavaBean      Other POJOs
```

A JavaBean is a **convention-based kind of POJO**, but not every POJO is a JavaBean.

---

# Anemic Model vs Rich Domain Model

This distinction is useful when discussing object-oriented design.

## Anemic Domain Model

An object mainly contains data:

```java
class BankAccount {

    private double balance;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
```

Most logic exists somewhere else.

```text
BankAccount
    ↓
mostly data
```

---

## Rich Domain Model

The object contains both:

```text
state
+
behavior/business rules
```

Example:

```java
class BankAccount {

    private double balance;

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException();
        }

        balance += amount;
    }

    public void withdraw(double amount) {
        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        balance -= amount;
    }

    public double getBalance() {
        return balance;
    }
}
```

Here the object itself controls its business rules.

---

# POJO — Interview Questions

### Q1. What is a POJO?

**Answer:**
POJO stands for Plain Old Java Object. It is a regular Java class that is not required to extend a particular framework class or implement a framework-specific interface. It is commonly used to represent data and/or domain behavior.

---

### Q2. Does a POJO have to contain only fields, getters, and setters?

**Answer:**
No. A POJO can contain constructors, methods, validation logic, business logic, `equals()`, `hashCode()`, and other normal Java features. There is no strict rule that a POJO can contain only fields and getters/setters.

---

### Q3. What is the difference between POJO and JavaBean?

**Answer:**
POJO is a broad concept referring to a simple Java object without special framework requirements. A JavaBean is a POJO that follows specific conventions, such as private properties, accessor methods, and typically a public no-argument constructor.

---

### Q4. What is an Anemic Domain Model?

**Answer:**
An anemic domain model is a design in which domain objects mainly contain data while most business logic is implemented in separate service classes.

---

### Q5. What is a Rich Domain Model?

**Answer:**
A rich domain model places both data and the business behavior that operates on that data inside domain objects, allowing the objects themselves to enforce relevant business rules.

---

## 🔥 Quick Revision Sheet

```text
==================== JAVA QUICK REVISION ====================

PUBLIC CLASS
────────────────────────────────────────
• Maximum 1 public top-level class per file.
• Filename must match public class name.
• Example:
      public class Student
      → Student.java


WRAPPER CLASSES
────────────────────────────────────────
byte    → Byte
short   → Short
int     → Integer
long    → Long
float   → Float
double  → Double
char    → Character
boolean → Boolean

• Primitive → Wrapper = Autoboxing
• Wrapper → Primitive = Unboxing
• Collections use wrapper/reference types.
• Wrapper can represent null.
• Wrapper classes provide utility methods.


INTEGER CACHE ⭐
────────────────────────────────────────
Integer cache guaranteed for:
-128 to 127

Integer a = 100;
Integer b = 100;

a == b          → true (same cached object)

Integer a = 200;
Integer b = 200;

a == b          → don't rely on the result

Use:
a.equals(b)     → value comparison


NULL UNBOXING ⭐
────────────────────────────────────────
Integer x = null;
int y = x;

→ NullPointerException

because:
x.intValue()


ABSTRACT CLASS
────────────────────────────────────────
✓ Constructor
✓ Instance variables
✓ Static members
✓ Concrete methods
✓ Abstract methods
✓ Final methods
✓ Private methods
✓ Zero abstract methods possible

✗ Cannot instantiate directly
✗ Cannot be final
✗ Abstract method cannot be private
✗ Abstract method cannot be final


POJO
────────────────────────────────────────
POJO = Plain Old Java Object

• Ordinary Java object.
• No mandatory framework inheritance.
• Can contain fields, constructors and methods.
• Can contain business logic.
• POJO is broader than JavaBean.

Anemic Model:
→ mostly data

Rich Domain Model:
→ data + business behavior
==============================================================
```
