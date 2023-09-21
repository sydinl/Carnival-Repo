package com.carnival.service.Impl;

import com.carnival.dao.ExtractDataDao;
import com.carnival.domain.User;
import com.carnival.service.ExtractDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExtractDataServiceImpl implements ExtractDataService {

    @Autowired
    private ExtractDataDao extractDataDao;

    @Override
    public List<User> loadData(){
        return extractDataDao.selectList(null);
    }

    @Override
    public List<String> getAllTables(String DBName) {
        return extractDataDao.getAllTables(DBName);
    }

    @Override
    public List<String> getAllColumns(String tableName) {
        return extractDataDao.getAllColumns(tableName);
    }
}
