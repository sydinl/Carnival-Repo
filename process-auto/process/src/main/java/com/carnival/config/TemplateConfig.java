package com.carnival.config;

import com.carnival.entity.TemplateInfo;
import com.carnival.entity.TemplateMapping;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TemplateConfig {


    TemplateInfo templateInfo;

    List<TemplateMapping> templateMapping;


}
