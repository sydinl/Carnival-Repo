package com.carnival.endpoint;

import com.carnival.config.TemplateConfig;
import com.carnival.endpoint.Endpoint;

public abstract class InboundAdapter implements Endpoint {




    public Object processInbound(TemplateConfig config){
        return null;
    }

    @Override
    public boolean isReady() {
        return false;
    }
}
