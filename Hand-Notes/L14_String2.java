// =============== String part-2 in java ===============================

⭕ String with new keyword :-
        -> String s1 = new String(); // -> empty String
                System.out.println(s1); // print nothing


    ⭕ Different constructor of String() :-
            1. No value : String s1 = new String();

            2. With value : String s2 = new String("Hello");

            3. With String literal : 
                        String s = "Aditya";
                        String s3 = new String(s);

            4. With Char Array :-
                        char[] arr= ['A','d','i','t','y','a'];
                        String s4 = new String(arr);
                        arr[0] = 'B';
                        System.out.println(s4) ; // still show Aditya bcz String class Values array copy each character form scratch char[].

            5. With char[] through offset :-
                        char[] arr= {'A','d','i','t','y','a',' ','S','i','n','g','h'};
                        String s5 = new String(arr, 0, 5);
                                                    |   |------> count - ending index excluding
                         offset starting index  ----|

                        System.out.println(s5); // -> Adity 

            6. With byte[] :-
                        byte[] arr={97,98,99};
                        String s6 = new String(arr);
                        System.out.println(s6); //-> abc
            
            7. with byte[] through offset :-
                        byte[] arr={97,98,99};
                        String s7 = new String(arr,0,3);
                                                    | |-----> excluding end index
                           including start index <--|
                        System.out.println(s7); //-> abc

            8. with StringBuilder / StringBuffer :-
                        StringBuilder sb = new StringBuilder("hello");
                        String s = new String(sb);
                        System.out.println(s); // 


⭕ Method of String class :-
        -- hey chatgpt, explain each method syntax and highlist in concise way time complexity in searching
        1. Length/Emptiness :-
                |--> length()
                |--> isEmpty()
                |--> isBlank()
        
        2. Character Access :-
                |--> charAt(int)
                |--> toCharArray()
        
        3. Comparison :-
                |--> equals()
                |--> equalsIgnoreCase()
                |--> compareTo()

        4. Searching :-
                |--> contains()
                |--> indexOf()
                |--> lastIndexOf()
                |--> startsWith()
                |--> endsWith()
        
        5. Extraction / tranformation :-
                |--> substring()
                |--> toUpperCase()
                |--> toLowerCase()
                |--> trim()
                |--> strip()
                |--> repeat()
                |--> replace()
                |--> replaceAll()
                |--> split()
                |--> join()
        
        6. Conversion :-
                |--> valueOf()
                |--> getBytes()

        7. Advance :-
                |--> intern()
                |--> format()

⭕ : StringBuffer / StringBuilder :- 
                used to provide mutable string.
                            AbstractStringBuilder interface
                                    /          \
                            StringBuffer   StringBuilder
                    thread-safe             not thread-safe
                                \          /
                                java.lang


            -> conceptual view :-
                    class StringBuilder extends AbstractStringBuilder{
                        byte[] values;
                        int count;
                    }

            let StringBuilder sb = new StringBuilder("java");
                    in memory :- ['j']['a']['v']['a'][][][][][][][][][][][][]

                    here, count=4 byte
                            capacity = 16 byte.

            intially capacity are 16 byte then increase by factor (capacity * 2) + 2 if older get full filled.


⭕ StringBuilder / StringBuffer methods :-
            |---> append()
            |---> insert()
            |---> delete()
            |---> replace()
            |---> reverse()
            |---> charAt()
            |---> setCharAt()
            |---> length()
            |---> capacity()
            |---> ensureCapacity()
            |---> trimToSize();
        
        in StringBuilder/StringBuffer :- equals() method are overridden therefore it compares references not values of string.

⭐⭐ all method of String are not present in StringBuilder/StringBuffer