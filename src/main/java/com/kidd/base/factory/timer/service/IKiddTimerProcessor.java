package com.kidd.base.factory.timer.service;

import com.kidd.base.common.exception.KiddFactoryException;
import com.kidd.base.factory.timer.KiddTimerFuture;

/**
 * 定时器处理类
 * 
 */
public interface IKiddTimerProcessor<T> {

	/**
	 * 定时器处理接口
	 * 
	 * @return
	 * @throws KiddFactoryException
	 */
	abstract KiddTimerFuture<T> process() throws KiddFactoryException;
}
