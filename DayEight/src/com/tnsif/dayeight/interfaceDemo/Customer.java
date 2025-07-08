package com.tnsif.dayeight.interfaceDemo;

//Entity
public class Customer {
	
	private String name;
	private String city;
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name=name;
	}
	
	public void setCity(String city) {
		this.city=city;
	}
	//para constructor

	public Customer(String name, String city) {
		
		this.name = name;
		this.city = city;
	}

	//default Constructor
	public Customer() {
		super();
	}
	
	
	
	

}
