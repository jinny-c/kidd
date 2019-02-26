package com.kidd.test.jdk8.lambda;


import java.util.*;

/**
 *
 *  java8中常用的函数式接口：
 *
 *   Function<T,R>      接收一个T类型的参数，返回一个R类型的结果
 *
 *   Consumer<T>    接收一个T类型的参数，不返回值
 *
 *   Predicate<T>   接收一个T类型的参数，返回一个boolean类型的结果
 *
 *   Supplier<T>    不接受参数，返回一个T类型的结果
 *
 *
 * BiFunction<T, U, R>  接收T类型和U类型的两个参数，返回一个R类型的结果
 *
 * BiConsumer<T , U>    接收T类型和U类型的两个参数，不返回值
 *
 * BiPredicate<T, U>    接收T类型和U类型的两个参数，返回一个boolean类型的结果
 *
 */

public class NewFeatures {
    public static void main(String[] args) {
        //lambdaTest();
        lambdaTest2();


        //test1();
        //testLamda1();
        //testLamda2();
        //testLamda3();
    }

    public static void lambdaTest(){
        MyLambdaVoid m1 = new MyLambdaVoid() {
            @Override
            public void getMessage(String msg) {
                System.out.println("getMessage:" + msg);
            }
        };
        m1.getMessage("say hello");

        MyLambdaVoid m2 = y -> System.out.println("get msg:"+y);
        m2.getMessage("say hi");

        MyLambdaReturn mm1 = new MyLambdaReturn() {
            @Override
            public String getInfo(String input) {
                return input + " is first return";
            }
        };
        System.out.println(mm1.getInfo("the words"));

        MyLambdaReturn mm2 = info -> info +" is second return";
        System.out.println(mm2.getInfo("this word"));

    }

    public static void lambdaTest2(){
        MyLambdaReturn mm3 = new MyLambdaReturn() {
            @Override
            public String getInfo(String input) {
                return MyLambdaReturn.staticMethod2(input);
            }
        };
        System.out.println(mm3.getInfo("input"));

        MyLambdaVoid1 mm40 = ()-> System.out.println(MyLambdaVoid1.staticMethod2());
        mm40.getMessage();

        MyLambdaReturn mm4 = mlr->MyLambdaReturn.staticMethod2(mlr);
        System.out.println(mm4.getInfo("input"));

        MyLambdaReturn mm5 = MyLambdaReturn::staticMethod2;
        System.out.println(mm5.getInfo("input"));

        //MyLambdaReturn mm4 = String::new;
        //System.out.println(mm3.getInfo("this word"));

    }

    //这是常规的Collections的排序的写法，需要对接口方法重写
    public static void test1() {
        List<String> list = Arrays.asList("aaa", "fsa", "ser", "eere");
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.compareTo(o1);
            }
        });
        for (String string : list) {
            System.out.println(string);
        }
    }

    //这是带参数类型的Lambda的写法
    public static void testLamda1() {
        List<String> list = Arrays.asList("aaa", "fsa", "ser", "eere");
        Collections.sort(list, (Comparator<? super String>) (String a, String b) -> {
                    return b.compareTo(a);
                }
        );
        list.forEach(s -> System.out.println(s));
    }

    //这是不带参数的lambda的写法
    public static void testLamda2() {
        List<String> list = Arrays.asList("aaa", "fsa", "ser", "eere");
        Collections.sort(list, (a, b) -> b.compareTo(a)
        );
        list.forEach(s -> System.out.println(s));
    }

    public static void testLamda3() {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        map.forEach((k, v) -> System.out.println(k + "=" + v));
    }
}