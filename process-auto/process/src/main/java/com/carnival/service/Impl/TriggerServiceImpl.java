package com.carnival.service.Impl;

import com.carnival.entity.TemplateInfo;
import com.carnival.entity.TemplateMapping;
import com.carnival.service.TriggerService;
import org.springframework.core.task.SyncTaskExecutor;

import java.util.List;

public class TriggerServiceImpl implements TriggerService {


    SyncTaskExecutor executor =new SyncTaskExecutor();


    @Override
    public void trigger(TemplateInfo templateInfo, List<TemplateMapping> templateMappings) {
        executor.execute(()->{
            String sourceEnd = templateInfo.getSourceEnd();
            String source = templateInfo.getSource();
            



        });
    }
}
