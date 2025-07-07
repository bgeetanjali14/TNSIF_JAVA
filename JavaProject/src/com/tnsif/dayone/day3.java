package com.tnsif.dayone;

public class day3 {
	public static void main(String[] args) {
		int beg=5;
		int end=20;
		
		for(int i=beg; i<=end; i++) {
			for(int j=1; j<=10; j++)
			{
				System.out.println(i + "*"+ j + "="+ i*j);
			}
			System.out.println();
		}
	}

}
