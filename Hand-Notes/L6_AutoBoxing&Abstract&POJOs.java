//=========================== 
// 1. why have only one public class per file.  
// 2. why name of public class should be same as filename.

//================= Autoboxing / Unboxing =======================

-> java gives wrapper class of each primitives datatype.
            DataType 
              /   \
    primitives     Non-primitives
     (stack)         (heap)
        int  ---->  Integer
        float --->  Float 
        long ---->  Long 
        short --->  Short 
        double -->  Double 
        char   -->  Character
        boolean-->  Boolean 

=> why Wrapper even needed ??
    1. collection framwork deals with class not primitives types.
    2. java is Object-Oriented, therefore want Classes of each primitives.
    3. primitives are faster than wrapper.

⭐ Autoboxing says int can automatically convert to Integer.
        Eg :- int x = 10;
              Integer y = x; // autoboxing ,
              // internally -> Integer y = new Integer(x); // used is older java 
              // in newer java use, Integer y = Integer.valueOf(x); // better and optimized through caching.

⭐ Unboxing says non-primitives convert to primitives
        Eg :- Integer x = 10; // depricated version -> Integer x = new Integer(10);
              int y = x; // unboxing internally, int y = x.intValue();

Now, we trying to guess Integer class
        public final Class Integer{
            int value;
            // construtors
            static Integer valueOf(int x){

            };
            int intValue(){

            };
            public boolean equals(Integer y){
                return this.value == y.value;
            }
        }

### when autoboxing/unboxing done automatically
    1. Assignments
    2. Method calls
    3. Arithemetic Operations
    4. println() statement
    5. nullptr exception in this :-
            Integer x = null;
            int y = x; // nullptr exception , bcz -> int y = x.intValue();
    6. how == works internally
            1. for  object, compares references.
            2. for primitives, compares value.
            Eg :-  int x = 100;
                   int y = 100;
                   System.out.println(x==y); // true;

                   Integer x = 100;
                   Integer y = 100;
                   System.out.println(x==y); // false 💯  compares references
                   System.out.println(x.intValue() == y.intValue()); // true bcz convert into interger
                   System.out.println(x.equals(y)); // equals compares values not reference in non-primitive types.

#⭐⭐ how valueOf() in wrapper class are optimized :-
internally, Integer x = 200 use Integer.valueOf() to convert x int into Integer class.

=> it generally, preallocated object in heap for int from -128 to 127 using caching technique.
if you created for Integer x = 100;
                   Integer y = 100;
                => these two gona point to same object(reference) in heap bcz is already cached.

⭐⭐ generally, Integer from -128 to 127 range , == return true bcz caching optimized technique.
                => System.out.println(x==y); // -> true


// =============================== Abstract Class ====================================

1. cannot be instantiated directly.
2. yes it can have construtor althoght object cannot be created but it child class object creation requires parent class construtor.
        Eg :- abstract class Animal{
                String name;
                Animal(String name){
                        this.name = name;
                }
                void sounds();
        }
3. abstract class cann't be final bcz it meant to be inherited.
4. abstract class can have static method,variable,blocks.
5. private method are allowed in abstract but it should not be abstract.
        Eg :- abstract class Animal{
                private void bark(); // ❌ Error bcz private method are visible to subclass from implemantation.
                void sounds();
        }
6. abstract class can have final method but it should not be abstract.
7. can abstract class no abstract method ?
  -> yes it is possible

// ====================== POJO class ==================================================

-> stands Plain Old Java Object.

                        POJO
                         /\
                        /  \
        Anemic model       Rich domain model
        - getter/setter    - can have business logic
        - construtor
        - field
        - no business logic