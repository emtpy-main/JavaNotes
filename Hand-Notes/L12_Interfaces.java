// ================ Interfaces in java ====================

-> interfaces defines what an object can do without telling how it does that.
-> interfaces is like "contract"

⭕ Rules :-
    1. one interface can extends other interface.
    2. interfaces use dynamic dispatch concepts.
    3. Variables inside interface are by default public static final.
        Eg:-    
            interface MathConstant{
                double PI_VALUE = 3.14;
            }
            internally equals to |
            interface MathConstant{
                public static final double PI_VALUE = 3.14;
            }
    
    4. Method insde interface are by default abstract & public.
        Eg: 
            interface Math{
                int add();
            }
            internally equalt to :-
            interface Math{
                public abstract int add();
            }

    5. Interfaces allow to implement multiple inheritance.
        Eg:-
            interface B {
                void fun();
            }
            interface C {
                void fun2();
            }
            class D implement B,C{
                @Override
                void fun(){
                    System.out.print("my implementation");
                }
            }
    
    6. Interface inheritance :-

    7. after java 8 --> interface can ahve default methods, static methods, private methods
        history why need, bcz java need to add some method in List interface, but adding abstract method can break older codebase, then added default method with implementation.
        Eg:-
            interface Vehicle{
                default void drive(){
                    System.out.println("Vehicle is driving");
                }
                static brake(){
                    System.out.println("Vehicle is applying break");
                }
                private helper(){
                    // can access in interface only.
                }
            }
    8. how interface solves diamond problem.
        Eg:- 
            interface A{
                void fun();
            }
            interface B extends A{
                default void fun(){
                    System.out.println("B");
                }
            }
            interface c extends A{
                    default void fun(){
                        // System.out.println("C");
                        B.super.fun();
                        C.super.fun();
                    }
            }
    9. Java Resolution priority rule :-

        |-> class wala method aapne aap run hojayega, agr tum use run nhi krna chahate toh us method ko override krdo.

        interface A{
            default void fun(){
                System.out.println("Inside A interface");
            }
        }
        class B{
            public void fun(){
                System.out.println("Inside B class")
            }
        }
        class C extends B implements A{

        }
    
    10. what is differnce between interface and abstract class they all have abstract, default, private, static methods.

                    interface                           |                   abstract
        ->  contract/Roles/functionalities              |   ->  Families of similar Class.
            Eg: Runnable, Walkable                      |   ->  Eg:- animal is abstract of Dog,Duck,Elephant
        ->  (can-do) relationship                       |   ->  (is-a) relationship
        ->  fields always public static final           |   ->  No compulson on fields, it can be anything
        ->  donot have constructor                      |   ->  Have constructor
        ->  multiple inheritance                        |   ->  no 
        ->  Methods are public only.                    |   ->  No access modifier restriction on methods.

⭕ Important type of Interface

    1. Functional interface :- contains only one method which are declares.
        -> they unlock Functional programming using Lambda expression.
        -> Eg :- comparable, Predicate
        Eg:-
            interface A{
                void fun();
            }
    
    2. Marker interface :- contains no method inside it.
        -> used to marks something.
        -> java have three marker interface :- Clonable, serializable, RandomAccess
        Eg :-
            interface A{

            }

    interface Internal :-
        - internally, interface are class itself with Acc_Interface tag to identify it.