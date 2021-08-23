package com.kidd.test;

import com.google.common.base.Joiner;
import com.kidd.base.common.serialize.KiddFastJsonUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyJavaTest {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        //tsJoin();
        regular();
    }

    /**
     * 字符串拼接
     */
    public static void tsJoin() {
        Map<String, String> mp = new HashMap<>();
        mp.put("a", "111");
        mp.put("b", "222");
        mp.put("c", "333");
        String sj = Joiner.on("&").withKeyValueSeparator("=").join(mp);
        System.out.println(sj);

        System.out.println(KiddFastJsonUtils.toStr(2234));
        System.out.println(KiddFastJsonUtils.toStr("1234"));

        Map<String, String> mp1 = new HashMap<>();
        mp1.put("a", KiddFastJsonUtils.toStr("111"));
        mp1.put("b", KiddFastJsonUtils.toStr("222"));
        mp1.put("c", KiddFastJsonUtils.toStr("333"));

        System.out.println(Joiner.on("&").withKeyValueSeparator("=").join(mp1));

    }

    public static void tsStJoin() {
        StringJoiner sjn = new StringJoiner("=", "", "");
        System.out.println(sjn.add("123").add("456"));

        System.out.println(StringUtils.join("11", "22", "33"));
        System.out.println(StringUtils.join(new Object[]{"11", "22", "33"}, "-"));

    }

    static void regular() {
        // https://zhuanlan.zhihu.com/p/59904349
        String ss = "12";
        //ss = null;
        //ss = "";
        //ss = "1.2";
        //ss = " ";
        //数字、非空
        Matcher m1 = Pattern.compile("^[1-9]\\d*$").matcher(ss);
        //数字、空
        Matcher m2 = Pattern.compile("^$|^[1-9]\\d*$").matcher(ss);
        System.out.println(m1.matches());
        System.out.println(m2.matches());

    }
}
