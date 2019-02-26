package com.kidd.test.jdk8;

public interface NewCharacter {
    public void test1();

    public default void test2() {
        System.out.println("我是新特性1");
    }
}
