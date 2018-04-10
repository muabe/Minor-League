import java.lang.reflect.*;

public class Arraycopy 
{
	public static void main(String[] args)  throws Exception 
	{
		int[] targetMethods = {-1,1,2};
		int[] superMethods = {3,4,5};
		int[] allMethods = new int[6];

		System.arraycopy(targetMethods, 0, allMethods, 0, targetMethods.length);
		System.arraycopy(superMethods, 0, allMethods, targetMethods.length, superMethods.length);

//		for(int i=0;i<allMethods.length;i++){
//			System.out.println(allMethods[i]);
//		}

		A a = new A();
		a.parentsClass = C.class;
		Method[] m = a.getMethods(a.getClass());
		for(int i=0;i < m.length; i++){
			System.out.println(m[i].getName());
		}
		
	}

	static class A extends B
	{
		public Class<?> parentsClass;

		public void what(){
		}

		private void is(){
		}
		protected void that(){
		}

		public Method[] getMethods(Class<?> targetClass) throws Exception{
			if(parentsClass!=null){
				Class<?> superClass = targetClass.getSuperclass();
				System.out.println(parentsClass.toString());
								System.out.println(superClass.toString());
				if(!parentsClass.equals(superClass) || Object.class.equals(superClass)){
					Method[] superMethods = getMethods(superClass);
					Method[] targetMethods = targetClass.getDeclaredMethods();
					Method[] allArray = new Method[targetMethods.length+superMethods.length];
					System.arraycopy(targetMethods, 0, allArray, 0, targetMethods.length);
					System.arraycopy(superMethods, 0, allArray, targetMethods.length, superMethods.length);
					return allArray;
				}
			}

			return targetClass.getDeclaredMethods();
		}
	}

	static class B extends C{
		public void ok(){
		}

		public void hey(){
		}
	}

	static class C{
		public void man(){
		}
	}
}
