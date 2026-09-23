// ==================== Nested Class =========================
-> class inside another class.

## why do we need nested class ? 
    1. Logical grouping :- a class make sense when it comes under outer class.
    2. Better access to Outer Class :- Inner class can access private member of outer class.

Type of nested class :-
 1. static class
 2. Inner class
 3. Local class
 4. Anonymous class

1️⃣: static nested class :-

    - we can create Inner class object without outer class object.
    - static inner clas only can access static member of outer class.
    - Inner class behave just like normal class it can extends classes and implement interface, only it name change i.e Outer.Inner.
    - Inner class can contain static method & variable.
    - inner class can have all access modifier in variable or method. 


        Eg:- class Outer{
                int x;

                static class Inner{

                    void fun(){
                        System.out.println("Inner class method");
                    }
                }
            }

        => object creation : Outer.Inner inner = new Outer.Inner();

    - for accessing non-static variable, we have to pass Outer class reference.

        Eg :- void fun(Outer outer){
                System.out.println(outer.x);
            }

   
    ## UseCase of static nested class :-
        1. As helper class for any outer class.
        2. Builder Design pattern
        3. If you want to have static method inside a nested class.
        4. Request/Response DTO

        Eg:- class BankAccount{
                private static class InterestCalculator{
                    static double calculateYearly(double principal,double rate){
                        return principal*rate;
                    }
                }

                public double computeInterest(double principal){
                    return InterestCalculator.calculateYearly(principal,0.09);
                }
            }

2️⃣: Inner nested class :-

    => any nested class which is not static.
    => Inner class belong to outer Class object.

        Eg :- class Outer{

                int x;

                class Inner{
                    void fun(){
                        System.out.println();
                    }
                }
            }
        
        💯:- object creation of inner class

        1.  Outer outer = new Outer();
            Outer.Inner inner = outer.new Inner(); //
            inner.fun();

        2.  Outer.Inner inner = new Outer().new Inner(); // here we don'nt have outer class reference

    - inner class always have reference to outer class. duing memory allocation two separate object are created in heap then connect through this keyword.
    - inner class can static & non-static member of outer class.
    - upto java 16, inner class can not have static member , but newer java allowed it. why ??
      
      - static member works that they belong to class/ shared among object.
        but in inner nested class, each inner class associated with outer class object i.e one to one mapping between them.
        therefore, i create confusion that static member shared among outer class object OR each inner class object have separate static member.
        to avoid it java restricted that static member in inner class can created with static class.
        we now further now newer java solve this problem.

    Eg:- class Outer{
            int x = 10;
            class Inner{
                // internally compiler add (Outer outer; )reference to Inner class by default
                int x = 20;

                void fun(){
                    System.out.println(x);
                    System.out.println(Outer.this.x);

                }
            }
        }

    
3️⃣: Local Class :-

    => if class created inside a code block like if statement , for loop ,block, function etc is called Local class.
    => usage : if you want to logical group function in inside other function in class.
    => local class behave like normal class.
    => ⭐⭐ Effective final variable 

        💯:- watch video to know reason; important.

        Eg:- class Outer {
                void greet(){
                    int x = 5; // it must be final if want local class use it.
                    x++; // is create problem if used in local class.
                    class Local{
                        void sayHello(){
                            System.out.println("Hello");
                        }
                        void sayHi(){
                            System.out.println("Hi");
                            System.out.println(x); // if i change x it show error.
                        }
                        void sayTakeCare(){
                            System.out.println("TakeCare");
                        }
                    }

                    Local local = new Local(); // local class object created inside class declaration block
                    local.sayHello();
                }
            }

            main(){
                Outer outer = new Outer();
                outer.greet();  //print Hello;
            }

4️⃣: Anonymous nested class :-
    => agr kisi kaam ko ek baar krna hai toh Anonymous class ka use krte hai.
    => anonymous class does not have construtor bcz construtor name should be same as class i.e class have no name.
    => if anonymous class are created inside function then to use local variable of function we havve to declaration variable as Effective final.

    Eg:- class Person{
            void introduce(){
                System.out.println("Hi, I am a person");
            }
        }

        if i want that introduce should print 'Hi i am a guest', then what i can do.

        1. create another class overridden introduce method.
            class Guest{
                @override
                void introduce(){
                    System.out.println("Hi I am a guest");
                }
            }

            main(){
                Person p = new Guest();
                p.introduce(); // print "hi i am a guest"; 
            }
        
        2. but it can done using Anonymous class bcz it need only one time to print usage.

            class Main{
                public static void main(String[] args){

                    Person p = new Person(){  // anonymous class starts
                        int x=10;
                        @override
                        void introduce(){
                            greet();
                            System.out.println("Hi I am a guest");
                        }
                        void greet(){
                            System.out.println("Hi I am a greeting");
                        }
                    }

                    p.introduce(); // overidden method execute;
                    p.x; // error bcz p reference to Person class that does not have x varaible means in anonymous class we can only access those variable which are in object class or overridden in anonymous class.
                    p.greet(); // cann't show error 
                }
            }


