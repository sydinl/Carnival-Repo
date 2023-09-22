package com.carnival.endpoint;

import com.carnival.config.TemplateConfig;

import java.util.List;
import java.util.Map;

public abstract class OutBoundAdpter implements Endpoint{


    public Object processOutbound(TemplateConfig config, List<Map<String,Object>> object){
        return null;
    }

    @Override
    public boolean isReady() {
        return false;
    }
}
