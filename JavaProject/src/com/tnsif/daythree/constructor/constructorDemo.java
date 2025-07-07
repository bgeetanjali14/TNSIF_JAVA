package com.tnsif.daythree.constructor;

import java.util.Scanner;

public class constructorDemo {
	
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		
		int id;
		System.out.println("Enter Customer id");
		sc.nextInt();
		
		String name;
		System.out.println("Enter Customer name");
		sc.nextLine();
		
		String city;
		System.out.println("Enter Customer city");
		sc.nextLine();
		
		Customer c1=new Customer();
		Customer c2=new Customer("Geetanjali",1,"Chandrapur");
		Customer c3=new Customer(2,"Pune");
		
	}

}
