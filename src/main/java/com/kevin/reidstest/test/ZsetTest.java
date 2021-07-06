package com.kevin.reidstest.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 *
 * </p>
 *
 * @author zhaowenjian
 * @since 2021/6/29 10:27
 */
@Component
public class ZsetTest {

    @Autowired
    ZSetOperations zset;

    @Autowired
    RedisTemplate redisTemplate;

    String key = "zset";

    String key1 = "zset1";

    String key2 = "zset2";

    String sort = "sort";

    String kevin = "kevin";

    public void zSet(){
        // 获取操作对象
        zset.getOperations();
        boolean flag = zset.add(key,"kevin",10);
        flag = zset.add(key1,"kevin",1000);
        flag = zset.add(key2,"kevin",10000);
        Set set = new HashSet();
        set.add(new DefaultTypedTuple("kevin1",11.0));
        set.add(new DefaultTypedTuple("kevin2",12.0));
        set.add(new DefaultTypedTuple("kevin3",12.0));
        //结果为添加的条数
        Long result = zset.add(key,set);
        result = zset.count(key,10,11);//分数范围内的value个数，范围为闭区间
        result = zset.count(key,0,-1);
        flag = zset.add(key,"test",10000000);
        result = zset.remove(key,"test");// 返回删除数据的条数
        flag = zset.add(key,"test",10000000);
        flag = zset.add(key,"test1",10000000);
        String [] strs = {"test","test1"};
        result = zset.remove(key,strs);
        Double value = zset.incrementScore(key,kevin,10);// 返回该表后的score值

        // 交集
        // 取交集时如果分数不同，value值相同则取分数值之和
        result = zset.intersectAndStore(key,key1,"ZsetStore");
        result = zset.intersectAndStore(key, Arrays.asList(key1,key2),"ZsetStore1");
        result = zset.intersectAndStore(key, Arrays.asList(key1,key2),"ZsetStore2", RedisZSetCommands.Aggregate.MAX);
        RedisZSetCommands.Weights weights = RedisZSetCommands.Weights.of(0.1,0.2,0.3);
        // weight 代表计算分数时的权重，weight的个数必须与key的个数相同，按顺序为每个key分配权重
        result = zset.intersectAndStore(key, Arrays.asList(key1,key2),"ZsetStore3", RedisZSetCommands.Aggregate.SUM,weights);
        // 获取排序后的下标范围内的数据，数据按分数由小到大排列，后两个值代表下标，下标从零开始
        Set setValue = zset.range(key,0,-1);
        setValue = zset.range(key,1,3);

        //用于非分数排序的情况，根据value进行排序,没太明白range用法
        flag = zset.add(sort,"add",10000);
        flag = zset.add(sort,"gfd",10000);
        flag = zset.add(sort,"rgf",10000);
        setValue = zset.rangeByLex(sort, RedisZSetCommands.Range.range().gte("c"));
        setValue = zset.rangeByLex(sort, RedisZSetCommands.Range.range().lt("c"));
        // 研究limit的用法，count保留数据的数量，offset有问题
        setValue = zset.rangeByLex(key,RedisZSetCommands.Range.range(), RedisZSetCommands.Limit.limit().count(2));
        setValue = zset.rangeByLex(key,RedisZSetCommands.Range.range(), RedisZSetCommands.Limit.limit().offset(0));
        //按分数从小到大排列，包含最大值最小值
        setValue = zset.rangeByScore(key,11,100);
        //key,分数最小值分数最大值，offset下标、count保留数量
        setValue = zset.rangeByScore(key,10,100,1,10);
        // 返回结果带分数，从小到大
        Set<DefaultTypedTuple> map = zset.rangeWithScores(key,0,-1);
        map = zset.rangeByScoreWithScores(key,10,100);
        //key,分数最小值分数最大值，offset下标、count保留数量
        map = zset.rangeByScoreWithScores(key,10,100,10,10);
        // 返回该值的下标
        result = zset.rank(key,"kevin");

        value = zset.score(key,kevin);

        // zcard与size底层使用一个方法
        result = zset.size(key);
        result = zset.zCard(key);

        //此方法排序为从大到小，其他无区别
        setValue = zset.reverseRange(key,0,-1);
        setValue = zset.reverseRangeByScore(key,10,1000);
        setValue = zset.reverseRangeByScore(key,10,1000,10,10);
        setValue = zset.reverseRangeWithScores(key,0,-1);
        map = zset.reverseRangeByScoreWithScores(key,10,1000);
        map = zset.reverseRangeByScoreWithScores(key,10,1000,10,10);
        // 返回当前value分数对应的下标，注意排序为从大到小
        result = zset.reverseRank(key,kevin+"1");

        //并集
        result = zset.unionAndStore(key,key1,"uniZset");
        result = zset.unionAndStore(key, Arrays.asList(key1,key2),"uniZset1");
        result = zset.unionAndStore(key, Arrays.asList(key1,key2),"uniZset2", RedisZSetCommands.Aggregate.MAX);
        // weight 代表计算分数时的权重，weight的个数必须与key的个数相同，按顺序为每个key分配权重
        result = zset.unionAndStore(key, Arrays.asList(key1,key2),"uniZset3", RedisZSetCommands.Aggregate.MAX,RedisZSetCommands.Weights.of(0.1,0.2,0.3));

        // scan
        Cursor<ZSetOperations.TypedTuple<Object>> cursor = zset.scan(key,ScanOptions.scanOptions().match("*").count(1).build());
        while(cursor.hasNext()){
            ZSetOperations.TypedTuple typedTuple = cursor.next();
            System.out.println(typedTuple.getScore());
            System.out.println(typedTuple.getValue());
        }
        try {
            cursor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        result = zset.removeRange(key,0,-1);
        result = zset.removeRangeByScore(key1,0,1000000);
        result = zset.removeRangeByScore(key2,0,100000000);

        result = zset.removeRange("uniZset",0,-1);
        result = zset.removeRange("uniZset1",0,-1);
        result = zset.removeRange("uniZset2",0,-1);
        result = zset.removeRange("uniZset3",0,-1);

        result = zset.removeRange("ZsetStore",0,-1);
        result = zset.removeRange("ZsetStore1",0,-1);
        result = zset.removeRange("ZsetStore2",0,-1);
        result = zset.removeRange("ZsetStore3",0,-1);
    }
}
