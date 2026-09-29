//==================== Generics part 1 in java ====================================

-> before generics understand Typesafety in java

⭕ Typesafety :-
    -> Typesafety define rules what operations can perform on that data.
    ->   eg :- Integer -> arithematic operations can work
                String -> string methods
                Animal -> only Animal member method can operates

    -> to avoid illegal operations like.
        eg :-   String s = "Hello";
                int y = s; // error string cann't convert into int;
    
    ⭕ Upcasting :-
        -> parent class references can hold child class object.
        -> making specific object to general object.

            Eg:-        Animal a = new Dog();           [Animal]
                        Dog d = new Dog();                  |
                        a = d ;/* upcast  */             [Dog] 
                although a reference to Dog() but only Animal class method can called only with overridden method in Dog class.
                bcz reference variable which class member are accessed.

    ⭕ Downcating :-
        -> child class reference cann't store parent class object directly bcz compiler does not know (sure about it) it can be downcasted. so do it manually.

            Eg :-
                    Object obj = "Hello";
                    String s = obj ; // compiler error 
            // do manually
                    String s= (String) obj; // can give classCastException

                --> compile time error :-
                        int x="Hello"

                --> runtime error :-

                        Object obj = 10;
                        String s = (String) obj; // give classCastException Integer --> String

            --> compiler time error are better than runtime error due avoid crashed
        
        Eg :-   class Box{
                        int value;
                        Box(int value){
                            this.value = value;
                        }
                        int getValue(){
                            return this.value;
                        }
                }

                -->  Box b1 = new Box(35);
                -->  Box b2 = new Box("Pratik"); // give runtime error because box have only interger type values
                --> to store different type of value in class :-
                    class Box{                  class Box{
                        String value;               boolean value;
                    }                           }

            Problem :- we have to create many classes of each type.

        --> make generics class to store all type of values using Object class :-
            Eg :-
                class Box {
                    Object value;
                    Box(Object value){
                        this.value = value;
                    }
                    int getValue(){
                        return this.value;
                    }
                }

                let Box b1 = new Box(13); // ✔️
                    Box b2 = new Box("Ram"); // ✔️
                    Box b3 = new Box(true);

                    System.out.println(b2 + 4); // runtime error  bcz type information lost.
            ---> hey gpt give various example to show how b1,b2,b3 have lost type infromation and give runtime time than compiler time.


        Problem it is bad to have runtime error bcz now Box class are too general.
    ⭐⭐ upcasting & downcasting work when there is parent-child relationship.
            String s = (String) b1.getValue(new Integer(24)); // never cast bcz String , Integer are siblings.

⭕ Limitations of using object as universal type :-
    -> type information is loss.
    -> wrong object could be inserted.
    -> casting became necessary when reading.
    -> Many errors shift to Runtime.


⭕ What is Generics ?


⭕ Generics Class :-

                    |---------------> Type parameter
    Eg :-   class Box<T> {
                private T value;

                Box(T value){
                    this.value = value;
                }
                public T getValue(){
                    return this.value;
                }
                public void setValue(T value){
                    this.value = value;
                }
            }

            Box b1 = new Box(8); // show warning bcz compiler still assumes T as Object.
            Box<Integer> b1 = new Box<>(80);
                    |-----------------|--------------------> type argument
            Box<String> b2 = new Box<>("Hello");
            Box<Boolean> b3 = new Box<>(true);

            b1.getValue() + 5 ; // 13 --> no error
            b2.getValue() + 5 ;  //  Hello5 --> no error    

        // difference between Object general class vs   Generics
                a. Object class general =>  b1.substring()  here b1 is object class reference which contains interger values so it does not compile time. but at run-time  interge does not provide substring() method give runtime error.

                b. generics => b1.substring() here b1 is Integer class reference which contains interger value so it instantly show compile time error.
    
    Eg:- pair class :-
            class Pair<T,Y>{
                T first;
                Y second;
                Pair(T first,Y second){
                    this.first = first;
                    this.second = second;
                }
            }

            Pair<Integer,String> p1 = new Pair<>(23,"Pratik");
            System.out.println(p1.first + " " + p1.second);


⭕ Generics Method :-
    
    --> type inference : java automatically infer type of argument in Generics methods.

        Eg:- public static void main(String[] args){
                    Integer y = getResult(23);   // 
                    printPair(23,"pratik");

            }
            public static <T> T getResult(T x){
                return x;
            }
            public static <T,U> void printPair(T first,U second){
                System.out.println(first + " " + second);
            }  

⭕ Bounds on Generics :-


    --> we can bound which class can passed as Type parameter in Generic class.
            Eg :- in box class, we can pass integer, long, float, double, String, boolean class as value type.
                    but we want box class can contain only Integer, Float, Double, Long.

                we know that Integer, Float, Double, Long extends Number class.

    --> apply Number as upper bound.
    --> only subclass of upper bound can used.

            Eg :-  class Box<T extends Number>{
                        T value;
                        Box(T value){
                            this.value = value;
                        }
                        public void printDouble(){
                            System.out.println(value.doubleValue());
                        }
                    }
                    Box<Integer> b1 = new Box<>(80);
                    Box<Double> b2 = new Box<>(89.22);
                    Box<String> b3 = new Box<>("Pratik"); // compile time error


    --> how to bound interface generics class can implement :-
                <T extends Class & interface1, interface2, interface3>


            Eg :-  class Animal{
                        void display(){
                            System.out.println("Displaying Animal");
                        }
                   }
                   interface Swimmable{
                        void swim(){
                            System.out.println("Swimming...");
                        }
                   }
                   class Dog extends Animal{

                   }
                   class Fish extends Animal implement Swimmable{
                        @Override 
                        public void swim(){
                            System.out.println("Fish is Swimming...");
                        }
                   }

                Define : Generic with bounding interface :-

                    class Box<T extends Animal & Swimmable>{
                        T value;
                    }
                usage :-
                    Box<Dog> b1 = new Box<>(); // error bcz box generic bounded to implement Swimmable interfacee but dog class does not holds it.
                    Box<Fish> b2 = new Box<>();

    ---> what if i have not bound on class then how i can declare interface in Generic class
                   


    