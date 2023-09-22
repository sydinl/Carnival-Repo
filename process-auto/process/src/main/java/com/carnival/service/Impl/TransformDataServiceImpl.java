package com.carnival.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.carnival.dao.TemplateInfoDao;
import com.carnival.dao.TemplateMappingDao;
import com.carnival.dao.TransformDataDao;
import com.carnival.domain.TemplateInfo;
import com.carnival.domain.TemplateMapping;
import com.carnival.service.TransformDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TransformDataServiceImpl implements TransformDataService {

    @Autowired
    private TransformDataDao transformDataDao;

    @Autowired
    private TemplateInfoDao templateInfoDao;

    @Autowired
    private TemplateMappingDao templateMappingDao;

    @Override
    @Transactional
    public void saveData(TemplateInfo templateInfo) {
        templateInfoDao.saveTemplateInfo(templateInfo);
        templateMappingDao.saveTemplateMapping(templateInfo.getSrcfields());
    }

    @Override
    public List<TemplateMapping> selectData() {
        return templateMappingDao.selectList(null);
    }

    @Override
    public String selectTemplateData(String templateName) {
        TemplateInfo templateInfo = templateInfoDao.selectTemplateData(templateName);
        TemplateMapping templateMapping = templateMappingDao.selectTemplateMappingData(templateName);

        transformDataDao.triggerData();
        return "";
    }
}
