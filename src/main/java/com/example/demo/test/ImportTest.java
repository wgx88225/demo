package com.example.demo.test;

import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @ClassName ImportTest.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年07月12日 09:38:00
 */

public class ImportTest {

    private static final Logger logger = LogManager.getLogger();
    public static void main(String[] args) {

        System.setProperty("com.sun.jndi.rmi.object.trustURLCodebase","true");
        String name = "${java:version}";
        logger.info("name->====={}=====", name);
    }

}
