package cn.springboot.common.constants;

/**
 * 
 * @ClassName: Regexes<br>
 * @Description: 正则表达式匹配常量<br>
 * @see http://blog.csdn.net/kiss_vicente/article/details/8050816
 * @see http://www.runoob.com/java/java-regular-expressions.html
 * @author liutao<br>
 * @date 2016年11月10日下午3:39:37<br>
 *
 */
public interface Regexes {
	/**
	 * 匹配整数
	 */
	public static final String INTEGER = "^-?\\d+$";
	/**
	 * 匹配浮点
	 */
	public static final String DECIMAL = "^(-?\\d+)(\\.\\d+)?$";
	
	/**
	 * 匹配日期格式 DF1_8或DF2_8 yyyy-MM-dd yyyy/MM/dd
	 */
	public static final String DF_8 = "^\\d{4}\\D+\\d{2}\\D+\\d{2}$";
//	public static final String DF_8 = "^\\d{4}[-|/]((0[1-9])|1[0-2])[-|/]((0[1-9])|([1-2][0-9])|(3[0-1]))$";
	/**
	 * 匹配日期格式 DF1_8 yyyy-MM-dd
	 */
	public static final String DF1_8 = "^\\d{4}-((0[1-9])|1[0-2])-((0[1-9])|([1-2][0-9])|(3[0-1]))$";
	/**
	 * 匹配日期格式 DF2_8 yyyy/MM/dd
	 */
	public static final String DF2_8 = "^\\d{4}/((0[1-9])|1[0-2])/((0[1-9])|([1-2][0-9])|(3[0-1]))$";

}
