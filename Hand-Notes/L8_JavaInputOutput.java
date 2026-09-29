// =========================== Standard InputOutput of Java ============================

=> how input/output data in/out of outer environment.

⭕: Types of I/O :-
    1. Console I/O 
    2. File I/O 
    3. Network I/O 
    4. Memory I/O 

in this, section we focue on Console I/O, read file I/O next lecture.

⭕ Internal of System.out.println() :-

    - println() = method
    - out must be object so that println() method access
    - System is class in which "out" is static variable reference to (PrintStream Class) ; i.e accessed directly.
    - println() take String argument only if not converted.
    - how we get access to System clas without importing bcz System Class written in java.lang package.
    - java.lang package are imported automatically.
    - PrintStream class have
        |- println() => new line
        |- print() => without new line
        |- printf() => use for formatting with output

        Eg:- conceptual overview 
                class System{
                    static public final PrintStream out;
                    static public final PrintStream err;
                }
    
    - err are used for error printing.  Eg :- System.err.println();

⭕ Input from user :-
    
    - intially, java are meant for server side programming or GUI programming but it never think that is can become console language. 
    - that is why intially itself does not provide library.
    - to know deeply we have to start from beginning.
        - System class also have "in" variable for input.
        - in reference to Inputstream.
            Eg: class System{
                static public final PrintStream out;
                static public final PrintStream err;
                static public final InputStream in;  // for input
            }

        ❔❔❔ Streams :- flow of data
            => java ka I/O pura "stream-based" hai, due to which it read/write multiple source.
            => All of them are "Streams of Bytes"
        Streams
            |-> InputStream - read() : data flow Into program
            |-> OutputStream - write() : data flow Out Of program
                /\
               /  \   these two are abstract classes which tell about only what to read(),write()  
        file console N/w        kaha se input se lete hai (never tells about implementation)
         
    


⭕ Hierarchy of Streams (hey don not explain this section we read it further)
    Streams
        1. InputStream (abstract) -> read();
                |-> FileInputStream -> read(){};
                |-> ByteArrayInputStream -> read(){};
                |-> BufferedInputStream -> read(){};
                |-> DataInputStream -> read(){};

                ⭐⭐ :- System.in have BufferedInputStream as concreate class
                            i.e. :-
                                    Inputstream in = new BufferedInputStream(...);

        2. OutputStream (abstract) -> write();
                |-> FileOutpurStream -> write(){};
                |-> ByteArrayOuputStream -> write(){};
                |-> BufferedOuputStream -> write(){};
                |-> PrintStream -> write(){};


⭕ in-depth :-
    1. for input -> read() method used it read byte from console 

            Problem :- read() reads only one byte at a time.

            Eg:- int x = System.in.read();
                 System.out.println(x);
                 System.out.println((char)x);

            :-- enter Aditya
            but, output are - 65
                            - A 
                            bcz read() take one byte at a time remaining are stored in Inputbuffer.

                Buffer :  [A][d][i][t][y][a][\n]
                acutally in byte [65][102][105][116][121][97][10]


            solution :-  String s="";
                         int c;
                         while(c!='\n'){
                            c = System.in.read();
                            s += (char)c;
                         }
                :- to avoid this ugly code each time java introduce Reader() Class

        ⭕ Reader(abstract) :- reads streams of character are directly.(no byte req.)
                |-> BufferedReader
                |-> InputStreamReader
                |-> FileReader

            
            ⭕ BufferedReader 
                    suppose we have 1000 character words , using older way we have to make 1000 os calls to read each character at time.

                                     |----------- |
                        keyboard -> OsBuffer -> program
                    
                    now, to avoid more OS calls 
                                                    |-------------|
                        keyboard -> OsBuffer -> JavaBuffer -> program

                    Notes : 
                        1. ReadChunk of character from OS Buffer.
                        2. store it in Memory.
                        3. give them to program when required.


                    ⭐⭐⭐ BufferedReader are directly compatible to Inputstream (reading responsibility) 
                    bcz, BufferedReader -> stream of char
                         InputStream -> streams of Byte

                    💯 solution : InputStreamReader class acts as a bridge from byte streams to character streams. It reads raw bytes from an underlying data source (like a file or the console) and decodes them into readable characters using a specified charset (character encoding).


                    ⭐⭐ InputStreamReader 
                                    ByteStream ----> character stream
                            
                            Eg :- 
                                InputStreamReader isr = new InputStreamReader(System.in);
                                BufferedInputStream br = new BufferedInputStream(isr);

                                String name = br.readLine();
                                System.out.println(name); // print Aditya
                
        ✔️✔️ complete flow     
        1. Aditya -> i/p 
        2. OS Buffer -> (65,100,105,116,121,97)
        3. System.in (InputStream) receives bytes
        4. InputStreamReader --> stream of bytes into stream of characters
                ('A','d','i','t','y','a')
        5. BufferedReader --> readLine --> Aditya --> name
        6. Aditya --> o/p   

        ⭕ Limitation of BufferedReader :-

                1. it always reads in character formatting
                        Eg :- BufferedInputStream br = new BufferedInputStream(new InputStreamReader(System.in));
                            String s = br.readLine();
                            int i = Integer.parseInt(s);  

                2. complicated 
        
⭕ Scanner Class :-
    -> after java 1.5
    -> what is done 
            |-> simplify (input)
            |-> different method for int, double,boolean, string input

    -> Scanner class does not comes under Java.io package.
    -> Scanner class comes from Java.util package as a Utility class different from standard I/O.

        Eg. Scanner sc = new Scanner(System.in);
            String name = sc.nextline();

    -> Scanner is wrapper class to simplify.

    -> works with different input

        1. keyboard :- Scanner sc = new Scanner(System.in);
        2. File :- Scanner sc = new Scanner(new File("single.text"));
        3. String :- Scanner sc = new Scanner("10, 20, 30")
                        sc.readline();

    -> Scanner works on basis of "Tokenization"

            :- I/P = Hello I am Pratik. here delimiter are space to take input
    
    -> methods of Scanners :-
            |-> next() :
            |-> nextline() :
            |-> nextInt() :
            |-> nextDouble() :
            |-> nextBoolean() :

    ⭐⭐ Scanner is slower than BufferedReader bcz 
            scanner do  -> tokengization
                        -> regex
                        -> type conversion

            


                
