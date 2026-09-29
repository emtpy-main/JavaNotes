public class L0_demo{
    public static void main(String[] args){
        System.out.println("Hello java runs");
        D d = new D();
        d.fun();
    }
}

interface A{
                void fun();
            }
            interface B extends A{
                default void fun(){
                    System.out.println("B");
                }
            }
            interface C extends A{
                    default void fun(){
                        System.out.println("C");
                    }
                
            } 
            class D implements B,C {
                public void fun(){
                    // System.out.println("D");
                    B.super.fun();
                }
                
            }