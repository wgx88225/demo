package com.example.demo.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.HashSet;

/**
 * @ClassName Test002.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年10月27日 11:33:00
 */
public class Test002 {

    public static void main(String[] args) throws Exception{
        HashSet<Object> set = Sets.newHashSet();
        set.add(new UserIn(1,"a","北京"));
        set.add(new UserIn(1,"b","北京"));
        set.add(new UserIn(2,"b","北京"));
        System.out.println(JSON.toJSONString(set));

    }
}

@EqualsAndHashCode(of = "uid")
@Data
@NoArgsConstructor
@AllArgsConstructor
class UserIn{
    private Integer uid;
    private String name;
    @JSONField(name="AD")
    private String addr;
}
