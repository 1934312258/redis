package com.kevin.reidstest.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *
 * </p>
 *
 * @author zhaowenjian
 * @since 2021/6/28 14:18
 */
@Component
public class SetTest {

    @Autowired
    SetOperations set;

    @Autowired
    RedisTemplate redisTemplate;

    String key = "kevinSet";
    String key1 = "kevinSet1";
    String key2 = "kevinSet2";
    String key3 = "kevinSet3";

    public void SetTest() throws IOException {
        // 获取操作对象，template
        set.getOperations();
        // 返回值为成功添加了几条数据
        Long result = set.add(key,1,2,2);
        result = set.add(key1,4,5,6,7,8,9);
        result = set.add(key2,10,23,15,19);
        result = set.add(key3, Arrays.asList(10,23,15,19).toArray());
        result = set.size(key1);
        // match 匹配要遍历的值，count一次取出多少条数据
        Cursor<Integer> cursor = set.scan(key1, ScanOptions.scanOptions().match("*").count(2).build());
        while(cursor.hasNext()){
            // 通过next移动游标
            int resultInt = cursor.next().intValue();
            System.out.println(resultInt);
        }
        // 最后一定要关闭游标，不然一直占用连接
        cursor.close();
        // 取差集，返回key中独有的数据
        Set values = set.difference(key,key1);
        values = set.difference(Arrays.asList(key,key1,key2));
        values = set.difference(key, Arrays.asList(key1,key2,key3));
        result = set.differenceAndStore(key,key1,"store");
        result = set.differenceAndStore(Arrays.asList(key,key1,key2),"store1");
        result = set.differenceAndStore(key, Arrays.asList(key1,key2,key3),"store2");
        // 取交集
        values = set.intersect(key,key1);
        values = set.intersect(Arrays.asList(key,key1,key2));
        values = set.intersect(key, Arrays.asList(key1,key2,key3));
        result = set.intersectAndStore(key,key1,"inteStore");
        result = set.intersectAndStore(Arrays.asList(key,key1,key2),"inteStore1");
        result = set.intersectAndStore(key, Arrays.asList(key1,key2,key3),"inteStore2");
        values = set.distinctRandomMembers(key1,3L);
        boolean flag = set.isMember(key,2);
        values = set.members(key1);
        flag = set.move(key1,5,"remove");
        // 随机取一个出来，并删掉
        int value = (int) set.pop(key1);
        //随机取两个出来，删掉
        List list = set.pop(key2,2);
        result = set.remove(key3,115);
        values = set.union(key,key1);
        values = set.union(Arrays.asList(key,key1,key2));
        values = set.union(key,Arrays.asList(key1,key2,key3));
        result = set.unionAndStore(key,key1,"uniStore");
        result = set.unionAndStore(Arrays.asList(key,key1,key2),"uniStore1");
        result = set.unionAndStore(key,Arrays.asList(key3,key1,key2),"uniStore2");


        redisTemplate.delete(key3);
        values = set.union(Arrays.asList(key));
    }

}
