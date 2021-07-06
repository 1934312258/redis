package com.kevin.reidstest.controller;

import com.kevin.reidstest.test.HashTest;
import com.kevin.reidstest.test.ListTest;
import com.kevin.reidstest.test.MainTest;
import com.kevin.reidstest.test.SetTest;
import com.kevin.reidstest.test.ZsetTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * <p>
 *
 * </p>
 *
 * @author zhaowenjian
 * @since 2021/6/21 15:45
 */
@RestController
public class TestController {
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    HashTest hashTest;

    @Autowired
    ListTest listTest;

    @Autowired
    SetTest setTest;

    @Autowired
    ZsetTest zsetTest;

    @Autowired
    MainTest mainTest;

    @RequestMapping("/test")
    public void test2() throws IOException {
        hashTest.hashTest();
    }

    @RequestMapping("/listTest")
    public void test5() throws IOException {
        listTest.ListTest();
    }

    @RequestMapping("/Settest")
    public void test3() throws IOException {
        setTest.SetTest();
    }

    @RequestMapping("/Zsettest")
    public void test4() throws IOException {
        zsetTest.zSet();
    }

    @RequestMapping("/maintest")
    public void test6() throws IOException {
        mainTest.mainTest();
    }
}
