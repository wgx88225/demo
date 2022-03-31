package com.example.demo.file;

import com.sun.javafx.binding.StringFormatter;
import javafx.beans.binding.StringExpression;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.util.FileCopyUtils;

import java.io.File;

/**
 * @ClassName TestFile.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年11月24日 09:46:00
 */
public class TestFile {

    public static void main(String[] args) {
        // D:\ziptest\1.txt
//        File file = new File("D:\\ziptest\\1.txt");
//        String baseName = FilenameUtils.getBaseName("D:\\ziptest\\1.txt");
//        System.out.println(baseName);
//        FileRunnable runnable = new FileRunnable();
//        new Thread(runnable).start();
//        new Thread(runnable).start();
        String cmd = "zip -q -r %s %s";

//        String format = String.format("zip -q -r %s %s", "1", "2");
//        System.out.println(format.toString());
        String s= "marketing_activities:1:20";

        String[] split = s.split(":");
        System.out.println(split[2]);

    }
}

class FileRunnable implements Runnable {


     File  file = new File("D:\\ziptest\\1.txt");

    @SneakyThrows
    @Override
    public void run() {

        System.out.println("线程名-->" + Thread.currentThread().getName());
        File temp = new File("D:\\ziptest\\temp");
        File file2 = new File(temp, file.getName());
        if(file2.exists()){
            return;
        }
        FileUtils.copyFileToDirectory(this.file, temp);
    }
}
