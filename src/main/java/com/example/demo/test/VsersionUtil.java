package com.example.demo.test;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @ClassName VsersionUtil.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年10月09日 11:57:00
 */
@Slf4j
public class VsersionUtil {

    public static String upgradeVersion(String version) {
        String upgradeVsersion;
        if (StringUtils.isEmpty(version)) {
            version = "V1.0.0";
        }
        version = StringUtils.removeStart(version, "V");
        //将版本号拆解成整数数组
        List<String> strArr = Splitter.on(".").splitToList(version);
        Integer[] ints = new Integer[strArr.size()];
        for (int i = 0; i < strArr.size(); i++) {
            ints[i] = Integer.parseInt(strArr.get(i));
        }
        //递归调用
        upgradeVersion(ints, ints.length - 1);

        //数组转字符串
        upgradeVsersion = Joiner.on(".").join(ints);

        return "V" + upgradeVsersion;
    }

    private static void upgradeVersion(Integer[] ints, int index) {
        if (index == 0) {
            ints[0] = ints[0] + 1;
        } else {
            int value = ints[index] + 1;
            if (value < 10) {
                ints[index] = value;
            } else {
                ints[index] = 0;
                upgradeVersion(ints, index - 1);
            }
        }
    }

    public static void main(String[] args) {
//        log.info("version:1.0.0.0|" + upgradeVersion("0.0.20"));
//        log.info("version:1.0.0.9|" + upgradeVersion("1.0.9"));
//        log.info("version:1.0.1.9|" + upgradeVersion("1.1.9"));
//        log.info("version:1.0.9.9|" + upgradeVersion("1.9.9"));
//        log.info("version:1.9.9.9|" + upgradeVersion("1.9.9"));
        System.out.println(str(1001));
    }

    private static String str(int i) {
        String str = i + "";

        StringBuffer buf = new StringBuffer();
        int length = str.length();

        if (length < 4) {
            str = String.format("%0" + (4 - length) + "d", i);
        }
        char[] chars = str.toCharArray();
        for (char aChar : chars) {
            buf.append(aChar);
            buf.append(".");
        }

        return StringUtils.removeEnd(buf.toString(), ".");
    }
}