package com.example.demo.runnable;

/**
 * @ClassName GZipFiles.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年10月15日 13:19:00
 */

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GZipFiles {

    private final static int THREAD_COUNT = 4;
    private static ExecutorService service = Executors.newFixedThreadPool(THREAD_COUNT);

    public static void GZip(File fileArgs) {

        GZipRunnable gZipRunnable = new GZipRunnable(fileArgs);
        service.submit(gZipRunnable);

    }

    public static void shutdown() {
        service.shutdown();
    }
}
