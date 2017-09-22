package com.kidd.base.factory.asnyc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.kidd.base.common.exception.KiddException;

/**
 * <a href="SimpleAsyncTaskExecutor.java.html"><b><i>View Source</i></b></a>
 * 
 * Description ★ AsyncTaskExecutor 基于JDK ExecutorService的简单实现
 * 
 */
public class SimpleAsyncTaskExecutor implements IAsyncTaskExecutor {

	private static final long DEFAULT_TIMEOUT = 30;

	private ExecutorService executor = Executors.newCachedThreadPool();

	private long timeout = DEFAULT_TIMEOUT;

	
	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	@Override
	public <T> T execute(AsyncTaskCallBack<T> callBack) throws KiddException {
		Future<T> future = doExecute(callBack);
		try {
			return future.get(timeout, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			throw new KiddException("E-BASE-100", "Async Interrupted Error.", e);
		} catch (ExecutionException e) {
			throw new KiddException("E-BASE-101", "Async Execution Error.", e);
		} catch (TimeoutException e) {
			throw new KiddException("E-BASE-102", "Async Invork Timeout.", e);
		}
	}
	
	@Override
	public <T> Future<T> executeTask(AsyncTaskCallBack<T> callBack) throws KiddException {
		return doExecute(callBack);
	}

	@Override
	public void exeWithoutResult(AsyncTaskCallBack<Object> callBack) throws KiddException {
		doExecute(callBack);
	}

	protected <T> Future<T> doExecute(final AsyncTaskCallBack<T> callBack) {
		return executor.submit(new Callable<T>() {
			@Override
			public T call() throws Exception {
				return callBack.invork();
			}
		});
	}
}
