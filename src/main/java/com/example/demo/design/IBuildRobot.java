package com.example.demo.design;

/**
 * @ClassName BuildService.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年11月29日 15:46:00
 */
public interface IBuildRobot {

    void buildHead();

    void buildBody();

    void buildHand();

    void buildFoot();

    Robot createRobot();

    default void print() {
        System.out.println("打印清单");
    }

}
