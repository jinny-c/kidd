package com.kidd.test.jdk8.lambda;


/**
 * @FunctionalInterface注释的约束
 * 1、接口有且只能有个一个抽象方法，只有方法定义，没有方法体
 * 2、在接口中覆写Object类中的public方法，不算是函数式接口的方法。
 *
 */
@FunctionalInterface
public interface MyLambdaVoid {

    @Override
    String toString();  //Object中的方法
    @Override
    boolean equals(Object obj); //Object中的方法


    public void getMessage(String msg);

    //这里如果继续加一个抽象方法便会报错
    //public void getMessage2(String msg);


    //default方法可以任意定义
    default String defMethod1(){
        System.out.println("defMethod1 void2");
        return "def1 void2";
    }

    default void defMethod2(){
        System.out.println("defMethod2 void2");
    }

    //static方法也可以定义
    static void staticMethod1(){
        System.out.println("staticMethod1 void2");
    }
    static String staticMethod2(){
        System.out.println("staticMethod2 void2");
        return "static2 void2";
    }
}
