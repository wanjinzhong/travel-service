package com.tip.travel.service.dao;

import com.tip.travel.common.domain.Travel;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TravelDao {
    @Select("select * from travel where id = #{id}")
    @Results({
            @Result(property = "ownerId", column = "owner_id"),
            @Result(property = "coverImage", column = "cover_image"),
            @Result(property = "startDate", column = "start_date"),
            @Result(property = "endDate", column = "end_date"),
            @Result(property = "entryId", column = "entry_id"),
            @Result(property = "entryDatetime", column = "entry_datetime"),
            @Result(property = "updateId", column = "update_id"),
            @Result(property = "updateDatetime", column = "update_datetime"),
            @Result(property = "deleteId", column = "delete_id"),
            @Result(property = "deleteDatetime", column = "delete_datetime"),
    })
    Travel findById(Long id);

    @Insert("INSERT INTO travel\n" +
            "(owner_id, name, description, cover_image, start_date, end_date, entry_id, entry_datetime)\n" +
            "VALUES(#{ownerId}, #{name}, #{description}, #{coverImage}, #{startDate}, #{endDate}, #{entryId}, #{entryDatetime});\n")
    Integer createNewTravel(Travel travel);

    @Delete("delete from travel where id = #{id}")
    Integer deleteTravel(Long id);
}
