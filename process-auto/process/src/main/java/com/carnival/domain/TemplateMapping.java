package com.carnival.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.List;

@Data
public class TemplateMapping {

    @TableId(type = IdType.AUTO)
    private Long myRowId;

    private String templateName;

    private String sourceField;

    private String transferLogic;

    private String descField;

    private String filterOps;

    private String filterCondition;


    private String value;

    private String filterOps;

    private String filterCondition;

    private List<Filter> filters;


}
