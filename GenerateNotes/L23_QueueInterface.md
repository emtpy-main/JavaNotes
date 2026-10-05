Absolutely — this part needs a few **important corrections**, especially around `ArrayDeque`, `LinkedList`, `Stack`, and `PriorityQueue`. I’ll preserve your note-taking style while filling the missing concepts and adding interview points.

# =========== Queue Interface — Collection Notes Part-7 ===============

## ⭐⭐⭐ Queue / Deque Hierarchy

```text
                         [ Iterable <<I>> ]
                                |
                         [ Collection <<I>> ]
                          /               \
                         /                 \
                [ List <<I>> ]       [ Queue <<I>> ]
                     |                     |
                     |                     |
               [ LinkedList ]       [ Deque <<I>> ]
                     |                  /       \
                     |                 /         \
                     |        [ArrayDeque]   [LinkedList]
                     |
                     |
              Can work as
              List + Queue + Deque
```

### ⭐ Important

```text
Queue  → FIFO  → First In First Out
Stack  → LIFO  → Last In First Out
Deque  → Double Ended Queue
```

`Deque` is pronounced **"deck"**.

---

# ⭕ ArrayDeque

### Definition

`ArrayDeque` is a **concrete class** that implements the `Deque` interface.

```java
ArrayDeque<Integer> deque = new ArrayDeque<>();
```

Because `Deque` extends `Queue`, an `ArrayDeque` can behave as:

```text
        ArrayDeque
             |
       ----------------
       |      |       |
     Queue   Stack   Deque
```

### ⭐ It uses an array-based data structure internally

Conceptually:

```text
              Array
        +----+----+----+----+----+
        |    | 10 | 20 | 30 |    |
        +----+----+----+----+----+
               ↑         ↑
             Front      Rear
```

⚠️ **Important correction:**  
It is better to say that `ArrayDeque` is an **array-backed deque**, rather than saying it is simply "an array implementation of Queue."

It is designed to efficiently insert/remove from **both ends**.

---

# ⭐⭐ How does Java conceptually implement Deque using an Array?

Suppose:

```java
Deque<Integer> deque = new ArrayDeque<>();

deque.addLast(20);
deque.addLast(30);
deque.addFirst(10);
```

Conceptually:

```text
        Front                         Rear
          ↓                            ↓
       +----+----+----+----+
       | 10 | 20 | 30 |    |
       +----+----+----+----+
```

Operations:

```text
addFirst(5)

       Front
         ↓
      +----+----+----+----+
      |  5 | 10 | 20 | 30 |
      +----+----+----+----+

addLast(40)

      +----+----+----+----+----+
      |  5 | 10 | 20 | 30 | 40 |
      +----+----+----+----+----+
       ↑                       ↑
     Front                   Rear
```

### ⭐ Important internal idea

`ArrayDeque` does **not** need to physically shift all elements every time you add/remove from the front.

It conceptually maintains positions for the **front and rear** and uses a **circular-array technique**.

```text
        +----+----+----+----+----+----+
        | 10 | 20 | 30 |    |    |  5 |
        +----+----+----+----+----+----+
          ↑                   ↑
        Front               Rear
```

The implementation can wrap around the end of the array.

---

# ⭐⭐ How does Java conceptually implement Deque using LinkedList?

`LinkedList` implements both:

```java
List<E>
Deque<E>
```

Conceptually:

```text
      Front                              Rear
        ↓                                  ↓
     +----+      +----+      +----+
     | 10 | <--> | 20 | <--> | 30 |
     +----+      +----+      +----+
```

Each node conceptually contains:

```text
+---------+
| previous|
| element |
| next    |
+---------+
```

So:

```java
deque.addFirst(5);
deque.addLast(40);
```

becomes conceptually:

```text
       Front                                      Rear
         ↓                                          ↓
      +----+      +----+      +----+      +----+
      |  5 | <--> | 10 | <--> | 20 | <--> | 40 |
      +----+      +----+      +----+      +----+
```

### ⭐ Complexity

| Operation | ArrayDeque | LinkedList |
|---|---:|---:|
| addFirst | O(1) amortized | O(1) |
| addLast | O(1) amortized | O(1) |
| removeFirst | O(1) | O(1) |
| removeLast | O(1) | O(1) |
| get by index | Not supported | O(n) |
| Memory overhead | Lower | Higher |

---

# ⭐⭐ Can ArrayDeque contain `null`?

❌ **No.**

```java
ArrayDeque<Integer> deque = new ArrayDeque<>();

deque.add(null);       // NullPointerException
```

This is an important interview point.

### Why?

Because `null` is used as a meaningful return value by methods such as:

```java
peek()
poll()
```

For example:

```java
deque.poll();
```

returns `null` when the deque is empty.

If `null` were allowed as an element, Java could not distinguish:

```text
null because deque is empty
        VS
null because null was stored
```

Therefore:

```text
ArrayDeque → does NOT allow null
LinkedList → allows null
```

---

# ⭐⭐ Why is `Stack` class usually not preferred? Why use `ArrayDeque` as Stack?

Java has an old class:

```java
Stack<Integer> stack = new Stack<>();
```

But modern Java code generally prefers:

```java
Deque<Integer> stack = new ArrayDeque<>();
```

### Reasons

#### 1️⃣ `Stack` is a legacy class

`Stack` extends `Vector`.

```text
Stack
  |
Vector
  |
Collection
```

`Vector` uses synchronization inherited from its legacy design.

For a normal single-threaded stack, this adds unnecessary overhead.

---

#### 2️⃣ `Deque` provides a cleaner abstraction

```java
Deque<Integer> stack = new ArrayDeque<>();

stack.push(10);
stack.push(20);
stack.push(30);

System.out.println(stack.pop());   // 30
```

This naturally provides:

```text
push() → insert at front
pop()  → remove from front
peek() → inspect front
```

---

#### 3️⃣ `ArrayDeque` is generally faster for stack operations

For normal stack usage:

```java
Deque<Integer> stack = new ArrayDeque<>();
```

is the preferred modern approach.

### ⭐ Interview answer

> `Stack` is a legacy class that extends the synchronized `Vector` class. For stack behavior, `Deque` with `ArrayDeque` is generally preferred because it provides a cleaner abstraction and avoids the unnecessary synchronization overhead of `Stack`.

---

# ⭕ Queue

A `Queue` generally follows:

```text
FIFO
↓
First In First Out
```

Example:

```text
Insert → 10 20 30

Front                         Rear
  ↓                             ↓
+----+----+----+
| 10 | 20 | 30 |
+----+----+----+

remove()
   ↓

+----+----+
| 20 | 30 |
+----+----+
```

---

# ⭐ Queue Declaration

Prefer programming to the interface:

```java
Queue<Integer> queue = new ArrayDeque<>();
```

rather than:

```java
ArrayDeque<Integer> queue = new ArrayDeque<>();
```

when you only need Queue behavior.

---

# ⭐⭐⭐ Queue Method Pairs

Queue methods are designed in pairs:

| Operation | Throws exception | Special value |
|---|---|---|
| Insert | `add(e)` | `offer(e)` |
| Remove | `remove()` | `poll()` |
| Examine | `element()` | `peek()` |

### Easy memory trick

```text
ADD     ↔ OFFER
REMOVE  ↔ POLL
ELEMENT ↔ PEEK
```

---

## 1️⃣ `add(E e)`

Adds an element.

```java
queue.add(5);
```

If insertion cannot be performed, it may throw an exception.

---

## 2️⃣ `offer(E e)`

Attempts to insert an element.

```java
queue.offer(25);
```

Returns:

```text
true  → successfully inserted
false → insertion failed
```

### ⭐ Important

With an unbounded `ArrayDeque`, normal insertion won't fail because of a full queue under ordinary conditions. The distinction between `add` and `offer` is especially relevant for **bounded queues**.

---

# 3️⃣ `peek()`

Returns the front element **without removing it**.

```java
System.out.println(queue.peek());
```

If empty:

```text
null
```

---

# 4️⃣ `element()`

Returns the front element without removing it.

```java
queue.element();
```

If empty:

```text
NoSuchElementException
```

---

# 5️⃣ `remove()`

Removes and returns the front element.

```java
queue.remove();
```

If empty:

```text
NoSuchElementException
```

---

# 6️⃣ `poll()`

Removes and returns the front element.

```java
queue.poll();
```

If empty:

```text
null
```

---

# ⭐ Queue Example

```java
Queue<Integer> queue = new ArrayDeque<>();

queue.offer(10);
queue.offer(20);
queue.offer(30);

System.out.println(queue.peek());  // 10

System.out.println(queue.poll());  // 10
System.out.println(queue.poll());  // 20
System.out.println(queue.poll());  // 30
```

---

# ⭕ Deque — Double Ended Queue

`Deque` allows insertion and removal from **both ends**.

```text
          Front                         Rear
            ↓                            ↓
        +----+----+----+----+
        | 10 | 20 | 30 | 40 |
        +----+----+----+----+
```

You can:

```text
insert → Front / Rear
remove → Front / Rear
inspect → Front / Rear
```

---

# ⭐ Deque Methods

## 🔹 Insertion

```java
addFirst(e)
addLast(e)

offerFirst(e)
offerLast(e)
```

### Exception vs special value

```text
addFirst()  → exception on failure
addLast()   → exception on failure

offerFirst() → false on failure
offerLast()  → false on failure
```

---

# 🔹 Removal

```java
removeFirst()
removeLast()

pollFirst()
pollLast()
```

```text
removeFirst() → exception if empty
removeLast()  → exception if empty

pollFirst() → null if empty
pollLast()  → null if empty
```

---

# 🔹 Inspection

```java
getFirst()
getLast()

peekFirst()
peekLast()
```

```text
getFirst() → exception if empty
getLast()  → exception if empty

peekFirst() → null if empty
peekLast()  → null if empty
```

---

# ⭐ Complete Deque Table

| Operation | Exception version | Special-value version |
|---|---|---|
| Insert front | `addFirst()` | `offerFirst()` |
| Insert rear | `addLast()` | `offerLast()` |
| Remove front | `removeFirst()` | `pollFirst()` |
| Remove rear | `removeLast()` | `pollLast()` |
| Inspect front | `getFirst()` | `peekFirst()` |
| Inspect rear | `getLast()` | `peekLast()` |

### 🧠 Memory Trick

```text
ADD     → OFFER
REMOVE  → POLL
GET     → PEEK
```

And:

```text
First ↔ Front
Last  ↔ Rear
```

---

# ⭕ Stack Using Deque

Instead of:

```java
Stack<Integer> stack = new Stack<>();
```

prefer:

```java
Deque<Integer> stack = new ArrayDeque<>();
```

### Stack operations

```java
stack.push(10);
stack.push(20);
stack.push(30);
```

Conceptually:

```text
        TOP
         ↓
       +----+
       | 30 |
       +----+
       | 20 |
       +----+
       | 10 |
       +----+
```

Then:

```java
stack.pop();
```

returns:

```text
30
```

---

# ⭐ Stack Methods in Deque

`Deque` already provides:

```java
push(E e)
pop()
peek()
```

Conceptually:

```text
push(e) → addFirst(e)
pop()   → removeFirst()
peek()  → peekFirst()
```

⚠️ Your original note had:

```text
pop() → pollFirst()
```

This is **not the exact semantic equivalent**.

Why?

```text
pop()         → throws NoSuchElementException if empty
pollFirst()   → returns null if empty
```

So the better mapping is:

```text
push() → addFirst()
pop()  → removeFirst()
peek() → peekFirst()
```

---

# ⭐⭐ Queue vs Stack Using Same Deque

This is one of the most useful concepts.

## Queue

```java
Deque<Integer> queue = new ArrayDeque<>();

queue.addLast(10);
queue.addLast(20);
queue.addLast(30);

queue.removeFirst();    // 10
```

```text
addLast()
    ↓
10 → 20 → 30
↑
removeFirst()
```

Therefore:

```text
FIFO
```

---

## Stack

```java
Deque<Integer> stack = new ArrayDeque<>();

stack.addFirst(10);
stack.addFirst(20);
stack.addFirst(30);

stack.removeFirst();    // 30
```

```text
30
20
10
↑
removeFirst()
```

Therefore:

```text
LIFO
```

### ⭐ Same data structure, different behavior

```text
              Deque
             /     \
        Queue       Stack
        FIFO         LIFO

Queue:
addLast() + removeFirst()

Stack:
addFirst() + removeFirst()
```

---

# ⭐⭐ What about methods inherited from Collection?

`Queue` extends `Collection`, so it inherits methods such as:

```java
size()
isEmpty()
contains()
iterator()
toArray()
remove(Object)
clear()
addAll()
removeAll()
retainAll()
containsAll()
```

These methods are still available.

### But...

A `Queue` has a **behavioral contract**:

```text
FIFO
```

If you use:

```java
queue.remove(20);
```

you are removing a specific object rather than necessarily removing the front element.

Therefore, it is not a normal **queue operation**.

Similarly, collection methods such as:

```java
remove(Object)
clear()
removeAll()
retainAll()
```

can modify the contents in ways that don't represent normal FIFO processing.

### ⭐ Important distinction

The methods are **legal to call**.

They don't "break Java."

They simply aren't necessarily **queue-order operations**.

So the developer should use the API according to the abstraction they want.

---

# ⭕ PriorityQueue

A `PriorityQueue` is a Queue implementation where elements are processed according to **priority**, rather than normal FIFO order.

```java
PriorityQueue<Integer> pq = new PriorityQueue<>();
```

For integers, the default ordering is natural ascending order.

```java
pq.offer(30);
pq.offer(10);
pq.offer(20);

System.out.println(pq.poll()); // 10
System.out.println(pq.poll()); // 20
System.out.println(pq.poll()); // 30
```

---

# ⭐⭐ Internal Data Structure

`PriorityQueue` is internally based on a:

```text
Heap
```

More specifically:

```text
PriorityQueue
      ↓
Binary Heap
```

By default, it behaves as a:

```text
Min-Heap
```

### Conceptual representation

```text
             10
            /  \
          20    30
         /  \
       40    50
```

The smallest element is at the root.

Therefore:

```java
pq.peek();
```

returns:

```text
10
```

---

# ⭐ PriorityQueue Important Point

Don't say:

> PriorityQueue stores elements in sorted order.

❌ Not exactly.

The internal heap only guarantees that the **highest-priority element is available at the head**.

For a min-priority queue:

```java
pq.peek()
```

gives the smallest element.

But iterating through the queue does **not** guarantee sorted order.

If you need elements completely sorted, repeatedly use:

```java
poll()
```

until the queue becomes empty.

---

# ⭐ PriorityQueue with Custom Comparator

By default:

```java
PriorityQueue<Integer> pq = new PriorityQueue<>();
```

is a min-heap.

For max-heap:

```java
PriorityQueue<Integer> pq =
        new PriorityQueue<>(Collections.reverseOrder());
```

or:

```java
PriorityQueue<Integer> pq =
        new PriorityQueue<>((a, b) -> b - a);
```

⚠️ Prefer:

```java
(a, b) -> Integer.compare(b, a)
```

instead of `b - a`, because subtraction can overflow for extreme integer values.

---

# ⭐⭐⭐ Complexity

For `PriorityQueue`:

| Operation | Complexity |
|---|---:|
| `peek()` | O(1) |
| `offer()` | O(log n) |
| `add()` | O(log n) |
| `poll()` | O(log n) |
| `remove()` head | O(log n) |
| `contains()` | O(n) |
| arbitrary `remove(Object)` | O(n) |

---

# ⭐⭐⭐ Queue Implementations Comparison

| Implementation | Ordering | Internal idea | Null |
|---|---|---|---|
| `ArrayDeque` | FIFO / Deque | Resizable circular array | ❌ |
| `LinkedList` | FIFO / Deque | Doubly linked list | ✅ |
| `PriorityQueue` | Priority | Binary heap | ❌ |
| `Stack` | LIFO | Legacy `Vector` based | ✅ |

---

# ⭐⭐⭐ ArrayDeque vs LinkedList

| Feature | ArrayDeque | LinkedList |
|---|---|---|
| Implements `Deque` | ✅ | ✅ |
| Implements `List` | ❌ | ✅ |
| Array-backed | ✅ | ❌ |
| Doubly linked | ❌ | ✅ |
| Add/remove both ends | ✅ | ✅ |
| Random access | ❌ | ✅ |
| `null` elements | ❌ | ✅ |
| Memory overhead | Lower | Higher |
| Recommended for stack/deque | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ |

### ⭐ Practical rule

```text
Need Stack/Queue/Deque?
        ↓
   ArrayDeque
```

```text
Need List + Deque behavior?
        ↓
    LinkedList
```

---

# ⭐⭐⭐ Important Interview Traps

### Q1. Is `Queue` a class?

❌ No.

```java
Queue
```

is an **interface**.

---

### Q2. Is `ArrayDeque` a Queue?

More precisely:

```text
ArrayDeque → implements Deque
Deque      → extends Queue
```

So an `ArrayDeque` can be used as a Queue.

---

### Q3. Difference between `poll()` and `remove()`?

```text
poll()
→ removes front
→ returns null if empty

remove()
→ removes front
→ throws NoSuchElementException if empty
```

---

### Q4. Difference between `peek()` and `element()`?

```text
peek()
→ front element
→ null if empty

element()
→ front element
→ NoSuchElementException if empty
```

---

### Q5. Can ArrayDeque contain null?

❌ No.

```java
new ArrayDeque<>().add(null);
```

throws `NullPointerException`.

---

### Q6. Why prefer ArrayDeque over Stack?

```text
Stack
 ↓
Legacy class
 ↓
extends Vector
 ↓
synchronized
```

`ArrayDeque` is generally preferred for ordinary stack use because it is designed for deque/stack operations without the legacy `Vector` design.

---

### Q7. Is PriorityQueue FIFO?

❌ No.

It removes elements according to their **priority/order**, not insertion order.

---

### Q8. Is PriorityQueue internally sorted?

❌ No.

It is heap-based.

Only the head is guaranteed to be the highest-priority element according to the queue's ordering.

---

### Q9. What is the default PriorityQueue?

```text
Min-Heap
```

For integers:

```text
10, 20, 30

poll() → 10
```

---

### Q10. How can PriorityQueue become a Max-Heap?

```java
PriorityQueue<Integer> pq =
        new PriorityQueue<>(Collections.reverseOrder());
```

---

# 🧠 ONE-PAGE REVISION

```text
//====================== QUEUE REVISION ======================

Queue
  ↓
FIFO

Deque
  ↓
Double Ended Queue

ArrayDeque
  ↓
Array-backed Deque
  ↓
Can behave as:
    Queue
    Stack
    Deque

-------------------------------------------------------------

QUEUE METHODS

Insert:
    add()    → exception
    offer()  → false

Remove:
    remove() → exception
    poll()   → null

Inspect:
    element() → exception
    peek()    → null

-------------------------------------------------------------

DEQUE

Insert:
    addFirst()
    addLast()
    offerFirst()
    offerLast()

Remove:
    removeFirst()
    removeLast()
    pollFirst()
    pollLast()

Inspect:
    getFirst()
    getLast()
    peekFirst()
    peekLast()

-------------------------------------------------------------

STACK USING DEQUE

push() → addFirst()
pop()  → removeFirst()
peek() → peekFirst()

-------------------------------------------------------------

PRIORITY QUEUE

PriorityQueue
      ↓
Binary Heap
      ↓
Default = Min Heap

peek() → O(1)
offer() → O(log n)
poll() → O(log n)

-------------------------------------------------------------

IMPORTANT

ArrayDeque → ❌ null
PriorityQueue → ❌ null

LinkedList → ✅ null

Map ≠ Collection

Queue → FIFO
Stack → LIFO
Deque → Both ends
PriorityQueue → Priority
```

### 🧠 Memory Trick

```text
QUEUE
"First comes → First goes"

STACK
"Last comes → First goes"

DEQUE
"Both doors are open"

PRIORITY QUEUE
"Important one goes first"
```

This gives you the **core Queue/Deque/Stack/PriorityQueue picture** you need for both Java collections revision and interviews.