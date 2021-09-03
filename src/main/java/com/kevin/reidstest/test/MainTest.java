package com.kevin.reidstest.test;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.kevin.reidstest.entity.People;
import com.kevin.reidstest.entity.People1;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * </p>
 *
 * @author zhaowenjian
 * @since 2021/7/2 13:23
 */
@Component
public class MainTest {

    @Autowired
    RedisTemplate redisTemplate;

    public void mainTest(){

        ValueOperations value = redisTemplate.opsForValue();
        HashOperations hash = redisTemplate.opsForHash();
        List<People> list = Arrays.asList(new People("kevin",1L),new People("zhao",2L));
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonArray  array = gson.toJsonTree(list).getAsJsonArray();
        String str = gson.toJson(list);

//        redisTemplate.opsForSet().add("KevinArray",array);
//        redisTemplate.opsForValue().set("kevinStr",str);

        value.set("kevin","kevin");
        value.set("kevin",null);
        Map<String,String> map = new HashMap<>();
        map.put("kevin","1");
        map.put("zhao","2");
        hash.putAll("hash",map);
//        Set setKey = hash.keys("hash");
//        hash.delete("hash",setKey.toArray());
        hash.putAll("hash",new HashMap());


        hash.putAll("hash",new HashMap());

        Set set = redisTemplate.opsForSet().members("KevinArray");
        String jsonStr = (String) redisTemplate.opsForValue().get("kevinStr");
        JsonArray jsonArray = gson.fromJson(jsonStr,JsonArray.class);


        List<People1> people1s = list.stream().map(a->{
            People1 people1 = new People1();
            BeanUtils.copyProperties(a,people1);
            return people1;
        }).collect(Collectors.toList());
        redisTemplate.opsForSet().add("Kevin",list.toArray());
        Set<Object> peopleList = redisTemplate.opsForSet().members("Kevin");
        peopleList.forEach(a->{
            People people = (People) a;

        });
        List<People> peopleList1 = new ArrayList(peopleList);
        System.out.println();


    }

    public static void main(String[] args) {
        String str = "kevin";
        System.out.println(str.indexOf("n"));
        List list = Arrays.asList(1,2,3,4,5);
        list.forEach(a->{
            if(a.equals(2)){
                System.out.println("触发了");
                return ;
            }
            System.out.println(a);
        });
        System.out.println("执行了");
    }

}
