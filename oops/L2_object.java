// about object its size and structure
01. reference of string take 4 byte or 8 byte.
2. what include in object size => Header size
                                => exact fields
                                => padding
3. object header stores about metadata => Markword (8 byte): lock, synchronization, garbage collection
                                        => class pointer (4 byte or 8 byte depends on JVM ) : reference store any object
4. exact data means actual reference variable. (string=> 4 byte, number => 4 or 8 byte).
5. padding = add additional byte so that overall object memory is multiple of 8. 

example = class A{
    string name ; => 4 byte.
    Number rollno ; => 4 byte.
    int age ; => 4 byte.
    string college; => 4 byte.
}

header size = 8 byte + 4 byte = 12 byte.
exact field = 16 byte
total = 28 byte 
padding = 4 byte 
total = 32 byte.

6. call by value / call by reference , in java there is not call by reference it only have call by value , why java dont have call by reference ?

Example = call by value
class Main{
    public static void main(String* args){
        int x=10,y=20;
        System.out.println(x + " " + y);
        addItem(x,y); // java only have this call by value not call by reference like c++ have addItem(&x,&y);
        System.out.println(x+ " " + y);
    }
    static addItem(int x,int y){ // new local variable
        x += 10;
        y += 10;
    }
}

============= output =======================
10 20
10 20

Example = call by reference

class Main{
    public static void main(String* args){
        Randome r = new Random(10,20);
        System.out.println(x + " " + y);
        addItem(r); // java only have this call by value not call by reference like c++ have addItem(&x,&y);
        System.out.println(x+ " " + y);
    }
    static addItem(Random r){ // new local variable reference to same object
        r.x += 10;
        r.y += 10;
    }
}
class Random{
    int x,y;
    Randome(int x,int y){
        this.x=x;
        this.y=y;
    }
}

============= output =======================
10 20
20 30

we can see different behaviour
primitive type = call by value
non- primitive type = call by value but behave like call by reference


7. copy constructor create new object with same data as argument object

Example :  class Random {
    int x,y;
    Random ( int x, int y){
        this.x = x;
        this.y = y;
    }
    Random (Random r){
        this.x = r.x;
        this.y = r.y;
    }
}

this does not share same memory but Random r3 = r2  have same memory only reference changes


 notes : object only storage variable in heap not methods 