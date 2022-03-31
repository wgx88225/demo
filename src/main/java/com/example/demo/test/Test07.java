package com.example.demo.test;

import org.apache.commons.io.FilenameUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.StringJoiner;
import java.util.stream.IntStream;

/**
 * @ClassName Test07.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年11月10日 11:59:00
 */
public class Test07 {
    public static void main(String[] args) {
//        long a = 4010963900000L;
//        long b = 1 << 30 ;
////        DecimalFormat  df=new DecimalFormat("0.00");
////        String c=df.format((double) a/b);
////        System.out.println(c);
//        double f1 = new BigDecimal((float)a/b).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//
//        System.out.println(f1);
//        String extension = FilenameUtils.getExtension("acbf5a96-b2f7-4d42-b526-dfdc1a6190d2.png");
//        System.out.println(extension);

//        StringJoiner joiner = new StringJoiner(";");
//        IntStream.range(1,10).forEach(i -> joiner.add(i+""));
//        System.out.println(joiner);
//        String s1 = "E";
//        String s2 = "A";
//
//        char oldO = s1.charAt(0);
//        char newO = s2.charAt(0);
//
//        System.out.println(oldO-newO > 0);

        System.out.println(new Random().nextInt(999));
    }
}
