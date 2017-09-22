package com.kidd.base.factory.timer;

/**
 * 定时器执行结果类
 * 
 */
public class KiddTimerFuture<T> {

	/** 是否执行成功 **/
	private boolean isSucc = false;

	/** 执行的次数 **/
	private int exeNum = 0;

	/** 执行的结果 **/
	private T future;

	public KiddTimerFuture(boolean isSucc, T future) {
		this.isSucc = isSucc;
		this.future = future;
	}

	public boolean isSucc() {
		return isSucc;
	}

	public void setSucc(boolean isSucc) {
		this.isSucc = isSucc;
	}

	public T getFuture() {
		return future;
	}

	public void setFuture(T future) {
		this.future = future;
	}

	public int getExeNum() {
		return exeNum;
	}

	public void setExeNum(int exeNum) {
		this.exeNum = exeNum;
	}
}
