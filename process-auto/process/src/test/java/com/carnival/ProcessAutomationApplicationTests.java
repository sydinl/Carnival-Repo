package com.carnival;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.carnival.common.config.PaConfig;
import com.carnival.common.utils.file.FileUploadUtils;
import com.carnival.common.utils.file.FileUtils;
import com.carnival.dao.ExtractDataDao;
import com.carnival.dao.TemplateInfoDao;
import com.carnival.dao.TemplateMappingDao;
import com.carnival.domain.TemplateInfo;
import com.carnival.domain.TemplateMapping;
import com.carnival.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProcessAutomationApplicationTests {

//    @Autowired
//    private FileController fileController;

    @Test
    public void testFile() throws IOException {
        System.out.println("dfas");
        String filePath = PaConfig.getUploadPath();
        File file = new File("E:\\home\\carnival\\logs\\sys-error.log");
//        FileUploadUtils.upload((MultipartFile) file);
    }

    @Autowired
    private ExtractDataDao userMapper;

    @Test
    public void testSelectList() {
        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        wrapper.gt("age", 23);
        List<User> users = this.userMapper.selectList(wrapper);
        for (User user : users) {
            System.out.println("user = " + user);
        }
    }

    @Test
    public void testExportDataToCSV(){
        String filePath = "E:\\Java\\project\\hackthon\\testResult\\file.csv";
        List<User> users = this.userMapper.selectList(null);
        FileUtils.exportToCsv(users,filePath);
    }

    @Test
    public void testparseCSV() throws Exception {
//        List<Map<String, Object>> mapList = FileUtils.castCSVToList("E:\\Java\\project\\hackthon\\testResult\\file.csv");
//        List<Map<String, Object>> mapList = FileUtils.castExcelToList("E:\\Java\\project\\hackthon\\testResult\\3432.xlsx");
        List<Map.Entry<String, Object>> entryList = FileUtils.castXmlToMap("E:\\Java\\project\\hackthon\\testResult\\pom.xml");
        System.out.println(entryList);
    }

    @Autowired
    private TemplateInfoDao templateInfoDao;

    @Autowired
    private TemplateMappingDao templateMapping;

    @Test
    public void testGet(){
        List<TemplateMapping> templateMappings = templateMapping.selectList(null);
        List<TemplateInfo> templateInfos = templateInfoDao.selectAll();
        System.out.println(templateInfos);
    }

}