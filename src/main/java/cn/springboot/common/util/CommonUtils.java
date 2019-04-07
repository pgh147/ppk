package cn.springboot.common.util;


import cn.springboot.common.constants.Regexes;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.BigIntegerConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.lang3.EnumUtils;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * Description: 公共工具类 
 * author: mokeo 
 * Createdate: 2016年9月25日上午11:47:54
 */
// @SuppressWarnings({ "rawtypes", "unchecked" })
// 请不要对整个类压制警告，因为这样可能会掩盖了一些重要的警告
// 尽量小范围的压制，且需要对压制警告处增加备注 effctive java n24
public class CommonUtils {
	private CommonUtils() {
	}



	/**
	 * 对字符串进行MD5加密
	 * 
	 * @param plainText
	 *            String
	 * @return String
	 */
	public static String md5(String plainText) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0) {
					i += 256;
				}
				if (i < 16) {
					buf.append("0");
				}
				buf.append(Integer.toHexString(i));
			}
			return buf.toString();
		} catch (NoSuchAlgorithmException e) {
			// e.printStackTrace();
			return "";
		}
	}

	/**
	 * 文件或url扩展名是否为指定的扩展名
	 * 
	 * @param fileName
	 * @param extension
	 * @return
	 */
	public static boolean isExtension(String fileName, String extension) {
		return (extension.equalsIgnoreCase(CommonUtils.getFileExtension(fileName))) ? true : false;
	}

	/**
	 * 获取文件或url扩展名
	 * 
	 * @param fileName、url
	 * @return
	 */
	public static String getFileExtension(String fileName) {
		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		} else {
			return "";
		}
	}

	/**
	 * javabean驼峰格式转下划线格式 如：testName test_name
	 * 
	 * @param javeBeanStr
	 * @return
	 */
	public static String convertJaveBeanStrToUnderLine(String javeBeanStr) {
		StringBuffer buf = new StringBuffer();
		Pattern p = Pattern.compile("[A-Z]");
		Matcher m = p.matcher(javeBeanStr);
		while (m.find()) {
			m.appendReplacement(buf, "_" + m.group(0));
		}
		m.appendTail(buf);
		return buf.toString().toLowerCase();
	}

	/**
	 * 下划线格式转javabean驼峰格式 如： test_name testName
	 * 
	 * @param underLineStr
	 * @return
	 */
	public static String convertUnderLineStrToJaveBean(String underLineStr) {
		StringBuffer buf = new StringBuffer(underLineStr);
		Matcher mc = Pattern.compile("_").matcher(underLineStr);
		int i = 0;
		while (mc.find()) {
			int position = mc.end() - (i++);
			// 如果'_'为最后的字符,则直接退出循环,解决db2中'rownumber_'字符串数组越界问题
			if (position == underLineStr.length()) {
				break;
			}
			buf.replace(position - 1, position + 1, buf.substring(position, position + 1).toUpperCase());
		}
		return buf.toString();
	}

	
	/**
	 * 设置对象属性值，不管设置值的类型，主要针对数值属性的设置
	 * @param sourceObj 对象
	 * @param propertyName	对象属性
	 * @param inputValue	设置值
	 */
	public static void setValueByAnyType(Object sourceObj, String propertyName, Object inputValue){
		setValueByAnyType(sourceObj, propertyName, inputValue, false);
	}

	public static void setValueByAnyType(Object sourceObj, String propertyName, Object inputValue, boolean ignoreNotFound){
		Object newValue = inputValue;
		String newPropertyName = convertUnderLineStrToJaveBean(propertyName);	//若为下划线'_',则自动转为大小,原生sql可以不用设别名
		Field f = ReflectUtils.getAccessibleField(sourceObj, newPropertyName);
		if(f == null){
			if(ignoreNotFound){
				return;
			}
			throw new RuntimeException("propertyName["+ propertyName +"] not in the vo class[" + sourceObj.getClass() + "]");
		}
		Class expectedType = f.getType();
		if(inputValue!=null && expectedType!=null && !expectedType.isAssignableFrom(inputValue.getClass())){
			newValue = convertValue(expectedType, inputValue);
		}
		ReflectUtils.invokeSetter(sourceObj, newPropertyName, newValue);
	}
	
	/**
	 * 转换为指定类型的value对象，主要为数值类型
	 * @param expectedType 期望类型当前只考虑了数值类型
	 * @param value 输入值
	 * @return
	 */
	public static Object convertValue(Class<?> expectedType, Object value){
		// 若为枚举类型，则转换为枚举对象
		if(expectedType.isEnum()){
			return EnumUtils.getEnum((Class<? extends Enum>)expectedType, value.toString());
		}
		String expectedTypeName = expectedType.getName();
		if(expectedTypeName.contains("Long")){
			return Long.valueOf(value.toString());
		}else if(expectedTypeName.contains("Integer") || expectedTypeName.contains("int")){
			return new IntegerConverter().convert(expectedType, value);
		}else if(expectedTypeName.contains("String")){
			return value.toString();
		}else if(expectedTypeName.contains("BigInteger")){
			return new BigIntegerConverter().convert(expectedType, value);
		}else if(expectedTypeName.contains("BigDecimal")){
			return new BigDecimalConverter().convert(expectedType, value.equals("")||null==value?BigDecimal.ZERO:value);
		}else if(expectedTypeName.contains("Decimal")){
			return new BigDecimalConverter().convert(expectedType, value);
		}else{
			return value;
		}
		
	}
	
	/**
	 * 判断是否为整数
	 * @param s
	 * @return
	 */
	public static boolean isInteger(String s){
		// 正则初始化
		Pattern p = Pattern.compile(Regexes.INTEGER);
		// 匹配器初始化
		Matcher m = p.matcher(s);
		return m.matches();
	}
	
	/**
	 * 判断是否为浮点数
	 * @param s
	 * @return
	 */
	public static boolean isDecimal(String s){
		// 正则初始化
		Pattern p = Pattern.compile(Regexes.DECIMAL);
		// 匹配器初始化
		Matcher m = p.matcher(s);
		return m.matches();
	}
	
	/*
	 * 判断是否为日期格式 yyyy-MM-dd yyyy/MM/dd
	 */
	public static boolean isDF_8(String s){
		Pattern p = Pattern.compile(Regexes.DF_8);
		Matcher m = p.matcher(s);
		return m.matches();
	}
	
	public static String handleMultiCondition(Object obj){
		if (obj instanceof Object[]) {
			StringBuilder sb = new StringBuilder();
			Object[] strs = (Object[])obj;
			for (int i = 0; i < strs.length; i++) {
				if (i == strs.length-1) {
					sb.append("'"+strs[i]+"'");
				}else{
					sb.append("'"+strs[i]+"',");
				}
			}
			return sb.toString();
		}
		return null;
	}
	
	
	
	/**
	 * 多字段；源对象，目标对象；目标对象值设置
	 * @param sourceObj
	 * @param targetObj
	 * @param fieldNames
	 */
	private static void setFieldValues(Object sourceObj, Object targetObj, String[] fieldNames){
		for(String s: fieldNames){
			String field = s;
			String mappedName = s;
			int index = s.indexOf(":");
			if(index > 0){
				field = s.substring(0, index);
				mappedName = s.substring(index+1);
			}

			Object fieldValue = ReflectUtils.invokeGetter(sourceObj, mappedName);
			ReflectUtils.invokeSetter(targetObj, field, fieldValue);
		}
	}
	
	/**
	 * 判断属性是否是数字类型
	 * @param fieldName
	 * @return
	 */
	public static boolean fieldIsNumber(Class<?> clazz, String fieldName){
		Field[] fields = clazz.getDeclaredFields();
		for(int i=0;i<fields.length;i++){
			if(fields[i].getName().equals(fieldName)){
				String typeName= fields[i].getType().toString();
				if(isNumberType(typeName))return true;
			}
		}
		Field[] fields1 = clazz.getSuperclass().getDeclaredFields();
		for(int i=0;i<fields1.length;i++){
			if(fields1[i].getName().equals(fieldName)){
				String typeName= fields1[i].getType().toString();
				if(isNumberType(typeName))return true;
			}
		}
		return false;
	}
	
	private static boolean isNumberType(String typeName){
		if(typeName.contains("Integer") || typeName.contains("int") 
				|| typeName.contains("Double") || typeName.contains("double")
				|| typeName.contains("Long") || typeName.contains("long")
				|| typeName.contains("BigDecimal")
				|| typeName.contains("Short") || typeName.contains("short")){
			return true;
		}
		return false;
	}

	/**
	 * 获取异常的堆栈信息
	 *
	 * @param t
	 * @return
	 */
	public static String getStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		try {
			t.printStackTrace(pw);
			return sw.toString();
		} finally {
			pw.close();
		}
	}

	public static void object2MapWithoutNull(Object obj, Map map)
			throws IllegalArgumentException, IllegalAccessException {
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			Field[] fields = superClass.getDeclaredFields();
			for (int j = 0; j < fields.length; j++) {
				fields[j].setAccessible(true);
				String fieldName = fields[j].getName();
				Object fieldValue = fields[j].get(obj);

				// 针对子类与父类中存在同样field的情况，直接以子类中的值为准，避免子类的值被父类所覆盖
				if(map.get(fieldName) != null){
					continue;
				}

				if (fieldValue != null) {
					if ((fieldValue instanceof Date)) {
						boolean jfFalge = fields[j].isAnnotationPresent(JSONField.class);
						if (jfFalge) {
							JSONField jf = (JSONField) fields[j].getAnnotation(JSONField.class);
							SimpleDateFormat sdf = new SimpleDateFormat(jf.format());
							map.put(fieldName, sdf.format(fieldValue));
						} else {
							SimpleDateFormat sdf = new SimpleDateFormat(cn.springboot.common.constants.DateFormats.DF1_16);
							map.put(fieldName, sdf.format(fieldValue));
						}
					} else {
						map.put(fieldName, fieldValue);
					}
				} else {
					map.put(fieldName, "");
				}
			}
		}
	}
	

//	public static void main(String[] args){
//		String s = "nae:2323";
//		String mappedName = s;
//		String field = s;
//		int index = s.indexOf(":");
//		if(index > 0){
//			field = s.substring(0, index);
//			mappedName = s.substring(index+1);
//		}
//		System.out.println(field);
//		System.out.println(mappedName);
//
//	}

}
