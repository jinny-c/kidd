package com.kidd.test.pattern.structural.twelve;

import java.util.concurrent.ConcurrentHashMap;

public class FlyWeightFactory {
	private static ConcurrentHashMap<String, FlyWeight> allFlyWeight = new ConcurrentHashMap<String, FlyWeight>();

	public static FlyWeight getFlyWeight(String name) {
		if (allFlyWeight.get(name) == null) {
			synchronized (allFlyWeight) {
				if (allFlyWeight.get(name) == null) {
					System.out.println("Instance of name = {} does not exist, creating it");
					FlyWeight flyWeight = new ConcreteFlyWeight(name);
					System.out.println("Instance of name = {} created");
					allFlyWeight.put(name, flyWeight);
				}
			}
		}
		return allFlyWeight.get(name);
	}
}
