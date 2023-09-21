package com.carnival.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.carnival.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ExtractDataDao extends BaseMapper<User> {

    @Select("SELECT table_name FROM information_schema.tables WHERE table_schema= #{DBName}")
    public List<String> getAllTables(String DBName);

    @Select("SELECT column_name FROM information_schema.columns WHERE table_name = #{tableName}")
    List<String> getAllColumns(String tableName);
}
