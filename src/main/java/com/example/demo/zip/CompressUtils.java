package com.example.demo.zip;

/**
 * @ClassName CompressUtils.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年11月03日 16:07:00
 */

import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile;
import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;

import java.io.*;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


/**
 * 对文件进行压缩和加密
 * 对文件进行解压和解密
 *
 * @author fenghao
 */
@Slf4j
public class CompressUtils {


    public static void main(String[] args) throws Exception {
//        zip("D:\\ziptest\\v1.0 - 副本.apk", "D:\\ziptest\\zz\\", false, "222");
//        System.out.println("压缩结束");

        unZip(new File("D:\\ziptest\\zz\\v1.0 - 副本.zip"), "D:\\ziptest\\zz\\yy", "222");
        System.out.println("解压结束");
    }

    /**
     * GZIP方式 对文件进行压缩
     *
     * @param source 源文件
     * @param target 目标文件
     * @throws IOException
     */
    public static void zipFile(List<String> srcList, String target) throws IOException {
        long start = System.currentTimeMillis();
        FileInputStream fin = null;
        FileOutputStream fout = null;
        GZIPOutputStream gzout = null;
        try {
            for (String url : srcList) {
                fin = new FileInputStream(url);
                fout = new FileOutputStream(target);
                gzout = new GZIPOutputStream(fout);
                byte[] buf = new byte[1024 * 1024];
                int num;
                while ((num = fin.read(buf)) != -1) {
                    gzout.write(buf, 0, num);
                }
            }
        } finally {
            if (gzout != null)
                gzout.close();
            if (fout != null)
                fout.close();
            if (fin != null)
                fin.close();
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    public static void compressFiles2Zip(List<String> srcList, String zipFilePath) {
        if (!ObjectUtils.isEmpty(srcList)) {
            ZipArchiveOutputStream zaos = null;
            File f = new File(zipFilePath);
            if (f.isDirectory()) {
                System.out.println("isDirectory");
                return;
            }
            if (f.exists()) {
                f.delete();
            }
            try {
                File zipFile = new File(zipFilePath);
                zaos = new ZipArchiveOutputStream(zipFile);
                zaos.setUseZip64(Zip64Mode.AsNeeded);

                int index = 0;
                for (String url : srcList) {
                    File file = new File(url);
                    if (file.exists()) {
                        ZipArchiveEntry zipArchiveEntry = new ZipArchiveEntry(file, file.getName());
                        zaos.putArchiveEntry(zipArchiveEntry);
                        InputStream is = null;
                        try {
                            is = new BufferedInputStream(new FileInputStream(file));
                            byte[] buffer = new byte[1024 * 1024 * 10];
                            int len = -1;
                            while ((len = is.read(buffer)) != -1) {
                                // 把缓冲区的字节写入到ZipArchiveEntry
                                zaos.write(buffer, 0, len);
                            }
                            // Writes all necessary data for this entry.
                            zaos.closeArchiveEntry();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        } finally {
                            if (is != null)
                                is.close();
                        }
                    }
                }
                zaos.finish();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    if (zaos != null) {
                        zaos.close();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }

    }

    /**
     * 7z文件压缩
     *
     * @param srcList    待压缩文件件名
     * @param outputFile 生成的压缩包名字
     */

    public static void compress7z(List<String> srcList, String outputFile) throws Exception {
        SevenZOutputFile out = new SevenZOutputFile(new File(outputFile));
        compress(out, srcList);
        out.close();
    }

    /**
     *
     */
    //递归压缩
    public static void compress(SevenZOutputFile out, List<String> srcList) throws IOException {

        SevenZArchiveEntry entry = null;
        for (String bo : srcList) {
            File input = new File(bo);
            FileInputStream fos = new FileInputStream(input);
            BufferedInputStream bis = new BufferedInputStream(fos);
            entry = out.createArchiveEntry(input, input.getName());
            out.putArchiveEntry(entry);
            int len = -1;
            //将源文件写入到7z文件中
            byte[] buf = new byte[1024];
            while ((len = bis.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
            bis.close();
            fos.close();
        }

        out.closeArchiveEntry();
//        }
    }

    /**
     * 解压加密的压缩文件
     *
     * @param zipfile
     * @param dest
     * @param passwd
     * @throws ZipException
     */
    public static void unZip(File zipfile, String dest, String passwd) throws ZipException {
        ZipFile zfile = new ZipFile(zipfile);
//        zfile.setFileNameCharset("GBK");//在GBK系统中需要设置
        if (!zfile.isValidZipFile()) {
            throw new ZipException("压缩文件不合法，可能已经损坏！");
        }
        File file = new File(dest);
        if (file.isDirectory() && !file.exists()) {
            file.mkdirs();
        }
        if (zfile.isEncrypted()) {
            zfile.setPassword(passwd.toCharArray());
        }
        zfile.extractAll(dest);
    }

    public static void zip2(List<String> fileUrlList, String dest, String passwd) throws Exception {
        try {
            File destFile = new File(dest);
            if (destFile.exists()) {
                destFile.delete();
            }
            ZipParameters par = new ZipParameters();
            par.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            par.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
            if (StringUtils.isNoneBlank(passwd)) {
                par.setEncryptFiles(true);
                par.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
                par.setPassword(passwd.toCharArray());
            }
            ZipFile zipfile = new ZipFile(dest);

            File tempDir = new File("D:\\ziptest\\temp");
            ArrayList<File> temp = new ArrayList<File>();
            long start = System.currentTimeMillis();
            int i = 1;
            for (String src : fileUrlList) {
                String extension = StringUtils.lowerCase(FilenameUtils.getExtension(src));
                File file1 = new File(tempDir, i + "." + extension);
                FileUtils.copyFile(new File(src), new File(tempDir, i + "." + extension));
//                File file = new File(tempDir, new File(src).getName());
                temp.add(file1);
                i++;
            }
            System.out.println("拷贝文件耗时:" + (System.currentTimeMillis() - start));
//            zipfile.createZipFileFromFolder(tempDir, par, true, 65536*1000L);
//            temp.addAll(srcList);
            zipfile.addFiles(temp, par);
//            zipfile.addFolder(tempDir, par);

//            tempDir.delete();
            FileUtils.deleteDirectory(tempDir);
        } catch (ZipException e) {
            e.printStackTrace();
            log.error("压缩文件失败", e.getMessage(), e);
            throw new RuntimeException("压缩文件失败！");
        }
    }

    public static void zip(List<String> srcList, String dest, String passwd) throws IOException {
        //创建目标文件
//        File file = new File(dest);
//        if (!file.exists()) {
//            file.createNewFile();
//        } else {
//            file.delete();
//        }
        String destname = dest;
        ZipParameters par = new ZipParameters();
        par.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        par.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
        if (passwd != null) {
            par.setEncryptFiles(true);
            par.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
            par.setPassword(passwd.toCharArray());
        }
        try {
            ZipFile zipfile = new ZipFile(destname);
            List<File> collect = srcList.stream().map(item -> new File(item)).collect(Collectors.toList());
            long sum = collect.stream().mapToLong(item -> item.length()).sum();
            long g = 1 << 30;
            double size = new BigDecimal((double) sum / g).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            System.out.println("文件大小：" + size + " G");
            ArrayList<File> temp = new ArrayList<File>();
            temp.addAll(collect);
            zipfile.addFiles(temp, par);
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param srcList
     * @param downloadPath
     * @return void
     * @description 普通文件压缩
     * @author Vince
     * @version V1.0.0
     * @createTime 2021/11/17 13:22
     */
    public static void toZip(List<String> srcList, String downloadPath) throws IOException {
        ZipOutputStream zos = null;
        FileOutputStream fos = null;
        FileInputStream fis = null;
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 1024 * 1024);
        try {
            File zipFile = new File(downloadPath);
            if (!zipFile.exists()) {
                zipFile.createNewFile();
            }
            fos = new FileOutputStream(zipFile);
            zos = new ZipOutputStream(fos);
            List<File> collect = srcList.stream().map(item -> new File(item)).collect(Collectors.toList());
            long sum = collect.stream().mapToLong(item -> item.length()).sum();
            long g = 1 << 30;
            double size = new BigDecimal((double) sum / g).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            System.out.println("文件大小：" + size + " G");
            for (String bo : srcList) {
                File file = new File(bo);
                FileChannel channel = new FileInputStream(file).getChannel();
                // 以资源主键ID 为压缩后的文件名 方便后期对应查找
                zos.putNextEntry(new ZipEntry(file.getName()));
//                log.debug("生成资源压缩文件名:{}", file.getName());
                fis = new FileInputStream(file);
                byte[] buf = new byte[1024 * 1024 * 10];
                int len = 0;
                while ((len = fis.read(buf)) != -1) {
                    zos.write(buf, 0, len);
                }
//                while (true) {
//                    byteBuffer.clear();
//                    int read = channel.read(byteBuffer);
//                    if (read == -1) break;
//                    zos.write(byteBuffer.array());
//                }
//                channel.close();
            }
            zos.flush();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("文件压缩异常ERROR", e.getMessage(), e);
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (zos != null) {
                zos.close();
            }
            if (fos != null) {
                fos.close();
            }
        }

    }

    /**
     * 压缩文件且加密
     *
     * @param src
     * @param dest
     * @param is
     * @param passwd
     */
    public static void zip(String src, String dest, boolean is, String passwd) {
        File srcfile = new File(src);
        //创建目标文件
        String destname = buildDestFileName(srcfile, dest);
        ZipParameters par = new ZipParameters();
        par.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        par.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
        if (passwd != null) {
            par.setEncryptFiles(true);
            par.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
            par.setPassword(passwd.toCharArray());

        }
        try {
            ZipFile zipfile = new ZipFile(destname);
            if (srcfile.isDirectory()) {
                if (!is) {
                    File[] listFiles = srcfile.listFiles();
                    ArrayList<File> temp = new ArrayList<File>();
                    Collections.addAll(temp, listFiles);
                    zipfile.addFiles(temp, par);
                }
                zipfile.addFolder(srcfile, par);
            } else {
                zipfile.addFile(srcfile, par);
            }
        } catch (ZipException e) {
            e.printStackTrace();
        }


    }

    /**
     * 目标文件名称
     *
     * @param srcfile
     * @param dest
     * @return
     */
    public static String buildDestFileName(File srcfile, String dest) {
        if (dest == null) {//没有给出目标路径时
            if (srcfile.isDirectory()) {
                dest = srcfile.getParent() + File.separator + srcfile.getName() + ".zip";
            } else {
                String filename = srcfile.getName().substring(0, srcfile.getName().lastIndexOf("."));
                dest = srcfile.getParent() + File.separator + filename + ".zip";
            }
        } else {
            createPath(dest);//路径的创建
            if (dest.endsWith(File.separator)) {
                String filename = "";
                if (srcfile.isDirectory()) {
                    filename = srcfile.getName();
                } else {
                    filename = srcfile.getName().substring(0, srcfile.getName().lastIndexOf("."));
                }
                dest += filename + ".zip";
            }
        }
        return dest;
    }

    /**
     * 路径创建
     *
     * @param dest
     */
    private static void createPath(String dest) {
        File destDir = null;
        if (dest.endsWith(File.separator)) {
            destDir = new File(dest);//给出的是路径时
        } else {
            destDir = new File(dest.substring(0, dest.lastIndexOf(File.separator)));
        }
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
    }


}
