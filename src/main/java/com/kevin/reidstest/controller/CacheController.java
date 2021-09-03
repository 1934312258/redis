package com.kevin.reidstest.controller;

import com.kevin.reidstest.entity.Role;
import com.kevin.reidstest.entity.User;
import com.kevin.reidstest.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author zhaowenjian
 * @since 2021/7/5 16:27
 */
@RestController
public class CacheController {

    @Autowired
    TestService service;

    @RequestMapping("/getUsers")
    public List<User> getall(){
        User user = new User();
        user.setName("kevin");
        String name = "kevin";
        int age = 18;
        return service.getAll();
    }

    @RequestMapping("/addUser")
    public int addUser(@RequestBody User user){
        return service.add(user);
    }

    @RequestMapping("/updateUser")
    public int updateUser(@RequestBody User user){
        return service.update(user);
    }

    @RequestMapping("/deleteUser")
    public void updateUser(){
        service.delete();
    }

    @RequestMapping("/testTr")
    public void testTr(){
        service.testTr();
    }

    @RequestMapping("/getRoles")
    public List<Role> getallRole(){
        return service.getAll1();
    }

    @RequestMapping("/addRole")
    public int addRole(@RequestBody Role role){
        return service.addRole(role);
    }

    @RequestMapping("/updateRole")
    public int updateRole(@RequestBody Role role){
        return service.update1(role);
    }
}
