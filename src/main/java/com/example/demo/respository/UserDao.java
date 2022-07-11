package com.example.demo.respository;

import java.util.LinkedHashMap;
import java.util.Map;



public class UserDao {
	public Map<String, Map<String,String>> users;
	{
		//A01, 1234, amdin,normal,ROLE_manager
		Map<String, String> info1= new LinkedHashMap<>();
		info1.put("password","$2a$10$AVqlik75aZH7Ei0q5otyCeAv2ZbHAfiDjv2dopOOc1hSJWLi3QUou");
		info1.put("authority","admin,normal,Role_manager");
		
		//A01, 5678, normal,Role_employee
		Map<String,String> info2 =new LinkedHashMap<>();
		info2.put("password","$2a$10$lSkDD7IUtE71jCqllbr.GOz7pXuZzS.UQwwfgHUX43FyEDsheO6s6");
		info2.put("authority","normal,Role employee");
		
		users= new LinkedHashMap<>();
		users.put("A01",info1);
		users.put("A02",info2);
		
		System.out.println(users);
		
	}

}
