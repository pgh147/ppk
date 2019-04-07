package cn.springboot.service.product;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.springboot.common.util.CommonUtils;
import cn.springboot.model.auth.User;
import cn.springboot.model.product.TProduct;


/**
 * @author liutao<br>
 * @ClassName: ExportExcelService<br>
 * @Description: export excel 服务<br>
 * @date 2016年12月5日下午9:50:41<br>
 */

@SuppressWarnings("rawtypes")
@Service
public class ExportBigExcelService {
    private final Logger logger = LoggerFactory.getLogger(ExportBigExcelService.class);
    public static final int rowAccessWindowSize = 5000; //内存中保留的行数，超出后会写到磁盘
    public static final int perSheetRows = 1000000; //每个sheet最大1048576w条
    private final String EXPORT_FILENAME_PREFIX = "excel";
    private final String DOWNLOAD_URL = "download?fileurl=";    
    
//    public static void main(String[] args) {
//    	ExportBigExcelService s = new ExportBigExcelService();
//    	User user = new User();
//    	user.setUsername("penggh");
//        // 导出列Map
//        List<Map> exportColumns = new ArrayList<>();;
//        Map<String,String> map1 = new HashMap<String,String>(); 
//        Map<String,String> map2 = new HashMap<String,String>(); 
//        Map<String,String> map3 = new HashMap<String,String>(); 
//        map1.put("title", "productName");
//        map1.put("field", "productName");
//        map2.put("title", "productNo");
//        map2.put("field", "productNo");
//        map3.put("title", "ingQty");
//        map2.put("field", "ingQty");
//        exportColumns.add(map1);exportColumns.add(map2);exportColumns.add(map3);
//        List<TProduct> listData = new ArrayList<TProduct>();
//        TProduct p1 = new TProduct();
//        p1.setProductName("penggege");
//        p1.setProductNo("1");
//        p1.setIngQty(67);
//        TProduct p2 = new TProduct();
//        p2.setProductName("penggege");
//        p2.setProductNo("1");
//        p2.setIngQty(67);
//        TProduct p3 = new TProduct();
//        p3.setProductName("penggege");
//        p3.setProductNo("1");
//        p3.setIngQty(67);
//        listData.add(p1);listData.add(p2);listData.add(p3);
//    	s.export(user,exportColumns,listData);
//	}
    /**
     * 支持大数据量导出
     * excel 每个sheet最多1048576行
     *
     * @param searchable      查询条件
     * @param user            当前用户
     * @param reqData         导出请求类
     *                        数据格式 {"enableFlag":"{\"comboData\":[{\"id\":\"0\",\"text\":\"禁用\"},{\"id\":\"1\",\"text\":\"启用\"}],\"textFiled\":\"text\",\"valeFiled\":\"id\"}","brandNo":"{\"comboKey\":\"bas_brand\",\"textFiled\":\"brandName\",\"valeFiled\":\"brandNo\"}","divisionNo":"{\"comboKey\":\"bas_division\",\"textFiled\":\"divisionName\",\"valeFiled\":\"divisionNo\"}"}
     * @param baseService     实体服务
     * @throws Exception 
     */
    //@Async
    public SXSSFWorkbook export(User user,List<Map> exportColumns,List<?> listData) throws Exception  {
        
        long startTime=System.currentTimeMillis();
        
        logger.info("start execute time:"+startTime);
        
        int totalRows = 1; //统计总行数
        SXSSFWorkbook wb = null;
        long beginTime = System.currentTimeMillis();
        wb = new SXSSFWorkbook(rowAccessWindowSize);
        Map<String, Boolean> numberFieldMap = new HashMap<String, Boolean>();
        Map<String, String> combosCache = new HashMap<String, String>();
        // 创建标题样式
        CellStyle styleHead = createStyleHead(wb);
        // 创建内容样式
        CellStyle styleRIGHT = createStyleContent(wb, "right");
        CellStyle styleLEFT = createStyleContent(wb, "left");
        Sheet sheet=wb.createSheet("sheet1");
        //生成标题(必须放在前面，否则导出大数据EXCEL标题丢失)
        createHeadRow(sheet, exportColumns, styleHead);
        totalRows = 1;
        // 生成excel内容
//            List<?> listData = null;
        // 获取导出数据，，数据量大时无法导出的问题
        for (Object data : listData) {
            Object obj = data;
            Row row = sheet.createRow(totalRows);
            setExcelDataRow(obj, exportColumns, numberFieldMap,
                    row, styleRIGHT, styleLEFT,
                     combosCache);
            totalRows++;
        }

            // 处理文件并发送通知
//            processFileAndSendNotification(wb, beginTime, user, response);
        long finishedTime = System.currentTimeMillis();//处理完成时间
        logger.info("finished  execute time:"+(finishedTime - startTime)/1000 + "s");
       
        return wb;
    }

    private Row createHeadRow(Sheet sheet, List<Map> exportColumns, CellStyle style1) {
        //设置表头
        Row headerRow = sheet.createRow(0);
        headerRow.setHeightInPoints(20);
        for (int i = 0; i < exportColumns.size(); i++) {
            Cell cell1 = headerRow.createCell(i);
            cell1.setCellType(XSSFCell.CELL_TYPE_STRING);
            cell1.setCellValue(exportColumns.get(i).get("title").toString());
            cell1.setCellStyle(style1);
        }
        return headerRow;
    }

    /**
     * 创建标题的样式
     *
     * @param wb
     * @return
     */
    private CellStyle createStyleHead(Workbook wb) {
        //设置样式 表头
        CellStyle styleHead = wb.createCellStyle();
        styleHead.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        Font font1 = wb.createFont();
        font1.setFontHeightInPoints((short) 13);
        font1.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        styleHead.setFont(font1);

        return styleHead;
    }

    /**
     * 创建内容的样式
     *
     * @param wb
     * @return
     */
    private CellStyle createStyleContent(Workbook wb, String align) {
        CellStyle styleContent = wb.createCellStyle();
        if (Objects.equals("left", align)) {
            styleContent.setAlignment(XSSFCellStyle.ALIGN_LEFT);
        } else if (Objects.equals("right", align)) {
            styleContent.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
        }
        styleContent.setWrapText(false);

        return styleContent;
    }



    private void setExcelDataRow(Object vo, List<Map> exportColumns, Map<String, Boolean> numberFieldMap,
                                 Row row, CellStyle styleRIGHT, CellStyle styleLEFT,
                                  Map<String, String> combosCache) throws Exception {
        setExcelDataRow(vo, exportColumns, numberFieldMap, row, styleRIGHT, styleLEFT, combosCache, 0);
    }

    /**
     * 设置excel数据行
     *
     * @param vo
     * @param exportColumns
     * @param numberFieldMap
     * @param row
     * @param styleRIGHT
     * @param styleLEFT
     * @param exportCombos
     * @param combosCache
     * @param realMaxFnSize   实际最大的Fn数
     * @throws Exception
     */
    private void setExcelDataRow(Object vo, List<Map> exportColumns, Map<String, Boolean> numberFieldMap,
                                 Row row, CellStyle styleRIGHT, CellStyle styleLEFT,
                                  Map<String, String> combosCache,
                                 int realMaxFnSize) throws Exception {
        Map<String, Object> voMap = new HashMap<String, Object>();
        Class<?> clazz = vo.getClass();
        if (vo instanceof JSONObject) {
            for (Map.Entry<String, Object> entry : ((JSONObject) vo).entrySet()) {
                voMap.put(entry.getKey(), entry.getValue());
            }
        } else {
            CommonUtils.object2MapWithoutNull(vo, voMap);
        }

        // 处理非尺码横排列字段
        for (int i = 0; i < exportColumns.size(); i++) {
            String fieldName = (String) exportColumns.get(i).get("field");
            String fieldValue = voMap.get(fieldName) == null ? "" : String.valueOf(voMap.get(fieldName));
//            fieldValue = this.getComboText(fieldValue, fieldName, exportCombos, combosCache);
            Cell cell = row.createCell(i);
            setExcelDataCell(cell, fieldName, fieldValue, clazz, numberFieldMap, styleRIGHT, styleLEFT);
        }
    }


    /**
     * 设置excel数据列
     *
     * @param cell
     * @param fieldName
     * @param fieldValue
     * @param clazz
     * @param numberFieldMap
     * @param styleRIGHT
     * @param styleLEFT
     */
    private void setExcelDataCell(Cell cell, String fieldName, String fieldValue,
                                  Class<?> clazz, Map<String, Boolean> numberFieldMap,
                                  CellStyle styleRIGHT, CellStyle styleLEFT) {
        if (StringUtils.isNotBlank(fieldValue) && StringUtils.isNotBlank(fieldName)) {
            if (!numberFieldMap.containsKey(fieldName)) {
                if (CommonUtils.fieldIsNumber(clazz, fieldName)) {
                    try {
                        cell.setCellValue(new Double(fieldValue));
                        numberFieldMap.put(fieldName, true);
                        cell.setCellStyle(styleRIGHT);
                    } catch (Exception e) {
                        cell.setCellValue(fieldValue);
                        cell.setCellStyle(styleLEFT);
                        numberFieldMap.put(fieldName, false);
                    }

                    return;
                }
            } else {
                if (numberFieldMap.get(fieldName)) {
                    cell.setCellValue(new Double(fieldValue));
                    cell.setCellStyle(styleRIGHT);
                    return;
                } else {
                    cell.setCellValue(fieldValue);
                    cell.setCellStyle(styleLEFT);
                    return;
                }
            }
            cell.setCellValue(fieldValue);
        }
        cell.setCellStyle(styleLEFT);
    }

    /**
     * 生成要导出的文件名
     *
     * @param user
     * @param contextRealPath
     * @param extension
     * @return
     */
    private String generateFilename(final User user, final String contextRealPath, final String extension, final String fileName) {
        return generateFilename(user, contextRealPath, null, extension, fileName);
    }

    private String generateFilename(final User user, final String contextRealPath, Integer index,
                                    final String extension, final String fileName) {
        String path = contextRealPath+user.getUsername();
        path =  path+fileName + "_" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS") + (index != null ? ("_" + index) : "") + "." + extension;

        File file = new File(path);
        if (!file.exists()) {
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            return path;
        }
        return generateFilename(user, contextRealPath, extension, fileName);
    }

    /**
     * 写入，保存文件(mongo)并发送通知
     *
     * @param wb
     * @param beginTime
     * @param user
     * @throws Exception
     */
    public void processFileAndSendNotification( Workbook wb,long beginTime, User user,HttpServletResponse response) throws Exception {
        String fileName ="export";
        String fileType = "xlsx";
        String contextRealPath = "D:\\";
        fileName = generateFilename(user, contextRealPath, StringUtils.isBlank(fileType) ? "xlsx" : fileType,
                StringUtils.isBlank(fileName) ? EXPORT_FILENAME_PREFIX : fileName);
        File file = new File(fileName);

        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
        wb.write(out);
        IOUtils.closeQuietly(out);
        // 保存文件
        String mongoFileUrl = null;//mongodbManager.insert(MongoConstants.MONGO_UC_STORE, file.getAbsolutePath(), MediaType.EXECEL, TimeMachine.now().forward(3).days().inTime());

        
        if (file.isFile()) {
            file.delete();//删除文件
        }

        long endTime = System.currentTimeMillis();
        Map<String, Object> context = new HashMap<String, Object>();
        context.put("seconds",(double)((endTime - beginTime) / 1000));   // 需强制转换为double，则可以得到小数部分
//        context.put("ctx", basePath);
        String url = DOWNLOAD_URL + mongoFileUrl;
        if (!url.startsWith("/")) {
            url = "/" + url;
        }
        context.put("url", url);

    }

//    private Cell createCell(Row row, int cellNo, String cellValue, CellStyle style) {
//        Cell cell = row.createCell(cellNo);
//        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
//        cell.setCellValue(cellValue == null ? "" : cellValue);
//        cell.setCellStyle(style);
//        return cell;
//    }


}