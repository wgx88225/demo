package com.example.demo.test;

/**
 * @ClassName ReadFile.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年10月15日 16:50:00
 */

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class ReadFile {
    public static void main(String[] args) throws IOException {
        run();
    }


    public static void run() {
        String path = "D:\\ziptest\\test\\资源订阅.zip";
        ZipInputStream zin = null;
        try {
            zin = new ZipInputStream(new FileInputStream(path), StandardCharsets.UTF_8);
            ZipFile  zf = new ZipFile(new File(path), Charset.forName("GBK"));
            ZipEntry ze;
            while ((ze = zin.getNextEntry()) != null) {
                if (ze.toString().endsWith("/node_modules/define-property/package.json")) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(zf.getInputStream(ze)));
                    String line;
                    StringBuilder sb = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        sb.append(line.toString().trim());
                    }
                    System.out.println(Thread.currentThread().getName() + " :: " + ze.getName() + " :: " + sb.toString());
                    br.close();
                    break;
                }
            }
            System.out.println();
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (zin != null) {
                try {
                    zin.closeEntry();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}