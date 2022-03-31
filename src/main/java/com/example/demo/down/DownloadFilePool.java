package com.example.demo.down;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName DownloadFilePool.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年11月02日 18:45:00
 */
public class DownloadFilePool {
    // 网络资源路径

    private String urlLocation;

// 存储路径

    private String filePath;

// 多少个线程

    private int poolLength;

    public DownloadFilePool(String urlLocation, String filePath, int poolLength) {
        super();

// 如果 保存路径为空则默认存在 D盘，文件名跟下载名相同

        if (filePath == null) {
            String fileName = urlLocation.substring(urlLocation.lastIndexOf("/") + 1);

            filePath = "D:/" + fileName;

        }

        this.urlLocation = urlLocation;

        this.filePath = filePath;

        this.poolLength = poolLength;

    }

    public void getFile() {
        try {
// 获取文件长度

            long fileLength = Util.getHttpConnection(urlLocation).getContentLengthLong();

            Util.sum = fileLength;

            ExecutorService pool = Executors.newCachedThreadPool();

// 获取每片大小

            long slice = fileLength / poolLength;

            for (int i = 0; i < poolLength; i++) {
                long start = i * slice;

                long end = (i + 1) * slice - 1;

                if (i == poolLength - 1) {
                    start = i * slice;

                    end = fileLength;

                }

                System.out.println(start + "---" + end);

// 创建下载类

                DownloadFileRang downloadFileRang = new DownloadFileRang(start, end, urlLocation, filePath);

// 执行线程

                pool.execute(downloadFileRang);

            }

// 关闭线程池

            pool.shutdown();

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

}
