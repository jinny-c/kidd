package com.kidd.base.factory.timer.service.impl;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;

import com.kidd.base.common.constant.KiddErrorCodes;
import com.kidd.base.common.exception.KiddFactoryException;
import com.kidd.base.common.utils.KiddDateUtils;
import com.kidd.base.factory.timer.KiddTimerFuture;
import com.kidd.base.factory.timer.service.IKiddTimerProcessor;

/**
 * 定时器,任务调度
 * 
 */
public class KiddTimerExecutor {
	private static Logger logger = Logger.getLogger(KiddTimerExecutor.class);

	private static final Long DEF_TIMEOUT_MILLS = 10 * 1000l;

	private Long timeOut;

	public KiddTimerExecutor() {
	}

	public KiddTimerExecutor(Long timeOut) {
		this.timeOut = timeOut;
	}

	/**
	 * 安排指定的任务从[指定的延迟时间]后在[超时时间]内开始进行[重复的固定]延迟执行。
	 * 
	 * @param processor
	 *            [任务执行器]
	 * @param delay
	 *            [延时执行时间]
	 * @param period
	 *            [间隔执行时间]
	 * @param timeOut
	 *            [超时时间]
	 * @return
	 * @throws KiddFactoryException
	 */
	public <T> T schedAtFixedDelayTimeOut(IKiddTimerProcessor<T> processor,
			Long delay, Long period, Long timeOut) throws KiddFactoryException {
		long num = period == null || period.longValue() == 0l
				|| timeOut == null ? 0 : (timeOut / period);
		return this.sched(processor, System.currentTimeMillis() + delay,
				period, num + 1, calcTimeOut(delay, period, null, timeOut));
	}

	/**
	 * 安排指定的任务从[指定的延迟时间]后在[规定的次数]内开始进行[重复的固定]延迟执行。
	 * 
	 * @param processor
	 *            [任务执行器]
	 * @param delay
	 *            [延时执行时间]
	 * @param period
	 *            [间隔执行时间]
	 * @param maxExeNum
	 *            [最大执行次数]
	 * @return
	 * @throws KiddFactoryException
	 */
	public <T> T schedAtFixedDelayCount(IKiddTimerProcessor<T> processor,
			Long delay, Long period, Long maxExeNum)
			throws KiddFactoryException {
		return sched(processor, System.currentTimeMillis() + delay, period,
				maxExeNum, calcTimeOut(delay, period, maxExeNum, null));
	}

	/**
	 * 安排指定的任务从[开始执行时间]到[结束执行时间]内开始进行[重复的固定]延迟执行。
	 * 
	 * @param processor
	 *            [任务执行器]
	 * @param start
	 *            [开始执行时间]
	 * @param period
	 *            [间隔执行时间]
	 * @param end
	 *            [结束执行时间]
	 * @return
	 * @throws KiddFactoryException
	 */
	public <T> T schedule(IKiddTimerProcessor<T> processor, Date start,
			Long period, Date end) throws KiddFactoryException {

		return sched(
				processor,
				start.getTime(),
				period,
				((end.getTime() - start.getTime()) / period) + 1,
				calcTimeOut(start.getTime() - System.currentTimeMillis(),
						period, null, (end.getTime() - start.getTime())));
	}

	private <T> T sched(final IKiddTimerProcessor<T> processor, Long start,
			Long period, Long maxExeCount, Long timeOut)
			throws KiddFactoryException {
		final Long actuStart = start == null ? System.currentTimeMillis()
				: start;
		final Long actuPeriod = period == null ? 0l : period;
		final Long actuMaxExeNum = maxExeCount == null
				|| maxExeCount.longValue() == 0 ? 1l : maxExeCount;
		FutureTask<T> ft = new FutureTask<T>(new Callable<T>() {
			@Override
			public T call() throws Exception {
				return schedProc(processor, actuStart, actuPeriod,
						actuMaxExeNum);
			}
		});

		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.execute(ft);
		try {
			return ft.get(timeOut, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			logger.error("the timer exec fail", e);
			if (e != null && e.getCause() instanceof KiddFactoryException) {
				KiddFactoryException be = (KiddFactoryException) e.getCause();
				throw new KiddFactoryException(be.getErrorCode(),
						be.getErrorMsg(), e);
			}
			if (e instanceof TimeoutException) {
				return null;
			}
			if (e != null && e.getCause() instanceof RuntimeException) {
				throw (RuntimeException) e.getCause();
			}
			throw new KiddFactoryException(KiddErrorCodes.E_KIDD_ERROR,
					"the timer exec fail", e.getCause());
		} finally {
			if (executor != null) {
				executor.shutdown();
			}
		}
	}

	private <T> T schedProc(IKiddTimerProcessor<T> processor, Long start,
			Long period, Long maxExeNum) throws KiddFactoryException {
		long currentTime = 0l;
		int count = 0;
		KiddTimerFuture<T> future = null;
		while (true) {
			currentTime = System.currentTimeMillis();
			if (currentTime < start) {
				continue;
			}
			if (count >= maxExeNum) {
				return future != null ? future.getFuture() : null;
			}
			future = processor.process();
			future.setExeNum(count + 1);
			logger.debug("processor[" + processor + "],start["
					+ KiddDateUtils.getCurrentDate() + "],period[" + period
					+ "],maxExeCount[" + maxExeNum + "],currExeCount["
					+ (count + 1) + "],future[" + future + "]");
			if (future != null && future.isSucc()) {
				return future.getFuture();
			}
			start += period;
			count++;
		}
	}

	private long calcTimeOut(Long delay, Long period, Long maxExeCount,
			Long timeOuts) {
		if (timeOut != null) {
			return timeOut;
		}
		if (timeOuts != null) {
			return (delay == null ? 0l : delay) + timeOuts;
		}
		if (maxExeCount == null || maxExeCount == 0l) {
			return 0l;
		}
		if (period == null || period == 0l) {
			return (delay == null ? 0l : delay) + DEF_TIMEOUT_MILLS;
		}
		return (delay == null ? 0l : delay) + period * maxExeCount;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(KiddDateUtils.getCurrentDate());
		final KiddTimerExecutor timer = new KiddTimerExecutor();

		ExecutorService es = Executors.newFixedThreadPool(20);

		for (int i = 1; i <= 1; i++) {
			final int j = i;
			es.execute(new Runnable() {

				@Override
				public void run() {
					Integer resp;
					try {
						resp = timer.schedAtFixedDelayCount(
								new IKiddTimerProcessor<Integer>() {
									private int count = 0;
									private KiddTimerFuture<Integer> future = new KiddTimerFuture<Integer>(
											false, 6);

									@Override
									public KiddTimerFuture<Integer> process()
											throws KiddFactoryException {
										System.out.println("proc==="
												+ KiddDateUtils
														.getCurrentDate());
										count++;
										if (count == 2) {
											future.setFuture(j);
											future.setSucc(true);
										}
										if (count > 5) {
											future.setFuture(j);
											future.setSucc(true);
										}
										// KiddFactoryException("M00009","操作失败");
										//throw new NullPointerException("11111");
										return future;
									}
								}, 1 * 1000l, 1 * 1000l, 4l);
						System.out.println("resp-----" + resp);
					} catch (KiddFactoryException e) {
						e.printStackTrace();
						// System.out.println("errorCode---"+e.getErrorCode());
						// System.out.println("errorMsg---"+e.getErrorMsg());
					}
				}
			});
		}
		es.shutdown();
	}
}
