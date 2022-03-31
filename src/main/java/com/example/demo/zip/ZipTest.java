package com.example.demo.zip;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.*;

/**
 * @ClassName ZipTest.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年11月17日 12:42:00
 */
public class ZipTest {




    public static void main(String[] args) throws Exception {
        testZip();

//        run();

    }



    private static void testZip() throws Exception {
        String str = FileUtils.readFileToString(new File("D:\\ziptest\\test.json"), Charset.forName("utf-8"));


        List<String> fileUrlList = JSONArray.parseArray(str, String.class);
        long start = System.currentTimeMillis();
        System.out.println("开始压缩>>>>>>>>>>");
        String dest = "D:\\ziptest\\9527.zip";
//        CompressUtils.zip(fileUrlList, dest, null);
        CompressUtils.zip2(fileUrlList, dest, null);
//        CompressUtils.toZip(fileUrlList, dest);
//        CompressUtils.compress7z(fileUrlList,  "D:\\ziptest\\9527.7z");
//        CompressUtils.compressFiles2Zip(fileUrlList,  dest);
//        CompressUtils.zipFile(fileUrlList,  dest);
        System.out.println("压缩结束>>>>>>>>>>");
        System.out.println("耗时>>>>>>>>>>" + (System.currentTimeMillis() - start));
    }

}
