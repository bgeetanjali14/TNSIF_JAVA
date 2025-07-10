package com.tnsiif.dayseven.overriding;

public class CoVariantOverriding {
 class Colour{
	 Colour s;
	 protected Colour getColour() {
		 Colour s = new Colour();
		 return s;
		 
	 }
 }
 
 class Red extends Colour{
	 protected Colour getColour() {
		 Colour s = new Red();
		 return s;//
	 
 }
}
 
 public static void main(String[] args) {
//	 TODO auto generated method  
	 
 }
}
