package com.kidd.test.guava;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @description 字符串处理
 * guava--Joiner、Splitter、MapJoinner、MapSplitter
 * @auth chaijd
 * @date 2021/11/30
 */
@Slf4j
public class StringHandling {
    List<String> lt = Arrays.asList("123","abc","测试","",null);
    Map<String,String> mp = ImmutableMap.of("mpk1","123","mpk2","abc",
            "mpk3","测试3","mpk4","");

    Map<String, String> mmp = new TreeMap<String, String>() {
        {
            put("mpk1", "123");
            put("mpk2", "abc");
            put("mpk3", "测试3");
            put("mpk4", "");
            put("mpk5", null);
        }
    };

    String ltStr = "123,abc,测测,,";
    String mpStr = "name=姓名&cardNo=62212346666&mobile=136121348888";

    @Test(expected = NullPointerException.class)
    public void joinerTest(){
        Joiner jn = Joiner.on("#").skipNulls();
        log.info("val==>{}", jn.join(lt));

        Joiner.MapJoiner jnm = Joiner.on("&").withKeyValueSeparator("=");
        log.info("val==>{}", jnm.join(mp));
        Joiner.MapJoiner jnmm = Joiner.on("&").withKeyValueSeparator("=").useForNull("df");
        log.info("val==>{}", jnmm.join(mmp));
    }

    @Test
    public void splitterTest(){
        Splitter sp = Splitter.on(",").omitEmptyStrings();
        log.info("val==>{}", sp.split(ltStr));

        Splitter.MapSplitter spm = Splitter.on("&").withKeyValueSeparator("=");
        log.info("val==>{}", spm.split(mpStr));
    }

    private void checkTest(){
        String ss = null;
        Preconditions.checkNotNull(null,"不能为null");
        Integer itg = 10;
        Preconditions.checkArgument(itg > 0, "必须大于0", itg);
    }
}
