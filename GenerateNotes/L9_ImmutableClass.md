Your notes have the right core idea, but there are **two important corrections**:

1. An immutable class means its **object state cannot be changed after construction**. It does **not** mean that methods themselves cannot be modified.
2. Your defensive-copy approach is correct for `College`, but calling it simply a **deep copy** needs some nuance. It is a defensive copy of that nested object; a truly deep copy must recursively copy the entire mutable object graph.

Here is the enhanced version.

# 🔒 Immutable Class in Java

## 1. Overview

An **immutable class** is a class whose **object state cannot be changed after the object has been created**.

Once an immutable object is constructed, its fields cannot be changed through that object.

### Example

```java
String name = "Pratik";
```

A `String` object is immutable.

If we write:

```java
name = name.concat(" Singh");
```

the original `String` object is **not modified**.

Instead, a new `String` object is created.

```text
"Pratik"
   ↓
"Pratik Singh"   ← new object
```

---

# 2. Definition

> **An immutable class is a class whose objects cannot have their observable state modified after construction.**

In other words:

```text
Create Object
     ↓
Initialize State
     ↓
State cannot be changed
```

This is why immutable objects are particularly useful when objects need to be safely shared.

---

# 3. Rules for Creating an Immutable Class

There are several common practices for designing an immutable class.

### Rule 1: Make the class `final`

```java
final class Student {
}
```

Why?

If the class can be subclassed, a subclass might introduce behavior that exposes or changes state.

Making it `final` prevents inheritance.

```text
final class Student
       ↓
Cannot extend Student
       ↓
No subclass can override its behavior
       ↓
Helps preserve immutability
```

### Important correction

Don't say:

> "`final` prevents method modification."

More accurately:

> Making the class `final` prevents subclassing, which prevents subclasses from overriding its methods and potentially violating the intended immutability contract.

---

# 4. Make Instance Variables `private` and `final`

Example:

```java
private final int age;
private final String name;
```

### `private`

Prevents direct access from outside the class.

```java
student.name;  // ❌
```

### `final`

The field can be assigned once and cannot be reassigned afterward.

```java
this.age = age;
```

After construction:

```java
this.age = 30; // ❌
```

However, there is an **extremely important distinction**:

> `final` prevents a reference variable from being reassigned; it does **not automatically make the referenced object immutable**.

This becomes important with objects such as `College`.

---

# 5. Don't Provide Setters

An immutable class should not provide methods that allow its state to be changed.

Avoid:

```java
public void setAge(int age) {
    this.age = age;
}
```

Instead, provide getters if required:

```java
public int getAge() {
    return age;
}
```

So:

```text
Getter  → allows reading
Setter  → allows modification
```

For an immutable class:

```text
Getter     ✅
Setter     ❌
```

---

# 6. Basic Immutable Class

```java
final class Student {

    private final int age;
    private final String name;

    public Student(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }
}
```

Usage:

```java
Student student = new Student(28, "Pratik");

System.out.println(student.getAge());
System.out.println(student.getName());
```

There is no way to modify the state after construction.

---

# 7. Why `final` Alone Does NOT Guarantee Immutability

This is one of the **most important interview concepts**.

Consider:

```java
final class Student {

    private final College college;

    public Student(College college) {
        this.college = college;
    }

    public College getCollege() {
        return college;
    }
}
```

At first glance, `college` is:

```java
private final College college;
```

So you might think it cannot change.

But there is a problem.

---

# 8. Mutable Object as an Instance Variable

Suppose `College` is mutable:

```java
class College {

    String name;
    String address;

    College(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
```

Now:

```java
College college =
        new College("IITG", "Assam");

Student student =
        new Student(28, "Pratik", college);
```

The important point is:

```java
final College college;
```

means the **reference cannot point to another College object**.

It does **not** mean that the College object itself cannot change.

---

# 9. `final` Reference vs Immutable Object

Consider:

```java
final College college = new College("IITG", "Assam");
```

This is prohibited:

```java
college = new College("IITB", "Mumbai"); // ❌
```

because the reference is `final`.

But this is still possible:

```java
college.name = "IITB"; // ✅
```

because the object itself is mutable.

### Visualize it

```text
college
   │
   │ final reference
   ↓
┌─────────────────────┐
│ College Object      │
│                     │
│ name = "IITG"       │
│ address = "Assam"   │
└─────────────────────┘
```

`final` protects:

```text
reference ─────X────→ another object
```

It does **not automatically protect:**

```text
College Object
   ↓
name/address
```

from modification.

---

# 10. How Immutability Can Be Broken

Suppose:

```java
final class Student {

    private final int age;
    private final String name;
    private final College college;

    Student(int age, String name, College college) {
        this.age = age;
        this.name = name;
        this.college = college;
    }

    public College getCollege() {
        return college;
    }
}
```

Then:

```java
College college =
        new College("IITG", "Assam");

Student student =
        new Student(28, "Pratik", college);
```

Now:

```java
student.getCollege().name = "IITB";
```

This changes the `College` object referenced by the `Student`.

Therefore, the **observable state of `Student` has effectively changed**.

---

# 11. Why Does This Happen?

The problem is **reference sharing**.

Initially:

```text
college ─────────────┐
                     ↓
              ┌───────────────┐
              │ College       │
              │ name = IITG   │
              │ address=Assam │
              └───────────────┘
                     ↑
                     │
student.college ─────┘
```

Both references point to the **same object**.

So:

```java
student.getCollege().name = "IITB";
```

modifies that shared object.

Now:

```text
college.name
```

is also:

```text
"IITB"
```

---

# 12. Shallow Copy vs Reference Sharing

Your note calls this **shallow copy**, but there is an important terminology distinction.

The code:

```java
this.college = college;
```

is actually **reference assignment**, not a copy.

Both variables refer to the same object.

```text
college ───────┐
               ↓
          College Object
               ↑
               │
student.college
```

A **shallow copy** generally means creating a new outer object while copying references to nested objects.

For example:

```text
Original Student
      │
      └──→ College A

Shallow Copy
      │
      └──→ College A
```

There are two `Student` objects but the same `College`.

---

# 13. Solutions

There are two common approaches.

### Solution 1: Make the nested object immutable

If `College` itself is immutable, sharing it is safe.

### Solution 2: Use defensive copying

Create a separate copy when:

* receiving a mutable object in the constructor
* returning a mutable object from a getter

This is called **defensive copying**.

---

# 🛡️ 14. Defensive Copy

> **Defensive copying means creating an independent copy of a mutable object so that external code cannot modify the internal state of your object through a shared reference.**

There are two important places to use it:

### 1. During construction

Protect internal state from the object supplied by the caller.

### 2. During getter calls

Protect internal state from being exposed to the caller.

---

# 15. Defensive Copy in Constructor

Instead of:

```java
this.college = college;
```

use:

```java
this.college =
    new College(college.name, college.address);
```

Now:

```text
Original College
      │
      ↓
┌───────────────┐
│ IITG          │
│ Assam         │
└───────────────┘


Student's College
      │
      ↓
┌───────────────┐
│ IITG          │
│ Assam         │
└───────────────┘
```

These are **two different objects**.

---

# 16. Defensive Copy in Getter

There is another potential problem.

Suppose you have:

```java
public College getCollege() {
    return college;
}
```

Even though you created a defensive copy in the constructor, you're now exposing your internal mutable object.

The caller can do:

```java
student.getCollege().name = "IITB";
```

and modify the `Student` object's internal state.

Therefore, return another copy:

```java
public College getCollege() {
    return new College(
        college.name,
        college.address
    );
}
```

Now the caller receives a **separate object**.

---

# 17. Correct Immutable `Student`

```java
final class Student {

    private final int age;
    private final String name;
    private final College college;

    public Student(int age, String name, College college) {
        this.age = age;
        this.name = name;

        // Defensive copy
        this.college =
            new College(college.name, college.address);
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public College getCollege() {

        // Defensive copy
        return new College(
            college.name,
            college.address
        );
    }
}
```

---

# 18. Testing the Defensive Copy

```java
College college =
        new College("IITG", "Assam");

Student student =
        new Student(28, "Pratik", college);
```

Now:

```java
college.name = "IITB";
```

The original external object changes.

But:

```java
student.getCollege().name
```

still returns:

```text
IITG
```

because `Student` has its own `College` object.

---

## 19. Why Getter Must Also Return a Copy

Suppose constructor copying is done:

```java
this.college =
    new College(college.name, college.address);
```

but getter is:

```java
return college;
```

Then:

```java
College c = student.getCollege();

c.name = "IITB";
```

Now:

```text
Student
   │
   └──→ Internal College
          ↑
          │
          c
```

The caller has a reference to the actual internal object.

Therefore, immutability is broken.

### Correct:

```java
return new College(college.name, college.address);
```

Now:

```text
Student
   │
   └──→ Internal College

Caller
   │
   └──→ New College Copy
```

They are independent.

---

# 20. Defensive Copy vs Deep Copy

These terms are related but **not exactly synonymous**.

### Defensive Copy

A defensive copy is made specifically to protect an object's internal state from external modification.

### Deep Copy

A deep copy creates independent copies of nested mutable objects throughout the relevant object graph.

For example:

```text
Student
   │
   └── College
          │
          └── Address
                 │
                 └── City
```

If only `College` is copied:

```text
Student
   │
   └── College Copy
          │
          └── same Address
```

then it is **not a fully deep copy** if `Address` is mutable.

A true deep copy would require:

```text
Student Copy
   │
   └── College Copy
          │
          └── Address Copy
                 │
                 └── City Copy
```

### ⭐ Interview rule

> **Defensive copying is about protecting encapsulation; deep copying is about recursively creating independent copies of mutable referenced objects.**

---

# 21. Best Way: Make Nested Classes Immutable

If possible, a simpler solution is to make `College` immutable too.

```java
final class College {

    private final String name;
    private final String address;

    public College(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
```

Now:

```java
final class Student {

    private final int age;
    private final String name;
    private final College college;

    public Student(int age, String name, College college) {
        this.age = age;
        this.name = name;
        this.college = college;
    }

    public College getCollege() {
        return college;
    }
}
```

Sharing is now safe because `College` itself cannot be modified.

```text
Student
   │
   └──────→ Immutable College
```

There is no need to create defensive copies of an immutable object.

---

# 22. `String` as an Example of an Immutable Class

Java's `String` is a classic immutable class.

```java
String s = "Hello";

s.concat(" Java");

System.out.println(s);
```

Output:

```text
Hello
```

Why?

`concat()` does not modify the existing `String`.

It creates a new object.

```java
String s2 = s.concat(" Java");

System.out.println(s2);
```

Output:

```text
Hello Java
```

Conceptually:

```text
s
↓
"Hello"

s2
↓
"Hello Java"
```

---

# 23. Why Are Immutable Objects Useful?

### 1. Thread safety

Immutable objects can be safely shared between threads because their state cannot change.

### 2. Security

Sensitive values cannot unexpectedly change after validation.

### 3. HashMap/HashSet safety

If an object is used as a key, changing fields involved in `hashCode()`/`equals()` can cause lookup problems.

Immutable keys avoid this category of problem.

### 4. Caching

Immutable objects can safely be reused and cached.

### 5. Easier reasoning

If an object is immutable:

```text
Created state
     ↓
Never changes
```

This makes code easier to reason about.

---

# 24. Characteristics of a Good Immutable Class

```text
                    Immutable Class
                          │
        ┌─────────────────┼─────────────────┐
        ↓                 ↓                 ↓
      final           private final      no setters
      class             fields              │
        │                 │                  │
        └─────────────────┼──────────────────┘
                          ↓
              Defensive handling of
                mutable references
                          ↓
                 State cannot change
```

### Practical checklist

* ✅ Class is `final` or inheritance is otherwise safely controlled.
* ✅ Instance fields are `private`.
* ✅ Fields are initialized during construction.
* ✅ Fields are `final` where appropriate.
* ✅ No setters or other state-mutating methods.
* ✅ Mutable objects are defensively copied.
* ✅ Mutable objects are not directly exposed through getters.
* ✅ Nested mutable state is also protected.
* ✅ Constructor doesn't allow `this` to escape during construction.

---

# 🎯 Important Interview Questions

## 🟢 Basic

### 1. What is an immutable class?

**Answer:**
An immutable class is a class whose objects cannot have their observable state changed after construction. Once the object is created, its state remains fixed.

---

### 2. How do you create an immutable class in Java?

**Answer:**
Common practices include making the class `final`, keeping instance fields `private` and `final`, initializing them through the constructor, avoiding setters and other mutating methods, and using defensive copies for mutable objects.

---

### 3. Why are instance variables made `private`?

**Answer:**
`private` provides encapsulation and prevents external code from directly accessing or modifying the object's internal state.

---

### 4. Why are instance variables made `final`?

**Answer:**
`final` prevents the field from being reassigned after it has been initialized. However, if the field refers to a mutable object, `final` does not make that object immutable.

---

# 🟡 Intermediate

### 5. Does `final` make an object immutable?

**Answer:**
No. `final` prevents a reference variable from being reassigned, but it does not prevent modification of the referenced object's internal state.

Example:

```java
final List<Integer> list = new ArrayList<>();

list.add(10);       // ✅
list = new ArrayList<>(); // ❌
```

The reference cannot change, but the list can still change.

---

### 6. Why should an immutable class generally be `final`?

**Answer:**
Making the class `final` prevents subclassing. This prevents a subclass from adding mutable state or overriding methods in ways that could violate the immutability guarantees of the original class.

---

### 7. What is defensive copying?

**Answer:**
Defensive copying is the practice of creating an independent copy of a mutable object when accepting it from external code or returning it to external code. It prevents callers from modifying an immutable object's internal state through shared references.

---

### 8. Why do we need defensive copying in both constructor and getter?

**Answer:**
The constructor copy protects the object from changes made through the caller's original reference, while the getter copy prevents the caller from modifying the object's internal mutable state through the returned reference.

---

# 🔴 Advanced / SDE

### 9. Can an immutable class contain a mutable object?

**Answer:**
Yes, but the mutable object's state must not be exposed directly. The immutable class must protect it through defensive copies or another mechanism that prevents external mutation.

---

### 10. What is the difference between shallow copy and deep copy?

**Answer:**
A shallow copy creates a new outer object but may retain references to the same nested objects. A deep copy creates independent copies of the nested mutable objects as well.

```text
Shallow:
Object A → Nested X
Object B → Nested X

Deep:
Object A → Nested X
Object B → Nested Y
```

---

### 11. How can a getter break immutability?

**Answer:**

Suppose:

```java
public College getCollege() {
    return college;
}
```

If `College` is mutable, the caller can do:

```java
student.getCollege().name = "IITB";
```

This directly modifies the internal state of `Student`.

Therefore, return a defensive copy or make `College` immutable.

---

### 12. Why is `String` immutable in Java?

**Answer:**
`String` is designed as an immutable class, which provides benefits such as safe sharing, thread safety, predictable behavior, security, and reliable use in hash-based collections. Operations that appear to modify a `String` actually create a new `String` object.

---

### 13. Why are immutable objects useful as keys in `HashMap`?

**Answer:**
A hash-based collection relies on an object's `hashCode()` and `equals()` behavior. If the state relevant to these methods changes after insertion, the object may no longer be found in the expected bucket. Immutability prevents such state changes and makes the object safer to use as a key.

---

### 14. Is `final class + private final fields + no setters` always enough to make a class immutable?

**Answer:**
No. This is not sufficient if the fields refer to mutable objects. The class must also ensure that mutable internal objects cannot be modified externally, usually through defensive copying or by using immutable types.

---

# ⭐ Interview Trap

If an interviewer asks:

> **"If a class has all `private final` variables and no setters, is it immutable?"**

Don't immediately say **yes**.

Say:

> **"Not necessarily. Primitive and immutable reference fields are safe, but if a field refers to a mutable object such as `ArrayList`, `Date`, or a custom mutable class, the class can still be modified indirectly unless that mutable state is protected using defensive copying or another appropriate mechanism."**

That is the kind of answer that demonstrates a deeper understanding of **Java immutability and object references**.

---

# 🧠 Final Revision

```text
IMMUTABLE CLASS
│
├── Object state cannot change after construction
│
├── Class → preferably final
│
├── Fields → private + final
│
├── Initialize → constructor
│
├── Setters → avoid
│
├── Mutable field?
│      │
│      ├── Make it immutable
│      │
│      └── Use defensive copy
│              │
│              ├── Constructor → copy incoming object
│              └── Getter → return copy
│
└── final reference ≠ immutable object
```

### The single most important concept:

> **`final` protects the reference; immutability protects the object's state.**

That's the distinction to remember for interviews.
