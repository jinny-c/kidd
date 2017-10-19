package com.kidd.test.scanner;

import java.util.Scanner;

public class GameTest {
	public static final double Gold = ((1 + Math.sqrt(5.0)) / 2.0);

	//巴什博奕
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//game1();
		int n =31;
		int m = 3;
		//谁拿光，谁输
		System.out.println((n-1)%(m+1));
		//先拿到最后一个，胜利
		System.out.println((n)%(m+1));
		System.out.println(99%11);
		System.out.println(100%11);
	}

	public static void game1() {
		Scanner in = new Scanner(System.in);
		//n 总数，k最大取的数
		int n, k;
		while (in.hasNext()) {
			n = in.nextInt();
			k = in.nextInt();

			if (n == 0 && k == 0)
				break;
			if ((n - 1) % (k + 1) != 0) {
				System.out.println("first Tang win,he did not get the last");
			} else {
				System.out.println("second Jiang win,he did not get the last");
			}

		}
	}
}
