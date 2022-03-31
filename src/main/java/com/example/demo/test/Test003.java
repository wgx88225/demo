package com.example.demo.test;

import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName Test002.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年10月27日 11:33:00
 */
public class Test003 {

    public static void main(String[] args) throws Exception {

        String code = "SR123n\nSR124\nSR125\nSR126\nSR127\nSR128\nSR129\nSR130\nSR131\nSR132\nSR133\nSR134\nSR135\nSR136\nSR137\nSR138\nSR139\nSR140\nSR141\nSR142\n";
        String[] sns = StringUtils.split(code, "n");
        for (String sn : sns) {
            System.out.println(sn);
        }
    }
}


