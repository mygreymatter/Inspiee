package com.mayo.inspiee.utils;

import java.util.ArrayList;

public class StringUtils {
	
	public static String getNameInCaps(String personality) {
		StringBuilder name = new StringBuilder();
		String subString;
		int at;
		char first;
		int len = personality.length();
		int count = 0;

		for (int index = 0; index < len; index++) {
			if (personality.charAt(index) == '_')
				count++;
		}

		for (int index = 0; index <= count; index++) {
			if (personality.contains("_")) {
				at = personality.indexOf("_");
				first = Character.toUpperCase(personality.charAt(0));
				subString = personality.substring(1, at);
				name.append(first).append(subString).append(" ");
				personality = personality.substring(at + 1, len);
				len = personality.length();
			} else {
				first = Character.toUpperCase(personality.charAt(0));
				subString = personality.substring(1, len);
				name.append(first).append(subString);
			}
		}
		return name.toString();
	}
	
	public static ArrayList<String> getArrayList(String str){
		if(str.isEmpty())
			return null;

		String sub;
		ArrayList<String> names = new ArrayList<String>();
		char comma = ',';
		int initial = 1;
		int commaAt = str.indexOf(comma);
		
		while(commaAt != -1){
		
			sub = str.substring(initial, commaAt);
			System.out.println("Sub :" + sub);
			names.add(sub);
			
			initial = commaAt + 2;
			commaAt = str.indexOf(comma,initial);	
		}
		
		if(!str.substring(initial).isEmpty()){
			int lastBracket = str.length();
			sub = str.substring(initial,lastBracket-1);
			System.out.println("Sub :" + sub);
			names.add(sub);
		}
		System.out.println("Names Size: " + names.size());
		
		return names;

		
	}
}
