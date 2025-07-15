package Runnable;

public class RunnableDmeo {
	public static void main(String[] args) {
		
		Runnable r=new UsingRunnable(10, 1, "hii");
		
		Thread t1=new Thread(r);
		t1.start();
		
//		Runnable r1=() -> System.out.println("runnable with lamba expression ");
//		new Thread(r1).start();
		
		

	}


}
