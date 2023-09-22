package com.carnival.endpoint;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.carnival.config.TemplateConfig;
import com.carnival.entity.SecurityTrade;
import com.carnival.entity.TemplateMapping;
import com.carnival.service.ISecurityTradeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("carnival database")
@AllArgsConstructor
public class DBReader extends InboundAdapter {

    ISecurityTradeService tradeService;

    @Override
    public Object processInbound(TemplateConfig config) {

        String sourceEnd = config.getTemplateInfo().getSourceEnd();
        String source = config.getTemplateInfo().getSource();
        List<TemplateMapping> templateMappings = config.getTemplateMapping();
        List<SecurityTrade> trades = null;
        Wrapper<?> queryWrapper = composeWrapper(templateMappings);
        if ("security_trade".equals(source)) {
            trades = tradeService.list((Wrapper<SecurityTrade>) queryWrapper);
        }
        return trades;
    }

    public Wrapper<?> composeWrapper(List<TemplateMapping> templateMappings) {
        QueryWrapper<Object> queryWrapper = new QueryWrapper<>();
        for (TemplateMapping templateMapping : templateMappings) {
            String ops = templateMapping.getFilterOps();
            String sourceField = templateMapping.getSourceField();
            queryWrapper.select(sourceField);
            String target = templateMapping.getFilterCondition();
            switch (ops) {
                case "eq":
                    queryWrapper.eq(sourceField, target);
                    break;
                case "neq":
                    queryWrapper.ne(sourceField, target);
                    break;
                case "contains":
                    queryWrapper.like(sourceField, target);
                    break;
            }
        }
        return queryWrapper;
    }


}


