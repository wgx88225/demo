package com.example.demo.cmd;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * @ClassName TestCmd.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年11月18日 15:23:00
 */
public class TestCmd {

    public static void main(String[] args) {

        String cmd = "mysqldump -hlocalhost  -uroot  -p1234   db_smart_learning -r /root/123.sql";

        String line = null;
        StringBuilder sb = new StringBuilder();
        Runtime runtime = Runtime.getRuntime(); //得到本程序
        try {
            Process process = runtime.exec(cmd);  //该实例可用来控制进程并获得相关信息
            //获取进程输出流
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.forName("utf-8")));
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line + "\n");
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
