// ================= Object Class =========================

-> present in Java.lang package.
-> it is parent of all class in java whether userdefined or predefined.
-> Every class in java inherits from Object class.

-> why java is need root level parent Class.
    -> common behaviour which are common to all class.
    -> Object obj = new Student(); // usage : we expect student object anywhere but Student reference out of compiler's bounds (polymorphism).
    -> 

⭕ Common behaviour Object class defines :-

    1. Core methods
        |-> toString()
        |-> equals()
        |-> hashCode()
        |-> getClass()
    
    2. Cloning
        |-> clone()
    
    3. Garbage Collection  
        |-> finalize()
    
    4. Threads
        |-> wait()
        |-> notify()
        |-> notifyAll()

⭕ Core method of Object class :-

    1. toString() 
        - Convert anything to String.
            public String toString(){

            }
        - return String representation of any object.

        - default implementation of toString() 
            => className@Hexcode
            for student class it print student@78a72d
        
    2. equals() 
        - compare 2 object & return true/false
        - signature :-
            class Object {
                public boolean equals(Object obj){
                    // Default implementation
                            |-> compares reference. //⭐
                }
            }

            Eg :- Student s1 = new Student("Aditya",21);
                  Student s2 = new Student("Aditya",21);
                  s1.equals(s2); // => return false 
                  s1.equals(s1); // => return true
            
        - this internally use == to compare.

            Eg:-
                @override
                public String equals(Object obj){
                    if(obj == this) return true; // same object always equals
                    if(obj == null) return false; // nullexception
                    if(obj.getClass() == this.getClass()) // to avoid ClassCastException i.e this, obj can be different class object
                        return false
                    Student s = (Student)obj;  // why this is it requruied.
                    return (obj.name == this.name && obj.age == this.age);
                }

    3. hashCode() 
        -> return an integer of an object in Hexadecimal format.
        -> same of same class.
        -> used in Collection framework.
        -> ⭐ if two object are equals then there hashCode must be equal but reverse might not be true.

            in java, Student s1 = new Student("pratik",19);
                     Student s2 = new Student("pratik",19);

                     if s1.equals(s2) are true then
                     s1.hashCode() == s2.hashCode()
        Signature :- 
            class Object{
                public int hashCode(){
                    // default implementation
                }
            }
    ⭕: in Objects class from Java.util package provide hash() function that return hash of given arguments.

    4. getClass() :-
        -> return runtime class of an object
        -> in java, have class named class
        -> getClass() cannot be override

        Signature :-
            class Object{
                public final Class<?> getClass(){
                    // default;
                }
            }

            Eg :- Student s = new Student();
                    s.getClass().getName(); // return class name;

    ->⭐ instanceOf operator => Check if an object is instance of a class or any of its subclass.
            Eg :- s1 instanceOf Student; => true
                  s1 instanceOf Object; => true

    5. Clone() :-
        -> create Copy of an object.
        -> any class can override clone() if class implementated Clonable interface.
            -> java wants Not Every Object should be clonable.
                        |-> DataBase
                        |-> Threads
            -> Clonable interface have no method to override, these interface are called "marker interfaces"
                        interface Clonable{

                        }

            -> clone() method always do shallow copy. for deep clone, override clone().

        Signature :-
            class Object{
                protected Object clone() throws CloneNotSupportedException{
                    // default
                }
            }
        
            Eg :- 
                    class Student implement Clonable {
                        @override
                        protected Object clone() throws CloneNotSupportedException {
                            return super.clone();
                        }
                    }

                    Student s1 = new Student();
                    Student s2 = (Student) s1.clone();
    
    6. finalize() :-
        -> Garbage Collection
        -> deprecated bcx -> unpredictable
                          -> unsafe
                          -> unrealiable

    rest are studied later lectures.

    Array -> non-primitives datatypes
    Arrays -> class used for array operations.

        





    
