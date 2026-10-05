
```java
//===============================================================
//          ITERABLE INTERFACE & ITERATOR - PART 2
//===============================================================
```

# 7. Why do we need Iterable?

Suppose we have:

```java
List<Integer> list = new ArrayList<>();

list.add(10);
list.add(20);
list.add(30);
```

Since `ArrayList` supports indexed access, we can do:

```java
for(int i = 0; i < list.size(); i++) {
    System.out.println(list.get(i));
}
```

### Important correction ⭐

You wrote:

```java
list.length()
```

For a `List`, use:

```java
list.size()
```

`length` is used with arrays:

```java
arr.length
```

`length()` is used with `String`:

```java
str.length()
```

`size()` is used with collections:

```java
list.size()
```

---

# 8. What about other data structures?

Suppose we have a `Set`:

```java
Set<Integer> set = new HashSet<>();

set.add(10);
set.add(20);
set.add(30);
```

A `Set` doesn't provide indexed access like:

```java
set.get(0);       // ❌
```

So how do we traverse it?

Java provides:

```text
Iterable
   ↓
Iterator
```

---

# 9. What is Iterable?

### Definition

> `Iterable` is an interface that represents an object whose elements can be traversed/iterated one by one.

It provides a mechanism for obtaining an `Iterator`.

Basic conceptual structure:

```java
interface Iterable<T> {
    Iterator<T> iterator();
}
```

The important method is:

```java
iterator()
```

which returns an `Iterator`.

---

# 10. What is Iterator?

### Definition

> `Iterator` is an interface used to traverse elements of a collection sequentially without exposing the internal structure of the collection.

Basic methods:

```java
hasNext()
next()
remove()
forEachRemaining()
```

The most commonly used methods are:

```java
hasNext()
next()
```

---

# 11. How Iterator works?

Consider:

```text
Collection

[10] [20] [30] [40]
 ↑
cursor
```

When we call:

```java
Iterator<Integer> it = collection.iterator();
```

we obtain an iterator.

Then:

```java
it.hasNext()
```

checks whether another element is available.

```java
it.next()
```

returns the next element and advances the iterator.

Example:

```java
while(it.hasNext()) {
    System.out.println(it.next());
}
```

Flow:

```text
hasNext() → true
next()    → 10

hasNext() → true
next()    → 20

hasNext() → true
next()    → 30

hasNext() → false
stop
```

---

# 12. Conceptual Relationship

```text
Iterable
    |
    | iterator()
    ↓
Iterator
    |
    | hasNext()
    | next()
    ↓
Elements
```

Conceptually:

```java
interface Iterable<T> {
    Iterator<T> iterator();
}

interface Iterator<T> {
    boolean hasNext();
    T next();
}
```

---

# 13. How does ArrayList provide Iterator?

Conceptually, we can imagine:

```java
class ArrayList implements Iterable {

    @Override
    public Iterator iterator() {
        return new ArrayListIterator();
    }

    class ArrayListIterator implements Iterator {

        int position = 0;

        public boolean hasNext() {
            return position < size;
        }

        public Integer next() {
            return arr[position++];
        }
    }
}
```

### Important

This is a **conceptual implementation** to understand how the mechanism works.

The actual Java `ArrayList` implementation is more sophisticated.

---

# 14. ArrayList Traversal

```java
List<Integer> list = new ArrayList<>();

list.add(10);
list.add(20);
list.add(30);

Iterator<Integer> it = list.iterator();

while(it.hasNext()) {
    System.out.println(it.next());
}
```

Output:

```text
10
20
30
```

---

# 15. LinkedList Iterator — Conceptual Understanding

Your idea is correct, but there is one important correction:

> `LinkedList` **does have `get(index)`**, because it implements `List`.

However, indexed access is generally slower than in `ArrayList`.

For example:

```java
LinkedList<Integer> list = new LinkedList<>();

list.get(2);
```

is valid.

But internally, a linked list must traverse nodes to reach an index.

Conceptually:

```text
head
 ↓
[10] → [20] → [30] → [40] → null
```

Iterator can simply maintain a reference to the current node:

```java
class LinkedListIterator implements Iterator<Integer> {

    Node current = head;

    public boolean hasNext() {
        return current != null;
    }

    public Integer next() {
        int data = current.data;
        current = current.next;
        return data;
    }
}
```

This is one reason iterators are useful: the traversal mechanism can be adapted to the internal structure of each collection.

---

# 16. What about Set?

Consider:

```java
Set<Integer> set = new HashSet<>();

set.add(10);
set.add(20);
set.add(30);
```

We cannot do:

```java
set.get(0);       // ❌
```

Instead:

```java
Iterator<Integer> it = set.iterator();

while(it.hasNext()) {
    System.out.println(it.next());
}
```

The iterator internally knows how to traverse the particular `Set` implementation.

---

# 17. What about TreeSet?

`TreeSet` maintains its elements according to their sorting order.

Example:

```java
TreeSet<Integer> set = new TreeSet<>();

set.add(30);
set.add(10);
set.add(20);
```

Traversal:

```text
10
20
30
```

Its iterator traverses the underlying tree structure in sorted order.

Conceptually:

```text
        20
       /  \
     10    30
```

The iterator performs the appropriate tree traversal internally.

---

# 18. What about HashSet?

`HashSet` internally uses hashing-based data structures.

Conceptually:

```text
HashSet
   |
Hash Table
   |
Buckets
```

Its iterator traverses the available entries in the hash table.

### Important ⭐

Do **not** assume that `HashSet` traversal is sorted.

```java
HashSet<Integer> set = new HashSet<>();
```

does not guarantee insertion order or sorted order.

---

# 19. What about Map?

There is an important distinction here.

`Map` does **not** implement `Iterable`.

Therefore:

```java
Map<Integer, String> map = new HashMap<>();

for(String value : map) { }     // ❌
```

But we can iterate over:

```java
map.keySet()
map.values()
map.entrySet()
```

For example:

```java
for(Integer key : map.keySet()) {
    System.out.println(key);
}
```

or:

```java
for(Map.Entry<Integer, String> entry : map.entrySet()) {
    System.out.println(entry.getKey() + " " + entry.getValue());
}
```

This is an important interview point.

---

# 20. Custom Iterator

We can make our own class iterable.

Example:

```java
class NameContainer implements Iterable<String> {

    private String[] names;
    private int size;

    NameContainer(String[] names) {
        this.names = names;
        this.size = names.length;
    }

    @Override
    public Iterator<String> iterator() {
        return new NameContainerIterator();
    }

    private class NameContainerIterator
            implements Iterator<String> {

        private int pos = 0;

        @Override
        public boolean hasNext() {
            return pos < size;
        }

        @Override
        public String next() {
            return names[pos++];
        }
    }
}
```

Use:

```java
public class Main {

    public static void main(String[] args) {

        String[] names = {
            "Aditya",
            "Pratik",
            "Rishabh",
            "Rakshit"
        };

        NameContainer container =
                new NameContainer(names);

        Iterator<String> it =
                container.iterator();

        while(it.hasNext()) {
            System.out.print(it.next() + " ");
        }
    }
}
```

Output:

```text
Aditya Pratik Rishabh Rakshit
```

---

# 21. Why create a Custom Iterator?

Because the class may have its own internal data structure.

For example:

```text
Custom Data Structure
        ↓
Custom Iterator
        ↓
Standard traversal mechanism
```

The user of the class doesn't need to know how the data is internally stored.

This follows the principle of **encapsulation**.

---

# 22. Anonymous Iterator

If the iterator implementation is used only once, we can conceptually use an anonymous class rather than creating a separate named class.

Example:

```java
@Override
public Iterator<String> iterator() {

    return new Iterator<String>() {

        private int pos = 0;

        @Override
        public boolean hasNext() {
            return pos < size;
        }

        @Override
        public String next() {
            return names[pos++];
        }
    };
}
```

Modern Java may also allow simpler implementations using lambdas in suitable functional-interface situations, but `Iterator` itself is not a functional interface because it has multiple abstract methods.

---

# 23. Enhanced For Loop / For-Each Loop ⭐⭐⭐

Java provides:

```java
for-each
```

to simplify iteration.

Example:

```java
for(String name : container) {
    System.out.println(name);
}
```

Instead of manually writing:

```java
Iterator<String> it = container.iterator();

while(it.hasNext()) {
    String name = it.next();
    System.out.println(name);
}
```

---

# 24. How does for-each loop work?

For an object implementing `Iterable`, conceptually:

```java
for(String name : container) {
    System.out.println(name);
}
```

is converted roughly into:

```java
Iterator<String> it = container.iterator();

while(it.hasNext()) {

    String name = it.next();

    System.out.println(name);
}
```

Therefore:

> The enhanced `for` loop uses the `Iterator` mechanism for objects that implement `Iterable`.

---

# 25. Does for-each work only with Iterable?

There are two important cases.

### Case 1 — Arrays

```java
int[] arr = {10, 20, 30};

for(int x : arr) {
    System.out.println(x);
}
```

Arrays don't implement `Iterable`, but enhanced for-loop has special compiler support for arrays.

### Case 2 — Iterable objects

```java
List<Integer> list = new ArrayList<>();

for(Integer x : list) {
    System.out.println(x);
}
```

Here iteration happens through the `Iterable`/`Iterator` mechanism.

---

# 26. Iterable Interface — Important Methods

In modern Java, `Iterable<T>` provides:

```java
Iterator<T> iterator()
```

and default methods such as:

```java
forEach()
spliterator()
```

Conceptually:

```java
interface Iterable<T> {

    Iterator<T> iterator();

    default void forEach(Consumer<? super T> action) {
        ...
    }

    default Spliterator<T> spliterator() {
        ...
    }
}
```

### Your note:

```text
Iterable
    ├── iterator()
    ├── forEach()
    └── spliterator()
```

is therefore correct conceptually.

---

# 27. Iterator Interface — Important Methods

Modern Java's `Iterator<T>` includes:

```java
boolean hasNext()
T next()
default void remove()
default void forEachRemaining(...)
```

The important methods are:

```text
hasNext()
next()
remove()
forEachRemaining()
```

---

# 28. Why does Iterator have remove() but not add()?

This is a very good interview question. ⭐⭐⭐

### Answer:

An `Iterator` is designed primarily for **traversing** a collection.

`remove()` is provided because removing the element that was just returned by `next()` can be safely coordinated with the iterator's internal state.

Adding an element is more complicated because it could affect:

- the iterator's traversal position
- ordering
- internal structure
- whether the new element should be visited again

Therefore, Java does not provide a general `add()` operation in `Iterator`.

If we need to add elements, we generally use the collection itself:

```java
list.add(100);
```

For lists, Java also provides:

```java
ListIterator
```

which supports:

```java
add()
remove()
set()
```

This is an important additional concept.

---

# 29. Iterator vs ListIterator ⭐

| Iterator | ListIterator |
|---|---|
| Works with collections generally | Works only with `List` |
| Forward traversal | Forward + backward |
| `hasNext()` | `hasNext()` |
| `next()` | `next()` |
| `remove()` | `remove()` |
| No `add()` | `add()` |
| No `previous()` | `previous()` |
| No `set()` | `set()` |

Example:

```java
ListIterator<Integer> it = list.listIterator();

while(it.hasNext()) {
    System.out.println(it.next());
}
```

Backward:

```java
while(it.hasPrevious()) {
    System.out.println(it.previous());
}
```

---

# 30. ConcurrentModificationException ⭐⭐⭐

Consider:

```java
List<Integer> list = new ArrayList<>();

list.add(1);
list.add(2);
list.add(3);

Iterator<Integer> it = list.iterator();

while(it.hasNext()) {

    int value = it.next();

    if(value == 3) {
        list.remove(Integer.valueOf(3));
    }

    System.out.println(value);
}
```

This can result in:

```text
ConcurrentModificationException
```

### Why?

Because we are modifying the collection directly while an iterator is actively traversing it.

```text
Iterator
   ↓
Collection
   ↓
Collection modified externally
   ↓
Iterator detects structural modification
   ↓
ConcurrentModificationException
```

---

# 31. Fail-Fast Iterator

Most standard collection iterators are **fail-fast**.

### Definition

> A fail-fast iterator attempts to detect structural modifications to the collection during iteration and throws `ConcurrentModificationException` rather than continuing with potentially inconsistent traversal.

For example:

```java
Iterator<Integer> it = list.iterator();

while(it.hasNext()) {

    int value = it.next();

    if(value == 3) {
        list.remove(Integer.valueOf(3));  // ❌
    }
}
```

---

# 32. Correct way to remove using Iterator

If we want to remove the current element during iteration, use:

```java
Iterator<Integer> it = list.iterator();

while(it.hasNext()) {

    int value = it.next();

    if(value == 3) {
        it.remove();     // ✅
    }
}
```

Why is this safe?

Because the iterator itself performs the removal and can update its internal state consistently.

---

# 33. Important: `remove()` does not mean any removal

This:

```java
list.remove(3);
```

and:

```java
it.remove();
```

are not equivalent.

For an `Iterator`:

```java
it.remove();
```

means:

> Remove the last element returned by this iterator's `next()` method.

It does **not** mean "remove element whose value is 3."

---

# 34. `remove()` and Integer Trap ⭐

Another important Java interview detail.

Suppose:

```java
List<Integer> list = new ArrayList<>();

list.add(10);
list.add(20);
list.add(30);
```

Then:

```java
list.remove(1);
```

means:

> Remove the element at **index 1**.

So the result is:

```text
10 30
```

If you want to remove the value `1`, use:

```java
list.remove(Integer.valueOf(1));
```

This happens because `List` has overloaded methods:

```java
remove(int index)
remove(Object object)
```

---

# 35. Fail-Fast ≠ Thread Safety

Very important ⭐

A fail-fast iterator does **not** mean that the collection is thread-safe.

Fail-fast behavior is primarily a mechanism for detecting unexpected structural modification during iteration.

It should not be relied upon as a synchronization mechanism.

---

# 36. Structural Modification

A structural modification generally means an operation that changes the structure/size of the collection.

Examples:

```java
add()
remove()
clear()
```

For many collections, these modifications can invalidate an existing iterator.

---

# 37. Quick Revision

```text
Collection Framework
        |
        ├── List
        │    ├── ArrayList
        │    ├── LinkedList
        │    ├── Vector
        │    └── Stack
        │
        ├── Set
        │    ├── HashSet
        │    ├── LinkedHashSet
        │    └── TreeSet
        │
        └── Queue
             ├── PriorityQueue
             └── Deque
                  ├── ArrayDeque
                  └── LinkedList


Map → Separate hierarchy
 |
 ├── HashMap
 ├── LinkedHashMap
 ├── TreeMap
 └── Hashtable
```

Traversal:

```text
Iterable
    |
 iterator()
    ↓
Iterator
    |
 ├── hasNext()
 ├── next()
 ├── remove()
 └── forEachRemaining()
```

Enhanced for:

```java
for(Element e : collection) {
    ...
}
```

Conceptually:

```java
Iterator it = collection.iterator();

while(it.hasNext()) {
    Element e = it.next();
}
```

---

# Important Interview Questions — Iterable & Iterator

### Q1. What is Iterable?

**Answer:**

> `Iterable` is an interface that represents an object whose elements can be traversed sequentially. It provides the `iterator()` method, which returns an `Iterator`.

---

### Q2. What is Iterator?

**Answer:**

> `Iterator` is an interface used to traverse the elements of a collection sequentially without exposing its internal implementation.

---

### Q3. Difference between Iterable and Iterator?

**Answer:**

> `Iterable` represents an object that can provide an iterator, whereas `Iterator` represents the actual mechanism used to traverse the elements.

```text
Iterable
   ↓
provides iterator()

Iterator
   ↓
performs traversal
```

---

### Q4. How does enhanced for-loop work internally?

**Answer:**

> For an object implementing `Iterable`, the enhanced for-loop internally uses an `Iterator` by calling `iterator()`, followed by repeated calls to `hasNext()` and `next()`.

---

### Q5. Can Map be directly used with the enhanced for-loop?

**Answer:**

> No. `Map` does not implement `Iterable`. However, its `keySet()`, `values()`, or `entrySet()` views can be iterated using the enhanced for-loop.

Example:

```java
for(Map.Entry<Integer, String> entry : map.entrySet()) {
    System.out.println(entry.getKey());
    System.out.println(entry.getValue());
}
```

---

### Q6. Why does Iterator have `remove()` but not `add()`?

**Answer:**

> `Iterator` is primarily designed for traversal. Its `remove()` method provides a controlled way to remove the last element returned by `next()`. Adding elements can interfere with the iterator's traversal state and is therefore not part of the general `Iterator` interface. `ListIterator` provides `add()` for lists.

---

### Q7. What is ConcurrentModificationException?

**Answer:**

> `ConcurrentModificationException` is a runtime exception that may occur when a collection is structurally modified while it is being traversed using a fail-fast iterator, except through the iterator's own permitted modification mechanism.

---

### Q8. How can you safely remove an element while iterating?

**Answer:**

Use the iterator's `remove()` method:

```java
Iterator<Integer> it = list.iterator();

while(it.hasNext()) {

    int value = it.next();

    if(value == 3) {
        it.remove();
    }
}
```

---

### Q9. Is LinkedList indexable?

**Answer:**

> Yes. `LinkedList` implements the `List` interface and therefore supports indexed operations such as `get(index)`. However, accessing an arbitrary index is generally slower than `ArrayList` because LinkedList may need to traverse nodes to reach that position.

---

### Q10. Is HashSet ordered?

**Answer:**

> No. `HashSet` does not guarantee insertion order or sorted order.

If insertion order is required:

```java
LinkedHashSet
```

If sorted order is required:

```java
TreeSet
```

---

### Q11. Is Iterator thread-safe?

**Answer:**

> No. An `Iterator` itself is not a general thread-safety mechanism. Standard collection iterators are often fail-fast, but fail-fast behavior should not be confused with thread safety.

---

### Q12. What is ListIterator?

**Answer:**

> `ListIterator` is a specialized iterator for `List` implementations that supports bidirectional traversal and additional operations such as `add()` and `set()`.

---

## ⭐ Most Important Things to Remember

```text
1. Collection ≠ Collection Framework

2. Map is part of Collection Framework
   but Map does NOT extend Collection.

3. List → ordered + duplicates allowed

4. Set → duplicates not allowed

5. Queue → designed for processing elements

6. Map → key-value pairs

7. Iterable → provides iterator()

8. Iterator → performs traversal

9. Iterator → hasNext(), next(), remove()

10. for-each works with arrays and Iterable objects

11. Map cannot directly be used in for-each

12. HashSet → no guaranteed order

13. LinkedHashSet → insertion order

14. TreeSet → sorted order

15. Iterator.remove() is the safe iterator-based
    removal mechanism.

16. Direct structural modification during iteration
    can cause ConcurrentModificationException.

17. Fail-fast ≠ thread-safe.

18. LinkedList supports get(index), but it is generally
    slower than ArrayList for random access.

19. ListIterator works only with List.

20. Collection Framework provides reusable,
    standardized data structures and algorithms.
```

This is the structure I’ll follow for the **rest of your Java notes**: **your original concept → corrected concept → proper definition → internal/conceptual working → examples → ⭐ interview traps → interview Q&A → quick revision**.