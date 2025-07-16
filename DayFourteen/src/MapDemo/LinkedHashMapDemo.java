package MapDemo;

import java.util.LinkedHashMap;

public class LinkedHashmapdemo {

	public static void main(String[] args) {
		
		
		LinkedHashMap ht=new LinkedHashMap();
		ht.put(101, 1000);
		ht.put(1012, 23.34);
		ht.put(203, "Pune");
		ht.put(401, 10);
		ht.put(101, 3000);
		ht.put(102, 3000);
		
		
		System.out.println(ht);

	}

}