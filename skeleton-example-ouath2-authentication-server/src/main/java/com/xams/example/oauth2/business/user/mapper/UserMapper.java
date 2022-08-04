package com.xams.example.oauth2.business.user.mapper;


import com.xams.example.oauth2.business.user.entity.SysUser;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    @Select("select * from sys_user where username=#{username}")
    @Results({
        @Result(id = true, property = "id", column = "id"),
        @Result(property = "roles", column = "id", javaType = List.class,
            many = @Many(select = "com.xams.example.oauth2.business.user.mapper.RoleMapper.findByUid"))
    })
    SysUser findByUsername(String username);

}
