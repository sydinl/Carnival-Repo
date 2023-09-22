package com.carnival.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.carnival.common.core.domain.AjaxResult;
import com.carnival.entity.TemplateInfo;
import com.carnival.entity.TemplateMapping;
import com.carnival.service.ITemplateInfoService;
import com.carnival.service.ITemplateMappingService;
import com.carnival.service.TriggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TriggerController {


    @Autowired
    private ITemplateMappingService templateMappingService;

    @Autowired
    private ITemplateInfoService templateInfoService;

    @Autowired
    private TriggerService triggerService;

    @GetMapping("/trigger")
    public AjaxResult processData(String templateName){

        LambdaQueryWrapper<TemplateMapping> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TemplateMapping::getTemplateName,templateName);
        LambdaQueryWrapper<TemplateInfo> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(TemplateInfo::getTemplateName,templateName);
        List<TemplateMapping> templateMappings = templateMappingService.list(wrapper);
        TemplateInfo templateInfo = templateInfoService.getOne(wrapper1);

        triggerService.trigger(templateInfo,templateMappings);


//        ExcelUtil<TemplateInfo> excelUtil = new ExcelUtil<>(TemplateInfo.class);
//        excelUtil.exportExcel(templateInfoList,"Template Information");
        return AjaxResult.success();
    }
}
