package com.carnival.endpoint;

import com.carnival.config.TemplateConfig;

import java.util.List;
import java.util.Map;

public abstract class InboundAdapter implements Endpoint {




    public List<Map<String,Object>> processInbound(TemplateConfig config){
        return null;
    }

    @Override
    public boolean isReady() {
        return false;
    }

}
