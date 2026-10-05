// ====================== Set & Map interface part-2 collection notes part-6 ========================

⭐⭐ Hierarchy :-

1. Set :-
                       [ Iterable ]
                            |
                        [ Collection ]
                            |
                        [ Set ]
                        /\
                        /  \
                [ HashSet ]  [ SortedSet ] <<interface>>
                    |           |
            [ LinkedHashSet] [ NavigableSet ] <<interface>>
                                |
                                [TreeSet]


    --> Set interface does not provide any new method it contain all previous methods of Collection interface.

    --> new method mostly introduce in TreeSet.

    ⭕ Constructor :-

        a. for both LinkedHashSet / HashSet :-

            1. no-argument :- Set<Integer> set = new HashSet<>();
            2. intial-capacity :- Set<Integer> set = new HashSet<>(100);
            3. capacity, load Factor :- Set<Integer> set = new HashSet<>(100,0.8f);
            4. another collection :- Set<Integer> set = new Hashset<>(List.of(1,2,2,3,4,5,4,1,1));

        b. for TreeSet :- (hey gpt fill it)

    
    ⭕ Method in TreeSet :-
        
        Eg :-   TreeSet<Integer> set = new TreeSet<>();
                set.add(80);
                set.add(23);
                set.add(10);
                set.add(90);
                set.add(50);
        
        a. method through SortedSet interface :-
            
            1.  first() :- smallest value bcz in BST, leftmost first node holds smallest value.

                        System.out.println(set.first()); // 10

            2.  last() :-  Largest value bcz in BST, rightmost last node holds largest value.

                        System.out.println(set.last()); // 80
            
            3.  headSet(a) :- element strict less then a (a is exclusive)

                        System.out.println(set.headSet(80)); // [ 10, 20, 30]

            4.  tailSet(a) :- element strictly greater than a (a is inclusive)

                        System.out.println(set.tailSet(80)); // [ 80, 90]
                    
            5.  subSet(a,b) :- get a range of element between a (inclusive) & b (exclusive).

                        System.out.print(23,80); // [ 23, 80]
            
            6.  lower(a) :- largest number smaller than a.
                            if no element present less than a then return null.
                        System.out.println(80); // 50

            7.  floor(a) :- greatest element less than or equal to a.
                            if no element present less than a then return a;
                        System.out.println(10); // 10

            8.  higher(a) :- smallest number greater than a or return null.

            9.  ceil(a) :-   smallest element greater than or equal to a.

        b. method through NavigableSet interface :-

            1.  NavigableSet also contain lower() , floor(), higher(), ceil() with similar behaviour.

            2.  pollFirst() :- it poll out (remove & return) smallest element.

                        System.out.println(set.pollFirst());

            3.  pollLast() :- it polls out largest element.

                        System.out.println(set.pollLast());
                    
            4.  descendingSet() :- return set element in descending order.

                        System.out.println(set.descendingSet());

            5.  descendingIterator() :- to print element in descending order.
                        Iterator<Integer> it = set.descendingIterator();
                        while(it.hasNext()){
                            System.out.println(it.next());
                        }

            6.  headSet(a, flag) :- element lesser than a. a is inclusive then flag is True else exclusive.

            7.  tailSet(a, flag) :- element greater than a. a is inclusive if flag is true else exclusive.

            8.  subSet(a, b, aflag, bflag) :-

2. Map :-

    - map does not comes under collection interface.

    ⭐⭐ Hierarchy :-   
                        [ Map ]
                          / \
                         /   -------------|
                [ HashMap ]           [ SortedMap ]
                    |                     |
                [ LinkedHashMap ]     [ NavigableMap ]
                                          |
                                    [ TreeMap ]

    - we have few other maps also :-
                - Hashtable
                - Properties
                - EnumMap
                - IdentityHashMap
                - ConcurrentHashMap
                - WeakHashMap

    
    - bcz map have separate hierarchy therefore it have to define its separate methods.

    ⭕ Constructor 

    ⭕ Methods in map interface :-
            Map<Integer,String> map = new HashMap<>();
            map.put(101,"Aditya");
            map.put(102,"Rohit");
            map.put(103,"Rohan")

        1.  size() :-
        2.  isEmpty() :-
        3.  containsKey(T key) :- O(1)
        4.  containsValue(T value) :- O(n)
        5.  get(T key) :- 
                    if (Key exist) return value of key
                    else return null;

        6.  put(T key, V value) :-
                    if( key already key) then update new value return old value of key
                    else add new key-value in map return null.
        
        7.  remove(T key) :- remove key from map

        8.  putAll(Map<T,K> mp) :- put key-value of one map into new map.

        9.  clear() :- clear map

        10. Set<T> keySet() :- return set of key in map

        11. Collection<V> values() :- return Collection of values bcz it can repeats.

        12. Set<Entry< T, V >> entrySet() :- return all key-values entry. 
                        |----> map interface have sub interface name Entry.
                        |----> Entry represent Key-value pair.

                Eg :- how to print all Entry
        
        13. getOrDefault(T key,defaultValue) :- we get defaultValue if key not present rather than null.

        14. putIfAbsent(T key, V value) :- put value in map if key does not present else not put.

        15. remove(T key, V value) :- remove Entry from map if key-value pair exist.

        16. replace(T key,V value) :- it replace of Key. if key does not exist it does not put new entry.

        17. replace(T key,V oldValue, V newValue) :- replace of value of key with newValue if older value have same value as oldValue.

        18. Method in Map.Entry interface :
                Eg :-
                        Set<Map.Entry<Integer,String>> entries = map.entrySet();
                        for(Map.Entry<Integer,String> entry : entries){
                            Integer key = entry.getKey();
                            String value = entry.getValue();

                            System.out.println(key + " , " + value);
                        }

        19. of() :- used to create immutable map.
                Map<Integer,String> map2 = map.of(101,"Aditya",102,"Rohan");

⭕ HashMap / LinkedHashMap :-

        constructor :-
            1. no-argument :-   Map<Integer,String> = new HashMap();
            2. capacity :-   Map<Integer,String> = new HashMap(100);
            3. capacity & load factor :-   Map<Integer,String> = new HashMap(100,0.8);
            4. collection :-   Map<Integer,String> = new HashMap(map2);

        - what is structual difference between them.

⭕ TreeMap :-

    - method in treeMap :
        from SortedMap :-
            - firstKey()
            - lastKey()
            - firstEntry()
            - lastEntry()
            - headMap(key)
            - tailMap(key)
            - subMap(fromKey,toKey)
        
        from NavigableMap :-
            - lowerKey(key)
            - lowerEntry(key)
            - higherKey(key)
            - higherEntry(key)
            - floor(key)
            - ceil(key)
            - descendingMap()
            - headMap() with flag
            - tailMap() with flag

⭕  HashTable :-
        - legagcy class
        - similar to HashMap
        - By default Thread safe
        - not used much bcz too slow.
        - lock entire table for thread safety which make is slower.

⭕ Properties :-
        - specific map that represent configuration data.
               [ HashTable ] ----------> [ Properties ]

⭕ WeakHashMap :-
        - rarely used
        - cache like behaviour
        - bcz have weak references

⭕ IdentityHashMap :-

        - normal HashMap based on Equality based using equals() method.

        Eg : String a = new String("Pratik");
             String b = new String("Pratik");

             normal hashmap use equals method to compare then put in hashmap.
             so a.equals(b) return true althrough they are different object but string override equals that compare values.
             so only one key is created with "Pratik"
        
        - IdentityHashMap change it say it will consider a & b same if both are same object else nothing i.e. it used a==b for comparison.

⭕ EnumMap :-

        -- suppose hashmap have enum constants as key. then
            Eg :- normal HashMap
                    enum Day{           Map<Day,Integer> mp = new HashMap<>();
                        Mon,            mp.put(Mon,0);
                        Tue,
                        Wed,
                        ..
                        ..
                        ..
                    }

        -- this normal hashmap are not optimized for this kind of thing but Java provide EnumMap which optimized it how.

            - in enum each constants have ordinal value which is integer therefore maximum range index of Bucket are fixed then java can directly declare HashMap of that size.
            - it reduce collision to zero.

            - does not use Hashing.
            - Memory efficient
            - null key are not allowed
            - null value are allowed
            - iterationOrder preserve

⭕ ConcurrentHashMap :- 
            
        - latest Thread-safe Map