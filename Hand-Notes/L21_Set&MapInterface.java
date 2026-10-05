// ====================== Set & Map interface collection notes part-5 ========================

⭕ Set :-   
        - Duplicate are not allowed.
        - constant time search operations.

⭕ Map :-
        - stores key-value where key is unique.
        - Duplicate key are not allowed.
        - constant time search.

    --> Set/Map have No positional access.
    


    Eg :- 
            Set<String> set = new HashSet<>();
            set.add("Aditya");
            set.add("Rohit");
            set.add("Rohan");
            set.add("Pratik");

            print(set.contains("Abhay")) // false;
            print(set.contains("Pratik")) // True;

            Map<Integer,String> map = new HashMap<>();
            map.put(102,"Rohit");
            map.put(203,"Pratik");

            print(map.contains(203)); // true;
            print(map.get(203)) // Pratik

⭕ How internally HashSet/HashMap works :- (hey gpt explain it) 

        --> use hashing technique with modulo divison.
        --> for collision resolution use linked list at bucket[i].

        --> Object use HashCode() to return number then hash that number put in bucket like if target bucket is filled then add to linkedlist at that bucket.

⭐⭐   --> There is not data structure named Set. Java does not have Set.

        --> underline , Set ------> use Map for its operation.
                |--> bcz both have same kind of properties.

        -   Set<Integer> st = new HashSet<>(); -----> private HashMap<Integer,Object> map = new HashMap<>();
        
        -   st.add(2);                         ----->  map.put(2,PRESENT);
                                                                    |
                                                                    |--> private static final PRESENT = new Object() ;

        -   Java map working (Set/Map --> Map):-
                1. Key --> hashCode (K,V)
                2. Bucket No. (%)
                3. Store in Bucket

        == Conceptual View :-

            class Node<K,V>{                |   class HashMap{
                K key;                      |       static class Node{
                V value;                    |           -----------
                int hash;                   |            --------
                Node<K,V> next;             |       }
            }                               |   }

        
        - java handle collision through
                    - chaining (LinkedList)
                    - not open addressing

        ⭐⭐ intially have capacity = 16.

⭕ Load Factor :- ratio of elements in bucket / capacity.
        -   if(Load Factor > 0.75) then rehashing with increased capacity.
        -   capacity increased by 2x as array size.

        lets,
            element = 4, capacity = 5;
            LF = element/capacity = 0.8 then need rehashing with 2X capacity.


⭕ Treefication :-
        - if size of linkedlist in bucket[i] have nodes greater than threshold (8).
        - then internally java convert linkedlist into BST (self-balancing).
        - time complexity reduce O(n) --> O(log(n)).


⭕ LinkedHashSet / LinkedHashMap :-
        --> insertion order are maintained during iteration.
        --> in this, each node in linkedlist of bucket point to next node in insertion order same.
        --> in increase complexities.

            class Node<K,T>{
                    K key;
                    T value;
                    int hash;
                    Node<K,T> next;
                    Node<K,T> before,after;
            }

        --> very remain same.

⭕ TreeSet/ TreeMap :-
        --> implmented using self-balancing BST (Red-black Tree).
        --> major operation have O(log(n)) complexity.
        --> 
                class Node<K,T>{
                    K key;
                    T value;
                    int hash;
                    Node<K,T> parent;
                    Node<K,T> left,right;
                    int color; // for balancing
                }

⭐⭐ Null value :-
        -- LinkedHashMap / LinkedHashSet / HashSet / HashMap
                    ---> allows 1 null as key
                    ---> allows 0....infinite null as values
        
        -- TreeSet / TreeMap 
                    --> does not allows null as key
                    --> allows null as values.

⭐⭐ hierarchy :-
                        [ Collection ] <<interface>>
                               |
                            [ Set <<interface>> ]----------------
                                |                               |
                            [ HashSet ]-----------|         [ TreeSet ]
                                |                 |             |             
                            [ LinkedHashSet ]--|  |             |
                                               |  |             |
===============================================|==|=============|================================================
                                               |  |             |
                        [ Map <<Interface>> ]--|--|-------------|---------|
                                |              |  |             |         |
                            [ HashMap ]<-------|--|             |-----> [ TreeMap ]
                                |                 |
                            [ LinkedHashMap ]<----|