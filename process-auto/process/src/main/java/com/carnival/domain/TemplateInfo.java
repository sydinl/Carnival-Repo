package com.carnival.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.List;

@Data
public class TemplateInfo {

    @TableId(type = IdType.AUTO)
    private Long myRowId;

    private String sourceEnd;

    private String source;

    private String destination;

    private String destinationEnd;

    private String templateName;

    private List<TemplateMapping> srcfields;

}
