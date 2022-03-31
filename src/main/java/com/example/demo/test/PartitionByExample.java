package com.example.demo.test;

import cn.hutool.core.collection.ListUtil;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.ListUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName PartitionByGuavaExample.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年11月15日 09:13:00
 */
public class PartitionByExample {
    // 原集合
    private static final List<String> OLD_LIST = Arrays.asList(
            "唐僧,悟空,八戒,沙僧,曹操,刘备,孙权".split(","));

    public static void main(String[] args) {
//        testListUtils();
        testByStream();
    }

    public static void testGuava() {
        // 集合分片
        List<List<String>> newList = Lists.partition(OLD_LIST, 3);
        // 打印分片集合
        newList.forEach(
                System.out::println
        );


    }


    public static void testListUtils() {
        // 集合分片

        List<List<String>> newList = ListUtils.partition(OLD_LIST, 3);
        newList.forEach(
                System.out::println
        );
    }

    public static void testHutool() {
        // 集合分片

        List<List<String>> newList = ListUtil.partition(OLD_LIST, 3);
        newList.forEach(
                System.out::println
        );
    }

    public static void testByStream() {
        // 集合分片
        // 原集合
         List<Integer> OLD_LIST2 = Arrays.asList(
                1, 2, 3, 4, 5, 6);
        // 集合分片：将大于 3 和小于等于 3 的数据分别分为两组
        Map<Boolean, List<Integer>> newMap = OLD_LIST2.stream().collect(
                Collectors.partitioningBy(i -> i > 3)
        );
        // 打印结果
        System.out.println(newMap);
    }
}
