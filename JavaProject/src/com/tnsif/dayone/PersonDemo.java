package com.tnsif.dayone;

import java.util.Scanner;

public class PersonDemo {
	public static void main(String[] args) {
		Scanner ob=new Scanner(System.in);
		
		String name;
		System.out.println("Enter name");
		name= ob.nextLine();
		
		System.out.println("Enter age:");
		int age= ob.nextInt();
		
		System.out.println("Enter gender:");
		String gender= ob.next();
		
		System.out.println("Enter income");
		int income= ob.nextInt();
		
		System.out.println("Enter mobile no.:");
		long mobileno.= ob.nextLong();
		
		Person person = new Person();
		person.setName(name);
		person.setAge(age);
		person.setGender(gender);
		peson.setIncome(income);
		person.setMobileno.(mobileno.);
		
		//using getter method
		System.out.println(person.getName());
		System.out.println(person.getAge());
		System.out.println(person.getGender());
		
		//using toString()
		System.out.println(person);
		


		
		
	}

}
