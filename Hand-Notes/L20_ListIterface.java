//============= List Interface collection notes part-4 ==================

--> List concreate classes :-
                        [collection] -------> SequenceCollection
                            -----------------------|
                            |
                        [ List ] << interface >>
                            |
        ----------------------------------------------
        |                   |                       |
    ArrayList           LinkedList                Vector
                                                    | 
                                                   Stack (extends vector)

--> Properties :-
        -   Element have position.
        -   Duplicate are allowed
        -   Insertion order is preserved.
        -   you can access element by Index.

--> SequenceCollection have :-
    introduce after java 21,
        - getFirst()
        - getLast()
        - addFirst()
        - addLast()
        - removeFirst()
        - removeLast()

--> List interface specific methods :-
        
        1. E get(index) :-
        2. set(index,E value) :- set element at index to new value. (change value only)
        3. boolean add(index, E value) :- add new element at index. ( add new element)
        4. boolean addAll(index,Collection< ? extends E>) :-
        5. boolean remove(index) :-
        6. indexOf(Object o) :-
        7. lastIndexOf(Object o) :-
        8. listIterator() :- have extra methor from Iterator().
                |----> insert()
                |----> remove()
                |----> hasPrevious()
                |----> previous()
                |----> previousIndex()
                |----> hasNext()
                |----> next()

        9. listIterator(int index) :- iterator start at given index.
        10. of() :- to create immutable list.
                List<Integer> l = list.of(1,2,3);
                l.add(8); // error bcz L is immutable.
        11. Copyof() :- copy one list in another immutable list.

⭕ ArrayList :-
    --> it is dynamic array.
    --> if array size get filled it increase by 1.5x
            => new capacity = old capacity + (old capacity)/2

    --> randome access
    --> cache friendly
    --> simple structure

        constructor :-
                1. no-argument = new ArrayList<>()
                2. intial capacity = new ArrayList<>(15);
                3. collection = new ArrayList<>(other collection)

    --> get(i) = O(1)
    --> set(i) = O(1)
    --> add(i,0) = O(n)
    --> remove(i) = O(n)

    ⭐⭐ ArrayList specific method :- method are provided by ArrayList

            1. ensureCapacity() :-
            2. trimToSize() :-

⭕ LinkedList :-
        --> internally used Node class to form linked list.
        
        --> operations :-  slower than ArrayList therefor LinkedList not used much.
                                get(index) - O(n)
                                add() - O(n)
                                remove() - O(n)
                                addAll() - O(n)

⭕ Vector/ stack :- legagcy classes & thread safe and comparatively slow.

        - vector -> dynamic array
        - Hashtable -> hashmap
        - Stack -> Stack implementation using array.


