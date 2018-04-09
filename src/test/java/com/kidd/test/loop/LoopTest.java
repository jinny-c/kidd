package com.kidd.test.loop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;
import java.util.Set;
import java.util.TreeSet;

public class LoopTest {
	private static Map<String, String> loopMap = new HashMap<String, String>();
	private static List<String> loopList = new ArrayList<String>();
	private static String[] loopArray = new String[4];

	static {
		loopMap.put("key1", "val1");
		loopMap.put("key2", "val2");
		loopMap.put("key3", "val3");
		loopMap.put("key4", "val4");

		loopList.add("list1");
		loopList.add("list2");
		loopList.add("list3");
		loopList.add("list4");
		loopList.add("list4");
		//loopList.add(null);

		loopArray[0] = "arr1";
		loopArray[1] = "arr2";
		loopArray[2] = "arr3";
		loopArray[3] = "arr4";
	}

	public static void main(String[] args) {
		int mtd = 0;
		mtd = 4;
		
		switch (mtd) {
		case 1:
			forEachMethod();
			break;
		case 2:
			whileMethod();
			break;
		case 3:
			asArrayMethod();
			break;
		case 4:
			removeMethod();
			break;
		case 5:
			forMethod();
			break;
		default:
			collectionMethod();
			break;
		}
	}


	private static void forEachMethod() {

		// 使用 entrySet 遍历 Map 类集合 KV，而不是 keySet 方式进行遍历
		for (Map.Entry<String, String> entry : loopMap.entrySet()) {
			System.out.println("loopMap:" + entry.getKey() + entry.getValue());
		}
		for (String st : loopMap.keySet()) {
			System.out.println("loopMap.keySet:" + st);
		}
		for (String st : loopMap.values()) {
			System.out.println("loopMap.values:" + st);
		}

		for (String st : loopList) {
			System.out.println("loopList:" + st);
		}
		for (String st : loopArray) {
			System.out.println("loopArray:" + st);
		}
	}

	private static void whileMethod() {
		Iterator<Map.Entry<String, String>> entries = loopMap.entrySet()
				.iterator();
		while (entries.hasNext()) {
			Map.Entry<String, String> entrie = entries.next();
			System.out.println("loopMap：" + entrie.getKey() + entrie.getValue());
		}
	}

	private static void collectionMethod() {
		System.out.println("loopMap.isEmpty():" + loopMap.isEmpty());
		System.out.println("loopList.isEmpty():" + loopList.isEmpty());
		System.out.println("loopArray.length=" + loopArray.length);
		
		List<String> loopLink = new LinkedList<String>();
		
		System.out.println("loopLink.isEmpty=" + loopLink.isEmpty());
		System.out.println("需要遍历，loopLink.size=" + loopLink.size());
	}

	private static void asArrayMethod() {
		// System.out.println("loopArray:" + loopArray);
		System.out.println("loopArray:" + Arrays.asList(loopArray));
		System.out.println("loopList:" + loopList);
		// System.out.println("loopList:" + Arrays.asList(loopList));
		System.out.println("loopMap:" + loopMap);
		// System.out.println("loopMap:" + Arrays.asList(loopMap));
	}

	private static void removeMethod() {
		Set<String> hSet = new HashSet<String>();
		hSet.addAll(loopList);
		System.out.println("hSet:" + hSet);
		// 非null值
		Set<String> tSet = new TreeSet<String>();
		tSet.addAll(loopList);
		System.out.println("tSet:" + tSet);
		
		//System.out.println("tSet:" + new ArrayList(new TreeSet(loopList)));
	}
	
	private static void forMethod() {
		if (loopList instanceof RandomAccess) {
			for (int i = 0; i < loopList.size(); i++) {
				System.out.println("for:"+loopList.get(i));
			}
		} else {
			Iterator<?> iterator = loopList.iterator();
			while (iterator.hasNext()) {
				System.out.println("interator:"+iterator.next());
			}
		}
	}
	
}
