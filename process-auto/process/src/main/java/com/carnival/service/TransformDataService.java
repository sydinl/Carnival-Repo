package com.carnival.service;

import com.carnival.domain.TemplateInfo;
import com.carnival.domain.TemplateMapping;

import java.util.List;

public interface TransformDataService {

    void saveData(TemplateInfo templateInfo);

    List<TemplateMapping> selectData();

    String selectTemplateData(String templateName);
}
