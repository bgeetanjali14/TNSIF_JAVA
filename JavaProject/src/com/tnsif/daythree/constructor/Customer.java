package com.tnsif.daythree.constructor;

import java.lang.reflect.Constructor;

/**
 * 
 */
public class Customer {
	
	private String customerName;
	private int customerId;
	private String cutomerCity;
	
	//default constructor
	public Customer()
	{
		System.outprintln(This is a default Constructor.class.....!);
		}

	
	//para constructor
	public Customer(String customerName, int customerId, String cutomerCity) {
		this(); //calling default constructor
		
		System.out.println("Hello this is para constructor");
		this.customerName = customerName;
		this.customerId = customerId;
		this.cutomerCity = cutomerCity;
	}
           
	  
	
}
