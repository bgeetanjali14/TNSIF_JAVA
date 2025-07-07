package finalDemo;

public class FinalClass {
	void show ()
	{
		System.out.println("Final class cannot be inherited");
	}
}


//class Student extends FinalClass{
//	
//}



public class FinalClassDemo {

	public static void main(String[] args) {
		
		FinalClass f=new FinalClass();
		f.show();

	}

}
