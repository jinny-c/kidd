package com.kidd.test.thread;

import com.kidd.test.thread.sync.IAsyncTaskExecutor;
import com.kidd.test.thread.sync.SimpleAsyncTaskExecutor;

/**
 * @description TODO
 * @auth chaijd
 * @date 2022/7/5
 */
public class ThreadTest {
    public static void main(String[] args) throws Exception{
//        IAsyncTaskExecutor m1 = new SimpleAsyncTaskExecutor();
//        IAsyncTaskExecutor m2 = new SimpleAsyncTaskExecutor();
//        IAsyncTaskExecutor m3 = new SimpleAsyncTaskExecutor();

        IAsyncTaskExecutor m1 = SimpleAsyncTaskExecutor.getInstance();
        IAsyncTaskExecutor m2 = SimpleAsyncTaskExecutor.getInstance();
        IAsyncTaskExecutor m3 = SimpleAsyncTaskExecutor.getInstance();

        m3.exeWithoutResult(()->{
            for (int i = 0; i < 10; i++) {
                final int index = i;
                try {
                    Thread.sleep(index * 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                m1.exeWithoutResult(()->{
                    System.out.println(Thread.currentThread().getName());
                    //Thread.currentThread().setName("m3.m1");
                    System.out.println("111111111");
                    return null;
                });
            }
            return null;
        });

        m3.exeWithoutResult(()->{
            for (int i = 0; i < 10; i++) {
                final int index = i;
                try {
                    Thread.sleep(index * 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                m2.exeWithoutResult(()->{
                    System.out.println(Thread.currentThread().getName());
                    //Thread.currentThread().setName("m3.m2");
                    System.out.println("222222222");
                    return null;
                });
            }
            return null;
        });

    }
}
