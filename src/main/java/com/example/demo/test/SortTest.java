package com.example.demo.test;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName SortTest.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年10月19日 17:23:00
 */
public class SortTest {

    public static void main(String[] args) {
        List<UserInfo> list = Lists.newArrayList();
        list.add(new UserInfo(1, "张三", 18));
        list.add(new UserInfo(2, "李四", 17));
        list.add(new UserInfo(3, "王五", 19));
        list.add(new UserInfo(4, "猪柳", 16));
        Collections.sort(list, new UserInfo());
        System.out.println(list);

    }

}

@Data
@AllArgsConstructor
@NoArgsConstructor
class UserInfo implements Comparator<UserInfo> {

    private Integer uid;
    private String name;
    private Integer age;

    @Override
    public int compare(UserInfo o1, UserInfo o2) {
        if (o1.age > o2.age)
            return 1;
        else
            return -1;
    }
}