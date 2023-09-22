package com.carnival.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class TemplateMapping {

    @TableId(type = IdType.AUTO)
    private Long myRowId;

    private String templateName;

    private String sourceField;

    private String transferLogic;

    private String descField;

}
