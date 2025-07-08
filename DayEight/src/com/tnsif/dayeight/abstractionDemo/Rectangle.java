package com.tnsif.dayeight.abstractionDemo;

public class Rectangle extends Shape {
	
	float width;
	float height;
	
	
	public Rectangle() {
		width=3.2f;
		height=2.0f;
		
	}
	public Rectangle (float width, float height) {
		this.width=width;
		this.height=height;
	}
      
	@Override
	public void calArea() {
		this.area= width*height;
	}
}
