package com.kidd.test.jdk8;

public class NewCharacterImpl implements NewCharacter {
    @Override
    public void test1() {

    }
    public static void main(String[] args) {
        NewCharacter nca = new NewCharacterImpl();
        nca.test2();
    }

}
