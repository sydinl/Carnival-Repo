package com.carnival.service;

import com.carnival.domain.TemplateInfo;

public interface TransformDataService {

    void saveData(TemplateInfo templateInfo);

    TemplateInfo getTemplate(String templateName);
}
