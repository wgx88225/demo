package com.example.demo.zip;

import com.google.common.collect.Lists;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @ClassName ZiPtST.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年11月02日 18:55:00
 */
public class ZiPtST {

    public static void main(String[] args) {


        boolean marge = Marge("D:\\ziptest\\new.zip", "D:\\ziptest\\101.zip", "D:\\ziptest\\102.zip");
        System.out.println(marge);
    }

    /**
     * 合并压缩文件
     *
     * @param tartetZipFile
     * @param sourceZipFiles
     * @return
     */
    public static boolean Marge(String tartetZipFile, String... sourceZipFiles) {
        boolean flag = true;
        ZipOutputStream out = null;
        List<ZipInputStream> ins = new ArrayList<ZipInputStream>();
        try {
            out = new ZipOutputStream(new FileOutputStream(tartetZipFile));
            HashSet<String> names = new HashSet<String>();
            for (String sourceZipFile : sourceZipFiles) {

                ZipFile zipFile = new ZipFile(sourceZipFile, Charset.forName("GBK"));
                ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(sourceZipFile));
                ins.add(zipInputStream);
                ZipEntry ze;
                Enumeration<? extends ZipEntry> enumeration = zipFile.entries();
                while (enumeration.hasMoreElements()) {
                    ze = enumeration.nextElement();
                    if (ze.isDirectory()) {

                    } else {
                        if (names.contains(ze.getName())) {

                            continue;
                        }
                        ZipEntry oze = new ZipEntry(ze.getName());
                        out.putNextEntry(oze);
                        if (ze.getSize() > 0) {
                            DataInputStream dis = new DataInputStream(zipFile.getInputStream(ze));
                            int len = 0;
                            byte[] bytes = new byte[1024];
                            while ((len = dis.read(bytes)) > 0) {
                                out.write(bytes, 0, len);
                            }
                            out.closeEntry();
                            out.flush();
                        }
                        names.add(oze.getName());
                    }
                }
                zipInputStream.closeEntry();
                flag = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            flag = false;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            try {
                for (ZipInputStream in : ins) {
                    if (in != null) {
                        in.close();
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return flag;
    }
}
