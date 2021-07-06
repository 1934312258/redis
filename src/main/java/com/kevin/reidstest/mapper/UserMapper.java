package com.kevin.reidstest.mapper;

import com.kevin.reidstest.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author zhaowenjian
 * @since 2021/7/1 9:12
 */
@Mapper
public interface UserMapper {

    @Select("select * from user")
    public List<User> getUserS();

    @Insert("insert into user(name,age)values(#{name},#{age})")
    public Integer add(User user);

    @Update("update user set name = #{name},age= #{age} where id = #{id}")
    public Integer update(User user);


}
