package com.carnival.common.utils.file;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import com.carnival.common.config.PaConfig;
import com.carnival.common.utils.DateUtils;
import com.carnival.common.utils.StringUtils;
import com.carnival.common.utils.uuid.IdUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件处理工具类
 * 
 * @author carnival
 */
@Slf4j
public class FileUtils {
    public static String FILENAME_PATTERN = "[a-zA-Z0-9_\\-\\|\\.\\u4e00-\\u9fa5]+";

    /**
     * cast excel to list
     *
     * @param
     * @return
     * @throws IOException
     */
    public static List<Map<String, Object>> castExcelToList(String fileName) throws Exception {
        int sheetnum = 0, startrow = 0, startcol = 0;
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try (InputStream inputStream = new FileInputStream(new File(fileName))) {
            Workbook workbook=getExcelWorkBook(inputStream,fileName);
            Sheet sheet = workbook.getSheetAt(sheetnum);
            int rowNum = sheet.getLastRowNum() + 1;
            Map<String, Object> Header = new HashMap<String, Object>();
            for (int i = startrow; i < rowNum; i++) {
                Map<String, Object> map = null;
                Row row = sheet.getRow(i);
                if (row == null) break;
                int cellNum = row.getLastCellNum();
                if (i != 0) {
                    map = new HashMap<String, Object>();
//                    map.put("lineNumber", i + 1);
                }
                for (int j = startcol; j < cellNum; j++) {
                    Cell cell2 = row.getCell(j);
                    String cellValue = getCellValue(cell2);

                    if (i == 0) {
                        Header.put(j + "", cellValue);
                    } else {
                        map.put((String) Header.get(j + ""), cellValue);
                    }
                }
                if (null != map) {
                    Collection<Object> values = map.values();
                    Set<String> keySet = map.keySet();
                    long count2 = keySet.stream().filter(o -> !o.equals("lineNumber")).count();
                    long count = values.stream().filter(o -> null == o).count();
                    if (count < count2) {
                        list.add(map);
                    }
                }
            }
            workbook.close();
        } catch (Exception e) {
            throw e;
        }
        return list;
    }

    private static Workbook getExcelWorkBook(InputStream inputStream,String fileName) throws IOException {
        String fileType= FileTypeUtils.getFileType(fileName);
        if (fileType.equalsIgnoreCase("xls")) {
            //xls格式
            return new HSSFWorkbook(inputStream);
        } else {
            //xlsx格式
            return new XSSFWorkbook(inputStream);
        }
    }

    private static String getCellValue(Cell cell) {
        String cellvalue = null;
        if (cell != null) {
            switch (cell.getCellType()) {
                case NUMERIC: {
                    short format = cell.getCellStyle().getDataFormat();
                    if (format == 14 || format == 31 || format == 57 || format == 58) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        double value = cell.getNumericCellValue();
                        Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
                        cellvalue = sdf.format(date);
                    }
                    else if (DateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
                        cellvalue = formater.format(date);
                    } else {
                        cellvalue = NumberToTextConverter.toText(cell.getNumericCellValue());

                    }
                    break;
                }
                case STRING:
                    cellvalue = cell.getStringCellValue().replaceAll("'", "''");
                    break;
                case BLANK:
                    cellvalue = null;
                    break;
                default: {
                    cellvalue = "";
                }
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;
    }




    /**
     * cast csv to bean
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> parseCsvData(MultipartFile file, Class<T> clazz) {
        InputStreamReader in = null;
        CsvToBean<T> csvToBean = null;
        try {
            in = new InputStreamReader(file.getInputStream(), "utf-8");
            HeaderColumnNameMappingStrategy<T> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(clazz);
            csvToBean = new CsvToBeanBuilder<T>(in).withMappingStrategy(strategy).build();
        } catch (Exception e) {
            log.error("数据转化失败");
            return null;
        }
        return csvToBean.parse();
    }

    /**
     * cast csv to list
     * @return
     * @throws IOException
     */
    public static List<Map<String,Object>> castCSVToList(String filePath) throws IOException {
        List<Map<String,Object>> dataList = new ArrayList<>();
        BufferedReader in = new BufferedReader(new FileReader(filePath));
        // 读取表格第一行作为map中的key
        String key = in.readLine();
        List<String> keyList = Arrays.stream(key.split(","))
                .filter(Objects::nonNull)
                .filter(string -> !string.isEmpty())
                .collect(Collectors.toList());

        String s = null;
        int line = 1;
        while ((s = in.readLine()) != null) {
            List<String> param = Arrays.stream(s.split(","))
                    .filter(Objects::nonNull)
                    .filter(string -> !string.isEmpty())
                    .collect(Collectors.toList());
            line++;
            if (keyList.size() != param.size()) {
                System.out.println("数据第" + line + "行存在空数据,请核查！");
                continue;
            }
            Map<String, Object> keyParam = keyList.stream()
                    .collect(Collectors.toMap(keys -> keys, keys -> param.get(keyList.indexOf(keys))));
            dataList.add(keyParam);
        }
        return dataList;
    }

    /**
     * Export data to CSV file
     * @param dataList
     * @param filePath path + fileName
     */
    public static void exportToCsv(List<?> dataList, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            if (!dataList.isEmpty()) {
                // 获取对象的属性名作为CSV文件的表头
                Class<?> clazz = dataList.get(0).getClass();
                Field[] fields = clazz.getDeclaredFields();
                StringBuilder header = new StringBuilder();
                for (Field field : fields) {
                    header.append(field.getName()).append(",");
                }
                header.deleteCharAt(header.length() - 1); // 删除最后一个逗号
                header.append("\n");
                writer.write(header.toString());

                // 遍历对象列表，提取属性值并写入CSV文件
                for (Object data : dataList) {
                    StringBuilder rowData = new StringBuilder();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        Object value = field.get(data);
                        rowData.append(value != null ? value.toString() : "").append(",");
                    }
                    rowData.deleteCharAt(rowData.length() - 1); // 删除最后一个逗号
                    rowData.append("\n");
                    writer.write(rowData.toString());
                }
            }
        } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    /**
     * 输出指定文件的byte数组
     * 
     * @param filePath 文件路径
     * @param os 输出流
     * @return
     */
    public static void writeBytes(String filePath, OutputStream os) throws IOException
    {
        FileInputStream fis = null;
        try
        {
            File file = new File(filePath);
            if (!file.exists())
            {
                throw new FileNotFoundException(filePath);
            }
            fis = new FileInputStream(file);
            byte[] b = new byte[1024];
            int length;
            while ((length = fis.read(b)) > 0)
            {
                os.write(b, 0, length);
            }
        }
        catch (IOException e)
        {
            throw e;
        }
        finally
        {
            IOUtils.close(os);
            IOUtils.close(fis);
        }
    }

    /**
     * cast xml to list
     * @return
     */
    public static List<Map<String,Object>> castXmlToList(String fileName) throws IOException {
        InputStream inputStream = new FileInputStream(new File(fileName));
        String xml = IOUtils.toString(inputStream);
        List<Map<String,Object>> mapList = new ArrayList<>();
        Map<String,Object> retMap = new HashMap<>();
        try {
            SAXReader reader = new SAXReader();
            InputStream targetStream = IOUtils.toInputStream(xml, StandardCharsets.UTF_8.name());
            Document document = reader.read(targetStream);
            Element rootElement = document.getRootElement();
            StringBuilder builder = new StringBuilder();
            mapList = parser(rootElement, builder, null, retMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return mapList;
    }

    /**
     * xml递归解析器
     * @param ele 解析节点
     * @param eleKey 上级节点key
     * @param retMap 返回map
     */
    private static List<Map<String,Object>> parser(Element ele, StringBuilder eleKey, String firstEleName, Map<String,Object> retMap){
        List<Map<String,Object>> list = new ArrayList<>();
        StringBuilder builder = new StringBuilder(eleKey.toString());
        if(StringUtils.isEmpty(firstEleName)
                || firstEleName.equals(ele.getName())){
            firstEleName = null;
            builder.append("->"+ele.getName());
            if(StringUtils.isNotEmpty(StringUtils.stripToEmpty(ele.getData()+""))){
                retMap.put(builder.toString(), StringUtils.stripToEmpty(ele.getData()+""));
            }
            List<Attribute> attributes = ele.attributes();
            for (Attribute attribute : attributes) {
                StringBuilder builder1 = new StringBuilder(builder.toString());
                builder1.append("=>"+attribute.getName());
                if(StringUtils.isNotEmpty(attribute.getValue())){
                    retMap.put(builder1.toString(),attribute.getValue());
                    list.add(retMap);
                }
            }
        }
        Iterator iterator1 = ele.elementIterator();
        while (iterator1.hasNext()){
            Element eleChild = (Element) iterator1.next();
            parser(eleChild,builder,firstEleName,retMap);
        }
        return list;
    }


    /**
     * 写数据到文件中
     *
     * @param data 数据
     * @return 目标文件
     * @throws IOException IO异常
     */
    public static String writeImportBytes(byte[] data) throws IOException
    {
        return writeBytes(data, PaConfig.getImportPath());
    }

    /**
     * 写数据到文件中
     *
     * @param data 数据
     * @param uploadDir 目标文件
     * @return 目标文件
     * @throws IOException IO异常
     */
    public static String writeBytes(byte[] data, String uploadDir) throws IOException
    {
        FileOutputStream fos = null;
        String pathName = "";
        try
        {
            String extension = getFileExtendName(data);
            pathName = DateUtils.datePath() + "/" + IdUtils.fastUUID() + "." + extension;
            File file = FileUploadUtils.getAbsoluteFile(uploadDir, pathName);
            fos = new FileOutputStream(file);
            fos.write(data);
        }
        finally
        {
            IOUtils.close(fos);
        }
        return FileUploadUtils.getPathFileName(uploadDir, pathName);
    }

    /**
     * 删除文件
     * 
     * @param filePath 文件
     * @return
     */
    public static boolean deleteFile(String filePath)
    {
        boolean flag = false;
        File file = new File(filePath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists())
        {
            flag = file.delete();
        }
        return flag;
    }

    /**
     * 文件名称验证
     * 
     * @param filename 文件名称
     * @return true 正常 false 非法
     */
    public static boolean isValidFilename(String filename)
    {
        return filename.matches(FILENAME_PATTERN);
    }

    /**
     * 检查文件是否可下载
     * 
     * @param resource 需要下载的文件
     * @return true 正常 false 非法
     */
    public static boolean checkAllowDownload(String resource)
    {
        // 禁止目录上跳级别
        if (StringUtils.contains(resource, ".."))
        {
            return false;
        }

        // 检查允许下载的文件规则
        if (ArrayUtils.contains(MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION, FileTypeUtils.getFileType(resource)))
        {
            return true;
        }

        // 不在允许下载的文件规则
        return false;
    }

    /**
     * 下载文件名重新编码
     * 
     * @param request 请求对象
     * @param fileName 文件名
     * @return 编码后的文件名
     */
    public static String setFileDownloadHeader(HttpServletRequest request, String fileName) throws UnsupportedEncodingException
    {
        final String agent = request.getHeader("USER-AGENT");
        String filename = fileName;
        if (agent.contains("MSIE"))
        {
            // IE浏览器
            filename = URLEncoder.encode(filename, "utf-8");
            filename = filename.replace("+", " ");
        }
        else if (agent.contains("Firefox"))
        {
            // 火狐浏览器
            filename = new String(fileName.getBytes(), "ISO8859-1");
        }
        else if (agent.contains("Chrome"))
        {
            // google浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        }
        else
        {
            // 其它浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        }
        return filename;
    }

    /**
     * 下载文件名重新编码
     *
     * @param response 响应对象
     * @param realFileName 真实文件名
     */
    public static void setAttachmentResponseHeader(HttpServletResponse response, String realFileName) throws UnsupportedEncodingException
    {
        String percentEncodedFileName = percentEncode(realFileName);

        StringBuilder contentDispositionValue = new StringBuilder();
        contentDispositionValue.append("attachment; filename=")
                .append(percentEncodedFileName)
                .append(";")
                .append("filename*=")
                .append("utf-8''")
                .append(percentEncodedFileName);

        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition,download-filename");
        response.setHeader("Content-disposition", contentDispositionValue.toString());
        response.setHeader("download-filename", percentEncodedFileName);
    }

    /**
     * 百分号编码工具方法
     *
     * @param s 需要百分号编码的字符串
     * @return 百分号编码后的字符串
     */
    public static String percentEncode(String s) throws UnsupportedEncodingException
    {
        String encode = URLEncoder.encode(s, StandardCharsets.UTF_8.toString());
        return encode.replaceAll("\\+", "%20");
    }

    /**
     * 获取图像后缀
     * 
     * @param photoByte 图像数据
     * @return 后缀名
     */
    public static String getFileExtendName(byte[] photoByte)
    {
        String strFileExtendName = "jpg";
        if ((photoByte[0] == 71) && (photoByte[1] == 73) && (photoByte[2] == 70) && (photoByte[3] == 56)
                && ((photoByte[4] == 55) || (photoByte[4] == 57)) && (photoByte[5] == 97))
        {
            strFileExtendName = "gif";
        }
        else if ((photoByte[6] == 74) && (photoByte[7] == 70) && (photoByte[8] == 73) && (photoByte[9] == 70))
        {
            strFileExtendName = "jpg";
        }
        else if ((photoByte[0] == 66) && (photoByte[1] == 77))
        {
            strFileExtendName = "bmp";
        }
        else if ((photoByte[1] == 80) && (photoByte[2] == 78) && (photoByte[3] == 71))
        {
            strFileExtendName = "png";
        }
        return strFileExtendName;
    }

    /**
     * 获取文件名称 /profile/upload/2022/04/16/carnival.png -- carnival.png
     * 
     * @param fileName 路径名称
     * @return 没有文件路径的名称
     */
    public static String getName(String fileName)
    {
        if (fileName == null)
        {
            return null;
        }
        int lastUnixPos = fileName.lastIndexOf('/');
        int lastWindowsPos = fileName.lastIndexOf('\\');
        int index = Math.max(lastUnixPos, lastWindowsPos);
        return fileName.substring(index + 1);
    }

    /**
     * 获取不带后缀文件名称 /profile/upload/2022/04/16/carnival.png -- carnival
     * 
     * @param fileName 路径名称
     * @return 没有文件路径和后缀的名称
     */
    public static String getNameNotSuffix(String fileName)
    {
        if (fileName == null)
        {
            return null;
        }
        String baseName = FilenameUtils.getBaseName(fileName);
        return baseName;
    }
}
