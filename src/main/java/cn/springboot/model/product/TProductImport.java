package cn.springboot.model.product;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import cn.springboot.model.BaseEntity;

/**
 * @Description excel导入的对象
 */
public class TProductImport implements BaseEntity<String> {

	private static final long serialVersionUID = 3624947930970250778L;

	private String amazonOrderId;
	private String merchantOrderId;// '产品编号',
//	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@JSONField(format = "yyyy-MM-dd'T'HH:mm:ss")
	private Date purchaseDate;	 //'购买日期',
//	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@JSONField(format = "yyyy-MM-dd'T'HH:mm:ss")
	private Date lastUpdatedDate	;
	private String orderStatus	;
	/**
	 * //'交付渠道',
	 */
	private String fulfillmentChannel	;
	/**
	 * //'销售渠道',
	 */
	private String salesChannel	;
	private String orderChannel	;
	private String url	;
	/**
	 * //'船舶服务水平'
	 */
	private String shipServiceLevel;	
	private String productName	;
	private String sku	;
	private String asin	;
	private String itemStatus	;
	private String quantity	;
	private String currency	;
	private BigDecimal itemPrice	;
	private String itemTax	;
	private BigDecimal shippingPrice	;
	private String shippingTax	;
	private BigDecimal giftWrapPrice	;
	private String giftWrapTax;	
	private String itemPromotionDiscount	;
	private String shipPromotionDiscount	;
	private String shipCity	;
	private String shipState	;
	private String shipPostalCode	;
	private String shipCountry	;
	private String promotionIds ;
	
	public String getAmazonOrderId() {
		return amazonOrderId;
	}
	public void setAmazonOrderId(String amazonOrderId) {
		this.amazonOrderId = amazonOrderId;
	}
	public String getMerchantOrderId() {
		return merchantOrderId;
	}
	public void setMerchantOrderId(String merchantOrderId) {
		this.merchantOrderId = merchantOrderId;
	}
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getFulfillmentChannel() {
		return fulfillmentChannel;
	}
	public void setFulfillmentChannel(String fulfillmentChannel) {
		this.fulfillmentChannel = fulfillmentChannel;
	}
	public String getSalesChannel() {
		return salesChannel;
	}
	public void setSalesChannel(String salesChannel) {
		this.salesChannel = salesChannel;
	}
	public String getOrderChannel() {
		return orderChannel;
	}
	public void setOrderChannel(String orderChannel) {
		this.orderChannel = orderChannel;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getShipServiceLevel() {
		return shipServiceLevel;
	}
	public void setShipServiceLevel(String shipServiceLevel) {
		this.shipServiceLevel = shipServiceLevel;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getAsin() {
		return asin;
	}
	public void setAsin(String asin) {
		this.asin = asin;
	}
	public String getItemStatus() {
		return itemStatus;
	}
	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public BigDecimal getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(BigDecimal itemPrice) {
		this.itemPrice = itemPrice;
	}
	public String getItemTax() {
		return itemTax;
	}
	public void setItemTax(String itemTax) {
		this.itemTax = itemTax;
	}
	public BigDecimal getShippingPrice() {
		return shippingPrice;
	}
	public void setShippingPrice(BigDecimal shippingPrice) {
		this.shippingPrice = shippingPrice;
	}
	public String getShippingTax() {
		return shippingTax;
	}
	public void setShippingTax(String shippingTax) {
		this.shippingTax = shippingTax;
	}
	public BigDecimal getGiftWrapPrice() {
		return giftWrapPrice;
	}
	public void setGiftWrapPrice(BigDecimal giftWrapPrice) {
		this.giftWrapPrice = giftWrapPrice;
	}
	public String getGiftWrapTax() {
		return giftWrapTax;
	}
	public void setGiftWrapTax(String giftWrapTax) {
		this.giftWrapTax = giftWrapTax;
	}
	public String getItemPromotionDiscount() {
		return itemPromotionDiscount;
	}
	public void setItemPromotionDiscount(String itemPromotionDiscount) {
		this.itemPromotionDiscount = itemPromotionDiscount;
	}
	public String getShipPromotionDiscount() {
		return shipPromotionDiscount;
	}
	public void setShipPromotionDiscount(String shipPromotionDiscount) {
		this.shipPromotionDiscount = shipPromotionDiscount;
	}
	public String getShipCity() {
		return shipCity;
	}
	public void setShipCity(String shipCity) {
		this.shipCity = shipCity;
	}
	public String getShipState() {
		return shipState;
	}
	public void setShipState(String shipState) {
		this.shipState = shipState;
	}
	public String getShipPostalCode() {
		return shipPostalCode;
	}
	public void setShipPostalCode(String shipPostalCode) {
		this.shipPostalCode = shipPostalCode;
	}
	public String getShipCountry() {
		return shipCountry;
	}
	public void setShipCountry(String shipCountry) {
		this.shipCountry = shipCountry;
	}
	public String getPromotionIds() {
		return promotionIds;
	}
	public void setPromotionIds(String promotionIds) {
		this.promotionIds = promotionIds;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String getId() {
		return amazonOrderId;
	}
	
	

}
