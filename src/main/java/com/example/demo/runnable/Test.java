package com.example.demo.runnable;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @ClassName Test.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年10月15日 13:19:00
 */
public class Test {
    public static void main(String[] args) {

        String tempPath =System.getProperty("java.io.tmpdir")+File.separator;
        System.out.println(tempPath);
        //获取秒数
//        Long second = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
////获取毫秒数
//        Long milliSecond = LocalDateTime.now().plusDays(30).toInstant(ZoneOffset.of("+8")).toEpochMilli();
//
//        System.out.println(second);
//        System.out.println(milliSecond);
//        System.out.println(System.currentTimeMillis());
//        long start = System.currentTimeMillis();
//        File file = new File("D:\\新建文件夹\\test001.mp4");
//        GZipFiles.GZip(file);
//        File file2 = new File("D:\\新建文件夹\\test002.mp4");
//        GZipFiles.GZip(file2);
//        GZipFiles.shutdown();
//        long end = System.currentTimeMillis();
//
//        System.out.println("总耗时：" + (end - start));
    }
}
