package com.carnival.service;

import com.carnival.entity.TemplateInfo;
import com.carnival.entity.TemplateMapping;

import java.util.List;

public interface TriggerService {

   void trigger(TemplateInfo templateInfo, List<TemplateMapping> templateMappings);
}
