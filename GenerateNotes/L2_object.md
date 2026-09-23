# ==================== Objects in Java ====================

## 1. What is an Object?

An **object** is an instance of a class.

A class defines the structure and behavior, while an object represents an actual entity created from that class.

```java
class Student {
    String name;
    int age;

    void study() {
        System.out.println("Studying");
    }
}
```

Creating an object:

```java
Student s = new Student();
```

Here:

```text
Student → class
s       → reference variable
new Student() → object
```

A useful way to visualize it:

```text
Stack                         Heap

s ────────────────────────> Student Object
                             ├── name
                             └── age
```

> **Important:** `s` is not the object itself. `s` is a reference variable that holds a reference to the object.

---

# 2. Where Are Objects Stored?

In the JVM's typical HotSpot implementation, objects are allocated in the **heap**.

Example:

```java
Student s = new Student();
```

Conceptually:

```text
Stack
┌─────────────┐
│ s           │ ────────────────┐
└─────────────┘                 │
                                ▼
                           Heap
                      ┌──────────────┐
                      │ Student      │
                      │ Object       │
                      ├──────────────┤
                      │ name         │
                      │ age          │
                      └──────────────┘
```

The exact JVM memory implementation can vary, so this should be treated as the conceptual model used for learning.

---

# 3. What Is the Size of an Object?

A Java object does **not** have a universal fixed size.

Its size depends on:

* JVM implementation
* 32-bit vs 64-bit architecture
* JVM configuration
* Object header layout
* Instance fields
* References
* Alignment/padding
* JVM options such as compressed references

A useful conceptual formula is:

```text
Object Size
    =
Object Header
+ Instance Fields
+ Padding
```

---

# 4. Object Structure

A typical Java object can conceptually be divided into:

```text
┌─────────────────────────────┐
│       Object Header         │
├─────────────────────────────┤
│       Instance Fields       │
├─────────────────────────────┤
│          Padding            │
└─────────────────────────────┘
```

Let's understand each part.

---

# 5. Object Header

The object header contains JVM-level metadata associated with the object.

In a typical HotSpot JVM, the header includes concepts such as:

### 1. Mark Word

The **mark word** stores JVM metadata related to the object.

Depending on the JVM and object state, it can be associated with information such as:

* Object locking/synchronization information
* Identity hash code
* Garbage-collection-related information

On a typical 64-bit HotSpot JVM, the mark word is commonly **8 bytes**, but the exact layout is JVM-dependent.

---

### 2. Class Metadata / Class Pointer

The object also needs information that identifies its class.

Conceptually:

```text
Object
  ↓
Which class does this object belong to?
  ↓
Class metadata
```

For example:

```java
Student s = new Student();
```

The JVM needs to know that the object is a `Student`.

In HotSpot, this is commonly represented through a **Klass pointer**.

Its size depends on the JVM configuration. With compressed class pointers, it can commonly be 4 bytes; without compression, it can commonly be 8 bytes on a 64-bit JVM.

> **Important:** Don't memorize "class pointer is always 4 or 8 bytes." The exact size depends on the JVM configuration.

---

# 6. Instance Fields

After the object header, memory is required for the object's **instance fields**.

Example:

```java
class Student {

    int age;
    long id;
    String name;
}
```

Conceptually, the object contains storage for:

```text
age
id
name reference
```

### Typical primitive sizes

| Type      |                 Typical size |
| --------- | ---------------------------: |
| `byte`    |                       1 byte |
| `short`   |                      2 bytes |
| `int`     |                      4 bytes |
| `long`    |                      8 bytes |
| `float`   |                      4 bytes |
| `double`  |                      8 bytes |
| `char`    |                      2 bytes |
| `boolean` | JVM-dependent representation |

### Reference size

A reference is **not necessarily 4 bytes or 8 bytes**.

On a 64-bit HotSpot JVM:

* With compressed ordinary object pointers (**compressed oops**), a reference is commonly 4 bytes.
* Without compressed references, it is commonly 8 bytes.

So this:

```java
String name;
```

does **not** mean that the `String` object itself occupies 4 bytes.

It means the `Student` object contains a **reference** to a `String` object.

---

# 7. Very Important: Reference vs Object

Consider:

```java
String name = "Pratik";
```

There are conceptually two things involved:

```text
name
 ↓
reference
 ↓
String object
```

The reference is stored as part of the object/local state where the variable resides.

The actual `String` object has its **own memory**.

Therefore, if a class contains:

```java
class Student {

    String name;
}
```

we should **not** calculate the object size as:

```text
Student
+ String object
```

just because it has a `String` field.

The `Student` object contains a reference to the `String` object.

---

# 8. Padding and Object Alignment

The JVM generally aligns objects to a particular memory boundary.

In a common HotSpot configuration, object alignment is often **8 bytes**.

If the calculated object size isn't aligned properly, the JVM can add unused space called **padding**.

Example conceptually:

```text
Calculated size = 28 bytes

Next 8-byte boundary = 32 bytes

Padding = 4 bytes

Final size = 32 bytes
```

So:

```text
Object size
=
Header
+
Fields
+
Padding
```

> **Important:** 8-byte alignment is common in HotSpot, but don't state that every JVM must always use 8-byte alignment.

---

# 9. Correcting Your Object Size Example

You wrote:

```java
class A {
    String name;
    Number rollno;
    int age;
    String college;
}
```

You assumed:

```text
Header = 8 + 4 = 12 bytes
Fields = 16 bytes
Total = 28 bytes
Padding = 4 bytes
Final = 32 bytes
```

This is a **useful simplified example**, but it is not guaranteed to be the actual size.

Why?

Because:

1. `String` is a reference, not a 4-byte String object.
2. `Number` is also a reference.
3. Reference size depends on JVM configuration.
4. Object header size depends on JVM configuration.
5. Field layout can be reordered/aligned by the JVM.
6. Padding depends on the resulting layout.

For a common 64-bit HotSpot JVM with compressed ordinary object pointers and compressed class pointers, you might commonly encounter:

```text
Mark Word             = 8 bytes
Compressed class ptr  = 4 bytes
4 references          = 16 bytes
```

giving a conceptual total around:

```text
8 + 4 + 16 = 28 bytes
```

which may then be rounded to:

```text
32 bytes
```

But **the exact object size should not be memorized without specifying the JVM and configuration**.

---

# 10. Do Methods Take Space Inside Every Object?

Your note:

> object only storage variable in heap not methods

The idea is correct, but let's make it more precise.

Instance methods are **not copied into every object**.

For example:

```java
class Student {

    int age;

    void study() {
        System.out.println("Studying");
    }
}
```

If we create:

```java
Student s1 = new Student();
Student s2 = new Student();
Student s3 = new Student();
```

we do **not** get three separate copies of the `study()` method.

Conceptually:

```text
Objects

s1 → [age]
s2 → [age]
s3 → [age]

          ↓
       class metadata
          ↓
      study() method
```

The JVM maintains class/method-related information separately from each object's instance fields.

> **Interview-safe statement:** An object's memory primarily contains its object header and instance state; instance method code is not duplicated inside every object.

---

# ==================== Call by Value vs Call by Reference ====================

This is one of the **most important Java interview topics**.

## 11. Is Java Pass-by-Value or Pass-by-Reference?

Java is **always pass-by-value**.

There is no pass-by-reference parameter-passing mechanism like C++ references.

The confusion comes from the fact that **object references are themselves passed by value**.

---

# 12. Primitive Types — Pass by Value

Example:

```java
public class Main {

    public static void main(String[] args) {

        int x = 10;
        int y = 20;

        System.out.println(x + " " + y);

        addItem(x, y);

        System.out.println(x + " " + y);
    }

    static void addItem(int x, int y) {

        x += 10;
        y += 10;
    }
}
```

Output:

```text
10 20
10 20
```

### Why?

When calling:

```java
addItem(x, y);
```

the **values** `10` and `20` are copied into the parameters of `addItem()`.

Conceptually:

```text
main()

x = 10
y = 20

       ↓ copy values

addItem()

x = 10
y = 20
```

The local variables inside `addItem()` are different variables.

Changing them doesn't affect the original variables.

---

# 13. Object References — Still Pass by Value

Now consider:

```java
class Random {

    int x;
    int y;

    Random(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
```

And:

```java
public class Main {

    public static void main(String[] args) {

        Random r = new Random(10, 20);

        System.out.println(r.x + " " + r.y);

        addItem(r);

        System.out.println(r.x + " " + r.y);
    }

    static void addItem(Random r) {

        r.x += 10;
        r.y += 10;
    }
}
```

Output:

```text
10 20
20 30
```

This sometimes causes people to say:

> "Java passes objects by reference."

That statement is **incorrect**.

Java passes the **reference value by value**.

---

# 14. What Actually Happens?

Before the method call:

```text
main()

r ────────────────┐
                  │
                  ▼
             Random Object
             x = 10
             y = 20
```

When:

```java
addItem(r);
```

is called, the reference value is copied.

```text
main()                    addItem()

r ────────┐               r ────────┐
          │                         │
          └─────────┐   ┌───────────┘
                    ▼   ▼
                 Same Object
                 x = 10
                 y = 20
```

There are **two reference variables**, but both contain a reference to the **same object**.

Therefore:

```java
r.x += 10;
```

modifies the same object.

---

# 15. The Best Proof That Java Is Pass-by-Value

This example is extremely important for interviews:

```java
class Random {

    int x;
    int y;

    Random(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
```

```java
public class Main {

    public static void main(String[] args) {

        Random r = new Random(10, 20);

        changeReference(r);

        System.out.println(r.x + " " + r.y);
    }

    static void changeReference(Random r) {

        r = new Random(100, 200);
    }
}
```

Output:

```text
10 20
```

### Why?

The parameter `r` is a **copy of the reference value**.

Inside the method:

```java
r = new Random(100, 200);
```

we make the **local reference** point to a different object.

The original reference in `main()` still points to the original object.

```text
main()                     changeReference()

r ───────────────┐
                 │
                 ▼
            Object A
            10, 20


local r ───────────────> Object B
                         100, 200
```

After the method returns, the local reference disappears.

The original `r` still points to Object A.

This proves:

> **Java is pass-by-value, even when the value being passed is an object reference.**

---

# 16. Primitive vs Object Passing

Don't remember:

```text
primitive → pass by value
object    → pass by reference
```

❌ This is incorrect.

Remember:

```text
Java
 │
 └── Always pass by value
       │
       ├── primitive → value is copied
       │
       └── object reference → reference value is copied
```

This is the correct mental model.

---

# ==================== Copy Constructor ====================

## 17. What is a Copy Constructor?

A copy constructor is a constructor that creates a new object using another object of the same class.

Java does not provide a special built-in copy-constructor mechanism, but we can define one ourselves.

```java
class Random {

    int x;
    int y;

    Random(int x, int y) {
        this.x = x;
        this.y = y;
    }

    Random(Random r) {
        this.x = r.x;
        this.y = r.y;
    }
}
```

Usage:

```java
Random r1 = new Random(10, 20);

Random r2 = new Random(r1);
```

Now:

```text
r1 ───────> Object A
             x = 10
             y = 20

r2 ───────> Object B
             x = 10
             y = 20
```

`r1` and `r2` refer to **different objects**.

---

# 18. Copy Constructor vs Reference Assignment

This distinction is extremely important.

### Copy Constructor

```java
Random r2 = new Random(r1);
```

Creates a **new object**.

```text
r1 ───> Object A

r2 ───> Object B
```

Both objects initially contain the same values.

---

### Reference Assignment

```java
Random r2 = r1;
```

Does **not** create a new object.

It simply copies the reference.

```text
r1 ─────┐
        ├──> Same Object
r2 ─────┘
```

Therefore:

```java
r2.x = 100;
```

also changes what you observe through `r1.x`.

---

# 19. Copy Constructor and Reference Fields

Be careful with objects containing references.

```java
class Student {

    String name;
    Address address;

    Student(Student other) {

        this.name = other.name;
        this.address = other.address;
    }
}
```

The copy constructor above creates a new `Student`, but both students may refer to the **same `Address` object**.

That's a shallow copy.

To perform a deep copy, the referenced object must also be copied.

---

# ==================== ⭐ Quick Revision ====================

### Object

```text
Object = instance of a class
```

### Object memory

```text
Object Size ≈ Header + Instance Fields + Padding
```

### Object Header

Common HotSpot concepts:

```text
Mark Word
Class/Klass Pointer
```

### Reference

```text
String name;
```

`name` stores a **reference**, not the entire String object.

Reference size depends on JVM configuration.

### Padding

Used to satisfy object alignment requirements.

### Methods

Methods are **not duplicated inside every object**.

### Java parameter passing

```text
Java → Always pass-by-value

Primitive:
value is copied

Object:
reference value is copied
```

### Copy constructor

```java
Random r2 = new Random(r1);
```

→ new object.

### Reference assignment

```java
Random r2 = r1;
```

→ same object.

---

# ==================== ⭐ Important Interview Questions ====================

### Q1. What is an object in Java?

**Answer:**

> An object is an instance of a class. It contains the instance state represented by its fields, while the behavior is defined by the class's methods.

---

### Q2. Where are objects stored in Java?

**Answer:**

> Objects are generally allocated on the heap in a typical JVM implementation such as HotSpot. The exact memory implementation is JVM-dependent.

---

### Q3. What is the structure of a Java object?

**Answer:**

> Conceptually, an object consists of an object header, storage for its instance fields, and possible padding required for memory alignment. The exact layout depends on the JVM and its configuration.

---

### Q4. What is stored in an object header?

**Answer:**

> In a typical HotSpot JVM, the object header contains metadata such as the mark word and class metadata reference. The mark word can contain information related to locking, identity hash code, and garbage-collection-related state.

---

### Q5. Is a String variable of size 4 bytes?

**Answer:**

> Not necessarily. A variable such as `String name` is a reference to a String object. On a 64-bit HotSpot JVM with compressed references, that reference is commonly 4 bytes, but the exact size depends on the JVM configuration. The actual String object occupies its own memory.

---

### Q6. What is object padding?

**Answer:**

> Object padding is unused memory added by the JVM so that an object's size or fields satisfy the required memory-alignment rules. In common HotSpot configurations, objects are often aligned to an 8-byte boundary.

---

### Q7. Does every object contain a copy of its methods?

**Answer:**

> No. Instance methods are not duplicated inside every object. Objects primarily contain their instance state and object metadata, while method implementation is associated with the class-level runtime representation.

---

### Q8. Is Java pass-by-value or pass-by-reference?

**Answer:**

> Java is strictly pass-by-value. For primitives, the actual value is copied. For objects, the value of the reference is copied, so both the original and parameter references can point to the same object.

---

### Q9. Why does modifying an object's field inside a method affect the original object?

**Answer:**

> Because Java passes the object's reference value by value. The copied reference and the original reference can point to the same object, so modifying the object's fields through either reference affects that shared object.

---

### Q10. If Java is pass-by-value, why can an object be modified inside a method?

**Answer:**

> Because the value being copied is the reference to the object. The copied reference still points to the same object, so modifying the object's fields through that reference modifies the original object.

---

### Q11. Prove that Java does not use pass-by-reference for objects.

**Answer:**

> If Java passed object references by reference, reassigning the parameter to a new object would change the caller's reference. However, in Java, reassigning the parameter only changes the local copy of the reference, leaving the caller's reference unchanged. Therefore Java passes the reference value by value.

Example:

```java
static void change(Random r) {
    r = new Random(100, 200);
}
```

The caller's reference still points to the original object.

---

### Q12. What is the difference between these two?

```java
Random r2 = r1;
```

and:

```java
Random r2 = new Random(r1);
```

**Answer:**

> `Random r2 = r1` copies the reference, so both variables refer to the same object. `new Random(r1)` invokes the copy constructor and creates a separate object initialized with the data from `r1`.

---

### Q13. Does Java have a built-in copy constructor?

**Answer:**

> No. Java does not define a special built-in copy-constructor mechanism. However, a programmer can define a constructor that accepts an object of the same class and copies its state.

---

### Q14. What is shallow copy?

**Answer:**

> A shallow copy creates a new outer object but copies references for reference-type fields, so the original and copied objects may share the same nested objects.

---

### Q15. What is deep copy?

**Answer:**

> A deep copy creates a new object and independently copies the referenced objects as well, so modifications to nested objects in one copy do not affect the other.

---

### Q16. What is the difference between an object and a reference variable?

**Answer:**

> An object is the actual runtime entity containing instance state, whereas a reference variable stores a reference that allows us to access that object.

Example:

```java
Student s = new Student();
```

Here:

```text
s              → reference variable
new Student()  → object
```

---

### Q17. Are local variables and instance variables treated the same way regarding default values?

**Answer:**

> No. Instance variables receive default values when an object is initialized, but local variables do not receive default values and must be explicitly initialized before they are read.

---

### Q18. Does `new Student()` create the reference or the object?

**Answer:**

> The `new` expression creates the object and returns a reference to that object. The reference is then assigned to the variable.

```java
Student s = new Student();
```

Conceptually:

```text
new Student()
      ↓
creates object
      ↓
returns reference
      ↓
stored in s
```

---

### Q19. Is object size always a multiple of 8 bytes?

**Answer:**

> Not as a universal Java language rule. However, common HotSpot JVM configurations use object alignment such as 8 bytes, so object sizes are often rounded to an aligned boundary. The exact behavior depends on the JVM configuration.

---

### Q20. Can we determine the exact size of a Java object just by looking at its class?

**Answer:**

> Not reliably. Exact object size depends on the JVM implementation, architecture, reference compression, object layout, field alignment, and padding. Therefore, a class definition alone is not enough to determine the exact runtime memory footprint.
