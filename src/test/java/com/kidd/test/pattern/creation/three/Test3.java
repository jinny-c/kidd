package com.kidd.test.pattern.creation.three;

//用"影子实例"的办法为单例对象的属性同步更新
//单例模式（Singleton）
public class Test3 {
	public static void main(String[] args) {
		Test3 t3 = new Test3();
		t3.method();
	}
	public void method(){
		try{
			System.out.printf("A");
			int i =10/0;
			System.out.printf("B");
		}catch (Exception e){
			System.out.printf("C");
		}finally {
			System.out.printf("D");
		}
	}
}
