package com.example.demo.test;

import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 * @ClassName SingletonEager.java
 * @Description
 * @Author Vince
 * @CreateTime 2022年02月16日 11:24:00
 */
public class SingletonEager {

    public static void main(String[] args) {
        JFrame jf = new JFrame("饿汉单例模式测试");
        jf.setLayout(new GridLayout(1, 2));
        Container contentPane = jf.getContentPane();
        Bajie obj1 = Bajie.getInstance();
        contentPane.add(obj1);
        Bajie obj2 = Bajie.getInstance();
         contentPane.add(obj2);
        if (obj1 == obj2) {
            System.out.println("他们是同一人！");
        } else {
            System.out.println("他们不是同一人！");
        }
        jf.pack();
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

class Bajie extends JPanel {
    private static Bajie instance = new Bajie();

    private Bajie()  {

        try {
            byte[] bytes = FileUtils.readFileToByteArray(new File("E:\\素材\\Bajie.jpg"));
            JLabel l1 = new JLabel(new ImageIcon(bytes));
            this.add(l1);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Bajie getInstance() {
        return instance;
    }
}



