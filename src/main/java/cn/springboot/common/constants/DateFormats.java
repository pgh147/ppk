package cn.springboot.common.constants;

/**
 * 
 * @ClassName: DateFormats<br>
 * @Description: 日期格式常量<br>
 * @author liutao<br>
 * @date 2016年11月10日下午3:39:37<br>
 *
 */
public interface DateFormats {
	/**
	 * 格式命令规则 
	 * DF1_8
	 * 其中DF-格式标识, 1-连接符为-的格式串, 8-代表年月日时分秒有效位数
	 * DF2_8
	 * 其中DF-格式标识, 2-连接符为/的格式串, 8-代表年月日时分秒有效位数
	 */
	public static final String  DF1_8 = "yyyy-MM-dd";
	public static final String  DF2_8 = "yyyy/MM/dd";
	public static final String  DF1_16 = "yyyy-MM-dd HH:mm:ss";
	public static final String  DF2_16 = "yyyy/MM/dd HH:mm:ss";
	public static final String  DF1_6 = "yyyy-M-d";
	public static final String  DF2_6 = "yyyy/M/d";
	
}
