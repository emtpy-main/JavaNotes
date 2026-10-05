# `String` — Part 2 in Java

---

## 1. 🧵 String with `new` Keyword

Normally, when we create a String using a literal:

```java
String s = "Hello";
```

Java uses the **String Constant Pool (SCP)**.

But when we use `new`:

```java
String s = new String("Hello");
```

➡️ A **new String object is explicitly created in the heap**.

### Example:

```java
String s1 = new String();
System.out.println(s1);
```

Output:

```text
```

➡️ It creates an **empty String**.

```java
System.out.println(s1.length());  // 0
System.out.println(s1.isEmpty()); // true
```

### ⚠️ Important

```java
String s1 = "Hello";
String s2 = new String("Hello");
```

Here:

* `"Hello"` → String literal, stored in SCP.
* `new String("Hello")` → creates a separate String object in heap.
* Both contain the same characters.
* But `s1 == s2` → `false`
* `s1.equals(s2)` → `true`

---

# 2. 🏗️ Different Constructors of `String`

`String` provides several constructors for creating String objects from different types of data.

---

### ① Empty String

```java
String s1 = new String();
```

➡️ Creates an empty String.

```java
System.out.println(s1.length()); // 0
```

---

### ② From Another String

```java
String s2 = new String("Hello");

System.out.println(s2); // Hello
```

➡️ Creates a new String object containing the same sequence of characters.

---

### ③ From Another String Variable

```java
String s = "Aditya";

String s3 = new String(s);

System.out.println(s3); // Aditya
```

➡️ Creates a new String object with the same contents as `s`.

⚠️ It does **not** make the two variables refer to the same object.

```java
System.out.println(s == s3);      // false
System.out.println(s.equals(s3)); // true
```

---

### ④ From `char[]`

```java
char[] arr = {'A', 'd', 'i', 't', 'y', 'a'};

String s4 = new String(arr);

System.out.println(s4); // Aditya
```

The characters of the array are used to create the String.

### Important: String is immutable

```java
arr[0] = 'B';

System.out.println(s4);
```

Output:

```text
Aditya
```

➡️ Changing the original array does **not** change the String.

### Why?

`String` is immutable. The String constructor creates the String's internal character/byte representation rather than keeping the caller's mutable `char[]` as the String itself.

---

### ⑤ From `char[]` with Offset

```java
char[] arr = {
    'A','d','i','t','y','a',' ','S','i','n','g','h'
};

String s5 = new String(arr, 0, 5);

System.out.println(s5);
```

Output:

```text
Adity
```

### Syntax

```java
new String(char[] array, int offset, int count)
```

```text
                    count
                      ↓
new String(arr,     0,     5);
                  ↑
               offset
```

➡️ `offset` = starting index
➡️ `count` = number of characters to copy

⚠️ `count` is **not the ending index**.

For example:

```java
new String(arr, 2, 3);
```

copies:

```text
index →  2   3   4
         i   t   y
```

Result:

```text
ity
```

---

# ⑥ From `byte[]`

```java
byte[] arr = {97, 98, 99};

String s6 = new String(arr);

System.out.println(s6);
```

Output:

```text
abc
```

Why?

```text
97 → a
98 → b
99 → c
```

➡️ The bytes are decoded using the platform's default charset for this constructor.

### ⭐ Better practice

When converting bytes to String, specify the charset explicitly:

```java
String s = new String(arr, StandardCharsets.UTF_8);
```

This avoids platform-dependent behavior.

---

# ⑦ From `byte[]` with Offset

```java
byte[] arr = {97, 98, 99};

String s7 = new String(arr, 0, 3);

System.out.println(s7); // abc
```

### Syntax

```java
new String(byte[] bytes, int offset, int length)
```

```text
                         length
                           ↓
new String(arr,           0,     3);
                         ↑
                      offset
```

➡️ `offset` = starting position
➡️ `length` = number of bytes to decode

⚠️ Again, the third argument is a **length**, not an ending index.

---

# ⑧ From `StringBuilder` / `StringBuffer`

```java
StringBuilder sb = new StringBuilder("hello");

String s = new String(sb);

System.out.println(s);
```

Output:

```text
hello
```

Similarly:

```java
StringBuffer sb = new StringBuffer("hello");

String s = new String(sb);

System.out.println(s);
```

Output:

```text
hello
```

➡️ A new immutable `String` is created from the current contents of the builder/buffer.

---

# ⭐ 3. Important String Properties

### String is:

```text
String
  ↓
final class
  ↓
Immutable
  ↓
Sequence of characters
```

Example:

```java
String s = "Hello";

s.concat(" World");

System.out.println(s);
```

Output:

```text
Hello
```

Why?

Because `concat()` creates a **new String**.

```java
s = s.concat(" World");

System.out.println(s);
```

Output:

```text
Hello World
```

---

# 4. 🔍 String Methods

---

## ① Length / Emptiness

### `length()`

```java
String s = "Hello";

System.out.println(s.length());
```

Output:

```text
5
```

➡️ Returns the number of characters.

### Syntax

```java
int length()
```

### Time Complexity

```text
O(1)
```

---

### `isEmpty()`

```java
String s = "";

System.out.println(s.isEmpty());
```

Output:

```text
true
```

➡️ Checks whether length is `0`.

```java
s.length() == 0
```

is effectively what it checks.

### Complexity

```text
O(1)
```

---

### `isBlank()` ⭐ Java 11+

```java
String s = "   ";

System.out.println(s.isBlank());
```

Output:

```text
true
```

`isBlank()` returns `true` when the String is empty or contains only Unicode whitespace.

```java
"".isBlank();       // true
"   ".isBlank();    // true
" hello ".isBlank();// false
```

### Difference

```text
isEmpty()
    ↓
Only checks length == 0

isBlank()
    ↓
Checks empty OR only whitespace
```

---

# ② Character Access

## `charAt(int index)`

```java
String s = "Hello";

System.out.println(s.charAt(1));
```

Output:

```text
e
```

Indexes:

```text
H   e   l   l   o
0   1   2   3   4
```

### Syntax

```java
char charAt(int index)
```

### Complexity

```text
O(1)
```

⚠️ Invalid index:

```java
s.charAt(10);
```

throws:

```text
StringIndexOutOfBoundsException
```

---

## `toCharArray()`

```java
String s = "Hello";

char[] arr = s.toCharArray();
```

Output conceptually:

```text
['H', 'e', 'l', 'l', 'o']
```

➡️ Creates a new `char[]`.

### Complexity

```text
O(n)
```

because all characters must be copied.

---

# ③ ⚖️ Comparison Methods

## `equals()`

```java
String s1 = "Hello";
String s2 = "Hello";

System.out.println(s1.equals(s2));
```

Output:

```text
true
```

➡️ Compares **String contents**, not references.

### Complexity

```text
O(n)
```

Worst case, where `n` is the number of characters compared.

---

## `equalsIgnoreCase()`

```java
String s1 = "Hello";
String s2 = "hello";

System.out.println(s1.equalsIgnoreCase(s2));
```

Output:

```text
true
```

➡️ Compares contents while ignoring case differences.

---

## `compareTo()`

```java
String s1 = "apple";
String s2 = "banana";

System.out.println(s1.compareTo(s2));
```

Conceptually:

```text
negative → s1 comes before s2
0        → both are equal
positive → s1 comes after s2
```

➡️ Performs lexicographical comparison.

### Important

Do **not** assume:

```text
negative = -1
positive = +1
```

The exact non-zero value can be different.

### Complexity

```text
O(min(n, m))
```

in the general case, where comparison stops at the first differing character.

---

# ④ 🔎 Searching Methods

## `contains()`

```java
String s = "Hello World";

System.out.println(s.contains("World"));
```

Output:

```text
true
```

### Syntax

```java
boolean contains(CharSequence sequence)
```

### Complexity

```text
O(n × m)   // general naive-style worst-case bound
```

where:

* `n` = main String length
* `m` = searched sequence length

⚠️ Don't write `O(1)` just because you're "searching a String."

---

## `indexOf()`

```java
String s = "banana";

System.out.println(s.indexOf('a'));
```

Output:

```text
1
```

➡️ Returns the first occurrence.

If not found:

```java
System.out.println(s.indexOf('x'));
```

Output:

```text
-1
```

### Complexity

```text
O(n)
```

for a single-character search.

---

## `lastIndexOf()`

```java
String s = "banana";

System.out.println(s.lastIndexOf('a'));
```

Output:

```text
5
```

➡️ Returns the last occurrence.

If not found:

```text
-1
```

---

## `startsWith()`

```java
String s = "Hello World";

System.out.println(s.startsWith("Hello"));
```

Output:

```text
true
```

---

## `endsWith()`

```java
String s = "Hello World";

System.out.println(s.endsWith("World"));
```

Output:

```text
true
```

---

# ⑤ ✂️ Extraction / Transformation

## `substring()`

### One argument

```java
String s = "Hello World";

System.out.println(s.substring(6));
```

Output:

```text
World
```

➡️ Starts from index `6` to the end.

---

### Two arguments

```java
System.out.println(s.substring(0, 5));
```

Output:

```text
Hello
```

### ⭐ Important

```java
substring(start, end)
```

➡️ `start` = inclusive
➡️ `end` = exclusive

```text
Hello World
012345678910

substring(0,5)
↓
01234
Hello
```

---

# `toUpperCase()`

```java
String s = "hello";

String result = s.toUpperCase();

System.out.println(result);
```

Output:

```text
HELLO
```

⚠️ Original String remains unchanged.

---

# `toLowerCase()`

```java
String s = "HELLO";

System.out.println(s.toLowerCase());
```

Output:

```text
hello
```

---

# `trim()`

```java
String s = "   Hello   ";

System.out.println(s.trim());
```

Output:

```text
Hello
```

➡️ Removes leading and trailing characters `<= U+0020` (ASCII control/space range).

⚠️ `trim()` is **not equivalent to removing all Unicode whitespace**.

---

# `strip()` ⭐ Java 11+

```java
String s = "   Hello   ";

System.out.println(s.strip());
```

➡️ Removes leading and trailing Unicode whitespace.

### Difference

```text
trim()
   ↓
older behavior, based on characters <= U+0020

strip()
   ↓
Unicode-aware whitespace
```

Also:

```java
stripLeading()
stripTrailing()
```

---

# `repeat()` ⭐ Java 11+

```java
String s = "Hi";

System.out.println(s.repeat(3));
```

Output:

```text
HiHiHi
```

### Syntax

```java
String repeat(int count)
```

⚠️ Negative count causes `IllegalArgumentException`.

---

# `replace()`

```java
String s = "banana";

System.out.println(s.replace('a', 'o'));
```

Output:

```text
bonono
```

It can also replace sequences:

```java
String s = "Java is good";

System.out.println(s.replace("good", "powerful"));
```

Output:

```text
Java is powerful
```

➡️ `replace()` treats the target as a **literal sequence**, not a regex.

---

# `replaceAll()`

```java
String s = "a1b2c3";

System.out.println(s.replaceAll("\\d", ""));
```

Output:

```text
abc
```

➡️ Uses **regular expressions**.

### ⭐ Important Interview Difference

```text
replace()
    ↓
Literal replacement

replaceAll()
    ↓
Regex-based replacement
```

Because regex processing is involved, `replaceAll()` can be more expensive than a simple literal replacement.

---

# `split()`

```java
String s = "Java,C++,Python";

String[] arr = s.split(",");

for(String x : arr)
    System.out.println(x);
```

Output:

```text
Java
C++
Python
```

### Syntax

```java
String[] split(String regex)
```

⚠️ `split()` uses **regular expressions**.

Therefore:

```java
"1.2.3".split(".")
```

does **not** mean split on literal `.` because `.` has special meaning in regex.

Use:

```java
"1.2.3".split("\\.")
```

---

# `String.join()`

```java
String result = String.join("-", "Java", "Python", "C++");

System.out.println(result);
```

Output:

```text
Java-Python-C++
```

Another example:

```java
List<String> list = List.of("A", "B", "C");

String result = String.join(",", list);
```

Result:

```text
A,B,C
```

---

# ⑥ 🔄 Conversion Methods

## `String.valueOf()`

Used to convert primitive values and objects into String representations.

```java
int n = 100;

String s = String.valueOf(n);

System.out.println(s);
```

Output:

```text
100
```

### Examples

```java
String.valueOf(10);
String.valueOf(10.5);
String.valueOf(true);
String.valueOf('A');
```

⭐ Important:

```java
String.valueOf(null)
```

returns:

```text
"null"
```

whereas:

```java
null.toString()
```

would throw `NullPointerException`.

---

# `getBytes()`

Converts a String into a byte array.

```java
String s = "ABC";

byte[] arr = s.getBytes();
```

Better:

```java
byte[] arr = s.getBytes(StandardCharsets.UTF_8);
```

⭐ Always prefer specifying the charset when encoding/decoding data.

---

# ⑦ 🚀 Advanced String Methods

## `intern()`

```java
String s1 = new String("Hello");

String s2 = s1.intern();
```

➡️ `intern()` returns the canonical representation of the String from the **String pool**.

Example:

```java
String s1 = new String("Hello");
String s2 = "Hello";

System.out.println(s1 == s2);          // false
System.out.println(s1.intern() == s2); // true
```

### ⭐ Interview Concept

```text
String Pool
     ↓
intern()
     ↓
returns pooled/canonical String reference
```

---

# `String.format()`

```java
String name = "Aditya";
int age = 20;

String result = String.format(
    "My name is %s and my age is %d",
    name,
    age
);

System.out.println(result);
```

Output:

```text
My name is Aditya and my age is 20
```

Common format specifiers:

```text
%s → String
%d → integer
%f → floating-point
%c → character
%b → boolean
```

---

# ⭐ 8. String Concatenation

You should also remember:

```java
String s = "Hello" + " World";
```

The `+` operator performs String concatenation when one operand is a String.

```java
String name = "Aditya";

String result = "Hello " + name;
```

For compile-time constants, the compiler can optimize concatenation.

For repeated concatenation inside loops, prefer:

```java
StringBuilder
```

Example:

❌ Potentially inefficient:

```java
String s = "";

for(int i = 0; i < 1000; i++) {
    s += i;
}
```

✅ Better:

```java
StringBuilder sb = new StringBuilder();

for(int i = 0; i < 1000; i++) {
    sb.append(i);
}

String s = sb.toString();
```

---

# 🧵 9. StringBuilder / StringBuffer

`String` is:

```text
Immutable
```

But:

```text
StringBuilder
StringBuffer
```

are:

```text
Mutable
```

They are useful when a String needs to be modified repeatedly.

---

# 🏗️ Hierarchy

Conceptually:

```text
                 AbstractStringBuilder
                         /       \
                        /         \
             StringBuilder     StringBuffer
```

Both classes are in:

```text
java.lang
```

### Thread Safety

```text
StringBuilder
     ↓
Not synchronized
     ↓
Generally faster
     ↓
Preferred for single-threaded String manipulation


StringBuffer
     ↓
Synchronized
     ↓
Thread-safe legacy mutable sequence
     ↓
Potentially more synchronization overhead
```

⚠️ Do not say "`StringBuffer` is always faster/slower" as an absolute rule. Performance depends on the workload, but synchronization can add overhead.

---

# 10. 🧠 Conceptual Internal Structure

A simplified conceptual view:

```java
class StringBuilder extends AbstractStringBuilder {
    // internal storage
    // current length
}
```

Modern Java implementations use internal byte/character storage details that can vary by JDK version, so don't memorize:

```java
byte[] values;
int count;
```

as a guaranteed public implementation.

For interview understanding, think:

```text
StringBuilder
      ↓
Internal resizable storage
      ↓
Characters/data
      ↓
Current length
      ↓
Capacity
```

---

# 11. 📦 Capacity vs Length

Suppose:

```java
StringBuilder sb = new StringBuilder("java");
```

Conceptually:

```text
Content:

j a v a
```

### `length()`

```java
System.out.println(sb.length());
```

Output:

```text
4
```

➡️ Number of actual characters.

### `capacity()`

```java
System.out.println(sb.capacity());
```

For a newly constructed builder from `"java"`, the capacity is implementation-defined according to the constructor's capacity rules; don't assume it is always exactly 16.

For the no-argument constructor:

```java
StringBuilder sb = new StringBuilder();

System.out.println(sb.capacity());
```

Traditionally:

```text
16
```

---

# ⭐ Length vs Capacity

```text
length()
   ↓
Actual characters currently stored

capacity()
   ↓
Amount of storage available before expansion
```

Example:

```java
StringBuilder sb = new StringBuilder();

sb.append("Hello");

System.out.println(sb.length());   // 5
System.out.println(sb.capacity()); // 16 initially
```

---

# 12. 📈 Capacity Growth

When capacity becomes insufficient, the builder expands its internal storage.

A commonly documented growth rule for `AbstractStringBuilder` is approximately:

```text
newCapacity = oldCapacity * 2 + 2
```

Example:

```text
old capacity = 16

new capacity = 16 × 2 + 2
             = 34
```

⚠️ Important:

Do not treat this as a universal immutable guarantee for every Java implementation/version. For interview purposes, **`2 × old capacity + 2` is the standard growth rule to know**.

---

# 13. 🛠️ StringBuilder / StringBuffer Methods

---

## `append()`

Adds data at the end.

```java
StringBuilder sb = new StringBuilder("Hello");

sb.append(" World");

System.out.println(sb);
```

Output:

```text
Hello World
```

---

## `insert()`

Inserts data at a particular index.

```java
StringBuilder sb = new StringBuilder("Helo");

sb.insert(2, 'l');

System.out.println(sb);
```

Output:

```text
Hello
```

---

## `delete()`

```java
StringBuilder sb = new StringBuilder("Hello World");

sb.delete(5, 11);

System.out.println(sb);
```

Output:

```text
Hello
```

⭐ Again:

```text
start → inclusive
end   → exclusive
```

---

## `deleteCharAt()`

```java
StringBuilder sb = new StringBuilder("Hello");

sb.deleteCharAt(1);

System.out.println(sb);
```

Output:

```text
Hllo
```

---

## `replace()`

```java
StringBuilder sb = new StringBuilder("Hello World");

sb.replace(6, 11, "Java");

System.out.println(sb);
```

Output:

```text
Hello Java
```

---

## `reverse()`

```java
StringBuilder sb = new StringBuilder("Hello");

sb.reverse();

System.out.println(sb);
```

Output:

```text
olleH
```

---

## `charAt()`

```java
StringBuilder sb = new StringBuilder("Hello");

System.out.println(sb.charAt(1));
```

Output:

```text
e
```

---

## `setCharAt()`

Unlike `String`, you can directly modify a character.

```java
StringBuilder sb = new StringBuilder("Hello");

sb.setCharAt(0, 'Y');

System.out.println(sb);
```

Output:

```text
Yello
```

⭐ This demonstrates **mutability**.

---

## `length()`

```java
StringBuilder sb = new StringBuilder("Hello");

System.out.println(sb.length());
```

Output:

```text
5
```

---

## `capacity()`

```java
StringBuilder sb = new StringBuilder();

System.out.println(sb.capacity());
```

Traditionally:

```text
16
```

---

## `ensureCapacity()`

Used to ensure that the builder has at least a specified capacity.

```java
StringBuilder sb = new StringBuilder();

sb.ensureCapacity(100);
```

➡️ Useful when you know approximately how much data will be appended.

This can reduce repeated resizing.

---

## `trimToSize()`

```java
StringBuilder sb = new StringBuilder();

sb.append("Hello");

sb.trimToSize();
```

➡️ Attempts to reduce capacity to the current size.

```text
Before:

length   = 5
capacity = 16

After trimToSize():

length   = 5
capacity ≈ 5
```

The exact implementation behavior should be understood as an optimization request, not as a reason to rely on a specific internal allocation detail.

---

# ⭐ 14. `equals()` in StringBuilder/StringBuffer

This is a **very important interview question**.

```java
StringBuilder sb1 = new StringBuilder("Hello");
StringBuilder sb2 = new StringBuilder("Hello");

System.out.println(sb1.equals(sb2));
```

Output:

```text
false
```

Why?

Because `StringBuilder` does **not** override `equals()` to compare character contents.

Therefore, `equals()` comes from `Object` and effectively compares references.

Same idea for `StringBuffer`.

### Compare their contents instead:

```java
sb1.toString().equals(sb2.toString());
```

Output:

```text
true
```

---

# ⭐ 15. String vs StringBuilder vs StringBuffer

| Feature                               | String              | StringBuilder          | StringBuffer                           |
| ------------------------------------- | ------------------- | ---------------------- | -------------------------------------- |
| Mutable                               | ❌ No                | ✅ Yes                  | ✅ Yes                                  |
| Thread-safe through synchronization   | N/A                 | ❌ No                   | ✅ Yes                                  |
| Content-based `equals()`              | ✅ Yes               | ❌ No                   | ❌ No                                   |
| Main use                              | Fixed text          | Frequent modifications | Mutable text with synchronized methods |
| Performance for repeated modification | Usually inefficient | Usually preferred      | Usually more overhead                  |
| Package                               | `java.lang`         | `java.lang`            | `java.lang`                            |

### ⭐ Easy way to remember

```text
String
   ↓
Immutable

StringBuilder
   ↓
Mutable + faster in typical single-threaded use

StringBuffer
   ↓
Mutable + synchronized
```

---

# ⭐ 16. Why is String Immutable?

This is one of the **most important Java interview questions**.

String immutability provides several benefits:

### 1. Security 🔐

Strings are commonly used for:

```text
file paths
URLs
class names
database connections
network resources
credentials/configuration data
```

If Strings could be changed after creation, shared references could create security problems.

---

### 2. String Pool Optimization

Because Strings cannot change:

```java
String a = "Hello";
String b = "Hello";
```

both can safely refer to the same pooled String object.

```text
       String Pool
           |
        "Hello"
        /     \
       a       b
```

If Strings were mutable, changing `a` could unexpectedly change `b`.

---

### 3. Hashing

Strings are frequently used as keys in:

```java
HashMap
HashSet
Hashtable
```

Their immutability means their content/hash relationship remains stable after insertion.

---

### 4. Thread Safety

Immutable objects can safely be shared between threads without synchronization for their state.

---

# ⭐ 17. String Method Complexity — Quick Revision

For interview preparation, remember the general cost rather than memorizing every implementation detail:

| Method               |                             Typical complexity |
| -------------------- | ---------------------------------------------: |
| `length()`           |                                           O(1) |
| `isEmpty()`          |                                           O(1) |
| `charAt()`           |                                           O(1) |
| `toCharArray()`      |                                           O(n) |
| `equals()`           |                                O(n) worst case |
| `equalsIgnoreCase()` |                                           O(n) |
| `compareTo()`        |                                    O(min(n,m)) |
| `contains()`         |                      O(n·m) general worst-case |
| `indexOf(char)`      |                                           O(n) |
| `lastIndexOf(char)`  |                                           O(n) |
| `startsWith()`       |                                           O(k) |
| `endsWith()`         |                                           O(k) |
| `substring()`        | O(k) if characters must be materialized/copied |
| `toUpperCase()`      |                                           O(n) |
| `toLowerCase()`      |                                           O(n) |
| `trim()` / `strip()` |                                           O(n) |
| `repeat(k)`          |                        O(n·k) output-sensitive |
| `replace()`          |                                   O(n) typical |
| `replaceAll()`       |                         Depends on regex/input |
| `split()`            |                         Depends on regex/input |
| `toCharArray()`      |                                           O(n) |
| `getBytes()`         |                                           O(n) |

Where:

```text
n = String length
m = searched String length
k = relevant prefix/suffix/output length
```

⚠️ **Interview note:** exact complexity can depend on the JDK implementation, charset, Unicode processing, and regex engine. Don't blindly memorize a single complexity for every overload.

---

# 🚨 18. Important Missing String Concepts

These are worth adding to your notes because they are frequently asked in interviews.

---

## ⭐ String Pool

```java
String s1 = "Hello";
String s2 = "Hello";
```

Usually:

```java
s1 == s2       // true
```

because both refer to the same pooled String.

But:

```java
String s3 = new String("Hello");

s1 == s3       // false
s1.equals(s3)  // true
```

### Remember:

```text
==      → reference comparison
equals  → content comparison
```

---

# ⭐ `==` vs `equals()`

```java
String a = new String("Java");
String b = new String("Java");

System.out.println(a == b);
System.out.println(a.equals(b));
```

Output:

```text
false
true
```

Because:

```text
==       → checks whether references point to same object
equals() → checks String content
```

---

# ⭐ StringBuilder → String

```java
StringBuilder sb = new StringBuilder("Hello");

String s = sb.toString();
```

➡️ Converts mutable builder contents into an immutable String.

---

# ⭐ String → StringBuilder

```java
String s = "Hello";

StringBuilder sb = new StringBuilder(s);
```

---

# ⭐ StringBuilder vs String concatenation

### Bad for many repeated modifications:

```java
String result = "";

for(int i = 0; i < 10000; i++) {
    result += i;
}
```

Each modification can create new String objects because String is immutable.

### Better:

```java
StringBuilder result = new StringBuilder();

for(int i = 0; i < 10000; i++) {
    result.append(i);
}
```

---

# 🎯 Important Interview Questions

## Basic

### Q1. What is a String in Java?

**Answer:**

> A String in Java is an immutable sequence of characters represented by the `java.lang.String` class. Once a String object is created, its contents cannot be changed.

---

### Q2. Is String a primitive data type?

**Answer:**

> No. String is a class in Java and therefore is a reference type, not a primitive data type.

---

### Q3. Why is String immutable?

**Answer:**

> String is immutable to provide security, enable String Pool sharing, maintain stable hash codes, and make String objects safely shareable between threads.

---

### Q4. What is the difference between `==` and `equals()` for String?

**Answer:**

> `==` compares object references, whereas `equals()` compares the contents of two String objects.

---

### Q5. What is String Pool?

**Answer:**

> The String Pool is a special pool of String objects maintained by the JVM to reuse identical String literals and reduce unnecessary object creation.

---

### Q6. What is the difference between:

```java
String s1 = "Java";
```

and

```java
String s2 = new String("Java");
```

**Answer:**

> The first statement uses the String Pool and reuses an existing pooled String if available. The second explicitly creates a new String object, while the literal `"Java"` may also exist in the String Pool.

---

### Q7. What is the difference between `isEmpty()` and `isBlank()`?

**Answer:**

> `isEmpty()` checks whether the String has zero characters, while `isBlank()` checks whether the String is empty or contains only Unicode whitespace characters.

---

### Q8. What is the difference between `trim()` and `strip()`?

**Answer:**

> `trim()` removes leading and trailing characters based on the older `<= U+0020` rule, whereas `strip()` uses Unicode-aware whitespace handling and was introduced in Java 11.

---

### Q9. What is the difference between `replace()` and `replaceAll()`?

**Answer:**

> `replace()` performs literal replacement, while `replaceAll()` treats its first argument as a regular expression and performs regex-based replacement.

---

### Q10. What is `intern()`?

**Answer:**

> `intern()` returns the canonical pooled representation of a String. If an equivalent String already exists in the String Pool, its reference is returned; otherwise, the String is added to the pool and that reference is returned.

---

# 🔥 StringBuilder / StringBuffer Interview Questions

### Q11. Why do we use StringBuilder?

**Answer:**

> StringBuilder is used when a String needs to be modified repeatedly because it is mutable and avoids creating a new String object for every modification.

---

### Q12. Difference between String and StringBuilder?

**Answer:**

> String is immutable, whereas StringBuilder is mutable. Therefore, StringBuilder is generally more suitable for repeated String modifications, especially in single-threaded code.

---

### Q13. Difference between StringBuilder and StringBuffer?

**Answer:**

> Both are mutable character sequences, but StringBuffer provides synchronized methods for thread-safe access, whereas StringBuilder is not synchronized and is generally preferred when external synchronization is unnecessary.

---

### Q14. Is StringBuilder thread-safe?

**Answer:**

> No. StringBuilder is not synchronized and should not be assumed to be thread-safe when shared and modified concurrently.

---

### Q15. Is StringBuffer thread-safe?

**Answer:**

> StringBuffer's methods are synchronized, providing thread-safe access to its individual operations.

---

### Q16. Does StringBuilder override `equals()`?

**Answer:**

> No. StringBuilder does not override `equals()` for content comparison, so its inherited `Object.equals()` compares object identity.

---

### Q17. How do you compare two StringBuilder objects by content?

**Answer:**

```java
StringBuilder sb1 = new StringBuilder("Hello");
StringBuilder sb2 = new StringBuilder("Hello");

System.out.println(sb1.toString().equals(sb2.toString()));
```

Output:

```text
true
```

---

### Q18. What is the difference between `length()` and `capacity()`?

**Answer:**

> `length()` returns the number of characters currently stored, whereas `capacity()` returns the amount of storage available before the builder needs to expand.

---

### Q19. What happens when StringBuilder capacity is exceeded?

**Answer:**

> StringBuilder expands its internal storage to accommodate additional data. The commonly documented growth rule is approximately `oldCapacity * 2 + 2`, subject to implementation constraints.

---

### Q20. Why is StringBuilder generally faster than StringBuffer?

**Answer:**

> StringBuilder is generally faster in single-threaded scenarios because its methods are not synchronized, whereas StringBuffer uses synchronization.

---

# 🧠 Final Revision Map

```text
                         STRING
                           |
             ┌─────────────┴─────────────┐
             |                           |
          Immutable                  String Pool
             |
     ┌───────┴────────┐
     |                |
  StringBuilder   StringBuffer
     |                |
  Mutable          Mutable
  Not synchronized Synchronized
     |
  append()
  insert()
  delete()
  replace()
  reverse()
  setCharAt()
  capacity()
  ensureCapacity()
  trimToSize()
```

### ⭐ Most important things to remember

```text
1. String is immutable.

2. StringBuilder and StringBuffer are mutable.

3. == compares references.

4. String.equals() compares contents.

5. StringBuilder.equals() does NOT compare contents.

6. String literals use the String Pool.

7. new String("Java") explicitly creates a new object.

8. substring(start, end)
       start → inclusive
       end   → exclusive

9. StringBuilder capacity ≠ length.

10. StringBuilder → preferred for frequent single-threaded modifications.

11. StringBuffer → synchronized mutable String class.

12. replace() → literal replacement.

13. replaceAll() → regex replacement.

14. split() → regex-based.

15. strip() → Unicode-aware whitespace.

16. intern() → canonical pooled String.

17. Always be careful with charset when converting
    between byte[] and String.

18. For repeated String concatenation in loops,
    prefer StringBuilder.
```

This version also fixes a few subtle inaccuracies in the original notes—especially the `char[]`/`byte[]` offset terminology, `equals()` behavior, StringBuilder capacity details, and the difference between `trim()` and `strip()`.
