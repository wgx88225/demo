package com.example.demo.design;

import org.springframework.stereotype.Component;

/**
 * @ClassName Director.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年11月29日 15:49:00
 */
@Component
public class Director {

    public Robot createRobotByDirecotr(IBuildRobot ibr) {
        ibr.buildBody();
        ibr.buildFoot();
        ibr.buildHand();
        ibr.buildHead();
        ibr.print();
        return ibr.createRobot();
    }
}
