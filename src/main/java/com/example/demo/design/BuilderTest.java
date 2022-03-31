package com.example.demo.design;

/**
 * @ClassName BuilderTest.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年11月29日 15:49:00
 */
public class BuilderTest {

    public static void main(String[] args) {
        Director director = new Director();
        Robot robot = director.createRobotByDirecotr(new DanceRobotBuilder ());
        System.out.println(robot);
    }
}
