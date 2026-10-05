Absolutely. This part has several **very important interview concepts**, and there are a few corrections needed in your notes—especially **Java 21 `SequencedCollection`**, `ArrayList` growth, `LinkedList` complexity, and the return types of `List` methods.

I’ll keep your note style and expand it into proper future-revision notes.

```java id="48271"
//======================================================================
//                  LIST INTERFACE - COLLECTION FRAMEWORK PART-4
//======================================================================
```

# 1. What is List Interface?

### Definition ⭐

> `List` is an ordered collection interface that represents a sequence of elements in which **duplicate elements are allowed** and elements can be accessed using their **zero-based index**.

Example:

```java id="a1"
List<Integer> list = new ArrayList<>();

list.add(10);
list.add(20);
list.add(10);
```

Result:

```text id="b2"
Index:    0    1    2
          ↓    ↓    ↓
List:    10   20   10
```

Here:

- Order is maintained.
- Duplicates are allowed.
- Elements have positions.
- Index starts from `0`.
- Elements can be accessed using `get(index)`.

---

# 2. List Hierarchy

The simplified hierarchy is:

```text id="c3"
                         Collection
                              |
                      SequencedCollection
                              |
                           List
                        <<interface>>
                              |
             ----------------------------------
             |                |               |
         ArrayList        LinkedList        Vector
                                              |
                                             Stack
```

### Important ⭐

`SequencedCollection` was introduced in **Java 21**.

It provides a common abstraction for collections that have a defined encounter sequence and operations for working with the first and last elements.

---

# 3. Main List Implementations

The commonly encountered `List` implementations are:

```text id="d4"
List
 |
 ├── ArrayList
 |
 ├── LinkedList
 |
 ├── Vector
 |    |
 |   Stack
 |
 └── CopyOnWriteArrayList
```

`CopyOnWriteArrayList` is also an important `List` implementation, particularly for certain concurrent/read-heavy scenarios.

---

# 4. Properties of List ⭐⭐⭐

## 1. Elements have positions

Every element has an index.

```java id="e5"
List<String> names = new ArrayList<>();

names.add("Pratik");
names.add("Rahul");
names.add("Amit");
```

Conceptually:

```text id="f6"
Index       0         1         2
            ↓         ↓         ↓
          Pratik    Rahul      Amit
```

---

## 2. Duplicate elements are allowed

```java id="g7"
List<Integer> list = new ArrayList<>();

list.add(10);
list.add(20);
list.add(10);
```

Result:

```text id="h8"
[10, 20, 10]
```

---

## 3. Insertion order is preserved

If we insert:

```text id="i9"
30 → 10 → 20
```

a normal `List` maintains that encounter order:

```text id="j10"
[30, 10, 20]
```

---

## 4. Index-based access

```java id="k11"
list.get(1);
```

returns the element at index `1`.

---

# 5. List Indexing

Java uses **zero-based indexing**.

```text id="l12"
Element:    A    B    C    D
Index:      0    1    2    3
```

First element:

```java id="m13"
list.get(0);
```

Last element:

```java id="n14"
list.get(list.size() - 1);
```

---

# 6. List and `null`

Unlike `Set` implementations such as `TreeSet` in some configurations, many `List` implementations allow `null`.

For example:

```java id="o15"
List<Integer> list = new ArrayList<>();

list.add(null);
list.add(10);
list.add(null);
```

Result:

```text id="p16"
[null, 10, null]
```

So:

> `List` generally permits duplicate elements and may contain `null` elements depending on the implementation.

---

# 7. SequencedCollection — Java 21 ⭐⭐⭐

Java 21 introduced:

```text id="q17"
SequencedCollection
```

It represents collections with a defined **encounter order** and provides operations for accessing the beginning and end of that sequence.

Simplified hierarchy:

```text id="r18"
Collection
     |
SequencedCollection
     |
   List
```

Some important methods include:

```java id="s19"
getFirst()
getLast()

addFirst(E)
addLast(E)

removeFirst()
removeLast()

reversed()
```

---

# 8. `getFirst()`

Returns the first element.

```java id="t20"
List<Integer> list =
        new ArrayList<>(List.of(10, 20, 30));

System.out.println(list.getFirst());
```

Output:

```text id="u21"
10
```

---

# 9. `getLast()`

Returns the last element.

```java id="v22"
System.out.println(list.getLast());
```

Output:

```text id="w23"
30
```

This is cleaner than:

```java id="x24"
list.get(list.size() - 1);
```

---

# 10. `addFirst()` and `addLast()`

```java id="y25"
list.addFirst(5);
list.addLast(40);
```

If the original list was:

```text id="z26"
[10, 20, 30]
```

the result becomes:

```text id="aa27"
[5, 10, 20, 30, 40]
```

---

# 11. `removeFirst()` and `removeLast()`

```java id="ab28"
list.removeFirst();
list.removeLast();
```

Example:

```text id="ac29"
Before:
[10, 20, 30]

removeFirst()
    ↓
[20, 30]

removeLast()
    ↓
[20]
```

---

# 12. `reversed()` ⭐

Java 21 also introduced:

```java id="ad30"
list.reversed();
```

It provides a **reversed-order view** of the sequence.

Example:

```java id="ae31"
List<Integer> list =
        new ArrayList<>(List.of(10, 20, 30));

System.out.println(list.reversed());
```

Conceptually:

```text id="af32"
[30, 20, 10]
```

### Important

`reversed()` provides a view rather than simply creating an unrelated copied list.

---

# 13. List-Specific Methods

Apart from methods inherited from `Collection`, `List` provides index-based operations.

---

# 14. `get(int index)`

### Definition

> Returns the element present at the specified index.

```java id="ag33"
E get(int index);
```

Example:

```java id="ah34"
List<String> names =
        new ArrayList<>(List.of(
                "Pratik",
                "Rahul",
                "Amit"
        ));

System.out.println(names.get(1));
```

Output:

```text id="ai35"
Rahul
```

---

# 15. `set(int index, E element)`

### Definition

> Replaces the element at the specified index with the specified element.

```java id="aj36"
E set(int index, E element);
```

Example:

```java id="ak37"
list.set(1, 100);
```

Before:

```text id="al38"
[10, 20, 30]
```

After:

```text id="am39"
[10, 100, 30]
```

### Very important ⭐

`set()` **replaces** an existing element.

It does NOT increase the size of the list.

---

# 16. `add(int index, E element)`

### Definition

> Inserts an element at the specified index and shifts the existing elements to the right.

```java id="an40"
void add(int index, E element);
```

### Important correction ⭐

Your note says:

```text
boolean add(index, E value)
```

The return type is:

```java id="ao41"
void
```

Example:

```java id="ap42"
List<Integer> list =
        new ArrayList<>(List.of(10, 20, 30));

list.add(1, 99);
```

Before:

```text id="aq43"
[10, 20, 30]
```

After:

```text id="ar44"
[10, 99, 20, 30]
```

Notice:

```text id="as45"
size increases by 1
```

---

# 17. `set()` vs `add(index, element)` ⭐⭐⭐

This is a very common interview question.

```text id="at46"
set()
 ↓
REPLACE

add(index, element)
 ↓
INSERT
```

Example:

```java id="au47"
List<Integer> list =
        new ArrayList<>(List.of(10, 20, 30));

list.set(1, 99);
```

Result:

```text id="av48"
[10, 99, 30]
```

Size remains:

```text id="aw49"
3
```

But:

```java id="ax50"
list.add(1, 99);
```

Result:

```text id="ay51"
[10, 99, 20, 30]
```

Size becomes:

```text id="az52"
4
```

---

# 18. `addAll(int index, Collection<? extends E>)`

### Definition

> Inserts all elements from the specified collection at the given index.

```java id="ba53"
boolean addAll(
    int index,
    Collection<? extends E> c
);
```

Example:

```java id="bb54"
List<Integer> list =
        new ArrayList<>(List.of(10, 40));

list.addAll(1, List.of(20, 30));
```

Result:

```text id="bc55"
[10, 20, 30, 40]
```

---

# 19. `remove(int index)`

### Definition

> Removes and returns the element at the specified index.

```java id="bd56"
E remove(int index);
```

### Important correction ⭐⭐⭐

Your note says:

```text
boolean remove(index)
```

The actual return type is:

```text id="be57"
E
```

Example:

```java id="bf58"
List<Integer> list =
        new ArrayList<>(List.of(10, 20, 30));

int removed = list.remove(1);

System.out.println(removed);
```

Output:

```text id="bg59"
20
```

List becomes:

```text id="bh60"
[10, 30]
```

---

# 20. `remove(Object)` vs `remove(index)` ⭐⭐⭐

This is a famous Java interview trap.

`List` has:

```java id="bi61"
remove(int index)
remove(Object o)
```

Suppose:

```java id="bj62"
List<Integer> list =
        new ArrayList<>(List.of(10, 20, 30));
```

Then:

```java id="bk63"
list.remove(1);
```

means:

> Remove element at index `1`.

Result:

```text id="bl64"
[10, 30]
```

But:

```java id="bm65"
list.remove(Integer.valueOf(10));
```

means:

> Remove the element whose value is `10`.

---

# 21. `indexOf(Object o)`

### Definition

> Returns the index of the **first occurrence** of the specified element, or `-1` if the element is not present.

Example:

```java id="bn66"
List<Integer> list =
        new ArrayList<>(List.of(10, 20, 10, 30));

System.out.println(list.indexOf(10));
```

Output:

```text id="bo67"
0
```

If:

```java id="bp68"
list.indexOf(99);
```

output:

```text id="bq69"
-1
```

---

# 22. `lastIndexOf(Object o)`

### Definition

> Returns the index of the **last occurrence** of the specified element, or `-1` if it is not present.

```java id="br70"
System.out.println(list.lastIndexOf(10));
```

Output:

```text id="bs71"
2
```

---

# 23. `indexOf()` vs `lastIndexOf()`

```text id="bt72"
List:
[10, 20, 10, 30, 10]
  ↑       ↑       ↑
  0       2       4
```

```java id="bu73"
indexOf(10)
    ↓
0
```

```java id="bv74"
lastIndexOf(10)
    ↓
4
```

---

# 24. `ListIterator` ⭐⭐⭐

`ListIterator` is a specialized iterator designed specifically for `List`.

It supports:

```text id="bw75"
Forward traversal
Backward traversal
Add
Remove
Replace
```

Important methods:

```java id="bx76"
hasNext()
next()

hasPrevious()
previous()

nextIndex()
previousIndex()

add(E)
remove()
set(E)
```

---

# 25. `listIterator()`

```java id="by77"
ListIterator<Integer> it =
        list.listIterator();
```

The iterator initially starts **before the first element**.

Conceptually:

```text id="bz78"
       cursor
         ↓
[10] [20] [30]
```

Then:

```java id="ca79"
it.next();
```

moves forward.

---

# 26. `listIterator(int index)`

We can specify where the iterator starts.

```java id="cb80"
ListIterator<Integer> it =
        list.listIterator(2);
```

Conceptually:

```text id="cc81"
[10] [20] | [30] [40]
            ↑
          cursor
```

The next call to:

```java id="cd82"
it.next();
```

returns the element at index `2`.

---

# 27. Forward Traversal

```java id="ce83"
ListIterator<Integer> it =
        list.listIterator();

while(it.hasNext()) {
    System.out.println(it.next());
}
```

---

# 28. Backward Traversal

```java id="cf84"
while(it.hasPrevious()) {
    System.out.println(it.previous());
}
```

This is one major difference from `Iterator`.

---

# 29. `ListIterator.add()`

Example:

```java id="cg85"
ListIterator<Integer> it =
        list.listIterator();

it.next();
it.add(99);
```

The new element is inserted at the iterator's current position.

---

# 30. `ListIterator.set()`

`set()` replaces the last element returned by `next()` or `previous()`.

Example:

```java id="ch86"
ListIterator<Integer> it =
        list.listIterator();

int value = it.next();

it.set(100);
```

The element returned by `next()` is replaced with `100`.

---

# 31. `List.of()` ⭐⭐⭐

Java provides factory methods for creating **unmodifiable lists**.

```java id="ci87"
List<Integer> list =
        List.of(10, 20, 30);
```

Trying:

```java id="cj88"
list.add(40);
```

results in:

```text id="ck89"
UnsupportedOperationException
```

### Important correction ⭐

Don't say:

```text
l.add(8); // compile-time error
```

It is **not a compilation error**.

The code compiles, but attempting a modifying operation at runtime throws:

```text id="cl90"
UnsupportedOperationException
```

---

# 32. Properties of `List.of()`

```java id="cm91"
List<Integer> list =
        List.of(10, 20, 30);
```

The resulting list is:

- Unmodifiable
- Does not allow `null`
- Maintains encounter order
- Allows duplicate values

For example:

```java id="cn92"
List<Integer> list =
        List.of(10, 10, 20);
```

is valid.

But:

```java id="co93"
List<Integer> list =
        List.of(10, null, 20);
```

throws:

```text id="cp94"
NullPointerException
```

---

# 33. `List.copyOf()` ⭐⭐⭐

Used to create an **unmodifiable list containing the elements of another collection**.

```java id="cq95"
List<Integer> original =
        new ArrayList<>(List.of(10, 20, 30));

List<Integer> copy =
        List.copyOf(original);
```

Now:

```java id="cr96"
copy.add(40);
```

throws:

```text id="cs97"
UnsupportedOperationException
```

### Important distinction

`List.copyOf()` creates an unmodifiable list.

It does not create a normal mutable `ArrayList`.

---

# 34. `List.of()` vs `List.copyOf()`

| `List.of()` | `List.copyOf()` |
|---|---|
| Creates list from given elements | Creates list from an existing collection |
| Unmodifiable | Unmodifiable |
| Does not allow `null` | Does not allow `null` |
| Duplicates allowed | Duplicates allowed |
| Maintains order | Maintains order |

---

# 35. ArrayList ⭐⭐⭐

### Definition

> `ArrayList` is a resizable-array implementation of the `List` interface.

Conceptually:

```text id="ct98"
ArrayList
    ↓
Dynamic Array
```

Example:

```java id="cu99"
ArrayList<Integer> list =
        new ArrayList<>();
```

Unlike a normal Java array:

```java id="cv100"
int[] arr = new int[5];
```

an `ArrayList` can dynamically grow as elements are added.

---

# 36. ArrayList Internal Structure

Conceptually:

```text id="cw101"
ArrayList

[10][20][30][40][ ][ ][ ]
```

It maintains an internal array and tracks the number of actual elements.

```text id="cx102"
size     → number of elements currently stored
capacity → space available in internal array
```

### ⭐ Important

`size` and `capacity` are different.

Example:

```text id="cy103"
capacity = 10
size     = 4
```

means:

> The internal array can currently hold up to 10 elements, but only 4 positions contain actual list elements.

---

# 37. Does ArrayList always grow by 1.5×?

Your note says:

```text id="cz104"
new capacity = old capacity + old capacity / 2
```

This is a common description of **OpenJDK's growth behavior**, but don't write it as a Java API guarantee.

### Interview-safe statement ⭐

> `ArrayList` automatically grows its internal array when necessary. The exact growth strategy is implementation-dependent and should not be relied upon as part of the Java API contract.

In common OpenJDK implementations, growth has historically been approximately **1.5×**.

This distinction is important in interviews.

---

# 38. ArrayList Constructors

## 1. Default constructor

```java id="da105"
ArrayList<Integer> list =
        new ArrayList<>();
```

Creates an empty `ArrayList`.

---

## 2. Initial capacity

```java id="db106"
ArrayList<Integer> list =
        new ArrayList<>(15);
```

This specifies an **initial capacity**.

### Important ⭐

It does **not** mean:

```text
size = 15
```

Initially:

```text
size = 0
capacity ≈ 15
```

Conceptually.

---

## 3. Collection constructor

```java id="dc107"
List<Integer> list =
        new ArrayList<>(otherCollection);
```

Creates an `ArrayList` containing the elements from another collection.

---

# 39. ArrayList Time Complexity

For typical `ArrayList` usage:

| Operation | Complexity |
|---|---:|
| `get(index)` | O(1) |
| `set(index, value)` | O(1) |
| `add(element)` | O(1) amortized |
| `add(index, element)` | O(n) |
| `remove(index)` | O(n) |
| `contains()` | O(n) |
| `indexOf()` | O(n) |

### Why is `add()` amortized O(1)?

Most additions simply place the element at the next available position.

Occasionally, the internal array must grow and elements must be copied.

Therefore:

```text id="dd108"
Most operations → O(1)
Occasional resize → O(n)

Overall amortized → O(1)
```

---

# 40. Why is ArrayList `get()` O(1)?

Because elements are stored in an array-like contiguous structure.

If we want:

```java id="de109"
list.get(5);
```

the underlying array can directly calculate the location:

```text id="df110"
base address + index × element-size
```

Conceptually, this gives constant-time random access.

---

# 41. Why is ArrayList Cache-Friendly?

Array elements are stored in a contiguous array-like structure.

```text id="dg111"
[10][20][30][40][50][60]
```

When the CPU accesses nearby memory locations, CPU cache mechanisms can make sequential access efficient.

Therefore:

> `ArrayList` generally has good cache locality compared with node-based linked structures.

This is one reason it often performs better than `LinkedList` in real-world iteration workloads.

---

# 42. `add(index, element)` in ArrayList

Suppose:

```text id="dh112"
[10, 20, 30, 40]
```

Execute:

```java id="di113"
list.add(1, 99);
```

Elements must be shifted:

```text id="dj114"
Before:
[10, 20, 30, 40]

Shift:
     20 → 30
     30 → 40

After:
[10, 99, 20, 30, 40]
```

Therefore:

```text id="dk115"
add(index, element) → O(n)
```

---

# 43. `remove(index)` in ArrayList

Suppose:

```text id="dl116"
[10, 20, 30, 40]
```

Remove index `1`:

```java id="dm117"
list.remove(1);
```

Elements after the removed element must shift left:

```text id="dn118"
[10, 30, 40]
```

Therefore:

```text id="do119"
remove(index) → O(n)
```

---

# 44. ArrayList Specific Methods

ArrayList provides methods such as:

```java id="dp120"
ensureCapacity()
trimToSize()
```

---

# 45. `ensureCapacity()`

### Definition

> `ensureCapacity()` increases the internal capacity of an `ArrayList` if necessary so that it can hold at least the specified number of elements without requiring an immediate resize.

Example:

```java id="dq121"
ArrayList<Integer> list =
        new ArrayList<>();

list.ensureCapacity(1000);
```

This can be useful when we know approximately how many elements will be added.

### Important

It changes **capacity**, not the current `size`.

```text id="dr122"
ensureCapacity(1000)

size     → still 0
capacity → enough for at least 1000 elements
```

---

# 46. `trimToSize()`

### Definition

> `trimToSize()` reduces the internal capacity of an `ArrayList` to approximately its current size.

Example:

```java id="ds123"
ArrayList<Integer> list =
        new ArrayList<>(100);

list.add(10);
list.add(20);

list.trimToSize();
```

Conceptually:

```text id="dt124"
Before:
size = 2
capacity = 100

After trim:
size = 2
capacity ≈ 2
```

It can reduce unused capacity, but should not be called unnecessarily.

---

# 47. LinkedList ⭐⭐⭐

### Definition

> `LinkedList` is a doubly-linked list implementation of both `List` and `Deque`.

This is important:

```text id="du125"
LinkedList
    |
    ├── List
    |
    └── Deque
```

Therefore, `LinkedList` can be used as:

- List
- Queue
- Deque

---

# 48. LinkedList Internal Structure

Conceptually:

```text id="dv126"
null ← [10] ⇄ [20] ⇄ [30] ⇄ [40] → null
          ↑
       previous
       next
```

Each node generally stores:

```text id="dw127"
previous reference
data
next reference
```

Conceptually:

```java id="dx128"
class Node<E> {

    E item;

    Node<E> next;

    Node<E> previous;
}
```

---

# 49. LinkedList Time Complexity

This is where your original notes need an important correction.

You wrote:

```text
get(index) → O(n)
add()      → O(n)
remove()   → O(n)
addAll()   → O(n)
```

This is too broad.

### More accurate view:

| Operation | Typical Complexity |
|---|---:|
| `get(index)` | O(n) |
| `set(index)` | O(n) |
| `add(element)` at end | O(1) |
| `addFirst()` | O(1) |
| `addLast()` | O(1) |
| `removeFirst()` | O(1) |
| `removeLast()` | O(1) |
| `add(index, element)` | O(n) |
| `remove(index)` | O(n) |
| `contains()` | O(n) |

Why?

Because `LinkedList` maintains references to its ends and can directly manipulate nodes at the beginning/end.

---

# 50. Why is `LinkedList.get(index)` O(n)?

Suppose:

```text id="dy129"
[10] → [20] → [30] → [40] → [50]
```

To get:

```java id="dz130"
list.get(4);
```

the linked list cannot directly jump to index `4`.

It must traverse nodes.

```text id="ea131"
10 → 20 → 30 → 40 → 50
                  ↑
               search
```

Therefore:

```text id="eb132"
get(index) → O(n)
```

Java's `LinkedList` can traverse from the nearer end, so the traversal is optimized based on the index, but asymptotically it remains O(n).

---

# 51. Is LinkedList always slower than ArrayList?

Don't memorize:

> "LinkedList is slower than ArrayList."

That is too general.

Better:

> `LinkedList` is generally inefficient for random indexed access and often has higher memory/cache overhead than `ArrayList`, but it can provide efficient insertion/removal at the ends and can be useful when working with deque-like operations.

In modern Java applications:

```text
ArrayList
    ↓
usually preferred for general-purpose List usage
```

while:

```text
ArrayDeque
    ↓
often preferred for queue/deque operations
```

rather than using `LinkedList` as a queue.

---

# 52. Vector ⭐

### Definition

> `Vector` is a legacy, synchronized, dynamically growing array implementation of `List`.

Conceptually:

```text id="ec133"
Vector
  ↓
Dynamic Array
```

It is similar to `ArrayList`, but many of its methods are synchronized.

---

# 53. ArrayList vs Vector

| ArrayList | Vector |
|---|---|
| Modern general-purpose List | Legacy class |
| Not synchronized by default | Synchronized methods |
| Generally better performance in single-threaded code | Synchronization can add overhead |
| Preferred for normal use | Usually avoided in new code |

### Important ⭐

Don't say:

> "Vector is thread-safe, therefore always use Vector for multithreading."

Modern concurrent programming usually uses more appropriate concurrency utilities depending on the requirement.

---

# 54. Stack ⭐⭐⭐

`Stack` is a legacy class:

```text id="ed134"
Stack
  ↓
extends Vector
```

It represents a **LIFO** data structure.

```text id="ee135"
Last In
   ↓
First Out
```

Example:

```text id="ef136"
push(10)
push(20)
push(30)

        ↓

       30 ← pop()
       20
       10
```

---

# 55. Should we use Stack?

For new Java code, generally prefer:

```java id="eg137"
Deque<Integer> stack = new ArrayDeque<>();
```

Then:

```java id="eh138"
stack.push(10);
stack.push(20);
stack.push(30);

System.out.println(stack.pop());
```

Output:

```text id="ei139"
30
```

### Interview answer ⭐

> `Stack` is a legacy class that extends `Vector`. For stack operations in modern Java, `Deque` implementations such as `ArrayDeque` are generally preferred.

---

# 56. List Implementation Comparison ⭐⭐⭐

| Feature | ArrayList | LinkedList | Vector | Stack |
|---|---|---|---|---|
| Internal structure | Dynamic array | Doubly linked list | Dynamic array | Vector-based |
| Random access | Fast | Slow | Fast | Fast |
| `get(index)` | O(1) | O(n) | O(1) | O(1) |
| End insertion | Amortized O(1) | O(1) | Amortized O(1) | Amortized O(1) |
| Thread synchronization | No | No | Yes | Yes |
| Legacy | No | No | Yes | Yes |
| Recommended generally | ⭐⭐⭐ | Situational | Usually no | Usually no |
| Best use | General List | Node/deque operations | Legacy code | Legacy stack |

---

# 57. ArrayList vs LinkedList ⭐⭐⭐⭐⭐

This is one of the most important interview questions.

### ArrayList

```text id="ej140"
Dynamic Array
      ↓
Fast random access
      ↓
O(1) get()
```

### LinkedList

```text id="ek141"
Doubly Linked Nodes
      ↓
No direct random access
      ↓
O(n) get()
```

### Practical rule

```text id="el142"
Need normal List?
       ↓
    ArrayList

Need efficient deque/end operations?
       ↓
    ArrayDeque

Need LinkedList specifically?
       ↓
    Only when its linked-node/deque behavior
    actually fits the use case.
```

---

# 58. List Method Cheat Sheet

```text id="em143"
List<E>
│
├── get(index)
│      → get element
│
├── set(index, value)
│      → replace element
│
├── add(index, value)
│      → insert element
│
├── addAll(index, collection)
│      → insert multiple elements
│
├── remove(index)
│      → remove + return element
│
├── indexOf(value)
│      → first occurrence
│
├── lastIndexOf(value)
│      → last occurrence
│
├── listIterator()
│      → ListIterator
│
├── listIterator(index)
│      → iterator from index
│
├── List.of(...)
│      → unmodifiable list
│
└── List.copyOf(...)
       → unmodifiable copy
```

---

# 59. `set()` vs `add()` vs `remove()` — Must Remember ⭐⭐⭐⭐⭐

```text id="en144"
set(index, value)
       ↓
Replace existing element
       ↓
size unchanged


add(index, value)
       ↓
Insert new element
       ↓
size increases


remove(index)
       ↓
Remove element
       ↓
size decreases
       ↓
returns removed element
```

Example:

```java id="eo145"
List<Integer> list =
        new ArrayList<>(List.of(10, 20, 30));

list.set(1, 99);
// [10, 99, 30]

list.add(1, 50);
// [10, 50, 99, 30]

int x = list.remove(1);
// x = 50
// [10, 99, 30]
```

---

# ⭐ Important Interview Questions — List

### Q1. What is List in Java?

**Answer:**

> `List` is an ordered collection interface that allows duplicate elements and provides index-based access to its elements.

---

### Q2. What are the main implementations of List?

**Answer:**

> The commonly used implementations are `ArrayList`, `LinkedList`, and the legacy `Vector` and `Stack`. `CopyOnWriteArrayList` is another implementation designed for specific concurrent use cases.

---

### Q3. What are the properties of List?

**Answer:**

> A List maintains encounter order, allows duplicate elements, supports index-based access, and generally permits `null` elements depending on the implementation.

---

### Q4. What is SequencedCollection?

**Answer:**

> `SequencedCollection` is an interface introduced in Java 21 that represents collections with a defined encounter order and provides common operations for accessing, adding, removing, and reversing elements at the beginning and end of the sequence.

---

### Q5. What is the difference between `set()` and `add(index, element)`?

**Answer:**

> `set()` replaces an existing element at the specified index without changing the list size, whereas `add(index, element)` inserts a new element at that index and shifts subsequent elements to the right.

---

### Q6. What does `remove(int index)` return?

**Answer:**

> `remove(int index)` removes the element at the specified index and returns the removed element.

---

### Q7. What is the difference between `remove(1)` and `remove(Integer.valueOf(1))` for a `List<Integer>`?

**Answer:**

> `remove(1)` invokes `remove(int index)` and removes the element at index `1`. `remove(Integer.valueOf(1))` invokes `remove(Object)` and removes the element whose value is `1`.

⭐⭐ Very common interview trap.

---

### Q8. Why is ArrayList's `get()` O(1)?

**Answer:**

> `ArrayList` uses an array internally, so an element can be accessed directly using its index without traversing previous elements.

---

### Q9. Why is `ArrayList.add(index, element)` O(n)?

**Answer:**

> Because inserting an element at an arbitrary index may require shifting subsequent elements to make room for the new element.

---

### Q10. Why is `ArrayList.add(element)` amortized O(1)?

**Answer:**

> Most additions place the element directly at the end. Occasionally, the internal array must be resized and its elements copied, which is expensive. When averaged over many additions, the amortized complexity is O(1).

---

### Q11. Does ArrayList always grow by 1.5 times?

**Answer:**

> No. The Java API does not guarantee a particular growth factor. Some OpenJDK implementations use approximately 1.5× growth, but this is an implementation detail and should not be relied upon.

⭐⭐ Excellent interview answer.

---

### Q12. What is the difference between ArrayList size and capacity?

**Answer:**

> `size` is the number of elements currently stored in the list, whereas `capacity` is the amount of space currently allocated in the internal array.

Example:

```text id="ep146"
size = 5
capacity = 10
```

means five elements are currently stored, while space for approximately ten elements is available internally.

---

### Q13. What does `new ArrayList<>(100)` mean?

**Answer:**

> It specifies an initial capacity of approximately 100; it does not create a list containing 100 elements. The initial size is still zero.

---

### Q14. Why is LinkedList `get()` O(n)?

**Answer:**

> A linked list does not provide direct random access. To reach an arbitrary index, it must traverse nodes from one end, so indexed access takes O(n) time.

---

### Q15. Is insertion in LinkedList always O(1)?

**Answer:**

> No. Insertion at the beginning or end can be O(1), but insertion at an arbitrary index is generally O(n) because locating the required position can require traversal.

---

### Q16. ArrayList vs LinkedList — which is better?

**Answer:**

> Neither is universally better. `ArrayList` is generally preferred for general-purpose lists because it provides O(1) indexed access and good cache locality. `LinkedList` can be useful when frequent insertion/removal at the ends or deque-style operations are required, although `ArrayDeque` is often preferred for pure deque use cases.

---

### Q17. What is Vector?

**Answer:**

> `Vector` is a legacy synchronized dynamic-array implementation of `List`. It is generally not preferred for new code when a normal `ArrayList` or an appropriate concurrent collection can be used.

---

### Q18. What is Stack?

**Answer:**

> `Stack` is a legacy LIFO collection class that extends `Vector`. For modern Java code, `Deque` with an implementation such as `ArrayDeque` is generally preferred for stack operations.

---

### Q19. Is List thread-safe?

**Answer:**

> The `List` interface itself does not guarantee thread safety. Thread safety depends on the implementation or the way the list is wrapped/used.

For example:

```text id="eq147"
ArrayList
    → not synchronized

Vector
    → synchronized legacy implementation

CopyOnWriteArrayList
    → specialized concurrent implementation
```

---

### Q20. What is `List.of()`?

**Answer:**

> `List.of()` is a Java factory method used to create an unmodifiable list. It does not permit `null` elements, although duplicate non-null elements are allowed.

---

### Q21. What happens if we call `add()` on a list created using `List.of()`?

**Answer:**

> The code compiles, but the operation throws `UnsupportedOperationException` at runtime because the list is unmodifiable.

---

### Q22. Difference between `List.of()` and `List.copyOf()`?

**Answer:**

> `List.of()` creates an unmodifiable list from explicitly supplied elements, whereas `List.copyOf()` creates an unmodifiable list containing the elements of an existing collection.

---

# 🔥 Final List Revision Sheet

```text id="er148"
                         Collection
                              |
                     SequencedCollection
                              |
                            List
                              |
          ----------------------------------------
          |                 |                    |
      ArrayList         LinkedList             Vector
                                                |
                                               Stack


LIST PROPERTIES
────────────────────────────────────

✓ Ordered
✓ Duplicate allowed
✓ Index-based access
✓ Usually supports null
✓ Zero-based indexing


ARRAYLIST
────────────────────────────────────

Internal → Dynamic Array

get()             → O(1)
set()             → O(1)
add(end)          → O(1) amortized
add(index)        → O(n)
remove(index)     → O(n)
contains()        → O(n)

Advantages:
✓ Fast random access
✓ Cache friendly
✓ General-purpose List

Methods:
ensureCapacity()
trimToSize()


LINKEDLIST
────────────────────────────────────

Internal → Doubly Linked List

get(index)        → O(n)
set(index)        → O(n)
addFirst()        → O(1)
addLast()         → O(1)
removeFirst()     → O(1)
removeLast()      → O(1)
add(index)        → O(n)
remove(index)     → O(n)

Also implements:
✓ List
✓ Deque


VECTOR
────────────────────────────────────

✓ Dynamic array
✓ Synchronized
✓ Legacy
✓ Usually not preferred for new code


STACK
────────────────────────────────────

Stack extends Vector

✓ LIFO
✓ Legacy

Modern alternative:

Deque<Integer> stack = new ArrayDeque<>();


IMPORTANT LIST METHODS
────────────────────────────────────

get(index)
    → read

set(index, value)
    → replace

add(index, value)
    → insert

remove(index)
    → remove + return

indexOf()
    → first occurrence

lastIndexOf()
    → last occurrence

listIterator()
    → bidirectional iterator

List.of()
    → unmodifiable list

List.copyOf()
    → unmodifiable copy
```

## ⭐ The interview traps from this chapter

```text id="es149"
1. List is ordered and allows duplicates.

2. ArrayList is generally preferred for
   general-purpose List usage.

3. ArrayList get(index) → O(1).

4. LinkedList get(index) → O(n).

5. ArrayList add(end) → O(1) amortized.

6. ArrayList add(index) → O(n).

7. LinkedList addFirst/addLast → O(1).

8. List.add(index, element) returns void.

9. List.remove(index) returns the removed element.

10. remove(1) on List<Integer>
    → removes index 1.

11. remove(Integer.valueOf(1))
    → removes value 1.

12. set() replaces; add() inserts.

13. ArrayList's exact growth factor is not
    guaranteed by the Java specification.

14. new ArrayList<>(100)
    → initial capacity, NOT size 100.

15. List.of() creates an unmodifiable list
    and rejects null.

16. List.copyOf() creates an unmodifiable copy.

17. Stack is legacy; prefer Deque/ArrayDeque.

18. LinkedList is also a Deque.

19. SequencedCollection was introduced in Java 21.

20. SequencedCollection provides operations such as
    getFirst(), getLast(), addFirst(), addLast(),
    removeFirst(), removeLast(), and reversed().
```