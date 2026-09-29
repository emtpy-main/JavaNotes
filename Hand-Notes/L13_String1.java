// ================ String part-1 in java ===============================

-> Character are human readible symbol like 'A','a','b'................,'1','#'.
-> string are sequence of Character i.e Char[].
    in program :-   ['a','b','c'] -> [97,98,99];
-> we need string for various method & properties which can not possible in Char[].

-> string comes under java.lang.String.

-> String are immutable Class.
    -> why java keeps string as immutable bcz many things are stores in string format like password,URL,Hashes if they are change whole meaning program crashed.

-> we can declare string using two method
                                    /  \
                                   /    \
                            Literal     new

⭕ String Literal (String Pool):-
    -> it help in optimization suppose application can contain million of string then for each string creating new object in heap can crashs memory.

    -> ⭐ : String Pool is separate memory section in heap which stores string literal.
            -> if two string literal have same values does not create separate object.
                    String s1 = "Hello";
                    String s2 = "Hello";
                    print(s1==s2); // => true;
            -> both object s1 & s2 are point to same object in string pool in because they have same values.

⭕ String using new keyword :-
    -> create every time new object in heap whenever assigned or modified.
        Eg: String s1 = new String("Hello");
            String s2 = new String("Hello");
            print(s1==s2); // => False 
    -> both object s1 & s2 are point to different object in heap.

⭕ Golden Rule :-

    1. Only Compiles time constants go to String Pool automatically.
            Eg :-
                    String s1 = "Ja" + "va";
                    String s2 = "Java";
                    -> print(s1 == s2) ; // => true;
                    -> Both s1 are create in String pool bcz it is compile time.

                    String s3 = s2;  // s3 in string pool bcz assignment operator resolve at compile time.

    2. Runtime Created String go to heap.
            Eg:-
                    String s1 = "Ja";
                    String s2 = s1 + "va";
                    String s3 = "Java";
                    -> print(s2==s3); // -> false s3 in string pool , s2 in heap

                    -> s2 object are created in Heap Memory bcz s1 + "va" is decide at runtime.
                    -> but "va" is string literal in string pool with no reference variable.

            ⭐ String s4 = new String("Pratik");
                    here, "Pratik" is lateral object create at String pool with no reference. then s4 object is created at heap with new keyword.


⭕ Problem of Immutability :-
        Eg :-
                String s = "";
                for(int i=0; i<5; i++){
                    s = s + i;
                    System.out.println(s);
                }   

        -> each time new object are created
        -> "" in string pool.
        -> "0","01","012","0123","01234" object are created in Heap.
        -> at list s point to "01234".

    -> to solve this java introduce StringBuilder & StringBuffer.

⭕ Why java change Char[] --> byte[] :-

    -> Intially java stores string in char array but after java 9, ditch char array use byte array now.

    -> Why :-
        -> Character consumes 2 byte to store 1 char.
                Char[] values = [J|a|v|a]; // used 8 byte
            -> char[] can stores unicode utf-16 Character like hindi Character
        
        -> most of time we deal with ASCII Character which can stores with 1 Byte, therefore to reduce unnecessary memory java used byte[].
                byte[] values = [J|a|v|a]; // used 4 byte 💯 50% memory reduced.

⭕ String class internals :-
        Eg :-
            public final class String {
                public final byte[] values;
                public final byte coder;
                private int hash;
            }
        
        -> if we stores hindi character then 'byte coder' help compiler to tell that read two char (2 byte) of values.
                                byte coder;
                                     / \
                                    /   \
                            1 byte[0]    [1] 2 byte
                                Latin1    UTF 16 
                        read 1 char       read 2 char at a time.

        -> hash field are used to store hashCode. it is mutable field bcz hash computation are heavy to avoid it hash values are 'Cached'.

⭕ String class optimization :-
        |-> string pool
        |-> char[] --> byte[]
        |-> caching the hashValues