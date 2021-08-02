package com.example.demo;

import com.example.demo.utils.JacksonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.gson.GsonProperties;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.util.ConcurrentReferenceHashMap;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


public class DemoApplicationTests2 {
    @Test
    public void test001() throws Exception {

    }


    @Test
    public void contextLoads() throws JsonProcessingException {

        User user1 = User.builder().id(1).name("張三").build();
        User user2 = User.builder().id(1).name("張三").build();
        List<User> list = Lists.list(user1,user2);


        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(list);
//        System.out.println(json);

//        List<User> list2 = om.readValue(json, list.getClass());

//        System.out.println(list2);



        List<String> names = JacksonUtil.parseStringList(json, "name");
//        System.out.println(names);
        System.out.println(tableSizeFor(0));
        System.out.println(tableSizeFor(1));
        System.out.println(tableSizeFor(2));
        System.out.println(tableSizeFor(3));
        System.out.println(tableSizeFor(4));
        System.out.println(tableSizeFor(5));
        System.out.println(tableSizeFor(6));
        System.out.println(tableSizeFor(7));
        System.out.println(tableSizeFor(8));
        System.out.println(tableSizeFor(9));

    }
    private  final int MAXIMUM_CAPACITY = 1 << 30;
    private  final int tableSizeFor(int c) {
        int n = c - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class User {
    private Integer id;
    private String name;
    private String age="";
}