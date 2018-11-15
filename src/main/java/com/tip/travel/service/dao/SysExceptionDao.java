package com.tip.travel.service.dao;

import com.tip.travel.common.domain.SysException;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysExceptionDao {

    @Insert("insert into sys_exception (class, message, stack_trace, entry_id, entry_datetime)"
            + " values (#{className}, #{message}, #{stackTrace}, 0, now())")
    void saveException(SysException exception);
}
