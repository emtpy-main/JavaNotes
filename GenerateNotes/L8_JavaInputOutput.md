# Java Standard Input/Output (I/O)

---

## 1. Overview of Java I/O

**I/O (Input/Output)** refers to the process of transferring data **into and out of a Java program**.

* **Input** → Data coming **into** the program.
* **Output** → Data going **out of** the program.

```text
        INPUT                         OUTPUT
          ↓                             ↑
Keyboard / File / Network        Console / File / Network
          ↓                             ↑
      Java Program  ────────────────────
```

### Types of I/O

Java I/O can broadly involve:

1. **Console I/O** → Communication with the terminal/console.
2. **File I/O** → Reading/writing files.
3. **Network I/O** → Sending/receiving data over a network.
4. **Memory I/O** → Reading/writing data from memory-based sources such as byte/character arrays.

> 📌 **In this section:** We focus primarily on **Console I/O**.

---

# 2. Standard Output in Java

The most commonly used statement for console output is:

```java
System.out.println("Hello Java");
```

Let's understand what is actually happening here.

---

## 3. Internal Structure of `System.out.println()`

Break it into three parts:

```java
System.out.println("Hello Java");
│      │       │
│      │       └── method
│      └────────── field/reference
└───────────────── class
```

### `System`

`System` is a class from the `java.lang` package.

```java
java.lang.System
```

We don't need to explicitly import it because **`java.lang` is automatically imported into every Java source file**.

Conceptually:

```java
import java.lang.*;
```

is automatically available.

---

### `out`

`out` is a **static field** of the `System` class.

Its declared type is:

```java
PrintStream
```

Conceptually:

```java
class System {
    public static final PrintStream out;
    public static final PrintStream err;
    public static final InputStream in;
}
```

Therefore:

```java
System.out
```

refers to a `PrintStream` object representing the **standard output stream**.

### Important correction

Your note said:

> `out must be object`

More precisely:

> `out` is a `static final` reference variable whose type is `PrintStream` and which refers to the standard output stream.

Because it is `static`, we access it through the class:

```java
System.out
```

rather than through a `System` object.

---

### `println()`

`println()` is a **method of the `PrintStream` class**.

Therefore:

```java
System.out.println("Hello");
```

can conceptually be understood as:

```text
System
  ↓
out
  ↓
PrintStream object
  ↓
println()
```

### `PrintStream` commonly provides

| Method      | Purpose                                           |
| ----------- | ------------------------------------------------- |
| `print()`   | Prints without automatically moving to a new line |
| `println()` | Prints and then moves to the next line            |
| `printf()`  | Prints formatted output                           |

Example:

```java
System.out.print("Hello ");
System.out.print("Java");
```

Output:

```text
Hello Java
```

```java
System.out.println("Hello");
System.out.println("Java");
```

Output:

```text
Hello
Java
```

```java
System.out.printf("Age = %d", 23);
```

Output:

```text
Age = 23
```

---

## 4. Does `println()` Accept Only String?

❌ Your original note:

> `println() take String argument only if not converted.`

This is not correct.

`PrintStream` provides **overloaded `println()` methods** for many data types.

For example:

```java
System.out.println(10);
System.out.println(10.5);
System.out.println(true);
System.out.println('A');
System.out.println("Hello");
```

It also accepts objects:

```java
System.out.println(obj);
```

For an object, Java ultimately uses its string representation, typically through `String.valueOf()` / the object's `toString()` behavior.

### Important interview point

```java
println()
```

is **overloaded**, not restricted to `String`.

---

# 5. `System.err`

`System.err` represents the **standard error stream**.

Its type is also:

```java
PrintStream
```

Example:

```java
System.err.println("Something went wrong!");
```

Conceptually:

```text
System
 ├── out → standard output
 ├── err → standard error
 └── in  → standard input
```

### `out` vs `err`

| Feature | `System.out`           | `System.err`            |
| ------- | ---------------------- | ----------------------- |
| Purpose | Normal output          | Error/diagnostic output |
| Type    | `PrintStream`          | `PrintStream`           |
| Example | `System.out.println()` | `System.err.println()`  |
| Stream  | Standard output        | Standard error          |

> ⚠️ `System.err` is not necessarily only for exceptions. It is a separate stream conventionally used for errors and diagnostic messages.

---

# 6. Standard Input — `System.in`

Now let's understand how Java receives input from the user.

Java provides:

```java
System.in
```

`System.in` represents the **standard input stream**, normally connected to the console/keyboard.

Its declared type is:

```java
InputStream
```

Conceptually:

```java
class System {
    public static final InputStream in;
    public static final PrintStream out;
    public static final PrintStream err;
}
```

So:

```java
System.in
```

is an `InputStream` reference.

---

# 7. What is a Stream?

A **stream** can be thought of as a flow of data between a source and a destination.

```text
Source  ───────────→  Java Program
          Input
```

```text
Java Program  ─────→  Destination
           Output
```

For example:

```text
Keyboard → InputStream → Java Program
Java Program → OutputStream → Console
```

### Important distinction

Your note says:

> Java I/O is stream based and all of them are streams of bytes.

The first part is broadly correct, but the second part needs correction.

Java has **two major stream families**:

```text
                    Java I/O
                       │
             ┌─────────┴─────────┐
             │                   │
        Byte Streams       Character Streams
             │                   │
      InputStream          Reader
      OutputStream         Writer
```

### Byte streams

Used for handling raw binary data.

```java
InputStream
OutputStream
```

Examples:

* images
* audio
* video
* binary files

### Character streams

Designed for text/character data.

```java
Reader
Writer
```

Examples:

* text files
* user-entered text
* character-based data

> ⭐ **Interview point:** `InputStream`/`OutputStream` work primarily with **bytes**, while `Reader`/`Writer` work with **characters**.

---

# 8. `InputStream` and `OutputStream`

These are important abstract classes in Java's I/O hierarchy.

### `InputStream`

Used for reading byte-oriented data.

Important method:

```java
int read()
```

### `OutputStream`

Used for writing byte-oriented data.

Important method:

```java
void write(...)
```

Conceptually:

```text
Input
  ↓
InputStream
  ↓
read()
  ↓
Java Program
```

and:

```text
Java Program
  ↓
OutputStream
  ↓
write()
  ↓
Output
```

`InputStream` and `OutputStream` define common behavior; concrete subclasses provide specific implementations.

---

# 9. Important Stream Hierarchy

You don't need to memorize the entire hierarchy initially, but understand the major classes.

### Input side

```text
InputStream
│
├── FileInputStream
├── ByteArrayInputStream
├── BufferedInputStream
└── DataInputStream
```

### Output side

```text
OutputStream
│
├── FileOutputStream
├── ByteArrayOutputStream
├── BufferedOutputStream
└── PrintStream
```

### Character side

```text
Reader
│
├── InputStreamReader
├── BufferedReader
└── FileReader
```

```text
Writer
│
├── OutputStreamWriter
├── BufferedWriter
└── FileWriter
```

> ⚠️ **Important correction:** `System.in` has declared type `InputStream`. You should not assume that its concrete implementation is always `BufferedInputStream`. The actual underlying implementation is handled by the Java runtime/environment.

---

# 10. Reading Input Using `System.in.read()`

The simplest low-level way of reading console input is:

```java
int x = System.in.read();
```

### What does `read()` do?

`InputStream.read()` reads **one byte** and returns it as an `int`.

Example:

```java
int x = System.in.read();

System.out.println(x);
System.out.println((char) x);
```

Suppose the user enters:

```text
A
```

The ASCII/UTF-8 byte representation for basic character `A` is:

```text
65
```

So:

```java
System.out.println(x);
```

may produce:

```text
65
```

while:

```java
System.out.println((char)x);
```

produces:

```text
A
```

### Why does `read()` return `int` instead of `byte`?

Because it needs a special value:

```java
-1
```

to indicate **end of stream (EOF)**.

So the return value conceptually is:

```text
0–255 → byte value
-1    → end of stream
```

⭐ **Very important interview question.**

---

# 11. Input Buffer

Suppose the user enters:

```text
Aditya
```

followed by Enter.

The input can conceptually be represented as:

```text
[A][d][i][t][y][a][newline]
```

For basic ASCII characters:

```text
[65][100][105][116][121][97][...]
```

When:

```java
System.in.read();
```

is called, only **one byte is consumed by that particular call**.

The remaining input stays available in the input system/buffer until it is read.

### Example

```java
int x = System.in.read();

System.out.println((char)x);
```

If the input begins with:

```text
Aditya
```

the first character read is:

```text
A
```

The remaining characters are still available for subsequent reads.

---

# 12. Problem with `System.in.read()`

Reading an entire line character-by-character would require code such as:

```java
String s = "";
int c;

while ((c = System.in.read()) != '\n') {
    s += (char)c;
}
```

This approach has several problems:

* verbose
* difficult to use
* handles characters manually
* doesn't conveniently provide line-oriented input
* repeated `String` concatenation can be inefficient

Therefore, Java provides **character-oriented reader classes**.

---

# 13. `Reader` — Character Stream

`Reader` is an **abstract class** designed for reading character data.

```text
Reader
│
├── BufferedReader
├── InputStreamReader
└── FileReader
```

Unlike:

```java
InputStream
```

which works with bytes, `Reader` works with characters.

### Main idea

```text
InputStream
    ↓
    bytes

Reader
    ↓
    characters
```

---

# 14. `InputStreamReader`

This is one of the most important classes for understanding Java I/O.

`InputStreamReader` acts as a **bridge between byte streams and character streams**.

```text
InputStream
   │
   │ bytes
   ↓
InputStreamReader
   │
   │ characters
   ↓
Reader
```

Example:

```java
InputStreamReader isr =
        new InputStreamReader(System.in);
```

Here:

```text
System.in
   ↓
InputStreamReader
   ↓
characters
```

`InputStreamReader` also performs **character decoding** using a character encoding/charset.

For example:

```java
InputStreamReader isr =
        new InputStreamReader(System.in);
```

uses the appropriate default charset configuration.

You can explicitly specify one when required:

```java
InputStreamReader isr =
        new InputStreamReader(System.in, StandardCharsets.UTF_8);
```

---

# 15. `BufferedReader`

`BufferedReader` is a character-buffering class.

It wraps a `Reader`:

```java
BufferedReader br =
        new BufferedReader(new InputStreamReader(System.in));
```

Notice the relationship:

```text
System.in
   ↓
InputStream
   ↓
InputStreamReader
   ↓
Reader
   ↓
BufferedReader
```

### Why use buffering?

Without buffering, many small read operations can cause more interaction with the underlying stream.

Buffering allows a chunk of data to be read and temporarily stored in memory.

```text
Keyboard
   ↓
OS / underlying input
   ↓
InputStreamReader
   ↓
Java buffer
   ↓
Program
```

Conceptually:

1. Data is obtained from the underlying input source.
2. Data is stored in a buffer.
3. The program consumes data from the buffer as required.
4. When necessary, another chunk is fetched.

### Benefits

* reduces frequent underlying I/O operations
* improves efficiency
* provides convenient methods such as `readLine()`

---

# 16. Important Correction — `BufferedReader` and `InputStream`

Your original note says:

> BufferedReader are directly compatible to InputStream.

❌ Not directly.

`BufferedReader` expects a **`Reader`**, not an `InputStream`.

Therefore this is correct:

```java
BufferedReader br =
    new BufferedReader(
        new InputStreamReader(System.in)
    );
```

Because:

```text
System.in
InputStream
   ↓
InputStreamReader
Reader
   ↓
BufferedReader
Reader-based buffering
```

---

# 17. Reading a Complete Line

Now we can simply write:

```java
BufferedReader br =
        new BufferedReader(new InputStreamReader(System.in));

String name = br.readLine();

System.out.println(name);
```

If input is:

```text
Aditya
```

then:

```java
name
```

contains:

```text
"Aditya"
```

### `readLine()`

```java
br.readLine();
```

reads a complete line of text.

It returns:

```java
String
```

or:

```java
null
```

when the end of the stream is reached.

---

# 18. Complete Input Flow

Suppose the user enters:

```text
Aditya
```

Conceptually:

```text
1. User enters:
       Aditya
          ↓
2. Operating system / console input
          ↓
3. System.in
   InputStream
          ↓
4. InputStreamReader
   bytes → characters
          ↓
5. BufferedReader
   buffering + readLine()
          ↓
6. String name
       "Aditya"
          ↓
7. System.out.println(name)
          ↓
8. Console
```

### ⭐ Remember this chain

```text
System.in
   ↓
InputStreamReader
   ↓
BufferedReader
   ↓
readLine()
```

---

# 19. Limitation of `BufferedReader`

`BufferedReader` is excellent for reading **text**, but it doesn't directly provide methods such as:

```java
readInt()
readDouble()
readBoolean()
```

For example:

```java
String s = br.readLine();
int i = Integer.parseInt(s);
```

For a double:

```java
double d = Double.parseDouble(br.readLine());
```

For a boolean:

```java
boolean b = Boolean.parseBoolean(br.readLine());
```

So you need **manual parsing/conversion**.

---

# 20. Scanner Class

To make console input easier, Java provides the:

```java
Scanner
```

class.

It was introduced in **Java 5 (JDK 1.5)**.

Import:

```java
import java.util.Scanner;
```

Unlike the classes discussed above, `Scanner` belongs to:

```text
java.util
```

not:

```text
java.io
```

---

# 21. Basic Scanner Usage

```java
Scanner sc = new Scanner(System.in);

String name = sc.nextLine();

System.out.println(name);
```

For an integer:

```java
int age = sc.nextInt();
```

For a double:

```java
double salary = sc.nextDouble();
```

For a boolean:

```java
boolean value = sc.nextBoolean();
```

---

# 22. Scanner Methods

| Method          | Purpose                                     |
| --------------- | ------------------------------------------- |
| `next()`        | Reads the next token                        |
| `nextLine()`    | Reads the entire current line               |
| `nextInt()`     | Reads an integer                            |
| `nextDouble()`  | Reads a double                              |
| `nextFloat()`   | Reads a float                               |
| `nextLong()`    | Reads a long                                |
| `nextBoolean()` | Reads a boolean                             |
| `hasNext()`     | Checks whether another token exists         |
| `hasNextInt()`  | Checks whether the next token is an integer |

### ⚠️ Important

It is:

```java
nextLine()
```

not:

```java
nextline()
```

Java method names are **case-sensitive**.

---

# 23. Scanner and Tokenization

`Scanner` works by dividing input into **tokens** using delimiters.

By default, whitespace acts as a delimiter.

Suppose:

```text
Hello I am Pratik
```

Tokens are approximately:

```text
Hello
I
am
Pratik
```

Therefore:

```java
Scanner sc = new Scanner(System.in);

String s1 = sc.next();
String s2 = sc.next();
```

would read:

```text
s1 = "Hello"
s2 = "I"
```

while:

```java
String line = sc.nextLine();
```

reads the entire line.

---

# 24. Scanner Can Read From Different Sources

One useful feature of `Scanner` is that it can tokenize data from different sources.

### Keyboard

```java
Scanner sc = new Scanner(System.in);
```

### File

```java
Scanner sc = new Scanner(new File("data.txt"));
```

### String

```java
Scanner sc = new Scanner("10 20 30");
```

Then:

```java
System.out.println(sc.nextInt());
```

produces:

```text
10
```

---

# 25. Why is Scanner Slower?

`Scanner` is convenient, but it is generally slower than `BufferedReader` for high-volume input.

One reason is that `Scanner` performs additional processing such as:

```text
Input
  ↓
Tokenization
  ↓
Pattern/delimiter processing
  ↓
Type conversion
  ↓
Result
```

For example:

```java
int x = sc.nextInt();
```

does more work than simply reading raw characters/bytes.

### Therefore

| Feature                       | Scanner            | BufferedReader              |
| ----------------------------- | ------------------ | --------------------------- |
| Package                       | `java.util`        | `java.io`                   |
| Main purpose                  | Convenient parsing | Efficient character reading |
| Reads lines                   | Yes                | Yes                         |
| `nextInt()`                   | Yes                | No                          |
| `nextDouble()`                | Yes                | No                          |
| Tokenization                  | Yes                | No                          |
| Parsing convenience           | High               | Lower                       |
| Usually faster for huge input | ❌                  | ✅                           |
| Beginner friendly             | ✅                  | Moderate                    |

> ⭐ **Competitive programming:** `BufferedReader` is often preferred when input size is large and performance matters.
> ⭐ **Normal applications / learning:** `Scanner` is often convenient and readable.

---

# 26. Very Important `Scanner` Trap

One of the most frequently asked Java interview/coding questions:

```java
Scanner sc = new Scanner(System.in);

int age = sc.nextInt();
String name = sc.nextLine();

System.out.println(name);
```

Suppose input is:

```text
23
Pratik
```

You may expect:

```text
Pratik
```

but `nextLine()` can immediately consume the **remaining newline** after `23`, resulting in an empty string.

### Why?

```text
23\n
```

`nextInt()` consumes:

```text
23
```

but the newline remains.

Then:

```java
nextLine()
```

consumes the remaining newline.

### Common solution

```java
int age = sc.nextInt();
sc.nextLine(); // consume leftover newline

String name = sc.nextLine();
```

⭐ **Very common interview/coding question.**

---

# 27. Important Correction About "Java Was Not Designed for Console"

Your original note says Java was initially meant for server-side/GUI programming and therefore did not provide console input.

This is better **not to memorize**.

A more accurate way to think about it is:

> Java provides low-level I/O through the `java.io` APIs, including `System.in`, `System.out`, streams, readers, and writers. Higher-level convenience classes such as `Scanner` were added later to make common input tasks easier.

So don't use the historical explanation as a technical reason for why `Scanner` exists.

---

# 28. Complete Java Console I/O Picture

```text
                         JAVA CONSOLE I/O
                                │
                 ┌──────────────┴──────────────┐
                 │                             │
               INPUT                         OUTPUT
                 │                             │
            System.in                      System.out
                 │                             │
          InputStream                      PrintStream
                 │                             │
       ┌─────────┴─────────┐          ┌────────┼────────┐
       │                   │          │        │        │
    read()          InputStreamReader print() println() printf()
                           │
                        Reader
                           │
                    BufferedReader
                           │
                       readLine()
                           
                 OR

                 System.in
                     │
                  Scanner
                     │
          ┌──────────┼──────────┐
        next()   nextLine()  nextInt()
```

---

# 29. `System.in`, `System.out`, `System.err` — Must Remember

This is one of the most important things from this topic.

```java
System.in
System.out
System.err
```

| Component    | Type          | Purpose         |
| ------------ | ------------- | --------------- |
| `System.in`  | `InputStream` | Standard input  |
| `System.out` | `PrintStream` | Standard output |
| `System.err` | `PrintStream` | Standard error  |

Conceptually:

```text
                 System
                   │
       ┌───────────┼───────────┐
       ↓           ↓           ↓
    in            out         err
       ↓           ↓           ↓
 InputStream  PrintStream  PrintStream
       ↓           ↓           ↓
    Input       Output      Errors
```

---

# 30. Byte Stream vs Character Stream

⭐ **Extremely important for interviews.**

| Feature      | Byte Stream                   | Character Stream                               |
| ------------ | ----------------------------- | ---------------------------------------------- |
| Base classes | `InputStream`, `OutputStream` | `Reader`, `Writer`                             |
| Unit         | Bytes                         | Characters                                     |
| Suitable for | Binary data                   | Text data                                      |
| Examples     | Images, audio, binary files   | Text files                                     |
| Conversion   | —                             | `InputStreamReader` bridges bytes → characters |

Remember:

```text
InputStream  → bytes
Reader       → characters

OutputStream → bytes
Writer       → characters
```

---

# 31. `InputStreamReader` vs `BufferedReader`

This distinction is frequently misunderstood.

### `InputStreamReader`

**Converts/decodes bytes into characters.**

```text
bytes → characters
```

### `BufferedReader`

**Buffers characters and provides convenient character-reading operations such as `readLine()`.**

```text
characters → buffer → application
```

Together:

```java
BufferedReader br =
    new BufferedReader(
        new InputStreamReader(System.in)
    );
```

Think:

```text
System.in
   ↓
InputStreamReader
"Convert bytes → characters"
   ↓
BufferedReader
"Buffer characters + readLine()"
```

---

# 32. Quick Revision Sheet 🧠

### Standard streams

```java
System.in
System.out
System.err
```

### Their types

```text
System.in  → InputStream
System.out → PrintStream
System.err → PrintStream
```

### Byte streams

```text
InputStream
OutputStream
```

### Character streams

```text
Reader
Writer
```

### Byte → Character bridge

```text
InputStreamReader
```

### Buffered character input

```text
BufferedReader
```

### Easy user input

```text
Scanner
```

### Scanner package

```java
java.util.Scanner
```

### Scanner common methods

```java
next()
nextLine()
nextInt()
nextDouble()
nextBoolean()
```

### BufferedReader common method

```java
readLine()
```

### Low-level byte reading

```java
System.in.read()
```

### Output

```java
System.out.print()
System.out.println()
System.out.printf()
```

---

# 🎯 Important Interview Questions & Answers

## 🟢 Basic Level

### 1. What is I/O in Java?

**Answer:**
I/O, or Input/Output, is the mechanism through which a Java program communicates with external sources and destinations, such as the console, files, memory, and network. Input transfers data into the program, while output transfers data from the program.

---

### 2. What is `System.out.println()`?

**Answer:**
`System.out.println()` is used to print data to the standard output stream, normally the console, followed by a line terminator. `System` is a class, `out` is a static `PrintStream` reference, and `println()` is a method of `PrintStream`.

---

### 3. What is `System.in`?

**Answer:**
`System.in` is a static field of the `System` class that represents the standard input stream. Its declared type is `InputStream` and it is normally connected to the console input.

---

### 4. What is `System.err`?

**Answer:**
`System.err` represents the standard error stream. It is a `PrintStream` used conventionally for error and diagnostic messages.

---

### 5. Why can we use `System` without importing it?

**Answer:**
`System` belongs to the `java.lang` package, and Java automatically makes the classes of `java.lang` available to every Java source file.

---

# 🟡 Intermediate Questions

### 6. What is a stream in Java?

**Answer:**
A stream represents a flow of data between a source and a destination. Java provides byte-oriented streams through `InputStream` and `OutputStream`, and character-oriented streams through `Reader` and `Writer`.

---

### 7. What is the difference between `InputStream` and `Reader`?

**Answer:**
`InputStream` is used for reading byte-oriented data, whereas `Reader` is used for reading character-oriented data. `InputStream` is commonly suitable for binary data, while `Reader` is designed for text data.

---

### 8. What is `InputStreamReader`?

**Answer:**
`InputStreamReader` is a bridge between byte streams and character streams. It reads bytes from an `InputStream` and decodes them into characters using a character encoding.

---

### 9. Why do we use `BufferedReader`?

**Answer:**
`BufferedReader` buffers character input to reduce frequent interactions with the underlying input source and provides convenient methods such as `readLine()` for reading complete lines.

---

### 10. Can we directly pass `System.in` to `BufferedReader`?

**Answer:**
No. `BufferedReader` requires a `Reader`, while `System.in` is an `InputStream`. Therefore, we use `InputStreamReader` as a bridge:

```java
BufferedReader br =
    new BufferedReader(
        new InputStreamReader(System.in)
    );
```

---

### 11. Why does `InputStream.read()` return `int` instead of `byte`?

**Answer:**
`read()` returns an `int` so that it can represent all possible byte values and also use `-1` to indicate the end of the input stream.

---

### 12. What is the difference between `print()` and `println()`?

**Answer:**
`print()` writes the output without automatically adding a line terminator, while `println()` writes the output and then moves to the next line.

---

# 🔴 SDE Interview Questions

### 13. Why is `Scanner` slower than `BufferedReader`?

**Answer:**
`Scanner` performs additional processing such as tokenization, delimiter/pattern processing, and type conversion. `BufferedReader` primarily focuses on efficient buffered character reading, so it generally has lower overhead for large amounts of input.

---

### 14. Why was `InputStreamReader` required in this code?

```java
new BufferedReader(
    new InputStreamReader(System.in)
);
```

**Answer:**
`System.in` provides byte-oriented input through `InputStream`, while `BufferedReader` works with character-oriented `Reader` objects. `InputStreamReader` converts the byte stream into a character stream, allowing `BufferedReader` to process it.

---

### 15. Explain the complete flow of this statement:

```java
BufferedReader br =
    new BufferedReader(
        new InputStreamReader(System.in)
    );
```

**Answer:**

```text
System.in
    ↓
InputStream
    ↓
InputStreamReader
    ↓
Reader
    ↓
BufferedReader
```

`System.in` provides byte input. `InputStreamReader` decodes those bytes into characters, and `BufferedReader` buffers the characters and provides convenient methods such as `readLine()`.

---

### 16. Why does `Scanner.nextInt()` followed by `nextLine()` sometimes return an empty string?

**Answer:**
`nextInt()` reads the integer but does not consume the line terminator that follows it. When `nextLine()` is called immediately afterward, it consumes that remaining line terminator and therefore may return an empty string.

Solution:

```java
int age = sc.nextInt();
sc.nextLine();

String name = sc.nextLine();
```

---

### 17. Is `System.out` an object or a method?

**Answer:**
`System.out` is not a method. `out` is a `static final` field of the `System` class whose type is `PrintStream` and which refers to the standard output stream.

---

### 18. Is `println()` overloaded?

**Answer:**
Yes. `PrintStream` provides overloaded `println()` methods for different data types such as `int`, `long`, `float`, `double`, `boolean`, `char`, `String`, and `Object`.

---

### 19. Is `Scanner` a part of `java.io`?

**Answer:**
No. `Scanner` belongs to the `java.util` package.

```java
import java.util.Scanner;
```

It is a utility class designed to make input parsing convenient.

---

### 20. What is the difference between `next()` and `nextLine()`?

**Answer:**

`next()` reads the **next token**, stopping at whitespace, whereas `nextLine()` reads the **remaining characters of the current line**.

For input:

```text
Hello Java World
```

```java
sc.next();
```

returns:

```text
Hello
```

while:

```java
sc.nextLine();
```

can return:

```text
Hello Java World
```

---

# ⭐ Most Important Things to Remember

If you revise this topic before an interview, remember these **10 points**:

```text
1. System is a class.
2. System.in is an InputStream.
3. System.out is a PrintStream.
4. System.err is a PrintStream.
5. InputStream/OutputStream → byte streams.
6. Reader/Writer → character streams.
7. InputStreamReader → byte → character bridge.
8. BufferedReader → buffering + readLine().
9. Scanner → convenient token-based/type-specific input.
10. Scanner generally has more overhead than BufferedReader.
```

And the most important flow:

```text
                 INPUT
                   │
             System.in
                   │
             InputStream
                   │
        InputStreamReader
          bytes → chars
                   │
           BufferedReader
          buffering/readLine
                   │
             Java Program


                 OUTPUT
                   │
             Java Program
                   │
             System.out
                   │
             PrintStream
                   │
        print / println / printf
                   │
                Console
```

This gives you a much stronger foundation for the next topics: **File I/O, `FileInputStream`/`FileOutputStream`, `FileReader`/`FileWriter`, buffering, serialization, and NIO (`Path`, `Files`, `Channels`, `Buffers`)**.
