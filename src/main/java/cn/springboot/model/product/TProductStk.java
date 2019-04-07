package cn.springboot.model.product;

import cn.springboot.model.BaseEntity;

/**
 * @Description excel导入的对象
 */
public class TProductStk implements BaseEntity<String> {

	private static final long serialVersionUID = 3624947930970250778L;

	private String sellerSku;
	private String fulfillmentChannelSku;// '产品编号',
	private String asin;	 //'购买日期',
	private String conditionType	;
	private String warehouseConditionCode	;
	private String quantityAvailable	;


	
	@Override
	public String getId() {
		return sellerSku;
	}



	public String getSellerSku() {
		return sellerSku;
	}



	public void setSellerSku(String sellerSku) {
		this.sellerSku = sellerSku;
	}



	public String getFulfillmentChannelSku() {
		return fulfillmentChannelSku;
	}



	public void setFulfillmentChannelSku(String fulfillmentChannelSku) {
		this.fulfillmentChannelSku = fulfillmentChannelSku;
	}



	public String getAsin() {
		return asin;
	}



	public void setAsin(String asin) {
		this.asin = asin;
	}



	public String getConditionType() {
		return conditionType;
	}



	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;
	}



	public String getWarehouseConditionCode() {
		return warehouseConditionCode;
	}



	public void setWarehouseConditionCode(String warehouseConditionCode) {
		this.warehouseConditionCode = warehouseConditionCode;
	}



	public String getQuantityAvailable() {
		return quantityAvailable;
	}



	public void setQuantityAvailable(String quantityAvailable) {
		this.quantityAvailable = quantityAvailable;
	}
	
	

}
