package com.kidd.test.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestThreadPool {
	public static void main(String[] args) {
		newPool1();
		//newPool2();
	}

	/**
	 * 
	 * ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, 
	 * 						TimeUnit unit,  BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler)
	 *
	 *	corePoolSize： 线程池维护线程的最少数量	maximumPoolSize：线程池维护线程的最大数量	keepAliveTime： 线程池维护线程所允许的空闲时间 
	 *	unit： 线程池维护线程所允许的空闲时间的单位	workQueue： 线程池所使用的缓冲队列	handler： 线程池对拒绝任务的处理策略 
	 *
	 *
	 *一个任务通过 execute(Runnable)方法被添加到线程池，任务就是一个 Runnable类型的对象，任务的执行方法就是 Runnable类型对象的run()方法。 
			当一个任务通过execute(Runnable)方法欲添加到线程池时： 
			如果此时线程池中的数量小于corePoolSize，即使线程池中的线程都处于空闲状态，也要创建新的线程来处理被添加的任务。 
			如果此时线程池中的数量等于 corePoolSize，但是缓冲队列 workQueue未满，那么任务被放入缓冲队列。 
			如果此时线程池中的数量大于corePoolSize，缓冲队列workQueue满，并且线程池中的数量小于maximumPoolSize，建新的线程来处理被添加的任务。 
			如果此时线程池中的数量大于corePoolSize，缓冲队列workQueue满，并且线程池中的数量等于maximumPoolSize，那么通过 handler所指定的策略来处理此任务。 
	也就是：处理任务的优先级为： 
			核心线程corePoolSize、任务队列workQueue、最大线程maximumPoolSize，如果三者都满了，使用handler处理被拒绝的任务。 
			当线程池中的线程数量大于 corePoolSize时，如果某线程空闲时间超过keepAliveTime，线程将被终止。这样，线程池可以动态的调整池中的线程数。 
	unit可选的参数为java.util.concurrent.TimeUnit中的几个静态属性： 
			NANOSECONDS、MICROSECONDS、MILLISECONDS、SECONDS。 
	workQueue我常用的是：java.util.concurrent.ArrayBlockingQueue 
			ThreadPoolExecutor.AbortPolicy() 
			抛出java.util.concurrent.RejectedExecutionException异常 
			ThreadPoolExecutor.CallerRunsPolicy() 
			重试添加当前的任务，他会自动重复调用execute()方法 
			ThreadPoolExecutor.DiscardOldestPolicy() 
			抛弃旧的任务 
			ThreadPoolExecutor.DiscardPolicy() 
			抛弃当前的任务 
	 *
	 */
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

		for (int i = 0; i < 10; i++) {
			final int index = i;
			try {
				Thread.sleep(index * 10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			cachedThreadPool.execute(new Runnable() {
				public void run() {
					System.out.println(index);
				}
			});
		}

	}

	private static void newPool2() {
		// 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
		// 因为线程池大小为3，每个任务输出index后sleep 2秒，所以每两秒打印3个数字
		for (int i = 0; i < 10; i++) {
			final int index = i;
			fixedThreadPool.execute(new Runnable() {
				public void run() {
					try {
						System.out.println(index);
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

	private void newPool3() {
		// 创建一个定长线程池，支持定时及周期性任务执行
		ScheduledExecutorService scheduledThreadPool = Executors
				.newScheduledThreadPool(5);
		// 表示延迟3秒执行
		scheduledThreadPool.schedule(new Runnable() {
			public void run() {
				System.out.println("delay 3 seconds");
			}
		}, 3, TimeUnit.SECONDS);
	}

	private void newPool3_1() {
		ScheduledExecutorService scheduledThreadPool = Executors
				.newScheduledThreadPool(5);
		scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
			public void run() {
				System.out
						.println("delay 1 seconds, and excute every 3 seconds");
			}
		}, 1, 3, TimeUnit.SECONDS);
		// 表示延迟1秒后每3秒执行一次
	}

	private void newPool4() {
		// 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行
		ExecutorService singleThreadExecutor = Executors
				.newSingleThreadExecutor();
		for (int i = 0; i < 10; i++) {
			final int index = i;
			singleThreadExecutor.execute(new Runnable() {
				public void run() {
					try {
						System.out.println(index);
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
}
