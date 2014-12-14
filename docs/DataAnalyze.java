package com.aeh.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class DataAnalyze {
	static String FILENAME = "/home/niranjan/APL/fijitest/n.txt";


	public static void main(String [] args) throws FileNotFoundException{

		File f = new File(FILENAME);
		Scanner sc = new Scanner(f);
		Map<Integer,Integer> countList = new HashMap<Integer,Integer>();
		Map<Integer,Integer> noList = new HashMap<Integer,Integer>();
		while(sc.hasNext()){
			String s = sc.next();
			s =s.replaceAll("Executing\\[","");
			s =s.replaceAll("\\]", "");
			String s2 = sc.next();
			s2 = s2.replaceAll("\\{", "");
			s2 = s2.replaceAll("\\}", "");
			//System.out.println(s+"  "+s2);


			if(noList.containsKey(Integer.parseInt(s))){
				int temp = noList.get(Integer.parseInt(s));
				noList.put(Integer.parseInt(s),temp++);
				countList.put(Integer.parseInt(s), countList.get(Integer.parseInt(s))+Integer.parseInt(s2));
			}
			else{
				noList.put(Integer.parseInt(s),1);
				countList.put(Integer.parseInt(s), Integer.parseInt(s2));
			}

		}
		for(Map.Entry<Integer, Integer> map:countList.entrySet())
			System.out.println("Average  of :"+map.getKey() + "   :"+map.getValue()/noList.get(map.getKey()));

	}

}
