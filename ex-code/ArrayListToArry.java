import java.util.*;

public class ArrayListToArry
{
	public static void main(String args[]){
		ArrayList<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(2);
		Integer[] array = new Integer[list.size()];
		list.toArray(array);
		for(int i=0; i<array.length; i++){
			System.out.println(array[i]);
		}

	}
}