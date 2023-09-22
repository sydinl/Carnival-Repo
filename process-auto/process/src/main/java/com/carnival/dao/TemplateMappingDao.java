package com.carnival.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.carnival.domain.TemplateMapping;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TemplateMappingDao extends BaseMapper<TemplateMapping> {

    void saveTemplateMapping(List<TemplateMapping> list);

    @Select("SELECT * FROM template_mapping WHERE String templateName = #{templateName}")
    List<TemplateMapping> getTemplateMappingsByTMName(String templateName);
}
