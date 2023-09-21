package com.carnival.service.Impl;

import com.carnival.dao.TransformDataDao;
import com.carnival.service.TransformDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TransformDataServiceImpl implements TransformDataService {

    @Autowired
    private TransformDataDao transformDataDao;
}
