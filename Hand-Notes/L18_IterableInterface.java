//===================== Iterable Interface collections notes part-2 ===========================

--> suppose we have to loop(traverse) through complete data structure.

                    [ Data structure ]
                            |
                --------------------------
                |                         |
            Ordering maintain       Not Ordering maintain
            - ArrayList                 - Set
            - LinkedList                - Map
                                        - [stack/queue] maintain but different order.

--> in simple ArrayList we can easy loop using for loop.
        Eg :-   
            List<Integer> list = new ArrayList<>();

            for(int i = 0 ; i < list.length() ; i++ ){
                System.out.println(list.get(i));
            }

        but for non-ordering DS, we do not have get() function then how we can traverse DS.
            LinkedList DS, does not have indexing then how we can traverse DS.

--> Defination :- It is the root interface that represent any object whose element can be traversed one by one.

    --> methods interface :-
        1. Iterable interface have method iterator() which return object of iterator that point to current cursor on collection.
        2. Iterator is a interface that contain two methods
                - hasNext() :-
                - next() :-

        3. but if iterator is interface then how we can create its object therefor each collection have specific class which implements Iterator interface.

⭕ Conceptual view :-

    interface Iterable{             |       interface Iterator{
        Iterator iterator();        |           hasNext();
    }                               |           next();
                                    |       }
    -------------------------- ArrayList ------------------------
    Iterator iterator(){            |       class ArrayListIterator implements Iterator{
        return new                  |           hasNext(){};
                ArrayListIterator();|           next(){};
    }                               |       }

⭕ How different collection Conceptual implements Iterable/Iterator;

    1. ArrayList :-

            class ArrayList implements Iterable{
                private Integer[] arr;
                private int size;

                @Override 
                Iterator iterator(){
                    return new ArrayListIterator();
                }

                class ArrayListIterator implements Iterator{
                    int pos=0;
                    @Override
                    public boolean hasNext(){
                        return pos < size;
                    }
                    @Override
                    public Integer next(){
                        return arr[pos++];
                    }
                }
            }
            main(){
                List<Integer> = new ArrayList<>();
                Iterator it = list.iterator();
                while(it.hasNext()){
                    System.out.println(it.next());
                }
            }

    2. LinkedList :-

        class LinkedList implements Iterable{
                static class Node{
                    int data;
                    Node next;
                }
                Node head;
                @Override 
                Iterator iterator(){
                    return new LinkedListIterator();
                }

                class LinkedListIterator implements Iterator{
                    int current=head;
                    @Override
                    public boolean hasNext(){
                        return current!=null;
                    }
                    @Override
                    public Integer next(){
                        int data = current.data;
                        current = current.next;
                        return data;
                    }
                }
            }

            main(){
                List<Integer> = new LinkedList<>();
                Iterator<Integer> it = list.iterator();
                while(it.hasNext()){
                    System.out.println(it.next());
                }
            }
    3. Rest you can think Conceptual : (gpt explain this)
            a. Set/Map :-
            b. TreeSet/Treemap :-
            c. ArrayList

⭕ Custom Iterator :-
    Eg :-
        class NameContainer implements Iterable<String> {
            private String[] names;
            private int size;

            NameContainer(String[] names){
                this.names = names;
                this.size = names.length();
            }

            // it can have many method like getName, setName, insertName..... etc.
            @Override
            public Iterator<String> iterator(){
                return new NameContainerIterator();
            }

            private class NameContainerIterator implements Iterator<String>{
                    private int pos=0;
                    @Override
                    public boolean hasNext(){
                        return pos < size;
                    }
                    @Override
                    public String next(){
                        return names[pos++];
                    }
            }
        }
        main(){
            String[] names = {"Aditya","Pratik","Rishabh","Rakshit"};
            NameContainer container = new NameContainer(names);

            Iterator it = container.iterator();

            while(it.hasNext()){
                System.out.print(it.next() + " ");
            }
        }

    --> we can use anonymous class to optimize it bcz NameContainerIterator used only once.

⭐⭐ Enhanced For loop (for each loop) :-
        - it is suger coating for iterator to loop.
        - this loop work only for array and intance of iterable implemented class.

    Eg :- for(String name : container){

          }
    
⭐⭐ Important :

        1. Iterable (<< interface >>)
                |-----> iterator()
                |-----> ForEach() 
                |-----> splitIterator()

        2. Iterator (<< interface >>)
                |-----> hasNext()
                |-----> next()
                |-----> remove()
                |-----> forEachRemaining()

        ## if we have remove method then why not have add() in Iterator ?

⭐⭐ Concurrent Modification Exception :-
        --> it is fail fast bcz if collection modify instantly get failed rather than assuming things.

        Eg :-

            List<Integer> list = new ArrayList<>();
            list.add(1);
            list.add(2);
            list.add(3);
            Iterator it = list.iterator();
            while(it.hasNext()){
                int value = it.next();
                if(value == 3){
                    list.remove(value); // note here we used remove method of list not iterator
                    // ❌❌ exception
                }
                System.out.println(value + " ");
            }




