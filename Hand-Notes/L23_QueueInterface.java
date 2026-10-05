// ====================== Queue interface collection notes part-7 ========================


⭐⭐ : Hierarchy :-
                        [ Iterable <<I>> ]
                            |
                        [ Collection <<I>> ]
                      /     |
                     /      |
            [ List ]    [ Queue <<I>> ]
                |           |
                |           |
        [LinkedList] <---- [ Dequeu <<I>> ]
                            |
                        [ ArrayDeque << Class >> ]
                            |
                            |-----------> can works as
                                            - single Queue
                                            - Stack
                                            - Double ended Queue

⭕ ArrayDeque :-
        - it is a Concrete class which can behave like Queue & stack depending on programmer.
        - it use Array for Queue implementation 
        - for linked list implementation we can use LinkedList Class which implment Dequeue interface also apart from List interface.

            Eg :-           [393, 3930, 3939, 3939, 3930]
                              |                      | 
                            Front                   Rear   

    ⭐⭐ conceptuall how java implement Deque through Array :-

    ⭐⭐ conceptuall how java implement Deque through LinkedList :-

            (Hey gpt answer it)

    ⭐⭐ in ArrayDeque can not be inserted but LinkedList can accept.

    ⭐⭐ why Stack class not used but ArrayDeque used as Stack ?


⭕ Queue :-

    a. as Single ended Queue :-
        Queue<Integer> queue = new ArrayDeque<>();

            1.  add(e) :- enqueue element but can throw exception if failed to insert due to any reason like full memory etc.

                queue.add(5) // exception
            
            2.  offer(e) :- enqueue element but does not throw exception on failure but return false value.

                queue.offer(25) // true else false
            
            3.  peek() :- front element access , can return null if queue is empty.

                queue.peek(); // 5

            4.  element() :- front element access , can throw exception if queue is empty.

                queue.element();

            5.  remove() :- remove front element, can throw exception if queue is empty.

                queue.remove()

            6.  poll() :-   remove front element, can return null if queue is empty.

                queue.poll()

    b. as Double ended Queue :-
            ArrayDeque<Integer> deque = new ArrayDeque<>();
            LinkedList<Integer> deque = new LinkedList<>();
        
        -- both ArrayDeque & LinkedList have these methods

            -- each method get two alternative of previous methods 

            -- enqueue
                1.  addFirst(e)
                2.  addLast(e)
                3.  offerFirst(e)
                4.  offerLast(e)

            -- remove
                5.  removeFirst()
                6.  removeLast()
                7.  pollFirst()
                8.  pollLast()

            -- inspect
                9. getFirst()
                10. getLast()
                11. peekFirst()
                12. peekLast()



⭕ Stack :-

        -- Stack are used through  LinkedList & ArrayDeque so used there methods

            --  for pushing element 
                    -- offerFirst() / addFirst()
            --  for poping out element
                    -- pollFirst() / removeFirst()
            --  for top element
                    -- peekFirst() / getFirst()

            but to maintain similarity with other language java add some wrapper method over these

            --  push(E e) ->    offerFirst(E e)
            --  pop()     ->    pollFirst()
            --  peek()    ->    peek()

    ⭐⭐ what about method inherited from Collection interface into Queue interface ?
            ---> they work here also but 
                    E.g. remove(Object o) can remove any element from collection which will break LIFO & FIFO order

    ⭐⭐ so it developer job to not use such method which will break behavioural contract from collection 
            - stack = LIFO
            - queue = FIFO

⭕ Priority Queue :-
    - 
    - internally, use Heap data structure.



    
    

            