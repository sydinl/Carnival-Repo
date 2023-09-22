package com.carnival.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.carnival.domain.TemplateInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TemplateInfoDao extends BaseMapper<TemplateInfo> {

    void saveTemplateInfo(TemplateInfo templateInfo);

    @Select("select my_row_id from template_info;")
    List<TemplateInfo> selectAll();

    TemplateInfo selectTemplateData(String templateName);
}
