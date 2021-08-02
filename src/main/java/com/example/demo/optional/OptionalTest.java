package com.example.demo.optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.util.FileUtil;
import org.springframework.util.FileSystemUtils;

import java.util.Optional;

/**
 * @ClassName OptionalTest.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年07月27日 14:43:00
 */
public class OptionalTest {

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    private static class Basket {
        private Apple apple;
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    private static class Apple {
        private String color;

        private Double weight;
    }

    public static void main(String[] args) {

      Byte c = 0;
        System.out.println(c.byteValue());
        Apple apple = new Apple("red", 20.00);
//        Apple apple = null;
        Basket basket = new Basket(apple);
        try {

            Optional<Apple> optionalApple2 = Optional.ofNullable(apple);

            System.out.println(optionalApple2.isPresent());
            Apple apple1 = optionalApple2.orElse(null);
            Double weight1 = optionalApple2.map(Apple::getWeight).get();
            Double weight2 = optionalApple2.map(Apple::getWeight).orElse(00.00);
            System.out.println(weight1);
            System.out.println(weight2);
            // 从 Basket 中 获取 Apple 的 weight
            Double appleWeight1 = Optional.ofNullable(basket)
                    .map(Basket::getApple)
                    .map(Apple::getWeight)
                    .orElse(00.00);
            System.out.println(appleWeight1);

            // 使用 orElseThrow
            basket.setApple(null);
            Double appleWeight2 = Optional.ofNullable(basket)
                    .map(Basket::getApple)
                    .map(Apple::getWeight)
                    .orElseThrow(NullPointerException::new);
            System.out.println(appleWeight2);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
