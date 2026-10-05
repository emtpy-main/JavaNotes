# //============ Generics Part-1 in Java ============//

---

# ⭕ Before Generics → Understand Type Safety

## 🔹 What is Type Safety?

**Type safety** means the compiler ensures that a variable or object is used only with operations that are valid for its data type.

In simple words:

> **Type safety prevents us from performing invalid operations on a particular type of data.**

### Example:

```java
int x = 10;

x + 5;       // ✔️ valid
x.substring(); // ❌ compile-time error
```

Because `int` does not provide `substring()`.

Similarly:

```java
String s = "Hello";

s.length();     // ✔️
s.substring(1); // ✔️
s + " World";   // ✔️
s * 2;          // ❌
```

---

## ⭐ Why Type Safety is Important?

Without type safety:

```text
wrong type of data
       ↓
wrong operation
       ↓
runtime error
       ↓
application may fail
```

With type safety:

```text
wrong operation
       ↓
compiler detects it
       ↓
compile-time error
       ↓
fix before execution
```

---

# ⭕ Upcasting

### Definition:

> **Upcasting is converting a child-class reference into a parent-class reference.**

It is also called:

> **Specific → General**

### Example:

```java
class Animal {
    void eat() {
        System.out.println("Animal eats");
    }
}

class Dog extends Animal {
    void bark() {
        System.out.println("Dog barks");
    }
}
```

Now:

```java
Dog d = new Dog();

Animal a = d;
```

Here:

```text
Dog object
    ↓
Animal reference
```

This is **upcasting**.

We can also directly write:

```java
Animal a = new Dog();
```

---

## ⭐ What members can be accessed after upcasting?

```java
Animal a = new Dog();

a.eat();  // ✔️
a.bark(); // ❌ compile-time error
```

Why?

Because:

```text
Reference type = Animal
```

The compiler checks which members are available in the **reference type**.

So:

```text
Animal reference
      ↓
Can access Animal members
```

---

## ⭐ But what about overridden methods?

This is very important.

```java
class Animal {
    void sound() {
        System.out.println("Animal sound");
    }
}

class Dog extends Animal {
    @Override
    void sound() {
        System.out.println("Dog sound");
    }
}
```

Now:

```java
Animal a = new Dog();

a.sound();
```

Output:

```text
Dog sound
```

Why?

Because **method overriding uses runtime polymorphism**.

```text
Reference type
      ↓
decides what can be accessed

Object type
      ↓
decides which overridden method executes
```

⭐ Remember:

> **Reference type controls accessibility, while actual object type controls overridden method execution.**

---

# ⭕ Downcasting

### Definition:

> **Downcasting is converting a parent-class reference into a child-class reference.**

It is:

```text
General → Specific
```

Example:

```java
Object obj = "Hello";
```

Here:

```text
Object reference
      ↓
String object
```

But:

```java
String s = obj;
```

❌ Compile-time error.

Why?

Because `Object` can refer to **any object**:

```text
Object
 ├── String
 ├── Integer
 ├── Double
 ├── Dog
 └── ...
```

The compiler cannot guarantee that `obj` actually contains a `String`.

Therefore, explicit casting is required:

```java
String s = (String) obj;
```

---

# ⭐ Compile-Time vs Runtime Error

### Case 1: Compile-time error

```java
String s = "Hello";

int x = s;
```

❌ Compiler rejects it immediately.

---

### Case 2: Runtime error

```java
Object obj = 10;

String s = (String) obj;
```

Compilation succeeds because the cast is syntactically possible between related reference types.

But during execution:

```text
actual object = Integer
required type = String
```

So JVM throws:

```text
ClassCastException
```

---

## ⭐ Safe Downcasting with `instanceof`

Instead of blindly casting:

```java
String s = (String) obj;
```

use:

```java
if (obj instanceof String) {
    String s = (String) obj;
    System.out.println(s.length());
}
```

Modern Java also supports pattern matching:

```java
if (obj instanceof String s) {
    System.out.println(s.length());
}
```

---

# ⭐ Important Rule

Upcasting:

```java
Dog d = new Dog();

Animal a = d;
```

✅ Safe and automatic.

Downcasting:

```java
Animal a = new Dog();

Dog d = (Dog) a;
```

✅ Possible, but requires explicit casting.

However:

```java
Animal a = new Animal();

Dog d = (Dog) a;
```

❌ Compiles but throws:

```text
ClassCastException
```

at runtime.

---

# ⭕ Problem Before Generics

Suppose we create:

```java
class Box {
    int value;

    Box(int value) {
        this.value = value;
    }

    int getValue() {
        return value;
    }
}
```

Then:

```java
Box b1 = new Box(35);
```

works.

But:

```java
Box b2 = new Box("Pratik");
```

❌ Doesn't compile because the Box accepts only `int`.

---

## ❌ Problem

If we want different types:

```text
Integer → BoxInteger
String  → BoxString
Double  → BoxDouble
Boolean → BoxBoolean
```

we would have to create multiple classes.

That creates:

```text
code duplication
        +
poor maintainability
```

---

# ⭕ Using `Object` as Universal Type

We can make Box generic using `Object`:

```java
class Box {
    Object value;

    Box(Object value) {
        this.value = value;
    }

    Object getValue() {
        return value;
    }
}
```

Now:

```java
Box b1 = new Box(13);
Box b2 = new Box("Ram");
Box b3 = new Box(true);
```

All are allowed.

Because:

```text
Object
  ↑
  |
Integer
String
Boolean
Double
...
```

Every Java reference type ultimately derives from `Object`.

---

# 🚨 Problem With `Object` as Universal Type

This is exactly where **Generics** become important.

---

## ❌ Problem 1: Type Information is Lost

```java
Box b1 = new Box(13);
Box b2 = new Box("Ram");
Box b3 = new Box(true);
```

From the Box's point of view:

```text
b1.value → Object
b2.value → Object
b3.value → Object
```

The compiler only knows:

```text
Object
```

It doesn't know that:

```text
b1 → Integer
b2 → String
b3 → Boolean
```

---

# ❌ Problem 2: Casting Becomes Necessary

Suppose:

```java
Box b1 = new Box(13);
```

To use the value as Integer:

```java
Integer x = (Integer) b1.getValue();

System.out.println(x + 5);
```

We have to manually cast.

Similarly:

```java
Box b2 = new Box("Hello");

String s = (String) b2.getValue();

System.out.println(s.length());
```

---

# ❌ Problem 3: Wrong Casting Causes Runtime Error

```java
Box b1 = new Box(13);

String s = (String) b1.getValue();
```

Compilation:

```text
✔️ Successful
```

Runtime:

```text
❌ ClassCastException
```

Because:

```text
actual object = Integer
cast requested = String
```

---

# ❌ Problem 4: Wrong Type Can Be Inserted

```java
Box b = new Box(10);

b.value = "Hello";
```

Because `value` is `Object`, this is allowed.

Now the Box originally contained:

```text
Integer
```

but later contains:

```text
String
```

This makes the class too general and loses type safety.

---

# ⭐ Object vs Generics

```text
Object approach
      ↓
Type information lost
      ↓
Casting required
      ↓
Wrong cast possible
      ↓
Runtime errors


Generics approach
      ↓
Type information preserved
      ↓
Casting usually handled by compiler
      ↓
Wrong type rejected
      ↓
Compile-time errors
```

---

# ⭕ What are Generics?

### Definition:

> **Generics are a feature in Java that allows classes, interfaces, and methods to work with different data types while providing compile-time type safety.**

In simple words:

> **Generics allow us to write reusable code while specifying what type of data it should work with.**

Example:

```java
Box<Integer>
Box<String>
Box<Double>
```

Same class:

```java
Box<T>
```

Different type arguments.

---

# ⭐ Advantages of Generics

```text
1. Type Safety
2. Compile-time error detection
3. No unnecessary explicit casting
4. Code reusability
5. Better readability
6. Reduces runtime ClassCastException
```

---

# ⭕ Generic Class

A class that uses a **type parameter** is called a generic class.

```java
class Box<T> {

    private T value;

    Box(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
```

Here:

```text
class Box<T>
          ↑
     Type Parameter
```

`T` is **not a real data type**.

It is a placeholder for a type.

---

# ⭐ Creating Generic Objects

```java
Box<Integer> b1 = new Box<>(80);
Box<String> b2 = new Box<>("Hello");
Box<Boolean> b3 = new Box<>(true);
```

Here:

```text
Box<Integer>
     ↑
Type Argument
```

---

## ⭐ Type Parameter vs Type Argument

This distinction is commonly asked in interviews.

### Declaration:

```java
class Box<T>
```

`T` → **Type Parameter**

### Usage:

```java
Box<Integer>
```

`Integer` → **Type Argument**

```text
T
↓
Type Parameter

Integer
↓
Type Argument
```

---

# ⭐ Diamond Operator `<>`

Instead of:

```java
Box<Integer> b1 =
    new Box<Integer>(80);
```

we can write:

```java
Box<Integer> b1 =
    new Box<>(80);
```

The compiler infers the type argument from the left side.

This is called the:

> **Diamond Operator**

---

# 🚨 Raw Type

You wrote:

```java
Box b1 = new Box(8);
```

This is called a **raw type**.

It means:

```text
Generic type used without specifying type argument
```

Example:

```java
Box b = new Box(8);
```

The compiler may issue an unchecked/raw-type warning.

### Better:

```java
Box<Integer> b = new Box<>(8);
```

---

# ⭐ Generic Type Safety

Consider:

```java
Box<Integer> b1 = new Box<>(10);
```

Now:

```java
b1.setValue(20);      // ✔️
b1.setValue(30);      // ✔️
b1.setValue("Hello"); // ❌ compile-time error
```

The compiler knows:

```text
b1 → Box<Integer>
```

Therefore only Integer is allowed.

---

# ⭐ Type Information is Preserved

```java
Box<Integer> b1 = new Box<>(10);

int x = b1.getValue();
```

No explicit cast is needed.

Conceptually, the compiler knows:

```text
b1.getValue()
      ↓
Integer
      ↓
auto-unboxing
      ↓
int
```

Compare this with Object:

```java
Box b1 = new Box(10);

int x = (Integer) b1.getValue();
```

Here explicit casting is required.

---

# ⭐ Example: Compile-Time Error Instead of Runtime Error

### Using Object:

```java
class Box {
    Object value;

    Box(Object value) {
        this.value = value;
    }

    Object getValue() {
        return value;
    }
}
```

```java
Box b = new Box(10);

String s = (String) b.getValue();
```

```text
Compile-time → ✔️
Runtime      → ❌ ClassCastException
```

---

### Using Generics:

```java
class Box<T> {

    T value;

    Box(T value) {
        this.value = value;
    }

    T getValue() {
        return value;
    }
}
```

```java
Box<Integer> b = new Box<>(10);

String s = b.getValue();
```

```text
Compile-time → ❌
Runtime      → never reached
```

⭐ This is one of the **main reasons Generics were introduced**.

---

# ⚠️ Correction in Your Original Example

You wrote:

```java
b2.getValue() + 5;
```

where:

```java
Box<String> b2 = new Box<>("Hello");
```

This does **not** produce:

```text
Hello5
```

with `+ 5`.

Java's `+` operator performs String concatenation only when at least one operand is a String.

But:

```java
b2.getValue() + 5
```

is effectively:

```java
"Hello" + 5
```

so **yes, the result is `"Hello5"`**.

However, this:

```java
b2.getValue() * 5
```

❌ gives a compile-time error because String cannot be multiplied by an integer.

---

# ⭕ Generic Pair Class

Generics can have multiple type parameters.

```java
class Pair<T, U> {

    T first;
    U second;

    Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }
}
```

Usage:

```java
Pair<Integer, String> p1 =
    new Pair<>(23, "Pratik");

System.out.println(
    p1.first + " " + p1.second
);
```

Output:

```text
23 Pratik
```

Here:

```text
T → Integer
U → String
```

---

# ⭐ Naming Convention for Type Parameters

Java doesn't enforce these names, but standard conventions are:

```text
T → Type
E → Element
K → Key
V → Value
N → Number
R → Return type
S, U, V → Additional type parameters
```

Example:

```java
class Pair<K, V> {
    K key;
    V value;
}
```

This is especially common for collections.

---

# ⭕ Generic Methods

A **generic method** is a method that declares its own type parameter.

Example:

```java
public static <T> T getResult(T x) {
    return x;
}
```

Notice:

```text
static <T> T getResult(T x)
       ↑
 type parameter declaration
```

---

# ⭐ Why `<T>` Comes Before Return Type?

Correct:

```java
public static <T> T getResult(T x)
```

Not:

```java
public static T <T> getResult(T x) // ❌
```

The generic type parameter declaration comes before the return type.

---

# ⭕ Type Inference

Java can automatically determine the type parameter from the arguments and/or target context.

Example:

```java
Integer x = getResult(23);
```

The compiler infers:

```text
T = Integer
```

So conceptually:

```java
Integer getResult(Integer x)
```

---

Another example:

```java
String s = getResult("Hello");
```

Compiler infers:

```text
T = String
```

---

# ⭐ Generic Method with Multiple Types

```java
public static <T, U>
void printPair(T first, U second) {

    System.out.println(first + " " + second);
}
```

Call:

```java
printPair(23, "Pratik");
```

Compiler infers:

```text
T = Integer
U = String
```

Another call:

```java
printPair("Hello", true);
```

Compiler infers:

```text
T = String
U = Boolean
```

---

# ⭕ Generic Method vs Generic Class

### Generic Class

```java
class Box<T> {
    T value;
}
```

➡️ Type parameter belongs to the **class**.

---

### Generic Method

```java
static <T> void print(T value) {
    System.out.println(value);
}
```

➡️ Type parameter belongs to the **method**.

---

# ⭐ Important Concept

A generic class can have a non-generic method:

```java
class Box<T> {

    T value;

    void display() {
        System.out.println(value);
    }
}
```

And a non-generic class can have a generic method:

```java
class Utility {

    static <T> void print(T value) {
        System.out.println(value);
    }
}
```

---

# ⭕ Bounds on Generics

Sometimes we don't want to allow **every type**.

For example:

```java
Box<Integer>
Box<Double>
Box<Float>
Box<Long>
```

are valid for our requirement.

But:

```java
Box<String>
Box<Boolean>
```

should not be allowed.

We can use a **bounded type parameter**.

---

# ⭐ Upper Bound

Syntax:

```java
<T extends Number>
```

Meaning:

> `T` must be `Number` or a subclass of `Number`.

Example:

```java
class Box<T extends Number> {

    T value;

    Box(T value) {
        this.value = value;
    }

    void printDouble() {
        System.out.println(value.doubleValue());
    }
}
```

Now:

```java
Box<Integer> b1 = new Box<>(80);
Box<Double> b2 = new Box<>(89.22);
Box<Float> b3 = new Box<>(12.5f);
Box<Long> b4 = new Box<>(100L);
```

All valid.

But:

```java
Box<String> b5 = new Box<>("Pratik");
```

❌ Compile-time error.

Because:

```text
String
  ❌
  |
Number
  ↑
Integer
Double
Float
Long
```

---

# ⭐ Why `extends Number`?

Because the wrapper classes:

```text
Integer
Double
Float
Long
Short
Byte
```

extend:

```text
Number
```

Therefore:

```java
T extends Number
```

allows operations available through `Number`.

For example:

```java
value.doubleValue();
value.intValue();
value.longValue();
```

---

# ⭐ Bound Gives Access to Parent Members

Without bound:

```java
class Box<T> {

    T value;

    void print() {
        // value.doubleValue(); ❌
    }
}
```

Why?

Because `T` could be anything:

```text
String
Integer
Dog
Student
...
```

But:

```java
class Box<T extends Number> {

    T value;

    void print() {
        System.out.println(value.doubleValue());
    }
}
```

Now compiler knows:

```text
T
↓
Number or subclass
↓
doubleValue() exists
```

So it is allowed.

---

# ⭕ Multiple Bounds

A generic type can have multiple bounds.

Syntax:

```java
<T extends ClassName & Interface1 & Interface2>
```

⭐ Important rule:

> **If a class bound exists, it must come first.**

Correct:

```java
<T extends Animal & Swimmable & Runnable>
```

Incorrect:

```java
<T extends Swimmable & Animal>
```

if `Animal` is a class.

---

# Example

```java
class Animal {

    void display() {
        System.out.println("Displaying Animal");
    }
}
```

Interface:

```java
interface Swimmable {

    void swim();
}
```

Dog:

```java
class Dog extends Animal {

}
```

Fish:

```java
class Fish extends Animal implements Swimmable {

    @Override
    public void swim() {
        System.out.println("Fish is swimming...");
    }
}
```

Now:

```java
class Box<T extends Animal & Swimmable> {

    T value;

    Box(T value) {
        this.value = value;
    }
}
```

---

## Usage

```java
Box<Fish> b1 = new Box<>(new Fish());
```

✅ Valid.

Because:

```text
Fish
 ↓
extends Animal
 ↓
implements Swimmable
```

But:

```java
Box<Dog> b2 = new Box<>(new Dog());
```

❌ Compile-time error.

Because:

```text
Dog
 ↓
Animal ✅

Dog
 ↓
Swimmable ❌
```

---

# ⭐ Your Question: What if I have no Class Bound but want Interface Bounds?

This is important.

You **do not need a class bound** to use interface bounds.

You can simply write:

```java
<T extends Swimmable>
```

Example:

```java
class Box<T extends Swimmable> {

    T value;

    Box(T value) {
        this.value = value;
    }

    void makeSwim() {
        value.swim();
    }
}
```

Now:

```java
Box<Fish> b = new Box<>(new Fish());
```

✅ Valid.

But:

```java
Box<Dog> b = new Box<>(new Dog());
```

❌ Invalid if `Dog` does not implement `Swimmable`.

---

# ⭐ Multiple Interface Bounds

If there is no class bound:

```java
<T extends Interface1 & Interface2 & Interface3>
```

Example:

```java
interface A {
    void methodA();
}

interface B {
    void methodB();
}

class Test<T extends A & B> {
    T obj;

    Test(T obj) {
        this.obj = obj;
    }
}
```

Now `T` must implement **both** interfaces.

---

# ⭐ Important: `extends` in Generics

This sometimes confuses beginners.

We write:

```java
<T extends Number>
```

even when interfaces are involved:

```java
<T extends Swimmable>
```

Why?

Because in generic bounds, `extends` means:

```text
"must be a subtype of"
```

It does **not** mean the type must literally use the `extends` keyword.

Therefore:

```java
<T extends Animal>
```

and:

```java
<T extends Swimmable>
```

are both correct.

---

# 🚨 Important: Primitive Types Cannot Be Used as Generic Arguments

This is a very important missing point.

❌ Not allowed:

```java
Box<int> b;
```

Generics work with **reference types**, not primitive types.

Use wrapper classes:

```java
Box<Integer> b;
Box<Double> d;
Box<Boolean> flag;
```

Because:

```text
int      → Integer
double   → Double
boolean  → Boolean
char     → Character
long     → Long
float    → Float
short    → Short
byte     → Byte
```

Autoboxing handles conversion:

```java
Box<Integer> b = new Box<>(10);
```

The `10` is automatically boxed into `Integer`.

---

# ⭐ Type Erasure — Very Important

Java implements generics using **type erasure**.

Conceptually:

```java
Box<Integer>
Box<String>
```

do not result in completely separate runtime classes like:

```text
BoxInteger.class
BoxString.class
```

Instead, generic type information is primarily enforced by the compiler and erased/translated for runtime representation.

This is why:

```java
Box<Integer>
```

and:

```java
Box<String>
```

have the same raw runtime class:

```java
Box.class
```

### Important consequence:

You generally cannot do:

```java
if (obj instanceof Box<Integer>) // ❌
```

because Java cannot test the parameterized type argument that way at runtime.

But:

```java
if (obj instanceof Box<?>) // ✅
```

is allowed.

---

# ⭐ Generics Do Not Work Directly With Static Type Parameters

Example:

```java
class Box<T> {

    static T value; // ❌
}
```

Why?

Because `static` belongs to the **class**, while `T` belongs to an **object/type parameterization**.

A static field cannot depend on an instance-specific type parameter.

---

# ⭐ Generic Array Problem

You cannot directly write:

```java
T[] arr = new T[10]; // ❌
```

because of type erasure.

Common workaround:

```java
T[] arr = (T[]) new Object[10];
```

but this involves an unchecked cast and must be handled carefully.

---
```
                                Generic Array
                                    |
                                    ↓
                            T[] arr = new T[10]
                                    |
                                    X
                            Java doesn't allow it
                                    |
                                    ↓
                                Type Erasure
                                    |
                                    ↓
                        Runtime doesn't know T
                                    |
                                    ↓
                        ┌─────────────────────┐
                        │                     │
                        ↓                     ↓
                Common workaround       Need real T[]
                        │                     │
                        ↓                     ↓
                (T[]) new Object[10]     Array.newInstance()
                        │                     │
                        ↓                     ↓
                    Object[]             String[] / Integer[]
                        │
                        ↓
                unchecked cast
```  
# 🧠 Generics Part-1 Final Revision

```text
                         GENERICS
                            |
          ┌─────────────────┴─────────────────┐
          |                                   |
      TYPE SAFETY                         REUSABILITY
          |
   ┌──────┴───────┐
   |              |
 Object         Generics
 approach       approach
   |              |
Type lost       Type preserved
   |              |
Casting         No unnecessary
required        explicit casting
   |              |
Runtime         Compile-time
errors          type checking
```

### Generic Class

```java
class Box<T> {
    T value;
}
```

### Generic Object

```java
Box<Integer> b = new Box<>(10);
```

### Generic Method

```java
static <T> T get(T value) {
    return value;
}
```

### Multiple Type Parameters

```java
class Pair<T, U> {
    T first;
    U second;
}
```

### Upper Bound

```java
<T extends Number>
```

### Multiple Bounds

```java
<T extends Animal & Swimmable>
```

### Interface-only Bound

```java
<T extends Swimmable>
```

### Primitive Types

```text
Box<int>       ❌
Box<Integer>   ✅
```

### Raw Type

```java
Box b = new Box(10);     // ⚠️ raw type
Box<Integer> b = new Box<>(10); // ✅
```

### Type Parameter vs Type Argument

```java
class Box<T>
          ↑
    Type Parameter

Box<Integer>
     ↑
Type Argument
```

---

# 🔥 Important Interview Questions

### Q1. What are Generics in Java?

**Answer:**

> Generics are a Java feature that allows classes, interfaces, and methods to operate on different types while providing compile-time type safety and reducing the need for explicit casting.

---

### Q2. Why were Generics introduced?

**Answer:**

> Generics were introduced to provide type safety at compile time, reduce explicit casting, improve code reusability, and move many type-related errors from runtime to compile time.

---

### Q3. What is type safety?

**Answer:**

> Type safety ensures that operations performed on an object are valid for its declared type, allowing the compiler to detect many invalid operations before the program runs.

---

### Q4. What is the difference between `Object` and Generics?

**Answer:**

> Using `Object` allows different types to be stored but loses specific type information and often requires explicit casting. Generics preserve type information at compile time and provide stronger type safety without unnecessary casting.

---

### Q5. What is a generic class?

**Answer:**

> A generic class is a class that declares one or more type parameters and can operate on different data types while maintaining compile-time type safety.

Example:

```java
class Box<T> {
    T value;
}
```

---

### Q6. What is a type parameter?

**Answer:**

> A type parameter is a placeholder for a type declared by a generic class, interface, or method, such as `T` in `class Box<T>`.

---

### Q7. What is a type argument?

**Answer:**

> A type argument is the actual type supplied to a generic type, such as `Integer` in `Box<Integer>`.

---

### Q8. What is a raw type?

**Answer:**

> A raw type is a generic type used without specifying its type argument, such as `Box b = new Box(10)`. Raw types should generally be avoided because they weaken compile-time type safety and may produce unchecked warnings.

---

### Q9. What is a bounded type parameter?

**Answer:**

> A bounded type parameter restricts the types that can be used as type arguments. For example, `<T extends Number>` allows only `Number` and its subclasses.

---

### Q10. Can an interface be used as a generic bound?

**Answer:**

> Yes. An interface can be used as a generic bound using `extends`, such as `<T extends Swimmable>`, which means that `T` must implement or extend `Swimmable`.

---

### Q11. Can we have multiple bounds?

**Answer:**

> Yes. A generic type parameter can have multiple bounds. If a class bound is present, it must appear first, followed by interface bounds.

```java
<T extends Animal & Swimmable & Runnable>
```

---

### Q12. Can primitive types be used with Generics?

**Answer:**

> No. Java Generics work with reference types, not primitive types. Wrapper classes such as `Integer`, `Double`, and `Boolean` must be used instead.

---

### Q13. What is a generic method?

**Answer:**

> A generic method is a method that declares its own type parameter independently of whether its containing class is generic.

```java
static <T> T get(T value) {
    return value;
}
```

---

### Q14. What is type inference in Generics?

**Answer:**

> Type inference is the compiler's ability to determine the appropriate generic type argument from the method arguments, target type, or surrounding context without requiring the programmer to explicitly specify it in every case.

---

### Q15. What is the difference between upcasting and downcasting?

**Answer:**

> Upcasting converts a child reference to a parent reference and is generally safe and implicit. Downcasting converts a parent reference to a child reference, requires an explicit cast, and can throw `ClassCastException` if the actual object is not an instance of the target child type.

---

### Q16. Why are compile-time errors preferred over runtime errors?

**Answer:**

> Compile-time errors are detected before program execution, allowing developers to fix problems earlier and preventing many invalid operations from reaching production runtime.

---

### Q17. What is type erasure?

**Answer:**

> Type erasure is the mechanism by which Java removes most generic type-parameter information during compilation while preserving compile-time type checking. This allows generic code to remain compatible with Java's older non-generic code.

---

### Q18. Can we create `new T()` inside a generic class?

**Answer:**

> No, Java does not allow direct instantiation of a type parameter using `new T()` because the actual type information is not available in the required form at runtime due to type erasure.

---

### Q19. Can we create an array of a generic type directly?

**Answer:**

> No, Java does not allow direct creation of a generic array such as `new T[10]` because generic type information is erased at runtime.

---

### Q20. Why does `Box<Integer>` not mean that a new class called `BoxInteger` is created?

**Answer:**

> Java uses type erasure for generics, so `Box<Integer>` and `Box<String>` are different parameterized views of the same generic class rather than separate runtime classes.

---

### ⭐ One-line Interview Revision

```text
Generics = Reusable code + Compile-time Type Safety
```

```text
Object
  ↓
Type information lost
  ↓
Casting required
  ↓
Runtime ClassCastException possible


Generics
  ↓
Type information preserved at compile time
  ↓
Casting usually unnecessary
  ↓
Wrong type detected by compiler
```

This gives you a strong **Generics Part-1 foundation**: Type Safety → Upcasting/Downcasting → Problems with `Object` → Generic Classes → Type Parameters/Arguments → Generic Methods → Type Inference → Bounds → Multiple Bounds → Interface Bounds → Raw Types → Primitive restriction → Type Erasure.
