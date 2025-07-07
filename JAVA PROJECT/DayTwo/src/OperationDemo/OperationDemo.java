package OperationDemo;

public class OperationDemo {
public static void main(String[] args) {
		
		int a=10;
		int b=20;
		int x=10;
		
		System.out.println("A and B value before the operator : "
		+ a + " "+ b);
		
		++a;
		
		int c= ++a + b + a--;
		System.out.println("C value after the operator : "+ c);
		
		
		
		int d= c++ + a + b--;
		System.out.println("D value after the operation : "+ d);
		}

}
