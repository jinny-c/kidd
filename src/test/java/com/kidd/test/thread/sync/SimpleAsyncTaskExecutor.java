package com.kidd.test.thread.sync;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * Description ★ AsyncTaskExecutor 基于JDK ExecutorService的简单实现
 */
public class SimpleAsyncTaskExecutor implements IAsyncTaskExecutor {

    private static final long DEFAULT_TIMEOUT = 15;
    private static final long DEFAULT_ALIVE_TIME = 0L;
    private static final int DEFAULT_CORE_POOL_SIZE = 8;
    private static final int DEFAULT_MAX_POOL_SIZE = 32;
    private static final int DEFAULT_QUEUE_SIZE = 100 * 1000;

    private ExecutorService executor = new ThreadPoolExecutor(DEFAULT_CORE_POOL_SIZE, DEFAULT_MAX_POOL_SIZE
            , DEFAULT_ALIVE_TIME, TimeUnit.SECONDS
            , new ArrayBlockingQueue(DEFAULT_QUEUE_SIZE)
            , new ThreadFactoryBuilder().setNameFormat("async-pool-%d").build());

    private long timeout = DEFAULT_TIMEOUT;

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    /**
     * 单例模式
     */
    private static SimpleAsyncTaskExecutor instance = new SimpleAsyncTaskExecutor();

    private SimpleAsyncTaskExecutor() {
    }

    public static SimpleAsyncTaskExecutor getInstance() {
        return instance;
    }

    @Override
    public <T> T execute(AsyncTaskCallBack<T> callBack) throws AsyncTaskException {
        Future<T> future = doExecute(callBack);
        try {
            return future.get(timeout, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new AsyncTaskException("ASYNC-100", "Async Interrupted Error.", e);
        } catch (ExecutionException e) {
            throw new AsyncTaskException("ASYNC-101", "Async Execution Error.", e);
        } catch (TimeoutException e) {
            throw new AsyncTaskException("ASYNC-102", "Async Invork Timeout.", e);
        }
    }

    @Override
    public <T> Future<T> executeTask(AsyncTaskCallBack<T> callBack) throws AsyncTaskException {
        return doExecute(callBack);
    }

    @Override
    public void exeWithoutResult(AsyncTaskCallBack<Object> callBack) throws AsyncTaskException {
        doExecute(callBack);
    }

    @Override
    public void printThreadStatus(boolean debugLevel) {
        if (debugLevel) {
            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
            int queueSize = threadPoolExecutor.getQueue().size();
            int getActiveCount = threadPoolExecutor.getActiveCount();
            int getPoolSize = threadPoolExecutor.getPoolSize();
            long getCompletedTaskCount = threadPoolExecutor.getCompletedTaskCount();
            long getTaskCount = threadPoolExecutor.getTaskCount();
            StringBuilder sbd = new StringBuilder();
            sbd.append(" 当前排队任务数：").append(queueSize);
            sbd.append(" 当前活动任务数：").append(getActiveCount);
            sbd.append(" 最大线程数：").append(getPoolSize);
            sbd.append(" 执行完成任务数：").append(getCompletedTaskCount);
            sbd.append(" 总任务数：").append(getTaskCount);
            System.out.println(sbd.toString());
        }
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
