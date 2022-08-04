package com.xams.example.oauth2.business.user.mapper;

import com.xams.example.oauth2.business.user.entity.SysRole;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMapper {

    @Select("select r.id,r.role_name roleName,r.role_desc roleDesc " +
        "FROM sys_role r,sys_user_role ur " +
        "WHERE r.id=ur.role_id AND ur.user_id=#{uid}")
    List<SysRole> findByUid(Integer uid);


}
