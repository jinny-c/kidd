package com.kidd.test.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
 * @description 锁
 *
 * synchronized（关键字）跟Lock（对象）
 *
 * @auth chaijd
 * @date 2021/12/28
 */

/*
线程A和B都要获取对象O的锁定，假设A获取了对象O锁，B将等待A释放对O的锁定，
如果使用 synchronized ，如果A不释放，B将一直等下去，不能被中断
如果 使用ReentrantLock，如果A不释放，可以使B在等待了足够长的时间以后，中断等待，而干别的事情

synchronized是在JVM层面上实现的，不但可以通过一些监控工具监控synchronized的锁定，而且在代码执行时出现异常，JVM会自动释放锁定，但是使用Lock则不行，lock是通过代码实现的，要保证锁定一定会被释放，就必须将unLock()放到finally{}中

在资源竞争不是很激烈的情况下，Synchronized的性能要优于ReetrantLock，但是在资源竞争很激烈的情况下，Synchronized的性能会下降几十倍，但是ReetrantLock的性能能维持常态；

各种 Atomic 类
*/
public class SaleTickets implements Runnable {
	public int total;
	public int count;
	private Lock lock;
	private Condition condition;

	public SaleTickets() {
		total = 100;
		count = 0;
		// 为true表示为公平锁，为fasle为非公平锁。默认情况下，如果使用无参构造器，则是非公平锁
		//lock = new ReentrantLock(true);// 公平锁
		lock = new ReentrantLock();
		// 即便是公平锁，如果通过不带超时时间限制的tryLock()的方式获取锁的话，它也是不公平的
		// 但是带有超时时间限制的tryLock(long timeout, TimeUnit unit)方法则不一样，还是会遵循公平或非公平的原则的
		condition = lock.newCondition();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//method();
		method3();
	}

	/**
	 * tryLock锁
	 */
	private void method() {
		while (true) {
			try {
				// 买票前准备,休眠1毫秒模拟拿出证件
				Thread.sleep(4);
				// 获取当前线程名字
				String threadName = Thread.currentThread().getName();
				//lock.lock();
				// tryLock()如果获取成功，则返回true，如果获取失败（即锁已被其他线程获取），则返回false，也就说这个方法无论如何都会立即返回。在拿不到锁时不会一直在那等待。
				if (lock.tryLock()) {
					try {
						System.out.println("-----" + threadName + " tryLock()");
						if (!sale(threadName)) {
							break;
						}
					} finally {
						lock.unlock();
					}
				} else {
					// tryLock(long time, TimeUnit unit)方法和tryLock()方法是类似的
					// 只不过区别在于这个方法在拿不到锁时会等待一定的时间，在时间期限之内如果还拿不到锁，就返回false。如果如果一开始拿到锁或者在等待期间内拿到了锁，则返回true
					if (lock.tryLock(2, TimeUnit.SECONDS)) {
						try {
							System.out.println("======" + threadName
									+ " tryLock(5)");
							if (!sale(threadName)) {
								break;
							}
						} finally {
							lock.unlock();
						}
					} else {
						System.out.println(threadName + " 啥也没干!");
					}
				}
			} catch (InterruptedException e) {
				// TODO: handle exception
				e.getStackTrace();
			}
		}
	}

	private boolean sale(String threadName) throws InterruptedException {
		if (total <= 0) {
			System.out.println(threadName + " 车票已售罄!");
			return false;
		}
		if (total > 90 && threadName.contains("A")) {
			// Thread.sleep(3000);
			System.out.println(threadName + " 我休息片刻!");
			condition.await();
		}
		// Thread.sleep(1000);
		if (total < 90 && total > 80 && threadName.contains("B")) {
			System.out.println(threadName + " 我休息片刻!");
			condition.await();
		}
		if (total < 80 && total > 70 && threadName.contains("C")) {
			System.out.println(threadName + " 我休息片刻!");
			condition.await();
		}
		// if (total == 1) {
		if (total == 60||total == 50||total == 40||total == 30) {
			System.out.println(threadName + " 来个人来帮忙!");
			// 唤醒一个 先入先出
			condition.signal();
			//System.out.println(threadName + " 都来继续工作!");
			// 唤醒全部等待的线程
			//condition.signalAll();
			//Thread.sleep(2001);
		}

		Thread.sleep(10);
		// 打印火车票,休眠10毫秒模拟打印车票时间
		System.out.println(threadName + " 售出火车票No." + (++count));
		total--;
		return true;
	}

	/**
	 * 类似 synchronized 的 lock()锁
	 */
	private void method1() {
		while (true) {
			try {
				// 获取当前线程名字
				String threadName = Thread.currentThread().getName();
				// lock()方法是用来获取锁。如果锁已被其他线程获取，则进行等待。
				lock.lock();
				if (total <= 0) {
					System.out.println(threadName + " 车票已售罄!");
					break;
				}
				// 打印火车票,休眠20毫秒模拟打印车票时间
				Thread.sleep(20);
				System.out.println(threadName + " 售出火车票No." + ++count);
				total--;
			} catch (InterruptedException e) {
				// TODO: handle exception
				e.getStackTrace();
			} finally {
				lock.unlock();
			}
		}
	}

	/**
	 * synchronized 关键字
	 */
	private void method2() {
		while (true) {
			// 获取当前线程名字
			String threadName = Thread.currentThread().getName();

			synchronized (SaleTickets.class) {
				if (total <= 0) {
					System.out.println(threadName + " 车票已售罄!");
					break;
				}
				// 打印火车票,休眠20毫秒模拟打印车票时间
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(threadName + " 售出火车票No." + ++count);
				total--;
			}
		}
	}

	public AtomicInteger atmicTotal = new AtomicInteger(100);
	public AtomicInteger atmicCount = new AtomicInteger(0);

	/**
	 * Atomic原子操作
	 */
	private void method3() {
		while (true) {
			// 获取当前线程名字
			String threadName = Thread.currentThread().getName();
			//synchronized (SaleTickets.class) {
				//--total
				if (atmicTotal.decrementAndGet() < 0) {
				//total--
				//if (atmicTotal.getAndDecrement() <= 0) {
					System.out.println(threadName + " 车票已售罄!");
					break;
				}
				// 打印火车票,休眠20毫秒模拟打印车票时间
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//++count
				System.out.println(threadName + " 售出火车票No." + atmicCount.incrementAndGet());
				//count++
				//System.out.println(threadName + " 售出火车票No." + atmicCount.getAndIncrement());
			//}
		}
	}

	public static void main(String[] args) {
		SaleTickets ticket = new SaleTickets();
		// 创建售票线程 ,并设置窗口名字,然后启动线程,这里设置四个窗口
		new Thread(ticket, "窗口A").start();
		new Thread(ticket, "窗口B").start();
		new Thread(ticket, "窗口C").start();
		new Thread(ticket, "窗口D").start();
	}
}
