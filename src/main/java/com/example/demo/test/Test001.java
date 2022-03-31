package com.example.demo.test;


import lombok.Data;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @ClassName Test001.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年08月09日 19:00:00
 */
public class Test001 {

    public static void main(String[] args) {


//        int num1 = 3;
//        int num2 = 1;
//        NumberFormat numberFormat = NumberFormat.getInstance();
//        // 设置精确到小数点后2位
//        numberFormat.setMaximumFractionDigits(2);
//        String result = numberFormat.format((float) num2 / (float) num1 * 100);
//        System.out.println("num1和num2的百分比为:" + result + "%");

//        double c = b;
//        System.out.println(c / 100);
//        float num= (float)2/3 *100;
//        DecimalFormat df = new DecimalFormat("0.00");
//        //格式化小数
//         String s = df.format(num);
//        // 返回的是String类型
//        System.out.println(s);
//        System.out.println(System.currentTimeMillis());
//        File file = new File("D:\\uploadPath\\upload\\c7615448b4434a3a97a4e897df113442.png");
//        file.delete();
//        System.out.println(file.getName());
//        String str = "1";
//
//        String collect = Arrays.stream(StringUtils.split(str, ","))
//                .map(el -> Integer.parseInt(el)).map(el -> (char) (el + 64)).map(el -> el + "").collect(Collectors.joining());
//
//        System.out.println(collect);
//        List<Integer> list = new ArrayList<>();
//        for (int i = 1; i < 38; i++) {
//            list.add(i);
//        }
//
//        List<Integer> randomList = getRandomList(list, 10);
//        System.out.println(randomList);
//        long l  = 17911;
//        BigDecimal price = new BigDecimal(l/1024);
//        String p = price.toString();
//        System.out.println(p);
//        String str = "A010102";
//        String a = "A0101";
//        String[] split = StringUtils.split(str, a);
//        for (String s : split) {
//            System.out.println(s);
//        }
//        BigDecimal totalScore = new BigDecimal(100);
//        BigDecimal multiply = new BigDecimal(70);
//
//        int i = multiply.compareTo(totalScore);
//
//        Assert.isTrue(multiply.compareTo(totalScore) == 0, "大了");
//        System.out.println(i);
//        System.out.println(StringUtils.uncapitalize("AcccA"));

//        String s = null;
//        User u = null;
//        User u1 = Optional.ofNullable(u).orElse(new User());


//        System.out.println(getId());
//        System.out.println(autoGenericCode("1", 2));
//        List<String> list = new ArrayList<>();
//        list.add("a");
//        list.add("b");
//        list.add("c");
//        String join = String.join(",", list);
//        test(join);
//        String str = "1_admin_a";
//
//        String s = StringUtils.substringAfter(str, "_");
//        System.out.println(s);
//        Assert.isTrue(1 < 0, "ss");
    }

    /**
     * @Description list 随机取数据
     * @params list    list集合
     * num     随机取多少条
     **/
    public static List<Integer> getRandomList(List<Integer> list, int num) {
        List olist = new ArrayList<>();
        if (list.size() <= num) {
            return list;
        } else {
            Random random = new Random();
            for (int i = 0; i < num; i++) {
                int intRandom = random.nextInt(list.size() - 1);
                olist.add(list.get(intRandom));
                list.remove(list.get(intRandom));
            }
            return olist;
        }
    }

    public static void test(String... antPatterns) {

        for (String antPattern : antPatterns) {
            System.out.println(antPattern);
        }
    }

    private static String autoGenericCode(String code, int num) {
        String result = "";
        // 保留num的位数
        // 0 代表前面补充0
        // num 代表长度为4
        // d 代表参数为正数型
        result = String.format("%0" + num + "d", Integer.parseInt(code));

        return result;
    }


    public static String getId() {
        String id = "";
        //获取当前时间戳
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        String temp = sf.format(new Date());
        //获取6位随机数
        int random = (int) ((Math.random() + 1) * 100000);
        id = temp + random;
        return id;
    }

}

@Data
class User {
    private Long id;
    private String name;
}