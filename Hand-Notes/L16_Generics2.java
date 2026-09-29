//========================================= Generics part 2 in java ====================

--> General purpose class
--> generics break parent-child relationship. ⭐⭐

    💯 Generic<A> is not subtype of Generic<B>

    --> lets see how generics break parent-child relationship
        
        Eg :-       [Animal] --------> [Dog]
                =>  Animal a= new Dog();     // we know this that parent reference can hold childs object.

            in list interface,   [ List ] ----------------> [ ArrayList ]
                =>  List<Integer> list = new ArrayList<>();     // declare arraylist

        but,        Animal a = new Dog();
                    List<Dog> dogs = new ArrayList<>();
                    List<Animal> animals = dogs; // ❌❌❌❌ ⭐⭐ not allowed in java

                if Animal parent can holds Dog object why Animal(parent) list cannot holds Dog(child) list. that is why generics break parent-child relationship
    
    --> lets see if List<Animal> animals = dogs; is allowed it will loose "Typesafety" :-
            
            intially, [dog,dog,dog,dog] = list contain only dog object.

            during reading, animals.get(0) => get dog object at index 0. 
                    --> reading have no Problem
            
            during writing, we can write animals.add(new Animal()); // we can add bcz reference are Animal(parent) type
            
                --> now [dog,dog,dog,dog,animal]

                now, list have animal type object also 

                        for(Dog d : animals){
                            d.bark();
                        }

                --> this code give lots of runtime error due to loos Typesafety 
                    1. only dog object have bark method not animal object so d.bark() crashed
                    2. in for each loop dog reference can not hold its parent animal object.
                
                -- due to lossing Typesafety no one except animal object in array.


        ⭐⭐⭐⭐ :this property called Invariant
        =====> Generics are Invariant
                --> if A is child of B then Generic<A> is not child of Generic<B>.

        in array, have not problem this Invariant bcz it does not use generics.

                Dog[] dogs = new Dog();
                Animal[] animals = dogs.
                
                animals[2] = new Animal() ; // runtime error bcz internally each block of array except that they will referenc to Dog type object but on getting Animal type they show error.

                Eg :-
                    Dog[] dogs = new Dog[10];
                    Animal[] animals = dogs;

                    animals[0] = new Dog();
                    animals[1] = new Dog();
                    animals[2] = new Dog();
                    animals[3] = new Animal(); // give runtime exception name arrayStoredException ⭐⭐

            --> here we get, covariant.

    --> solve this problem java introduce WildCards.
    
⭕ : WildCards(?):-
    - define WildCards.
    -Syntax :- 
                List<?> list = new ArrayList< "anytype like Dog,Animal,cat">();
                    |
                    |------> List of specific type but we do not know of what type ?

            Eg :- void printAnimals(List<?> a){

                }
                in printAnimals method i can passed list of anythings like
                    printAnimals(new ArrayList<Dog>());
                    printAnimals(new ArrayList<Animal>());
                    printAnimals(new ArrayList<Cat>());
            
            but problem are with this is :-

                1. reading : can reads only through Object class bcz compile does not know type of object list holds. only object can hold any type of class. 
                        void fun(List<?> list){
                            Object obj = list.get(0);
                            for(Object O:list){
                                
                            }
                        }

                        ==> in this, it means only Object class method can run.
                2. writing : not allowed writing.
                        
                        void fun(List<?> list){
                            list.add("Hello"); // runtime error;
                            list.add(new Dog()); // runtime error;
                            list.add(new Animal()); // runtime error;
                        }
                    --> compiler does not know what type of object does list holds.
                    --> it gives runtime error bcz call substitution happens at runtime.

⭕ WildCards with upper bound (extends) :-
        --> List< ? extends Animals> can hold only Animals class and its subtype.
        --> Eg :-
                    void fun(List<? extends Animals> list){
                        for(Animal a: list){
                            a.eat();
                        }

                        // writing not possible
                        list.add(new Dog()); //error
                        list.add(new Animal()); //error
                    }

                    fun(list of dogs);
                    fun(list of animals);
                    fun(list of Integer); // error bcz not a subtype

        --> reading allowed bcz we already setted upper bound that maximum can go upto Animals or its subtype. so animals reference pointer can easily points subtype object.
        --> Writting still not allowed bcz we can add sibling class or child -> parent. // gpt explain this 
                Eg :- List of Animal 
                        fun added Cat it is fine
                    but List of Dog
                        fun added Dog. // error so sibling object can hold in same List.
                        fun added Animal. // error child -> parent.



⭕ WildCards with lower bound (super) :-

        --> List< ? super Animal> can holds only Animals or its superType.
        --> it defines minimum bound.

            Eg :- 
                    void fun(List<? super Animal> list){
                        // now writing possible
                            list.add(new Animal());
                            list.add(new Cat());
                            list.add(new labrador()); // subtype of Dog which is subtype of Animal.

                        // but reading not possible, can happens using Object 
                        Animal a = list.get(0); // possible if list contain animal but list can contain super class like object , so child can hold parent object.
                    }
                --> what i can send in fun.
                    fun(list of animals);
                    fun(list of Objects);
                    fun(list of Dog); // error not super type.

        Reason why writing is possible but reading not possible.

⭕ difference between super bound v/s extends bound (super) :- hey chatgpt answer it.
        
    ⭐⭐ : 
            List < ? extends T > :- covariant
            List < ? super T > :- couterVariant
            List < T > :- Invariant

⭕ PECS Rule : 
        --> Producer - extends
        --> Consumer - super

⭐⭐ when generics v/s WildCards are used :-


⭕ Type Erasures :-

    --> Does JVM knows Generics ?

        at runtime compiler removes as Generics from code.
        bcz generics are only for compile time safety.

    Eg :-
            code :
        class Box<T>{           compiles                        class Box {
            T values;      ------------------------------->         Object values;
        }                                                       }
    
    Rules :-
        1. If no Bound ---> Replace Object.
            Eg :-
                
            class Box<T>{           compiles                        class Box {
                T values;      ------------------------------->         Object values;
            }                                                       }

        2. if bounded ---> replace with bound.
            Eg :-
                
            class Box<T extends Number>{     compiles                class Box {
                T values;      ------------------------------->         Number values;
            }                                                         }

        3. Insert Casting automatically.
            Eg :-
                
                Box<Integer> b1 = new Box<>();  |     compile     | Box b1 = new Box();
                Integer x = b1.value();         | --------------> | Integer x = ()b1.value();
                                                                                |
                                 // compiler automatically cast to Integer <----|
                here, we have no chance to get classCastException bcz generics make sure at compile that value contain interger only.

⭕ things can not done through Generics :-

        1.  instanceOf operator :-
                Eg :- can not do 
                        List<String> l = new ArrayList<>();
                        if(l instanceOf List<String>){

                        }
                    bcz type are erased we can check specific type.
        
        2.  Method Overload ⭐⭐ based on generics

                Eg :- 
                        class Demo{
                            void print(List<String> l) {}
                            void print(List<Integer> l) {}
                        }

                    bcz at the end both become void print(List l) {}
        
⭕ Compiler generated bridge method :-

    --> compiler added method itself to support polymorphism after compilers in generics.

    Eg :-
                                                    after compilation
    class Parent<T>{                        |       class Parent{
        T get(){                            |           Object get(){
            return null;                    |               return null;
        }                                   |           }
    }                                       |       }
    class Child extends Parent<String>{     |       class Child extends Parent {
        @Override                           |           @Override 
        String get(){                       |           String get() {
            return "Hello";                 |                   return "Hello";
        }                                   |           }
    }                                       |       // is get method still overridden method ---> no
                                            |       // therfore compiler add method to not break previous
                                            |       // existing polymorphism
                                            |           @Override
                                            |           Object get() {
                                            |               return "Hello";
                                            |           }
                                            |       }



⭐⭐ why Type erasures exist ? 
    ---> generics introduce in java 5.
        previous :-     List l = new ArrayList();
        now :-          List<Integer> = new ArrayList<Integer>();
    directly adding generics into JVM crashe prevoius codebase.
    therefore, Java make Generics as Compiler safety test only so that previous & now code still look same.

⭐⭐ why Generics not support primitive ?

    Eg :-
        class Box <T> {
            T values ; // become Object values;
        }

    suppose,
        Box<int> b1 = new Box<>();  ❌❌

        but can Object reference in Box holds int ? no can bcz Object is super class for non-primitive only.