package com.carnival.dao;

import com.alibaba.druid.util.TransactionInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.carnival.domain.TemplateInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TransformDataDao extends BaseMapper<TemplateInfo> {


}
