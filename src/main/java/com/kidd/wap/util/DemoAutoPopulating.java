package com.kidd.wap.util;

import org.springframework.util.AutoPopulatingList;

/**
 * @author chaijd
 * @description TODO
 * @date 2024-05-30
 */
public class DemoAutoPopulating {
    public static void main(String[] args) {
        autoList();
    }

    private static void autoList() {
        AutoPopulatingList<String> strList = new AutoPopulatingList<>(String.class);
        String s = strList.get(5);    // 获取不到元素，则会根据给定索引自动填充集合
        strList.set(2,"test");
        for (String str : strList) {
            System.out.print(str + "   ");    // 输出：null   null   test   null   null
        }


        //AutoPopulatingMap<String, String> strMap = new AutoPopulatingMap<>(String.class, String.class);
    }

}
