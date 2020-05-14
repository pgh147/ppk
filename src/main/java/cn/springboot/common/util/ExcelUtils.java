package cn.springboot.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

import cn.springboot.common.constants.DateFormats;
import cn.springboot.common.exception.MyselfMsgException;
import cn.springboot.common.util.ReflectUtils;
import cn.springboot.model.ImportResolve;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * @ClassName: ExcelUtils<br>
 * @Description: excel工具类<br>
 * @author liutao<br>
 * @date 2016年11月10日下午3:32:34<br>
 *
 */
public class ExcelUtils {
	private static Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

	private ExcelUtils() {

	}

	/**
	 * 从导入的Excel获取数据
	 * 
	 * @param request
	 * @param colNames
	 *            列名数组
	 * @param mustArray
	 *            对应列是否为必须
	 * @param mainKey
	 *            确定是否重复的键
	 * @param c
	 *            Bean.class
	 * @return
	 */
	public static <T> ImportResolve<T> getData(HttpServletRequest request, String[] colNames, String[] mustArray,
			String[] mainKey, Class<T> c, String objJson,String importName) throws Exception {
		ImportResolve<T> ret = new ImportResolve<T>();

		ret = getData(request, 0, 1, colNames, mustArray, mainKey, c, objJson,importName);

		return ret;
	}

	/**
	 * 从导入的Excel获取数据
	 * 
	 * @param request
	 * @param sheetIdx
	 *            Excel工作簿序号,从0开始
	 * @param firstLineIdx
	 *            首行数据序列号 一般从1开始
	 * @param colNames
	 *            列名数组
	 * @param mustArray
	 *            对应列是否为必须
	 * @param mainKey
	 *            确定是否重复的键
	 * @param c
	 *            Bean.class
	 * @return
	 * @throws Exception
	 */
	public static <T> ImportResolve<T> getData(HttpServletRequest request, int sheetIdx, int firstLineIdx,
			String[] colNames, String[] mustArray, String[] mainKey, Class<T> c, String objJson,String importName) throws Exception {
		ImportResolve<T> importResolve = new ImportResolve<T>();
		List<Integer> rowlist = new ArrayList<Integer>();
		List<T> list = new ArrayList<T>();
		InputStream in = null;
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile multipartFile = multipartRequest.getFile(importName);
		if (multipartFile == null) {
			throw new IllegalArgumentException("importFile must assumed"); // 必须指定importFile参数值
		}
		in = multipartFile.getInputStream();
		if (in.available() > 5242880) {
			throw new MyselfMsgException("导入文件不能超过5M");
		}

		Workbook wb = createWorkbook(in);
		Map<String, String> mainKeyMap = new HashMap<String, String>();
		Map<String, String> mainKeyValueMap = new HashMap<String, String>();
		if (mainKey != null) {
			for (String mk : mainKey) {
				mainKeyMap.put(mk, mk);
			}
		}
		int colNum = colNames.length;//
		Map<String, String> columsLine = new HashMap<String, String>();
		for (int i = 0; i < colNum; i++) {
			columsLine.put(colNames[i], String.valueOf((char) ('A' + i)) + "列");
		}
		importResolve.setColumsLine(columsLine);

		Sheet sheet = wb.getSheetAt(sheetIdx);

		T obj = null;
		String fieldTypeStr;
		String fieldName;
		String data;
		Map<String, String> dfMap = new HashMap<String,String>();//Maps.newHashMap();
		
		// 计算后面的空白行数量
		int blankEndCount = 0;
		for (int idx = sheet.getLastRowNum(); idx >= firstLineIdx; idx--) {
			Row row = sheet.getRow(idx);
			if (row == null) {
				blankEndCount++;
			} else {
				boolean isRowAllBlank = true;
				for (int colIdx = 0; colIdx < colNum; colIdx++) {
					data = GetValueTypeForXLSX(row.getCell(colIdx));
					if (!StringUtils.isBlank(data)) {
						isRowAllBlank = false;
						break;
					}
				}
				if (isRowAllBlank) {
					blankEndCount++;
				} else {
					break;
				}
			}
		}

		int len = sheet.getLastRowNum() - blankEndCount + 1;
		if (len > 1) {
			logger.info("excel 需要解析的行数为:" + (len - 1));
		} else {
			importResolve.setErrorMsg("数据异常: 导入的数据行为空");
			return importResolve;
		}

		StringBuilder sb = new StringBuilder("");
		for (int idx = firstLineIdx; idx < len; idx++) {
			Row row = sheet.getRow(idx);
			String mainKeyValue = "";
			if (row == null) {
				sb.append("【 ").append(idx + 1).append("行").append(" 】不能为空<br/>");
				continue;
			}
			// t对象实例化
			if (StringUtils.isBlank(objJson)) {
				obj = c.newInstance();
			} else {
				obj = JSON.parseObject(objJson, c);
			}
			// 处理对象其他属性
			boolean flag = true;
			for (int colIdx = 0; colIdx < colNum; colIdx++) {
				int orgCellType = row.getCell(colIdx) == null ? 1 : row.getCell(colIdx).getCellType();
				data = GetValueTypeForXLSX(row.getCell(colIdx));
				fieldName = colNames[colIdx];
				if (mainKeyMap.get(fieldName) != null) {
					mainKeyValue += data;
				}
				if (StringUtils.isBlank(fieldName)) {
					continue;
				}

				Field f = ReflectUtils.getAccessibleField(obj, fieldName);
				if (f == null) {
					throw new IllegalArgumentException("导入列[" + fieldName + "]在Class " + obj.getClass() + "中不存在");
				}

				// 对象属性相应值的处理，包括是否必录 && 数据合法性校验
				Object fieldValue = data;
				fieldTypeStr = f.getType().getName();
				// 是否必录
				String baseInfo = "[" + (idx + 1) + "行" + ((char) ('A' + colIdx)) + "列]";
				if ("true".equals(mustArray[colIdx]) && StringUtils.isBlank(data)) {
					sb.append(baseInfo).append("不能为空<br/>");
					flag = false;
					continue;
				}
				// 数据合法性
				if (fieldTypeStr.contains("int") || fieldTypeStr.contains("Integer") || fieldTypeStr.contains("Long")
						|| fieldTypeStr.contains("BigInteger")) {
					if (null != data && !"".equals(data)&&!CommonUtils.isInteger(data)) {
						sb.append(baseInfo).append("必须为整数<br/>");
						flag = false;
						continue;
					}
				} else if (fieldTypeStr.contains("BigDecimal")) {
					if (null != data && !"".equals(data) && !CommonUtils.isDecimal(data)) {
						sb.append(baseInfo).append("必须为数值<br/>");
						flag = false;
						continue;
					}
				} 
				// 日期类型特别处理
				if(fieldTypeStr.contains("Date")) {
					try {
						// 如果原本的cell类型为date, 则直接获取java date
						//TODO 最后一行日期类型的导入会有问题，不能将其置于导入文档中的第一列，否则报错
						if(orgCellType == Cell.CELL_TYPE_NUMERIC){
							fieldValue = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(Double.valueOf(data));
						}else{
							fieldValue = getDateData(dfMap, fieldName, data, f);
						}
					} catch (Exception e) {
						sb.append(baseInfo).append("日期非法或格式无效，支持格式yyyy-MM-dd或yyyy/MM/dd<br/>");
						flag = false;
						continue;
					}
					ReflectUtils.invokeSetter(obj, fieldName, fieldValue);
				}else{
					// 忽略类型的setValue
					CommonUtils.setValueByAnyType(obj, fieldName, fieldValue);
				}
			}
			if (!StringUtils.isBlank(mainKeyValue)) {
				if (mainKeyValueMap.get(mainKeyValue) != null) {
					sb.append("【 ").append(idx + 1).append("行").append(" 】为重复数据<br/>");
					continue;
				} else {
					mainKeyValueMap.put(mainKeyValue, mainKeyValue);
				}
			}
			if (flag) {
				rowlist.add(idx + 1);
				list.add(obj);
			}
		}
		if (sb.length() > 0) {
			importResolve.setErrorMsg("数据异常:" + sb.toString());
		} else {
			importResolve.setErrorMsg("");
		}
		importResolve.setRowlist(rowlist);
		importResolve.setDataList(list);
		logger.info("excel 解析成功的行数为:" + list.size());
		return importResolve;
	}
	
	/**
	 * 获取日期数据
	 * @param dfMap
	 * @param fieldName
	 * @param data
	 * @param f
	 * @return
	 * @throws Exception
	 */
	private static Date getDateData(Map<String, String> dfMap, String fieldName, String data, Field f)
			throws Exception {
		Date date = null;
		String  format= dfMap.get(fieldName);
		if (StringUtils.isEmpty(format)) {	// 未存在格式，先找JSONField定义的格式，若未转换成功，则使用默认格式转换
			if(f.isAnnotationPresent(JSONField.class)){
				format =  ((JSONField) f.getAnnotation(JSONField.class)).format();
				try {
////					date = DateUtils.parseDate(data, format);
					SimpleDateFormat formatter = new SimpleDateFormat(format);
//					Date date2 = formatter.parse(data);
//					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//					date = sdf.parse(sdf.format(date2));
////					String sDate=sdf.format(date);
//					
					
					Date date2 = formatter.parse(data);
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//					String sDate=sdf.format(date);
					date = sdf.parse(sdf.format(date2));
				} catch (Exception e) {
					date = getDateByDefaultFormat(dfMap, fieldName, data);
				}
			}else{
				date = getDateByDefaultFormat(dfMap, fieldName, data);
			}
		}else{	// 已存在格式，则直接转换
			date = DateUtils.parseDate(data, format);
		}
		
		return date;
	}
	public static void main(String[] args) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date date;
		try {
			date = formatter.parse("2019-03-08T06:00:25+00:00");
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			String sDate=sdf.format(date);
			Date d = sdf.parse(sdf.format(date));
//			System.out.println(sDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private static Date getDateByDefaultFormat(Map<String, String> dfMap, String fieldName, String data) throws Exception{
		data.replaceAll("/", "-");
		dfMap.put(fieldName, DateFormats.DF1_8);
		return  DateUtils.parseDate(data, DateFormats.DF1_8);
	}

	private static String GetValueTypeForXLSX(Cell cell) {
		if (cell == null)
			return null;
		Object obj;
		
		cell.setCellType(Cell.CELL_TYPE_STRING);
		obj = cell.getStringCellValue();
		
		return obj == null ? "" : (obj + "").trim();
	}

	/**
	 * 解决异常：Package should contain a content type part [M1.13]
	 * http://blog.csdn.net/llwan/article/details/8890190?source=1
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	private static Workbook createWorkbook(InputStream in) throws IOException, InvalidFormatException {
		if (!in.markSupported()) {
			in = new PushbackInputStream(in, 8);
		}
		if (POIFSFileSystem.hasPOIFSHeader(in)) {
			return new HSSFWorkbook(in);
		}
		if (POIXMLDocument.hasOOXMLHeader(in)) {
			return new XSSFWorkbook(OPCPackage.open(in));
		}
		throw new IllegalArgumentException("你的excel版本目前poi解析不了");
	}
	
	
	
	
	
	 /**
     * 支持大数据量导出
     * excel 每个sheet最多1048576行
     *
     */
    @Async
    public void export(List<?> exportData,List<Map> exportColumns) {

        int totalRows = 1; //统计总行数

        Workbook wb = null;
        try {
            long beginTime = System.currentTimeMillis();
            wb = new HSSFWorkbook();
            Map<String, Boolean> numberFieldMap = new HashMap<String, Boolean>();
            Map<String, String> combosCache = new HashMap<String, String>();
            // 创建标题样式
            CellStyle styleHead = createStyleHead(wb);
            // 创建内容样式
            CellStyle styleRIGHT = createStyleContent(wb, "right");
            CellStyle styleLEFT = createStyleContent(wb, "left");
                Sheet sheet = wb.createSheet();
                totalRows = 0;
                // 生成excel内容
                Page<?> page = null;
                int realMaxFnSize = 0;
                Set<String> sizeTypeNoSet = new HashSet<String>();
                for (Object data : exportData) {
                    Object obj = data;
                    Row row = sheet.createRow(totalRows);
                    setExcelDataRow(obj, exportColumns, numberFieldMap,
                            row, styleRIGHT, styleLEFT,
                             combosCache);
                    totalRows++;
                }
                 createHeadRow(sheet, exportColumns, styleHead);


            // 处理文件并发送通知
//            processFileAndSendNotification(reqData, wb, beginTime, user);
        } catch (Exception e) {
//            processException(e, user);
        }
    }

    private Row createHeadRow(Sheet sheet, List<Map> exportColumns, CellStyle style1) {
        // 移动最末尾空行到第0行，作为标题
        sheet.shiftRows(0, sheet.getLastRowNum(), 1);
        //设置表头
        Row headerRow = sheet.createRow(0);
        headerRow.setHeightInPoints(20);
        for (int i = 0; i < exportColumns.size(); i++) {
            Cell cell1 = headerRow.createCell(i);
            cell1.setCellType(HSSFCell.ENCODING_UTF_16);
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
        styleHead.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        Font font1 = wb.createFont();
        font1.setFontHeightInPoints((short) 13);
        font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
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
            styleContent.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        } else if (Objects.equals("right", align)) {
            styleContent.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        }
        styleContent.setWrapText(false);

        return styleContent;
    }
    

    private void setExcelDataRow(Object vo, List<Map> exportColumns, Map<String, Boolean> numberFieldMap,
                                 Row row, CellStyle styleRIGHT, CellStyle styleLEFT, Map<String, String> combosCache) throws Exception {
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
                                 Row row, CellStyle styleRIGHT, CellStyle styleLEFT, Map<String, String> combosCache,
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
//            fieldValue = this.getComboText(fieldValue, fieldName, combosCache);
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
     * 写入，
     *
     * @param wb
     * @param beginTime
     * @param user
     * @throws Exception
     */
    public void processFile( Workbook wb, long beginTime) throws Exception {
        String fileName = "D://dddd.xls";
        File file = new File(fileName);
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
        wb.write(out);
        IOUtils.closeQuietly(out);
        if (file.isFile()) {
            file.delete();//删除文件
        }
    }
}
