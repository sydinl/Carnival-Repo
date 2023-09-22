package com.carnival.controller;

import com.carnival.common.core.domain.AjaxResult;
import com.carnival.domain.TemplateInfo;
import com.carnival.domain.TemplateMapping;
import com.carnival.domain.User;
import com.carnival.service.TransformDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transformData")
public class TransformDataController {

    @Autowired
    private TransformDataService transformDataService;

    @PostMapping("/saveDataToDB")
    public AjaxResult saveDataToDB(@RequestBody TemplateInfo templateInfo){
        convertData(templateInfo);
        transformDataService.saveData(templateInfo);
        return AjaxResult.success();
    }

    void convertData(TemplateInfo templateInfo){
        for(TemplateMapping tm:templateInfo.getSrcfields()){
            tm.setTemplateName(templateInfo.getTemplateName());
            tm.setDescField(tm.getDestField());
            tm.setSourceField(tm.getValue());
            tm.setFilterOps(tm.getFilters().get(0).getType());
            tm.setFilterCondition(tm.getFilters().get(0).getValue());
        }
    }

}
