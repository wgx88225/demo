package com.example.demo.down;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;

/**
 * @ClassName DownloadFileRang.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年11月02日 18:44:00
 */
public class DownloadFileRang implements Runnable {
    // 文件开始位置

    private long start;

// 文件结束位置

    private long end;

// url地址

    private String urlLocation;

// 文件存储位置

    private String filePath;

    public DownloadFileRang(long start, long end, String urlLocation, String filePath) {
        super();

        this.start = start;

        this.end = end;

        this.urlLocation = urlLocation;

        this.filePath = filePath;

    }

    @Override

    public void run() {
        try {
// 获取连接

            HttpURLConnection conn = Util.getHttpConnection(urlLocation);

// 设置获取资源范围

            conn.setRequestProperty("Range", "bytes=" + start + "-" + end);

            File file = new File(filePath);

            RandomAccessFile out = null;

            if (file != null) {
                out = new RandomAccessFile(file, "rw");

            }

            out.seek(start);

// 获取网络连接的 输入流

            InputStream is = conn.getInputStream();

            byte[] data = new byte[1024];

            int len = 0;

            while ((len = is.read(data)) != -1) {
                out.write(data, 0, len);

                synchronized (Util.class) {
                    Util.start += len;

                }

            }

// 关闭连接

            out.close();

            is.close();

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

}
