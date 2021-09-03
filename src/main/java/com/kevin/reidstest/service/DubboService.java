package com.kevin.reidstest.service;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

/**
 * <p>
 *
 * </p>
 *
 * @author zhaowenjian
 * @since 2021/7/14 11:30
 */
@Service
public class DubboService {

    @Reference(version = "1.0.1",group = "kevin",timeout = 30000)
    DemoService service;

    public void test(){
        String result = service.sayHello("kevin");
        System.out.println(result);
    }

}
