package com.kevin.reidstest.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @创建人 zhaowenjian
 * @创建时间 2020/12/9
 * @描述
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StringTest {

    //    @Resource(name ="redisTemplate")
//    RedisTemplate template;
    RedisTemplate template;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.template = redisTemplate;
    }

    @Autowired
    static ValueOperations<String, Object> ops;

    @Test
    public void string() {
        template.opsForValue().set(",", "");
        template.opsForGeo().add("kevin",new Point(1.0,2.0),1L);
        ops.set("kevin", "3254354");
    }

}
