package com.kidd.base.factory.asnyc;

import java.util.concurrent.Future;

import com.kidd.base.common.exception.KiddException;

/**
 * <a href="IAsyncTaskExecutor.java.html"><b><i>View Source</i></b></a>
 * 
 */
public interface IAsyncTaskExecutor {

	/**
	 * 执行异步调用——有返回值
	 * 
	 * @param callBack
	 * @return
	 * @throws KiddException
	 */
	<T> T execute(AsyncTaskCallBack<T> callBack) throws KiddException;
	
	/**
	 * 执行异步调用——有返回值
	 * @param <T>
	 * 
	 * @param callBack
	 * @return
	 * @throws KiddException
	 */
	<T> Future<T> executeTask(AsyncTaskCallBack<T> callBack) throws KiddException;
	
	/**
	 * 执行异步调用——无返回值
	 * 
	 * @param callBack
	 * @throws KiddException
	 */
	void exeWithoutResult(AsyncTaskCallBack<Object> callBack) throws KiddException;

	/**
	 * <a href="AsyncTaskCallBack.java.html"><b><i>View Source</i></b></a>
	 * 
	 * Description ★ 异步任务回调接口
	 * 
	 */
	public interface AsyncTaskCallBack<T> {

		/**
		 * 执行业务调用
		 * 
		 * @return
		 * @throws KiddException
		 */
		public T invork() throws KiddException;

	}

}
