package com.tnsif.dayone;

public class SwitchDemo {
	public static void main(String[] args) {
		int userInput=2;
		
		switch(userInput)
		{
		case 1: System.out.println("Current Recharge Details");
		break;
		
		case 2: System.out.println("Current recharge expiry");
		break;
		
		case 3: System.out.println("New Offers");
		break;
		
		case 4:System.out.println("Talk to our customer support person");
		break;
		
		default:
			System.out.println(userInput + "is not a valid input given by you");
			
		}
	}

}
