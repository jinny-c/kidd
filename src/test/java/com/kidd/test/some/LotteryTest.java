package com.kidd.test.some;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class LotteryTest {
    private static List<Integer> blue = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
            11, 12, 13, 14, 15, 16);
    private static List<Integer> red1 = new ArrayList<Integer>() {
        {
            add(1);
            add(2);
            add(3);
            add(4);
            add(5);
            add(6);
            add(7);
            add(8);
            add(9);
            add(10);
            add(11);
            add(12);
            add(13);
            add(14);
            add(15);
            add(16);
            add(17);
            add(18);
            add(19);
            add(20);
            add(21);
            add(22);
            add(23);
            add(24);
            add(25);
            add(26);
            add(27);
            add(28);
            add(29);
            add(30);
            add(31);
            add(32);
            add(33);
        }
    };
    private static int[] blue1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
            11, 12, 13, 14, 15, 16};

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        someThings();
//        List<Integer> l1 = Arrays.asList(10, 21, 3, 14, 25, 6);
//        BallEnty enty1 = new BallEnty();
//        enty1.setBlue(10);
//        enty1.setRed(l1);
//        List<Integer> l2 = Arrays.asList(3, 25, 14, 6, 10, 21);
//        BallEnty enty2 = new BallEnty();
//        enty2.setBlue(10);
//        enty2.setRed(l2);
//        System.out.println("" + enty1.equals(enty2));
//
//        System.out.println("" + l1.equals(l2));
    }

    private static void someThings() {
        try {
            BallEnty enty1 = null;
            BallEnty enty2 = null;
            int count = 0;
            while (true) {
                enty1 = first();
                enty2 = second();
                //System.out.println("enty1=" + enty1);
                //System.out.println("enty2=" + enty2);
                if (enty1.equals(enty2)) {
                    //System.out.println("enty=" + enty1);
                    log.info("enty1={},enty2={}", enty1, enty2);
                    break;
                }
                if (enty1.getRed().equals(enty2.getRed())) {
                    //System.out.println("enty=" + enty2);
                    log.info("enty1={},enty2={}", enty1, enty2);
                    break;
                }
                count++;
                //System.out.println("count=" + count);
                log.info("count={}", count);
            }
            System.out.println("end count=" + count);
        } catch (Exception e) {
            log.info("===", e);
        }
    }

    private static BallEnty first() {
        List<Integer> reds = new ArrayList<Integer>() {
            {
                add(1);
                add(2);
                add(3);
                add(4);
                add(5);
                add(6);
                add(7);
                add(8);
                add(9);
                add(10);
                add(11);
                add(12);
                add(13);
                add(14);
                add(15);
                add(16);
                add(17);
                add(18);
                add(19);
                add(20);
                add(21);
                add(22);
                add(23);
                add(24);
                add(25);
                add(26);
                add(27);
                add(28);
                add(29);
                add(30);
                add(31);
                add(32);
                add(33);
            }
        };
        Random random = new Random();
        List<Integer> my = new ArrayList<>();
        while (true) {
            //log.info("reds={}",reds);
            int len = reds.size();
            //log.info("len={}",len);
            //System.out.println(len);
            //int l = random.nextInt(len + 1);
            int l = random.nextInt(len);
            //log.info("l={}",l);
            my.add(reds.get(l));
            reds.remove(l);
            if (my.size() > 5) {
                break;
            }
        }
//        System.out.println("red=" + my);
//        System.out.println("blue=" + blue.get(random.nextInt(16 + 1)));
        BallEnty enty = new BallEnty();
        enty.setBlue(blue.get(random.nextInt(15) + 1));
        enty.setRed(my);
        return enty;
    }

    private static BallEnty second() {
        Set<Integer> myRed = new HashSet<>();
        while (true) {
            int i = (int) (Math.random() * 33 + 1);
            myRed.add(i);
            if (myRed.size() >= 6) {
                break;
            }
        }
        int myBlue = (int) (Math.random() * 16 + 1);
        //System.out.println("myRed=" + myRed + "myBlue=" + myBlue);

        BallEnty enty = new BallEnty();
        enty.setBlue(myBlue);
        enty.setRed(new ArrayList<>(myRed));
        return enty;
    }


    @Getter
    @Setter
    @ToString
    static
    class BallEnty {
        Integer blue;
        List<Integer> red;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BallEnty ballEnty = (BallEnty) o;
            return Objects.equals(blue, ballEnty.blue) && equalsList(red, ballEnty.red);
        }

        @Override
        public int hashCode() {
            return Objects.hash(blue, red);
        }

        private boolean equalsList(List<Integer> red1, List<Integer> red2) {
            if (null == red1 || red1.isEmpty() || null == red2 || red2.isEmpty()) {
                return false;
            }
            Collections.sort(red1);
            Collections.sort(red2);
            return red1.equals(red2);
//            red1.forEach(e->{
//                red2.forEach(r->{
//                    if(!e.equals(r)){
//                        return;
//                    }
//                });
//            });
//            return false;
        }
    }
}
