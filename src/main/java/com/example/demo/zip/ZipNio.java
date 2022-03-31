package com.example.demo.zip;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @ClassName ZipTest.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年11月17日 12:42:00
 */
public class ZipNio {

    static List<String> fileUrlList = Lists.newArrayList();
    static String FILE = "D:\\ziptest\\test9528.zip";
    static File ZIP_FILE;

    static {
        try {
            String str = FileUtils.readFileToString(new File("D:\\ziptest\\test.json"), Charset.forName("utf-8"));
            fileUrlList = JSONArray.parseArray(str, String.class);
//            fileUrlList.add("D:\\ziptest\\测试002.mp4"); // 224M
//            fileUrlList.add("D:\\ziptest\\new.zip"); // 23.7M
//            fileUrlList.add("D:\\uploadPath\\download\\resource-zip\\广州2服务器_20211117172152140308.zip"); // 23.7M

        } catch (IOException e) {
            e.printStackTrace();
        }
        ZIP_FILE = new File(FILE);
        if (ZIP_FILE.exists()) ZIP_FILE.delete();
    }

    public static void main(String[] args) {
//        zipFileNoBuffer(); // 23.7M -- 耗时：32597
//        zipFileBuffer(); // 23.7M -- 耗时：1553
        zipFileChannel(); // 23.7M -- 耗时：911
//        zipFileMap(); // 23.7M -- 耗时：10
    }


    public static void zipFileNoBuffer() {
        //开始时间
        long beginTime = System.currentTimeMillis();
        try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(ZIP_FILE))) {

            for (String item : fileUrlList) {
                try (InputStream input = new FileInputStream(item)) {
                    zipOut.putNextEntry(new ZipEntry(item));
                    int temp = 0;
                    while ((temp = input.read()) != -1) {
                        zipOut.write(temp);
                    }
                }
            }
            //结束时间
            long endTime = System.currentTimeMillis();
            System.out.println("耗时：" + (endTime - beginTime));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void zipFileBuffer() {

        try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(ZIP_FILE));
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(zipOut)) {
            //开始时间
            long beginTime = System.currentTimeMillis();
            for (String item : fileUrlList) {
                try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(item))) {
                    zipOut.putNextEntry(new ZipEntry(item));
                    int temp = 0;
                    while ((temp = bufferedInputStream.read()) != -1) {
                        bufferedOutputStream.write(temp);
                    }
                }
            }
            //结束时间
            long endTime = System.currentTimeMillis();
            System.out.println("耗时：" + (endTime - beginTime));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void zipFileChannel() {
        //开始时间
        long beginTime = System.currentTimeMillis();

        try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(ZIP_FILE));
             WritableByteChannel writableByteChannel = Channels.newChannel(zipOut)) {
            for (String item : fileUrlList) {
                File file = new File(item);
                try (FileChannel fileChannel = new FileInputStream(item).getChannel()) {
                    zipOut.putNextEntry(new ZipEntry(item));
                    fileChannel.transferTo(0, file.length(), writableByteChannel);
                }
            }

            //结束时间
            long endTime = System.currentTimeMillis();
            System.out.println("耗时：" + (endTime - beginTime));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void zipFileMap() {
        //开始时间
        long beginTime = System.currentTimeMillis();

        try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(ZIP_FILE));
             WritableByteChannel writableByteChannel = Channels.newChannel(zipOut)) {
            for (String item : fileUrlList) {
                File file = new File(item);
                zipOut.putNextEntry(new ZipEntry(item));

                //内存中的映射文件
                MappedByteBuffer mappedByteBuffer = new RandomAccessFile(item, "r").getChannel()
                        .map(FileChannel.MapMode.READ_ONLY, 0, file.length());

                writableByteChannel.write(mappedByteBuffer);
            }
            //结束时间
            long endTime = System.currentTimeMillis();
            System.out.println("耗时：" + (endTime - beginTime));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}