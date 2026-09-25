# ==================== `static` Keyword ====================

## 1. Overview

The `static` keyword is used to define members that belong to the **class rather than to individual objects**.

Normally, every object has its own copy of instance variables.

Consider:

```java
class Student {

    String name;
    int age;
    int rollNo;
    String college;
}
```

Suppose we create 1000 students, and all of them belong to the same college:

```text
Student 1 → IITG
Student 2 → IITG
Student 3 → IITG
...
Student 1000 → IITG
```

If `college` is an instance variable, every object has its own reference:

```text
1000 objects × reference size
```

This is unnecessary when all students share the same college.

Instead:

```java
static String college = "IITG";
```

creates **one class-level variable shared by all `Student` objects**.

---

# 2. Instance Variable vs Static Variable

### Instance variable

```java
class Student {

    String name;
    int age;
    String college;
}
```

Each object has its own `college`.

```text
s1 → college = IITG
s2 → college = IITG
s3 → college = IITG
```

### Static variable

```java
class Student {

    String name;
    int age;

    static String college;
}
```

Now there is one shared `college` variable associated with the class:

```text
Student
   │
   └── static college = IITG
          ↑
          │
      ┌───┼───┐
      │   │   │
     s1  s2  s3
```

---

# 3. Static Variable

A variable declared using the `static` keyword is called a **static variable** or **class variable**.

```java
class Student {

    String name;
    int age;

    static String college;
}
```

The important idea is:

> A static variable belongs to the class and is shared among all instances of that class.

---

# 4. How to Access a Static Variable

The preferred way is through the class name:

```java
Student.college = "IITG";

System.out.println(Student.college);
```

You can technically access it through an object:

```java
Student s = new Student();

s.college = "IITG";
```

but this is **not recommended**, because it can make it look like `college` belongs to that particular object.

Prefer:

```java
Student.college
```

instead of:

```java
s.college
```

---

# 5. Does Static Variable Belong to the Object?

No.

Consider:

```java
class Student {

    String name;
    static String college;
}
```

If:

```java
Student s1 = new Student();
Student s2 = new Student();
```

then conceptually:

```text
s1 ─────┐
        │
s2 ─────┼────> Student.college
        │
s3 ─────┘
```

There is only **one logical static field** shared by the class.

---

# 6. Where Is Static Data Stored?

Your note says:

> static variable is not stored in heap memory.

This is too absolute and should be corrected.

### Better interview answer:

> Static fields belong to the class rather than individual objects. In HotSpot JVM terminology, class-related metadata is associated with the **Metaspace**, while static field storage is managed as part of the JVM's class-related runtime state. The exact memory implementation is JVM-dependent.

So don't memorize:

```text
static = stack
```

or:

```text
static = Metaspace
```

as a universal Java rule.

The important conceptual distinction is:

```text
Instance variable → belongs to object
Static variable   → belongs to class
```

---

# 7. When Is Static Memory Allocated?

Static members become available when the class is **initialized**, which occurs after the class has been loaded and before its initialization-dependent use proceeds.

For practical interview understanding:

```text
Class loading
      ↓
Class initialization
      ↓
Static fields initialized
      ↓
Static blocks executed
```

### Important distinction

**Class loading and class initialization are not exactly the same thing.**

The JVM may load a class before it initializes it.

Static initialization occurs during **class initialization**.

---

# 8. Static Variable Initialization

You can initialize a static variable directly:

```java
class Student {

    static String college = "IITG";
}
```

This is usually the simplest approach when the initialization is straightforward.

---

# 9. Static Block

A **static block** is a block of code declared using `static`.

```java
class Student {

    static String college;

    static {
        college = "IITG";
    }
}
```

The static block is executed when the class is initialized.

---

# 10. Why Use a Static Block?

Static blocks are useful when static initialization requires **multiple statements or more complex logic**.

Example:

```java
class DatabaseConfig {

    static String url;

    static {
        url = "jdbc:mysql://localhost:3306/test";
        System.out.println("Database configuration initialized");
    }
}
```

For simple values, this:

```java
static String college = "IITG";
```

is generally clearer than:

```java
static {
    college = "IITG";
}
```

---

# 11. Multiple Static Blocks

A class can have multiple static blocks.

```java
class Demo {

    static {
        System.out.println("Block 1");
    }

    static {
        System.out.println("Block 2");
    }
}
```

They execute in **top-to-bottom order** during class initialization.

Output:

```text
Block 1
Block 2
```

---

# 12. Static Block Executes How Many Times?

A static block executes **once per class initialization**, not once per object.

Example:

```java
class Student {

    static {
        System.out.println("Static block");
    }

    Student() {
        System.out.println("Constructor");
    }
}
```

```java
Student s1 = new Student();
Student s2 = new Student();
```

Conceptually:

```text
Static block
Constructor
Constructor
```

The static block runs once, while the constructor runs once for each object.

---

# 13. Static Method

A method declared using `static` is called a **static method**.

```java
class Student {

    static void displayCollege() {
        System.out.println("IITG");
    }
}
```

It can be called using the class name:

```java
Student.displayCollege();
```

No `Student` object is required.

---

# 14. Why Can Static Methods Be Called Without Objects?

Because static methods belong to the **class**, not to a particular object.

```java
Student.displayCollege();
```

There is no need to create:

```java
new Student()
```

before calling it.

---

# 15. Why Can't a Static Method Directly Access Instance Variables?

Consider:

```java
class Student {

    String name;

    static void display() {

        System.out.println(name); // ERROR
    }
}
```

Why?

Because `name` belongs to an **object**.

But the static method can be called without any object:

```java
Student.display();
```

So Java has no specific `Student` object whose `name` should be accessed.

Conceptually:

```text
Static method
     ↓
No specific object
     ↓
Which object's name?
     ↓
Ambiguous
```

Therefore, a static method cannot directly access instance members.

---

# 16. Can a Static Method Access Instance Data?

Yes, **if you provide an object reference explicitly**.

```java
class Student {

    String name;

    static void display(Student s) {
        System.out.println(s.name);
    }
}
```

Usage:

```java
Student s = new Student();

s.name = "Pratik";

Student.display(s);
```

Here the method knows exactly which object's `name` it should access.

---

# 17. Static Method Can Access Static Members

```java
class Student {

    static String college = "IITG";

    static void display() {

        System.out.println(college);
    }
}
```

This works because both belong to the class.

```text
static method
      ↓
static variable
      ↓
same class-level context
```

---

# 18. Static Method Cannot Directly Use `this`

Example:

```java
class Student {

    static void display() {

        System.out.println(this);
    }
}
```

Compilation error.

### Why?

`this` refers to the **current object**.

A static method does not require an object to be invoked.

Therefore, there is no implicit current object associated with a static method.

Remember:

```text
this
 ↓
current object
 ↓
instance context

static method
 ↓
class context
 ↓
no implicit current object
```

---

# 19. Static Methods and Method Overriding

Static methods are **not overridden** in the same way instance methods are.

They are **hidden** when a subclass declares a static method with the same signature.

Example:

```java
class Parent {

    static void show() {
        System.out.println("Parent");
    }
}

class Child extends Parent {

    static void show() {
        System.out.println("Child");
    }
}
```

This is called **method hiding**, not runtime overriding.

---

# 20. What Can Be `static`?

In a top-level class, commonly:

| Entity          | Can be static?             |
| --------------- | -------------------------- |
| Variable        | ✅                          |
| Method          | ✅                          |
| Block           | ✅ Static initializer block |
| Parameter       | ❌                          |
| Local variable  | ❌                          |
| Top-level class | ❌                          |
| Nested class    | ✅                          |

---

# 21. Can a Parameter Be Static?

No.

```java
void fun(static int x) {   // ❌
}
```

A parameter belongs to a particular method invocation. It is not a member belonging to the class or object.

Similarly:

```java
void fun() {

    static int x; // ❌
}
```

Local variables cannot be declared `static`.

> **Important:** Don't explain this primarily using "stack vs heap." The key reason is that `static` applies to class members, while parameters and local variables belong to a particular method invocation.

---

# 22. Can a Top-Level Class Be Static?

No.

This is invalid:

```java
static class Student {   // ❌
}
```

A top-level class belongs to the package/module-level namespace, not to an enclosing class.

However, a **nested class** can be static:

```java
class Outer {

    static class Inner {
    }
}
```

This is called a **static nested class**.

---

# 23. Why Is `main()` Static?

This is a very common interview question.

The standard Java entry point is:

```java
public static void main(String[] args)
```

The `main()` method is static because the JVM needs an entry point that can be invoked **without first creating an instance of the class**.

Conceptually:

```text
JVM
 ↓
find main()
 ↓
invoke main()
```

If `main()` were an ordinary instance method, the JVM would first need an object on which to invoke it.

Static allows the entry point to be associated with the class itself.

### Important correction to your note

Don't say:

> "JVM cannot create an object of the main class because we are responsible for object creation."

The better explanation is:

> The `main` method is static so that the JVM can invoke the program's entry point without requiring an instance of the class to be created first.

---

# 24. Why Is `String[] args` Used in `main()`?

`args` is an array of strings used to receive **command-line arguments**.

Example:

```bash
java Demo Aditya Rohit Pratik
```

Then:

```java
public static void main(String[] args) {

    System.out.println(args[0]);
    System.out.println(args[1]);
    System.out.println(args[2]);
}
```

Output:

```text
Aditya
Rohit
Pratik
```

Conceptually:

```text
args[0] → "Aditya"
args[1] → "Rohit"
args[2] → "Pratik"
```

### Important

`String[] args` does **not** contain the `.java` filename.

For:

```bash
java Demo Aditya Rohit Pratik
```

the arguments are:

```text
Aditya
Rohit
Pratik
```

---

# 25. Complete Example

```java
class Student {

    String name;
    int rollNo;
    int age;

    static String college;

    static {
        college = "IITG";
        System.out.println("Student class initialized");
    }

    Student(String name, int rollNo, int age) {

        this.name = name;
        this.rollNo = rollNo;
        this.age = age;
    }

    static void displayCollege() {

        System.out.println("College: " + college);
    }

    void displayStudent() {

        System.out.println(name + " " + rollNo + " " + age);
        System.out.println("College: " + college);
    }
}

public class Main {

    public static void main(String[] args) {

        Student s1 = new Student("Pratik", 21, 20);
        Student s2 = new Student("Rahul", 22, 21);

        Student.displayCollege();

        s1.displayStudent();
        s2.displayStudent();
    }
}
```

Both students share:

```java
Student.college
```

while each has its own:

```text
name
rollNo
age
```

---

# ==================== `final` Keyword ====================

## 26. Overview

The `final` keyword is used to restrict modification, overriding, or inheritance depending on where it is applied.

It can be used with:

```text
final variable
final parameter
final method
final class
```

---

# 27. Final Variable

A variable declared `final` can be assigned **only once**.

```java
class Random {

    final double PI = 3.14;
}
```

After initialization:

```java
PI = 2.14; // ❌ ERROR
```

So:

> A final variable cannot be reassigned after it has been initialized.

---

# 28. Final Variable Must Be Initialized

This is valid:

```java
final int x = 10;
```

It is also valid to initialize it later exactly once:

```java
final int x;

x = 10;
```

But:

```java
final int x;

x = 10;
x = 20; // ❌
```

is invalid.

---

# 29. Final Instance Variable

A final instance variable can be initialized in:

### At declaration

```java
class Student {

    final int id = 101;
}
```

### Instance initializer

```java
class Student {

    final int id;

    {
        id = 101;
    }
}
```

### Constructor

```java
class Student {

    final int id;

    Student(int id) {
        this.id = id;
    }
}
```

This is useful when each object needs its own value that should not change after construction.

```java
Student s1 = new Student(101);
Student s2 = new Student(102);
```

Each object can have a different final value.

---

# 30. Naming Convention for Constants

Java conventionally uses uppercase letters for constants.

One word:

```java
final int MAX;
```

Multiple words:

```java
final int MAX_VALUE;
```

For a true class-level constant, we commonly use:

```java
static final double PI = 3.14159;
```

Notice:

```text
static → shared by class
final  → cannot be reassigned
```

Together:

```text
static final
     ↓
class-level constant
```

---

# 31. `final` Reference Variable — Important ⭐

`final` does **not** necessarily make an object immutable.

Example:

```java
final Student s = new Student();

s = new Student(); // ❌
```

You cannot make `s` refer to another object.

But you may still modify the object:

```java
s.name = "Pratik"; // ✅
```

So:

```text
final reference
       ↓
reference cannot change
       ↓
object may still be mutable
```

This is an extremely common interview question.

---

# 32. Final Parameter

A method parameter can be declared `final`.

```java
void addItem(final int a) {

    a = a + 10; // ❌
}
```

The parameter cannot be reassigned inside the method.

### Important

It does **not** mean the value is globally constant.

It only prevents reassignment of that parameter inside that method.

---

# 33. Final Reference Parameter

Consider:

```java
void update(final Student s) {

    s.name = "Pratik"; // ✅
    
    s = new Student(); // ❌
}
```

Why?

Because:

```text
s = new Student()
```

tries to change the reference.

But:

```text
s.name = "Pratik"
```

modifies the object being referenced.

So `final` protects the **reference**, not necessarily the object.

---

# 34. Final Method

A method declared `final` cannot be overridden by a subclass.

```java
class Parent {

    final void display() {
        System.out.println("Parent");
    }
}

class Child extends Parent {

    void display() {   // ❌ ERROR
    }
}
```

Why?

The parent has declared that this method cannot be replaced by subclass overriding.

---

# 35. Why Use Final Methods?

A final method can be useful when a class wants to guarantee that a particular implementation cannot be overridden by subclasses.

It can help preserve a specific behavior or invariant.

---

# 36. Final Class

A class declared `final` cannot be extended.

```java
final class Student {
}
```

This is invalid:

```java
class CollegeStudent extends Student {  // ❌
}
```

### Examples from Java

```java
final class String
```

`String` is final, so you cannot extend it.

---

# 37. Why Is `String` Final?

`String` is immutable and widely used throughout Java.

Making it final prevents subclasses from changing or overriding its behavior in ways that could undermine assumptions made by the platform and application code.

For interviews, a concise answer is:

> `String` is final to prevent subclassing and preserve its intended immutable and trusted behavior.

---

# 38. `final` vs `finally` vs `finalize`

These three are completely different.

| Keyword      | Meaning                                                                                            |
| ------------ | -------------------------------------------------------------------------------------------------- |
| `final`      | Restricts variable reassignment, method overriding, or class inheritance                           |
| `finally`    | Block used with exception handling that normally executes after `try`/`catch`                      |
| `finalize()` | Legacy/deprecated mechanism associated with object finalization; should not be used in modern Java |

> **Important:** `finalize()` has been deprecated for removal in modern Java. Do not recommend it for resource management.

---

# ==================== ⭐ Quick Revision ====================

## `static`

```text
static variable
    ↓
belongs to class
    ↓
one shared class-level field
```

```text
static method
    ↓
belongs to class
    ↓
can be called without object
    ↓
cannot directly use this
    ↓
cannot directly access instance members
```

```text
static block
    ↓
runs during class initialization
    ↓
used for static initialization
    ↓
runs once per class initialization
```

### What can be static?

```text
Variable          ✅
Method            ✅
Initializer block ✅
Nested class      ✅
Parameter         ❌
Local variable    ❌
Top-level class   ❌
```

---

## `final`

```text
final variable
    ↓
cannot be reassigned

final parameter
    ↓
cannot be reassigned inside method

final method
    ↓
cannot be overridden

final class
    ↓
cannot be extended
```

### Most important:

```java
final Student s = new Student();
```

means:

```text
s cannot point to another object
BUT
the Student object may still be modified
```

---

# ==================== ⭐ Important Interview Questions ====================

### Q1. What is `static` in Java?

**Answer:**

> `static` indicates that a member belongs to the class rather than to individual objects. Static variables are shared among instances, and static methods can be invoked without creating an object.

---

### Q2. What is the difference between static and instance variables?

**Answer:**

> An instance variable belongs to an individual object, so each object has its own copy. A static variable belongs to the class and is shared by all instances of that class.

---

### Q3. Why do we use static variables?

**Answer:**

> We use static variables when a value represents data shared across all objects of a class. This avoids maintaining a separate copy of the same logical value in every object.

---

### Q4. Can a static variable be accessed using an object?

**Answer:**

> Yes, Java allows it, but accessing a static member through the class name is preferred because it clearly communicates that the member belongs to the class.

```java
Student.college; // preferred
```

---

### Q5. Can a static method access an instance variable directly?

**Answer:**

> No. A static method does not have an implicit reference to a particular object, while an instance variable belongs to an object. An object reference must be explicitly provided to access the instance variable.

---

### Q6. Why can't a static method use `this`?

**Answer:**

> `this` represents the current object, but a static method can be called without an object. Therefore, a static method has no implicit current-object reference and cannot use `this`.

---

### Q7. Can a static method access a static variable?

**Answer:**

> Yes. Both belong to the class, so a static method can directly access static variables of the same class.

---

### Q8. Why is `main()` static?

**Answer:**

> The `main()` method is static so that the JVM can invoke the program's entry point without first creating an instance of the class.

---

### Q9. When are static blocks executed?

**Answer:**

> Static blocks execute during class initialization, before the class is used for operations that require initialization. A static block executes once per class initialization.

---

### Q10. What is the difference between a static block and a constructor?

**Answer:**

> A static block belongs to the class and executes during class initialization, generally once per class initialization. A constructor belongs to object creation and executes whenever an object is created.

---

### Q11. Can a constructor be static?

**Answer:**

> No. A constructor is associated with creating and initializing an object, while `static` members belong to the class. Therefore, constructors cannot be static.

---

### Q12. Can a top-level class be static?

**Answer:**

> No. A top-level class cannot be declared static. However, a nested class can be declared static.

---

### Q13. Can a local variable be static?

**Answer:**

> No. The `static` modifier is used for class members and certain nested declarations, not for local variables or method parameters.

---

### Q14. What is the difference between static method hiding and method overriding?

**Answer:**

> Instance methods participate in runtime overriding, whereas static methods belong to the class and are resolved based on the class/reference context. When a subclass declares a static method with the same signature, it is called method hiding rather than overriding.

---

### Q15. What is `final` in Java?

**Answer:**

> `final` is a restriction mechanism. A final variable cannot be reassigned, a final method cannot be overridden, and a final class cannot be extended.

---

### Q16. Does final make an object immutable?

**Answer:**

> No. If a reference variable is final, the reference cannot be changed to point to another object, but the referenced object can still be modified if that object's class allows mutation.

---

### Q17. What is the difference between `final` and `static final`?

**Answer:**

> A final instance variable can have a separate final value for each object, whereas a `static final` variable belongs to the class and represents one shared constant.

Example:

```java
final int id;
```

can differ between objects, while:

```java
static final double PI = 3.14159;
```

is shared by the class.

---

### Q18. Can a final variable be initialized later?

**Answer:**

> Yes. A final variable can be initialized at declaration, in an initializer block, or in a constructor, depending on the kind of variable. However, it must be assigned exactly once before it is used.

---

### Q19. Can a final method be overridden?

**Answer:**

> No. A final method cannot be overridden by a subclass.

---

### Q20. Can a final class be inherited?

**Answer:**

> No. A final class cannot be extended.

---

### Q21. Why is Java `String` final?

**Answer:**

> `String` is final to prevent subclassing and preserve the assumptions and guarantees associated with its immutable and trusted behavior.

---

### Q22. What is the difference between `final`, `finally`, and `finalize()`?

**Answer:**

> `final` is a Java keyword used to restrict reassignment, overriding, or inheritance. `finally` is an exception-handling block that normally executes after `try`/`catch`. `finalize()` is a legacy finalization mechanism that is deprecated for removal and should not be used for resource management.

---

### Q23. What happens if we modify a final reference object's field?

```java
final Student s = new Student();
s.name = "Pratik";
```

**Answer:**

> This is valid if `name` is mutable. `final` prevents the reference `s` from being reassigned; it does not automatically make the referenced `Student` object immutable.

---

### Q24. Why can't Java make the `main()` method non-static and create its object automatically?

**Answer:**

> The Java entry-point mechanism is designed so that the JVM can invoke the static `main` method directly on the class without requiring application-level object construction first. This provides a simple class-level entry point for starting the application.
