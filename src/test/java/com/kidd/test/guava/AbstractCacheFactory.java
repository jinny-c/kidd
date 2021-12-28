package com.kidd.test.guava;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.kidd.test.base.ComonException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * @description guava缓存
 * guava - cache
 * @auth chaijd
 * @date 2021/11/30
 */
@Slf4j
public abstract class AbstractCacheFactory<T> {

    private LoadingCache<String, Optional<T>> cache;


    private AbstractCacheFactory() {
    }

    public AbstractCacheFactory(CacheConfigBean dto) {
        this.cache = initCache(dto);
    }

    public AbstractCacheFactory(CacheConfigBean dto, Executor executor) {
        this.cache = initCache(dto, executor);
    }

    /**
     * 初始化
     *
     * @param dto
     * @return
     */
    private LoadingCache<String, Optional<T>> initCache(CacheConfigBean dto) {
        return CacheBuilder.newBuilder()
                //当缓存项上一次更新操作之后的多久会被刷新
                .refreshAfterWrite(dto.getRefreshInterval(), TimeUnit.SECONDS)
                //当缓存项在指定的时间段内没有更新就会被回收
                .expireAfterWrite(dto.getRecycleInterval(), TimeUnit.MINUTES)
                //当缓存项在指定的时间段内没有被读或写就会被回收
                //.expireAfterAccess(dto.getRecycleInterval(), TimeUnit.MINUTES)
                //并发级别是指可以同时写缓存的线程数
                .concurrencyLevel(dto.getConcurrency())
                .initialCapacity(dto.getInitSize())
                //超过之后就会按照LRU最近虽少使用算法来移除缓存项
                .maximumSize(dto.getMaxSize())
                .build(initLoader());
    }

    /**
     * 重写 load、reload方法
     *
     * @return
     */
    private CacheLoader<String, Optional<T>> initLoader() {
        return new CacheLoader<String, Optional<T>>() {
            @Override
            public Optional<T> load(String key) throws ComonException {
                log.debug("load start,key={}", key);
                return Optional.fromNullable(loadByKey(key));
            }

            @Override
            public ListenableFuture<Optional<T>> reload(String key, Optional<T> oldValue) throws ComonException {
                log.info("reload start,key={}", key);
                Preconditions.checkNotNull(key);
                Preconditions.checkNotNull(oldValue);
                try {
                    return Futures.immediateFuture(this.load(key));
                } catch (Exception e) {
                    log.debug("load exception:", e);
                    log.info("return old value,load exception={}", e.getMessage());
                }
                return Futures.immediateFuture(oldValue);
            }
        };
    }

    /**
     * 根据key加载缓存
     *
     * @param key
     * @return
     * @throws ComonException
     */
    protected abstract T loadByKey(String key) throws ComonException;


    /**
     * 异步线程
     *
     * @param dto
     * @param executor
     * @return
     */
    private LoadingCache<String, Optional<T>> initCache(CacheConfigBean dto, Executor executor) {
        return CacheBuilder.newBuilder()
                .refreshAfterWrite(dto.getRefreshInterval(), TimeUnit.SECONDS)
                .expireAfterWrite(dto.getRecycleInterval(), TimeUnit.MINUTES)
                .concurrencyLevel(dto.getConcurrency())
                .initialCapacity(dto.getInitSize())
                .maximumSize(dto.getMaxSize())
                .build(initLoader(executor));
    }

    private CacheLoader<String, Optional<T>> initLoader(Executor executor) {
        return CacheLoader.asyncReloading(initLoader(), executor);
    }

    private Optional<T> getOptional(String key) {
        return cache.getUnchecked(key);
    }

    public T getCacheByKey(String key) {
        log.debug("getCacheByKey start,key={}", key);
        if (StringUtils.isBlank(key)) {
            return null;
        }
        try {
            Optional<T> result = getOptional(key);
            if (result.isPresent()) {
                return result.get();
            }
        } catch (Exception e) {
            log.debug("从缓存中获取信息失败", e);
            log.error("load exception,message={}", e.getMessage());
        }
        return null;
    }

    private void putOptional(String key, Optional<T> optional) {
        //会覆盖原key的value值
        cache.put(key, optional);
    }

    public void putCache(String key, T value) {
        log.debug("putCache start,key={}", key);
        if (StringUtils.isNotEmpty(key)) {
            putOptional(key, Optional.fromNullable(value));
        }
    }

    public void refreshCache(String key) {
        log.debug("refreshCache start,key={}", key);
        if (StringUtils.isNotEmpty(key)) {
            //刷新时，为异步操作
            //会判断，是执行load 还是 reload方法
            cache.refresh(key);
        }
    }

    public void removeCache(String key) {
        log.debug("removeCache start,key={}", key);
        if (StringUtils.isNotEmpty(key)) {
            //根据key移除
            cache.invalidate(key);
        }
    }
}
@Setter
@Getter
@ToString
class CacheConfigBean {
    /**
     * 刷新时间间隔
     * 单位秒【second】
     */
    private long refreshInterval = 10 * 60;

    /**
     * 失效间隔时间： 缓存在该时间内没有被访问则失效回收
     * 单位分【min】
     */
    private long recycleInterval = 5 * 60;

    /**
     * 并发数
     */
    private int concurrency = 8;
    /**
     * 初始容量
     */
    private int initSize = 128;
    /**
     * 最大容量
     */
    private long maxSize = 1024;

}
