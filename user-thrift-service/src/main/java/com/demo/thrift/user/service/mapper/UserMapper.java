package com.demo.thrift.user.service.mapper;

import com.demo.thrift.user.service.api.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select id,username,password,nickname,mobile,email " +
            "from pe_user where id=#{id}")
    UserInfo getUserById(@Param("id")int id);

    @Select("select id,username,password,nickname,mobile,email " +
            "from pe_user where username=#{username}")
    UserInfo getUserByName(@Param("username")String username);

    @Insert("insert into pe_user (username,password,nickname,mobile,email)" +
            "value (#{user.username},#{user.password},#{user.nickname},#{user.mobile},#{user.email})")
    void registerUser(@Param("user") UserInfo user);

    @Select("select u.id,u.username,u.password,u.nickname,u.mobile,u.email,t.intro,t.rate " +
            "from pe_user as u, pe_teacher as t " +
            "where id=#{id} and id=user_id;")
    UserInfo getTeacherById(@Param("id")int id);
}
