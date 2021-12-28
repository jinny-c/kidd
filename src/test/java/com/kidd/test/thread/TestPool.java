package com.kidd.test.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
/**
 * @description 线程池
 *
 * @auth chaijd
 * @date 2021/12/28
 */
@Slf4j
public class TestPool {
	private static ExecutorService pools = Executors.newFixedThreadPool(250);
	  
	public static void main(String[] args) {
		//newPool1();
		newPoolTask();
	}
	private static void newPoolTask() {
		for (int i = 0; i < 100; i++) {
			final int index = i;
			pools.execute(new TestTask(index));
		}
	}
	
	private static void newPool() {
		ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200,
				TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(5));

		// 一般需要根据任务的类型来配置线程池大小：
		// 如果是CPU密集型任务，就需要尽量压榨CPU，参考值可以设为 NCPU+1
		// 如果是IO密集型任务，参考值可以设置为2*NCPU
	}

	private static void newPool1() {
		// 创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程
		ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
		// 线程池为无限大，当执行第二个任务时第一个任务已经完成，会复用执行第一个任务的线程，而不用每次新建线程

		for (int i = 0; i < 100; i++) {
			final int index = i;
			cachedThreadPool.execute(new Runnable() {
				public void run() {
					//System.out.println(index);
					log.info("---{}",index);
				}
			});
		}

	}

}

@Slf4j
class TestTask implements Runnable {
	private int i;
    public TestTask(int i) {
        this.i = i;
    }

    @Override
    public void run() {
		try {
			Thread.sleep(i * 100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        log.info("TestTask={}",i);
    }

}
