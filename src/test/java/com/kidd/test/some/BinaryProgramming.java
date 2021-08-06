package com.kidd.test.some;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @description 数学问题转为二进制问题
 * 七瓶药水一瓶有毒三只小白鼠一个小时
 * @auth chaijd
 * @date 2021/8/6
 */
public class BinaryProgramming {
    public static void main(String[] args) {
//        experiment1();
        experiment2();
    }

    private static void experiment1() {
        //老鼠个数
        Integer mouse = 3;
        //2的3次方 -1
        Integer bottledWater = (1 << 3) - 1;
//        System.out.println(bottledWater);
//        double power = Math.pow(2,3);
//        System.out.println(power);
        String binaryStr = "000";
        String[] strArray = new String[bottledWater];
        while (true) {
            if (bottledWater == 0) {
                break;
            }
            binaryStr = Integer.toBinaryString(bottledWater);
            strArray[bottledWater - 1] = StringUtils.leftPad(binaryStr, mouse, "0");
            //System.out.println(strArray[bottledWater-1]);
            bottledWater--;
        }
        System.out.println(Arrays.asList(strArray));
        //System.out.println(mouse);
        StringBuilder sbd = null;
        while (true) {
            if (mouse == 0) {
                break;
            }
            //老鼠编号
            sbd = new StringBuilder().append(mouse).append("#");

            for (int i = 0; i < strArray.length; i++) {
                if (StringUtils.equals("1", StringUtils.mid(strArray[i], mouse - 1, 1))) {
                    sbd.append("-").append(i + 1).append("(").append(strArray[i]).append(")");
                }
            }
            //瓶子号
            System.out.println(sbd.toString());
            mouse--;
        }

//        3#-1(001)-3(011)-5(101)-7(111)
//        2#-2(010)-3(011)-6(110)-7(111)
//        1#-4(100)-5(101)-6(110)-7(111)
//        1表示死，0表示活
//        3死-001；2死010；1死100；23死-011；13死-101；12死-110；123死-111
//        转10进制，即为几号瓶子有毒

    }

    private static void experiment2() {
        //重复值后的返回值
        Map<String, String> mp = new HashMap<>();
        mp.containsKey("k");

        Set<Object> st = new HashSet<>();
        boolean ad1 = st.add("123");
        System.out.println(String.valueOf(ad1));
        boolean ad2 = st.add("123");
        System.out.println(ad2 + "");

        boolean ad3 = st.add(mp);
        System.out.println(ad3 + "");
        boolean ad4 = st.add(mp);
        System.out.println(ad4 + "");
    }

}
