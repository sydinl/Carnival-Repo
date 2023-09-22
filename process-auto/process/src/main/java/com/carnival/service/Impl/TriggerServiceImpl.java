package com.carnival.service.Impl;

import com.carnival.config.TemplateConfig;
import com.carnival.endpoint.InboundAdapter;
import com.carnival.endpoint.OutBoundAdpter;
import com.carnival.entity.TemplateInfo;
import com.carnival.entity.TemplateMapping;
import com.carnival.service.ISecurityTradeService;
import com.carnival.service.TriggerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class TriggerServiceImpl implements TriggerService {


    SyncTaskExecutor executor = new SyncTaskExecutor();

    @Autowired
    Map<String, InboundAdapter> inboundAdapterMap;
    @Autowired
    Map<String, OutBoundAdpter> outBoundAdpterMap;

    @Autowired
    ISecurityTradeService tradeService;

    @Override
    public void trigger(TemplateInfo templateInfo, List<TemplateMapping> templateMappings) {
        executor.execute(() -> {
            TemplateConfig templateConfig = new TemplateConfig(templateInfo, templateMappings);
            InboundAdapter inboundAdapter = inboundAdapterMap.get(templateInfo.getSourceEnd());
            OutBoundAdpter outBoundAdpter = outBoundAdpterMap.get(templateInfo.getDestinationEnd());
            Object o = inboundAdapter.processInbound(templateConfig);
            outBoundAdpter.processOutbound(templateConfig, o);
        });
    }
}
