package com.tip.travel.service.dao;

import com.tip.travel.service.UserSecretBo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {

    @Select("select u.id, u.alias, s.password, s.salt from user u join security s on u.id = s.user_id where u.phone = #{phone}")
    @Results({
            @Result(property = "userId", column = "id"),
            @Result(property = "userName", column = "alias")
    })
    UserSecretBo querySecurityByPhone(String phone);
}
