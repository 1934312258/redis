package com.kevin.reidstest.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @创建人 zhaowenjian
 * @创建时间 2020/12/9
 * @描述
 */
public class templateString {
    @Autowired
    static RedisTemplate template;

    @Autowired
    static ValueOperations<String, Object> ops;

    public static void main(String[] args) {

        ops.set("kevin", "4326534y");
        String Kevin = (String) ops.get("kevin");

        ops.set("kevin", "timeout", Duration.ofSeconds(5));
        Kevin = (String) ops.get("kevin");
        ops.set("kevin", "timeout", Duration.ofMillis(5000));

        // 没有过期时间，则相当于setNx
        boolean result = ops.setIfAbsent("kevin", "setNx");
        result = ops.setIfAbsent("kevin", "lock", 5, TimeUnit.SECONDS);
        result = ops.setIfAbsent("kevin", "lock1", Duration.ofSeconds(5));

        // 验证setIfAbsent与setIfPresent的区别
        result = ops.setIfPresent("kevin1", "unknown");
        result = ops.setIfPresent("kevin", "unknown1", 5, TimeUnit.SECONDS);
        result = ops.setIfPresent("kevin", "unknown2", Duration.ofSeconds(5));

        Map<String, Object> map = new HashMap<>();
        map.put("map", "map");
        map.put("map1", "map1");
        ops.multiSet(map);

        map.put("map2", "map2");
        result = (boolean) ops.multiSetIfAbsent(map);

        Kevin = (String) ops.getAndSet("kevin", "kevin");
        List<String> list = new ArrayList<>();
        list.add("kevin");
        list.add("kevin1");
        List<Object> stringList = ops.multiGet(list);

        Long num = ops.increment("num");
        num = ops.increment("num", 10);
        Double du = ops.increment("num", 1.234);

        num = ops.decrement("num", 6);

        int number = ops.append("kevin", "is a good man");

        // 截取存储字符串
        ops.set("subString", "substring", 3);
        String substring = ops.get("subString", 2, 5);
        long size = ops.size("kevin");

        // bigMap 命令  ，Kevin 最外层key，1000，偏移量  ，true
        result = ops.setBit("kevin", 1000, true);
        result = ops.getBit("kevin", 1000);
        int tongji = (int) template.execute((RedisCallback<Long>) con -> con.bitCount("kevin".getBytes()));

        //bitfield w get i3 2    i表示有符号数，u表示无符号数，3表示截取三位，2表示从第三位开始截取


        // 为了促进用户连续签到，有时会给连续签到的用户一些额外的奖励，这时就需要使用bitfield来判断用户连续签到多少天了。
        ops.bitField("kevin", BitFieldSubCommands.create().get(BitFieldSubCommands.BitFieldType.unsigned(10)).valueAt(10));


    }
}
