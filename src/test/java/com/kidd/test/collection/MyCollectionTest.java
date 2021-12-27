package com.kidd.test.collection;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.RandomAccess;

/**
 * @description list的历遍
 *
 * @auth chaijd
 * @date 2021/12/24
 */
public class MyCollectionTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		testList();
	}
	
	public static void testList(){
		LinkedList<String> ll = new LinkedList<>();
		ll.add("123b");
		ll.add("123a");
		ll.add("123c");
		
		System.out.println(ll.getFirst());
		System.out.println(ll.getLast());
		
	}
	public static void testSet(){
		HashSet<String> hs = new HashSet<>();
		
		LinkedHashSet<String> lhs = new LinkedHashSet<>();
	}
	
	public static void testFitLoop(List collection){
		if (collection instanceof RandomAccess) {
			for (int i = 0; i < collection.size(); i++) {
				System.out.println("for:"+collection.get(i));
			}
		} else {
			Iterator<?> iterator = collection.iterator();
			while (iterator.hasNext()) {
				System.out.println("interator:"+iterator.next());
			}
		}
	}

	
}
