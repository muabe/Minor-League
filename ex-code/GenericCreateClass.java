
import java.lang.reflect.*;

public class GenericCreateClass<T>{

   public static void main(String []args) {         
          
     GenericCreateClass<Integer> gcc = new GenericCreateClass<>();
          
	 System.out.println(gcc.getClass().getTypeParameters()[0].getClass().toString());
   }

   
} 
