package com.carnival.service;

import com.carnival.domain.User;

import java.util.List;

public interface ExtractDataService {

    List<User> loadData();

    List<String> getAllTables(String DBName);

    List<String> getAllColumns(String tableName);
}
