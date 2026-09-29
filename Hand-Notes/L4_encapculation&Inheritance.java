//============================== Encapculation ===========================

to form a capsule. Real life object properties and behaviour are encapculated.

Rules :-
1. Both data & behaviour should be together (encapculated) within object.
2. We should not provide unrestricted access of our data. Example bank Account balance direct update can enable customer to modify balance.
3. 

// ============== ****** Access Modifier ******* ===============
who have access to  -> Variable
                    -> Method
                    -> Constructor 
                    -> Class :- can not have private or protected modifier , only nested class can be private.

=> public :- anyone can access
=> default :- anyone can access belonging to same packages.
=> private :- only accessed inside Class.
=> protected :- can access in same package's Class + inheritance class( child class);

// ============== ****** Packages ******* =====================
A package group similar Class/Interface together.
        /                    \
        
    user defined package   inbuildt package
    package custome         java.lang -> string
                            java.util -> 
                            java.io

 how JVM handles package
 as bytecode generate of demo.class import all .class file of all package imported and loaded to remove ambiguity.

//============================= Inheritance ===============================

 vehicle                    class Vehicle{
    |                           void start(){
    | (is-a)                    }
    |                       }
   Car
                            class Car extends Vehicle {
                                void start(){
                                }
                            }

Rules :-
1. Reusability
2. Support polymorphism.
3. define inheritance.
4. is-a relationship
5. parent class method are inherited to child class.


******* Type of inheritance *****

1. single inheritance           A class
                                    |
                                B class
2. Multi-level inheritance      A class -> B class -> C class

3.Hierarchy inheritance         A class
                                  / \
                            B class C class

4. Multiple inheritance ( Not supported in java , but done through interfaces) 
                A class  B class
                     \    /
                    C class
    it not supported due to diamond problem*****.
                A class (show() method)
                  /  \
                 /    \
    B class (show())  C class (show())
                \      /
                 \    /
                 D class (java can not decide at runtime which show() should be called by obj.show()).

notes ⭐⭐⭐ :- final class can not be inherited.
//================== Super keyword ==================
it holds the reference of parent class.

Uses :-
    1. to access parent class variable.
        class A{
            int x=4;
        }
        class B extends B {
            super.x; // parent variable
            int x; // both x are different
        }
    2. to call parent class Method directly from child.
    3. to call parent class constructor
        class A{
            A(){

            }
        }
        class B extends A{
            B(){
                super(); // it is optional to add super here, bcz java itself add by default call parent constructor during inheritance.
            } 
        }
                                                