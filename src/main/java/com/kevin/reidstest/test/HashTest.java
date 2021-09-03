package com.kevin.reidstest.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 *
 * </p>
 *
 * @author zhaowenjian
 * @since 2021/6/25 15:14
 */
@Component
public class HashTest {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    HashOperations<String, String, Object> objectHashOperations;

    public void hashTest(){
        HashOperations hash = redisTemplate.opsForHash();
        boolean result;
        String value;
        List values;
        String key = "kevin";
        String name = "name";
        hash.put("kevin","name",name);
        Map map = new HashMap();
        map.put("age",18);
        map.put(key,"sfdjgh");
        hash.putAll("kevin",map);
        result = hash.putIfAbsent(key,"face","handsome");
        int age = (int) hash.get(key,"age");
        List list = Arrays.asList("name","face");
        values = hash.multiGet(key,list);
        RedisOperations redisOperations = hash.getOperations();
        // 获取所有的值
        Map all = hash.entries(key);
        result = hash.hasKey(key,"name");
        Set set = hash.keys(key);
        Long now = hash.increment(key,"age",10L);
        Double dou = hash.increment(key,"age",1.0);
        // 获取的长度比实际长度多2
        now = hash.lengthOfValue(key,"name");
        now = hash.lengthOfValue(key,"face");
        int leng = hash.get(key,"face").toString().length();
        now = hash.delete(key,key);
        Cursor<Map.Entry<String, String>> cursor = hash.scan(key,ScanOptions.scanOptions().match("*").count(1).build());
        while(cursor.hasNext()){
            // next方法会移动游标，需要注意
            Map.Entry entry = cursor.next();
            Object scanValue = entry.getValue();
            scanValue.toString();
            String scanKey = (String) entry.getKey();
        }
        // 关闭游标
        try {
            cursor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        now = hash.size(key);
    }

}
