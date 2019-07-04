package com.yunze.vehiclemanagement.mapper;

import com.yunze.vehiclemanagement.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
  @Select(" select * from user_tb where id = #{id}")
  User getUserById(String id);

  // 用户名唯一
  @Select(" select * from user_tb where username = #{username}")
  User getUserByUsername(String username);

  @Insert(" insert into user_tb(id, username, password) values(#{id}, #{username}, #{password})")
  int insertUser(User user);

}
