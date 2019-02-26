package com.kidd.test.jdk8.lambda;

@FunctionalInterface
public interface MyLambdaReturn {

    String getInfo(String input);

    @Override
    String toString();  //Object中的方法

    static String staticMethod2(){
        System.out.println("staticMethod2 re");
        return "static2 re";
    }
    static String staticMethod2(String in) {
        System.out.println("staticMethod2 in re");
        return in + " static2 re";
    }

    default String defMethod1(){
        System.out.println("defMethod1 re");
        return "def1 re";
    }

}
