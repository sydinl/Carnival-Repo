package com.carnival.controller;

import com.carnival.common.config.PaConfig;
import com.carnival.common.utils.file.FileTypeUtils;
import com.carnival.common.utils.file.FileUploadUtils;
import com.carnival.common.utils.file.FileUtils;
import com.carnival.common.utils.poi.ExcelUtil;
import com.carnival.domain.User;
import com.carnival.service.ExtractDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/extractFileData")
public class ExtractDataFromFileController {

    @Autowired
    private ExtractDataService extractDataService;

    @PostMapping("/parseFile")
    public List<Map<String, Object>> extractDatafromFile(MultipartFile file) throws Exception {
        String fileName = FileUploadUtils.upload(file);
        String fileType = FileTypeUtils.getFileType(fileName);
        if("csv".equals(fileType)){
            List<Map<String, Object>> list = FileUtils.castCSVToList(fileName);
            return list;
        }else if("xlsx".equals(fileType) || "xls".equals(fileType)){
            List<Map<String, Object>> mapList = FileUtils.castExcelToList(fileName);
            return mapList;
        }else if("xml".equals(fileType)){
            List<Map<String, Object>> mapList = FileUtils.castXmlToList(fileName);
            return mapList;
        }
        return null;
    }




}
