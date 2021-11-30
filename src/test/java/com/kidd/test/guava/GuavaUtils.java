package com.kidd.test.guava;

import com.kidd.test.base.ComonException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * @description TODO
 * @auth chaijd
 * @date 2021/11/30
 */
@Slf4j
public class GuavaUtils {

    private void retryTest() {

    }

    @Test
    public void cacheTest() {
        AbstractCacheFactory<String> cacheFactory = new AbstractCacheFactory<String>(new CacheConfigBean()) {
            @Override
            protected String loadByKey(String key) throws ComonException {
                return StringUtils.join(key, "-", key);
            }
        };

        log.info("value={}", cacheFactory.getCacheByKey("123"));
    }

}
