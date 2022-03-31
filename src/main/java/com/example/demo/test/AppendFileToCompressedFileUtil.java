package com.example.demo.test;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.Deflater;

/**
 * @ClassName AppendFileToCompressedFileUtil.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年10月15日 12:39:00
 */
public class AppendFileToCompressedFileUtil {
    private static final String compressedFilePath = "D:\\ziptest\\data.zip";
    private static final String newCompressedFilePath = "D:\\ziptest\\测试压缩2.zip";
    private static final String appendFilePackage = "data";

    public void append(String appendFile) throws Exception {
        ZipOutputStream zos = null;
        InputStream input = null;
        File newCompressedFile = new File(newCompressedFilePath);
        if (newCompressedFile.exists()) {
            newCompressedFile.delete();
        }
        try {
            ZipFile compressedFile = new ZipFile(compressedFilePath, "GBK");
            zos = new ZipOutputStream(new FileOutputStream(newCompressedFilePath));
            zos.setEncoding("GBK");
            zos.setComment("Bale tdp!");
            zos.setLevel(Deflater.BEST_COMPRESSION);
            zos.setMethod(Deflater.DEFLATED);
            //
            if (!"".equals(appendFile)) {
                File f = new File(appendFile);
                ZipEntry pag = new ZipEntry(appendFilePackage + f.separator);
                zos.putNextEntry(pag);
                ZipEntry fileEntry = new ZipEntry(appendFilePackage + f.separator + f.getName());
                zos.putNextEntry(fileEntry);
                input = new FileInputStream(f);
                startCopy(zos, input);
            }
            Enumeration<? extends ZipEntry> e = compressedFile.getEntries();
            while (e.hasMoreElements()) {
                ZipEntry entry = e.nextElement();
                zos.putNextEntry(entry);
                if (!entry.isDirectory()) {
                    startCopy(zos, compressedFile.getInputStream(entry));
                }
                zos.closeEntry();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (zos != null)
                zos.close();
        }
    }

    public void startCopy(ZipOutputStream zos, InputStream input) throws Exception {
        int data = 0;
        try {
            while ((data = input.read()) != -1) {
                zos.write(data);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (input != null)
                input.close();
        }
    }

    // main test method
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        AppendFileToCompressedFileUtil a = new AppendFileToCompressedFileUtil();
        String append = "D:\\ziptest\\测试9529.pdf";
        a.append(append);
        System.out.println("总耗时：" + (System.currentTimeMillis() - start) + " 毫秒");
    }
}
