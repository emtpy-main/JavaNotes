// ==================  Immutable Class ===================

-> a class in which once variable & method are declared & initalize then we can'nt modify it.

⭕: Rules of Immutable Class :-
    1. Make Class as final bcz it prevent inheritance through which method cannot be override. (method modification prevented).
    2. Make instance variable as final and private.
    3. No setter which prevent obj's instance variable modification.

    Eg:-
        final class Student{
            private final int age;
            private final String name;
            private final College college;

            Student(int age,String name,College college){
                this.age = age;
                this.name = name;
                this.college = college
            }

            // No setter, only getters
            int getAge(){ return this.age;}
            String getName(){ return this.name;}
            College getCollege(){ 
                return this.college;
            }
        }

    let suppose, you have reference variable to non-primitive.How it break Immutablility.

        class College {
            String name;
            String address;
            College(String name,String address){
                this.name = name;
                this.address = address;
            }
        }


    ⭕ What if :-

        College college = new College("IITG",assam);
        Student student = new Student(28,"Pratik",college);

        student.getName();
        student.name ; // cannot due to private

        student.getCollege().name = "IITB"; //we are able to modify directly then Student class loos immutability.

        college.name; // original college object name getchanged.

            - Reason :- due to shallow Copy
                means once college object created in heap, then reference instance variable in student also point to that object. getCollege() return new temp reference variable that also point to same object in heap.
                that is why we are sharing same single object & modification reflected.


    ⭕ Solutions :-
        1. Make college class itself Immutable.
        2. use Defensive Copy in constructor/getters.

⭕ Defensive Copy :-

    -> make always new object in class with same data during assigning.

    corrected :-
        final class Student{
            private final int age;
            private final String name;
            private final College college;

            Student(int age,String name,College college){
                this.age = age;
                this.name = name;
                this.college = new College(college.name,college.address); // ⭐
            }

            // No setter, only getters
            int getAge(){ return this.age;}
            String getName(){ return this.name;}

            College getCollege(){ 
                return new College(this.college.name,this.college.address);
            }
        }

    then :-
        College college = new College("IITG",assam);
        Student student = new Student(28,"Pratik",college);

        student.college.name = "IITB";

        but college.name are still IITG bcz here each time new object are create separately.

    this is called deep copy.



