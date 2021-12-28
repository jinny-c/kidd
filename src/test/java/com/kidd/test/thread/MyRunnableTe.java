package com.kidd.test.thread;
/**
 * @description 线程
 *
 * @auth chaijd
 * @date 2021/12/28
 */
public class MyRunnableTe implements Runnable {

    private static Integer i = new Integer(0);

    @Override
    public void run() {
        while (true) {
            synchronized (i) {
                if (i < 10) {
                    i++;
                    System.out.println("i=" + i);
                } else {
                    //超过100 跳出循环
                    break;
                }
            }
        }
    }

    private static void threadTest(){
        Thread t1 = new Thread(new MyRunnableTe());
        Thread t2 = new Thread(new MyRunnableTe());
        t1.start();
        t2.start();
    }

    public static void main(String[] args) {
        IntegerTest();
        //threadTest();
    }

    private static void IntegerTest(){
        //Integer中表示值的变量value本身就是一个final类型的，即不可变类型，不可重新赋值。
        //当Integer++时，首先调用intValue()自动拆箱
        //返回的value自增后，用 valueOf(int i)自动装箱，然后返回一个新的Integer对象。
        Integer i = 9;
        Integer j = i++;
        Integer k = new Integer(10);
        //Integer l = ++i;

        System.out.println("i=" + i + ",j=" + j + ",k=" + k);

        System.out.println(i == j);
        System.out.println(i == k);
        System.out.println(k == j);
    }
}
