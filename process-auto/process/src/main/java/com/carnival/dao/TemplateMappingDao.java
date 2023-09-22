package com.carnival.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.carnival.domain.TemplateMapping;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TemplateMappingDao extends BaseMapper<TemplateMapping> {

    void saveTemplateMapping(List<TemplateMapping> list);
}
