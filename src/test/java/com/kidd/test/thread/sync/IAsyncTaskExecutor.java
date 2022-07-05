package com.kidd.test.thread.sync;

import java.util.concurrent.Future;


public interface IAsyncTaskExecutor {

    /**
     * 执行异步调用——有返回值
     *
     * @param callBack
     * @return
     * @throws AsyncTaskException
     */
    <T> T execute(AsyncTaskCallBack<T> callBack) throws AsyncTaskException;

    /**
     * 执行异步调用——有返回值
     *
     * @param <T>
     * @param callBack
     * @return
     * @throws AsyncTaskException
     */
    <T> Future<T> executeTask(AsyncTaskCallBack<T> callBack) throws AsyncTaskException;

    /**
     * 执行异步调用——无返回值
     *
     * @param callBack
     * @throws AsyncTaskException
     */
    void exeWithoutResult(AsyncTaskCallBack<Object> callBack) throws AsyncTaskException;

    void printThreadStatus(boolean debugLevel);

    /**
     * Description ★ 异步任务回调接口
     */
    public interface AsyncTaskCallBack<T> {

        /**
         * 执行业务调用
         *
         * @return
         * @throws AsyncTaskException
         */
        public T invork() throws AsyncTaskException;

    }

}
