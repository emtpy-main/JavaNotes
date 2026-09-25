Your notes cover the most important `Object` concepts, but there are a few **important corrections**, especially around `equals()`, `hashCode()`, `clone()`, and `finalize()`. I’ve also added the missing `wait()/notify()` relationship and corrected the `instanceof` and `equals()` examples.

# 🧩 Object Class in Java

## 1. Overview

`Object` is the **root class of the Java class hierarchy**.

It belongs to:

```java
java.lang.Object
```

Since `java.lang` is automatically available, we normally don't need:

```java
import java.lang.Object;
```

### Important rule

> Every Java class directly or indirectly inherits from `Object`, except interfaces.

For example:

```java
class Student {
}
```

is conceptually similar to:

```java
class Student extends Object {
}
```

So:

```java
Student s = new Student();
```

also means that `s` has access to methods inherited from `Object`.

---

# 2. Why Does Java Have an `Object` Root Class?

The main reason is to provide a **common type and common behavior** for all objects.

### 1. Common methods

Every object should have basic operations such as:

* converting itself to a string
* checking equality
* generating a hash code
* identifying its runtime class
* synchronization/waiting operations

These common methods are defined in `Object`.

### 2. Polymorphism

Because every class inherits from `Object`, an `Object` reference can refer to an instance of any class.

```java
Object obj = new Student();
```

Here:

```text
Object reference
       ↓
   Student object
```

This is an example of **upcasting**.

It allows APIs to work with objects of many different types.

For example:

```java
void printObject(Object obj) {
    System.out.println(obj);
}
```

can accept:

```java
printObject(new Student());
printObject(new String("Hello"));
printObject(new Integer(10));
```

---

# 3. Object Class Methods

`Object` provides methods for several common purposes.

```text
Object
│
├── Object representation
│     └── toString()
│
├── Equality & hashing
│     ├── equals()
│     └── hashCode()
│
├── Runtime type information
│     └── getClass()
│
├── Object copying
│     └── clone()
│
├── Thread coordination
│     ├── wait()
│     ├── notify()
│     └── notifyAll()
│
└── finalize()  ⚠️ deprecated / obsolete
```

> ⚠️ `finalize()` is deprecated and should not be used in modern Java. We'll discuss why below.

---

# 4. `toString()`

## Definition

`toString()` returns a **string representation of an object**.

Its signature is:

```java
public String toString()
```

Example:

```java
Student s = new Student("Pratik", 21);

System.out.println(s.toString());
```

---

## Default Implementation

If your class does not override `toString()`, the implementation inherited from `Object` produces a string based on:

```text
ClassName@hexadecimalHashCode
```

For example:

```text
Student@78a72d
```

Conceptually, it is based on:

```java
getClass().getName() + "@" +
Integer.toHexString(hashCode())
```

### Important

The hexadecimal part is related to the object's hash code, but don't memorize the output format as a strict guarantee for every custom override. It describes the standard `Object.toString()` implementation.

---

# 5. Why Override `toString()`?

Suppose:

```java
class Student {

    String name;
    int age;

    Student(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
```

Then:

```java
Student s = new Student("Pratik", 21);

System.out.println(s);
```

may produce:

```text
Student@5acf9800
```

This isn't very useful.

So override it:

```java
@Override
public String toString() {
    return "Student{name='" + name + "', age=" + age + "}";
}
```

Now:

```java
System.out.println(s);
```

could produce:

```text
Student{name='Pratik', age=21}
```

### ⭐ Interview point

When you write:

```java
System.out.println(object);
```

Java effectively uses:

```java
object.toString()
```

to obtain the object's string representation.

---

# 6. `equals()`

## Definition

`equals()` is used to determine whether two objects are **logically equal**, according to the equality definition of the class.

Signature:

```java
public boolean equals(Object obj)
```

The default implementation in `Object` performs **reference identity comparison**.

Conceptually:

```java
public boolean equals(Object obj) {
    return this == obj;
}
```

---

# 7. `==` vs `equals()`

This is one of the most important Java interview questions.

For objects:

```java
==
```

checks whether two references refer to the **same object**.

Whereas:

```java
equals()
```

can be overridden to check **logical/content equality**.

Example:

```java
Student s1 = new Student("Pratik", 21);
Student s2 = new Student("Pratik", 21);
```

Initially, if `Student` doesn't override `equals()`:

```java
s1 == s2
```

returns:

```text
false
```

and:

```java
s1.equals(s2)
```

also returns:

```text
false
```

because `Object.equals()` uses reference identity.

But:

```java
s1 == s1
```

and:

```java
s1.equals(s1)
```

both return:

```text
true
```

---

# 8. Overriding `equals()`

Suppose we want two `Student` objects to be considered equal when their:

* name
* age

are the same.

We can override `equals()`:

```java
@Override
public boolean equals(Object obj) {

    if (this == obj)
        return true;

    if (obj == null)
        return false;

    if (getClass() != obj.getClass())
        return false;

    Student s = (Student) obj;

    return age == s.age &&
           Objects.equals(name, s.name);
}
```

Notice the important steps.

---

# 9. Why Do We Write `Object obj`?

The method inherited from `Object` has this signature:

```java
public boolean equals(Object obj)
```

Therefore, when overriding it, the parameter must be:

```java
Object obj
```

We cannot correctly override it with:

```java
public boolean equals(Student obj)
```

because that would be **method overloading**, not overriding.

---

# 10. Why Cast `Object` to `Student`?

Consider:

```java
Object obj = new Student("Pratik", 21);
```

The compiler only knows:

```text
obj → Object reference
```

It doesn't allow us to directly access:

```java
obj.name
obj.age
```

because those members aren't defined in `Object`.

After checking:

```java
if (getClass() != obj.getClass())
    return false;
```

we know that `obj` refers to a `Student`.

Therefore:

```java
Student s = (Student) obj;
```

allows us to access:

```java
s.name
s.age
```

---

# 11. Your `equals()` Code Had an Important Error

You wrote something like:

```java
if(obj.getClass() == this.getClass())
    return false;
```

❌ This is reversed.

It should be:

```java
if (obj.getClass() != this.getClass())
    return false;
```

Because we want to reject the object when its class is **different**.

Correct logic:

```java
if (obj == null)
    return false;

if (getClass() != obj.getClass())
    return false;
```

---

# 12. Why Use `Objects.equals()` for Strings?

Avoid:

```java
name == s.name
```

when you want to compare String contents.

Use:

```java
Objects.equals(name, s.name)
```

or:

```java
name.equals(s.name)
```

with appropriate null handling.

Why?

```java
String a = new String("Pratik");
String b = new String("Pratik");

System.out.println(a == b);       // false
System.out.println(a.equals(b));  // true
```

`==` compares references.

`String.equals()` compares content.

---

# 13. `equals()` Contract

This is **very important for SDE interviews**.

The `equals()` contract requires the relation to be:

### Reflexive

```text
x.equals(x) → true
```

### Symmetric

```text
x.equals(y) == y.equals(x)
```

### Transitive

If:

```text
x.equals(y)
y.equals(z)
```

then:

```text
x.equals(z)
```

### Consistent

Repeated calls should return the same result as long as the relevant state hasn't changed.

### Non-null

```text
x.equals(null) → false
```

---

# 14. `hashCode()`

## Definition

`hashCode()` returns an `int` hash value associated with an object.

Signature:

```java
public int hashCode()
```

It is heavily used by hash-based collections such as:

```text
HashMap
HashSet
Hashtable
```

---

# 15. Most Important `hashCode()` Rule

⭐ Memorize this:

> **If two objects are equal according to `equals()`, they must have the same hash code.**

In mathematical form:

```text
a.equals(b) == true
        ↓
a.hashCode() == b.hashCode()
```

But the reverse is **not necessarily true**.

```text
a.hashCode() == b.hashCode()
        ↓
Does NOT guarantee
a.equals(b) == true
```

This situation is called a **hash collision**.

---

# 16. Why Are `equals()` and `hashCode()` Connected?

Suppose:

```java
Student s1 = new Student("Pratik", 19);
Student s2 = new Student("Pratik", 19);
```

If:

```java
s1.equals(s2)
```

returns:

```text
true
```

then:

```java
s1.hashCode() == s2.hashCode()
```

**must** also be true.

Otherwise, hash-based collections could fail to locate logically equal objects correctly.

---

# 17. Overriding `equals()` Without `hashCode()`

This is a **major interview trap**.

Suppose you override:

```java
equals()
```

but don't override:

```java
hashCode()
```

Then two logically equal objects may have different hash codes.

This violates the `equals()`/`hashCode()` contract.

Therefore:

> **Whenever you override `equals()`, you should normally override `hashCode()` consistently.**

Example:

```java
@Override
public int hashCode() {
    return Objects.hash(name, age);
}
```

---

# 18. `Objects.hash()`

Java provides the `Objects` utility class in:

```java
java.util.Objects
```

It provides:

```java
Objects.hash(...)
```

Example:

```java
@Override
public int hashCode() {
    return Objects.hash(name, age);
}
```

This creates a hash based on the supplied values.

### Don't confuse:

```java
Object
```

with:

```java
Objects
```

| Class     | Package     | Purpose                     |
| --------- | ----------- | --------------------------- |
| `Object`  | `java.lang` | Root class                  |
| `Objects` | `java.util` | Utility methods for objects |

---

# 19. `getClass()`

`getClass()` returns the **runtime class** of an object.

Signature:

```java
public final Class<?> getClass()
```

Example:

```java
Student s = new Student();

System.out.println(s.getClass());
```

Output may be:

```text
class Student
```

You can also use:

```java
System.out.println(s.getClass().getName());
```

which may output:

```text
Student
```

depending on the package.

---

# 20. Why is `getClass()` `final`?

`getClass()` is declared `final`, so it cannot be overridden.

This ensures that the runtime class information provided by this method cannot be changed through method overriding.

---

# 21. What is `Class<?>`?

This is another concept worth understanding.

Java has a class named:

```java
Class
```

A `Class` object represents runtime information about a class.

For example:

```java
Student.class
```

represents the `Class` object describing `Student`.

Similarly:

```java
s.getClass()
```

returns the runtime `Class` object for the object referenced by `s`.

---

# 22. `instanceof` Operator

`instanceof` checks whether an object is an instance of a particular type.

Syntax:

```java
object instanceof ClassName
```

Example:

```java
Student s = new Student();

System.out.println(s instanceof Student);
```

Output:

```text
true
```

Since `Student` inherits from `Object`:

```java
System.out.println(s instanceof Object);
```

also gives:

```text
true
```

### Important

The correct keyword is:

```java
instanceof
```

not:

```text
instanceOf
```

---

# 23. `getClass()` vs `instanceof`

Another excellent interview question.

Suppose:

```java
class Animal {}
class Dog extends Animal {}
```

and:

```java
Animal a = new Dog();
```

Then:

```java
a instanceof Animal
```

→ `true`

```java
a instanceof Dog
```

→ `true`

But:

```java
a.getClass() == Animal.class
```

→ `false`

because the **actual runtime class is `Dog`**.

```java
a.getClass() == Dog.class
```

→ `true`

### Difference

| `instanceof`                   | `getClass()`                    |
| ------------------------------ | ------------------------------- |
| Checks type compatibility      | Returns exact runtime class     |
| Considers inheritance          | Gives actual runtime class      |
| Can return true for superclass | Exact class comparison required |

---

# 24. `clone()`

`clone()` is used to create a copy of an object.

Its declaration in `Object` is:

```java
protected native Object clone()
        throws CloneNotSupportedException;
```

Conceptually, it creates another object with copied field values.

---

# 25. `Cloneable`

To use `Object.clone()` through the conventional mechanism, the class should implement:

```java
Cloneable
```

Example:

```java
class Student implements Cloneable {

    int age;
    String name;

    @Override
    public Student clone() throws CloneNotSupportedException {
        return (Student) super.clone();
    }
}
```

Then:

```java
Student s1 = new Student();

Student s2 = s1.clone();
```

---

# 26. Why is `Cloneable` Called a Marker Interface?

`Cloneable` does not declare a method that you have to implement.

Conceptually:

```java
public interface Cloneable {
}
```

An interface with no methods or fields intended to mark a class with a particular capability/property is called a **marker interface**.

Examples include:

```text
Cloneable
Serializable
```

---

# 27. Why Does `Object.clone()` Throw `CloneNotSupportedException`?

The `Object.clone()` mechanism checks whether the object's class implements `Cloneable`.

If it doesn't, cloning through `Object.clone()` can throw:

```java
CloneNotSupportedException
```

So:

```java
class Student {
}
```

doesn't automatically mean you can successfully call `super.clone()`.

---

# 28. Shallow Copy Using `clone()`

A very important point:

> The default `Object.clone()` performs a **shallow field-by-field copy**.

Suppose:

```java
class Student implements Cloneable {

    int age;
    College college;

    @Override
    public Student clone()
            throws CloneNotSupportedException {
        return (Student) super.clone();
    }
}
```

Then:

```java
Student s2 = s1.clone();
```

produces:

```text
s1 ───────────────→ Student Object A
                       │
                       └──→ College Object X


s2 ───────────────→ Student Object B
                       │
                       └──→ College Object X
```

There are two `Student` objects, but they reference the **same `College` object**.

That's shallow copying.

---

# 29. How to Perform Deep Copy with `clone()`?

If the nested object is mutable, you would need to clone/copy it as well.

Conceptually:

```java
@Override
public Student clone() throws CloneNotSupportedException {

    Student copy = (Student) super.clone();

    copy.college = this.college.clone();

    return copy;
}
```

Then:

```text
s1 → Student A → College X

s2 → Student B → College Y
```

However, for modern Java development, manually implementing `Cloneable`/`clone()` is often avoided because the API has several design limitations.

Alternatives include:

* copy constructors
* static factory methods
* explicit copy methods
* immutable objects

---

# 30. `finalize()`

Your note correctly identifies this as related to garbage collection, but there's an important modern Java detail.

Historically, `Object` provided:

```java
protected void finalize()
```

which could be invoked as part of the garbage collector's finalization mechanism.

However:

> ⚠️ **Finalization is deprecated for removal and should not be used in modern Java code.**

Why?

### Problems

* execution timing is unpredictable
* it can delay resource reclamation
* it can introduce performance problems
* it creates reliability and security concerns
* it is not guaranteed to run before program termination

### Don't use `finalize()` for resource management.

Instead use:

```text
try-with-resources
```

for resources such as files, sockets, and database connections.

---

# 31. `wait()`, `notify()`, `notifyAll()`

These are also methods inherited from `Object`.

They are related to **thread coordination**.

```java
wait()
notify()
notifyAll()
```

are used with an object's **monitor/lock**.

Example concept:

```text
Thread A
   ↓
wait()
   ↓
releases monitor and waits

Thread B
   ↓
notify()
   ↓
wakes a waiting thread
```

### Important

These methods are not general-purpose thread control methods.

They are associated with an object's monitor and are normally used inside synchronized code.

For example:

```java
synchronized (obj) {
    obj.wait();
}
```

and:

```java
synchronized (obj) {
    obj.notify();
}
```

We'll cover these properly in **Multithreading**.

---

# 32. Complete `Object` Method Summary

| Method        | Purpose                   | Important?    |
| ------------- | ------------------------- | ------------- |
| `toString()`  | String representation     | ⭐⭐⭐           |
| `equals()`    | Logical equality          | ⭐⭐⭐⭐⭐         |
| `hashCode()`  | Hash value                | ⭐⭐⭐⭐⭐         |
| `getClass()`  | Runtime class information | ⭐⭐⭐⭐          |
| `clone()`     | Object copying            | ⭐⭐⭐           |
| `wait()`      | Wait for notification     | ⭐⭐⭐⭐          |
| `notify()`    | Wake one waiting thread   | ⭐⭐⭐⭐          |
| `notifyAll()` | Wake waiting threads      | ⭐⭐⭐⭐          |
| `finalize()`  | Finalization mechanism    | ⚠️ Deprecated |

---

# 33. Complete `Object` Relationship

```text
                         Object
                           │
       ┌───────────────────┼───────────────────┐
       │                   │                   │
   Representation      Equality             Type
       │                   │                   │
   toString()       equals()/hashCode()    getClass()
       
       ┌───────────────────┴───────────────────┐
       │                                       │
    Copying                               Thread Coordination
       │                                       │
    clone()                           wait/notify/notifyAll

       │
    Legacy
       │
   finalize() ⚠️
```

---

# 🎯 Interview Questions

## 🟢 Basic

### 1. What is the `Object` class?

**Answer:**
`Object` is the root class of the Java class hierarchy and belongs to the `java.lang` package. Every Java class directly or indirectly inherits from `Object`, so its methods are available to all objects.

---

### 2. Why is `Object` the parent of all classes?

**Answer:**
It provides a common root type and common behavior for all Java objects, such as equality checking, string representation, hash code generation, runtime type information, and thread coordination.

---

### 3. What are the important methods of `Object`?

**Answer:**

```text
toString()
equals()
hashCode()
getClass()
clone()
wait()
notify()
notifyAll()
```

`finalize()` historically existed as well, but it is deprecated and should not be used.

---

### 4. Can an `Object` reference hold a `Student` object?

**Answer:**
Yes.

```java
Object obj = new Student();
```

This is valid because `Student` directly or indirectly extends `Object`. It is an example of upcasting.

---

# 🟡 Intermediate

### 5. What is the difference between `==` and `equals()`?

**Answer:**
For object references, `==` checks whether two references refer to the same object. `equals()` is a method that can be overridden to define logical equality based on object state.

---

### 6. What is the default implementation of `Object.equals()`?

**Answer:**
The default implementation compares object identity, effectively behaving like:

```java
return this == obj;
```

Therefore, two different objects are not equal unless the class overrides `equals()` to define logical equality.

---

### 7. What is the contract between `equals()` and `hashCode()`?

**Answer:**
If two objects are equal according to `equals()`, they must return the same hash code. However, two objects can have the same hash code while still being unequal because hash collisions are possible.

---

### 8. Why should `hashCode()` be overridden when `equals()` is overridden?

**Answer:**
Because hash-based collections rely on both methods. If logically equal objects produce different hash codes, collections such as `HashMap` and `HashSet` may behave incorrectly.

---

### 9. What is the difference between `getClass()` and `instanceof`?

**Answer:**
`getClass()` returns the exact runtime class of an object, while `instanceof` checks whether an object is compatible with a specified type, including its subclasses.

---

### 10. What is `Cloneable`?

**Answer:**
`Cloneable` is a marker interface that indicates that a class permits cloning through the `Object.clone()` mechanism. It does not declare a `clone()` method.

---

# 🔴 SDE Interview Questions

### 11. Why is `Object.clone()` protected?

**Answer:**
`clone()` is protected so that arbitrary external code cannot directly invoke it on every object. A class can expose cloning according to its own design, usually by implementing `Cloneable` and providing an appropriate public/protected clone method or, preferably, using another copying mechanism.

---

### 12. Is `clone()` a deep copy or shallow copy?

**Answer:**
The default implementation provided by `Object.clone()` performs a shallow, field-by-field copy. Primitive values are copied, while object references are copied as references. Therefore, nested mutable objects may still be shared.

---

### 13. Can we override `getClass()`?

**Answer:**
No. `getClass()` is declared `final` in `Object`, so it cannot be overridden.

---

### 14. Can we override `equals()` without overriding `hashCode()`?

**Answer:**
Technically yes, the code can compile, but it generally violates the `equals()`/`hashCode()` contract if the equality semantics have changed. Therefore, when overriding `equals()`, you should normally override `hashCode()` consistently.

---

### 15. Why does `HashSet` need both `equals()` and `hashCode()`?

**Answer:**
`hashCode()` helps determine the hash bucket where an element should be located, while `equals()` is used to determine whether objects in the relevant bucket are logically equal. Both are therefore necessary for correct duplicate detection.

---

### 16. What happens if two unequal objects have the same hash code?

**Answer:**
That is a **hash collision**. It is allowed by the `hashCode()` contract. The collection handles the collision and uses `equals()` to distinguish the objects.

---

### 17. Why is `finalize()` deprecated?

**Answer:**
Finalization has unpredictable execution timing and can cause performance, reliability, and resource-management problems. Modern Java code should use explicit resource-management techniques such as try-with-resources instead.

---

### 18. Why are `wait()` and `notify()` methods in `Object` instead of `Thread`?

**Answer:**
Because Java's wait/notify mechanism is associated with an object's monitor. A thread waits on a particular object's monitor and another thread can notify threads waiting on that same object.

---

# ⭐ Very Common Interview Question

### `equals()` vs `==` vs `hashCode()`

| Concept      | Meaning                                                               |
| ------------ | --------------------------------------------------------------------- |
| `==`         | For object references, checks same object/reference identity          |
| `equals()`   | Checks logical equality according to the class                        |
| `hashCode()` | Produces an integer hash value used heavily by hash-based collections |

Remember:

```text
== 
↓
Same object?

equals()
↓
Logically equal?

hashCode()
↓
Which hash bucket / hash value?
```

And the golden rule:

```text
equals() == true
        ↓
hashCode() MUST be same
```

but:

```text
hashCode() same
        ↓
equals() may be false
```

---

# 🧠 Final Revision Notes

```text
OBJECT CLASS
│
├── Package → java.lang
│
├── Root class of Java class hierarchy
│
├── Every class directly/indirectly extends Object
│
├── Common methods
│
├── toString()
│     └── String representation
│
├── equals()
│     └── Logical equality
│
├── hashCode()
│     └── Hash value
│
├── getClass()
│     └── Runtime class
│
├── clone()
│     └── Shallow copy by default
│
├── wait()
│     └── Wait for notification
│
├── notify()
│     └── Notify one waiting thread
│
├── notifyAll()
│     └── Notify all waiting threads
│
└── finalize()
      └── Deprecated; don't use
```

### 🔥 5 things you absolutely should remember

1. **Every class indirectly extends `Object`.**
2. **Default `Object.equals()` compares references (`==`).**
3. **If `equals()` is true → `hashCode()` must be equal.**
4. **`Object.clone()` performs a shallow copy.**
5. **`finalize()` is deprecated; use proper resource management instead.**

One especially useful connection to your previous **Immutable Class** topic is this:

> **Immutability + correctly implemented `equals()` and `hashCode()` makes objects particularly safe and predictable when used as keys in `HashMap` or elements of `HashSet`.**
