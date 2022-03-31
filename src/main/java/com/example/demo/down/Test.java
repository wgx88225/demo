package com.example.demo.down;

import java.util.Date;

/**
 * @ClassName Test.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年11月02日 18:46:00
 */
public class Test {
    public static void main(String[] args) {
        Date startDate = new Date();

        DownloadFilePool pool = new DownloadFilePool("http://fs.w.kugou.com/201809152325/5cbbb70b45431a17cad6ddd6d5342ef5/G108/M03/0C/01/rA0DAFk_VuiALN9DADmXB0zHYTA058.mp3", null, 100);

        pool.getFile();

        long old = 0;

        long now = 0;

        while (Util.sum >= Util.start) {
            now = Util.start - old;

            old = Util.start;

            if (Util.sum == Util.start) {
                long t = new Date().getTime() - startDate.getTime();

                double speed = ((double) Util.sum / (t / 1000.0)) / 1024.0 / 1024.0;

                System.out.println("下载完成，用时：" + t / 1000.0 + " s 平均网速：" + speed + " M/s");

                break;

            }

            System.out.println("网速：" + ((double) (now / 0.5)) / 1024.0 / 1024.0 + " M/s,已完成：" + (Util.start / (double) Util.sum) * 100 + "%");

            try {
                Thread.sleep(500);

            } catch (InterruptedException e) {
                e.printStackTrace();

            }

        }

    }

}
