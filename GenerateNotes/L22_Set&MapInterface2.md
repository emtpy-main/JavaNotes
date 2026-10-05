Got it. I’ll continue in the **same enhanced-notes format** as Parts 1–5. I’ll preserve your structure and terminology, while correcting Java API names, return types, range semantics, and adding the missing interview-level details. Your source covers Set/TreeSet, Map/HashMap/TreeMap, and specialized maps such as `Hashtable`, `Properties`, `WeakHashMap`, `IdentityHashMap`, `EnumMap`, and `ConcurrentHashMap`. Pasted markdown

# Java Collection Framework — Set & Map Part 2

```java
//======================================================================
//       SET & MAP INTERFACE - COLLECTION FRAMEWORK PART-6
//======================================================================
```

# 1. Set Interface Hierarchy ⭐

```text
                         Iterable
                            |
                        Collection
                            |
                           Set
                            |
              ----------------------------
              |                          |
           HashSet                   SortedSet
              |                          |
       LinkedHashSet              NavigableSet
                                        |
                                      TreeSet
```

### Important

`Set` itself does not introduce many methods beyond those inherited from `Collection`.

Its main semantic rule is:

> **A Set does not allow duplicate elements.**

The more specialized methods such as:

```text
first()
last()
headSet()
tailSet()
subSet()
lower()
floor()
higher()
ceiling()
```

come from the sorted/navigable interfaces.

---

# 2. Set Constructors

## HashSet / LinkedHashSet

### 1. No-argument constructor

```java
Set<Integer> set = new HashSet<>();
```

Creates an empty `HashSet`.

---

### 2. Initial capacity

```java
Set<Integer> set = new HashSet<>(100);
```

Specifies the initial capacity.

⭐ This does **not** mean the Set contains 100 elements.

```text
size     = 0
capacity ≈ 100
```

---

### 3. Initial capacity + load factor

```java
Set<Integer> set =
        new HashSet<>(100, 0.8f);
```

Here:

```text
100  → initial capacity
0.8  → load factor
```

---

### 4. Collection constructor

```java
Set<Integer> set =
        new HashSet<>(
            List.of(1, 2, 2, 3, 4, 5, 4, 1)
        );
```

Result:

```text
[1, 2, 3, 4, 5]
```

because duplicates are automatically removed.

---

# 3. TreeSet Constructors ⭐

`TreeSet` has several useful constructors.

### 1. No-argument

```java
TreeSet<Integer> set =
        new TreeSet<>();
```

Uses the elements' **natural ordering**.

For `Integer`:

```text
10 < 20 < 30
```

---

### 2. Comparator

```java
TreeSet<Integer> set =
        new TreeSet<>(Comparator.reverseOrder());
```

Now elements are stored in descending order:

```text
[90, 80, 50, 23, 10]
```

---

### 3. Collection

```java
TreeSet<Integer> set =
        new TreeSet<>(List.of(30, 10, 20));
```

Result:

```text
[10, 20, 30]
```

---

### 4. SortedSet

```java
SortedSet<Integer> other =
        new TreeSet<>(List.of(10, 20, 30));

TreeSet<Integer> set =
        new TreeSet<>(other);
```

Creates a TreeSet containing the elements of the given sorted set.

---

# 4. TreeSet

### Definition ⭐

> `TreeSet` is a `NavigableSet` implementation that stores unique elements in sorted order.

Example:

```java
TreeSet<Integer> set = new TreeSet<>();

set.add(80);
set.add(23);
set.add(10);
set.add(90);
set.add(50);
```

Iteration gives:

```text
[10, 23, 50, 80, 90]
```

### Internal concept

```text
TreeSet
   |
   ↓
NavigableMap / TreeMap
   |
   ↓
Red-Black Tree
```

---

# 5. TreeSet and Natural Ordering

For:

```java
TreeSet<Integer>
```

Java knows how to compare `Integer` objects.

For custom objects, we can provide:

```text
Comparable
```

or:

```text
Comparator
```

Example:

```java
TreeSet<Student> students =
        new TreeSet<>(
            Comparator.comparing(Student::getMarks)
        );
```
## ⭕ TO Do :-
```
    -> Read about Comparator & comparing.
```
---

# 6. SortedSet Methods

`SortedSet` provides operations for working with the sorted sequence.

---

## `first()`

### Definition

> Returns the smallest element according to the Set's ordering.

```java
System.out.println(set.first());
```

If:

```text
[10, 23, 50, 80, 90]
```

output:

```text
10
```

---

## `last()`

### Definition

> Returns the largest element according to the Set's ordering.

```java
System.out.println(set.last());
```

Output:

```text
90
```

⭐ Your original note had `80`, but with the values `80, 23, 10, 90, 50`, the largest value is **90**.

---

# 7. `headSet(E toElement)`

### Definition

> Returns a view containing elements strictly less than the specified element.

Example:

```java
TreeSet<Integer> set =
        new TreeSet<>(
            List.of(10, 23, 50, 80, 90)
        );

System.out.println(set.headSet(80));
```

Output:

```text
[10, 23, 50]
```

`80` is excluded.

```text
headSet(80)
        ↓
< 80
```

---

# 8. `tailSet(E fromElement)`

### Definition

> Returns a view containing elements greater than or equal to the specified element.

```java
System.out.println(set.tailSet(80));
```

Output:

```text
[80, 90]
```

⭐ Your note said "strictly greater," but `tailSet(80)` is **inclusive by default**.

```text
tailSet(80)
       ↓
>= 80
```

---

# 9. `subSet(from, to)`

### Definition

> Returns a view containing elements from `from` inclusive to `to` exclusive.

```java
System.out.println(
    set.subSet(23, 80)
);
```

Output:

```text
[23, 50]
```

Conceptually:

```text
from ≤ x < to
```

So:

```text
23 ≤ x < 80
```

---

# 10. `lower(E e)` ⭐

Provided by `NavigableSet`.

### Definition

> Returns the greatest element strictly less than the specified element.

```java
set.lower(80);
```

If:

```text
[10, 23, 50, 80, 90]
```

result:

```text
50
```

---

# 11. `floor(E e)`

### Definition

> Returns the greatest element less than or equal to the specified element.

```java
set.floor(80);
```

Output:

```text
80
```

If `80` does not exist:

```text
floor(75)
```

would return:

```text
50
```

---

# 12. `higher(E e)`

### Definition

> Returns the smallest element strictly greater than the specified element.

```java
set.higher(80);
```

Output:

```text
90
```

---

# 13. `ceiling(E e)`

### Definition

> Returns the smallest element greater than or equal to the specified element.

```java
set.ceiling(80);
```

Output:

```text
80
```

If `80` doesn't exist:

```java
set.ceiling(75);
```

returns:

```text
80
```

---

# 14. `lower()` vs `floor()` vs `higher()` vs `ceiling()` ⭐⭐⭐⭐⭐

This is extremely important for interviews.

Suppose:

```text
[10, 20, 30, 40, 50]
```

For:

```java
x = 30
```

```text
lower(30)
   ↓
20

floor(30)
   ↓
30

higher(30)
   ↓
40

ceiling(30)
   ↓
30
```

Easy memory trick:

```text
lower    → <
floor    → <=

higher   → >
ceiling  → >=
```

---

# 15. `pollFirst()`

### Definition

> Removes and returns the first/smallest element.

```java
Integer x = set.pollFirst();
```

Example:

```text
Before:
[10, 20, 30]

pollFirst()
    ↓
returns 10

After:
[20, 30]
```

---

# 16. `pollLast()`

Removes and returns the last/largest element.

```java
Integer x = set.pollLast();
```

Example:

```text
Before:
[10, 20, 30]

returns → 30

After:
[10, 20]
```

---

# 17. `descendingSet()`

### Definition

> Returns a reverse-order view of the Set.

```java
System.out.println(set.descendingSet());
```

If:

```text
[10, 20, 30, 40]
```

then:

```text
[40, 30, 20, 10]
```

⭐ It is a **view**, not simply an independent copied Set.

---

# 18. `descendingIterator()`

Used to traverse the Set in descending order.

```java
Iterator<Integer> it =
        set.descendingIterator();

while(it.hasNext()) {
    System.out.println(it.next());
}
```

Output:

```text
90
80
50
23
10
```

---

# 19. Inclusive/Exclusive Range Methods ⭐⭐⭐

`NavigableSet` provides:

```java
headSet(toElement, inclusive)
tailSet(fromElement, inclusive)
subSet(fromElement, fromInclusive,
       toElement, toInclusive)
```

### `headSet()`

```java
set.headSet(50, true);
```

means:

```text
x <= 50
```

While:

```java
set.headSet(50, false);
```

means:

```text
x < 50
```

---

# 20. `tailSet()`

```java
set.tailSet(50, true);
```

means:

```text
x >= 50
```

while:

```java
set.tailSet(50, false);
```

means:

```text
x > 50
```

---

# 21. `subSet()`

```java
set.subSet(
    20,
    true,
    80,
    false
);
```

means:

```text
20 <= x < 80
```

Possible result:

```text
[20, 30, 40, 50, 60, 70]
```

---

# 22. Set Quick Revision

```text
TreeSet
│
├── SortedSet methods
│
├── first()
├── last()
├── headSet()
├── tailSet()
└── subSet()
│
└── NavigableSet methods
    │
    ├── lower()
    ├── floor()
    ├── higher()
    ├── ceiling()
    ├── pollFirst()
    ├── pollLast()
    ├── descendingSet()
    ├── descendingIterator()
    ├── headSet(..., inclusive)
    ├── tailSet(..., inclusive)
    └── subSet(..., flags)
```

---

# 23. Map Hierarchy ⭐⭐⭐

`Map` is a separate hierarchy from `Collection`.

```text
                         Map
                          |
              -------------------------
              |                       |
           HashMap                SortedMap
              |                       |
       LinkedHashMap             NavigableMap
                                      |
                                   TreeMap
```

Other important Map implementations include:

```text
Hashtable
Properties
EnumMap
IdentityHashMap
WeakHashMap
ConcurrentHashMap
```

⭐ Remember:

```text
Map ≠ Collection
```

`Map` does not extend `Collection`.

---

# 24. Why Does Map Have Separate Methods?

A Collection stores individual elements:

```text
10
20
30
```

A Map stores relationships:

```text
101 → Aditya
102 → Rohit
103 → Rohan
```

Therefore, Map needs operations such as:

```java
put()
get()
containsKey()
containsValue()
keySet()
values()
entrySet()
```

---

# 25. Basic Map Example

```java
Map<Integer, String> map =
        new HashMap<>();

map.put(101, "Aditya");
map.put(102, "Rohit");
map.put(103, "Rohan");
```

Conceptually:

```text
101 → Aditya
102 → Rohit
103 → Rohan
```

---

# 26. `size()`

Returns the number of key-value mappings.

```java
map.size();
```

---

# 27. `isEmpty()`

Returns:

```text
true
```

if the map contains no mappings.

```java
map.isEmpty();
```

---

# 28. `containsKey(K key)`

### Definition

> Checks whether the specified key exists in the map.

```java
map.containsKey(102);
```

Output:

```text
true
```

For a HashMap, average complexity is generally:

```text
O(1)
```

---

# 29. `containsValue(Object value)`

Checks whether at least one mapping contains the specified value.

```java
map.containsValue("Rohit");
```

Because values are not indexed by hash keys in the same way, this generally requires scanning entries:

```text
O(n)
```

---

# 30. `get(Object key)`

Returns the value associated with the key.

```java
map.get(102);
```

Output:

```text
Rohit
```

If the key doesn't exist:

```java
map.get(999);
```

returns:

```text
null
```

### Important ⭐

`null` can mean either:

1. Key does not exist, or
2. Key exists and its value is `null`.

Therefore, if this distinction matters:

```java
map.containsKey(key)
```

should be used.

---

# 31. `put(K key, V value)` ⭐⭐⭐

### Definition

> Adds a key-value mapping or replaces the value associated with an existing key.

```java
String old =
    map.put(102, "Pratik");
```

If `102` already exists:

```text
old value → Rohit
new value → Pratik
```

`put()` returns:

```text
old value
```

If the key didn't previously exist:

```text
returns null
```

---

# 32. `remove(Object key)`

Removes the mapping associated with the key.

```java
String removed =
        map.remove(102);
```

It returns the previous value associated with the key, or `null` if there was no mapping.

---

# 33. `putAll(Map<? extends K, ? extends V>)`

Your note has:

```text
Map<T,K>
```

but the generic relationship is better written conceptually as:

```java
putAll(
    Map<? extends K, ? extends V> m
)
```

It copies all mappings from another Map.

```java
map1.putAll(map2);
```

---

# 34. `clear()`

Removes all mappings:

```java
map.clear();
```

After:

```java
map.isEmpty();
```

returns:

```text
true
```

---

# 35. `keySet()`

### Definition

> Returns a Set view of all keys contained in the map.

```java
Set<Integer> keys =
        map.keySet();
```

Example:

```text
Map:
101 → Aditya
102 → Rohit
103 → Rohan

keySet():
[101, 102, 103]
```

Why `Set`?

Because Map keys are unique.

---

# 36. `values()`

### Definition

> Returns a Collection view of all values contained in the map.

```java
Collection<String> values =
        map.values();
```

Why not `Set`?

Because values **can be duplicated**.

Example:

```text
101 → Java
102 → C++
103 → Java
```

Values:

```text
[Java, C++, Java]
```

Therefore:

```java
Collection<V>
```

is used.

---

# 37. `entrySet()` ⭐⭐⭐⭐⭐

### Definition

> Returns a Set view containing all key-value mappings represented as `Map.Entry` objects.

Example:

```java
Set<Map.Entry<Integer, String>> entries =
        map.entrySet();
```

Each `Entry` represents:

```text
key + value
```

Example:

```text
101 → Aditya
```

is one Entry.

---

# 38. Iterating Through Map

The preferred way to access both key and value together:

```java
for(Map.Entry<Integer, String> entry
        : map.entrySet()) {

    Integer key = entry.getKey();

    String value = entry.getValue();

    System.out.println(
        key + " , " + value
    );
}
```

---

# 39. Why `entrySet()` is Efficient ⭐

Compare:

```java
for(Integer key : map.keySet()) {
    System.out.println(
        key + " " + map.get(key)
    );
}
```

with:

```java
for(Map.Entry<Integer, String> entry
        : map.entrySet()) {

    System.out.println(
        entry.getKey() +
        " " +
        entry.getValue()
    );
}
```

`entrySet()` directly gives us both the key and value as an entry, so it is generally the preferred approach when both are required.

---

# 40. `getOrDefault()`

### Definition

> Returns the value associated with a key, or a specified default value if the key is not present.

```java
String value =
    map.getOrDefault(
        999,
        "Not Found"
    );
```

If `999` doesn't exist:

```text
Not Found
```

---

# 41. `putIfAbsent()`

### Definition

> Adds a mapping only if the specified key does not already have a mapping.

```java
map.putIfAbsent(
    101,
    "New Value"
);
```

If key `101` already exists, its existing value remains unchanged.

---

# 42. `remove(key, value)`

This overloaded method removes a mapping **only if both the key and value match**.

```java
map.remove(101, "Aditya");
```

If:

```text
101 → Aditya
```

exists:

```text
removed → true
```

If:

```text
101 → Rohit
```

then:

```text
removed → false
```

---

# 43. `replace(key, value)`

Replaces the value associated with an existing key.

```java
map.replace(
    101,
    "Pratik"
);
```

If key `101` does not exist, it does not insert a new mapping.

---

# 44. `replace(key, oldValue, newValue)`

Conditional replacement.

```java
map.replace(
    101,
    "Aditya",
    "Pratik"
);
```

Replacement occurs only if:

```text
key = 101
AND
current value = Aditya
```

---

# 45. `Map.Entry` ⭐

`Map.Entry<K,V>` is a nested interface inside `Map`.

It represents a single key-value mapping.

Important methods:

```java
getKey()
getValue()
setValue()
```

Example:

```java
for(Map.Entry<Integer, String> entry
        : map.entrySet()) {

    System.out.println(
        entry.getKey()
    );

    System.out.println(
        entry.getValue()
    );
}
```

---

# 46. `Map.of()` ⭐⭐⭐

Your original note has:

```java
map.of(...)
```

The correct syntax is:

```java
Map<Integer, String> map2 =
        Map.of(
            101, "Aditya",
            102, "Rohan"
        );
```

`Map.of()` creates an **unmodifiable map**.

Therefore:

```java
map2.put(103, "Pratik");
```

throws:

```text
UnsupportedOperationException
```

Also:

```text
Map.of()
```

does not permit `null` keys or `null` values.

---

# 47. `Map.copyOf()`

Similar to `List.copyOf()`:

```java
Map<Integer, String> copy =
        Map.copyOf(map);
```

Creates an unmodifiable Map containing the mappings of the given Map.

---

# 48. HashMap / LinkedHashMap Constructors

### 1. No argument

```java
Map<Integer, String> map =
        new HashMap<>();
```

---

### 2. Initial capacity

```java
Map<Integer, String> map =
        new HashMap<>(100);
```

---

### 3. Initial capacity + load factor

```java
Map<Integer, String> map =
        new HashMap<>(100, 0.8f);
```

---

### 4. Another Map

```java
Map<Integer, String> map2 =
        new HashMap<>(map);
```

Creates a HashMap containing mappings from `map`.

---

# 49. HashMap vs LinkedHashMap — Structural Difference

```text
HashMap
   |
   ↓
Hash table
   |
   ↓
Buckets
```

`LinkedHashMap` adds a linked ordering mechanism:

```text
LinkedHashMap
      |
      ├── Hash table
      |
      └── Linked ordering
```

Therefore:

```text
HashMap
    → no guaranteed iteration order

LinkedHashMap
    → predictable iteration order
```

---

# 50. TreeMap ⭐⭐⭐

### Definition

> `TreeMap` is a `NavigableMap` implementation that stores mappings in ascending key order by default.

Example:

```java
TreeMap<Integer, String> map =
        new TreeMap<>();

map.put(80, "A");
map.put(20, "B");
map.put(50, "C");
```

Iteration:

```text
20 → B
50 → C
80 → A
```

---

# 51. TreeMap — SortedMap Methods

## `firstKey()`

Returns the smallest key.

```java
map.firstKey();
```

---

## `lastKey()`

Returns the largest key.

```java
map.lastKey();
```

---

## `firstEntry()`

Returns the mapping with the smallest key.

```java
map.firstEntry();
```

---

## `lastEntry()`

Returns the mapping with the largest key.

```java
map.lastEntry();
```

---

# 52. TreeMap Range Methods

### `headMap(key)`

Returns mappings whose keys are:

```text
< key
```

### `tailMap(key)`

Returns mappings whose keys are:

```text
>= key
```

### `subMap(fromKey, toKey)`

Returns:

```text
fromKey <= key < toKey
```

---

# 53. TreeMap Navigable Methods

```text
lowerKey()
lowerEntry()

floorKey()
floorEntry()

higherKey()
higherEntry()

ceilingKey()
ceilingEntry()
```

### Important correction ⭐

Your notes list:

```text
floor(key)
ceil(key)
```

The standard `NavigableMap` method names are:

```java
floorKey(key)
floorEntry(key)

ceilingKey(key)
ceilingEntry(key)
```

---

# 54. TreeMap Descending Methods

```java
map.descendingMap();
```

returns a reverse-order view.

Also:

```java
map.descendingKeySet();
```

provides keys in descending order.

---

# 55. Hashtable ⭐

### Definition

> `Hashtable` is a legacy synchronized implementation of the Map interface.

Characteristics:

```text
✓ Legacy
✓ Synchronized
✓ Thread-safe for its individual synchronized operations
✓ Does not allow null keys
✓ Does not allow null values
```

Compared with modern `HashMap`:

```text
HashMap
   → generally faster
   → not synchronized

Hashtable
   → synchronized
   → legacy
```

---

# 56. Why is Hashtable Usually Avoided?

Because its synchronization model is coarse-grained.

Conceptually:

```text
Thread 1
   ↓
locks Hashtable
   ↓
Thread 2 waits
```

Modern applications often use:

```java
ConcurrentHashMap
```

when concurrent access is required.

---

# 57. Properties ⭐

`Properties` is a specialized class for configuration/property data.

It extends:

```text
Hashtable
   |
Properties
```

Example:

```java
Properties properties =
        new Properties();

properties.setProperty(
    "username",
    "admin"
);

properties.setProperty(
    "theme",
    "dark"
);
```

Retrieval:

```java
String theme =
    properties.getProperty("theme");
```

Commonly used for:

```text
configuration
application settings
property files
```

---

# 58. WeakHashMap ⭐

### Definition

> `WeakHashMap` is a Map implementation whose keys are held using weak references, allowing entries to become eligible for garbage collection when their keys are no longer strongly referenced elsewhere.

Conceptually:

```text
Normal HashMap

Key
 ↓
strong reference
 ↓
Object remains reachable


WeakHashMap

Key
 ↓
weak reference
 ↓
may become GC eligible
```

This can be useful in certain cache-like or metadata scenarios.

### Important

Don't simply say:

> "WeakHashMap is a cache."

It can be useful for cache-like behavior, but it is **not a general-purpose cache implementation**.

---

# 59. IdentityHashMap ⭐⭐⭐

Normal `HashMap` uses logical equality:

```java
equals()
hashCode()
```

`IdentityHashMap` uses **reference identity**:

```java
==
```

Example:

```java
String a =
    new String("Pratik");

String b =
    new String("Pratik");
```

Now:

```java
System.out.println(a.equals(b));
```

returns:

```text
true
```

but:

```java
System.out.println(a == b);
```

returns:

```text
false
```

because they are different objects.

---

# 60. HashMap vs IdentityHashMap ⭐⭐⭐⭐⭐

### HashMap

Uses:

```text
equals()
hashCode()
```

So logically equal objects are treated as the same key.

### IdentityHashMap

Uses:

```text
==
```

and identity-based hashing.

Therefore:

```text
same object?
     ↓
same key

different objects?
     ↓
can be different keys
even if equals() is true
```

Example:

```java
Map<String, Integer> map =
        new IdentityHashMap<>();

map.put(a, 1);
map.put(b, 2);
```

Both can exist as separate keys because:

```text
a != b
```

even though:

```text
a.equals(b) == true
```

---

# 61. EnumMap ⭐⭐⭐

Suppose:

```java
enum Day {
    MON,
    TUE,
    WED,
    THU,
    FRI,
    SAT,
    SUN
}
```

We could use:

```java
Map<Day, Integer> map =
        new HashMap<>();
```

But Java provides:

```java
EnumMap<Day, Integer> map =
        new EnumMap<>(Day.class);
```

---

# 62. Why EnumMap is Special?

Enum constants have a fixed set of possible values and each enum constant has an ordinal position.

For example:

```text
MON → 0
TUE → 1
WED → 2
...
```

`EnumMap` is specifically optimized for enum keys.

### Important correction ⭐

Don't say:

> "EnumMap declares a HashMap of that size."

It does **not** use a HashMap internally.

A better conceptual explanation is:

> `EnumMap` uses the fixed, known universe of enum constants to provide an efficient array-like mapping internally.

---

# 63. EnumMap Properties

```text
EnumMap
✓ Only enum keys
✓ Does not allow null keys
✓ Allows null values
✓ Efficient
✓ Memory efficient
✓ Maintains enum declaration order
✓ Does not use hashing like HashMap
```

Example:

```java
EnumMap<Day, Integer> map =
        new EnumMap<>(Day.class);

map.put(Day.MON, 10);
map.put(Day.TUE, 20);
```

---

# 64. ConcurrentHashMap ⭐⭐⭐⭐⭐

### Definition

> `ConcurrentHashMap` is a thread-safe Map implementation designed for concurrent access with better scalability than synchronizing an entire map for every operation.

Example:

```java
Map<Integer, String> map =
        new ConcurrentHashMap<>();
```

Multiple threads can operate on it concurrently.

---

# 65. HashMap vs ConcurrentHashMap

| HashMap | ConcurrentHashMap |
|---|---|
| Not thread-safe | Thread-safe |
| Better for single-threaded use | Designed for concurrent access |
| Allows null key/value | Does not allow null keys/values |
| No concurrency guarantees | Provides concurrency guarantees |

### ⭐ Important

`ConcurrentHashMap` does **not** allow:

```java
map.put(null, "value");
```

or:

```java
map.put(1, null);
```

Both are invalid.

---

# 66. Complete Map Comparison ⭐⭐⭐⭐⭐

| Map | Ordering | Typical lookup | Null Key | Null Value | Main Use |
|---|---|---:|---|---|---|
| `HashMap` | No guaranteed order | O(1) avg. | One | Yes | General-purpose |
| `LinkedHashMap` | Insertion/access order | O(1) avg. | One | Yes | Predictable order |
| `TreeMap` | Sorted keys | O(log n) | Generally no natural-order null | Yes, subject to implementation/comparator | Sorted mappings |
| `Hashtable` | No guaranteed order | O(1) avg. | No | No | Legacy |
| `WeakHashMap` | No ordering guarantee | O(1) avg. typical | One | Yes | Weak-key mappings |
| `IdentityHashMap` | No normal equality semantics | O(1) avg. typical | Yes | Yes | Identity-based keys |
| `EnumMap` | Enum declaration order | O(1) | No | Yes | Enum keys |
| `ConcurrentHashMap` | No guaranteed order | O(1) avg. | No | No | Concurrent access |

---

# 67. Complete Set Comparison ⭐⭐⭐⭐⭐

| Set | Ordering | Typical `contains()` | Null |
|---|---|---:|---|
| `HashSet` | No guaranteed order | O(1) avg. | One null |
| `LinkedHashSet` | Insertion order | O(1) avg. | One null |
| `TreeSet` | Sorted | O(log n) | Generally no with natural ordering |

---

# 68. Most Important Interview Traps

```text
//===============================================================
//                 INTERVIEW TRAPS
//===============================================================

1. Set is an interface.
   HashSet is a concrete implementation.

2. Map does NOT extend Collection.

3. HashSet does NOT guarantee insertion order.

4. LinkedHashSet preserves insertion order.

5. TreeSet maintains sorted order.

6. HashMap provides average O(1), not guaranteed O(1).

7. TreeMap provides O(log n).

8. HashSet internally uses HashMap machinery.

9. HashMap uses hashCode() + equals().

10. Same hashCode does NOT mean objects are equal.

11. If equals() is true,
    hashCode() MUST be equal.

12. HashMap allows one null key
    and multiple null values.

13. HashSet allows one null element.

14. TreeSet generally doesn't allow null
    with natural ordering.

15. TreeMap generally doesn't allow null key
    with natural ordering.

16. List has index-based access.
    Set and Map do not.

17. Map has:
       keySet()
       values()
       entrySet()

18. keySet() returns Set
    because keys are unique.

19. values() returns Collection
    because values can duplicate.

20. entrySet() returns Set<Map.Entry<K,V>>.

21. List.of(), Set.of(), Map.of()
    create unmodifiable collections/maps.

22. Map.of() does not allow null keys/values.

23. TreeSet → Red-Black tree based.

24. TreeMap → Red-Black tree based.

25. Stack/Hashtable are legacy classes.

26. ConcurrentHashMap is preferred over Hashtable
    for modern concurrent Map use cases.

27. IdentityHashMap uses == rather than equals().

28. WeakHashMap uses weak references for keys.

29. EnumMap is specialized for enum keys.

30. EnumMap does NOT use HashMap internally.
```

# 🧠 One-Page Mental Model

```text
                         COLLECTION FRAMEWORK
                                |
              --------------------------------------
              |                                    |
          Collection                              Map
              |                                    |
        -------------                    -----------------------
        |           |                    |          |          |
       List        Set                HashMap    LinkedHashMap TreeMap
                   |                    |          |          |
          ----------------               Hashing   Hashing    Tree
          |              |               |          |          |
       HashSet      LinkedHashSet        Fast       Ordered    Sorted
          |
       Hashing
          |
       Average O(1)


SET
──────────────────────────────
HashSet
    → unique
    → no guaranteed order
    → average O(1)

LinkedHashSet
    → unique
    → insertion order
    → average O(1)

TreeSet
    → unique
    → sorted
    → O(log n)


MAP
──────────────────────────────
HashMap
    → key-value
    → unique keys
    → no guaranteed order
    → average O(1)

LinkedHashMap
    → key-value
    → insertion/access order
    → average O(1)

TreeMap
    → key-value
    → sorted keys
    → O(log n)


SPECIAL MAPS
──────────────────────────────
Hashtable
    → legacy + synchronized

Properties
    → configuration data

WeakHashMap
    → weak keys

IdentityHashMap
    → == comparison

EnumMap
    → enum keys

ConcurrentHashMap
    → concurrent/thread-safe Map
```

### ⭐ The four lines I'd memorize before an interview

```text
HashSet      → Unique + Fast average lookup + No guaranteed order

LinkedHashSet → Unique + Fast average lookup + Insertion order

TreeSet      → Unique + Sorted + O(log n)

HashMap      → Key-Value + Unique keys + Fast average lookup

LinkedHashMap → Key-Value + Insertion/access order

TreeMap      → Key-Value + Sorted keys + O(log n)
```

And for `TreeSet`/`TreeMap`, remember the boundary operators as:

```text
lower   <
floor   <=
higher  >
ceiling >=
```

That single pattern makes most of the `NavigableSet`/`NavigableMap` methods much easier to remember.