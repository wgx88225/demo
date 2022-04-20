package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.example.demo.bo.ToEmail;
import com.example.demo.entity.User;
import com.example.demo.job.ExamJob;
import com.example.demo.pdf.PdfProblem;
import com.example.demo.redis.RedisLockLuaScript;
import com.example.demo.service.*;
import com.google.common.collect.Lists;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private TopicManageService topicManageService;
    @Autowired
    private PdfProblem pdfProblem;
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Autowired
    private RedisLockLuaScript redisLockLuaScript;
    @Autowired
    private SeckillService seckillService;
    @Autowired
    private ExamTopicService examTopicService;
    @Autowired
    private ToEmailService toEmailService;
    @Autowired
    private ExamJob examJob;

    // 发邮件
    @Test
    public void sendExamJob() {
        examJob.sendMail();
    }
    // 发邮件
    @Test
    public void sendTextMail() {
        ToEmail toEmail = new ToEmail();
        toEmail.setTos(new String[]{"442872071@qq.com"});
        toEmail.setSubject("考试来了");
        toEmail.setContent("邮件内容");
        try {
            toEmailService.commonEmail(toEmail);//发送
            System.out.println("测试邮件已发送。");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("发送邮件时发生异常了！");
        }


    }

    @Test
    void testPdfExamTopic() throws Exception {
        examTopicService.genPdf("D:\\高项案例背诵_", 1, 10);

    }

    @Test
    void testSeckillService() throws Exception {
        List<String> qiangdan = seckillService.qiangdan();
        System.out.println(qiangdan);
    }

    @Test
    void testRedisLockLuaScript() throws Exception {
        //测试代码逻辑，放在测试包下运行   用UUID去获得锁，然后用uuid去删除这个锁
        String key = "locking";
        String requestId = UUID.randomUUID().toString();
        boolean ret = redisLockLuaScript.getLock(key, requestId);
        boolean ret2 = redisLockLuaScript.getLock(key, requestId);
        if (ret2) {
            System.out.println("ret2成功获取到锁: 执行业务逻辑 时间较长的话，可能会失败，超时等");
        }
        if (ret) {
            System.out.println("成功获取到锁: 执行业务逻辑 时间较长的话，可能会失败，超时等");
            Thread.sleep(6000);
            redisLockLuaScript.releaseLock(key, requestId);
            System.out.println("释放锁----" + requestId);
        } else {
            System.out.println("获取锁失败----");
        }


    }


    @Test
    void testPdfProblem() throws Exception {
        pdfProblem.genPdf("D:\\PMP错题集_", 2, 10);
//        pdfProblem.genPdf("D:\\软考架构师错题集_",2);
    }

    @Test
    void testTopicManageService() throws Exception {

        System.out.println(JSON.toJSONString(topicManageService.list()));
    }

    @Test
    void testUserService() throws Exception {
        User user = new User();
//        User user = userService.getById(1L);
        user.setUserId(1464067033906864129L);
        user.setUsername("李斯");
//        user.setPassword("123");
        userService.saveOrUpdate(user);
        System.out.println(user);
    }

    @Test
    void contextLoads() throws Exception {
        long start = System.currentTimeMillis();
//        testZip(); //耗时
//        testZip2(); // 408M 耗时13931
//        testSingle(); // 7.74G 耗时261877
        test001();
        System.out.println("耗时" + (System.currentTimeMillis() - start));

    }

    public void test001() throws Exception {
//        String src = "D:\\uploadPath\\download\\resource-zip1\\0f6bff19-0b63-4050-bdbf-a5aaa8b5b98f.zip";
//        String dest = "D:\\ziptest\\a5aaa8b5b98f.zip";
//        FileUtils.copyFile(new File(src),new File(dest));
        String dest = "D:\\ziptest";
        String src = "E:\\迅雷下载\\破茧01.mp4";
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            try {
                File file1 = new File(src);
                File file = new File(dest, FilenameUtils.getName(file1.getName()));
                System.out.println("file==>" + file);
                if (file.exists()) return;
                FileUtils.copyFileToDirectory(new File(src), new File(dest));
                System.out.println("线程：" + Thread.currentThread().getName());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }, threadPoolTaskExecutor);

        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
            try {
                File file1 = new File(src);
                File file = new File(dest, FilenameUtils.getName(file1.getName()));
                System.out.println("file==>" + file);
                if (file.exists()) return;
                FileUtils.copyFileToDirectory(new File(src), new File(dest));
                System.out.println("线程：" + Thread.currentThread().getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, threadPoolTaskExecutor);
        CompletableFuture<Void> combindFuture = CompletableFuture.allOf(future1, future2);
        combindFuture.get();
    }

    public void testSingle() throws IOException, ZipException {
        String dest = "D:\\ziptest\\9529.zip";
        File file = new File(dest);
        if (file.exists()) {
            file.delete();
        }
        ZipParameters parameters = new ZipParameters();
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
        try {
            ArrayList<File> filesToAdd = new ArrayList<File>();
            ZipFile zipFile = new ZipFile(dest);
            ArrayList<File> add1 = new ArrayList<File>();
            add1.add(new File("E:\\迅雷下载\\破茧01.mp4"));
            add1.add(new File("E:\\迅雷下载\\破茧02.mp4"));
//            add1.add(new File("E:\\迅雷下载\\破茧03.mp4"));
//            add1.add(new File("E:\\迅雷下载\\破茧04.mp4"));
//            add1.add(new File("E:\\迅雷下载\\破茧05.mp4"));
//            add1.add(new File("D:\\uploadPath\\download\\resource-zip\\广州2服务器_20211117111429100657.zip"));
//            add1.add(new File("D:\\uploadPath\\download\\resource-zip\\广州2服务器_20211117110935152841.zip"));
            filesToAdd.addAll(add1);
            zipFile.addFiles(filesToAdd, parameters);
        } catch (ZipException e) {
            e.printStackTrace();
        }
        System.out.println("成功");
    }

    public void testZip() throws IOException, ZipException, ExecutionException, InterruptedException {
        String dest = "D:\\ziptest\\9529.zip";
        File file = new File(dest);
        if (file.exists()) {
            file.delete();
        }
        ZipParameters parameters = new ZipParameters();
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        // set compression method to deflate compression
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

//        filesToAdd.add(new File("E:\\迅雷下载\\破茧02.mp4"));

        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            try {
                ArrayList<File> filesToAdd = new ArrayList<File>();
                ZipFile zipFile = new ZipFile(dest);
                ArrayList<File> add1 = new ArrayList<File>();
//                add1.add(new File("E:\\迅雷下载\\破茧01.mp4"));
//                add1.add(new File("E:\\迅雷下载\\破茧02.mp4"));
                add1.add(new File("D:\\uploadPath\\download\\resource-zip\\广州2服务器_20211117111429100657.zip"));
//                add1.add(new File("D:\\uploadPath\\download\\resource-zip\\广州2服务器_20211117110935152841.zip"));
                filesToAdd.addAll(add1);
                zipFile.addFiles(filesToAdd, parameters);
            } catch (ZipException e) {
                e.printStackTrace();
            }
        }, threadPoolTaskExecutor);
        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
            try {
                ArrayList<File> filesToAdd = new ArrayList<File>();
                ZipFile zipFile = new ZipFile(dest);
                ArrayList<File> add1 = new ArrayList<File>();
//                add1.add(new File("E:\\迅雷下载\\破茧03.mp4"));
//                add1.add(new File("E:\\迅雷下载\\破茧04.mp4"));
//                add1.add(new File("E:\\迅雷下载\\破茧05.mp4"));
//                add1.add(new File("D:\\uploadPath\\download\\resource-zip\\广州2服务器_20211117111429100657.zip"));
                add1.add(new File("D:\\uploadPath\\download\\resource-zip\\广州2服务器_20211117110935152841.zip"));
                filesToAdd.addAll(add1);
                zipFile.addFiles(filesToAdd, parameters);
            } catch (ZipException e) {
                e.printStackTrace();
            }
        }, threadPoolTaskExecutor);
        CompletableFuture<Void> combindFuture = CompletableFuture.allOf(future1, future2);
        combindFuture.get();
        System.out.println("成功");
    }

    public void testZip2() throws IOException, ZipException {
        String dest = "D:\\ziptest\\9529.zip";
        File file = new File(dest);
        if (file.exists()) {
            file.delete();
        }
        ZipParameters par = new ZipParameters();
        par.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        par.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
        ZipFile zipfile = new ZipFile(dest);
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            List<String> fileUrlList = Lists.newArrayList("E:\\迅雷下载\\破茧01.mp4");

            try {
//                CompressUtils.zip(fileUrlList, dest, null);


                List<File> collect = fileUrlList.stream().map(item -> new File(item)).collect(Collectors.toList());
                long sum = collect.stream().mapToLong(item -> item.length()).sum();
                long g = 1 << 30;
                double size = new BigDecimal((double) sum / g).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                System.out.println("文件大小：" + size + " G");
                ArrayList<File> temp = new ArrayList<File>();
                temp.addAll(collect);
                zipfile.addFiles(temp, par);
                System.out.println("线程：" + Thread.currentThread().getName());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }, threadPoolTaskExecutor);

        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
            List<String> fileUrlList = Lists.newArrayList("E:\\迅雷下载\\破茧02.mp4");

            try {
//                CompressUtils.zip(fileUrlList, dest, null);
                List<File> collect = fileUrlList.stream().map(item -> new File(item)).collect(Collectors.toList());
                long sum = collect.stream().mapToLong(item -> item.length()).sum();
                long g = 1 << 30;
                double size = new BigDecimal((double) sum / g).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                System.out.println("文件大小：" + size + " G");
                ArrayList<File> temp = new ArrayList<File>();
                temp.addAll(collect);
                zipfile.addFiles(temp, par);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, threadPoolTaskExecutor);
//
//        CompletableFuture<Void> future3 = CompletableFuture.runAsync(() -> {
//            List<String> fileUrlList = Lists.newArrayList("E:\\迅雷下载\\破茧03.mp4");
//            try {
//                CompressUtils.zip(fileUrlList, dest, null);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }, threadPoolTaskExecutor);
//        CompletableFuture<Void> future4 = CompletableFuture.runAsync(() -> {
//            List<String> fileUrlList = Lists.newArrayList("E:\\迅雷下载\\破茧04.mp4", "E:\\迅雷下载\\破茧05.mp4");
//            try {
//                Thread.sleep(100);
//                CompressUtils.zip(fileUrlList, dest, null);
//            } catch (IOException | InterruptedException e) {
//                e.printStackTrace();
//            }
//        }, threadPoolTaskExecutor);

//        CompletableFuture<Void> combindFuture = CompletableFuture.allOf(future1, future2, future3, future4);
        CompletableFuture<Void> combindFuture = CompletableFuture.allOf(future1, future2);
        try {
            combindFuture.get();
            System.out.println("结束>>>>>>>");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

}
