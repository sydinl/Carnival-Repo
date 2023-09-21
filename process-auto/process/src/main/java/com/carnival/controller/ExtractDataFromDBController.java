package com.carnival.controller;

import com.carnival.common.utils.poi.ExcelUtil;
import com.carnival.domain.User;
import com.carnival.service.ExtractDataService;
import com.carnival.service.LoadDataService;
import com.carnival.service.TransformDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/extractDBData")
public class ExtractDataFromDBController {

    @Autowired
    private ExtractDataService extractDataService;

    @GetMapping("/extractDatafromDB")
    public List<User> extractDatafromDB(String fields){
        List<User> userList = extractDataService.loadData();
        return userList;
    }

    /**
     * Get all tables from DB
     * @return
     */
    @GetMapping("/getAllTables")
    public List<String> getAllTables(@RequestParam String DBName){
        return extractDataService.getAllTables(DBName);
    }

    /**
     * Get all columns from table
     * @return
     */
    @GetMapping("/getAllColumns")
    public List<String> getAllColumns(@RequestParam String tableName){
        return extractDataService.getAllColumns(tableName);
    }

}
