//=================== Collection Interface collection notes part-3 ==============

-- it parent interface for all collection interfaces like List, queue & set.

--> so,  Collection<Integer> c = new ArrayList();
                                 new LinkedList();
                                 new HashSet();
                                 new ArrayDeque();

--> Collection interface contain all common methods that can be applied on every collection(DS).

⭐⭐ Collection vs Collections 
        |               |----------> Class (utility)
    interface

⭕ Methods :-
    Eg :-
            Collection<Integer> c = new ArrayList();
            c.add(3); c.add(8); c.add(9);

    1. size() :-  c.size() // --> 3;

    2. isEmpty() :- c.isEmpty() //--> False, we can do c.size() == 0 for empty check but isEmpty() is optimized sometime.

    3. contains(Object o) :-
            System.out.println(c.contains(2)); // true , why it take Object class not wildCards because contains requried equality comparison, it defined in object class.

    4. iterate() :- --> give iterator object.

    5. Object[] toArray() function :- return Array of Object.
            Object[] obj = c.toArray(); // it is not good bcz we can not do much things with it. need type casting.
    
    6. T[] toArray(T[] a) :- return Array of T

            Integer[]  arr = new Integer[0]; // what happens with zero size of array it get any memory.
            Integer[] arr2 = c.toArray(arr); // return Integer array.

    7.  boolean add(E e) :- return true of successfull addition else false.

            Collection<Integer> list = new HashSet<>();
            list.add(1); list.add(2);
            System.out.println(list.add(2)) // false bcz 2 already exist.
        
    8.  boolean remove(Object o) :- return true of successfull removal of first occurance else false. why take Object type parameter bcz it required comparison for deletion so Object have equals() method(), wildCards does not have it.

            System.out.print(list.remove(2));
        
    9.  boolean addAll(Collection< ? extends E> c) :- bulk addition.

    10. toString() Overriden in collections :-
            System.out.println(list);
            print like this : [1,3,3,2]

    11. boolean containsAll(Collection<?> c) :-

            System.out.println(c.containsAll(List.of(1,2,3))); 

    12. boolean removeAll(Collection<?> c) :-

    13. boolean retainAll(Collection<?> c) :- intersection keep only given element in collection else remove all element.

    14. clear() :-

few other in modern java:

    15. removeIf
    16. Spliterator
    17. stream
    18. parallelStream