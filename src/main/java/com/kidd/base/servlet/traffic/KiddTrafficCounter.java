package com.kidd.base.servlet.traffic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 流量阻断 【针对展示层的单实例进行并发限制】
 *
 * @history
 */
public class KiddTrafficCounter {
	private AtomicInteger count = new AtomicInteger(0);

	/**
	 * 最大并发允许数量, 负数表示不限制，展示层每个实例限制为500，总共2个实例
	 */
	private int maxToken = -1;

	public KiddTrafficCounter(String maxToken){
		this.maxToken = Integer.valueOf(maxToken);
	}

	public int getMaxToken() {
		return maxToken;
	}

	public void setMaxToken(int maxToken) {
		this.maxToken = maxToken;
	}

	/**
	 * 重置并发数
	 */
	public void reset(){
		count.set(0);
	}

	/**
	 * 获取当前并发数
	 * @return
	 */
	public int get(){
		return count.get();
	}

	/**
	 * 原子增加并发数量
	 * @return
	 */
	private int increase(){
		return count.incrementAndGet();
	}

	/**
	 * 原子减少并发数量
	 * @return
	 */
	private int decrement(){
		return count.decrementAndGet();
	}

	/**
	 * 判断并发是否超限
	 * @return
	 */
	private boolean isFull() {
		return get() >= maxToken;
	}

	/**
	 * 请求准入
	 * @return
	 */
	public boolean acquire() {
		if (increase() <= 0) {
			reset();
			return true;
		}
		return !isFull();
	}

	/**
	 * 退出释放
	 */
	public void release() {
		if (decrement() < 0) {
			reset();
		}
	}

}