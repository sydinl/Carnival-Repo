package com.carnival.endpoint;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.carnival.config.TemplateConfig;
import com.carnival.entity.SecurityTrade;
import com.carnival.entity.TemplateMapping;
import com.carnival.service.ISecurityTradeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component("carnival database")
@AllArgsConstructor
public class DBReader extends InboundAdapter {

    ISecurityTradeService tradeService;

    @Override
    public List<Map<String, Object>> processInbound(TemplateConfig config) {

        String source = config.getTemplateInfo().getSource();
        List<TemplateMapping> templateMappings = config.getTemplateMapping();
        List<SecurityTrade> trades = null;
        Wrapper<?> queryWrapper = composeWrapper(templateMappings);
        List<Map<String, Object>> mapList = new ArrayList<>();
        if ("security_trade".equals(source)) {
            trades = tradeService.list((Wrapper<SecurityTrade>) queryWrapper);
            dbMapping(trades, mapList, templateMappings);

        }

        return mapList;
    }

    private static void dbMapping(List<SecurityTrade> trades, List<Map<String, Object>> mapList, List<TemplateMapping> templateMappings) {
        trades.stream().map((trd) -> {
            Map<String, Object> map = new HashMap<>();
            Class<? extends SecurityTrade> aClass = trd.getClass();
            mapList.add(map);
            templateMappings.forEach((mapping) -> {
                try {
                    Field field = aClass.getDeclaredField(mapping.getSourceField());
                    field.setAccessible(true);
                    Object o = field.get(trd);
                    map.put(mapping.getDescField(), o);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }

            });
            return map;
        }).collect(Collectors.toList());
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


