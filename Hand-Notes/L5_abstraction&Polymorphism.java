//========================== Abstration =========================
Defination -> the process of focussing on what something does, while ignoring how it does that.
oops -> real word X 
    |-> human think about reality (perceptation).

-> tell about families of similar object.

Rules :-
    1. Represent whatever necessary (required).
    2. Even what we model, we dont want everyone to know how everything works, but they can still use it.
    3. hiding implementation details, know only how to use it.
    4. in java, abstration are implementation using two level :-
            -> low-level abstration (hiding implementation details).
            -> high-level abstration (separate WHAT from HOW).

# Low-level abstration
    class Car{
        String type;
        void brake(){}
        void acclerate(){}
        void start(){}
    }

    public static void main(String[] args){
        Car c = new Car();
        c.start();
        c.acclerate();
        c.brake();
    }
    
    -> here java automatically implementated low-level abstration, bcz client only know method to use they dont know about implemntation (Car class are hidden from client).

    -> here, C reference variable tightly coupled with start(), acclerate(), brake(). 
    -> What , How are tightly coupled.
    -> like if we have two car electric and fuel car then, for each we have to write two Class class 
        a. electric car class
        b. fuel car class
    -> based on usage, we have to create different obj of each class.
         // electric car ec c = new ec();
         // fuel car     fc c = new fc();

# High-level abstration
    |-> Abstract Class
    |-> Interface Class
    
    -> separate what from how in abstration.
            Car                 Car c = new FuelCar();
            /\                  Car c = new ElectricCar();
           /  \                 => this is different from previos abstration bcz single 
    FuelCar  ElectricCar            reference(what) have different (how).


// ===================== Abstract Class ============================================

Defination :- 

Points :-
    1. abstract keyword say method for declaration only child class are bound to give defination of method.
    2. Class have any abstract method must use abstract keyword become abstract class.
    3. abstract class can have concreate class also.
    4. abstract class  can not have object bcz it abstract method can't execute.
    5. child class extends abstract class and provide implementation of abstract methods.
    6. @Override annotation for better pratices.

    Example :-
        abstract public class Car{
            public void start(){
            }
            public abstract void acclerate();
            public abstract void brake();
        }
// ===================== Interface ===================================
-> syntax are similar to abstract class.
        -> class = Blueprint of object
        -> Interface = not related to object
        
-> interface say itself contract.
-> interface -> pure what (pure abstration).
-> inerface represent -> responsibility
                      -> capacibilites
                      -> roles
-> Nomenclature => comparable, serializable => at end able, means ability
                => we can create flyable, walkable, runnable.

Points :-
    1. using inteface keyword.
    2. all methods are abstract method, but no need to used abstract keyword.
    3. new java, allows default,private,static method in interfaces.


⭐⭐ => all rule about visibility(access modifier) of method in child class during override method and parameter and return types.
    -> subclass method never narrow down acces modifier of super.

### abstration v/s   encapsulation

    data/implementation hiding  | data security using access modifier


// ===================================== Polymorphism ================================
                                            /  \              Real life object 
                                           /    \                    |
                                        Many   form             they have many forms

=> same object behaves/response differently on same command if one parameter is Changed.

Defination of Polymorphism :- 

# Compile-Time Polymorphism :- method overloading

# Run-time Polymorphism :- method override

# static binding v/s dynamic binding

⭐⭐ => during override 
1. static method can not be override.
        class A{
            static void run(){

            }
        }
        class B extends A{
            static void run(){

            }
        }
        main(){
            A a = new B();
            a.run(); // it should run class B method but is run class A method bcz it is static allocated only once. they belong to class we have here reference of A class.
        }
2. private method can not be overridden.
3. final method can not be overridden.
        class A{
            final void run(){

            }
        }
        class B extends A{
            void run(){ // final method overridden error.

            }
        }
        main(){
            A a = new B();
            a.run();  
        }
4. field/variable cannot be polymorphic :- it always check reference type of variable 
        class A{
            int x = 10;
        }
        class B extends A{
            int x =20;
        }
        main(){
            A a = new B();
            System.out.println(a.x); // print class A 's x value reference is of class A.
        }