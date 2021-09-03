package com.kevin.reidstest.service;

import com.kevin.reidstest.entity.Role;
import com.kevin.reidstest.entity.User;
import com.kevin.reidstest.mapper.RoleMapper;
import com.kevin.reidstest.mapper.UserMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author zhaowenjian
 * @since 2021/7/5 16:54
 */
@Service
public class TestService {

    @Resource
    UserMapper userMapper;

    @Resource
    RoleMapper roleMapper;
    /**
     * @Cacheable
    * key = "#"  ,#号后面是方法的参数，如果参数对象可以通过user.name的方式获取对象内属性值
     *  如果方法有多个参数也可以拼起来使用key="#name+#age"，生成的key为两个参数拼接
     *  #p 代表的参数下标如：#p0为方法的第一个参数
     *  如果只定义value不指定key则会根据参数生成默认的keyvalue="users"，生成key：users::SimpleKey [kevin,18]
     *
     * value: value 定义key的前缀，可以单个可以多个，接受变量为数组，只有value属性时value=，可以省略，value = {"kevin","kevin1"}
     *
     * condition：用于判断是否要使用缓存,condition = "#p0=='kevin'",condition="#user.id%2==0"
     *
     * @CachePut
     * cachePut方法与cacheable方法使用相同，区别是cacheable执行前会检查是否存在缓存，存在则从缓存中获取，不存在则执行方法，然后将
     * 结果缓存，cachePut执行前不检查缓存，执行方法然后将结果缓存，也就是该方法不使用缓存
     *
     * @CacheEvict
     * 清除缓存，value、key、condition属性与cacheable用法相同，只是多了两个属性allEntries和beforeInvocation
     * allEntries:默认为false，如果设为true，那么会清除以value开头的所有缓存,若果设置condition，则要满足条件才会清除缓存
     * 此时设置key无作用，还是会删除所有以value开头的缓存
     *
     * beforeInvocation:默认值false，会先执行目标放方法，执行成功之后才会清除缓存，设置为true则会先清除缓存然后执行目标方法
     *
     *
     * @Caching
     * 该注解可以让我们在一个方法或者类上同时指定多个Spring Cache相关的注解。其拥有三个属性：cacheable、put和evict，分别用于指定@Cacheable、@CachePut和@CacheEvict
     * @Caching(cacheable = @Cacheable(value = "user"),evict = {@CacheEvict(value = "users",allEntries = true),@CacheEvict(value = "jfwf")}
     *     ,put = @CachePut(value = "put",key = "#p0"))
     *
     *   当cacheable与put同时存在时，返回结果是执行目标方法的返回值，cacheable的缓存如果存在并不会更新，不存在则添加
     *
     *
    **/



    //@Cacheable(value="users", key="#user.name") //生成key：users::kevin
    //@Cacheable(value="users", key="#name") //生成key：users::kevin
    //@Cacheable(value="users", key="#name+#age") //生成key：users::kevin18
    //@Cacheable(value="users", key="#p1")
    //@Cacheable(value="users") //生成key：users::SimpleKey [kevin,18]
    //@Cacheable({"kevin","kevin1"}) 生成两个key，kevin::User(id=null, name=kevin, age=null)
    //@Cacheable(value = {"kevin","kevin1"})
    //kevin1::User(id=null, name=kevin, age=null)
    //@Cacheable(value="users",key= "#p0+'1234'",condition = "#p0=='kevin'")
//    @CachePut(value="users",key= "#p0+'1234'",condition = "#p0=='kevin'")
//    @CachePut(value="users")
    //@Caching(cacheable = @Cacheable(value = "user"),evict = {@CacheEvict(value = "users",allEntries = true),@CacheEvict(value = "jfwf")}
    //,put = @CachePut(value = "put",key = "#p0"))
    @Cacheable("nokey")
    public List<User> getAll(){
        getAll1();
        return userMapper.getUserS();
    }

    //@CachePut(value = "add",key = "#user.name")
    // 添加的缓存为返回结果，所以add不需要加注解添加缓存
    @Transactional(rollbackFor = Exception.class)
    public int add(User user){
        int result = userMapper.add(user);
//        Role role = new Role();
//        role.setRoleName("admin");
//        roleMapper.insert(role);
//        addRole(role);
//        System.out.println(1/0);
        return result;
    }

   // @CacheEvict(value = "users",key = "#user.name")
    //@CacheEvict(value = {"users","user"},key = "#user.name",condition = "#user.name=='kevin'",allEntries=true)
    @CacheEvict(value = "users",key = "#user.name",beforeInvocation = false,allEntries=true)
    public int update(User user){
        System.out.println(1/0);
        return userMapper.update(user);
    }

    @CacheEvict(value = "users",key = "#user.name")
    public void delete(){

    }

    @Cacheable(value = "role")
    public List<Role> getAll1(){
        return roleMapper.getall();
    }

    @Transactional(rollbackFor = Exception.class)
    public int addRole(Role role){
        int result = roleMapper.insert(role);
        System.out.println(1/0);
        return result;
    }

    @CachePut
    public int update1(Role role){
        return roleMapper.update(role);
    }

//    @Transactional(rollbackFor = Exception.class)
    public void testTr(){
        User user = new User();
        user.setName("kevin");
        user.setAge(16);
        Role role = new Role();
        role.setRoleName("admin");
        add(user);
        addRole(role);

    }

}
