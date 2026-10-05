//=========== Comparable Interface & Collections Class (collection notes part-8) ===========

-- comparison are easy in primitive type
-- but user-defined data type does not what to compare it.

⭕ Comparable Interface :-
    - contract
    - i know how to compare myself with another object of my type.

        interface Comparable<T> {
            int compareTo(T o);  
        }       |-----------------------> Functional interface.

        Eg :-   
                class Student implements Comparable<Student> {
                    String name;
                    int marks;
                    public Student(String name,int marks){
                        this.name = name;
                        this.marks = marks;
                    }
                    @Override
                    public int compareTo(Student other){
                        return  this.marks-other.marks; // < 0 (-ive)= this.marks, other.marks
                                                        // 0 > (+ive)= other.marks, this.marks
                                                        // == 0 => any can comes first
                    }
                }

                public static void main(String[] args){
                    List<Student> list = new ArrayList<>();

                    list.add(new Student("Pratik",29));
                    list.add(new Student("Rohit",90));
                    list.add(new Student("Rohan",83));

                    Collections.sort(list); // error if comparable interface are not implemented in Student class.

                }

    - to use Treeset/ Treemap  on Student class we must have to implement comparable interface in Student class.

            one class ----> one natural ordering

            therefore each class have only one compare to

    - why does java does not use boolean or enum in compareTo return rather than integer ?

⭕ Danger of 0 return :-

    -- compareTo() can return +ive, -ive, 0.
    -- if return 0 then java consider two object equal not similar.
            means object1 equal to object2
    -- therefore data structure that use equals based for insertion like TreeSet / TreeMap that compare Object to create BST.

    -- if in this case return 0, then TreeSet / TreeMap consider both as equal as we know duplicate are not allowed therefore only any one object can be inserted in TreeSet / TreeMap.

    -- but like DS like HashSet / HashMap using hashing therefor it is not effect.

    -- ungracefully handling of return 0 can lead to loss of data.

⭐⭐⭐⭐ : Rule
        if a.compareTo(b) == 0
        then, make sure that a.equals(b) --> true.

⭕ When used Comparable :
    -- in Custom Class,
                Natural Ordering --> obvious
    -- if natural ordering are not obvious then do not use Comparable. ❌❌


⭕ Collections Class :-
    -- define it

    - methos :-
        - Collections.sort(list);
        -            .max(list)
        -            .min(list)
        -            .fill(list,0)
        -            .reverse(list)
        -            .shuffle(list)
        -            .swap(list,a,b)
        -            .binarySearch()
        -            .frequency(list,b)

        - Collections.unmodifiableList(list) --> return immutable list
        -            .unmodifiableSet(set)       --> return immutable set
        -            .unmodifiableMap(map)       --> return immutable map
        -            .emptyList(list) --> return empty list if we get null to avoid null pointer exception
        -            .emptySet(set) --> return empty set if we get null to avoid null pointer exception
        -            .emptyMap(map) --> return empty map if we get null to avoid null pointer exception