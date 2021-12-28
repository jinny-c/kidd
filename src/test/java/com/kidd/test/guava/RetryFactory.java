package com.kidd.test.guava;

import com.github.rholder.retry.*;
import com.google.common.base.Predicates;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @description 异步多次重试
 * guava - retrying
 * @auth chaijd
 * @date 2021/11/30
 */
public class RetryFactory {
    public Retryer retryer() {
        //定义重试机制
        Retryer<String> retry = RetryerBuilder.<String>newBuilder()
                //retryIf 重试条件
                .retryIfException()
                .retryIfRuntimeException()
                .retryIfExceptionOfType(Exception.class)
                .retryIfException(Predicates.equalTo(new Exception()))
                // 如果返回参数是null需要重试
                .retryIfResult(Predicates.isNull())
                // 如果返回结果不是 0000 需要重试
                .retryIfResult(str -> StringUtils.equals(str, "00"))
                // TODO 后期配置项挪入到 apollo
                //等待策略 : 每次请求间隔5s
                .withWaitStrategy(WaitStrategies.fixedWait(5, TimeUnit.SECONDS))
                //停止策略 : 尝试请求6次
                .withStopStrategy(StopStrategies.stopAfterAttempt(6))
                //时间限制 : 某次请求不得超过30s , 类似: TimeLimiter timeLimiter = new SimpleTimeLimiter();
                .withAttemptTimeLimiter(AttemptTimeLimiters.fixedTimeLimit(30, TimeUnit.SECONDS))
                .withRetryListener(new ResponseListener())
                .build();
        return retry;
    }

}
@Slf4j
class ResponseListener implements RetryListener {

    @Override
    public <V> void onRetry(Attempt<V> attempt) {
        if (attempt.getAttemptNumber() == 3) {
            if (attempt.hasException()) {
                log.info("发送邮件");
            }
        }
        if (attempt.hasException()) {
            log.info("getExceptionCause={}", attempt.getExceptionCause());
        }
        log.info("getResult={}", attempt.getResult());
    }
}
