package com.example.demo.service.impl;

import com.example.demo.redis.RedisLockLuaScript;
import com.example.demo.service.SeckillService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @ClassName SeckillServiceImpl.java
 * @Description
 * @Author Vince
 * @CreateTime 2022年01月11日 15:04:00
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SeckillServiceImpl implements SeckillService {

    @NonNull
    RedisTemplate<String, String> redisTemplate;

    @NonNull
    RedisLockLuaScript redisLockLuaScript;
    //总库存
    private long nKuCuen = 0;
    //商品key名字
    private String shangpingKey = "computer_key";
    // 库存k
    private String KuCuenListKey = "KuCuenList_Key";
    //获取锁的超时时间 秒
    private int timeout = 30 * 1000;


    @Override
    public List<String> qiangdan() {

        //抢到商品的用户
        List<String> shopUsers = new ArrayList<>();

        //构造很多用户
        List<String> users = new ArrayList<>();
        IntStream.range(0, 100000).parallel().forEach(b -> {
            users.add("神牛-" + b);
        });
        redisTemplate.delete(KuCuenListKey);
        for (int i = 0; i < 10; i++) {
            String uuid = UUID.randomUUID().toString();
            redisTemplate.boundListOps(KuCuenListKey).rightPush(uuid);
        }

        //初始化库存


        //模拟开抢
        users.parallelStream().forEach(b -> {
            String shopUser = qiang(b);
            if (!StringUtils.isEmpty(shopUser)) {
                shopUsers.add(shopUser);
            }
        });

        return shopUsers;
    }

    /**
     * 模拟抢单动作
     *
     * @param b
     * @return
     */
    private String qiang(String b) {
        //用户开抢时间
        long startTime = System.currentTimeMillis();

        //未抢到的情况下，30秒内继续获取锁
        while ((startTime + timeout) >= System.currentTimeMillis()) {
            //商品是否剩余
            long size = redisTemplate.boundListOps(KuCuenListKey).size();
            if (size <= 0) {
                break;
            }

//            if (redisLockLuaScript.getLock(shangpingKey, "s")) {
            if (redisTemplate.opsForValue().setIfAbsent(shangpingKey, "a")) {
                //用户b拿到锁
                log.info("用户{}拿到锁...", b);
                try {
                    //商品是否剩余
                    if (size <= 0) {
                        break;
                    }

                    //模拟生成订单耗时操作，方便查看：神牛-50 多次获取锁记录
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //抢购成功，商品递减，记录用户
//                    nKuCuen = nKuCuen - 1;
                    String no = redisTemplate.boundListOps(KuCuenListKey).leftPop();
                    //抢单成功跳出
                    log.info("用户{}抢单成功跳出...所剩库存：{}", b, size);

                    return b + "抢单成功 商品号 ["+no+"]，所剩库存：" + size;
                } finally {
                    log.info("用户{}释放锁...", b);
                    //释放锁
//                    redisLockLuaScript.releaseLock(shangpingKey, b);
                    redisTemplate.delete(shangpingKey);

                }
            } else {
                //用户b没拿到锁，在超时范围内继续请求锁，不需要处理
//                if (b.equals("神牛-50") || b.equals("神牛-69")) {
//                    logger.info("用户{}等待获取锁...", b);
//                }
            }
        }
        return "";
    }
}
