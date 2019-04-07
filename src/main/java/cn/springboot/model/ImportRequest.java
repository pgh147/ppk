package cn.springboot.model;


/**
 * 
 * @ClassName: ImportReqøuest<br>
 * @Description: 导入请求封装<br>
 * @author liutao<br>
 * @date 2016年10月22日下午10:20:38<br>
 *
 */

public class ImportRequest {
	/**
	 * 导入列的属性,以','分隔
	 */
	private String colNames;
	
	/**
	 * 是否必须,需与列属性一一对应
	 */
	private String mustArray;
	
	/**
	 * 联合主键列
	 */
	private String mainKey;
	
	/**
	 * 联合主键列名称
	 */
	private String mainKeyName;
	
	/**
	 * 是否全部验证通过才进行导入
	 */
	private boolean validateAll;

	/**
	 * 主表字段json串, like
	 * [{'field':'divisionNo','value':'D01','map':'divisionNo1'},{'field':'billNo','value':'LT0002'}]
	 */
	
	private String masterFields;
	
	
	
	/**======================导入逻辑用，与前端参数无关===================**/
	/**
	 * 当前行
	 */
	private Integer index = 2;
	
	/**
	 * 成功条数
	 */
	private Integer successCount = 0;
	
	
	public String getColNames() {
		return colNames;
	}
	public void setColNames(String colNames) {
		this.colNames = colNames;
	}
	public String getMustArray() {
		return mustArray;
	}
	public void setMustArray(String mustArray) {
		this.mustArray = mustArray;
	}
	public String getMainKey() {
		return mainKey;
	}
	public void setMainKey(String mainKey) {
		this.mainKey = mainKey;
	}
	public String getMainKeyName() {
		return mainKeyName;
	}
	public void setMainKeyName(String mainKeyName) {
		this.mainKeyName = mainKeyName;
	}
	public boolean getValidateAll() {
		return validateAll;
	}
	public void setValidateAll(boolean validateAll) {
		this.validateAll = validateAll;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public Integer getSuccessCount() {
		return successCount;
	}
	public void setSuccessCount(Integer successCount) {
		this.successCount = successCount;
	}
	public String getMasterFields() {
		return masterFields;
	}
	public void setMasterFields(String masterFields) {
		this.masterFields = masterFields;
	}

	
	

}
