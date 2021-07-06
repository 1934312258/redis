package com.kevin.reidstest.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *
 * </p>
 *
 * @author zhaowenjian
 * @since 2021/6/25 16:47
 */
@Component
public class ListTest {
    @Autowired
    ListOperations list;

    String key = "kevinList";
    String key1 = "kevinList1";

    public void ListTest(){
        // 获取的是template
        RedisOperations redisOperations = list.getOperations();
        Long result = list.leftPush(key,10);
        // set有问题不要用
//        list.set(key,1L,100);
        result = list.leftPush(key,1);
        result = list.rightPush(key,100000);
        result = list.leftPushIfPresent(key+"12",100);
        int [] ints = {1,2,3};
        // 不能通过数据添加多个值，此时只添加了数组作为一个值
//        result = list.rightPushAll(key,ints);
        // 可以通过集合添加多个值
        result = list.rightPushAll(key, Arrays.asList(23,45,65,2133,10,10,10,10,10,10,10));
        int index = (int) list.index(key,1);
        // 下标对应的value值，下标从零开始
        int resultInt = (int) list.leftPop(key);
        // 一直等待有数据返回，直至超时，如果没有这个key也会阻塞，
        // 命令执行的超时时间如果小于该命令的超时时间则会报命令执行超时
        resultInt = (int) list.leftPop(key,10, TimeUnit.SECONDS);
        List<Integer> integerList =  list.range(key,0,-1);
        resultInt = (int) list.rightPopAndLeftPush(key,key1);
        resultInt = (int) list.rightPopAndLeftPush(key,key1,10,TimeUnit.SECONDS);
        // 删除5个值为10的数据,返回值为删除了几条数据，从左侧开始删除
        result = list.remove(key,3L,10);
        result = list.size(key);
        // 保留下标区间内的数据，下标从零开始
        list.trim(key,1,10);
    }
}
