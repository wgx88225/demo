package com.example.demo.test;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * @ClassName ZipUtil.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年10月15日 14:37:00
 */
public class ZipUtil {

    public static void main(String[] args) throws Exception {
        File file = new File("D:\\ziptest\\test\\资源订阅.zip");
//        unZipDirToFiles(file,"D:\\ziptest\\test2");
        unZipDirToFiles(file);

//        readZipFile();
    }

    public static void readZipFile(String targetFileName) throws Exception {
        File file = new File("D:\\ziptest\\test\\资源订阅.zip");
        ZipFile  zip = new ZipFile(file, Charset.forName("GBK"));
        InputStream in = new BufferedInputStream(new FileInputStream(file));
        ZipInputStream zin = new ZipInputStream(in);
        ZipEntry ze;
        while ((ze = zin.getNextEntry()) != null) {
            if (ze.isDirectory()) {
            } else {
                System.out.println("file - " + ze.getName() + " : "
                        + ze.getSize() + " bytes");
                long size = ze.getSize();
                if (size > 0) {
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(zip.getInputStream(ze)));
                    String line;
                    while ((line = br.readLine()) != null) {
                        System.out.println(line);
                    }
                    br.close();
                }
                System.out.println();
            }
        }
        zin.closeEntry();
    }

    public static void unZipDirToFiles(File zipFile) throws IOException {


        ZipFile  zip = new ZipFile(zipFile, Charset.forName("GBK"));
        for (Enumeration entries = zip.entries(); entries.hasMoreElements(); ) {
            ZipEntry ze = (ZipEntry) entries.nextElement();
            //获取压缩目录名称
            String zipEntryName = ze.getName();
            System.out.println(zipEntryName);
            if(zipEntryName.contains("upDeviceResourceSub.json")){
                System.out.println("获取压缩目录名称==>" + zipEntryName);
                InputStream in = zip.getInputStream(ze);
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(zip.getInputStream(ze)));
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
                br.close();
                in.close();
            }



        }
        System.out.println("******************解压完毕********************");
    }

    public static void unZipDirToFiles(File zipFile, String descDir) throws IOException {
        File pathFile = new File(descDir);

        if (!pathFile.exists()) {
            pathFile.mkdirs();

        }
        ZipFile  zip = new ZipFile(zipFile, Charset.forName("GBK"));
        for (Enumeration entries = zip.entries(); entries.hasMoreElements(); ) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
           //获取压缩目录名称
            String zipEntryName = entry.getName();
            InputStream in = zip.getInputStream(entry);
            String outPath = (descDir + "//" + zipEntryName).replaceAll("\\*", "/");
           //判断路径是否存在,不存在则创建文件路径
            File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
            if (!file.exists()) {
                file.mkdirs();
            }
             //判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
            if (new File(outPath).isDirectory()) {
                continue;
            }
           //输出文件路径信息
            System.out.println(outPath);
            OutputStream out = new FileOutputStream(outPath);
            byte[] buf1 = new byte[1024];
            int len;
            while ((len = in.read(buf1)) > 0) {
                out.write(buf1, 0, len);
            }
            in.close();
            out.close();
        }
        System.out.println("******************解压完毕********************");
    }

    public static void unzip(String filePath) throws Exception {
        File source = new File(filePath);
        if (source.exists()) {
            ZipInputStream zis = null;
            BufferedOutputStream bos = null;
            try {
                zis = new ZipInputStream(new FileInputStream(source));
                ZipEntry entry = null;
                while ((entry = zis.getNextEntry()) != null
                        && !entry.isDirectory()) {
                    File target = new File(source.getParent(), entry.getName());
                    if (!target.getParentFile().exists()) {
                        // 创建文件父目录
                        target.getParentFile().mkdirs();
                    }
                    // 写入文件
                    bos = new BufferedOutputStream(new FileOutputStream(target));
                    int read = 0;
                    byte[] buffer = new byte[1024 * 10];
                    while ((read = zis.read(buffer, 0, buffer.length)) != -1) {
                        bos.write(buffer, 0, read);
                    }
                    bos.flush();
                }
                zis.closeEntry();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
//                IOUtils.closeQuietly(zis, bos);
            }
        }
    }


}
