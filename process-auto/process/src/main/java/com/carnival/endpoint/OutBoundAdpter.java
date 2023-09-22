package com.carnival.endpoint;

import com.carnival.config.TemplateConfig;

public abstract class OutBoundAdpter implements Endpoint{


    public Object processOutbound(TemplateConfig config,Object object){
        return null;
    }

    @Override
    public boolean isReady() {
        return false;
    }
}
