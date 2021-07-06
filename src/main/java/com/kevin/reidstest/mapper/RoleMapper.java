package com.kevin.reidstest.mapper;

import com.kevin.reidstest.entity.Role;
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
 * @since 2021/7/5 16:36
 */
@Mapper
public interface RoleMapper {

    @Select("select * from role")
    public List<Role> getall();

    @Insert("insert into role(role_name) value(#{roleName})")
    public int insert(Role role);

    @Update("update role set(role_name)= #{roleName}")
    public int update(Role role);
}
