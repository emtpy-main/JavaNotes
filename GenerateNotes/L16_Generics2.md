# // =========== Generics Part-2 in Java ============//

Part-2 is mainly about **Invariant Generics → Wildcards → `extends` / `super` → PECS → Type Erasure → Limitations of Generics → Bridge Methods**.

I also corrected a few important points in your original notes, especially around **runtime errors with wildcards, arrays, and type erasure**.

---

# ⭕ 1. General-Purpose Class

A **general-purpose class** is designed to work with different kinds of data instead of being restricted to one specific type.

Example:

```java
class Box<T> {
    T value;

    Box(T value) {
        this.value = value;
    }
}
```

Now the same class can be used as:

```java
Box<Integer> b1 = new Box<>(10);
Box<String> b2 = new Box<>("Hello");
Box<Double> b3 = new Box<>(10.5);
```

So:

```text
                 Box<T>
                   |
        ┌──────────┼──────────┐
        ↓          ↓          ↓
   Box<Integer> Box<String> Box<Double>
```

---

# ⭐ 2. Generics are Invariant

This is one of the **most important concepts in Generics**.

### Normal inheritance:

```java
class Animal { }

class Dog extends Animal { }
```

Therefore:

```java
Animal a = new Dog();  // ✅
```

Because:

```text
Dog
 ↓
Animal
```

A `Dog` **is an** `Animal`.

---

## But Generic types are different

Suppose:

```java
List<Dog> dogs = new ArrayList<>();
```

You might think:

```java
List<Animal> animals = dogs; // ❌
```

should work because:

```text
Dog extends Animal
```

But Java does **not** allow this.

```text
Dog extends Animal
        ↓
List<Dog> ≠ List<Animal>
```

### ⭐ Definition

> **Generics in Java are invariant, meaning that even if `Dog` is a subtype of `Animal`, `List<Dog>` is not a subtype of `List<Animal>`.**

In general:

```text
A extends B

does NOT imply

Generic<A> extends Generic<B>
```

---

# ❓ Why Doesn't Java Allow `List<Dog>` → `List<Animal>`?

Suppose Java allowed:

```java
List<Dog> dogs = new ArrayList<>();

List<Animal> animals = dogs;
```

Initially:

```text
dogs:
[Dog, Dog, Dog, Dog]
```

Since `animals` is declared as:

```java
List<Animal>
```

we should be able to do:

```java
animals.add(new Animal());
```

If Java allowed the assignment, this would effectively put:

```text
[Dog, Dog, Dog, Dog, Animal]
```

inside the original `List<Dog>`.

But now:

```java
Dog d = dogs.get(4);
```

would be unsafe because the object is actually an `Animal`.

If we then do:

```java
d.bark();
```

the program could fail.

### ⭐ Therefore:

Java rejects:

```java
List<Animal> animals = dogs;
```

at **compile time**.

This preserves **type safety**.

---

# 🧠 Important Difference: Reading vs Writing

If `List<Dog>` were treated as `List<Animal>`:

### Reading

```java
Animal a = animals.get(0);
```

would be safe because every Dog is an Animal.

### Writing

```java
animals.add(new Animal());
```

would NOT be safe because the underlying list is actually a `List<Dog>`.

So the real problem is **writing**.

---

# ⭐ 3. Invariance

```text
Dog extends Animal

But:

List<Dog>        ❌ List<Animal>
List<Animal>     ❌ List<Dog>
```

Therefore:

```text
Generic<A> and Generic<B>
```

are unrelated with respect to inheritance when `A` and `B` are different type arguments.

This property is called:

> **Invariance**

---

# ⭕ 4. Arrays are Covariant

Java arrays behave differently from Generics.

```java
Dog[] dogs = new Dog[10];

Animal[] animals = dogs;
```

This is:

```text
Dog[] → Animal[]
```

and Java allows it.

Why?

Because arrays are **covariant**.

### Definition

> If `Dog` is a subtype of `Animal`, then `Dog[]` is also considered a subtype of `Animal[]`.

```text
Dog extends Animal

therefore:

Dog[] extends Animal[]
```

---

# 🚨 But Arrays Can Cause Runtime Errors

Consider:

```java
Dog[] dogs = new Dog[10];

Animal[] animals = dogs;

animals[0] = new Dog();    // ✅
animals[1] = new Dog();    // ✅
animals[2] = new Animal(); // ❌
```

The last statement causes:

```text
ArrayStoreException
```

### Why?

The actual array object was created as:

```java
new Dog[10]
```

So internally it can store only:

```text
Dog
```

Even though the reference is:

```java
Animal[] animals
```

The JVM checks the **actual array type** at runtime.

---

# ⭐ Generic vs Array

```text
             Generics                 Arrays
                |                       |
           Invariant                Covariant
                |                       |
List<Dog> ❌ List<Animal>       Dog[] → Animal[] ✅
                |                       |
        Error at compile time       Error at runtime
```

This is a very common interview comparison.

---

# ⭕ 5. Wildcards `?`

Because generic types are invariant, Java provides **Wildcards** to make generic APIs more flexible.

### Definition

> A wildcard `?` represents an unknown type.

Syntax:

```java
List<?> list;
```

Meaning:

```text
List of some specific type,
but we don't know what that type is.
```

It could be:

```java
List<Dog>
List<Animal>
List<Cat>
List<Integer>
List<String>
```

---

# ⭐ Example

```java
void printList(List<?> list) {

}
```

Now all of these are valid:

```java
printList(new ArrayList<Dog>());
printList(new ArrayList<Animal>());
printList(new ArrayList<Cat>());
printList(new ArrayList<Integer>());
printList(new ArrayList<String>());
```

Because `?` means:

```text
"some unknown type"
```

---

# ⭕ 6. Unbounded Wildcard `<?>`

Example:

```java
void printList(List<?> list) {

    Object obj = list.get(0);
}
```

### Reading

You can safely read values as:

```java
Object
```

because every reference type in Java is ultimately an `Object`.

```java
Object obj = list.get(0); // ✅
```

But:

```java
String s = list.get(0);   // ❌
Dog d = list.get(0);      // ❌
```

The compiler doesn't know whether the list contains:

```text
String
Dog
Integer
Animal
...
```

---

# ❌ Writing to `List<?>`

Consider:

```java
void fun(List<?> list) {

    list.add("Hello");    // ❌
    list.add(new Dog());  // ❌
    list.add(new Animal());// ❌
}
```

Why?

Because the compiler doesn't know the actual type.

For example:

```java
List<Dog> dogs = new ArrayList<>();

fun(dogs);
```

If this were allowed:

```java
list.add(new Animal());
```

we could insert an Animal into `List<Dog>`.

So Java prevents it.

---

# ⭐ Important Correction

You wrote that:

```java
list.add("Hello"); // runtime error
```

That's not correct.

It is a:

```text
❌ Compile-time error
```

The compiler rejects the operation before the program runs.

---

# 🧠 What Can We Add to `List<?>`?

There is one important exception:

```java
list.add(null); // ✅
```

Why?

`null` can be assigned to any reference type.

So:

```java
List<?> list;

list.add(null); // valid
```

This is a nice interview point.

---

# ⭕ 7. Upper-Bounded Wildcard `? extends`

Syntax:

```java
List<? extends Animal>
```

Meaning:

> A List of some unknown type that is `Animal` or a subclass of `Animal`.

Possible lists:

```java
List<Animal>
List<Dog>
List<Cat>
List<Labrador>
```

assuming those classes extend `Animal`.

But:

```java
List<Integer>
```

❌ not allowed.

---

# ⭐ Example

```java
void printAnimals(List<? extends Animal> list) {

    for (Animal a : list) {
        a.eat();
    }
}
```

Now:

```java
List<Dog> dogs = new ArrayList<>();
List<Cat> cats = new ArrayList<>();
List<Animal> animals = new ArrayList<>();

printAnimals(dogs);     // ✅
printAnimals(cats);     // ✅
printAnimals(animals);  // ✅
```

Why can we read as `Animal`?

Because whatever the actual type is, it is guaranteed to be:

```text
Animal
   ↑
  Dog
  Cat
```

Therefore:

```java
Animal a = list.get(0);
```

is safe.

---

# ❌ Writing to `? extends`

```java
void fun(List<? extends Animal> list) {

    list.add(new Dog());    // ❌
    list.add(new Cat());    // ❌
    list.add(new Animal()); // ❌
}
```

This sometimes feels confusing.

Let's understand why.

---

# ⭐ Why Can't We Add to `List<? extends Animal>`?

Suppose:

```java
List<Dog> dogs = new ArrayList<>();
```

and we pass it:

```java
fun(dogs);
```

Inside `fun`, the parameter is:

```java
List<? extends Animal>
```

The compiler doesn't know whether it is:

```text
List<Dog>
List<Cat>
List<Labrador>
List<Animal>
```

Suppose the compiler allowed:

```java
list.add(new Dog());
```

If the actual list were:

```text
List<Cat>
```

then we would insert:

```text
Dog
```

into:

```text
List<Cat>
```

❌ Type safety violation.

Therefore Java doesn't allow adding non-null values.

---

# ⭐ "But Dog is an Animal!"

Yes.

The problem is not:

```text
Dog → Animal
```

That is valid.

The problem is:

```text
Which exact subtype is this list?
```

It could be:

```text
List<Dog>
List<Cat>
List<Labrador>
```

The compiler doesn't know.

Therefore:

```text
Reading → Safe
Writing → Unsafe
```

---

# ⭐ Upper Bound Summary

```text
List<? extends Animal>
          |
          ↓
     Unknown subtype
          |
   ┌──────┼──────┐
   ↓      ↓      ↓
  Dog    Cat   Animal
```

### Allowed:

```java
Animal a = list.get(0); // ✅
```

### Not allowed:

```java
list.add(new Dog());    // ❌
list.add(new Animal()); // ❌
```

### Memory Trick:

```text
? extends → READ
```

---

# ⭕ 8. Lower-Bounded Wildcard `? super`

Syntax:

```java
List<? super Animal>
```

Meaning:

> A List of some unknown type that is `Animal` or a superclass of `Animal`.

Possible:

```text
List<Animal>
List<Object>
```

assuming `Object` is the superclass of `Animal`.

But:

```java
List<Dog>
```

❌ not allowed.

---

# ⭐ Example

```java
void fun(List<? super Animal> list) {

}
```

We can pass:

```java
List<Animal> animals = new ArrayList<>();
List<Object> objects = new ArrayList<>();

fun(animals); // ✅
fun(objects); // ✅
```

But:

```java
List<Dog> dogs = new ArrayList<>();

fun(dogs); // ❌
```

Because `Dog` is a **subclass** of Animal, not a superclass.

---

# ⭐ Writing with `? super`

Now we can safely add:

```java
void fun(List<? super Animal> list) {

    list.add(new Animal());
    list.add(new Dog());
    list.add(new Cat());
}
```

All are valid if:

```text
Dog extends Animal
Cat extends Animal
```

Why?

Because the list is guaranteed to be capable of storing an `Animal`.

It can be:

```text
List<Animal>
```

or:

```text
List<Object>
```

Both can hold an Animal and its subclasses.

---

# ⭐ Why Can We Add Dog?

Suppose:

```java
List<Object> list = new ArrayList<>();
```

We can add:

```java
list.add(new Animal());
list.add(new Dog());
list.add(new Cat());
```

No problem.

Or:

```java
List<Animal> list = new ArrayList<>();
```

We can also add:

```java
list.add(new Animal());
list.add(new Dog());
list.add(new Cat());
```

Therefore both possibilities are safe.

---

# ❌ Reading from `? super`

Consider:

```java
void fun(List<? super Animal> list) {

    Animal a = list.get(0); // ❌
}
```

Why?

The actual list could be:

```java
List<Object>
```

and:

```java
Object obj = list.get(0);
```

could contain:

```text
String
Integer
Animal
Dog
...
```

Therefore the compiler can guarantee only:

```java
Object obj = list.get(0);
```

---

# ⭐ Why `Animal a = list.get(0)` Is Unsafe

Suppose:

```java
List<Object> list = new ArrayList<>();

list.add("Hello");
```

Then:

```java
Object obj = list.get(0);
```

is safe.

But:

```java
Animal a = list.get(0);
```

would require assuming that `"Hello"` is an Animal.

That's not guaranteed.

So only:

```java
Object
```

is safe for reading.

---

# ⭐ Lower Bound Summary

```text
List<? super Animal>
          |
          ↓
     Animal or Parent
          |
      ┌───┴────┐
      ↓        ↓
  Animal     Object
```

### Writing:

```java
list.add(new Animal()); // ✅
list.add(new Dog());    // ✅
list.add(new Cat());    // ✅
```

### Reading:

```java
Object obj = list.get(0); // ✅
Animal a = list.get(0);   // ❌
```

### Memory Trick:

```text
? super → WRITE
```

---

# ⭐ 9. `extends` vs `super`

| Feature            | `? extends T`       | `? super T`             |
| ------------------ | ------------------- | ----------------------- |
| Meaning            | T or subclass       | T or superclass         |
| Direction          | Upward from child   | Upward toward parent    |
| Read as            | `T`                 | `Object`                |
| Add `T`?           | ❌                   | ✅                       |
| Add subclass of T? | ❌                   | ✅                       |
| Main purpose       | Producer            | Consumer                |
| Concept            | Covariant-style use | Contravariant-style use |

---

# ⭐ Example to Remember

```java
List<? extends Animal> list;
```

Think:

```text
"I want to READ Animals."
```

```java
List<? super Animal> list;
```

Think:

```text
"I want to WRITE Animals."
```

---

# ⭐ 10. PECS Rule

One of the most important Generic interview rules:

> **PECS = Producer Extends, Consumer Super**

---

## Producer → `extends`

If a collection **produces/gives** values to you:

```java
List<? extends Animal>
```

Example:

```java
void printAnimals(List<? extends Animal> animals) {

    for (Animal a : animals) {
        a.eat();
    }
}
```

The list is producing Animals.

```text
Producer → extends
```

---

## Consumer → `super`

If a collection **consumes/accepts** values from you:

```java
List<? super Animal>
```

Example:

```java
void addAnimals(List<? super Animal> animals) {

    animals.add(new Dog());
    animals.add(new Cat());
}
```

The list consumes Animals.

```text
Consumer → super
```

---

# ⭐ 11. `List<T>` vs `List<?>` vs Bounds

### `List<T>`

Exact type is known.

```java
List<Dog>
```

Only Dogs can be added.

---

### `List<?>`

Unknown type.

```java
List<?>
```

Can read as `Object`, but cannot add non-null values.

---

### `List<? extends Animal>`

Unknown subtype of Animal.

```text
List<Animal>
List<Dog>
List<Cat>
```

Can read as Animal.

---

### `List<? super Animal>`

Unknown supertype of Animal.

```text
List<Animal>
List<Object>
```

Can add Animal and subclasses.

---

# ⭐ 12. Invariant / Covariant / Contravariant

Your original notes use:

```text
List<T>          → Invariant
List<? extends T> → Covariant
List<? super T>   → Contravariant
```

This is a useful way to think about wildcard variance.

### Invariant

```java
List<Dog>
```

is not a subtype of:

```java
List<Animal>
```

---

### Covariant

```java
List<? extends Animal>
```

can refer to:

```text
List<Dog>
List<Cat>
List<Animal>
```

---

### Contravariant

```java
List<? super Animal>
```

can refer to:

```text
List<Animal>
List<Object>
```

---

# ⭐ 13. When to Use Generic Type vs Wildcard?

This is another important point.

### Use a type parameter when:

You need to **relate multiple types**.

Example:

```java
static <T> T getFirst(List<T> list) {
    return list.get(0);
}
```

Here the return type is related to the list's element type.

---

### Use a wildcard when:

You don't need to preserve a specific type relationship and only need flexibility.

Example:

```java
static void printList(List<?> list) {
    for (Object x : list) {
        System.out.println(x);
    }
}
```

You don't care what exact type the list contains.

---

# ⭐ Simple Rule

```text
Need to connect types together?
        ↓
Use <T>

Don't care about exact type?
        ↓
Use ?
```

---

# ⭕ 14. Type Erasure

### Question:

> Does the JVM know Generic type information at runtime?

### Answer:

> Java Generics are primarily a compile-time feature. The Java compiler performs generic type checking and then uses type erasure so that parameterized type information is generally not available in the same form at runtime.

Example:

```java
class Box<T> {

    T value;

    T getValue() {
        return value;
    }
}
```

Conceptually after erasure:

```java
class Box {

    Object value;

    Object getValue() {
        return value;
    }
}
```

---

# ⭐ Rule 1: Unbounded Type

Before:

```java
class Box<T> {
    T value;
}
```

After erasure, conceptually:

```java
class Box {
    Object value;
}
```

Because `T` has no bound.

---

# ⭐ Rule 2: Bounded Type

Before:

```java
class Box<T extends Number> {
    T value;
}
```

After erasure, conceptually:

```java
class Box {
    Number value;
}
```

The **leftmost bound** becomes the erased type.

---

# ⭐ Rule 3: Compiler Inserts Casts

Suppose:

```java
Box<Integer> box = new Box<>();

Integer x = box.getValue();
```

Conceptually, after erasure:

```java
Box box = new Box();

Integer x = (Integer) box.getValue();
```

The compiler inserts the necessary cast.

### ⭐ Important

The cast is generated by the **compiler**, not written explicitly by you.

---

# 🚨 Important Correction in Your Note

You wrote:

> "here, we have no chance to get ClassCastException because generics make sure value contain Integer only."

The more accurate statement is:

> **Correctly typed generic code prevents many incorrect casts at compile time, but unchecked/raw-type operations or unsafe casts can still lead to `ClassCastException` at runtime.**

For example:

```java
Box raw = new Box();
raw.value = "Hello";

Box<Integer> box = raw;

Integer x = box.getValue();
```

This can result in:

```text
ClassCastException
```

because raw/unchecked usage bypassed normal generic type checking.

So:

```text
Generics → greatly reduce ClassCastException
```

but don't claim:

```text
Generics → ClassCastException can never happen
```

---

# ⭕ 15. Things You Cannot Do Directly With Generics

---

## ❌ 1. `instanceof` with Parameterized Type

You cannot:

```java
List<String> list = new ArrayList<>();

if (list instanceof List<String>) {
    // ❌
}
```

Because generic type arguments are erased.

But:

```java
if (list instanceof List<?>) {
    // ✅
}
```

is allowed.

---

# ❌ 2. Generic Type in `instanceof`

```java
if (obj instanceof List<Integer>) // ❌
```

Not allowed.

But:

```java
if (obj instanceof List<?>) // ✅
```

is allowed.

---

# ❌ 3. Overloading Based Only on Generic Type Arguments

You wrote:

```java
class Demo {

    void print(List<String> list) {}

    void print(List<Integer> list) {}
}
```

❌ Compile-time error.

Why?

Because after erasure, both effectively become:

```java
void print(List list)
void print(List list)
```

Java cannot distinguish them by generic type argument.

This causes a:

```text
name clash
```

---

# ⭐ But This Is Allowed

```java
void print(List<String> list) {}

void print(Set<String> set) {}
```

because after erasure:

```text
print(List)
print(Set)
```

These are different method signatures.

---

# ⭕ 16. Generic Type Cannot Be Used Directly With `static`

Example:

```java
class Box<T> {

    static T value; // ❌
}
```

Why?

Because `T` belongs to the generic type instance:

```text
Box<Integer>
Box<String>
```

But `static` belongs to the class itself.

There cannot be a separate static `T` for every type argument.

---

# ⭕ 17. Cannot Create `new T()`

```java
class Box<T> {

    T create() {
        return new T(); // ❌
    }
}
```

Why?

Because the JVM does not know the concrete type of `T` in the required way at runtime.

---

# ⭕ 18. Generic Arrays

This is not allowed:

```java
T[] arr = new T[10]; // ❌
```

because of type erasure and runtime array type requirements.

You may encounter workarounds involving:

```java
(T[]) new Object[10]
```

but that creates an unchecked cast and should be used carefully.

---

# ⭕ 19. Why Does Type Erasure Exist?

This is a **very important interview question**.

Generics were introduced in:

```text
Java 5
```

Before Java 5:

```java
List list = new ArrayList();

list.add("Hello");
list.add(10);
```

was valid.

After Java 5:

```java
List<String> list = new ArrayList<>();
```

was introduced.

Java wanted to introduce Generics **without breaking existing Java code and existing bytecode compatibility**.

Therefore, Java implemented Generics largely through:

```text
Compile-time checking
        +
Type Erasure
```

rather than requiring a completely new runtime generic object model.

---

# ⭐ Main Reason for Type Erasure

```text
Generics introduced
       ↓
Existing Java code already existed
       ↓
Need backward compatibility
       ↓
Generic information handled mainly by compiler
       ↓
Erasure for runtime compatibility
```

---

# ⭕ 20. Bridge Methods

This is an advanced but important concept.

Consider:

```java
class Parent<T> {

    T get() {
        return null;
    }
}
```

Now:

```java
class Child extends Parent<String> {

    @Override
    String get() {
        return "Hello";
    }
}
```

At source-code level:

```text
Parent<T>
    ↓
Parent<String>
    ↓
Child
```

The parent method conceptually becomes:

```java
Object get()
```

after erasure.

But the child has:

```java
String get()
```

Now there appears to be a mismatch.

---

# ⭐ Compiler-Generated Bridge Method

To preserve polymorphism, the compiler can generate a synthetic **bridge method** conceptually like:

```java
Object get() {
    return get();
}
```

which delegates to:

```java
String get()
```

Conceptually:

```text
Parent after erasure
        |
        | Object get()
        ↓
      Child
        |
        | String get()
        ↓
Compiler creates bridge method
        |
        ↓
Object get() → String get()
```

### ⭐ Purpose

> A bridge method is a compiler-generated synthetic method used to preserve polymorphism when type erasure causes method signatures to differ between a generic superclass/interface and its subclass implementation.

---

# ⭐ Important

Bridge methods are:

```text
✔️ generated by compiler
✔️ usually synthetic
✔️ related to type erasure
✔️ preserve polymorphism
```

You normally don't write them yourself.

---

# ⭕ 21. Why Don't Generics Support Primitive Types?

You cannot write:

```java
Box<int> b = new Box<>(); // ❌
```

Generics require **reference types**.

Use:

```java
Box<Integer> b = new Box<>();
```

Why?

Because generic type parameters are designed around reference-type semantics and type erasure.

Conceptually:

```java
class Box<T> {
    T value;
}
```

For an unbounded `T`, erasure gives something like:

```java
Object value;
```

But:

```text
Object
```

is a superclass/reference type for objects, not a superclass of primitive `int`.

Therefore:

```text
int
 ↓
primitive
```

whereas:

```text
Integer
 ↓
Object
```

---

# ⭐ Primitive → Wrapper

```text
int       → Integer
long      → Long
double    → Double
float     → Float
boolean   → Boolean
char      → Character
byte      → Byte
short     → Short
```

Then Java's **autoboxing/unboxing** makes generic collections convenient:

```java
List<Integer> list = new ArrayList<>();

list.add(10);       // int → Integer
int x = list.get(0); // Integer → int
```

---

# 🔥 22. Complete Wildcard Example

Let's combine everything.

```java
class Animal {
    void eat() {
        System.out.println("Eating");
    }
}

class Dog extends Animal {
    void bark() {
        System.out.println("Barking");
    }
}

class Cat extends Animal {
    void meow() {
        System.out.println("Meowing");
    }
}
```

### Producer:

```java
static void printAnimals(
        List<? extends Animal> animals) {

    for (Animal a : animals) {
        a.eat();
    }
}
```

Call:

```java
List<Dog> dogs = new ArrayList<>();

printAnimals(dogs); // ✅
```

---

### Consumer:

```java
static void addAnimals(
        List<? super Animal> animals) {

    animals.add(new Dog());
    animals.add(new Cat());
}
```

Call:

```java
List<Animal> animals = new ArrayList<>();

addAnimals(animals); // ✅
```

Also:

```java
List<Object> objects = new ArrayList<>();

addAnimals(objects); // ✅
```

---

# 🧠 23. The Most Important Wildcard Diagram

```text
                     Animal
                    /      \
                  Dog      Cat
                   |
                Labrador
```

### `? extends Animal`

```text
List<? extends Animal>

Possible:
    List<Animal>
    List<Dog>
    List<Cat>
    List<Labrador>

READ → Animal
WRITE → ❌
```

### `? super Animal`

```text
List<? super Animal>

Possible:
    List<Animal>
    List<Object>

READ → Object
WRITE → Animal + subclasses
```

### `?`

```text
List<?>

Possible:
    List<Anything>

READ → Object
WRITE → ❌ except null
```

---

# ⭐ 24. Generic vs Wildcard — When to Use What?

```text
                     Need Generic?
                           |
                 ┌─────────┴─────────┐
                 |                   |
                YES                  NO
                 |                   |
       Need relationship       Don't care about
       between types?          exact type?
                 |                   |
                <T>                 ?
```

### Example:

```java
static <T> T first(List<T> list) {
    return list.get(0);
}
```

Here:

```text
Input type = T
Return type = T
```

There is a relationship.

---

Whereas:

```java
static void print(List<?> list) {
    for (Object x : list) {
        System.out.println(x);
    }
}
```

We don't care about the exact type.

---

# 🎯 Important Interview Questions

## Q1. Why is `List<Dog>` not a subtype of `List<Animal>`?

**Answer:**

> Java Generics are invariant. Although `Dog` is a subtype of `Animal`, `List<Dog>` is not a subtype of `List<Animal>` because allowing such assignment would allow an `Animal` to be inserted into a list that is supposed to contain only `Dog` objects, violating type safety.

---

## Q2. What is invariance in Generics?

**Answer:**

> Invariance means that even if one type is a subtype of another type, their corresponding generic types do not automatically have the same subtype relationship. For example, `Dog` extends `Animal`, but `List<Dog>` is not a subtype of `List<Animal>`.

---

## Q3. Are Java arrays invariant?

**Answer:**

> No. Java arrays are covariant. If `Dog` extends `Animal`, then `Dog[]` can be assigned to an `Animal[]` reference. However, inserting an incompatible object can cause `ArrayStoreException` at runtime.

---

## Q4. What is a wildcard in Generics?

**Answer:**

> A wildcard represented by `?` represents an unknown type and allows generic APIs to work with different parameterized types.

---

## Q5. What is `List<?>`?

**Answer:**

> `List<?>` represents a list of an unknown type. We can safely read its elements as `Object`, but we cannot add non-null values because the compiler does not know the list's actual element type.

---

## Q6. What is `? extends`?

**Answer:**

> `? extends T` represents an unknown type that is `T` or a subtype of `T`. It is mainly used when a generic structure acts as a producer of values.

---

## Q7. What is `? super`?

**Answer:**

> `? super T` represents an unknown type that is `T` or a supertype of `T`. It is mainly used when a generic structure acts as a consumer of values.

---

## Q8. Why can't we add elements to `List<? extends Animal>`?

**Answer:**

> Because the exact subtype is unknown. The list could be a `List<Dog>`, `List<Cat>`, or `List<Animal>`. Adding a specific object such as a `Dog` could violate the actual list's type if it were a `List<Cat>`.

---

## Q9. Why can we add elements to `List<? super Animal>`?

**Answer:**

> Because the list is guaranteed to have `Animal` or one of its supertypes as its element type. Therefore, an `Animal` or any subclass of `Animal` can safely be stored in it.

---

## Q10. Why can we only read `Object` from `List<? super Animal>`?

**Answer:**

> Because the actual list could be a `List<Object>`, and an `Object` list may contain objects that are not Animals. Therefore, the compiler can guarantee only that a retrieved value is an `Object`.

---

## Q11. What is PECS?

**Answer:**

> PECS stands for **Producer Extends, Consumer Super**. We generally use `? extends T` when a structure produces values of type `T`, and `? super T` when a structure consumes values of type `T`.

---

## Q12. What is the difference between `List<T>` and `List<?>`?

**Answer:**

> `List<T>` represents a list of a specific type parameter `T`, while `List<?>` represents a list of an unknown type. A type parameter can establish relationships between multiple types, whereas a wildcard is generally used when the exact type is not important.

---

## Q13. What is type erasure?

**Answer:**

> Type erasure is the process used by Java to remove most generic type-parameter information after compile-time checking. Unbounded type parameters are generally erased to `Object`, while bounded type parameters are erased to their leftmost bound.

---

## Q14. Why does Java use type erasure?

**Answer:**

> Java uses type erasure mainly to preserve backward compatibility with Java code and bytecode written before Generics were introduced in Java 5.

---

## Q15. Can we use `instanceof List<String>`?

**Answer:**

> No. Java does not allow `instanceof` checks against a parameterized type such as `List<String>` because the generic type argument is erased at runtime. However, `instanceof List<?>` is allowed.

---

## Q16. Can we overload methods only by changing generic type arguments?

**Answer:**

> No. Methods such as `print(List<String>)` and `print(List<Integer>)` have the same erased signature, `print(List)`, so they cause a compile-time name clash.

---

## Q17. What is a bridge method?

**Answer:**

> A bridge method is a synthetic method generated by the Java compiler to preserve polymorphism when type erasure causes the method signatures of a generic superclass or interface and its subclass implementation to differ.

---

## Q18. Why can't Generics use primitive types?

**Answer:**

> Java Generics work with reference types, not primitive types. Therefore, wrapper classes such as `Integer`, `Double`, and `Boolean` must be used instead of `int`, `double`, and `boolean`.

---

## Q19. Can we create `new T()`?

**Answer:**

> No. Java does not allow direct instantiation of a type parameter using `new T()` because the actual type information is not available in the required form at runtime.

---

## Q20. Can we create `new T[10]`?

**Answer:**

> No. Java does not allow direct creation of arrays of a type parameter because generic type information is erased and arrays require runtime component-type information.

---

# 🔥 Final Generics Part-2 Revision

```text
                     GENERICS PART-2
                           |
        ┌──────────────────┼───────────────────┐
        ↓                  ↓                   ↓
    Invariance          Wildcards          Type Erasure
        |                  |                   |
   List<Dog> ≠        ┌────┼────┐          Compile time
   List<Animal>        |    |    |          type checking
                       ?  extends super          ↓
                       |     |     |         Runtime
                    unknown  |      |        erasure
                             |      |
                           Producer Consumer
                             |      |
                           extends super
                             |
                            PECS
```

### ⭐ Must Remember

```text
1. Generics are invariant.

2. Dog extends Animal
   BUT
   List<Dog> is NOT List<Animal>.

3. Arrays are covariant.

4. Dog[] → Animal[] is allowed.

5. Array covariance can cause ArrayStoreException.

6. ? = unknown type.

7. ? extends T = T or subclass.
   Mainly READ / Producer.

8. ? super T = T or superclass.
   Mainly WRITE / Consumer.

9. PECS = Producer Extends, Consumer Super.

10. List<?> → read as Object,
    write only null.

11. Generics are mainly compile-time type safety.

12. Type erasure removes generic type information
    from the runtime representation.

13. Unbounded T → Object after erasure.

14. T extends Number → Number after erasure.

15. Compiler inserts required casts.

16. instanceof List<String> ❌

17. instanceof List<?> ✅

18. Cannot overload only by generic type argument.

19. Bridge methods preserve polymorphism after erasure.

20. Generics don't directly support primitives.
    Use wrapper classes.
```

### 🧩 One concept that ties the whole chapter together

```text
                    Why Wildcards?
                         ↓
                Generics are invariant
                         ↓
              List<Dog> ≠ List<Animal>
                         ↓
             Need controlled flexibility
                         ↓
                    Wildcards
                         |
          ┌──────────────┼──────────────┐
          ↓              ↓              ↓
         <?>       <? extends T>    <? super T>
          |              |              |
     Unknown type      Producer       Consumer
          |              |              |
       Object         Read T         Write T
       on read
```

If you understand this diagram deeply, **Generics Part-2 becomes much easier**, especially when you later study Java Collections, because `List<? extends T>`, `List<? super T>`, and PECS appear frequently in real APIs such as `Collections.copy()`, `Comparator`, and other collection utilities.
