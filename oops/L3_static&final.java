// ================ static notes ======================

%% Background
class Student{
    string name;
    int age;
    int rollno;
    string college;
}

let suppose these 1000 student object having from same college "IITG" , then each student share separate college variable college (4 byte * 1000 = 4000 byte extra storage is unnecessary) , is there any way to reduce this extra space.
Yes, here we use static keyword , 

Notes :-

*** static variable ***

1. static keyword  make variable class level  variable not object level i.e each object share same memory location variable does not belong to object
2. using static like static string college 
3. can accessed using
    Student s = new Student();
    s.college = "IITG";
    Student.college = "IITG" ; // class access
4. static variable not storage in heap memory
5. static variable are class variable not instance variable.

when static variable get memory allocated ?? 

*** static method ***

6. static method are accessed and shared among all class. 
7. static method are accessd using Class name.
8. static method can use only static method or variable. (give reason behind it)
9. static method does not have access to this keyword.

*** static blocks ***

10. static blocks are used to intialize static variable.
11. as class loads static blocks get executed once. all static things runs.


Example:

class Student{
    String name;
    int rollno;
    int age;
    static String college; // or static String college = "IITG";

    // static blocks
    static{
        college = "IITG";
    }

    Student(String name, int rollno,int age){
        this.name = name;
        this.rollno = rollno;
        this.age = age;
    }
}

static void main(String[] args){
    Student s = new Student("Pratik",21,20);
    Student.college = "IITG" // static blocks used to avoid this .
}


*** what can be static ***
a. variable ✅
b. method ✅
c. parameter ❌ => reason : these are local variable , stored in stack , not relation with heap or object, use make those things static which want to move object level to call level 
d. class ❌ => nested class can static but root level class can not be static, bcz class are part of packages

****************** why main function is static in java ***************************
becauze static method does not need obj to called is can accessd using class name
there JVM cound not create Object of main class , bcz we are reponsible for object creation only
there java make main static so that jvm can access it without object creation

in main String[] is used for command line input like => - javac demon.java Aditya rohit pratik
//============================= Final keyword notes ===============================================

%% Background
class Random {
    final double PI = 3.14;
}


suppose someone change values of PI to 2.14 which should not happened therefore final keyword helps here.

Notes :-

*** variable ***

1. final make variable constant, can not modified
2. Nomenclature : 
    for one word = make all capital like PI not pi.
    for two word = make like PI_VALUE use underscore as separator
3. java allows only once declaration and defination of final variable, so it must be done in one time.
    final int PI = 3.14 or final int PI; PI = 3.14

*** parameter ***
4. if contain final then method can not modify local variable
    void addItem(final int a){
        a = a + 10; // error can'nt modify
    }


*** method ***

*** class *** 