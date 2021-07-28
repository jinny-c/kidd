package com.kidd.test.object;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.util.Collection;
import java.util.Map;

/**
 * @description 不可变对象测试
 * @auth chaijd
 * @date 2021/7/28
 */
public class ImmutableTest {

    public static void main(String[] args) {
        new ImmutableTest().objTest();
    }

    public void objTest(){
        ImmutablePOJO pojo = new ImmutablePOJO("xiaoli");
        System.out.println(pojo);
    }

    /**
     * 不可变list
     * @param cllc
     * @return
     */
    public ImmutableList getList(Collection cllc) {
        ImmutableList list = ImmutableList.of();
        return ImmutableList.copyOf(cllc);
    }
    /**
     * 不可变map
     * @param mp
     * @return
     */
    public ImmutableMap getMap(Map mp) {
        ImmutableMap map = ImmutableMap.of();
        return ImmutableMap.copyOf(mp);
    }

    //(Persistant Object)可以看成是与数据库中的表相映射的java对象
    //数据库表对应
    @Data
    class PersongPO {
        private String id;
        private String name;
        private int age;
    }
    //(Value Object)值对象。通常用于业务层之间的数据传递，和PO一样也是仅仅包含数据而已。
    //前端对象
    @Data
    class QueryVO{
        private String nextOne;
        private int age;
        //当前页数
        private int current;
    }
    //(Business Object)业务对象，封装业务逻辑的java对象,通过调用DAO方法,结合PO,VO进行业务操作
    //业务处理对象
    @Data
    class RelationshipBO {
        private String id1;
        private String id2;

        //是否有关联
        public boolean hasRelations(String id1, String id2) {
            return false;
        }
    }
    //(Data Transfer Object,数据传输对象)主要用于远程调用等需要大量传输对象的地方
    @Data
    class RealOneDTO{
        private String name;
    }
    //(Plain Ordinary Java Object简单无规则java对象)是纯粹的传统意义的java对象
    //不可变（反射除外）
    @Getter
    @ToString
    class ImmutablePOJO {
        private final String name;
        private int age;
        private String id;

        ImmutablePOJO(String name) {
            this.name = name;
        }

        public ImmutablePOJO(String name, int age, String id) {
            this.name = name;
            this.age = age;
            this.id = id;
        }

        public ImmutablePOJO set(String name, int age, String id) {
            return new ImmutablePOJO(name, age, id);
        }
    }
}
