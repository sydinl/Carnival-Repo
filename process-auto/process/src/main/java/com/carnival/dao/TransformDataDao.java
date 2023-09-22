package com.carnival.dao;

import com.alibaba.druid.util.TransactionInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.carnival.domain.TemplateInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface TransformDataDao extends BaseMapper<TemplateInfo> {

    List<Map<String,Object>> triggerData();

}
