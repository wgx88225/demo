package com.example.demo.down;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @ClassName Util.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年11月02日 18:42:00
 */
public class Util {
    // 记录读取了多少，一共读取了多少

    public static long start;
// 记录文件总大小
    public static long sum;

    /**
     * @throws
     * @Title: getHttpConnection
     * @Description: 获取 url 连接
     * @param: @param urlLocation
     * @param: @return HttpURLConnection实例化对象
     * @param: @throws IOException
     * @return: HttpURLConnection
     */

    public static HttpURLConnection getHttpConnection(String urlLocation) throws IOException {
        URL url = new URL(urlLocation);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setConnectTimeout(5000);

        conn.setRequestMethod("GET");

        return conn;

    }

}


