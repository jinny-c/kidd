package com.kidd.test;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TheBall {
	private static final String[] RED_BALL_POND = { "01", "02", "03", "04",
			"05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15",
			"16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26",
			"27", "28", "29", "30", "31", "32", "33" };
	private static final String[] BLUE_BALL_POND = { "01", "02", "03", "04",
			"05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15",
			"16" };

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
		List<String> redBall = new ArrayList<String>(
				Arrays.asList(RED_BALL_POND));
		int redLen = redBall.size();
		SecureRandom random = new SecureRandom();
		List<String> ball = new ArrayList<String>();
		int count = 0;
		int temp = 0;
		do {
			temp = random.nextInt(redLen);
			ball.add(redBall.get(temp));

			redBall.remove(redBall.get(temp));
			redLen = redBall.size();
			count++;
		} while (count < 5);

		System.out.println("red ball: " + ball);
		
		count = 0;
		do {
			List<String> blueBall = new ArrayList<String>(
					Arrays.asList(BLUE_BALL_POND));
			int blueLen = blueBall.size();

			System.out.println("blue ball: " + blueBall.get(random.nextInt(blueLen)));
			count++;
		} while (count < 6);
		
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
