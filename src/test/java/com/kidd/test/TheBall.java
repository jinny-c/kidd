package com.kidd.test;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TheBall {
	private static final String[] BALL_POND = { "1", "2", "3", "4", "5", "6",
			"7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
			"18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28",
			"29", "30", "31", "32", "33" };
    

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		getBall();
		//getBall();
		//getBall();
		//getBall();
		//getBall();
		//getBall();
	}
	
	private static void getBall() {
		List<String> ballList = new ArrayList<String>(Arrays.asList(BALL_POND));
		int length = ballList.size();
		SecureRandom random = new SecureRandom();
		List<String> ball = new ArrayList<String>();
		int count = 0;
		int temp = 0;
		do {
			temp = random.nextInt(length);
			ball.add(ballList.get(temp));
			
			ballList.remove(ballList.get(temp));
			length = ballList.size();
			count++;
		} while (count < 6);
		
		System.out.println(ball);
	}
	
	private static void getBall2() {
		SecureRandom random = new SecureRandom();
		List<Integer> ball = new ArrayList<Integer>();
		Integer count = 0;
		Integer temp = 0;
		do {
			temp = random.nextInt(33) + 1;
			if (ball.contains(temp)) {
				continue;
			}
			ball.add(temp);
			count++;
		} while (count < 6);

		System.out.println(ball);
	}
}
